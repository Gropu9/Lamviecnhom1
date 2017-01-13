package com.example.administrator.quanlyshopdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.quanlyshopdemo.Khac.MD5;
import com.example.administrator.quanlyshopdemo.doituong.nhanvien;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Administrator on 11/11/2016.
 */
public class DangnhapActivity extends Activity {


    ArrayList<nhanvien> nv= new  ArrayList<nhanvien>();
    EditText id,pass;
    Button dangnhap;
    int checkKetnoi=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nhanvien b = new nhanvien();
        b.TenDangNhap="hieudautieng";
        b.MatKhau="E10ADC3949BA59ABBE56E057F20F883E";
        nv.add(b);
        anhxa();




        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // link nhân viên
                new ReadJSON().execute("http://hdshop.somee.com/api/CauHinhNhanVien/GET_ListNhanVien");
            }
        });

        sukien();


        //thiết lập sự kiện chọn phần tử cho Spinner

    }






    // cửa hàng
    class ReadJSON extends AsyncTask<String,Integer, String> {


        @Override
        protected String doInBackground(String... params) {
            String chuoi = docNoiDung_Tu_URL(params[0]);
            return chuoi;
        }

        @Override
        protected void onPostExecute(String s) {
//            Toast.makeText(DangnhapActivity.this,s,Toast.LENGTH_LONG).show();

            JSONObject root = null;
            String kq="";
            ArrayList<String> mangcuahang = new ArrayList<String>();

            try {
                JSONArray mang = new JSONArray(s);

                for(int i=0;i<mang.length();i++)
                {
                    JSONObject son = mang.getJSONObject(i);
                    nhanvien a= new nhanvien();
                    a.NhanVienGuid=son.getString("NhanVienGuid");
                    kq=son.getString("NhanVienGuid");
                    a.ChucVuGuid=son.getString("ChucVuGuid");
                    a.CuaHangGuid=son.getString("CuaHangGuid");
                    a.TenNhanVien=son.getString("TenNhanVien");
                    a.TenDangNhap=son.getString("TenDangNhap");
                    a.MatKhau=son.getString("MatKhau");
                    a.DiaChi=son.getString("DiaChi");
                    a.SoDienThoai=son.getString("SoDienThoai");
                    nv.add(a);
//                    mangcuahang.add(son.getString("TenCuaHang"));
//                    kq =kq + son.getString("TenCuaHang");
                }




            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(kq.equals(""))
            {
                checkKetnoi=0;
            }
            else
            {

                checkKetnoi=1;
            }
//

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



    void anhxa()
    {
        id =(EditText) findViewById(R.id.txtuser);
        pass =(EditText) findViewById(R.id.txtpass);
        dangnhap=(Button) findViewById(R.id.btnlogin);
    }


    void sukien()
    {




        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,
//                        HomeActivity.class);




                if(checkKetnoi==0)
                {
                    Toast.makeText(DangnhapActivity.this,"Mất kết nối",Toast.LENGTH_LONG).show();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            // link nhân viên
                            new ReadJSON().execute("http://hdshop.somee.com/api/CauHinhNhanVien/GET_ListNhanVien");
                        }
                    });
                }
                else {

                    for (int i = 0; i < nv.size(); i++) {

                        Log.d("tài khoản", nv.get(i).TenDangNhap);
                    }


                    int vitri = -1;
                    if (id.getText() == null || pass.getText() == null) {
                        Toast.makeText(DangnhapActivity.this, "không được để trống", Toast.LENGTH_LONG).show();

                    } else {
                        for (int i = 0; i < nv.size(); i++) {

                            if (id.getText().toString().equals(nv.get(i).TenDangNhap.trim())) {
                                vitri = i;
                                break;

                            }
                        }
//                    Log.d("tên tài khoản", "" + nv.get(vitri).TenDangNhap + "haha");
//                    Log.d("tai khoan", " " + vitri);

                        if (vitri != -1) {

                            if (mahoa(pass.getText().toString()).toString().equalsIgnoreCase(nv.get(vitri).MatKhau)) {
                                Log.d("spin ", "nhận");
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(DangnhapActivity.this, "Nhập sai mật khẩu", Toast.LENGTH_LONG).show();

                            }
                        } else {
                            Toast.makeText(DangnhapActivity.this, "Tài khoản không tồn tại hoặc nhập sai tài khoản", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }
        });
    }


    String mahoa(String matkhau)
    {
        MD5 a= new MD5();
        Log.d("check mat khau",a.encryptMD5(matkhau));
        return  a.encryptMD5(matkhau);


    }





}
