package com.example.administrator.quanlyshopdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.quanlyshopdemo.R;
import com.example.administrator.quanlyshopdemo.doituong.cuahang;

import java.util.List;

/**
 * Created by Administrator on 11/11/2016.
 */
public class ListAdaptercuahang extends ArrayAdapter<cuahang> {

    //    Context context;
    public ListAdaptercuahang(Context context, int resource, List<cuahang> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            view =  vi.inflate(R.layout.activity_dongcuahang, null);
        }
        cuahang p = getItem(position);
        if (p != null) {
            // Anh xa + Gan gia tri
            TextView ten = (TextView) view.findViewById(R.id.tvTencuahangdongcuahang);
            ten.setText(p.TenCuaHang);


            TextView diachi = (TextView) view.findViewById(R.id.tvDiachidongcuahang);
            diachi.setText(p.DiaChi);

//            TextView gia = (TextView) view.findViewById(R.id.tvGia);
//            gia.setText(String.valueOf(p.GiaTien)+" VNĐ");
//
//            ImageView hinh =(ImageView)view.findViewById(R.id.ivIcon);
//            String a= "http://shopdh.somee.com"+p.HinhAnh;
//            Log.d("khu vuc ", "" + a);
//            Picasso.with(getContext()).load(a).into(hinh);




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

