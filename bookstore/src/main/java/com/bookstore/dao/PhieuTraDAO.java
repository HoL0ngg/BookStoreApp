package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.PhieuTraDTO;
import com.bookstore.utils.DatabaseUtils;
import com.bookstore.views.Panel.PhieuTra;

public class PhieuTraDAO {

    //Thêm phiếu trả
    public boolean themPhieuTra(PhieuTraDTO phieuTra) {
        String sql = "INSERT INTO PhieuTra (MaPhieuTra, NgayTra, MaNV, MaDocGia, MaPhieuMuon) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, phieuTra.getMaPhieuTra());
            stmt.setDate(2, new java.sql.Date(phieuTra.getNgayTra().getTime()));
            stmt.setString(3, phieuTra.getMaNV());
            stmt.setString(4, phieuTra.getMaDocGia());
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
    public boolean xoaPhieuTra(String maPhieuTra) {
        String sql = "DELETE FROM PhieuTra WHERE MaPhieuTra = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPhieuTra);
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
                    rs.getInt("MaPhieuMuon")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    // Tìm phiếu trả theo mã
    public PhieuTraDTO timPhieuTra(String maPhieuTra) {
        String sql = "SELECT * FROM PhieuTra WHERE MaPhieuTra = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPhieuTra);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PhieuTraDTO(
                        rs.getInt("MaPhieuTra"),
                        rs.getDate("NgayTra"),
                        rs.getString("MaNV"),
                        rs.getString("MaDocGia"),
                        rs.getInt("MaPhieuMuon")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<PhieuTraDTO> selectByMaPhieuTra(List<PhieuTraDTO> danhsach) {
        ArrayList<PhieuTraDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtils.getConnection();
            String sql = "SELECT * FROM PhieuTra WHERE maPhieuTra LIKE ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + danhsach + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                PhieuTraDTO pt = new PhieuTraDTO();
                pt.setMaPhieuTra(rs.getInt("MaPhieuTra"));
                pt.setMaNV(rs.getString("MaNhanVien"));
                pt.setMaDocGia(rs.getString("maDocGia"));
                pt.setMaPhieuMuon(rs.getInt("MaPhieuMuon"));
                pt.setNgayTra(rs.getDate("ngayTra"));

                list.add(pt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }

        return list;
    }
}
