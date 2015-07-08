package com.banhang.bavo.banhang;

/**
 * Created by Ba Vo on 6/21/2015.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
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
import java.util.List;
import java.util.Scanner;




/**
 * Created by Ba Vo on 6/4/2015.
 */

public class Banhang_ct_dskh extends ActionBarActivity {
    ListView listView;
    Button update,cancel;
    EditText search, sl_ct_dsbh;
    AutoCompleteTextView mahang_ct_dsbh;
    TextView donhang, tongtien, banhang_idct_dsbh, tenhang_ct_dsbh, dgb_ct, dvt_ct_dsbh;
    ListAdapter adapter, adapter1;
    ArrayList<HashMap<String, String>> mylist;
    ArrayList<HashMap<String, String>> arraytemple;
    //DrawerLayout dLayout;
    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    ////
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    LinkConnect l = new LinkConnect();

    String sumkq;
    double tongthanhtien;
    double thanhtiendv;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banhang_chitiet_dsbh);
        String id_bh = getIntent().getStringExtra("bh_id");
        String dh = getIntent().getStringExtra("msp");
        new Showctdh().execute(l.getUrlCtdh() + id_bh);
        //Edit Test
        listView = (ListView) findViewById(R.id.listchon_ctbh_dsbh);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_ctbh_dsbh);
        // dList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_ctbh_dsbh);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        update=(Button)findViewById(R.id.update_ct_dsbh);
        cancel=(Button)findViewById(R.id.cancel_ct_dsbh);
        ///
        donhang = (TextView) findViewById(R.id.tendh_ctbh_dsbh);
        donhang.setText(dh);
        tongtien = (TextView) findViewById(R.id.thanhtien_ctbh_dsbh);
        banhang_idct_dsbh = (TextView) findViewById(R.id.banhang_idct_dsbh);
        tenhang_ct_dsbh = (TextView) findViewById(R.id.tenhang_ct_dsbh);
        dgb_ct = (TextView) findViewById(R.id.dgb_ct);
        dvt_ct_dsbh = (TextView) findViewById(R.id.dvt_ct_dsbh);

        mahang_ct_dsbh = (AutoCompleteTextView) findViewById(R.id.mahang_ct_dsbh);
        sl_ct_dsbh = (EditText) findViewById(R.id.slban_ct_dsbh);
        search = (EditText) findViewById(R.id.Search_ctbh_dsbh);
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
                    String artical = current.get("CTBH_BHID");
                    String artical1 = current.get("CTBH_ID");
                    String artical2 = current.get("CTBH_HH_ID");
                    String artical3 = current.get("TEN_HANG");
                    String artical4 = current.get("DONGIA");
                    String artical5 = current.get("DVT");
                    String artical6 = current.get("NGAYBAN");
                    String artical7 = current.get("SL_MUA");
                    if (search.equalsIgnoreCase(artical2)) {
                        current.put("CTBH_BHID", artical);
                        current.put("CTBH_ID", artical1);
                        current.put("CTBH_HH_ID", artical2);
                        current.put("TEN_HANG", artical3);
                        current.put("DONGIA", artical4);
                        current.put("DVT", artical5);
                        current.put("NGAYBAN", artical6);
                        current.put("SL_MUA", artical7);
                        arraytemple.add(current);
                    }
                }
                adapter1 = new SimpleAdapter(Banhang_ct_dskh.this, arraytemple, R.layout.banhang_ct_dsbh_main, new String[]{"CTBH_BHID", "CTBH_ID", "CTBH_HH_ID", "TEN_HANG", "DONGIA", "DVT", "NGAYBAN", "SL_MUA"}, new int[]{R.id.textViewhh_ctbh_dsbh, R.id.textView1hh_ctbh_dsbh, R.id.textView2hh_ctbh_dsbh, R.id.textView3hh_ctbh_dsbh, R.id.textView4hh_ctbh_dsbh,
                        R.id.textView5hh_ctbh_dsbh, R.id.textView6hh_ctbh_dsbh, R.id.textView7hh_ctbh_dsbh});
                listView.setAdapter(adapter1);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String, String> article = arraytemple.get(position);
                        Intent intent = new Intent(Banhang_ct_dskh.this, Banhang_ctbh.class);
                        Banhang_ct_dskh.this.startActivity(intent);
                        Banhang_ct_dskh.this.finish();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ////

    }

    ///
    private void addDrawerItems() {
        String[] osArray = {"Bán hàng", "Hàng hóa", "Khách hàng", "Báo cáo", "Quay lại"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Banhang_ct_dskh.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
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
        Banhang_ct_dskh.this.finish();
    }

    ////////////////////////////////////////////////////////
    private class Showctdh extends AsyncTask<String, Void, String> {

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
            mylist = new ArrayList<HashMap<String, String>>();
            //final ArrayList<HashMap<String,String>> searchResult=new ArrayList<>();
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("ChitietDONHANGResult");
                int n = array.length();
                for (int i = 0; i < n; i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject v1 = array.getJSONObject(i);
                    map.put("ID", String.valueOf(i));
                    map.put("CTBH_BHID", v1.getString("CTBH_BHID"));
                    map.put("CTBH_ID", v1.getString("CTBH_ID"));
                    map.put("CTBH_HH_ID", v1.getString("CTBH_HH_ID"));
                    map.put("TEN_HANG", v1.getString("TEN_HANG"));
                    map.put("DONGIA", v1.getString("DONGIA"));
                    map.put("DVT", v1.getString("DVT"));
                    map.put("NGAYBAN", v1.getString("NGAYBAN"));
                    map.put("SL_MUA", v1.getString("SL_MUA"));
                    map.put("THANHTIEN",""+v1.getDouble("DONGIA")*v1.getInt("SL_MUA"));
                    mylist.add(map);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            adapter = new SimpleAdapter(Banhang_ct_dskh.this, mylist, R.layout.banhang_ct_dsbh_main, new String[]{"CTBH_BHID", "CTBH_ID", "CTBH_HH_ID", "TEN_HANG", "DONGIA", "DVT", "NGAYBAN", "SL_MUA"}, new int[]{R.id.textViewhh_ctbh_dsbh, R.id.textView1hh_ctbh_dsbh, R.id.textView2hh_ctbh_dsbh, R.id.textView3hh_ctbh_dsbh, R.id.textView4hh_ctbh_dsbh,
                    R.id.textView5hh_ctbh_dsbh, R.id.textView6hh_ctbh_dsbh, R.id.textView7hh_ctbh_dsbh});
            listView.setAdapter(adapter);
            String tt = getIntent().getStringExtra("tongtien");
            tongtien.setText(tt);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HashMap<String, String> article = mylist.get(position);
                    AlertDialog.Builder abc = new AlertDialog.Builder(Banhang_ct_dskh.this);
                    abc.setTitle("Tùy chon ?");
                    abc.setTitle("Lựa chọn của bạn ?");
                    final int positiontc = position;
                    abc.setNegativeButton("Cancel", null);
                    abc.setNeutralButton("Cập nhật", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HashMap<String, String> temp = mylist.get(positiontc);
                            String ctbh_id = temp.get("CTBH_ID");
                            String ctbh_hhid = temp.get("CTBH_HH_ID");
                            String ctbh_slmua = temp.get("SL_MUA");
                            String ctbh_ten = temp.get("TEN_HANG");
                            String ctbh_dg = temp.get("DONGIA");
                            String ctbh_dvt = temp.get("DVT");
                            String ctbh_thanhtien=temp.get("THANHTIEN");

                            banhang_idct_dsbh.setText(ctbh_id);
                            mahang_ct_dsbh.setText(ctbh_hhid);
                            sl_ct_dsbh.setText(ctbh_slmua);
                            tenhang_ct_dsbh.setText(ctbh_ten);
                            dgb_ct.setText(ctbh_dg);
                            dvt_ct_dsbh.setText(ctbh_dvt);
                            thanhtiendv=Double.parseDouble(ctbh_thanhtien);
                            Toast.makeText(Banhang_ct_dskh.this,""+thanhtiendv,Toast.LENGTH_SHORT).show();
                        }
                    });

                    abc.setPositiveButton("Delete", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            HashMap<String, String> temp = mylist.get(positiontc);
                            String ctbh_id = temp.get("CTBH_ID");
                            String bh_id = temp.get("CTBH_BHID");
                            String dg = temp.get("DONGIA");
                            float a = Float.parseFloat(dg);
                            String slmua = temp.get("SL_MUA");
                            int b = Integer.parseInt(slmua);
                            float thanhtien = a * b;
                            String d = tongtien.getText().toString();
                            double e = Double.parseDouble(d);
                            tongthanhtien = e - thanhtien;
                            String tt = "" + tongthanhtien;
                            tongtien.setText(tt);
                            String url = l.getUrlDeleteCtdh();
                            new Deletecthh().execute(url + ctbh_id + "/" + bh_id + "/" + tt);
                            mylist.remove(positiontc);
                            listView.setAdapter(adapter);
                        }
                    });
                    abc.show();
                }
            });
            new HangHoa().execute(l.getUrlIdhh());
        }

        public View getView(int positon, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) convertView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.banhang_showkh_main, null);
            }
            int select = positon;
            if (positon != select) {
                convertView.setBackgroundColor(Color.GRAY);
            } else {
                convertView.setBackgroundColor(Color.BLUE);
            }
            return convertView;
        }
    }

    private class Deletecthh extends AsyncTask<String, Void, String> {

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
                JSONArray array = json.getJSONArray("DeletedCTBHResult");
                JSONObject v = array.getJSONObject(0);
                String kq = v.getString("Detele");
                if (kq.equals("true")) {
                    Toast.makeText(Banhang_ct_dskh.this, "Bạn đã xóa hàng hóa thành công !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Banhang_ct_dskh.this, "Thất bại, kiểm tra lại", Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

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
            List<String> strings = new ArrayList<String>();
            arraytemple = new ArrayList<HashMap<String, String>>();
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("ShowAllResult");
                int n = array.length();
                for (int i = 0; i < n; i++) {
                    HashMap<String, String> map = new HashMap<>();
                    JSONObject v = array.getJSONObject(i);
                    map.put("ID_HANG", v.getString("HH_ID"));
                    map.put("TEN_HANG", v.getString("HH_TENHANG"));
                    map.put("LOAI_HH", v.getString("HH_LOAIHANGHOAID"));
                    map.put("MA_HH", v.getString("HH_MAHANGHOA"));
                    map.put("DONGIA", v.getString("HH_DONGIABANLE"));
                    map.put("MA_HH", v.getString("HH_MAHANGHOA"));
                    map.put("SL", v.getString("HH_SL_CD"));
                    map.put("DONVITINH", v.getString("HH_DONVITINHID"));
                    arraytemple.add(map);
                    strings.add(map.get("ID_HANG"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(Banhang_ct_dskh.this, "Bạn phải đăng nhập để thấy được hàng hóa !", Toast.LENGTH_SHORT).show();
            }
            final ArrayAdapter ab = new ArrayAdapter(Banhang_ct_dskh.this, android.R.layout.simple_list_item_1, strings);
            mahang_ct_dsbh.setAdapter(ab);
            mahang_ct_dsbh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String v=parent.getItemAtPosition(position).toString();
                    for(int i=0;i<arraytemple.size();i++) {
                        HashMap<String, String> luuid = arraytemple.get(i);
                        String idhang=luuid.get("ID_HANG");
                        if(mahang_ct_dsbh.getText().toString().equals("")) {
                            Toast.makeText(Banhang_ct_dskh.this,"Chưa nhập id hàng",Toast.LENGTH_SHORT).show();
                        }
                        else if(v.equals(idhang)){
                            String tenhang = luuid.get("TEN_HANG");
                            String dgb_show=luuid.get("DONGIA");
                            String dvt_show=luuid.get("DONVITINH");

                            tenhang_ct_dsbh.setText(tenhang);
                            dgb_ct.setText(dgb_show);
                            dvt_ct_dsbh.setText(dvt_show);


                        }
                    }
                }
            });
            //set update va cancel
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String dg=dgb_ct.getText().toString();
                    String slban=sl_ct_dsbh.getText().toString();
                    double db1=Double.parseDouble(dg);
                    int slban1=Integer.parseInt(slban);
                    double ttien=db1*slban1;
                    String getttt=tongtien.getText().toString();
                    double a=Double.parseDouble(getttt);
                    double b=(a+ttien)-thanhtiendv;
                    tongtien.setText(""+b);
                    new UpdateCTBH().execute(UrlupdateCTBH()+""+b);
                }
            });
        }
    }
    public  String UrlupdateCTBH(){
        String id_bh = getIntent().getStringExtra("bh_id");
        String urlkq=l.getUrlUpdateCTBH()
                + id_bh
                +"/"
                +banhang_idct_dsbh.getText().toString()
                +"/"
                +mahang_ct_dsbh.getText().toString()
                +"/"
                +sl_ct_dsbh.getText().toString()
                +"/"
                +dgb_ct.getText().toString()
                +"/"
                +dgb_ct.getText().toString()
                +"/"
                +dvt_ct_dsbh.getText().toString()
                +"/";
        return urlkq;
    }
    private class UpdateCTBH extends AsyncTask<String, Void, String> {

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
                JSONArray array = json.getJSONArray("UpdateCTBHResult");
                JSONObject v = array.getJSONObject(0);
                String kq=v.getString("Update");
                if(kq.equals("true")){
                    Toast.makeText(Banhang_ct_dskh.this,"Bạn đã cập nhật hàng hóa trong đơn hàng thành công!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Banhang_ct_dskh.this,"Bạn đã xóa đơn hàng thất bại , kiểm tra lại !",Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
