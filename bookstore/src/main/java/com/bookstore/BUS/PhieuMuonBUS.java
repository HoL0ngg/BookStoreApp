package com.bookstore.BUS;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.bookstore.DTO.PhieuMuonDTO;
import com.bookstore.dao.PhieuMuonDAO;
import com.bookstore.utils.DatabaseUtils;

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
        String sql = "INSERT INTO PhieuMuon (MaPhieuMuon, NgayMuon, NgayTraDuKien, TrangThai, MaDocGia, MaNhanVien) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, phieuMuon.getMaPhieuMuon());
            stmt.setDate(2, new java.sql.Date(phieuMuon.getNgayMuon().getTime()));
            stmt.setDate(3, (Date) phieuMuon.getNgayTraDuKien());
            stmt.setInt(4, phieuMuon.getTrangThai());
            stmt.setString(5, phieuMuon.getMaDocGia());
            stmt.setString(6, phieuMuon.getMaNhanVien());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    // Sửa phiếu mượn
    public boolean suaPhieuMuon(PhieuMuonDTO phieuMuon) {
        String sql = "UPDATE PhieuMuon SET NgayMuon = ?, NgayTraDuKien = ?, TrangThai = ?, MaDocGia = ?, MaNhanVien = ? WHERE MaPhieuMuon = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, (Date) phieuMuon.getNgayMuon());
            stmt.setDate(2, (Date) phieuMuon.getNgayTraDuKien());
            stmt.setInt(3, phieuMuon.getTrangThai());
            stmt.setString(4, phieuMuon.getMaDocGia());
            stmt.setString(5, phieuMuon.getMaNhanVien());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    // Xóa phiếu mượn
    public boolean xoaPhieuMuon(int maPhieuMuon){
        String sql = "DELETE DROM PhieuMuon WHERE MaPhieuMuon = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maPhieuMuon);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    public void themChiTietPhieuMuon(int mpm, String ms) {
    }

}
