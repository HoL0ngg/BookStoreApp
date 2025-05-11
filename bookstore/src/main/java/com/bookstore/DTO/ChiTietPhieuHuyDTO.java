package com.bookstore.DTO;

public class ChiTietPhieuHuyDTO {
    private String MaPhieuHuy;
    private String MaSach;
    private String GhiChu;


    
    public ChiTietPhieuHuyDTO(String maPhieuHuy, String maSach, String ghiChu) {
        MaPhieuHuy = maPhieuHuy;
        MaSach = maSach;
        GhiChu = ghiChu;
    }
    public ChiTietPhieuHuyDTO(String maPhieu, String maSach2) {
        //TODO Auto-generated constructor stub
    }
    public String getMaPhieuHuy() {
        return MaPhieuHuy;
    }
    public void setMaPhieuHuy(String maPhieuHuy) {
        MaPhieuHuy = maPhieuHuy;
    }
    public String getMaSach() {
        return MaSach;
    }
    public void setMaSach(String maSach) {
        MaSach = maSach;
    }
    public String getGhiChu() {
        return GhiChu;
    }
    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

} 
    
    

