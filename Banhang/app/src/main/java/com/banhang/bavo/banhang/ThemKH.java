package com.banhang.bavo.banhang;

/**
 * Created by Ba Vo on 6/4/2015.
 */

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
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

public class ThemKH extends ActionBarActivity {
    private ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;
    String mActivityTitle;
    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    //AutoCompleteTextView ten_loaihh_is,dvt_is;

    EditText ma_kh_is,ten_kh_is,loai_kh_is,diachi_is,sdt_is,thanhpho_is,quanhuyen_is;
    Button xacnhan,huybo;

    LinkConnect l=new LinkConnect();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themkh);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_iskh);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_iskh);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //set edittext
        ma_kh_is=(EditText)findViewById(R.id.ma_iskh);
        ten_kh_is=(EditText)findViewById(R.id.ten_iskh);
        loai_kh_is=(EditText)findViewById(R.id.loai_iskh);
        diachi_is=(EditText)findViewById(R.id.diachi_iskh);
        sdt_is=(EditText)findViewById(R.id.sdt_iskh);
        thanhpho_is=(EditText)findViewById(R.id.thanhpho_iskh);
        quanhuyen_is=(EditText)findViewById(R.id.quanhuyen_iskh);
        //set button
        xacnhan=(Button)findViewById(R.id.xacnhan_iskh);
        //set event
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String makhachhang=ma_kh_is.getText().toString();
                String tenkhachhang=ten_kh_is.getText().toString();
                String loaikh=loai_kh_is.getText().toString();
                String diachi=diachi_is.getText().toString();
                String sodienthoai=sdt_is.getText().toString();
                String thanhpho=thanhpho_is.getText().toString();
                String quanhuyen=quanhuyen_is.getText().toString();
                //su kien
                if(makhachhang.equals(null)||tenkhachhang.equals(null)||loaikh.equals(null)||diachi.equals(null)){
                    Toast.makeText(ThemKH.this, "Không có dữ liệu cập nhật", Toast.LENGTH_SHORT).show();
                }
                else {

                    String url =  l.getUrlInsertKH()+Uri.encode(loaikh)+
                            "/" + Uri.encode(makhachhang)+ "/"
                            + Uri.encode(tenkhachhang)
                            +"/"+Uri.encode(diachi)+"/"
                            +sodienthoai+"/" +Uri.encode(thanhpho)
                            +"/" +Uri.encode(quanhuyen);
                    new Themkhachhang().execute(url);
                }
            }
        });
        huybo=(Button)findViewById(R.id.huybo_iskh);
        huybo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ma_kh_is.setText("");
                ten_kh_is.setText("");
                loai_kh_is.setText("");
                diachi_is.setText("");
                sdt_is.setText("");
                thanhpho_is.setText("");
                quanhuyen_is.setText("");

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
                    Intent intent = new Intent(ThemKH.this, Banhang_Showkh.class);
                    ThemKH.this.startActivity(intent);
                    finish();
                }
                else if(position==1){
                    Intent intent = new Intent(ThemKH.this, ShowIDHH.class);
                    ThemKH.this.startActivity(intent);
                    finish();
                }
                else if(position==2){
                    Toast.makeText(ThemKH.this,"Bạn đang ở trong trang khách hàng !",Toast.LENGTH_SHORT).show();
                }
                else if (position==3){
                    Intent intent=new Intent(ThemKH.this,Index_Baocao.class);
                    ThemKH.this.startActivity(intent);
                    finish();
                }
                else {
                    Intent intent=new Intent(ThemKH.this,Index.class);
                    ThemKH.this.startActivity(intent);
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
    ///
    public void onBackPressed(){
        Intent intent=new Intent(ThemKH.this,ShowKH.class);
        ThemKH.this.startActivity(intent);
        finish();
    }
    ///
    private class Themkhachhang extends AsyncTask<String, Void, String> {

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
                JSONArray array = json.getJSONArray("Insert1KHResult");
                JSONObject v = array.getJSONObject(0);
                String kq=v.getString("Insert1KH");
                if(kq.equals("true")){
                    Toast.makeText(ThemKH.this,"Bạn đã thêm khách hàng thành công",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ThemKH.this,"Thêm khách hàng thất bại",Toast.LENGTH_SHORT).show();
                }


            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}

