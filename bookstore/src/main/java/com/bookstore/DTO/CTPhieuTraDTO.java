package com.bookstore.DTO;

public class CTPhieuTraDTO {
    private int MaCTPhieuTra;
    private int TrangThai;
    private int MaPhieuTra;
    private int MaSach;

    // Constructor
    public CTPhieuTraDTO(int MaCTPhieuTra, int TrangThai, int MaPhieuTra, int MaSach){
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

    public int getMaSach() {
        return MaSach;
    }

    public void setMaSach(int maSach) {
        MaSach = maSach;
    }

}
