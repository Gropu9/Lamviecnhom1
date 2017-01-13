package com.example.administrator.quanlyshopdemo.doituong;

/**
 * Created by Administrator on 11/9/2016.
 */
public class sanpham {
    public String SanPhamGuid;
    public String CuaHangGuid;
    public String LoaiSanPhamGuid;
    public  String TenSanPham;
    public  Integer GiaTien;
    public  String MoTa;
    public  String ChatLieu;
    public  String MauSac;
    public String HinhAnh;

    public sanpham() {

    }


    public sanpham(String tenSanPham, Integer giaTien, String hinhAnh) {
        TenSanPham = tenSanPham;
        GiaTien = giaTien;
        HinhAnh = hinhAnh;
    }
}
