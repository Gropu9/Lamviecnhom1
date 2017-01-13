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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.administrator.quanlyshopdemo.Adapter.ListAdaptercuahang;
import com.example.administrator.quanlyshopdemo.doituong.cuahang;
import com.example.administrator.quanlyshopdemo.doituong.khuvuc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView lv;
    Spinner spin;

    ArrayList<khuvuc> kv= new  ArrayList<khuvuc>();
    ArrayList<cuahang> ch= new  ArrayList<cuahang>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maindanhsachcuahang);

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

        lv =(ListView) findViewById(R.id.listViewkhuvuc);
        spin=(Spinner) findViewById(R.id.spinnerkhuvuc);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new ReadJSON1().execute("http://hdshop.somee.com/api/CauHinhCuaHang/GET_ListCuaHang");
                new ReadJSON().execute("http://hdshop.somee.com/api/CauHinhKhuVuc/GET_ListKhuVuc");
            }
        });


        //thiết lập sự kiện chọn phần tử cho Spinner
        spin.setOnItemSelectedListener(new MyProcessEvent());


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                // TODO Auto-generated method stub
//                Toast.makeText(getApplicationContext(), monhoc[position], Toast.LENGTH_SHORT).show();
                vaomanhinhthongke(position);
            }
        });

    }
    private class MyProcessEvent implements
            AdapterView.OnItemSelectedListener
    {
        //Khi có chọn lựa thì vào hàm này
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            //arg2 là phần tử được chọn trong data source


            loadcuahangtheokhu(arg2);
        }
        //Nếu không chọn gì cả
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }



    class ReadJSON extends AsyncTask<String,Integer, String> {


        @Override
        protected String doInBackground(String... params) {
            String chuoi = docNoiDung_Tu_URL(params[0]);
            return chuoi;
        }

        @Override
        protected void onPostExecute(String s) {
//            Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            JSONObject root = null;
            String kq="";
            ArrayList<String> mangkhuvuc = new ArrayList<String>();
            mangkhuvuc.add("Toàn bộ cửa hàng");


            try {
                JSONArray mang = new JSONArray(s);

                for(int i=0;i<mang.length();i++)
                {
                    JSONObject son = mang.getJSONObject(i);
                    khuvuc a= new khuvuc();
                    a.KhuVucGuid=son.getString("KhuVucGuid");
                    a.TenKhuVuc=son.getString("TenKhuVuc");
                    kv.add(a);
                    mangkhuvuc.add(son.getString("TenKhuVuc"));
                    kq =kq + son.getString("TenKhuVuc");
                }
                ArrayAdapter<String> adapter = new  ArrayAdapter<String>(
                        MainActivity.this,
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
    class ReadJSON1 extends AsyncTask<String,Integer, String> {


        @Override
        protected String doInBackground(String... params) {
            String chuoi = docNoiDung_Tu_URL(params[0]);
            return chuoi;
        }

        @Override
        protected void onPostExecute(String s) {
//            Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            JSONObject root = null;
            String kq="";
            ArrayList<String> mangcuahang = new ArrayList<String>();


            try {
                JSONArray mang = new JSONArray(s);

                for(int i=0;i<mang.length();i++)
                {
                    JSONObject son = mang.getJSONObject(i);
                    cuahang a= new cuahang();
                    a.CuaHangGuid=son.getString("CuaHangGuid");
                    a.KhuVucGuid=son.getString("KhuVucGuid");
                    a.TenCuaHang=son.getString("TenCuaHang");
                    a.KhuVucGuid=son.getString("KhuVucGuid");
                    a.DiaChi=son.getString("DiaChi");
                    a.SoDienThoai=son.getString("SoDienThoai");
                    a.CapDo=son.getString("CapDo");
                    ch.add(a);
                    mangcuahang.add(son.getString("TenCuaHang"));
                    kq =kq + son.getString("TenCuaHang");
                }
                ArrayAdapter<String> adapter = new  ArrayAdapter<String>(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        mangcuahang

                );

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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

        ArrayList<cuahang> mangcuahang2 = new ArrayList<cuahang>();

        if(a!=0) {
            int k = a-1 ;
            Log.d("spin ", " "+ ch.size());
            Log.d("spin ", " "+ kv.size());
            Log.d("spin ", " "+ k);
            for (int i = 0; i < ch.size(); i++) {

                if (ch.get(i).KhuVucGuid.toString().equals(kv.get(k).KhuVucGuid.toString())) {

                    mangcuahang2.add(new cuahang(ch.get(i).TenCuaHang, ch.get(i).DiaChi));
                    Log.d("spin ", "" + ch.get(i).TenCuaHang);
                    Log.d("spin ", "" + ch.get(i).DiaChi);

                } else {
                    Log.d("spin ", "không trùng");

                }
            }
        }
        else
        {
            for (int i = 0; i < ch.size(); i++)
            {

                mangcuahang2.add(new cuahang(ch.get(i).TenCuaHang, ch.get(i).DiaChi));
                Log.d("spin ", "" + ch.get(i).TenCuaHang);
                Log.d("spin ", "" + ch.get(i).DiaChi);

            }
        }

//        ArrayAdapter<String> adapter = new  ArrayAdapter<String>(
//                MainActivity.this,
//                android.R.layout.simple_list_item_1,
//                mangcuahang1
//
//        );
        Log.d("k1 ", "ok");
        ListAdaptercuahang adapter1 = new ListAdaptercuahang(
                MainActivity.this, R.layout.activity_dongcuahang,mangcuahang2
        );
        Log.d("k2 ", "ok");


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



    void vaomanhinhthongke(int cuahang)
    {
        Intent mh2 = new Intent(MainActivity.this,ChartthongkeActivity.class);
        mh2.putExtra("tench",ch.get(cuahang).TenCuaHang.toString());
        mh2.putExtra("mach",ch.get(cuahang).CuaHangGuid.toString());
        finish();
        startActivity(mh2);

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

