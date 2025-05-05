package com.bookstore.DTO;

public class CTPhieuNhapDTO {
    private String MaPhieuNhap;
    private String MaDauSach;
    private int SoLuong;
    private boolean status;

    public CTPhieuNhapDTO(String MaPhieuNhap, String MaDauSach, int SoLuong){
        this.MaPhieuNhap = MaPhieuNhap;
        this.MaDauSach = MaDauSach;
        this.SoLuong = SoLuong;
    }

    public String getMaPhieuNhap() {
        return MaPhieuNhap;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        MaPhieuNhap = maPhieuNhap;
    }

    public String getMaDauSach() {
        return MaDauSach;
    }

    public void setMaDauSach(String maDauSach) {
        MaDauSach = maDauSach;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    } 

    
}
