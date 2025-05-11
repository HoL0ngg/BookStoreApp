package com.bookstore.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.PhieuHuyDTO;
import com.bookstore.utils.DatabaseUtils;
public class PhieuHuyDAO {
    public int insert(PhieuHuyDTO phieu) {
        String query = "INSERT INTO phieuhuy (MaPhieuHuy, NgayTao, MaNhanVien, TrangThai) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, phieu.getMaPhieuHuy());
            stmt.setDate(2, Date.valueOf(phieu.getNgayTao()));
            stmt.setString(3, phieu.getMaNhanVien());
            stmt.setInt(4, phieu.getTrangThai());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<PhieuHuyDTO> selectAll() {
        List<PhieuHuyDTO> list = new ArrayList<>();
        String query = "SELECT * FROM phieuhuy";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new PhieuHuyDTO(
                    rs.getString("MaPhieuHuy"),
                    rs.getDate("NgayTao").toLocalDate(),
                    rs.getString("MaNhanVien"),
                    rs.getInt("TrangThai")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int updateTrangThai(String maPhieuHuy, int trangThai) {
        String query = "UPDATE phieuhuy SET TrangThai = ? WHERE MaPhieuHuy = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, trangThai);
            stmt.setString(2, maPhieuHuy);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int selectCount() {
    String query = "SELECT COUNT(*) FROM phieuhuy";
    try (Connection conn = DatabaseUtils.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
            return rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}

}
