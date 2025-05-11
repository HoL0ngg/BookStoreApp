package com.bookstore.BUS;

import java.util.List;

import com.bookstore.DTO.PhieuHuyDTO;
import com.bookstore.dao.PhieuHuyDAO;

public class PhieuHuyBUS {
    private PhieuHuyDAO dao = new PhieuHuyDAO();

    public boolean themPhieu(PhieuHuyDTO phieu) {
        return dao.insert(phieu) > 0;
    }

    public boolean capNhatTrangThai(String maPhieu, int trangThai) {
        return dao.updateTrangThai(maPhieu, trangThai) > 0;
    }

    public List<PhieuHuyDTO> getAllPhieuHuy() {
        return dao.selectAll();
    }
}
