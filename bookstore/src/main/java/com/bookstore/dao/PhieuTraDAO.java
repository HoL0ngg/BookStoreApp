package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.CTPhieuTraDTO;
import com.bookstore.DTO.PhieuTraDTO;
import com.bookstore.utils.DatabaseUtils;

public class PhieuTraDAO {

    //Thêm phiếu trả
    public boolean themPhieuTra(PhieuTraDTO phieuTra, List<CTPhieuTraDTO> tmplist) {
        String sql = "INSERT INTO PhieuTra (MaPhieuTra, NgayTra, MaNhanVien, MaDocGia, MaPhieuMuon, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, phieuTra.getMaPhieuTra());
            stmt.setDate(2, new java.sql.Date(phieuTra.getNgayTra().getTime()));
            stmt.setString(3, phieuTra.getMaNV());
            stmt.setString(4, phieuTra.getMaDocGia());
            stmt.setInt(5, phieuTra.getMaPhieuMuon());
            stmt.setInt(6, 1);
            stmt.executeUpdate();
            new CTPhieuTraDAO().themctpt(tmplist);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật phiếu trả
    public boolean capNhatPhieuTra(PhieuTraDTO phieuTra) {
        String sql = "UPDATE PhieuTra SET NgayTra = ?, MaNhanVien = ?, MaDocGia = ?, MaPhieuMuon = ? WHERE MaPhieuTra = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(phieuTra.getNgayTra().getTime()));
            stmt.setString(2, phieuTra.getMaNV());
            stmt.setString(3, phieuTra.getMaDocGia());
            stmt.setInt(4, phieuTra.getMaPhieuMuon());
            stmt.setInt(5, phieuTra.getMaPhieuTra());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa phiếu trả
    public boolean xoaPhieuTra(int maPhieuTra) {
        String sql = "UPDATE PhieuTra SET status = 0 WHERE MaPhieuTra = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maPhieuTra);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách tất cả phiếu trả -> list
    public List<PhieuTraDTO> layDanhSachPhieuTra() {
        List<PhieuTraDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM PhieuTra";
        try (Connection conn = DatabaseUtils.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                danhSach.add(new PhieuTraDTO(
                    rs.getInt("MaPhieuTra"),
                    rs.getDate("NgayTra"),
                    rs.getString("MaNhanVien"),
                    rs.getString("MaDocGia"),
                    rs.getInt("MaPhieuMuon"),
                    rs.getInt("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }
}
