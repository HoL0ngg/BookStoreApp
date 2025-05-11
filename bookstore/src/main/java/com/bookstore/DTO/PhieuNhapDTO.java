package com.bookstore.DTO;

import java.sql.Date;

public class PhieuNhapDTO {
    private String MaPhieuNhap;
    private Date thoigian;
    private String MaNV;
    private int MaNCC;
    private int TrangThai;
    private int status;

    public PhieuNhapDTO(String MaPhieuNhap, Date thoigian, String MaNV, int MaNCC, int status,int TrangThai) {
        this.MaPhieuNhap = MaPhieuNhap;
        this.thoigian = thoigian;
        this.MaNV = MaNV;
        this.MaNCC = MaNCC;
        this.status = status;
        this.TrangThai = TrangThai;
    }

    public String getMaPhieuNhap() {
        return MaPhieuNhap;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        MaPhieuNhap = maPhieuNhap;
    }

    public Date getThoigian() {
        return thoigian;
    }

    public void setThoigian(Date thoigian) {
        this.thoigian = thoigian;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public int getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(int maNCC) {
        MaNCC = maNCC;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
