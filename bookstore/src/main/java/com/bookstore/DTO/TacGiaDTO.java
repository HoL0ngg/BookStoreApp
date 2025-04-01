package com.bookstore.DTO;

public class TacGiaDTO {
    private int MaTacGia;
    private String TenTacGia;
    private int NamSinh;
    private String QuocTich;
    
    public TacGiaDTO(int maTacGia, String tenTacGia, int namSinh, String quocTich) {
        MaTacGia = maTacGia;
        TenTacGia = tenTacGia;
        NamSinh = namSinh;
        QuocTich = quocTich;
    }
    public int getMaTacGia() {
        return MaTacGia;
    }
    public void setMaTacGia(int maTacGia) {
        MaTacGia = maTacGia;
    }
    public String getTenTacGia() {
        return TenTacGia;
    }
    public void setTenTacGia(String tenTacGia) {
        TenTacGia = tenTacGia;
    }
    public int getNamSinh() {
        return NamSinh;
    }
    public void setNamSinh(int namSinh) {
        NamSinh = namSinh;
    }
    public String getQuocTich() {
        return QuocTich;
    }
    public void setQuocTich(String quocTich) {
        QuocTich = quocTich;
    }
}

