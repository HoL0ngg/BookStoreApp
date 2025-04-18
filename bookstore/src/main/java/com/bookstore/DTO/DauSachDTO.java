package com.bookstore.DTO;

import java.util.List;

public class DauSachDTO {
    private String MaDauSach;
    private String TenDauSach;
    private String HinhAnh;
    private String NhaXuatBan;
    private int NamXuatBan;
    private String NgonNgu;
    private int SoTrang;
    private List<TheLoaiDTO> listTheLoai;

    public DauSachDTO(String maDauSach, String tenDauSach, String hinhAnh, String nhaXuatBan, int namXuatBan,
            String ngonNgu, int soTrang) {
        MaDauSach = maDauSach;
        TenDauSach = tenDauSach;
        HinhAnh = hinhAnh;
        NhaXuatBan = nhaXuatBan;
        NamXuatBan = namXuatBan;
        NgonNgu = ngonNgu;
        SoTrang = soTrang;
    }

    public DauSachDTO(String maDauSach, String tenDauSach, String hinhAnh, String nhaXuatBan, int namXuatBan,
            String ngonNgu, int soTrang, List<TheLoaiDTO> listTheLoai) {
        MaDauSach = maDauSach;
        TenDauSach = tenDauSach;
        HinhAnh = hinhAnh;
        NhaXuatBan = nhaXuatBan;
        NamXuatBan = namXuatBan;
        NgonNgu = ngonNgu;
        SoTrang = soTrang;
        this.listTheLoai = listTheLoai;
    }

    public String getMaDauSach() {
        return MaDauSach;
    }

    public void setMaDauSach(String maDauSach) {
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

    public int getSoTrang() {
        return SoTrang;
    }

    public void setSoTrang(int soTrang) {
        SoTrang = soTrang;
    }

    public List<TheLoaiDTO> getListTheLoai() {
        return listTheLoai;
    }

    public void setListTheLoai(List<TheLoaiDTO> listTheLoai) {
        this.listTheLoai = listTheLoai;
    }

}
