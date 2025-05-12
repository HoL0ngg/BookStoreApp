package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.utils.DatabaseUtils;
import com.bookstore.DTO.CTPhieuMuonDTO;

public class CTPhieuMuonDAO {

    public List<CTPhieuMuonDTO> layDanhSachCTPhieuMuon() {
        List<CTPhieuMuonDTO> danhsach = new ArrayList<>();
        String sql = "SELECT * FROM CTPhieuMuon";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                danhsach.add(new CTPhieuMuonDTO(
                        rs.getInt("MaPhieuMuon"),
                        rs.getString("MaSach"),
                        rs.getBoolean("Status"),
                        rs.getInt("TrangThai")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhsach;
    }

    public void suapm(String ms) {
        String sql = "UPDATE CTPhieuMuon SET TrangThai = 1 WHERE MaSach = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ms);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
