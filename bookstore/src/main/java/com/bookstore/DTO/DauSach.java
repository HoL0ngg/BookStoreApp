package com.bookstore.DTO;

public class DauSach {
    private int MaDauSach;
    private String TenDauSach;
    private String HinhAnh;
    private String NhaXuatBan;
    private int NamXuatBan;
    private String NgonNgu;
    private int MaTheLoai;

    public DauSach(int maDauSach, String tenDauSach, String hinhAnh, String nhaXuatBan, int namXuatBan, String ngonNgu,
            int maTheLoai) {
        MaDauSach = maDauSach;
        TenDauSach = tenDauSach;
        HinhAnh = hinhAnh;
        NhaXuatBan = nhaXuatBan;
        NamXuatBan = namXuatBan;
        NgonNgu = ngonNgu;
        MaTheLoai = maTheLoai;
    }

    public int getMaDauSach() {
        return MaDauSach;
    }

    public void setMaDauSach(int maDauSach) {
        MaDauSach = maDauSach;
    }

    public String getTenDauSach() {
        return TenDauSach;
    }

    public void setTenDauSach(String tenDauSach) {
        TenDauSach = tenDauSach;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getNhaXuatBan() {
        return NhaXuatBan;
    }

    public void setNhaXuatBan(String nhaXuatBan) {
        NhaXuatBan = nhaXuatBan;
    }

    public int getNamXuatBan() {
        return NamXuatBan;
    }

    public void setNamXuatBan(int namXuatBan) {
        NamXuatBan = namXuatBan;
    }

    public String getNgonNgu() {
        return NgonNgu;
    }

    public void setNgonNgu(String ngonNgu) {
        NgonNgu = ngonNgu;
    }

    public int getMaTheLoai() {
        return MaTheLoai;
    }

    public void setMaTheLoai(int maTheLoai) {
        MaTheLoai = maTheLoai;
    }

}
