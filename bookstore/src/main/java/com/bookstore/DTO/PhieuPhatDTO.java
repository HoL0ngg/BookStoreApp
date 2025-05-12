package com.bookstore.DTO;

import java.util.Date;

public class PhieuPhatDTO {
    private int MaPhieuPhat;
    private int TienPhat;
    private Date NgayPhat;
    private int TrangThai;
    private String MaDocGia;
    private int MaCTPhieuTra;
    private int status;

    public PhieuPhatDTO(int MaPhieuPhat, int TienPhat, Date NgayPhat, int TrangThai, String MaDocGia,
            int MaCTPhieuTra, int status) {
        this.MaPhieuPhat = MaPhieuPhat;
        this.TienPhat = TienPhat;
        this.NgayPhat = NgayPhat;
        this.TrangThai = TrangThai;
        this.MaDocGia = MaDocGia;
        this.MaCTPhieuTra = MaCTPhieuTra;
        this.status = status;
    }

    public int getMaPhieuPhat() {
        return MaPhieuPhat;
    }

    public void setMaPhieuPhat(int maPhieuPhat) {
        MaPhieuPhat = maPhieuPhat;
    }

    public int getTienPhat() {
        return TienPhat;
    }

    public void setTienPhat(int tienPhat) {
        TienPhat = tienPhat;
    }

    public Date getNgayPhat() {
        return NgayPhat;
    }

    public void setNgayPhat(Date ngayPhat) {
        NgayPhat = ngayPhat;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

    public String getMaDocGia() {
        return MaDocGia;
    }

    public void setMaDocGia(String maDocGia) {
        MaDocGia = maDocGia;
    }

    public int getMaCTPhieuTra() {
        return MaCTPhieuTra;
    }

    public void setMaCTPhieuTra(int maCTPhieuTra) {
        MaCTPhieuTra = maCTPhieuTra;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
