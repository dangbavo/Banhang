package com.banhang.bavo.banhang;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


/**
 * Created by Ba Vo on 6/4/2015.
 */

public class Banhang_Showkh extends ActionBarActivity {
    ListView listView;
    EditText search;
    ListAdapter adapter;
    ArrayList<HashMap<String, String>> mylist;
    //
    //DrawerLayout dLayout;
    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    ////
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    LinkConnect l = new LinkConnect();
    TaoDatabase db = new TaoDatabase(Banhang_Showkh.this);

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banhang_showkh);

        //Edit Test
        listView = (ListView) findViewById(R.id.listViewkh_bh);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_kh_bh);
        // dList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList = (ListView) findViewById(R.id.left_drawerkh_bh);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ///
        search = (EditText) findViewById(R.id.Searchkh_bh);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final ArrayList<HashMap<String, String>> arraytemple = new ArrayList<HashMap<String, String>>();
                String search = s.toString();
                for (int i = 0; i < mylist.size(); i++) {
                    HashMap<String, String> current = mylist.get(i);
                    String artical = current.get("ID_KHACHHANG");
                    String artical1 = current.get("MA_KHACHHANG");
                    String artical2 = current.get("TEN_KHACHHANG");
                    if (search.equalsIgnoreCase(artical)) {
                        current.put("ID_KHACHHANG", artical);
                        current.put("MA_KHACHHANG", artical1);
                        current.put("TEN_KHACHHANG", artical2);
                        arraytemple.add(current);
                    }
                }
                adapter = new SimpleAdapter(Banhang_Showkh.this, arraytemple, R.layout.banhang_showkh_main, new String[]{"ID_KHACHHANG", "MA_KHACHHANG", "TEN_KHACHHANG"}, new int[]{R.id.textViewkh_bh, R.id.textView2kh_bh, R.id.textView3kh_bh});
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String, String> article = arraytemple.get(position);
                        String makhachhhang = article.get("ID_KHACHHANG");
                        String tenkhachhang = article.get("TEN_KHACHHANG");
                        Intent intent = new Intent(Banhang_Showkh.this, Banhang_ctbh.class);
                        intent.putExtra("makhachhang", makhachhhang);
                        intent.putExtra("tenkhachhang", tenkhachhang);
                        Banhang_Showkh.this.startActivity(intent);
                        Banhang_Showkh.this.finish();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ////
        if(checkInternetConnection()==false){
            mylist=new ArrayList<HashMap<String, String>>();
            mylist=db.getKh();
            adapter=new SimpleAdapter(Banhang_Showkh.this, mylist, R.layout.banhang_showkh_main, new String[]{"ID_KHACHHANG","MA_KHACHHANG", "TEN_KHACHHANG"}, new int[]{R.id.textViewkh_bh, R.id.textView2kh_bh,R.id.textView3kh_bh}) ;
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(Banhang_Showkh.this, "vi tri" + position + "noi dung" + listView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                    //view=getView(position,view,parent);
                    HashMap<String, String> article = mylist.get(position);
                    String makhachhhang = article.get("ID_KHACHHANG");
                    String tenkhachhang = article.get("TEN_KHACHHANG");
                    Intent intent = new Intent(Banhang_Showkh.this, Banhang_ctbh.class);
                    intent.putExtra("makhachhang", makhachhhang);
                    intent.putExtra("tenkhachhang", tenkhachhang);
                    Banhang_Showkh.this.startActivity(intent);
                    finish();
                }
            });
            listView.setAdapter(adapter);
        }
        else {
            new Khachhang().execute(l.getUrlIdkh());
        }

    }
    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
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
                }
                else if(position==1){
                    Intent intent = new Intent(Banhang_Showkh.this, ShowIDHH.class);
                    Banhang_Showkh.this.startActivity(intent);
                    finish();
                }
                else if(position==2){
                    Intent intent = new Intent(Banhang_Showkh.this, ShowKH.class);
                    Banhang_Showkh.this.startActivity(intent);
                    finish();
                }
                else if (position==3) {
                    Intent intent = new Intent(Banhang_Showkh.this, Index_Baocao.class);
                    Banhang_Showkh.this.startActivity(intent);
                    finish();
                }
                else {
                    Intent intent=new Intent(Banhang_Showkh.this,Index.class);
                    Banhang_Showkh.this.startActivity(intent);
                    finish();
                }
            }
        });
    }

    ////Progress bar
    private void setupDrawer() {
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
    public void onBackPressed() {
        Intent intent = new Intent(Banhang_Showkh.this, Index.class);

        Banhang_Showkh.this.startActivity(intent);
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
                for (int i = 0; i < n; i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject v1 = array.getJSONObject(i);
                    map.put("ID", String.valueOf(i));
                    map.put("ID_KHACHHANG", v1.getString("KHNCC_ID"));
                    map.put("MA_KHACHHANG", "MÃ KHÁCH HÀNG: " + v1.getString("KHNCC_MAKHACHHANG"));
                    map.put("TEN_KHACHHANG", "TÊN KHÁCH HÀNG: " + v1.getString("KHNCC_TEN"));
                    mylist.add(map);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            adapter=new SimpleAdapter(Banhang_Showkh.this, mylist, R.layout.banhang_showkh_main, new String[]{"ID_KHACHHANG","MA_KHACHHANG", "TEN_KHACHHANG"}, new int[]{R.id.textViewkh_bh, R.id.textView2kh_bh,R.id.textView3kh_bh}) ;
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(Banhang_Showkh.this, "vi tri" + position + "noi dung" + listView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                    //view=getView(position,view,parent);
                    HashMap<String, String> article = mylist.get(position);
                    String makhachhhang = article.get("ID_KHACHHANG");
                    String tenkhachhang=article.get("TEN_KHACHHANG");
                    Intent intent = new Intent(Banhang_Showkh.this, Banhang_ctbh.class);
                    intent.putExtra("makhachhang",makhachhhang);
                    intent.putExtra("tenkhachhang", tenkhachhang);
                    Banhang_Showkh.this.startActivity(intent);
                    finish();
                }
            });
            listView.setAdapter(adapter);

        }
        public View getView(int positon,View convertView,ViewGroup parent){
            if(convertView==null){
                LayoutInflater inflater=(LayoutInflater)convertView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView=inflater.inflate(R.layout.banhang_showkh_main,null);
        }
            int select=positon;
            if (positon!=select){
                convertView.setBackgroundColor(Color.GRAY);
            }
            else {
                convertView.setBackgroundColor(Color.BLUE);
            }
            return convertView;
            }
    }
    //////////////////////////////////

}
