package com.bookstore.BUS;

import java.util.List;

import com.bookstore.DTO.CTPhieuNhapDTO;
import com.bookstore.DTO.PhieuNhapDTO;
import com.bookstore.dao.CTPhieuNhapDAO;
import com.bookstore.dao.PhieuNhapDAO;
import com.bookstore.DTO.TaiKhoanDTO;

public class PhieuNhapBUS {
    private List<PhieuNhapDTO> listpn;

    public PhieuNhapBUS() {
        listpn = new PhieuNhapDAO().layDanhSachPhieuNhap();
    }

    @SuppressWarnings("unused")
    private List<PhieuNhapDTO> getList() {
        return listpn;
    }

    public boolean themPhieuNhap(PhieuNhapDTO PhieuNhap, List<CTPhieuNhapDTO> list) {
        listpn.add(PhieuNhap);
        if (new PhieuNhapDAO().themPhieuNhap(PhieuNhap, list)) {
            return true;
        } else
            return false;
    }

    public boolean xoaPhieuNhap(String maPhieuNhap) {
        if (new PhieuNhapDAO().xoaPhieuNhap(maPhieuNhap)){
            return true;
        }
        return false;
    }

    public List<CTPhieuNhapDTO> getChiTietPhieuNhap(String mpn) {
        List<CTPhieuNhapDTO> ds = new CTPhieuNhapDAO().layDanhSachCTPhieuNhap();
        return ds;
    }

    // hàm sửa phiếu nhập ( nhân viên chỉ được sửa khi phiếu nhập chưa duyệt )
    public boolean suaPhieuNhap(PhieuNhapDTO phieuNhap) {
        if (phieuNhap.getTrangThai() == 0) {
            System.out.println("phieu nhap chua duyet va ng sua la nv");
            if (new PhieuNhapDAO().suaThongTinPhieuNhap(phieuNhap)) {
                return true;
            }
            return false;
        }
        System.out.println("Phieu nhap da duyet -> ql moi dc sua");
        for (PhieuNhapDTO pn : listpn) {
            if (pn.getMaPhieuNhap().equals(phieuNhap.getMaPhieuNhap())) {
                pn.setTrangThai(phieuNhap.getTrangThai());
                break;
            }
        }
        if (new PhieuNhapDAO().suaPhieuNhap(phieuNhap)) {
            return true;
        }
        return false;
    }

}
