package com.example.administrator.quanlyshopdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.quanlyshopdemo.doituong.sanpham;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 11/10/2016.
 */
public class ListAdapter extends ArrayAdapter<sanpham> {

//    Context context;
    public ListAdapter(Context context, int resource, List<sanpham> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            view =  vi.inflate(R.layout.activity_dongsanpham, null);
        }
        sanpham p = getItem(position);
        if (p != null) {
            // Anh xa + Gan gia tri
            TextView ten = (TextView) view.findViewById(R.id.tvTenSP);
            ten.setText(p.TenSanPham);

            TextView gia = (TextView) view.findViewById(R.id.tvGia);
            gia.setText(String.valueOf(p.GiaTien)+" VNĐ");

            ImageView hinh =(ImageView)view.findViewById(R.id.ivIcon);
            String a= "http://hdshop.somee.com"+p.HinhAnh;
            Log.d("khu vuc ", "" + a);
            Picasso.with(getContext()).load(a).into(hinh);




////            hinh.setImageResource(R.drawable.h2);
//
//            Context context = hinh.getContext();

//            String a= b.substring(15);
////            String a= p.HinhAnh.substring(15);
////            int id = context.getResources().getIdentifier(a, "drawable", context.getPackageName());
////            hinh.setImageResource(id);
//
//            Resources res = hinh.getResources();
//            String mDrawableName = "logo_default";
//            int resID = res.getIdentifier(a , "drawable", "quanlyshopdemo");
//            Drawable drawable = res.getDrawable(resID );
//            hinh.setImageDrawable(drawable );
        }
        return view;
    }

}