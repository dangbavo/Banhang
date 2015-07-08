package com.banhang.bavo.banhang;

/**
 * Created by Ba Vo on 6/4/2015.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Show_ctkh extends ActionBarActivity {
    EditText ma_kh,ten_kh,loai_kh,diachi,sdt,quanhuyen,thanhpho;
    Button capnhat,xoa;
    TextView id_makh;

    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    ////
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    LinkConnect l=new LinkConnect();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ctkh);
        String idkhachhang=getIntent().getStringExtra("idkhachhang");
        //Toast.makeText(ChitietHH.this,idhanghoa,Toast.LENGTH_SHORT).show();
        new ChiTietKhachhang().execute(l.getUrlCtkh() + idkhachhang);
        //SET CAC TEXTFIELD
        id_makh=(TextView)findViewById(R.id.id_ctkh);
        ma_kh=(EditText)findViewById(R.id.ma_khachhang);
        ten_kh=(EditText)findViewById(R.id.ten_khachhang);
        loai_kh=(EditText)findViewById(R.id.loaikhachhang);
        diachi=(EditText)findViewById(R.id.diachi);
        sdt=(EditText)findViewById(R.id.sdt);
        thanhpho=(EditText)findViewById(R.id.thanhpho);
        quanhuyen=(EditText)findViewById(R.id.quanhuyen);
        //set Navagation
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_ctkh);
        // dList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_ctkh);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ///
        //SET CAC BUTTON
        capnhat=(Button)findViewById(R.id.capnhat_ctkh);
        xoa=(Button)findViewById(R.id.xoa_ctkh);
        capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ChitietHH.this,"Cap nhat",Toast.LENGTH_SHORT).show();
                String Id_kh=id_makh.getText().toString();
                String loai_khachhang=loai_kh.getText().toString();
                String makhachhang=ma_kh.getText().toString();
                String Ten_khachhang=ten_kh.getText().toString();
                /*
                String urlTenhang=null;
                try {
                    urlTenhang =URLEncoder.encode(Ten_hang,"utf-8");
                }catch (Exception e){

                }
                */
                String diachikh=diachi.getText().toString();
                String sdt_kh=sdt.getText().toString();
                String thanh_pho=thanhpho.getText().toString();
                String quan_huyen=quanhuyen.getText().toString();
                if(Id_kh.equals("")){
                    Toast.makeText(Show_ctkh.this, "Không có dữ liệu cập nhật", Toast.LENGTH_SHORT).show();
                }
                else {
                    String url = l.getUrlUpdateKH() + Id_kh +
                            "/" + Uri.encode(loai_khachhang) + "/" + Uri.encode(makhachhang) + "/"+Uri.encode(Ten_khachhang)+"/"+ Uri.encode(diachikh)+ "/" + Uri.encode(sdt_kh)+ "/" + Uri.encode(thanh_pho + "/" +Uri.encode(quan_huyen));
                    new CapnhatKhachhang().execute(url);
                }

            }
        });
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id_kh=id_makh.getText().toString();
                if(Id_kh.equals("")){
                    Toast.makeText(Show_ctkh.this,"Không có dữ liệu xóa",Toast.LENGTH_SHORT).show();
                }
                else{
                    String urldelete=l.getUrlDeleteKH()+Id_kh;
                    new Xoakhachhang().execute(urldelete);
                }
            }
        });

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
                    Intent intent = new Intent(Show_ctkh.this, Banhang_Showkh.class);
                    Show_ctkh.this.startActivity(intent);
                    finish();
                }
                else if(position==1){
                    Intent intent = new Intent(Show_ctkh.this, ShowIDHH.class);
                    Show_ctkh.this.startActivity(intent);
                    finish();
                }
                else if(position==2){
                    Toast.makeText(Show_ctkh.this,"Bạn đang ở trong trang khách hàng !",Toast.LENGTH_SHORT).show();
                }
                else if (position==3){
                    Intent intent=new Intent(Show_ctkh.this,Index_Baocao.class);
                    Show_ctkh.this.startActivity(intent);
                    finish();
                }
                else {
                    Intent intent=new Intent(Show_ctkh.this,Index.class);
                    Show_ctkh.this.startActivity(intent);
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
    /////////////het Navagation
    @Override
    public void onBackPressed(){
        Intent intent=new Intent(Show_ctkh.this,ShowKH.class);
        Show_ctkh.this.startActivity(intent);
        finish();
    }
    // Class Chitiet Hang hoa
    private class ChiTietKhachhang extends AsyncTask<String, Void, String> {

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
            //final ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
            //final ArrayList<HashMap<String, String>> searchResult = new ArrayList<>();
            String Id_khachhang=null;
            String ma_khachhang=null;
            String ten_khachhang=null;
            String loai_khachhang=null;
            String diachi_khachhang=null;
            String sodienthoai=null;
            String thanhpho_kh=null;
            String quanhuyen_kh=null;

            try {
                JSONObject json = new JSONObject(s);
                JSONArray array = json.getJSONArray("ShowChitietKHResult");
                JSONObject v = array.getJSONObject(0);
                Id_khachhang = v.getString("KHNCC_ID");
                ma_khachhang = v.getString("KHNCC_MAKHACHHANG");
                ten_khachhang=v.getString("KHNCC_TEN");/////can xem xet lai gia tri
                loai_khachhang=v.getString("KHNCC_LOAIKHACHHANG");
                diachi_khachhang=v.getString("KHNCC_DIACHI");
                sodienthoai=v.getString("KHNCC_SODIENTHOAI");
                quanhuyen_kh=v.getString("KHNCC_QUANHUYENTEN");
                thanhpho_kh=v.getString("TP_TEN");


            }catch (Exception e){
                e.printStackTrace();
            }
            id_makh.setText(Id_khachhang);
            ma_kh.setText(ma_khachhang);
            ten_kh.setText(ten_khachhang);
            loai_kh.setText(loai_khachhang);
            diachi.setText(diachi_khachhang);
            sdt.setText(sodienthoai);
            thanhpho.setText(thanhpho_kh);
            quanhuyen.setText(quanhuyen_kh);
        }
    }
    private class CapnhatKhachhang extends AsyncTask<String, Void, String> {

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
                JSONArray array = json.getJSONArray("Update1KHResult");
                JSONObject v = array.getJSONObject(0);
                String kq=v.getString("Update1KH");
                if(kq.equals("true")){
                    Toast.makeText(Show_ctkh.this,"Bạn đã câp nhật khách hàng thành công",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Show_ctkh.this,"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
                }


            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
    private class Xoakhachhang extends AsyncTask<String, Void, String> {

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
                JSONArray array = json.getJSONArray("Delete1KHResult");
                JSONObject v = array.getJSONObject(0);
                String kq=v.getString("Delete1KH");
                String kq1=v.getString("Excep");
                if(kq.equals("true")){
                    Toast.makeText(Show_ctkh.this,"Bạn đã xóa khách hàng thành công",Toast.LENGTH_SHORT).show();
                }
                else if(kq1.equals("du lieu dang duoc su dung")){
                    Toast.makeText(Show_ctkh.this,"Xóa thất bại ! Khách Hàng này đang được sử dụng",Toast.LENGTH_SHORT).show();
                }


            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

}
