package com.bookstore.BUS;

import java.util.List;

import com.bookstore.DTO.CTPhieuMuonDTO;
import com.bookstore.DTO.PhieuMuonDTO;
import com.bookstore.dao.CTPhieuMuonDAO;
import com.bookstore.dao.PhieuMuonDAO;

public class PhieuMuonBUS {
    private List<PhieuMuonDTO> listPM;

    public PhieuMuonBUS() {
        listPM = new PhieuMuonDAO().layDanhSachPhieuMuon();
    }

    public List<PhieuMuonDTO> getList() {
        return listPM;
    }

    // thêm phiếu mượn
    public boolean themPhieuMuon(PhieuMuonDTO phieuMuon) {
        listPM.add(phieuMuon);
        if (new PhieuMuonDAO().themPhieuMuon(phieuMuon)){
            return true;
        }
        return false;
    }

    // Sửa phiếu mượn
    public boolean suaPhieuMuon(PhieuMuonDTO phieuMuon) {
        if (new PhieuMuonDAO().suaPhieuMuon(phieuMuon)){
            return true;
        }
        return false;
    }

    // Xóa phiếu mượn
    public boolean xoaPhieuMuon(int maPhieuMuon){
        if (new PhieuMuonDAO().xoaPhieuMuon(maPhieuMuon)){
            return true;
        }
        return false;
    }

    public void themChiTietPhieuMuon(int mpm, String ms) {
        new PhieuMuonDAO().themChiTietPhieuMuon(mpm, ms);
    }

    public List<CTPhieuMuonDTO> getCTPhieuMuon(){
        return new CTPhieuMuonDAO().layDanhSachCTPhieuMuon();
    }

}
