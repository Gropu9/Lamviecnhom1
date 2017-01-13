package com.example.administrator.quanlyshopdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.quanlyshopdemo.R;
import com.example.administrator.quanlyshopdemo.doituong.hoadon;

import java.util.List;

/**
 * Created by Administrator on 11/11/2016.
 */
public class ListAdapterhoadon extends ArrayAdapter<hoadon> {

    //    Context context;
    public ListAdapterhoadon(Context context, int resource, List<hoadon> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            view =  vi.inflate(R.layout.activity_donghoadon, null);
        }
        hoadon p = getItem(position);
        if (p != null) {

            TextView shd = (TextView) view.findViewById(R.id.txtshddonghoadon);
            shd.setText(p.STT);


            TextView cuahang = (TextView) view.findViewById(R.id.txtcuahangdonghoadon);
            cuahang.setText(p.TenCuaHang);

            TextView khachhang = (TextView) view.findViewById(R.id.txtkhachhangdonghoadon);
            khachhang.setText(p.TenKhachHang);

            TextView ngaynhap = (TextView) view.findViewById(R.id.txtngaynhapdonghoadon);
            String thangn=p.NgayNhap.substring(5,7);
            String namn=p.NgayNhap.substring(0,4);
            String ngayn=p.NgayNhap.substring(8,10);
            String thoigiann=ngayn+"/"+thangn+"/"+namn;
            ngaynhap.setText(thoigiann);

            TextView ngaygiao = (TextView) view.findViewById(R.id.txtngaygiaodonghoadon);
            String thangg=p.NgayGiao.substring(5,7);
            String namg=p.NgayGiao.substring(0,4);
            String ngayg=p.NgayGiao.substring(8,10);
            String thoigiang=ngayg+"/"+thangg+"/"+namg;
            ngaygiao.setText(thoigiann);

            TextView gia = (TextView) view.findViewById(R.id.txttiendonghoadon);
            gia.setText(p.GiaTien+ "VNĐ");

            TextView thanhtoan = (TextView) view.findViewById(R.id.txtdathanhtoandonghoadon);
            thanhtoan.setText(p.ThanhToan);

        }
        return view;
    }

}
