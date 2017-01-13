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
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.quanlyshopdemo.doituong.hoadon;
import com.example.administrator.quanlyshopdemo.doituong.thongke;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Administrator on 11/27/2016.
 */
public class ChartthongkeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

  public   ArrayList<thongke> hd1= new  ArrayList<thongke>();
    ArrayList<hoadon> manghdtheoloai= new  ArrayList<hoadon>();
    FrameLayout layoutthongke;
    Spinner spin,spinthang,spinnam;
    ArrayList<String> mangloaithongke = new ArrayList<String>();
    ArrayList<String> mangdanhmuc = new ArrayList<String>();
    ArrayList<String> tencuahang = new ArrayList<String>();
    ArrayList<Integer> doanhthuthang = new ArrayList<Integer>();
    TextView txtthangthongke;
    ArrayList<String> mangthang = new ArrayList<String>();
    ArrayList<String> mangnam = new ArrayList<String>();

    String tench=null;
    String mach=null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainchartthongke);
        Bundle bd =getIntent().getExtras();
        if(bd!=null)
        {
            tench = bd.getString("tench");
            mach = bd.getString("mach");
        }





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





        layoutthongke = (FrameLayout) findViewById(R.id.layoutthongke);
      //  spinthang=(Spinner) findViewById(R.id.spinthangthongke);
        spinnam=(Spinner) findViewById(R.id.spinnamthongke);
        loaddanhmucnamthang();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new ReadJSON1().execute("http://hdshop.somee.com/api/CauHinhhoadon/GET_Listhoadon");

            }
        });


        spinnam.setOnItemSelectedListener(new MyProcessEvent());



    }

    private class MyProcessEvent implements
            AdapterView.OnItemSelectedListener {
        //Khi có chọn lựa thì vào hàm này
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            //arg2 là phần tử được chọn trong data source
         loadgiatritrongspinnam(arg2);
        }

        //Nếu không chọn gì cả
        public void onNothingSelected(AdapterView<?> arg0) {

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

//           Toast.makeText(ChartthongkeActivity.this,s,Toast.LENGTH_LONG).show();
            JSONObject root = null;
            String kq="";
            ArrayList<String> mangcuahang = new ArrayList<String>();
            int stt=0;

            try {
                JSONArray mang = new JSONArray(s);

                for(int i=0;i<mang.length();i++)
                {
                    JSONObject son = mang.getJSONObject(i);
                    thongke a= new thongke();
                    a.HoaDonGuid=son.getString("HoaDonGuid");
                    kq=son.getString("HoaDonGuid");
                    a.STT=String.valueOf(i+1);
                    a.TenCuaHang=son.getString("TenCuaHang");
                    a.TenKhachHang=son.getString("TenKhachHang");
                    String tam= son.getString("NgayNhap").substring(0,10);
                    a.NgayNhap=tam;

                    tam= son.getString("NgayGiao").substring(0,10);
                    a.NgayGiao=tam;
                    a.GiaTien=son.getInt("GiaTien");
                    Log.d("gia tien",""+a.GiaTien);
                    a.ThanhToan=son.getString("ThanhToan");
                    hd1.add(a);
                    Log.d("gia tien 2",""+hd1.get(i).GiaTien);
                }

//                ListAdapterhoadon adapter1=new  ListAdapterhoadon( ChartthongkeActivity.this,
//                        R.layout.activity_donghoadon,
//                        hd);
//
//
//
//                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);




            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("cửa hàng ", "" + kq);
            for (int i = 0; i < hd1.size(); i++) {
                Log.d("thong ke", ""+hd1.get(i).GiaTien);

            }

            dulieuthongke();
            loaddanhmucnamthang();
            if(kq.equals(""))
            {

            }
            else {
                loadgiatritrongspinnam(0);
            }

            loadthongke();
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


    void loadthongke()
    {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i=0;i<doanhthuthang.size();i++)
        {
            entries.add(new BarEntry(doanhthuthang.get(i), i));
        }

        BarDataSet dataset = new BarDataSet(entries, "VNĐ");


        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Tháng 1");
        labels.add("Tháng 2");
        labels.add("Tháng 3");
        labels.add("Tháng 4");
        labels.add("Tháng 5");
        labels.add("Tháng 6");
        labels.add("Tháng 7");
        labels.add("Tháng 8");
        labels.add("Tháng 9");
        labels.add("Tháng 10");
        labels.add("Tháng 11");
        labels.add("Tháng 12");

        BarChart chart = new BarChart(this);
        layoutthongke.addView(chart);

        BarData data = new BarData(labels, dataset);
        chart.setData(data);

        chart.setDescription("");


    }

    int checktencuahang(String a)
    {
        for(int i=0;i<tencuahang.size();i++)
        {
            if(a.equals(tencuahang.get(i).toString()))
            {
                return 1;
            }
        }
        return 0;
    }

    void loaddanhmuc()
    {
//        mangdanhmuc.clear();
//
//        for(int i=0;i<hd1.size();i++)
//        {
//            if(checktencuahang(hd1.get(i).TenCuaHang.toString())==0)
//            {
//                tencuahang.add(hd1.get(i).TenCuaHang.toString());
//            }
//        }



//        mangdanhmuc.add("Theo Từng Năm");
//
//        ArrayAdapter<String> adapter = new  ArrayAdapter<String>(
//                ChartthongkeActivity.this,
//                android.R.layout.simple_list_item_1,
//                tencuahang
//
//        );
//
//
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spin.setAdapter(adapter);
//      loaddanhmucnamthang();


    }

    void loaddanhmucnamthang()
    {

        mangnam.clear();

        mangdanhmuc.add("2016");
        int tam=0;
        for(int i=0;i<hd1.size();i++)
        {
            if(tam!=Integer.parseInt(hd1.get(i).NgayGiao.substring(0,4).toString())) {
                tam=Integer.parseInt(hd1.get(i).NgayGiao.substring(0,4).toString());
                mangnam.add(hd1.get(i).NgayGiao.substring(0, 4));
            }

        }

        for(int i=1;i<=12;i++)
        {
            mangthang.add(String.valueOf(i).toString());
        }





        ArrayAdapter<String> adapternam = new  ArrayAdapter<String>(
                ChartthongkeActivity.this,
                android.R.layout.simple_list_item_1,
                mangnam

        );

        spinnam.setAdapter(adapternam);

    }


    void dulieuthongke() {


        int mot=0,hai=0,ba=0,bon=0,nam=0,sau=0,bay=0,tam=0,chin=0,muoi=0,muoimot=0,muoihai=0;
        int thang=0;
        int namk=0;
        for (int i = 0; i < hd1.size(); i++) {

            namk=Integer.parseInt(hd1.get(i).NgayGiao.substring(0,4));
            Log.d("test nam",""+namk);
            if(namk != namspin)
                continue;

            if(tench!=null)
            {
                if((hd1.get(i).TenCuaHang.equals(tench)))
                {

                }
                else
                {
                    continue;
                }

            }

         thang=Integer.parseInt(hd1.get(i).NgayGiao.substring(5,7));
            if(thang==1)
            {
                mot+=hd1.get(i).GiaTien;
            }
            else if (thang==2)
            {
                hai+=hd1.get(i).GiaTien;
            }
            else if (thang==3)
            {
                ba+=hd1.get(i).GiaTien;
            }

            else if (thang==4)
            {
                bon+=hd1.get(i).GiaTien;
            }

            else if (thang==5)
            {
                nam+=hd1.get(i).GiaTien;
            }

            else if (thang==6)
            {
                sau+=hd1.get(i).GiaTien;
            }

            else if (thang==7)
            {
                bay+=hd1.get(i).GiaTien;
            }

            else if (thang==8)
            {
                tam+=hd1.get(i).GiaTien;
            }

            else if (thang==9)
            {
                chin+=hd1.get(i).GiaTien;
            }

            else if (thang==10)
            {
                muoi+=hd1.get(i).GiaTien;
            }

            else if (thang==11)
            {
                muoimot+=hd1.get(i).GiaTien;
            }

            else if (thang==12)
            {
                muoihai+=hd1.get(i).GiaTien;
            }
        }

        doanhthuthang.add(mot);
        doanhthuthang.add(hai);
        doanhthuthang.add(ba);
        doanhthuthang.add(bon);
        doanhthuthang.add(nam);
        doanhthuthang.add(sau);
        doanhthuthang.add(bay);
        doanhthuthang.add(tam);
        doanhthuthang.add(chin);
        doanhthuthang.add(muoi);
        doanhthuthang.add(muoimot);
        doanhthuthang.add(muoihai);
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

    int namspin=2016;

    void loadgiatritrongspinnam(int a)
    {

        namspin=Integer.parseInt(mangnam.get(a));

    }








}
