package com.bookstore.DTO;

public class SachDTO {
    private int MaSach;
    private String TrangThai;
    private int MaDauSach;

    public SachDTO(int MaSach, String TrangThai, int MaDauSach) {
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

}