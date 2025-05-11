package com.bookstore.DTO;

public class PhanQuyenDTO {
    private int MaNhomQuyen;
    private String TenNhomQuyen;
    private int TrangThai;

    public PhanQuyenDTO() {
    }

    public int getMaNhomQuyen() {
        return MaNhomQuyen;
    }

    public void setMaNhomQuyen(int maNhomQuyen) {
        MaNhomQuyen = maNhomQuyen;
    }

    public String getTenNhomQuyen() {
        return TenNhomQuyen;
    }

    public void setTenNhomQuyen(String tenNhomQuyen) {
        TenNhomQuyen = tenNhomQuyen;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

}
