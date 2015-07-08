package com.banhang.bavo.banhang;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


/**
 * Created by Ba Vo on 6/4/2015.
 */

public class Baocao_showdoanhthu extends ActionBarActivity {
    ListView listView;
    EditText tungay,toingay;
    ListAdapter adapter;
    ArrayList<HashMap<String, String>> mylist;
    Button xacnhan;
    //
    //DrawerLayout dLayout;
    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    ////
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    LinkConnect l = new LinkConnect();
    TaoDatabase db = new TaoDatabase(Baocao_showdoanhthu.this);

    Calendar cal;
    int month;
    int year;
    int day;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baocaodoanhthu);
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String layngay=dateFormat.format(calendar.getTime());
        //new BCDT().execute(l.getUrlBaocaoDT());
        //Edit Test
        listView = (ListView) findViewById(R.id.listchon_bcdt);
        xacnhan=(Button)findViewById(R.id.xachnhan_bcdt);
        tungay=(EditText)findViewById(R.id.tungay_bcdt);
        toingay=(EditText)findViewById(R.id.toingay_bcdt);
        cal=Calendar.getInstance();
        day=cal.get(Calendar.DAY_OF_MONTH);
        month=cal.get(Calendar.MONTH);
        year=cal.get(Calendar.YEAR);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_bcdt);
        // dList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_bcdt);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        tungay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });
        toingay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tungay1=tungay.getText().toString();
                String toingay1=toingay.getText().toString();
                new BCDT().execute(l.getUrlBaocaoDT()+tungay1+"/"+toingay1);
            }
        });
    }
    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        if(id==0) {
            return new DatePickerDialog(this, datePickerListener, year, month, day);
        }
        else {
            return new DatePickerDialog(this,datePickerListener1,year,month,day);
        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            tungay.setText(selectedYear + "-" + (selectedMonth + 1) + "-"
                    + selectedDay);
        }
    };
    private DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            toingay.setText(selectedYear + "-" + (selectedMonth + 1) + "-"
                    + selectedDay);
        }
    };

    ///
    private void addDrawerItems() {
        String[] osArray = {"Bán hàng", "Hàng hóa", "Khách hàng", "Báo cáo", "Quay lại"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    Intent intent=new Intent(Baocao_showdoanhthu.this,Banhang_Showkh.class);
                    Baocao_showdoanhthu.this.startActivity(intent);
                    finish();
                }
                else if(position==1){
                    Intent intent = new Intent(Baocao_showdoanhthu.this, ShowIDHH.class);
                    Baocao_showdoanhthu.this.startActivity(intent);
                    finish();
                }
                else if(position==2){
                    Intent intent = new Intent(Baocao_showdoanhthu.this, ShowKH.class);
                    Baocao_showdoanhthu.this.startActivity(intent);
                    finish();
                }
                else if (position==3){
                    Toast.makeText(Baocao_showdoanhthu.this,"Đang ở báo cáo",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent=new Intent(Baocao_showdoanhthu.this,Index.class);
                    Baocao_showdoanhthu.this.startActivity(intent);
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
        Intent intent = new Intent(Baocao_showdoanhthu.this, Index_Baocao.class);
        Baocao_showdoanhthu.this.startActivity(intent);
        finish();
    }

    ////////////////////////////////////////////////////////

    private class BCDT extends AsyncTask<String, Void, String> {

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
                JSONArray array = object.getJSONArray("ShowDHDATEResult");
                int n = array.length();
                for (int i = 0; i < n; i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject v1 = array.getJSONObject(i);
                    map.put("ID", String.valueOf(i));
                    map.put("MSP", v1.getString("BH_MASOPHIEU"));
                    map.put("MA_KHACHHANG", v1.getString("KHNCC_MAKHACHHANG"));
                    map.put("TEN_KHACHHANG", v1.getString("KHNCC_TEN"));
                    map.put("NGAYBAN",v1.getString("BH_NGAYBAN"));
                    map.put("DIACHI",v1.getString("KHNCC_DIACHI"));
                    map.put("SDT",v1.getString("KHNCC_SODIENTHOAI"));
                    map.put("DOANHTHU",v1.getString("BH_SOTIENPHAITHU"));
                    map.put("NGUOITHU",v1.getString("BH_NGUOIBAN"));
                    mylist.add(map);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            adapter=new SimpleAdapter(Baocao_showdoanhthu.this, mylist, R.layout.showbcdt_main, new String[]{"MSP","MA_KHACHHANG", "TEN_KHACHHANG","NGAYBAN","DIACHI","SDT","DOANHTHU","NGUOITHU"}, new int[]{R.id.textViewbcdt, R.id.textView1bcdt,R.id.textView2bcdt,R.id.textView3bcdt,R.id.textView4bcdt,R.id.textView5bcdt,R.id.textView6bcdt,R.id.textView7bcdt}) ;
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
