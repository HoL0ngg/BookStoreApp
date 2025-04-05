package com.bookstore.DTO;

public class TacGiaDTO {
    private String MaTacGia;
    private String TenTacGia;
    private int NamSinh;
    private String QuocTich;
    
    public TacGiaDTO(String maTacGia, String tenTacGia, int namSinh, String quocTich) {
        MaTacGia = maTacGia;
        TenTacGia = tenTacGia;
        NamSinh = namSinh;
        QuocTich = quocTich;
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

    public String getMaTacGia() {
        return MaTacGia;
    }

    public void setMaTacGia(String maTacGia) {
        MaTacGia = maTacGia;
    }

    @Override
    public String toString() {
        return "TacGia{" + "maTG=" + MaTacGia + ", tenTG=" + TenTacGia + ", namsinh=" + NamSinh + ", quoctich=" + QuocTich + '}';
    }
}

