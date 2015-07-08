package com.banhang.bavo.banhang;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Ba Vo on 5/28/2015.
 */
public class ChitietHH extends ActionBarActivity {
    EditText mahanghoa,tenhh,sl,giaban,giavon;
    Button capnhat,xoa;
    TextView idhh;
    AutoCompleteTextView ten_loaihh_ct,dvt_ct;

    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    ////
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    LinkConnect l=new LinkConnect();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cthh);
        String idhanghoa=getIntent().getStringExtra("mahang");
        //Toast.makeText(ChitietHH.this,idhanghoa,Toast.LENGTH_SHORT).show();
        new ChiTietHangHoa().execute(l.getUrlCthh()+idhanghoa);
        //SET CAC TEXTFIELD
        idhh=(TextView)findViewById(R.id.id_hanghoa);
        mahanghoa=(EditText)findViewById(R.id.ma_hang);
        tenhh=(EditText)findViewById(R.id.ten_hang);

        String [] itemdonvitinh={"Kg","Hộp","Cái","Chai","Miếng","Gói","Cal","Cục"};
        dvt_ct=(AutoCompleteTextView)findViewById(R.id.dvt_ct);
        dvt_ct.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,itemdonvitinh));

        String [] itemtenloai={"CHONG THAM","CO SON","HANG SX LAI","HANG TTNT","KEO BONG","NPL-SX BOT","Sơn",
                "SON DAU MODENA,SON DAU NERO-PL2","SON NUOC B.DAU","SON NUOC DAVIT","SON NUOC MODENA(0.13)","SON NUOC MODENA(0.15)",
                "SON NUOC NANOSHINE","SON NUOC NAVY-SOLEX-ROTEX","SON NUOC NERO","SON NUOC NERO-PHUY","SON NUOC NERO-PL1","SON NUOC NERO-PL2",
                "T.MAU NERO","T.MAU-TOAN","TIEN V.CHUYEN CHO KH","Vật liệu xây dựng"};
        ten_loaihh_ct=(AutoCompleteTextView)findViewById(R.id.ten_loai_ct);
        ten_loaihh_ct.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,itemtenloai));

        //loaihang=(EditText)findViewById(R.id.loaihang);
        //dvt=(EditText)findViewById(R.id.dvt);
        sl=(EditText)findViewById(R.id.sl);
        giaban=(EditText)findViewById(R.id.giaban);
        giavon=(EditText)findViewById(R.id.giavon);
        //set Navagation
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_ct);
        // dList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_ct);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ///
        //SET CAC BUTTON
        capnhat=(Button)findViewById(R.id.capnhat);
        xoa=(Button)findViewById(R.id.xoa);
        capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ChitietHH.this,"Cap nhat",Toast.LENGTH_SHORT).show();
                String Id_hh=idhh.getText().toString();
                String mahang=mahanghoa.getText().toString();
                String Ten_hang=tenhh.getText().toString();
                /*
                String urlTenhang=null;
                try {
                    urlTenhang =URLEncoder.encode(Ten_hang,"utf-8");
                }catch (Exception e){

                }
                */
                String dvtinh=dvt_ct.getText().toString();
                String donvitinh=null;
                if(dvtinh.equals("Lon")){
                    donvitinh="13";
                }
                else if(dvtinh.equals("Hộp")){
                    donvitinh="3";
                }
                else if(dvtinh.equals("Cái")){
                    donvitinh="4";
                }
                else if(dvtinh.equals("Kg")){
                    donvitinh="2";
                }
                else {
                    donvitinh="5";
                }
                String soluong=sl.getText().toString();
                String giabanh=giaban.getText().toString();
                String giavonhang=giavon.getText().toString();
                if(Id_hh.equals("")){
                    Toast.makeText(ChitietHH.this,"Không có dữ liệu cập nhật",Toast.LENGTH_SHORT).show();
                }
                else {
                    String url = l.getUrlUpdateHH() + Id_hh +
                            "/" + "16" + "/" + donvitinh + "/"+mahang+"/"+ Uri.encode(Ten_hang)+ "/" + soluong+ "/" +giabanh+ "/" + giavonhang;
                    new CapnhatHangHoa().execute(url);
                }

            }
        });
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id_hh=idhh.getText().toString();
                if(Id_hh.equals("")){
                    Toast.makeText(ChitietHH.this,"Không có dữ liệu cập nhật",Toast.LENGTH_SHORT).show();
                }
                else{
                    String urldelete=l.getUrlDeleteHH()+Id_hh;
                    new XoaHangHoa().execute(urldelete);
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
                    Intent intent = new Intent(ChitietHH.this, Banhang_Showkh.class);
                    ChitietHH.this.startActivity(intent);
                    finish();
                }
                else if(position==1){
                    Toast.makeText(ChitietHH.this,"Bạn đang ở trong trang hàng hóa !",Toast.LENGTH_SHORT).show();
                }
                else if(position==2){
                    Intent intent = new Intent(ChitietHH.this, ShowKH.class);
                    ChitietHH.this.startActivity(intent);
                    finish();
                }
                else if (position==3){
                    Intent intent=new Intent(ChitietHH.this,Index_Baocao.class);
                    ChitietHH.this.startActivity(intent);
                    finish();
                }
                else {
                    Intent intent=new Intent(ChitietHH.this,Index.class);
                    ChitietHH.this.startActivity(intent);
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

    // Class Chitiet Hang hoa
    private class ChiTietHangHoa extends AsyncTask<String, Void, String> {

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
            String Id_hang=null;
            String Ten_hang=null;
            String mahang=null;
            String tenloai=null;
            String donvitinh=null;
            String soluong=null;
            String giabanh=null;
            String giavonhang=null;
            try {
                JSONObject json = new JSONObject(s);
                JSONArray array = json.getJSONArray("ShowChitietHHResult");
                JSONObject v = array.getJSONObject(0);
                Id_hang = v.getString("HH_ID");
                Ten_hang = v.getString("HH_TENHANG");
                donvitinh=v.getString("DVT_TEN");/////can xem xet lai gia tri
                tenloai=v.getString("LHH_TEN");
                mahang=v.getString("HH_MAHANGHOA");
                soluong=v.getString("HH_SL_CD");
                giabanh=v.getString("HH_DONGIABANLE");
                giavonhang=v.getString("HH_DONGIANHAP");


            }catch (Exception e){
                e.printStackTrace();
            }
            idhh.setText(Id_hang);
            tenhh.setText(Ten_hang);
            mahanghoa.setText(mahang);
            dvt_ct.setText(donvitinh);
            ten_loaihh_ct.setText(tenloai);
            sl.setText(soluong);
            giaban.setText(giabanh);
            giavon.setText(giavonhang);
        }
    }
    private class CapnhatHangHoa extends AsyncTask<String, Void, String> {

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
                JSONArray array = json.getJSONArray("ShowUpdateHHResult");
                JSONObject v = array.getJSONObject(0);
                String kq=v.getString("ShowUpdateHH");
                if(kq.equals("true")){
                    Toast.makeText(ChitietHH.this,"Ban đã câp nhật hàng hóa thành công",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ChitietHH.this,"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
                }


            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
    //
    @Override
    public void onBackPressed(){
        Intent intent=new Intent(ChitietHH.this,ShowIDHH.class);
        ChitietHH.this.startActivity(intent);
        finish();
    }
    //
    private class XoaHangHoa extends AsyncTask<String, Void, String> {

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
                JSONArray array = json.getJSONArray("ShowDeleteHHResult");
                JSONObject v = array.getJSONObject(0);
                String kq=v.getString("ShowDeleteHH");
                String kq1=v.getString("Excep");
                if(kq.equals("true")){
                    Toast.makeText(ChitietHH.this,"Bạn đã xóa hàng hóa thành công",Toast.LENGTH_SHORT).show();
                }
                else if(kq1.equals("du lieu dang duoc su dung")){
                    Toast.makeText(ChitietHH.this,"Xóa thất bại ! Hàng hóa này đang được sử dụng",Toast.LENGTH_SHORT).show();
                }


            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

}