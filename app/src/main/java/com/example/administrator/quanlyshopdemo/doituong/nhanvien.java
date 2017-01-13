package com.example.administrator.quanlyshopdemo.doituong;

/**
 * Created by Administrator on 11/11/2016.
 */
public class nhanvien {

  public   String NhanVienGuid;
    public String ChucVuGuid;
    public  String CuaHangGuid;
    public String TenNhanVien;
    public String TenDangNhap;
    public String MatKhau;
    public String DiaChi;
    public String SoDienThoai;

  public nhanvien()
  {
  }

  public nhanvien(String a,String b)
  {
    TenNhanVien=a;
    DiaChi=b;

  }

  public nhanvien(String a,String b,String c)
  {
    TenNhanVien=a;
    DiaChi=b;
    SoDienThoai=c;
  }
}
