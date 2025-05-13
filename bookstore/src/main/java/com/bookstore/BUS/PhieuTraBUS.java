package com.bookstore.BUS;

import com.bookstore.dao.CTPhieuTraDAO;
import com.bookstore.dao.PhieuTraDAO;

import java.util.List;

import com.bookstore.DTO.CTPhieuTraDTO;
import com.bookstore.DTO.PhieuTraDTO;

public class PhieuTraBUS {
    private List<PhieuTraDTO> listpt;

    public PhieuTraBUS() {
        listpt = new PhieuTraDAO().layDanhSachPhieuTra();
    }

    public List<PhieuTraDTO> getList() {
        return listpt;
    }

    public boolean thempt(PhieuTraDTO pt, List<CTPhieuTraDTO> tmplist, int mpm) {
        listpt.add(pt);
        if (new PhieuTraDAO().themPhieuTra(pt, tmplist, mpm)) {
            return true;
        }
        return false;
    }

    public boolean suapt(PhieuTraDTO pt) {
        if (new PhieuTraDAO().capNhatPhieuTra(pt)) {
            return true;
        }
        return false;
    }

    public boolean xoapt(int mpt) {
        if (new PhieuTraDAO().xoaPhieuTra(mpt)) {
            return true;
        }
        return false;
    }

    public List<CTPhieuTraDTO> getListctpt(int mpt) {
        return new CTPhieuTraDAO().getList();
    }

    public List<CTPhieuTraDTO> getListctpt() {
        return new CTPhieuTraDAO().getList();
    }
}
