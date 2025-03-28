package com.bookstore.DTO;

public class Sach {
    private int MaSach;
    private int TrangThai;
    private int MaDauSach;

    public Sach(int MaSach, int TrangThai, int MaDauSach) {
        this.MaSach = MaSach;
        this.TrangThai = TrangThai;
        this.MaDauSach = MaDauSach;
    }

    public int getMasach() {
        return MaSach;
    }

    public void setMasach(int MaSach) {
        this.MaSach = MaSach;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public int getMaDauSach() {
        return MaDauSach;
    }

    public void setMaDauSach(int MaDauSach) {
        this.MaDauSach = MaDauSach;
    }

}