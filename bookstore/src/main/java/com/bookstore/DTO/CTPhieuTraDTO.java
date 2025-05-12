package com.bookstore.DTO;

public class CTPhieuTraDTO {
    private int TrangThai;
    private int MaPhieuTra;
    private String MaSach;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // Constructor
    public CTPhieuTraDTO(int TrangThai, int MaPhieuTra, String MaSach, int status){
        this.TrangThai = TrangThai;
        this.MaPhieuTra = MaPhieuTra;
        this.MaSach = MaSach;
        this.status = status;
    }

    // Getters Setters
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
