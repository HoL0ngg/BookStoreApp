package com.bookstore.BUS;

import java.util.List;

import com.bookstore.DTO.ChiTietPhieuHuyDTO;
import com.bookstore.dao.ChiTietPhieuHuyDAO;

public class ChiTietPhieuHuyBUS {
    private ChiTietPhieuHuyDAO dao = new ChiTietPhieuHuyDAO();

    public boolean themChiTiet(ChiTietPhieuHuyDTO ct) {
        return dao.insert(ct) > 0;
    }

    public List<ChiTietPhieuHuyDTO> getByPhieu(String maPhieu) {
        return dao.getByMaPhieuHuy(maPhieu);
    }
}

