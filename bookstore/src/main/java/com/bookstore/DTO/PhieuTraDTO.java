package com.bookstore.DTO;

import java.util.Date;

public class PhieuTraDTO {
    private int MaPhieuTra;
    private Date NgayTra;
    private int MaNV;
    private int MaDocGia;
    private int MaPhieuMuon;

    // Constructor
    public PhieuTraDTO(int MaPhieuTra, Date NgayTra, int MaNV, int MaDocGia, int MaPhieuMuon){
        this.MaPhieuTra = MaPhieuTra;
        this.NgayTra = NgayTra;
        this.MaNV = MaNV;
        this.MaDocGia = MaDocGia;
        this.MaPhieuMuon = MaPhieuMuon;
    }

    // Getters Setters
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

    public int getMaNV() {
        return MaNV;
    }

    public void setMaNV(int maNV) {
        MaNV = maNV;
    }

    public int getMaDocGia() {
        return MaDocGia;
    }

    public void setMaDocGia(int maDocGia) {
        MaDocGia = maDocGia;
    }

    public int getMaPhieuMuon() {
        return MaPhieuMuon;
    }

    public void setMaPhieuMuon(int maPhieuMuon) {
        MaPhieuMuon = maPhieuMuon;
    }

    
}
