package com.bookstore.DTO;

public class CTPhieuTraDTO {
    private int MaCTPhieuTra;
    private int TrangThai;
    private int MaPhieuTra;
    private String MaSach;

    // Constructor
    public CTPhieuTraDTO(int MaCTPhieuTra, int TrangThai, int MaPhieuTra, String MaSach){
        this.MaCTPhieuTra = MaCTPhieuTra;
        this.TrangThai = TrangThai;
        this.MaPhieuTra = MaPhieuTra;
        this.MaSach = MaSach;
    }

    // Getters Setters
    public int getMaCTPhieuTra() {
        return MaCTPhieuTra;
    }

    public void setMaCTPhieuTra(int maCTPhieuTra) {
        MaCTPhieuTra = maCTPhieuTra;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

    public int getMaPhieuTra() {
        return MaPhieuTra;
    }

    public void setMaPhieuTra(int maPhieuTra) {
        MaPhieuTra = maPhieuTra;
    }

    public String getMaSach() {
        return MaSach;
    }

    public void setMaSach(String maSach) {
        MaSach = maSach;
    }

}
