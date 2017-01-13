package com.example.administrator.quanlyshopdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.quanlyshopdemo.R;
import com.example.administrator.quanlyshopdemo.doituong.chitiethoadon;

import java.util.List;

/**
 * Created by Administrator on 11/25/2016.
 */
public class ListAdapterchitiethoadon extends ArrayAdapter<chitiethoadon> {


    //    Context context;
    public ListAdapterchitiethoadon(Context context, int resource, List<chitiethoadon> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            view =  vi.inflate(R.layout.activity_dongchitiethoadon, null);
        }
        chitiethoadon p = getItem(position);
        if (p != null) {
            TextView tensanpham = (TextView) view.findViewById(R.id.txtsanphamchitiethoadon);
            tensanpham.setText(p.TenSanPham);


            TextView soluong = (TextView) view.findViewById(R.id.txtsoluongchitiethoadon);
            soluong.setText(p.SoLuongMua);


            TextView gia = (TextView) view.findViewById(R.id.txtgiatienchitiethoadon);
            gia.setText(p.DonGia+"VNĐ");


        }
        return view;
    }
}


