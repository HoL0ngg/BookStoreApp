package com.bookstore.DTO;

import java.util.Date;

public class PhieuTraDTO {
    private int MaPhieuTra;
    private Date NgayTra;
    private String MaNV;
    private String MaDocGia;
    private int MaPhieuMuon;
    private int status;

    // Constructor
    public PhieuTraDTO(int MaPhieuTra, Date NgayTra, String MaNV, String MaDocGia, int MaPhieuMuon, int status) {
        this.MaPhieuTra = MaPhieuTra;
        this.NgayTra = NgayTra;
        this.MaNV = MaNV;
        this.MaDocGia = MaDocGia;
        this.MaPhieuMuon = MaPhieuMuon;
        this.status = status;
    }

    // Getters Setters

    public PhieuTraDTO() {
        // TODO Auto-generated constructor stub
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMaPhieuTra() {
        return MaPhieuTra;
    }

    public void setMaPhieuTra(int maPhieuTra) {
        MaPhieuTra = maPhieuTra;
    }

    public Date getNgayTra() {
        return NgayTra;
    }

    public void setNgayTra(Date ngayTra) {
        NgayTra = ngayTra;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getMaDocGia() {
        return MaDocGia;
    }

    public void setMaDocGia(String maDocGia) {
        MaDocGia = maDocGia;
    }

    public int getMaPhieuMuon() {
        return MaPhieuMuon;
    }

    public void setMaPhieuMuon(int maPhieuMuon) {
        MaPhieuMuon = maPhieuMuon;
    }

}
