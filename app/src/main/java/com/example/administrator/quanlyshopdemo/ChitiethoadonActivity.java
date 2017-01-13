package com.example.administrator.quanlyshopdemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.quanlyshopdemo.Adapter.ListAdapterchitiethoadon;
import com.example.administrator.quanlyshopdemo.doituong.chitiethoadon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Administrator on 11/25/2016.
 */
public class ChitiethoadonActivity extends AppCompatActivity {

    String mahoadon;
    private Button back;
    TextView tenkhach,tennhanvien,tongtien;
    ListView lv;
    ArrayList<chitiethoadon> cthd= new  ArrayList<chitiethoadon>();
    ArrayList<String> thongtinhoadon = new ArrayList<String>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiethoadon);

        lv =(ListView) findViewById(R.id.listViewdanhsachsanphamhoadon);

        tenkhach =(TextView) findViewById(R.id.txttenkhachhangchitiethoadon);
        tennhanvien =(TextView) findViewById(R.id.txtnhanvienchitiethoadon);
        tongtien =(TextView) findViewById(R.id.txtgiachitiethoadon);

        Bundle bd = getIntent().getExtras();
        if (bd != null) {
            mahoadon = bd.getString("mahoadon");
        }

        back = (Button) findViewById(R.id.btnbackhoadon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,
//                        HomeActivity.class);
                Log.d("spin ", "nhận");
                finish();
                startActivity(new Intent(getApplicationContext(), HoadonActivity.class));
//                startActivity(intent);

            }
        });

            Log.d("test mã háo đơn",mahoadon);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new ReadJSON1().execute("http://hdshop.somee.com/api/CauHinhCTHD/GET_ChiTietHoaDon_GetOne?id="+mahoadon);

            }
        });
    }




    // cửa hàng
    class ReadJSON1 extends AsyncTask<String,Integer, String> {


        @Override
        protected String doInBackground(String... params) {
            String chuoi = docNoiDung_Tu_URL(params[0]);
            return chuoi;
        }

        @Override
        protected void onPostExecute(String s) {

//            Toast.makeText(ChitiethoadonActivity.this,s,Toast.LENGTH_LONG).show();
            JSONObject root = null;
            String kq="";
            ArrayList<String> mangcuahang = new ArrayList<String>();
            int stt=0;

            try {
                JSONArray mang = new JSONArray(s);

                JSONObject d = mang.getJSONObject(0);
                tenkhach.setText(d.getString("TenKhachHang"));
                tennhanvien.setText(d.getString("TenNhanVien"));
                tongtien.setText(d.getString("GiaTien")+"VNĐ");




                for(int i=0;i<mang.length();i++)
                {
                    JSONObject son = mang.getJSONObject(i);
                    chitiethoadon a= new chitiethoadon();
                    a.TenSanPham = son.getString("TenSanPham");
                    a.SoLuongMua = son.getString("SoLuongMua");
                    a.DonGia = son.getString("DonGia");


                    cthd.add(a);
                }


                ListAdapterchitiethoadon adapter1=new  ListAdapterchitiethoadon( ChitiethoadonActivity.this,
                        R.layout.activity_dongchitiethoadon,
                        cthd);



                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                lv.setAdapter(adapter1);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("cửa hàng ", "" + kq);
            super.onPostExecute(s);
        }
    }






    private static String docNoiDung_Tu_URL(String theUrl)
    {
        StringBuilder content = new StringBuilder();

        try
        {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }
}
