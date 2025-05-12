package com.bookstore.DTO;

import java.util.Calendar;
import java.util.Date;

public class PhieuMuonDTO {
    
    private int MaPhieuMuon;
    private Date NgayMuon;
    private Date NgayTraDuKien;
    private int TrangThai;
    private String MaDocGia;
    private String MaNhanVien;
    private Boolean Status;

    public PhieuMuonDTO(int MaPhieuMuon, Date NgayMuon, Date NgayTraDuKien, int TrangThai,
                    String MaDocGia, String MaNhanVien, Boolean status){
        this.MaPhieuMuon = MaPhieuMuon;
        this.NgayMuon = NgayMuon;
        this.NgayTraDuKien = NgayTraDuKien;
        this.TrangThai = TrangThai;
        this.MaDocGia = MaDocGia;
        this.MaNhanVien = MaNhanVien;
        this.Status = status;
    }

    public int getMaPhieuMuon() {
        return MaPhieuMuon;
    }

    public void setMaPhieuMuon(int maPhieuMuon) {
        MaPhieuMuon = maPhieuMuon;
    }

    public Date getNgayMuon() {
        return NgayMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        NgayMuon = ngayMuon;
        setNgayTraDuKien(ngayMuon);
    }

    public Date getNgayTraDuKien() {
        return NgayTraDuKien;
    }

    private void setNgayTraDuKien(Date ngayMuon) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(ngayMuon);
        cal.add(Calendar.DAY_OF_MONTH, 15);
        this.NgayTraDuKien = cal.getTime(); 
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

    public String getMaNhanVien() {
        return MaNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        MaNhanVien = maNhanVien;
    }

    public boolean getStatus(){
        return Status;
    }

    public void setStatus(boolean status){
        Status = status;
    }

}
