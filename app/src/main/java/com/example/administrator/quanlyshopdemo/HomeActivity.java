package com.example.administrator.quanlyshopdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 11/9/2016.
 */
public class HomeActivity extends Activity {

    private Button back;
    private Button sanpham;
    private Button cuahang;
    private TextView userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,
                        DangnhapActivity.class);
                finish();
                startActivity(intent);

            }
        });

        sanpham = (Button) findViewById(R.id.btnsanpham);
        sanpham.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,
                        SanphamActivity.class);
                finish();

                startActivity(intent);
            }
        });

        cuahang = (Button) findViewById(R.id.btncuahang);
        cuahang.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,
                        MainActivity.class);
                finish();
                startActivity(intent);

            }
        });
    }
}