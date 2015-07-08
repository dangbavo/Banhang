package com.banhang.bavo.banhang;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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
 * Created by Ba Vo on 5/28/2015.
 */
public class Banhang_ctkh extends ActionBarActivity {
    private ListView bh_lv;
    private ListAdapter adapter;

    TextView tenkhang;
    EditText sluongmua;
    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    ArrayList<HashMap<String,String>> mylist;
    ////
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    LinkConnect l=new LinkConnect();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bh_ttkh);
        LinkConnect l=new LinkConnect();
        new Banhang_Showhh().execute(l.getUrlIdhh());

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_bh_ctkh);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_bh_ctkh);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //sluongmua=(EditText)findViewById(R.id.bh_main15);
        String tenkh=getIntent().getStringExtra("tenkhachhang");
        tenkhang=(TextView)findViewById(R.id.sotien_bh_ctkh);
        tenkhang.setText(tenkh);
    }

    //addDrawerItem
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
                    Intent intent = new Intent(Banhang_ctkh.this, ShowIDHH.class);
                    Banhang_ctkh.this.startActivity(intent);
                    finish();
                }
                else if(position==2){
                    Intent intent = new Intent(Banhang_ctkh.this, ShowKH.class);
                    Banhang_ctkh.this.startActivity(intent);
                    finish();
                }
                else if (position==3){
                    Intent intent=new Intent(Banhang_ctkh.this,Index_Baocao.class);
                    Banhang_ctkh.this.startActivity(intent);
                    finish();
                }
                else {
                    Intent intent=new Intent(Banhang_ctkh.this,Index.class);
                    Banhang_ctkh.this.startActivity(intent);
                    finish();
                }
            }
        });
    }
    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }


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
    /////////////het Navagation
    // Class Chitiet Hang hoa
    private class Banhang_Showhh extends AsyncTask<String, Void, String> {

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

                //??c d? li?u
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mylist = new ArrayList<HashMap<String, String>>();
            try {
                JSONObject json = new JSONObject(s);
                JSONArray array = json.getJSONArray("ShowAllResult");
                int n = array.length();
                for (int i = 0; i < n; i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject v = array.getJSONObject(i);
                    map.put("ID_HANG", v.getString("HH_ID"));
                    map.put("TEN_HANG", v.getString("HH_TENHANG"));
                    map.put("LOAI_HH", v.getString("HH_LOAIHANGHOAID"));
                    map.put("MA_HH", v.getString("HH_MAHANGHOA"));
                    map.put("DONGIA", v.getString("HH_DONGIABANLE"));
                    map.put("MA_HH", v.getString("HH_MAHANGHOA"));
                    map.put("SL", v.getString("HH_SL_CD"));
                    mylist.add(map);
                }
                bh_lv = (ListView) findViewById(R.id.bh_lv);
                adapter = new SimpleAdapter(Banhang_ctkh.this, mylist, R.layout.layout_bh_ttkh_main, new String[]{"ID_HANG", "MA_HH", "TEN_HANG", "DONGIA", "SL", "LOAI_HH"}, new int[]{R.id.bh_main9, R.id.bh_main10, R.id.bh_main11, R.id.bh_main12, R.id.bh_main13, R.id.bh_main14});
                bh_lv.setAdapter(adapter);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }/*
        public View getView(int position,View convertView, ViewGroup parent){
            final ViewHolder holder;
            if(convertView==null){
                holder=new ViewHolder();
                LayoutInflater inflater=Banhang_ctkh.this.getLayoutInflater();
                convertView=inflater.inflate(R.layout.layout_bh_ttkh_main,null);
                holder.edit1=(EditText)convertView.findViewById(R.id.bh_main15);
                convertView.setTag(holder);
            }else {
                holder=(ViewHolder)convertView.getTag();
            }
                holder.ref = position;
                holder.edit1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        mylist[holder.ref]=s.toString();
                    }
                });
            return convertView;
    }
    */
    }
    private class ViewHolder{
        TextView t1,t2,t3,t4,t5,t6;
        EditText edit1;
        int ref;
    }

    //
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Banhang_ctkh.this, Banhang_Showkh.class);
        Banhang_ctkh.this.startActivity(intent);
        finish();
    }
    ////////////////////////////////

}
