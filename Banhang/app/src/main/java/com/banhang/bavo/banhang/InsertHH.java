package com.banhang.bavo.banhang;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Ba Vo on 6/3/2015.
 */
public class InsertHH extends ActionBarActivity{
    private ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;
    String mActivityTitle;
    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    AutoCompleteTextView ten_loaihh_is,dvt_is;

    EditText mahang_is,ten_hang_is,giaban_is,giavon_is,sl_is,mau,quycach;
    Button xacnhan,huybo;

    LinkConnect l=new LinkConnect();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_hanghoa);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_inserthh);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_ishh);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //set edittext
        mahang_is=(EditText)findViewById(R.id.mahang_is);
        ten_hang_is=(EditText)findViewById(R.id.ten_hang_is);

        String [] itemdonvitinh={"Kg","Hộp","Cái","Chai","Miếng","Gói","Cal","Cục"};
        dvt_is=(AutoCompleteTextView)findViewById(R.id.dvt_is);
        dvt_is.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,itemdonvitinh));

        String [] itemtenloai={"CHONG THAM","CO SON","HANG SX LAI","HANG TTNT","KEO BONG","NPL-SX BOT","Sơn",
                "SON DAU MODENA,SON DAU NERO-PL2","SON NUOC B.DAU","SON NUOC DAVIT","SON NUOC MODENA(0.13)","SON NUOC MODENA(0.15)",
                "SON NUOC NANOSHINE","SON NUOC NAVY-SOLEX-ROTEX","SON NUOC NERO","SON NUOC NERO-PHUY","SON NUOC NERO-PL1","SON NUOC NERO-PL2",
                "T.MAU NERO","T.MAU-TOAN","TIEN V.CHUYEN CHO KH","Vật liệu xây dựng"};
        ten_loaihh_is=(AutoCompleteTextView)findViewById(R.id.ten_loaihh_is);
        ten_loaihh_is.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,itemtenloai));

        giaban_is=(EditText)findViewById(R.id.giaban_is);
        giavon_is=(EditText)findViewById(R.id.giavon_is);
        sl_is=(EditText)findViewById(R.id.sl_is);
        mau=(EditText)findViewById(R.id.mau_is);
        quycach=(EditText)findViewById(R.id.quycach_is);
        //set button
        xacnhan=(Button)findViewById(R.id.xacnhan);
        //set event
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mahang_t=mahang_is.getText().toString();
                String ten_hang_t=ten_hang_is.getText().toString();
                String dvt_t=dvt_is.getText().toString();
                String dvtt_t=null;
                if (dvt_t.equals("Kg")){
                    dvtt_t="15";
                }
                else if(dvt_t.equals("Lon")){
                    dvtt_t="13";
                }
                else if(dvt_t.equals("Hộp")){
                    dvtt_t="3";
                }
                else if(dvt_t.equals("Gói")){
                    dvtt_t="7";
                }
                String ten_loaihh_t=ten_loaihh_is.getText().toString();
                String ten_loaihangt_t=null;
                if(ten_loaihh_t.equals("CHONG THAM")){
                    ten_loaihangt_t="25";
                }
                else if(ten_loaihh_t.equals("CO SON")){
                    ten_loaihangt_t="22";
                }
                else if(ten_loaihh_t.equals("HANG SX LAI")){
                    ten_loaihangt_t="27";
                }
                else if(ten_loaihh_t.equals("HANG TTNT")){
                    ten_loaihangt_t="21";
                }
                String giaban_t=giaban_is.getText().toString();
                String giavon_t=giavon_is.getText().toString();
                String sl_t=sl_is.getText().toString();
                String mau_t=mau.getText().toString();
                String quycach_t=quycach.getText().toString();
                //su kien
                if(mahang_t.equals(null)||ten_hang_t.equals(null)||dvt_t.equals(null)||ten_loaihh_t.equals(null)){
                    Toast.makeText(InsertHH.this,"Không có dữ liệu cập nhật",Toast.LENGTH_SHORT).show();
                }
                else {

                    String url =  l.getUrlInsertHH()+ mahang_t +
                            "/" + Uri.encode(ten_hang_t)+ "/" + dvtt_t + "/"+ten_loaihangt_t+"/"+ giaban_t+"/" +giavon_t+ "/" +sl_t+ "/" + Uri.encode(mau_t)+"/"+Uri.encode(quycach_t);
                    new Themhanghoa().execute(url);
                }
            }
        });
        huybo=(Button)findViewById(R.id.huybo);
        huybo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mahang_is.setText("");
                ten_hang_is.setText("");
                dvt_is.setText("");
                ten_loaihh_is.setText("");
                giaban_is.setText("");
                giavon_is.setText("");
                sl_is.setText("");
                mau.setText("");
                quycach.setText("");

            }
        });
    }
    private void addDrawerItems() {
        String[] osArray = {"Bán hàng", "Hàng hóa", "Khách hàng", "Báo cáo", "Quay lại"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    Intent intent = new Intent(InsertHH.this, Banhang_Showkh.class);
                    InsertHH.this.startActivity(intent);
                    finish();
                }
                else if(position==1){
                    Toast.makeText(InsertHH.this,"Bạn đang ở trong trang hàng hóa !",Toast.LENGTH_SHORT).show();
                }
                else if(position==2){
                    Intent intent = new Intent(InsertHH.this, ShowKH.class);
                    InsertHH.this.startActivity(intent);
                    finish();
                }
                else if (position==3){
                    Intent intent=new Intent(InsertHH.this,Index_Baocao.class);
                    InsertHH.this.startActivity(intent);
                    finish();
                }
                else {
                    Intent intent=new Intent(InsertHH.this,Index.class);
                    InsertHH.this.startActivity(intent);
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
                getSupportActionBar().setTitle("Thêm hàng");
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
    //
    public void onBackPressed(){
        Intent intent=new Intent(InsertHH.this,ShowIDHH.class);
        InsertHH.this.startActivity(intent);
        finish();
    }
    //
    private class Themhanghoa extends AsyncTask<String, Void, String> {

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
        protected void onPostExecute(String s1) {
            super.onPostExecute(s1);
            //final ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
            //final ArrayList<HashMap<String, String>> searchResult = new ArrayList<>();
            try {
                JSONObject json = new JSONObject(s1);
                JSONArray array = json.getJSONArray("ShowInsertResult");
                JSONObject v = array.getJSONObject(0);
                String kq=v.getString("ShowInsert");
                if(kq.equals("true")){
                    Toast.makeText(InsertHH.this,"Bạn đã thêm thành công",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(InsertHH.this,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                }


            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
