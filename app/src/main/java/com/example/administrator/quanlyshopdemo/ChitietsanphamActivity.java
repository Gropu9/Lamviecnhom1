package com.example.administrator.quanlyshopdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.quanlyshopdemo.doituong.loaisanpham;
import com.example.administrator.quanlyshopdemo.doituong.sanpham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 11/10/2016.
 */
public class ChitietsanphamActivity extends Activity {


    private Button back;
    TextView ten,gia,chatlieu,mausac,mota;
    ImageView hinh ;
    String tensp,giasp,chatsp,mausp,hinhsp,motasp;
    ArrayList<loaisanpham> lsp = new ArrayList<loaisanpham>();
    ArrayList<sanpham> sp = new ArrayList<sanpham>();
    ArrayList<String> manggia = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);
        Bundle bd =getIntent().getExtras();
        if(bd!=null)
        {
            tensp = bd.getString("tensp");
            giasp = bd.getString("gia");
            chatsp = bd.getString("chatlieu");
            mausp = bd.getString("mausac");
            hinhsp = bd.getString("hinh");
            motasp = bd.getString("mota");
        }




        ten = (TextView) findViewById(R.id.txtTenspchitietsanpham);
        gia = (TextView) findViewById(R.id.txtGiachitietsanpham);
        chatlieu = (TextView) findViewById(R.id.txtchatlieuchitietsanpham);
        mausac = (TextView) findViewById(R.id.txtmausacchitietsanpham);
        mota = (TextView) findViewById(R.id.txtmotachitietsanpham);


       ten.setText(tensp);
        gia.setText(giasp);
        chatlieu.setText(chatsp);
        mausac.setText(mausp);
//        mota.setText(motasp);



        //thiết lập sự kiện chọn phần tử cho Spinner

        back = (Button) findViewById(R.id.btnbackchitietsanpham);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,
//                        HomeActivity.class);
                Log.d("spin ", "nhận");
                finish();
                startActivity(new Intent(getApplicationContext(), SanphamActivity.class));
//                startActivity(intent);

            }
        });






      ImageView hinh =(ImageView) findViewById(R.id.imagesHinhchitietsanpham);
        Context context = hinh.getContext();
        String a= "http://hdshop.somee.com"+hinhsp;
        Log.d("khu vuc ", "" + a);
        Picasso.with(context).load(a).into(hinh);





    }





}
