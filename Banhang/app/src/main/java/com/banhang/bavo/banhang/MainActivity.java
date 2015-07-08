package com.banhang.bavo.banhang;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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


public class MainActivity extends ActionBarActivity{
    EditText username,password;
    Button dangnhap,xoa;
    String user,pass;
    ArrayList<HashMap<String,String>> mylist;
    HashMap<String,String> map;

    CheckBox save;
    SharedPreferences savelogin;
    SharedPreferences.Editor loginEditor;
    Boolean check;

    private ProgressBar progressBar;
    TaoDatabase db=new TaoDatabase(MainActivity.this);
    LinkConnect l=new LinkConnect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        //xet progressbar
        progressBar=(ProgressBar)findViewById(R.id.prgressBar1);
        progressBar.setVisibility(View.GONE);

        //set edit
        username = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.pass);

        save=(CheckBox)findViewById(R.id.checkBox);

        savelogin=getSharedPreferences("LoginPre",MODE_PRIVATE);
        loginEditor=savelogin.edit();

        check=savelogin.getBoolean("check",false);
        if(check==true){
            username.setText(savelogin.getString("user",""));
            password.setText(savelogin.getString("pass",""));
            save.setChecked(true);
        }

        dangnhap=(Button)findViewById(R.id.btdangnhap);
        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load(v);
                InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(username.getWindowToken(),0);
                //username = (EditText) findViewById(R.id.user);
                //password = (EditText) findViewById(R.id.pass);
                user = username.getText().toString();
                pass = password.getText().toString();
                if(save.isChecked()){
                    loginEditor.putBoolean("check",true);
                    loginEditor.putString("user",user);
                    loginEditor.putString("pass",pass);
                    loginEditor.commit();
                }
                else {
                    loginEditor.clear();
                    loginEditor.commit();
                }
                if(user.equals("") || pass.equals("")){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getBaseContext(),"bạn chưa nhập mật khẩu hoặc tên đăng nhập !",Toast.LENGTH_SHORT).show();
                }
                else {
                    LinkConnect l=new LinkConnect();
                    String url = l.getUrl() + user + "/" + pass;
                    new HttpAsyncTask().execute(url);
                }

//              JSONObject jsonObject=GoiLogin.goiHam("http://10.0.0.66:8732/TestData/Login/Chungthuc/"+user+"/"+pass);
                //Toast.makeText(getBaseContext(),jsonObject.toString(),Toast.LENGTH_SHORT).show();
            }

        });
        xoa=(Button)findViewById(R.id.btxoa);
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
                password.setText("");
            }
        });

        //String user=username.getText().toString();
        //String pass=password.getText().toString();
        //JSONObject jsonObject=GoiLogin.goiHam("http://localhost:8732/TestJson/Login/Chungthuc/"+user+"/"+pass);
    }
    //set back
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

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
            showExitDialog();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
    public void showExitDialog() {
        // TODO Auto-generated method stub
        AlertDialog.Builder confirm=new AlertDialog.Builder(MainActivity.this);
        confirm.setTitle("Exit Application");
        confirm.setCancelable(false);
        confirm.setMessage("Bạn thật sự muốn thoát ứng dụng ?");
        confirm.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        });
        confirm.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        confirm.create().show();
    }
    //set Progess
    public void load(View view){
        progressBar.setVisibility(View.VISIBLE);
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) { try {
            JSONObject json = new JSONObject(result);
            JSONArray array = json.getJSONArray("ChungthucResult");
            int n = array.length();
            String kq = null;
            //String ID = json.getString("");
            for (int i = 0; i < n; i++) {
                JSONObject v1 = array.getJSONObject(i);
                kq = v1.getString("Chungthuc");
            }
            if (kq.equals("true")) {
                //set csdl vao sqlit;
                username = (EditText) findViewById(R.id.user);
                String user = username.getText().toString();
                password = (EditText) findViewById(R.id.pass);
                String pass = password.getText().toString();
                String ngay="2015-10-17 16:27:59.335";
                Calendar calendar=Calendar.getInstance();
                Date date = new Date(calendar.getTimeInMillis());
                if(checkCSDL()==true) {
                    //db.addUser(new User(user, pass, 4, a));
                    db.onUpdate(new User(2,user, pass, 5, ngay));
                    //db.addUser(new User(user, pass, 1, ngay));
                    List<User> users = db.getAllContacts();
                    for (User cn : users) {
                        String log = "ID: " + cn.getId() + " ,Name: " + cn.getUsername() + " ,Pass: " + cn.getPassword()
                                + " ,STATUS: " + cn.getStatus() + " ,CreateAt: " + cn.getCreate_at();
                        Log.d("Name: ", log);
                    }
                    new SynchHangHoa().execute(l.getUrlIdhh());
                    Log.d("Reading: ", "Re");
                    List<TableHanghoa> hanghoas = db.getAllContacts1();
                    for (TableHanghoa cn : hanghoas) {
                        String loq1 = "ID_HANG: " + cn.getId_hh() + " ,ID_LH: " + cn.getId_loaihang() + " ,DVT: " + cn.getDvt_id() + " ,TEN: " + cn.getTenhang()
                                + " ,MAHANG: " + cn.getMahanghoa()+ " ,MODIFYAT: "+cn.getModified_at();
                        Log.d("Name1: ", loq1);
                    }
                    new SynchKhachhang().execute(l.getUrlIdkh());
                    Log.d("Khachhang: ", "Hehe");
                    List<TableKhachhang> tableKhachhangs = db.getAllContacts2();
                    for (TableKhachhang cn : tableKhachhangs) {
                        String log2 = "ID_KH: " + cn.getId_kh() + " ,ID_LOAIKH: " + cn.getId_loaikh() + " ,MAKH: " + cn.getMa_kh() + " ,TENKH: " + cn.getTen_kh()
                        +" ,MODIFY: "+cn.getModified_at();
                        Log.d("Name2: ", log2);
                    }
                }
                else {
                    db.addUser(new User(user, pass, 1, ngay));
                    Log.d("Reading: ", "Re");
                    List<User> users = db.getAllContacts();
                    for (User cn : users) {
                        String log = "ID: " + cn.getId() + " ,Name: " + cn.getUsername() + " ,Pass: " + cn.getPassword()
                                + " ,STATUS: " + cn.getStatus() + " ,CreateAt: " + cn.getCreate_at();
                        Log.d("Name: ", log);
                    }

                    new SQLHangHoa().execute(l.getUrlIdhh());
                    Log.d("Reading: ", "Re");
                    List<TableHanghoa> hanghoas = db.getAllContacts1();
                    for (TableHanghoa cn : hanghoas) {
                        String loq1 = "ID_HANG: " + cn.getId_hh() + " ,ID_LH: " + cn.getId_loaihang() + " ,DVT: " + cn.getDvt_id() + " ,TEN: " + cn.getTenhang()
                                + " ,MAHANG: " + cn.getMahanghoa();
                        Log.d("Name1: ", loq1);
                    }
                    new SQLKhachhang().execute(l.getUrlIdkh());
                    Log.d("Khachhang: ", "Hehe");
                    List<TableKhachhang> tableKhachhangs = db.getAllContacts2();
                    for (TableKhachhang cn : tableKhachhangs) {
                        String log2 = "ID_KH: " + cn.getId_kh() + " ,ID_LOAIKH: " + cn.getId_loaikh() + " ,MAKH: " + cn.getMa_kh() + " ,TENKH: " + cn.getTen_kh();
                        Log.d("Name2: ", log2);
                    }
                    new SQLBanhang().execute(l.getUrlSynchBH());
                    Log.d("Banhang: ", "Hoho");
                    List<TableBanhang> tableBanhangs=db.getAllContacts3();
                    for (TableBanhang cn: tableBanhangs){
                        String log3="ID: "+cn.getId_bh()+" ,KH_ID: "+cn.getId_kh()+ ", TU KHO: "+cn.getTukho_id()+" ,MSP: "+cn.getBh_msp();
                        Log.d("Name3: ",log3);
                    }
                }
                    Toast.makeText(getBaseContext(), "Xin chào: " + user, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(MainActivity.this, Index.class);
                    startActivity(i);
                    finish();
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getBaseContext(), "tên đăng nhập sai hoặc mật khẩu sai !", Toast.LENGTH_LONG).show();
            }
        }
        catch (JSONException e){}
        }
    }
    public String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            String u=username.getText().toString();
            String p=password.getText().toString();
            db=new TaoDatabase(MainActivity.this);
            boolean kt=false;
            kt=db.getLogin(new User(u,p));
            if(kt) {
                Intent i = new Intent(MainActivity.this, Index.class);
                startActivity(i);
                finish();
            }
            else {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
    public boolean checkCSDL(){
        File database=getApplicationContext().getDatabasePath("MyDB");
        if(database.exists()){
            return true;
        }
        else {
            return false;
        }
    }
    //SHOW DANH SACH NGUOI DUNG TRONG LOGIN SQLITE
    private class SQLHangHoa extends AsyncTask<String, Void, String> {

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
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            mylist=new ArrayList<HashMap<String, String>>();
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("ShowAllResult");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject v1 = array.getJSONObject(i);
                    map=new HashMap<>();
                    map.put("HH_ID",v1.getString("HH_ID"));
                    map.put("HH_LOAIHANGHOAID",v1.getString("HH_LOAIHANGHOAID"));
                    map.put("HH_DONVITINHID",v1.getString("HH_DONVITINHID"));
                    map.put("HH_TENHANG", v1.getString("HH_TENHANG"));
                    map.put("HH_MAHANGHOA",v1.getString("HH_MAHANGHOA"));
                    map.put("HH_QUYCACH",v1.getString("HH_QUYCACH"));
                    map.put("HH_DONGIABANLE",v1.getString("HH_DONGIABANLE"));
                    map.put("HH_MAU",v1.getString("HH_MAU"));
                    map.put("HH_DVT_CD_ID",v1.getString("HH_DVT_CD_ID"));
                    map.put("HH_SL_CD",v1.getString("HH_SL_CD"));
                    map.put("CREATED_AT",v1.getString("CREATED_AT"));
                    map.put("MODIFIED_AT",v1.getString("MODIFIED_AT"));
                    map.put("DELETED_AT",v1.getString("DELETED_AT"));
                    mylist.add(map);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            for(int i=0;i<mylist.size();i++){
                HashMap<String,String> tam=mylist.get(i);
                int hhid=Integer.parseInt(tam.get("HH_ID"));
                int loai_hh=Integer.parseInt(tam.get("HH_LOAIHANGHOAID"));
                int hh_dvt=Integer.parseInt(tam.get("HH_DONVITINHID"));
                String tenhang=tam.get("HH_TENHANG");
                String mhh=tam.get("HH_MAHANGHOA");
                String qc=tam.get("HH_QUYCACH");
                String dgb=tam.get("HH_DONGIABANLE");
                String mau=tam.get("HH_MAU");
                String hh_dvt_cd=tam.get("HH_DVT_CD_ID");
                String sl_cd=tam.get("HH_SL_CD");
                String create=tam.get("CREATED_AT");
                String modify=tam.get("MODIFIED_AT");
                db.addHH(new TableHanghoa(hhid,loai_hh,hh_dvt,tenhang,mhh,qc,dgb,mau,hh_dvt_cd,sl_cd,create,modify));
            }
        }
    }
    private class SynchHangHoa extends AsyncTask<String, Void, String> {

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
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("ShowAllResult");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject v1 = array.getJSONObject(i);
                    int hhid = v1.getInt("HH_ID");
                    int loai_hh = v1.getInt("HH_LOAIHANGHOAID");
                    int hh_dvt = v1.getInt("HH_DONVITINHID");
                    String tenhang = v1.getString("HH_TENHANG");
                    String mhh = v1.getString("HH_MAHANGHOA");
                    String qc = v1.getString("HH_QUYCACH");
                    String dgb = v1.getString("HH_DONGIABANLE");
                    String mau = v1.getString("HH_MAU");
                    String hh_dvt_cd = v1.getString("HH_DVT_CD_ID");
                    String sl_cd = v1.getString("HH_SL_CD");
                    String create = v1.getString("CREATED_AT");
                    String modify = v1.getString("MODIFIED_AT");
                    db.onUpdateHH(new TableHanghoa(hhid, loai_hh, hh_dvt, tenhang, mhh, qc, dgb, mau, hh_dvt_cd, sl_cd, create, modify));
                    if(db.SynchInsertHH(new TableHanghoa(hhid))==false){
                        db.addHH(new TableHanghoa(hhid, loai_hh, hh_dvt, tenhang, mhh, qc, dgb, mau, hh_dvt_cd, sl_cd, create, modify));
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private class SQLKhachhang extends AsyncTask<String, Void, String> {

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
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("ShowAllKHResult");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject v1 = array.getJSONObject(i);
                    int kh_id=v1.getInt("KHNCC_ID");
                    String kh_loaikh=v1.getString("KHNCC_LOAIKHACHHANG");
                    String ma_kh=v1.getString("KHNCC_MAKHACHHANG");
                    String ten_kh=v1.getString("KHNCC_TEN");
                    String diachi=v1.getString("KHNCC_DIACHI");
                    String sdt=v1.getString("KHNCC_SODIENTHOAI");
                    String tp_ten=v1.getString("TP_TEN");
                    String qh_ten=v1.getString("KHNCC_QUANHUYENTEN");
                    String px_ten=v1.getString("PX_TEN");
                    String kh_create=v1.getString("KH_CREATED_AT");
                    String kh_modify=v1.getString("KH_MODIFIED_AT");
                    db.addKH(new TableKhachhang(kh_id, kh_loaikh, ma_kh, ten_kh, diachi, sdt, tp_ten, qh_ten, px_ten, kh_create, kh_modify));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private class SynchKhachhang extends AsyncTask<String, Void, String> {

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
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("ShowAllKHResult");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject v1 = array.getJSONObject(i);
                    int kh_id=v1.getInt("KHNCC_ID");
                    String kh_loaikh=v1.getString("KHNCC_LOAIKHACHHANG");
                    String ma_kh=v1.getString("KHNCC_MAKHACHHANG");
                    String ten_kh=v1.getString("KHNCC_TEN");
                    String diachi=v1.getString("KHNCC_DIACHI");
                    String sdt=v1.getString("KHNCC_SODIENTHOAI");
                    String tp_ten=v1.getString("TP_TEN");
                    String qh_ten=v1.getString("KHNCC_QUANHUYENTEN");
                    String px_ten=v1.getString("PX_TEN");
                    String kh_create=v1.getString("KH_CREATED_AT");
                    String kh_modify=v1.getString("KH_MODIFIED_AT");
                    db.UpdateKH(new TableKhachhang(kh_id, kh_loaikh, ma_kh, ten_kh, diachi, sdt, tp_ten, qh_ten, px_ten, kh_create, kh_modify));
                    if(db.SynchInsertKH(new TableKhachhang(kh_id))==false){
                        db.addKH(new TableKhachhang(kh_id, kh_loaikh, ma_kh, ten_kh, diachi, sdt, tp_ten, qh_ten, px_ten, kh_create, kh_modify));
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private class SQLBanhang extends AsyncTask<String, Void, String> {

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
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("ShowBHResult");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject v1 = array.getJSONObject(i);
                    int id_bh=v1.getInt("BH_ID");
                    int bh_kh=v1.getInt("BH_KHACHHANG");
                    int bh_tukhoid=v1.getInt("BH_TUKHOID");
                    String bhmsp=v1.getString("BH_MSP");
                    String ngayban=v1.getString("BH_NGAYBAN");
                    String bh_stpt=v1.getString("BH_SOTIENPHAITHU");
                    double stpt=Double.parseDouble(bh_stpt);
                    String bh_stdt=v1.getString("BH_SOTIENDATHU");
                    double stdt=Double.parseDouble(bh_stdt);
                    String ten_nb=v1.getString("BH_NGUOIBAN");
                    String uname=v1.getString("BH_UNAME");
                    String bh_create=v1.getString("BH_CREATE_AT");
                    String bh_modify=v1.getString("BH_MODIFY_AT");
                    db.addBH(new TableBanhang(id_bh,bh_kh,bh_tukhoid,bhmsp,ngayban,stpt,stdt,ten_nb,uname,bh_create,bh_modify));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
