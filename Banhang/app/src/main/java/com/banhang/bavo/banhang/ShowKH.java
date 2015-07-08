package com.banhang.bavo.banhang;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


/**
 * Created by Ba Vo on 6/4/2015.
 */

public class ShowKH extends ActionBarActivity {
    ListView listView;
    EditText search;
    ListAdapter adapter;
    Button them;
    //
    //DrawerLayout dLayout;
    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    ////
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    LinkConnect l=new LinkConnect();

    ArrayList<HashMap<String,String>> mylist;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_showkh);
        new Khachhang().execute(l.getUrlIdkh());
        //Edit Test
        listView =(ListView)findViewById(R.id.listViewkh);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_kh);
        // dList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList=(ListView)findViewById(R.id.left_drawerkh);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ///
        search=(EditText)findViewById(R.id.Searchkh);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final ArrayList<HashMap<String,String>> arraytemple=new ArrayList<HashMap<String, String>>();
                String search=s.toString();
                for(int i=0;i<mylist.size();i++){
                    HashMap<String,String> current=mylist.get(i);
                    String artical=current.get("ID_KHACHHANG");
                    String artical1=current.get("MA_KHACHHANG");
                    String artical2=current.get("TEN_HANG");
                    if(search.equalsIgnoreCase(artical)){
                        current.put("ID_KHACHHANG",artical);
                        current.put("MA_KHACHHANG",artical1);
                        current.put("TEN_KHACHHANG",artical2);
                        arraytemple.add(current);
                    }
                }
                adapter=new SimpleAdapter(ShowKH.this, arraytemple, R.layout.showkh_main, new String[]{"ID_KHACHHANG","MA_KHACHHANG", "TEN_KHACHHANG"}, new int[]{R.id.textViewkh, R.id.textView2kh,R.id.textView3kh}) ;
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String, String> article = arraytemple.get(position);
                        String makhachhang = article.get("ID_KHACHHANG");
                        Intent intent = new Intent(ShowKH.this, Show_ctkh.class);
                        intent.putExtra("idkhachhang", makhachhang);
                        ShowKH.this.startActivity(intent);
                        finish();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ////
        them=(Button)findViewById(R.id.themkh);
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent insert=new Intent(ShowKH.this,ThemKH.class);
                ShowKH.this.startActivity(insert);
                finish();
            }
        });

    }
    ///
    private void addDrawerItems() {
        String[] osArray = {"Bán hàng", "Hàng hóa", "Khách hàng", "Báo cáo", "Quay lại"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    Intent intent = new Intent(ShowKH.this, Banhang_Showkh.class);
                    ShowKH.this.startActivity(intent);
                    finish();
                }
                else if(position==1){
                    Intent intent = new Intent(ShowKH.this, ShowIDHH.class);
                    ShowKH.this.startActivity(intent);
                    finish();
                }
                else if(position==2){
                    Toast.makeText(ShowKH.this,"Bạn đang ở trong trang khách hàng !",Toast.LENGTH_SHORT).show();
                }
                else if (position==3){
                    Intent intent=new Intent(ShowKH.this,Index_Baocao.class);
                    ShowKH.this.startActivity(intent);
                    finish();
                }
                else {
                    Intent intent=new Intent(ShowKH.this,Index.class);
                    ShowKH.this.startActivity(intent);
                    finish();
                }
            }
        });
    }
    ////Progress bar
    private void setupDrawer(){
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);


    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    ///////////////////////////////Het Navagation
    public void onBackPressed(){
        Intent intent=new Intent(ShowKH.this,Index.class);
        ShowKH.this.startActivity(intent);
        finish();
    }
    ////////////////////////////////////////////////////////
    private class Khachhang extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            URL url = null;
            try {
                //tao url
                url = new URL(params[0]);
                //thiet lap ket noi
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                //Ket noi
                connection.connect();

                //Doc du lieu
                InputStream inputStream = connection.getInputStream();
                Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");

                String kq = scanner.hasNext() ? scanner.next() : "";
                inputStream.close();
                return kq;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }
        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            mylist=new ArrayList<HashMap<String, String>>();
            //final ArrayList<HashMap<String,String>> searchResult=new ArrayList<>();
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("ShowAllKHResult");
                int n = array.length();
                //String hh = null;
                //String ten_hh = null;
                //Intent intent = new Intent(Index.this, ShowHH.class);
                for (int i = 0; i < n; i++) {
                    HashMap<String,String> map=new HashMap<String,String>();
                    JSONObject v1 = array.getJSONObject(i);
                    map.put("ID",String.valueOf(i));
                    map.put("ID_KHACHHANG",v1.getString("KHNCC_ID"));
                    map.put("MA_KHACHHANG","MÃ KHÁCH HÀNG: "+v1.getString("KHNCC_MAKHACHHANG"));
                    map.put("TEN_KHACHHANG", "TÊN KHÁCH HÀNG: " + v1.getString("KHNCC_TEN"));
                    mylist.add(map);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ShowKH.this,"Bạn phải đăng nhập để thấy được khách hàng !",Toast.LENGTH_SHORT).show();
            }
            adapter=new SimpleAdapter(ShowKH.this, mylist, R.layout.showkh_main, new String[]{"ID_KHACHHANG","MA_KHACHHANG", "TEN_KHACHHANG"}, new int[]{R.id.textViewkh, R.id.textView2kh,R.id.textView3kh}) ;
            listView.setAdapter(adapter);
            //listView.setTextFilterEnabled(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HashMap<String, String> article = mylist.get(position);
                    String makhachhhang = article.get("ID_KHACHHANG");
                    Intent intent = new Intent(ShowKH.this, Show_ctkh.class);
                    //Object obj=listView.getAdapter().getItem(position);
                    //Toast.makeText(getBaseContext(), "Xin chào: " + idhanghoa, Toast.LENGTH_LONG).show();
                    intent.putExtra("idkhachhang", makhachhhang);
                    ShowKH.this.startActivity(intent);
                    finish();
                }
            });


        }
    }
}

/*
* //System.out.println("Hang hoa: "+hh+"\n"+"TEN: "+ten_hh);
                    //intent.putExtra("id",hh);
                    //intent.putExtra("ten",ten_hh);
                    //Index.this.startActivity(intent);


                //intent.putExtra("id",hh);
                //intent.putExtra("ten",ten_hh);
                //Index.this.startActivity(intent);


                //Tham chi?u ??n "main" trong chu?i Json
                //JSONObject main = object.getJSONObject("ShowAllResult");


        //bt2 = (ImageButton) findViewById(R.id.bt2);
        //bt2.setOnClickListener(new View.OnClickListener() {
            //@Override
           // public void onClick(View v) {

            //}
        //});

        //Nhan intent duoc gui tu Main
        //String h = getIntent().getStringExtra("h");

        //Toast.makeText(getBaseContext(), h + "Dang Ba Vo", Toast.LENGTH_SHORT).show();
* */


