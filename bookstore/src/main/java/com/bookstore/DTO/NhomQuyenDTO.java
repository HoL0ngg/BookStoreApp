package com.bookstore.DTO;

public class NhomQuyenDTO {
    private int maNhomQuyen;
    private String tenNhomQuyen;
    private int trangThai;

    public NhomQuyenDTO() {
    }

    public NhomQuyenDTO(String tenNhomQuyen) {
        this.tenNhomQuyen = tenNhomQuyen;
    }

    public int getMaNhomQuyen() {
        return maNhomQuyen;
    }

    public void setMaNhomQuyen(int maNhomQuyen) {
        this.maNhomQuyen = maNhomQuyen;
    }

    public String getTenNhomQuyen() {
        return tenNhomQuyen;
    }

    public void setTenNhomQuyen(String tenNhomQuyen) {
        this.tenNhomQuyen = tenNhomQuyen;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

}
