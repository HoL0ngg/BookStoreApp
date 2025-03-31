package com.bookstore.DTO;

import java.time.LocalDate;

public class SachDTO {
    private int MaSach;
    private String TrangThai;
    private int MaDauSach;
    private LocalDate NgayNhap;

    public SachDTO(int MaSach, String TrangThai, int MaDauSach) {
        this.MaSach = MaSach;
        this.TrangThai = TrangThai;
        this.MaDauSach = MaDauSach;
    }

    public SachDTO(int maSach, String trangThai, int maDauSach, LocalDate ngayNhap) {
        MaSach = maSach;
        TrangThai = trangThai;
        MaDauSach = maDauSach;
        NgayNhap = ngayNhap;
    }

    public SachDTO(int maSach, String trangThai, LocalDate ngayNhap) {
        MaSach = maSach;
        TrangThai = trangThai;
        NgayNhap = ngayNhap;
    }

    public int getMasach() {
        return MaSach;
    }

    public void setMasach(int MaSach) {
        this.MaSach = MaSach;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public int getMaDauSach() {
        return MaDauSach;
    }

    public void setMaDauSach(int MaDauSach) {
        this.MaDauSach = MaDauSach;
    }

    public int getMaSach() {
        return MaSach;
    }

    public void setMaSach(int maSach) {
        MaSach = maSach;
    }

    public LocalDate getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(LocalDate ngayNhap) {
        NgayNhap = ngayNhap;
    }

}