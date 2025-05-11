package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.ChiTietPhieuHuyDTO;
import com.bookstore.utils.DatabaseUtils;

public class ChiTietPhieuHuyDAO {
    public int insert(ChiTietPhieuHuyDTO ct) {
        String query = "INSERT INTO chitietphieuhuy (MaPhieuHuy, MaSach, GhiChu) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ct.getMaPhieuHuy());
            stmt.setString(2, ct.getMaSach());
            stmt.setString(3, ct.getGhiChu());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<ChiTietPhieuHuyDTO> getByMaPhieuHuy(String maPhieu) {
        List<ChiTietPhieuHuyDTO> list = new ArrayList<>();
        String query = "SELECT * FROM chitietphieuhuy WHERE MaPhieuHuy = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maPhieu);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new ChiTietPhieuHuyDTO(
                        rs.getString("MaPhieuHuy"),
                        rs.getString("MaSach"),
                        rs.getString("GhiChu")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

