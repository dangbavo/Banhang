package com.banhang.bavo.banhang;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
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
 * Created by Ba Vo on 5/21/2015.
 */
public class Index extends Activity {
    //final String myUrl = "http://192.168.3.147:8732/TestData/Showhang/HangHoa";
    //ListAdapter listAdapter;
    ImageButton bt1,bt2,bt3,bt4;
    private ProgressBar progressBar;
    TaoDatabase db=new TaoDatabase(Index.this);
    LinkConnect l=new LinkConnect();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_index);

        progressBar=(ProgressBar)findViewById(R.id.prgressBar1);
        progressBar.setVisibility(View.GONE);

        bt1=(ImageButton)findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Index.this,Banhang_Showkh.class);
                Index.this.startActivity(i);
                finish();
            }
        });

        bt2 = (ImageButton) findViewById(R.id.bt2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load(v);
                Intent i=new Intent(Index.this,ShowIDHH.class);
                Index.this.startActivity(i);
                finish();
            }
        });
        bt3=(ImageButton)findViewById(R.id.bt3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load(v);
                Intent i = new Intent(Index.this, ShowKH.class);
                Index.this.startActivity(i);
                finish();
            }
        });
        bt4=(ImageButton)findViewById(R.id.bt4);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load(v);
                Intent i = new Intent(Index.this, Index_Baocao.class);
                Index.this.startActivity(i);
                finish();
            }
        });

    }
    public void load(View view){
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed(){
        Intent intent=new Intent(Index.this,MainActivity.class);
        Index.this.startActivity(intent);
        finish();
    }

}

//Nhan intent duoc gui tu Main
//String h = getIntent().getStringExtra("h");

//Toast.makeText(getBaseContext(), h + "Dang Ba Vo", Toast.LENGTH_SHORT).show();
/*
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
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("ShowAllResult");
                int n = array.length();
                String hh = null;
                String ten_hh = null;
                Intent intent = new Intent(Index.this, ShowHH.class);
                for (int i = 0; i < n; i++) {
                    JSONObject v1 = array.getJSONObject(i);
                    hh = v1.getString("HH_ID");
                    ten_hh = v1.getString("HH_TENHANG");
                    //System.out.println("Hang hoa: "+hh+"\n"+"TEN: "+ten_hh);
                    intent.putExtra("id",hh);
                    intent.putExtra("ten",ten_hh);
                }
                Index.this.startActivity(intent);


                //intent.putExtra("id",hh);
                //intent.putExtra("ten",ten_hh);
                //Index.this.startActivity(intent);


                //Tham chi?u ??n "main" trong chu?i Json
                //JSONObject main = object.getJSONObject("ShowAllResult");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
*/



