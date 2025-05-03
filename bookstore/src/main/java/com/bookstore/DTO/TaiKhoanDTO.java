package com.bookstore.DTO;

import com.bookstore.dao.TaiKhoanDAO;

public class TaiKhoanDTO {
    private String TenDangNhap;
    private String MatKhau;
    private int TrangThai;
    private String Email;
    private int MaNhomQuyen;

    public TaiKhoanDTO() {
    }

    public TaiKhoanDTO(String TenDangNhap, String MatKhau,
            int TrangThai, String Email, int MaNhomQuyen) {

        this.TenDangNhap = TenDangNhap;
        this.MatKhau = MatKhau;
        this.TrangThai = TrangThai;
        this.Email = Email;
        this.MaNhomQuyen = MaNhomQuyen;

    }

    public String getTenDangNhap() {
        return TenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        TenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getMaNhomQuyen() {
        return MaNhomQuyen;
    }

    public void setMaNhomQuyen(int maNhomQuyen) {
        MaNhomQuyen = maNhomQuyen;
    }

}
