package com.banhang.bavo.banhang;

/**
 * Created by Ba Vo on 6/3/2015.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
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
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.prefs.BackingStoreException;


/**
 * Created by Ba Vo on 5/21/2015.
 */
public class Banhang_ctbh extends ActionBarActivity {
    ListView listView,listView1;
    AutoCompleteTextView search;
    ListAdapter adapter,adapter1;
    Button xacnhan,xoa,dongbo;
    ArrayList<HashMap<String,String>> mylist;
    ArrayList<HashMap<String, String>> arraytemple;
    ArrayList<HashMap<String,String>> arraydsbh;
    HashMap<String,String> map;
    ArrayAdapter<String> ab;
    TextView tenkh,thanhtien_ctbh;
    EditText sluongmua,msp;
    //
    //DrawerLayout dLayout;
    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    ////
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    String tongkq;
    double sumkq=0;
    int count=0;

    LinkConnect l;
    TaoDatabase db=new TaoDatabase(Banhang_ctbh.this);

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banhang_ctbh);
        LinkConnect l=new LinkConnect();
        String tenkh_ctbh=getIntent().getStringExtra("tenkhachhang");
        //Edit Test
        //NAVAGATION
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_ctbh);
        mDrawerList=(ListView)findViewById(R.id.left_drawer_ctbh);
        mActivityTitle = getTitle().toString();
        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //LISTVIEW
        listView=(ListView)findViewById(R.id.listchon_ctbh);
        listView1=(ListView)findViewById(R.id.listdaban_ctbh);
        //TEXTVIEW TEN KHACH HANG
        tenkh=(TextView)findViewById(R.id.tenkh_ctbh);
        tenkh.setText(tenkh_ctbh);
        thanhtien_ctbh=(TextView)findViewById(R.id.thanhtien_ctbh);
        //AUTOTEXXTVIEW and EDITEXT
        search=(AutoCompleteTextView)findViewById(R.id.autotexthh_ctbh);
        sluongmua=(EditText)findViewById(R.id.soluonghang_ctbh);
        msp=(EditText)findViewById(R.id.masophieu_ctbh);
        //BUTTON XAC NHAN CHON
        xacnhan=(Button)findViewById(R.id.xacnhan_ctbh);
        xoa=(Button)findViewById(R.id.huybo_ctbh);
        dongbo=(Button)findViewById(R.id.dongbo_ctbh);

        if(checkInternetConnection()==false){
            mylist=new ArrayList<HashMap<String, String>>();
            arraytemple = new ArrayList<HashMap<String, String>>();
            mylist=db.getidhh();
            msp.setText(db.getMaxMSP());
            List<String> t=new ArrayList<String>();
            for(int i=0;i<mylist.size();i++){
                HashMap<String ,String > tt=mylist.get(i);
                t.add(tt.get("ID_HANGHOA"));
            }
            ArrayAdapter ab=new ArrayAdapter(Banhang_ctbh.this, android.R.layout.simple_list_item_1,t);
            search.setAdapter(ab);
            xacnhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String search1 = search.getText().toString();
                    String sl = sluongmua.getText().toString();
                    Boolean kiemtra = ktdunghang(search1);
                    if (search1.equals("") || sl.equals("")) {
                        Toast.makeText(Banhang_ctbh.this, "Bạn chưa nhập hàng hóa hoặc số lượng!", Toast.LENGTH_SHORT).show();
                    } else {
                        //String sl=sluongmua.getText().toString();
                        int sl1 = Integer.parseInt(sl);
                        //String chon = search.getText().toString();
                        double thanhtien = 0;
                        //Toast.makeText(Banhang_ctbh.this, ""+sl1, Toast.LENGTH_SHORT).show();
                        String artical = null;
                        String artical1 = null;
                        String artical2 = null;
                        String artical3 = null;
                        String artical4 = null;
                        String artical5 = null;
                        String artical7 = null;
                        for (int i = 0; i < mylist.size(); i++) {
                            HashMap<String, String> atemp = mylist.get(i);
                            artical = atemp.get("ID_HANGHOA");
                            artical1 = atemp.get("TEN_HANGHOA");
                            artical2 = atemp.get("LHH_ID");
                            artical3 = atemp.get("MAHANGHOA");
                            artical4 = atemp.get("DONGIA");
                            artical5 = atemp.get("SL");
                            artical7 = atemp.get("DVT");
                            if (search1.equalsIgnoreCase(artical)) {
                                double b = Double.parseDouble(artical4);
                                thanhtien = sl1 * b;
                                atemp = new HashMap<String, String>();
                                atemp.put("ID_HANG", artical);
                                atemp.put("TEN_HANG", artical1);
                                atemp.put("LOAI_HH", artical2);
                                atemp.put("MA_HH", artical3);
                                atemp.put("DONGIA", artical4);
                                atemp.put("SL", artical5);
                                atemp.put("SL_MUA", sluongmua.getText().toString());
                                atemp.put("DONVITINH", artical7);
                                atemp.put("THANHTIEN", "" + thanhtien);
                                arraytemple.add(atemp);
                            }
                            adapter = new SimpleAdapter(Banhang_ctbh.this, arraytemple, R.layout.banhang_ctbh_main, new String[]{"ID_HANG", "MA_HH", "TEN_HANG", "DONGIA", "SL", "SL_MUA", "LOAI_HH", "DONVITINH", "THANHTIEN"}, new int[]{R.id.textViewhh_ctbh, R.id.textView1hh_ctbh, R.id.textView2hh_ctbh, R.id.textView3hh_ctbh, R.id.textView4hh_ctbh, R.id.textView5hh_ctbh, R.id.textView6hh_ctbh, R.id.textView7hh_ctbh, R.id.textView8hh_ctbh});
                            listView.setAdapter(adapter);
                        }
                        sumkq = sumkq + thanhtien;
                        tongkq = "" + sumkq;
                        thanhtien_ctbh.setText(tongkq);
                    }
                }
            });
            xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    search.setText("");
                    sluongmua.setText("");
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AlertDialog.Builder abc = new AlertDialog.Builder(Banhang_ctbh.this);
                    abc.setTitle("Tùy chon ?");
                    abc.setTitle("Lựa chọn của bạn ?");
                    final int positiontc = position;
                    abc.setNegativeButton("Cancel", null);
                    abc.setPositiveButton("Delete", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            HashMap<String, String> temp = arraytemple.get(positiontc);
                            String a = temp.get("THANHTIEN");
                            double b = Double.parseDouble(a);
                            String c = thanhtien_ctbh.getText().toString();
                            double d = Double.parseDouble(c);
                            sumkq = d - b;
                            tongkq = "" + sumkq;
                            thanhtien_ctbh.setText(tongkq);
                            arraytemple.remove(positiontc);
                            listView.setAdapter(adapter);
                        }
                    });
                    abc.show();
                    //String tam=atemp.get("ID_HANG");
                    //Toast.makeText(Banhang_ctbh.this,tam,Toast.LENGTH_SHORT).show();
                }
            });
            dongbo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (arraytemple.size() == 0) {
                        Toast.makeText(Banhang_ctbh.this, "Chưa có sản phẩm bán, Hãy nhập sản phẩm !", Toast.LENGTH_SHORT).show();
                    } else {
                        String makh_ctbh=getIntent().getStringExtra("makhachhang");
                        int id_kh=Integer.parseInt(makh_ctbh);
                        String masophieu=msp.getText().toString();
                        Calendar calendar=Calendar.getInstance();
                        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                        String layngay=dateFormat.format(calendar.getTime());
                        String thanhtien=thanhtien_ctbh.getText().toString();
                        double thanhtientinh=Double.parseDouble(thanhtien);
                        db.addBH(new TableBanhang(id_kh,1,masophieu,layngay,thanhtientinh,thanhtientinh,"Chi Thao","admin",layngay,layngay));
                        for (int i = 0; i < arraytemple.size(); i++) {
                            HashMap<String, String> atemp = arraytemple.get(i);
                            String artical = atemp.get("ID_HANG");
                            int id_hang=Integer.parseInt(artical);
                            String artical1 = atemp.get("SL_MUA");
                            int slmua=Integer.parseInt(artical1);
                            String artical2 = atemp.get("DONGIA");
                            double dongia=Double.parseDouble(artical2);
                            String artical3 = atemp.get("DONVITINH");
                            int dvt=Integer.parseInt(artical3);
                            db.addBH_CTBH_Offline(new Table_chitietbanhang(id_hang,slmua,dongia,dongia,dvt,layngay,layngay));
                        }
                        Toast.makeText(Banhang_ctbh.this,"Chưa có Internet đơn hàng của bạn đã được lưu lại",Toast.LENGTH_SHORT).show();
                    }
                    arraytemple.clear();
                    listView.setAdapter(adapter);
                    String makh_ctbh = getIntent().getStringExtra("makhachhang");
                }
            });

        }
        else {
            new HangHoa().execute(l.getUrlIdhh());
        }
    }
    /////////////////////////////////////////////
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



    ////////////////////////////////////////////
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
                    Intent intent = new Intent(Banhang_ctbh.this, ShowIDHH.class);
                    Banhang_ctbh.this.startActivity(intent);
                    finish();
                }
                else if(position==2){
                    Intent intent = new Intent(Banhang_ctbh.this, ShowKH.class);
                    Banhang_ctbh.this.startActivity(intent);
                    finish();
                }
                else if (position==3){
                    Intent intent=new Intent(Banhang_ctbh.this,Index_Baocao.class);
                    Banhang_ctbh.this.startActivity(intent);
                    finish();
                }
                else {
                    Intent intent=new Intent(Banhang_ctbh.this,Index.class);
                    Banhang_ctbh.this.startActivity(intent);
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
        Intent intent=new Intent(Banhang_ctbh.this,Banhang_Showkh.class);
        Banhang_ctbh.this.startActivity(intent);
        Banhang_ctbh.this.finish();
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
            List<String> strings=new ArrayList<String>();
            arraytemple = new ArrayList<HashMap<String, String>>();
            //final ArrayList<HashMap<String,String>> searchResult=new ArrayList<>();
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("ShowAllResult");
                int n = array.length();
                for (int i = 0; i < n; i++) {
                    map=new HashMap<>();
                    JSONObject v = array.getJSONObject(i);
                    map.put("ID_HANG", v.getString("HH_ID"));
                    map.put("TEN_HANG", v.getString("HH_TENHANG"));
                    map.put("LOAI_HH", v.getString("HH_LOAIHANGHOAID"));
                    map.put("MA_HH", v.getString("HH_MAHANGHOA"));
                    map.put("DONGIA", v.getString("HH_DONGIABANLE"));
                    map.put("SL", v.getString("HH_SL_CD"));
                    map.put("DONVITINH",v.getString("HH_DONVITINHID"));
                    map.put("MASOPHIEU_BH",v.getString("MASOPHIEU_BH"));
                    mylist.add(map);
                    strings.add(map.get("ID_HANG"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(Banhang_ctbh.this,"Bạn phải đăng nhập để thấy được hàng hóa !",Toast.LENGTH_SHORT).show();
            }
            //listView =(ListView)findViewById(R.id.listViewhh1_ctbh);
            //adapter = new SimpleAdapter(Banhang_ctbh.this, mylist, R.layout.banhang_ctbh_main, new String[]{"ID_HANG", "MA_HH","TEN_HANG","DONGIA","SL","LOAI_HH"}, new int[]{R.id.textViewhh_ctbh, R.id.textView1hh_ctbh, R.id.textView2hh_ctbh,R.id.textView3hh_ctbh,R.id.textView4hh_ctbh,R.id.textView5hh_ctbh});
            //listView.setAdapter(adapter);
            final ArrayAdapter ab=new ArrayAdapter(Banhang_ctbh.this, android.R.layout.simple_list_item_1,strings);
            search.setAdapter(ab);
            xacnhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String search1 = search.getText().toString();
                    String sl = sluongmua.getText().toString();
                    Boolean kiemtra = ktdunghang(search1);
                    if (search1.equals("") || sl.equals("")) {
                        Toast.makeText(Banhang_ctbh.this, "Bạn chưa nhập hàng hóa hoặc số lượng!", Toast.LENGTH_SHORT).show();
                    } else {
                        //String sl=sluongmua.getText().toString();
                        int sl1 = Integer.parseInt(sl);
                        //String chon = search.getText().toString();
                        double thanhtien = 0;
                        //Toast.makeText(Banhang_ctbh.this, ""+sl1, Toast.LENGTH_SHORT).show();
                        String artical = null;
                        String artical1 = null;
                        String artical2 = null;
                        String artical3 = null;
                        String artical4 = null;
                        String artical5 = null;
                        String artical6 = null;
                        String artical7=null;
                        for (int i = 0; i < mylist.size(); i++) {

                            HashMap<String, String> atemp = mylist.get(i);
                            artical = atemp.get("ID_HANG");
                            artical1 = atemp.get("TEN_HANG");
                            artical2 = atemp.get("LOAI_HH");
                            artical3 = atemp.get("MA_HH");
                            artical4 = atemp.get("DONGIA");
                            artical5 = atemp.get("SL");
                            artical7 = atemp.get("DONVITINH");
                            if (search1.equalsIgnoreCase(artical) && kiemtra == false) {

                                double b = Double.parseDouble(artical4);
                                thanhtien = sl1 * b;
                                atemp = new HashMap<String, String>();
                                atemp.put("ID_HANG", artical);
                                atemp.put("TEN_HANG", artical1);
                                atemp.put("LOAI_HH", artical2);
                                atemp.put("MA_HH", artical3);
                                atemp.put("DONGIA", artical4);
                                atemp.put("SL", artical5);
                                atemp.put("SL_MUA",sluongmua.getText().toString());
                                atemp.put("DONVITINH", artical7);
                                atemp.put("THANHTIEN", "" + thanhtien);
                                arraytemple.add(atemp);
                            }
                            adapter = new SimpleAdapter(Banhang_ctbh.this, arraytemple, R.layout.banhang_ctbh_main, new String[]{"ID_HANG", "MA_HH", "TEN_HANG", "DONGIA", "SL", "SL_MUA", "LOAI_HH", "DONVITINH", "THANHTIEN"}, new int[]{R.id.textViewhh_ctbh, R.id.textView1hh_ctbh, R.id.textView2hh_ctbh, R.id.textView3hh_ctbh, R.id.textView4hh_ctbh, R.id.textView5hh_ctbh, R.id.textView6hh_ctbh, R.id.textView7hh_ctbh,R.id.textView8hh_ctbh});
                            listView.setAdapter(adapter);
                        }
                        sumkq = sumkq + thanhtien;
                        tongkq = "" + sumkq;
                        thanhtien_ctbh.setText(tongkq);
                    }
                }
            });
            xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    search.setText("");
                    sluongmua.setText("");
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AlertDialog.Builder abc = new AlertDialog.Builder(Banhang_ctbh.this);
                    abc.setTitle("Tùy chon ?");
                    abc.setTitle("Lựa chọn của bạn ?");
                    final int positiontc = position;
                    abc.setNegativeButton("Cancel", null);
                    abc.setPositiveButton("Delete", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            HashMap<String, String> temp = arraytemple.get(positiontc);
                            String a = temp.get("THANHTIEN");
                            double b = Double.parseDouble(a);
                            String c = thanhtien_ctbh.getText().toString();
                            double d = Double.parseDouble(c);
                            sumkq = d - b;
                            tongkq = "" + sumkq;
                            thanhtien_ctbh.setText(tongkq);
                            arraytemple.remove(positiontc);
                            listView.setAdapter(adapter);
                        }
                    });
                    abc.show();
                    //String tam=atemp.get("ID_HANG");
                    //Toast.makeText(Banhang_ctbh.this,tam,Toast.LENGTH_SHORT).show();
                }
            });
            dongbo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (arraytemple.size() == 0) {
                        Toast.makeText(Banhang_ctbh.this, "Chưa có sản phẩm bán, Hãy nhập sản phẩm !", Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < arraytemple.size(); i++) {
                            HashMap<String, String> tem = arraytemple.get(i);
                            String a = H2S(tem);
                            l = new LinkConnect();
                            String url = l.getUrlDongboBH() + a+ msp.getText().toString();
                            new DongboBH().execute(url);
                        }
                    }
                    listView.setAdapter(adapter);
                    l = new LinkConnect();
                    String makh_ctbh=getIntent().getStringExtra("makhachhang");
                    new DSBH().execute(l.getUrlShowDSBH() + makh_ctbh);
                }
            });
            msp.setText(laysophieu());
            l=new LinkConnect();
            String makh_ctbh=getIntent().getStringExtra("makhachhang");
            new DSBH().execute(l.getUrlShowDSBH()+makh_ctbh);
            ///////////////////////////////////////////////////
            //adapter = new SimpleAdapter(Banhang_ctbh.this, arraytemple, R.layout.banhang_ctbh_main, new String[]{"ID_HANG", "MA_HH","TEN_HANG","DONGIA","SL","LOAI_HH"}, new int[]{R.id.textViewhh_ctbh, R.id.textView1hh_ctbh, R.id.textView2hh_ctbh,R.id.textView3hh_ctbh,R.id.textView4hh_ctbh,R.id.textView5hh_ctbh});
            //listView.setAdapter(adapter);
            //but ton chon

            /*listView.setAdapter(adapter);
            //listView.setTextFilterEnabled(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HashMap<String,String> article=mylist.get(position);
                    String mahang = article.get("ID_HANG");
                    Intent intent = new Intent(Banhang_ctbh.this, ChitietHH.class);
                    intent.putExtra("mahang", mahang);
                    Banhang_ctbh.this.startActivity`z(intent);
                    finish();
                }
            });*/

        }
        public float thanhtien(float thanhtien){
            for(int i=0;i<arraytemple.size();i++){
                HashMap<String,String> tem=arraytemple.get(i);
                String a=tem.get("THANHTIEN");
                float b=Float.parseFloat(a);
                thanhtien=thanhtien+b;
            }
            return thanhtien;
        }
    }
    private class DongboBH extends AsyncTask<String, Void, String> {

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
                JSONArray array = json.getJSONArray("LapBHResult");
                JSONObject v = array.getJSONObject(0);
                String kq=v.getString("LapBH");
                String Dunghang=v.getString("Dunghang");
                String mspmoi=v.getString("MSPMAX");
                if(kq.equals("true")){
                    Toast.makeText(Banhang_ctbh.this,"Bạn đã bán hàng thành công !",Toast.LENGTH_SHORT).show();
                    arraytemple.clear();
                    listView.setAdapter(adapter);
                }
                else if(Dunghang.equals("DUNGHANG")){
                    Toast.makeText(Banhang_ctbh.this,"Mã số phiếu đã thay đổi vì bị trùng mời bạn kiểm tra phía dưới!",Toast.LENGTH_SHORT).show();
                    msp.setText(mspmoi);
                    for (int i = 0; i < arraytemple.size(); i++) {
                        HashMap<String, String> tem = arraytemple.get(i);
                        String a = H2S(tem);
                        l = new LinkConnect();
                        String url = l.getUrlDongboBH() + a + mspmoi;
                        new DongboBH_MSP().execute(url);
                    }
                    arraytemple.clear();
                    listView.setAdapter(adapter);
                    l = new LinkConnect();
                    String makh_ctbh=getIntent().getStringExtra("makhachhang");
                    new DSBH().execute(l.getUrlShowDSBH() + makh_ctbh);
                }


            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
    private class DongboBH_MSP extends AsyncTask<String, Void, String> {

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
                JSONArray array = json.getJSONArray("LapBHResult");
                JSONObject v = array.getJSONObject(0);
                String kq=v.getString("LapBH");
                String Dunghang=v.getString("Dunghang");
                if(kq.equals("true")){
                }
                else if(Dunghang.equals("DUNGHANG")){
                    Toast.makeText(Banhang_ctbh.this,"Mã số phiếu đã thay đổi vì bị trùng mời ban bán hàng lại!",Toast.LENGTH_SHORT).show();
                }


            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private class DSBH extends AsyncTask<String, Void, String> {

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
            arraydsbh=new ArrayList<HashMap<String, String>>();
            //final ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
            //final ArrayList<HashMap<String, String>> searchResult = new ArrayList<>();
            try {
                JSONObject json = new JSONObject(s1);
                JSONArray array = json.getJSONArray("ShowDSBANHANGResult");
                for(int i=0;i<array.length();i++) {
                    HashMap<String,String> dsbh=new HashMap<String,String>();
                    JSONObject v = array.getJSONObject(i);
                    dsbh.put("BH_ID",v.getString("BH_ID"));
                    dsbh.put("BH_KHACHHANG",v.getString("BH_KHACHHANG"));
                    dsbh.put("BH_MSP",v.getString("BH_MSP"));
                    dsbh.put("BH_NGAYBAN",v.getString("BH_NGAYBAN"));
                    dsbh.put("BH_NGUOIBAN",v.getString("BH_NGUOIBAN"));
                    dsbh.put("BH_SOTIENPHAITHU",v.getString("BH_SOTIENPHAITHU"));
                    dsbh.put("BH_SOTIENDATHU",v.getString("BH_SOTIENDATHU"));
                    dsbh.put("BH_TUKHO",v.getString("BH_TUKHOID"));
                    arraydsbh.add(dsbh);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            adapter1 = new SimpleAdapter(Banhang_ctbh.this, arraydsbh, R.layout.banhang_ctbh_dsbh, new String[]{"BH_ID", "BH_KHACHHANG","BH_MSP","BH_NGAYBAN","BH_NGUOIBAN","BH_SOTIENPHAITHU","BH_SOTIENDATHU","BH_TUKHO"}, new int[]{R.id.textViewhh_dsbh, R.id.textView1hh_dsbh, R.id.textView2hh_dsbh,R.id.textView3hh_dsbh,R.id.textView4hh_dsbh,R.id.textView5hh_dsbh,R.id.textView6hh_dsbh,R.id.textView7hh_dsbh});
            listView1.setAdapter(adapter1);
            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AlertDialog.Builder abc = new AlertDialog.Builder(Banhang_ctbh.this);
                    abc.setTitle("Tùy chon ?");
                    abc.setTitle("Lựa chọn của bạn ?");
                    final int positiontc = position;
                    abc.setNegativeButton("Thoát",null);
                    abc.setNeutralButton("Chi tiết", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i=new Intent(Banhang_ctbh.this,Banhang_ct_dskh.class);
                            HashMap<String, String> dsbh = arraydsbh.get(positiontc);
                            String a = dsbh.get("BH_ID");
                            String b=dsbh.get("BH_SOTIENPHAITHU");
                            String c=dsbh.get("BH_MSP");
                            i.putExtra("bh_id",a);
                            i.putExtra("msp",c);
                            i.putExtra("tongtien",b);
                            Banhang_ctbh.this.startActivity(i);
                        }
                    });
                    abc.setPositiveButton("Xóa", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            HashMap<String, String> delete = arraydsbh.get(positiontc);
                            String a = delete.get("BH_ID");
                            l = new LinkConnect();
                            new DeleteDSBH().execute(l.getUrlDeleteDSBH() + a);
                            arraydsbh.remove(positiontc);
                            listView1.setAdapter(adapter1);
                        }
                    });
                    abc.show();
                }
            });
        }
    }
    private class DeleteDSBH extends AsyncTask<String, Void, String> {

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
                JSONArray array = json.getJSONArray("DeleteBANHANGResult");
                JSONObject v = array.getJSONObject(0);
                String kq=v.getString("Delete");
                if(kq.equals("true")){
                    Toast.makeText(Banhang_ctbh.this,"Bạn đã xóa đơn hàng thành công!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Banhang_ctbh.this,"Bạn đã xóa đơn hàng thất bại , kiểm tra lại !",Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public Boolean ktdunghang(String mahang){
        for(int i=0;i<arraytemple.size();i++){
            HashMap<String,String> tr=arraytemple.get(i);
            String mahanghoa=tr.get("ID_HANG");
            if(mahang==mahanghoa){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    public String H2S(HashMap<String,String> hs){
        String kq=null;
        String makh_ctbh=getIntent().getStringExtra("makhachhang");
        //Calendar calendar = new GregorianCalendar(Locale.getDefault());
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now=df.format(new Date());
        Date date=new Date();
        String tongtien=thanhtien_ctbh.getText().toString();
        kq =  makh_ctbh+
                "/"
                +"1"
                +"/"
                +tongtien
                +"/"
                +tongtien
                +"/"
                +"admin"
                +"/"
                +hs.get("ID_HANG")
                + "/"
                +hs.get("SL_MUA")
                +"/"
                +hs.get("DONVITINH")
                +"/"
                +hs.get("DONGIA")
                +"/"
                +hs.get("DONGIA")
                +"/";
        return kq;
    }
    public String laysophieu(){
        HashMap<String,String> sophieu= mylist.get(0);
        String kq=sophieu.get("MASOPHIEU_BH");
        return kq;
    }
}


