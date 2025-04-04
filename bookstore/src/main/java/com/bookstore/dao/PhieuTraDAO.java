package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.PhieuTraDTO;
import com.bookstore.utils.DatabaseUtils;

public class PhieuTraDAO {

    // Thêm phiếu trả vào database
    public boolean themPhieuTra(PhieuTraDTO phieuTra) {
        String sql = "INSERT INTO PhieuTra (MaPhieuTra, NgayTra, MaNV, MaDocGia, MaPhieuMuon) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, phieuTra.getMaPhieuTra());
            stmt.setDate(2, new java.sql.Date(phieuTra.getNgayTra().getTime()));
            stmt.setInt(3, phieuTra.getMaNV());
            stmt.setInt(4, phieuTra.getMaDocGia());
            stmt.setInt(5, phieuTra.getMaPhieuMuon());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật phiếu trả
    public boolean capNhatPhieuTra(PhieuTraDTO phieuTra) {
        String sql = "UPDATE PhieuTra SET NgayTra = ?, MaNV = ?, MaDocGia = ?, MaPhieuMuon = ? WHERE MaPhieuTra = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(phieuTra.getNgayTra().getTime()));
            stmt.setInt(2, phieuTra.getMaNV());
            stmt.setInt(3, phieuTra.getMaDocGia());
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
        String sql = "DELETE FROM PhieuTra WHERE MaPhieuTra = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maPhieuTra);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách tất cả phiếu trả
    public List<PhieuTraDTO> layDanhSachPhieuTra() {
        List<PhieuTraDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM PhieuTra";
        try (Connection conn = DatabaseUtils.getConnection();
            PreparedStatement stmt = (PreparedStatement) conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                danhSach.add(new PhieuTraDTO(
                    rs.getInt("MaPhieuTra"),
                    rs.getDate("NgayTra"),
                    rs.getInt("MaNV"),
                    rs.getInt("MaDocGia"),
                    rs.getInt("MaPhieuMuon")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    // Tìm phiếu trả theo mã
    public PhieuTraDTO timPhieuTra(int maPhieuTra) {
        String sql = "SELECT * FROM PhieuTra WHERE MaPhieuTra = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maPhieuTra);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PhieuTraDTO(
                        rs.getInt("MaPhieuTra"),
                        rs.getDate("NgayTra"),
                        rs.getInt("MaNV"),
                        rs.getInt("MaDocGia"),
                        rs.getInt("MaPhieuMuon")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
