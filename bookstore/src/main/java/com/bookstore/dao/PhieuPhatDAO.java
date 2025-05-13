package com.bookstore.dao;

import com.bookstore.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.PhieuPhatDTO;

public class PhieuPhatDAO {

    // lấy danh sách
    public List<PhieuPhatDTO> getList() {
        List<PhieuPhatDTO> danhsach = new ArrayList<>();
        String sql = "SELECT * FROM PhieuPhat";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                danhsach.add(new PhieuPhatDTO(
                        rs.getInt("MaPhieuPhat"),
                        rs.getInt("TienPhat"),
                        rs.getDate("NgayPhat"),
                        rs.getInt("TrangThai"),
                        rs.getString("MaDocGia"),
                        rs.getInt("MaCTPhieuTra"),
                        rs.getInt("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhsach;
    }

    // thêm pp
    public boolean thempp(PhieuPhatDTO pp) {
        String sql = "INSERT INTO PhieuPhat (TienPhat, NgayPhat, TrangThai, MaDocGia, MaCTPhieuTra, status) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pp.getTienPhat());
            stmt.setDate(2, (Date) pp.getNgayPhat());
            stmt.setInt(3, pp.getTrangThai());
            stmt.setString(4, pp.getMaDocGia());
            stmt.setInt(5, pp.getMaCTPhieuTra());
            stmt.setInt(6, 1);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // sửa pp
    public boolean suapp(PhieuPhatDTO pp) {
        String sql = "UPDATE PhieuPhat SET TienPhat = ?, NgayPhat = ?, TrangThai = ?, MaDocGia = ? WHERE MaPhieuPhat = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pp.getTienPhat());
            stmt.setDate(2, (Date) pp.getNgayPhat());
            stmt.setInt(3, pp.getTrangThai());
            stmt.setString(4, pp.getMaDocGia());
            stmt.setInt(5, pp.getMaPhieuPhat());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // xóa pp status = 0
    public boolean xoapp(int mpp) {
        String sql = "UPDATE PhieuPhat SET status = 0 WHERE MaPhieuPhat = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, mpp);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
