package com.banhang.bavo.banhang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Ba Vo on 7/3/2015.
 */
public class Index_Baocao extends ActionBarActivity {
    Button doanhthu,thu,chi,tonkho;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_baocao);

        doanhthu=(Button)findViewById(R.id.bt_doanhthu);
        doanhthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Index_Baocao.this, Baocao_showdoanhthu.class);
                Index_Baocao.this.startActivity(i);
                finish();
            }
        });
    }
    public void onBackPressed(){
        Intent intent=new Intent(Index_Baocao.this,Index.class);
        Index_Baocao.this.startActivity(intent);
        finish();
    }

}
