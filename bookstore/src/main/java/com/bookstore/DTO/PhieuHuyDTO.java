package com.bookstore.DTO;

import java.time.LocalDate;

public class PhieuHuyDTO {
    private String MaPhieuHuy;
    private LocalDate NgayTao;
    private String MaNhanVien;
    private int TrangThai;

    public static final int CHO_DUYET = 0;
    public static final int DA_DUYET = 1;
    public static final int TU_CHOI = 2;

    public PhieuHuyDTO(String maPhieuHuy, LocalDate ngayTao, String maNhanVien, int trangThai) {
        MaPhieuHuy = maPhieuHuy;
        NgayTao = ngayTao;
        MaNhanVien = maNhanVien;
        TrangThai = trangThai;
    }

    public String getMaPhieuHuy() {
        return MaPhieuHuy;
    }

    public void setMaPhieuHuy(String maPhieuHuy) {
        MaPhieuHuy = maPhieuHuy;
    }

    public LocalDate getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        NgayTao = ngayTao;
    }

    public String getMaNhanVien() {
        return MaNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        MaNhanVien = maNhanVien;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }
}