package com.bookstore.utils;

public class NguoiDungDangNhap {
    private static NguoiDungDangNhap instance;

    private String maNhanVien;
    private int maNhomQuyen;

    // Constructor private: không cho class khác new
    private NguoiDungDangNhap() {
    }

    // Lấy instance duy nhất
    public static NguoiDungDangNhap getInstance() {
        if (instance == null) {
            instance = new NguoiDungDangNhap();
        }
        return instance;
    }

    public void setThongTin(String maNV, int maQuyen) {
        this.maNhanVien = maNV;
        this.maNhomQuyen = maQuyen;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public int getMaNhomQuyen() {
        return maNhomQuyen;
    }

    public void clear() {
        maNhanVien = null;
        maNhomQuyen = 0;
        instance = null; // reset instance nếu cần
    }
}
