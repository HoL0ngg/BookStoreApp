package com.bookstore.DTO;

import java.time.LocalDate;

public class SachDTO {
    private String MaSach;
    private String TrangThai;
    private String MaDauSach;
    private LocalDate NgayNhap;

    public SachDTO(String MaSach, String TrangThai, String MaDauSach) {
        this.MaSach = MaSach;
        this.TrangThai = TrangThai;
        this.MaDauSach = MaDauSach;
    }

    public SachDTO(String maSach, String trangThai, String maDauSach, LocalDate ngayNhap) {
        MaSach = maSach;
        TrangThai = trangThai;
        MaDauSach = maDauSach;
        NgayNhap = ngayNhap;
    }

    public SachDTO(String maSach, String trangThai, LocalDate ngayNhap) {
        MaSach = maSach;
        TrangThai = trangThai;
        NgayNhap = ngayNhap;
    }

    public String getMasach() {
        return MaSach;
    }

    public void setMasach(String MaSach) {
        this.MaSach = MaSach;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public String getMaDauSach() {
        return MaDauSach;
    }

    public void setMaDauSach(String MaDauSach) {
        this.MaDauSach = MaDauSach;
    }

    public String getMaSach() {
        return MaSach;
    }

    public void setMaSach(String maSach) {
        MaSach = maSach;
    }

    public LocalDate getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(LocalDate ngayNhap) {
        NgayNhap = ngayNhap;
    }

}