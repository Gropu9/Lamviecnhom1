package com.example.administrator.quanlyshopdemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.administrator.quanlyshopdemo.doituong.loaisanpham;
import com.example.administrator.quanlyshopdemo.doituong.sanpham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Administrator on 11/9/2016.
 */
public class SanphamActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView lv;
    Spinner spin;
    private Button back;
    ImageView hinhsp;
    EditText timkiem;

    ArrayList<loaisanpham> lsp = new ArrayList<loaisanpham>();
    ArrayList<sanpham> sp = new ArrayList<sanpham>();
    ArrayList<String> manggia = new ArrayList<String>();
    ArrayList<sanpham> mangsanphamtheoloai = new ArrayList<sanpham>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maindanhsachsanpham);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        lv =(ListView) findViewById(R.id.listViewdanhsachsanpham);
        spin=(Spinner) findViewById(R.id.spinnerdanhsachsanpham);
        timkiem =(EditText) findViewById(R.id.editTexttimkiemdanhsachsanpham);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new ReadJSON3().execute("http://hdshop.somee.com/api/CauHinhSanPham/GET_List_SanPham");
                new ReadJSON().execute("http://hdshop.somee.com/api/CauHinhLoaiSanPham/GET_ListLoaiSanPham");
            }
        });


        //thiết lập sự kiện chọn phần tử cho Spinner
        spin.setOnItemSelectedListener(new MyProcessEvent());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                // TODO Auto-generated method stub
//                Toast.makeText(getApplicationContext(), monhoc[position], Toast.LENGTH_SHORT).show();
                vaomanhinhchitiet(position);
            }
        });



        timkiem.addTextChangedListener(new TextWatcher() {

            //Đang thay đổi
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            timkiem();

            }
            //Trước khi thay đổi
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }
            //Sau khi thay đổi
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });

    }
    private class MyProcessEvent implements
            AdapterView.OnItemSelectedListener {
        //Khi có chọn lựa thì vào hàm này
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            //arg2 là phần tử được chọn trong data source
//            Log.d("spin ", "" + kv.get(arg2).TenLoaiSanPham);
//            loadcuahangtheokhu(arg2);
                loadcuahangtheokhu(arg2);
        }

        //Nếu không chọn gì cả
        public void onNothingSelected(AdapterView<?> arg0) {

        }


    }

//    private class MyProcessEvent1 implements
//            AdapterView.OnItemSelectedListener {
//        //Khi có chọn lựa thì vào hàm này
//        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//
//            vaomanhinhchitiet(arg2);
//        }
//
//        //Nếu không chọn gì cả
//        public void onNothingSelected(AdapterView<?> arg0) {
//
//        }
//
//
//    }



    class ReadJSON extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {
            String chuoi = docNoiDung_Tu_URL(params[0]);
            return chuoi;
        }

        @Override
        protected void onPostExecute(String s) {
//            Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            JSONObject root = null;
            String kq = "";
            ArrayList<String> mangkhuvuc = new ArrayList<String>();
            mangkhuvuc.add("Toàn bộ sản phẩm");


            try {
                JSONArray mang = new JSONArray(s);

                for (int i = 0; i < mang.length(); i++) {
                    JSONObject son = mang.getJSONObject(i);
                    loaisanpham a = new loaisanpham();
                    a.LoaiSanPhamGuid = son.getString("LoaiSanPhamGuid");
                    a.TenLoaiSanPham = son.getString("TenLoaiSanPham");
                    lsp.add(a);
                    mangkhuvuc.add(son.getString("TenLoaiSanPham"));
                    kq = kq + son.getString("TenLoaiSanPham");
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        SanphamActivity.this,
                        android.R.layout.simple_list_item_1,
                        mangkhuvuc

                );


                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spin.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("khu vuc ", "" + kq);
            super.onPostExecute(s);
        }
    }


    // cửa hàng
    class ReadJSON3 extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {
            String chuoi = docNoiDung_Tu_URL(params[0]);
            return chuoi;
        }

        @Override
        protected void onPostExecute(String s) {
//            Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            JSONObject root = null;
            String kq = "";
            ArrayList<String> mangsanpham = new ArrayList<String>();


            try {
                JSONArray mang = new JSONArray(s);

                for (int i = 0; i < mang.length(); i++) {
                    JSONObject son = mang.getJSONObject(i);
                    sanpham a = new sanpham();
                    a.SanPhamGuid = son.getString("SanPhamGuid");
                    a.CuaHangGuid = son.getString("CuaHangGuid");
                    a.LoaiSanPhamGuid = son.getString("LoaiSanPhamGuid");
                    a.TenSanPham = son.getString("TenSanPham");
                    a.HinhAnh = son.getString("HinhAnh");
                    a.MauSac = son.getString("MauSac");
                    a.GiaTien = son.getInt("GiaTien");
                    manggia.add(son.getString("GiaTien"));
                    Log.d("spin ", String.valueOf(son.getInt("GiaTien")));
                    a.MoTa = son.getString("MoTa");
                    a.ChatLieu = son.getString("ChatLieu");
                    sp.add(a);
                    mangsanpham.add(son.getString("TenSanPham"));
                    kq = kq + son.getString("TenSanPham");
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        SanphamActivity.this,
                        android.R.layout.simple_list_item_1,
                        mangsanpham

                );

//                ListAdapter adapter1=new ListAdapter( getApplicationContext(), , mangsanpham);


                lv.setAdapter(adapter);


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


    private void loadcuahangtheokhu(int a) {

        ArrayList<sanpham> mangcuahang2 = new ArrayList<sanpham>();
        mangsanphamtheoloai.clear();

        if(a!=0) {
            int k = a - 1;
            for (int i = 0; i < sp.size(); i++) {

                if (sp.get(i).LoaiSanPhamGuid.toString().equals(lsp.get(k).LoaiSanPhamGuid.toString())) {
                    mangcuahang2.add(new sanpham(sp.get(i).TenSanPham, sp.get(i).GiaTien, sp.get(i).HinhAnh));
                    mangsanphamtheoloai.add(sp.get(i));
                    Log.d("spin ", "" + sp.get(i).TenSanPham);
                    Log.d("spin ", "" + sp.get(i).HinhAnh);
                    String haha = "http://hdshop.somee.com" + sp.get(i).HinhAnh;
                    Log.d("đường dẫn ", "" + haha);
                } else {
                    Log.d("spin ", "không trùng");
                    Log.d("spin ", sp.get(i).LoaiSanPhamGuid.toString() + "  " + lsp.get(k).LoaiSanPhamGuid.toString());
                }
            }
        }
        else
        {
            for (int i = 0; i < sp.size(); i++)
            {
                mangcuahang2.add(new sanpham(sp.get(i).TenSanPham, sp.get(i).GiaTien, sp.get(i).HinhAnh));
                mangsanphamtheoloai.add(sp.get(i));
                Log.d("spin ", "" + sp.get(i).TenSanPham);
                Log.d("spin ", "" + sp.get(i).HinhAnh);
                String haha = "http://hdshop.somee.com" + sp.get(i).HinhAnh;
                Log.d("đường dẫn ", "" + haha);

            }
        }



        ListAdapter adapter1 = new ListAdapter(
                SanphamActivity.this, R.layout.activity_dongsanpham,mangsanphamtheoloai
        );



        lv.setAdapter(adapter1);


    }


    void vaomanhinhchitiet(int sanpham)
    {
        Intent mh2 = new Intent(SanphamActivity.this,ChitietsanphamActivity.class);
        mh2.putExtra("tensp",mangsanphamtheoloai.get(sanpham).TenSanPham.toString());
        mh2.putExtra("gia",mangsanphamtheoloai.get(sanpham).GiaTien.toString());
        mh2.putExtra("chatlieu",mangsanphamtheoloai.get(sanpham).ChatLieu.toString());
        mh2.putExtra("mausac",mangsanphamtheoloai.get(sanpham).MauSac.toString());
        mh2.putExtra("mota",mangsanphamtheoloai.get(sanpham).MoTa.toString());
        mh2.putExtra("hinh",mangsanphamtheoloai.get(sanpham).HinhAnh.toString());
        finish();
        startActivity(mh2);


    }

    void timkiem()
    {

        //Bắt sự kiện thay đổi số b
        ArrayList<sanpham> mangcuahang2 = new ArrayList<sanpham>();
        String a=timkiem.getText().toString();
        mangsanphamtheoloai.clear();
        int b=a.length();
//        String c=a.substring(0,b);

        for (int i = 0; i < sp.size(); i++)
        {
            if(b>sp.get(i).TenSanPham.toString().length())
            {
                 continue;
            }
            if (a.equalsIgnoreCase(sp.get(i).TenSanPham.toString().substring(0,b))) {


                mangsanphamtheoloai.add(new sanpham(sp.get(i).TenSanPham, sp.get(i).GiaTien, sp.get(i).HinhAnh));
                Log.d("spin ", "" + sp.get(i).TenSanPham);
                Log.d("spin ", "" + sp.get(i).HinhAnh);
                String haha = "http://hdshop.somee.com" + sp.get(i).HinhAnh;
                Log.d("đường dẫn ", "" + haha);
            }


        }
        ListAdapter adapter1 = new ListAdapter(
                SanphamActivity.this, R.layout.activity_dongsanpham,mangsanphamtheoloai
        );



        lv.setAdapter(adapter1);


    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Log.d("spin ", "nhận");
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else if (id == R.id.nav_gallery) {
            Log.d("spin ", "nhận");
            finish();
            startActivity(new Intent(getApplicationContext(), SanphamActivity.class));

        } else if (id == R.id.nav_slideshow) {
            Log.d("spin ", "nhận");
            finish();
            startActivity(new Intent(getApplicationContext(), HoadonActivity.class));

        }else if (id == R.id.itemnhanvien) {
            Log.d("spin ", "nhận");
            finish();
            startActivity(new Intent(getApplicationContext(), NhanVienActivity.class));

        } else if (id == R.id.nav_manage) {

            Log.d("spin ", "nhận");
            finish();
            startActivity(new Intent(getApplicationContext(), ChartthongkeActivity.class));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Log.d("spin ", "nhận");
            finish();
            startActivity(new Intent(getApplicationContext(), DangnhapActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

