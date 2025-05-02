package com.bookstore.DTO;

public class CTPhieuMuonDTO {
    private int MaPhieuMuon;
    private String MaSach;
    private boolean Status;

    // Constructor
    public CTPhieuMuonDTO(int MaPhieuMuon, String MaSach, boolean Status){
        this.MaPhieuMuon = MaPhieuMuon;
        this.MaSach = MaSach;
        this.Status = Status;
    }

    public int getMaPhieuMuon() {
        return MaPhieuMuon;
    }

    public void setMaCTPhieuMuon(int maCTPhieuMuon) {
        MaPhieuMuon = maCTPhieuMuon;
    }

    public String getMaSach() {
        return MaSach;
    }

    public void setMaSach(String maSach) {
        MaSach = maSach;
    }

    public boolean getStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    
}
