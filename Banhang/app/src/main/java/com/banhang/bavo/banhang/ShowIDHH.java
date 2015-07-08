package com.banhang.bavo.banhang;

/**
 * Created by Ba Vo on 6/3/2015.
 */

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
 * Created by Ba Vo on 5/21/2015.
 */
public class ShowIDHH extends ActionBarActivity {
    ListView listView;
    EditText search;
    ListAdapter adapter,adapter1;
    Button them;
    ArrayList<HashMap<String,String>> mylist;
    HashMap<String,String> map;
    //
    //DrawerLayout dLayout;
    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    ////
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_sp);
        LinkConnect l=new LinkConnect();
        //String url=l.getUrlIdhh();
        new HangHoa().execute(l.getUrlIdhh());
        //Edit Test
        listView =(ListView)findViewById(R.id.listView);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // dList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList=(ListView)findViewById(R.id.left_drawer);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ///
        search=(EditText)findViewById(R.id.Search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final ArrayList<HashMap<String,String>> arraytemple=new ArrayList<HashMap<String, String>>();
                String search=s.toString();
                for(int i=0;i<mylist.size();i++){
                    map=mylist.get(i);
                    String artical=map.get("ID_HANG");
                    String artical1=map.get("MA_HANG");
                    String artical2=map.get("TEN_HANG");
                    if(search.equalsIgnoreCase(artical)){
                        map.put("ID_HANG",artical);
                        map.put("MA_HANG",artical1);
                        map.put("TEN_HANG",artical2);
                        arraytemple.add(map);
                    }
                }
                adapter=new SimpleAdapter(ShowIDHH.this, arraytemple, R.layout.showhh_main, new String[]{"ID_HANG","MA_HANG", "TEN_HANG"}, new int[]{R.id.textView, R.id.textView2,R.id.textView3}) ;
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String, String> article = arraytemple.get(position);
                        String mahang = article.get("ID_HANG");
                        Intent intent = new Intent(ShowIDHH.this, ChitietHH.class);
                        intent.putExtra("mahang", mahang);
                        ShowIDHH.this.startActivity(intent);
                        finish();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ////
        them=(Button)findViewById(R.id.them);
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent insert=new Intent(ShowIDHH.this,InsertHH.class);
                ShowIDHH.this.startActivity(insert);
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
                    Intent intent = new Intent(ShowIDHH.this, Banhang_Showkh.class);
                    ShowIDHH.this.startActivity(intent);
                    finish();
                }
                else if(position==1){
                    Toast.makeText(ShowIDHH.this,"Bạn đang ở trong trang hàng hóa !",Toast.LENGTH_SHORT).show();
                }
                else if(position==2){
                    Intent intent = new Intent(ShowIDHH.this, ShowKH.class);
                    ShowIDHH.this.startActivity(intent);
                    finish();
                }
                else if (position==3){
                    Intent intent=new Intent(ShowIDHH.this,Index_Baocao.class);
                    ShowIDHH.this.startActivity(intent);
                    finish();
                }
                else {
                    Intent intent=new Intent(ShowIDHH.this,Index.class);
                    ShowIDHH.this.startActivity(intent);
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

    @Override
    public void onBackPressed(){
        Intent intent=new Intent(ShowIDHH.this,Index.class);
        ShowIDHH.this.startActivity(intent);
        finish();
    }

    ////////////////////////////////////////////////////////
    private class HangHoa extends AsyncTask<String, Void, String> {

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
                JSONArray array = object.getJSONArray("ShowAllResult");
                int n = array.length();
                for (int i = 0; i < n; i++) {
                    JSONObject v1 = array.getJSONObject(i);
                    map=new HashMap<String,String>();
                    map.put("ID",String.valueOf(i));
                    map.put("ID_HANG",v1.getString("HH_ID"));
                    map.put("MA_HANG","MÃ HÀNG: "+v1.getString("HH_MAHANGHOA"));
                    map.put("TEN_HANG", "TEN HANG: " + v1.getString("HH_TENHANG"));
                    mylist.add(map);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ShowIDHH.this,"Bạn phải đăng nhập để thấy được hàng hóa !",Toast.LENGTH_SHORT).show();
            }
            adapter=new SimpleAdapter(ShowIDHH.this, mylist, R.layout.showhh_main, new String[]{"ID_HANG","MA_HANG", "TEN_HANG"}, new int[]{R.id.textView, R.id.textView2,R.id.textView3}) ;
            listView.setAdapter(adapter);
            listView.setTextFilterEnabled(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HashMap<String,String> article=mylist.get(position);
                    String mahang = article.get("ID_HANG");
                    Intent intent = new Intent(ShowIDHH.this, ChitietHH.class);
                    intent.putExtra("mahang", mahang);
                    ShowIDHH.this.startActivity(intent);
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

