package com.bookstore.DTO;

public class TheLoaiDTO {
    private String MaTheLoai;
    private String TenTheLoai;

    public TheLoaiDTO() {
    }

    public TheLoaiDTO(String maTheLoai, String tenTheLoai) {
        MaTheLoai = maTheLoai;
        TenTheLoai = tenTheLoai;
    }

    public String getMaTheLoai() {
        return MaTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        MaTheLoai = maTheLoai;
    }

    public String getTenTheLoai() {
        return TenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        TenTheLoai = tenTheLoai;
    }

}
