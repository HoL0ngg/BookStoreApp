package com.bookstore.BUS;

import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.TaiKhoanDTO;
import com.bookstore.dao.TaiKhoanDAO;

public class TaiKhoanBUS {
    private List<TaiKhoanDTO> list;

    public boolean dangNhap(String tenDangNhap, String matKhau) {
        return new TaiKhoanDAO().kiemTraDangNhap(tenDangNhap, matKhau);
    }

    public TaiKhoanBUS() {
        this.list = new TaiKhoanDAO().getList();
    }

    public List<TaiKhoanDTO> getList() {
        return list;
    }

    public void add(TaiKhoanDTO taikhoan) {
        list.add(taikhoan);
        new TaiKhoanDAO().insert(taikhoan);
    }

    public void update(TaiKhoanDTO taikhoan) {
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).getTenDangNhap() == taikhoan.getTenDangNhap()) {
                list.set(i, taikhoan);
                break;
            }
        }
        new TaiKhoanDAO().update(taikhoan);
    }

    public void delete(String TenDangNhap) {
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).getTenDangNhap() == TenDangNhap) {
                list.remove(i);
                break;
            }
        }
        new TaiKhoanDAO().delete(TenDangNhap);
    }

    public List<TaiKhoanDTO> search(String txt, String filter) {
        List<TaiKhoanDTO> result = new ArrayList<>();
        for (TaiKhoanDTO taikhoan : list) {
            switch (filter) {
                case "Tất cả":
                    if (String.valueOf(taikhoan.getTenDangNhap()).toLowerCase().contains(txt.toLowerCase())
                            || taikhoan.getMatKhau().toLowerCase().contains(txt.toLowerCase())
                            || taikhoan.getEmail().toLowerCase().contains(txt.toLowerCase())) {
                        // || taikhoan.getSoDienThoai().toLowerCase().contains(txt.toLowerCase())) {
                        result.add(taikhoan);
                    }
                    break;
                case "Tên đăng nhập":
                    if (String.valueOf(taikhoan.getTenDangNhap()).toLowerCase().contains(txt.toLowerCase())) {
                        result.add(taikhoan);
                    }
                    break;
                case "Mật khẩu":
                    if (taikhoan.getMatKhau().toLowerCase().contains(txt.toLowerCase())) {
                        result.add(taikhoan);
                    }
                    break;
                case "Email":
                    if (taikhoan.getEmail().toLowerCase().contains(txt.toLowerCase())) {
                        result.add(taikhoan);
                    }
                    break;
            }
        }
        return result;
    }
}