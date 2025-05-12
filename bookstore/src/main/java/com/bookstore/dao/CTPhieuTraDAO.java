package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.CTPhieuTraDTO;
import com.bookstore.utils.DatabaseUtils;

public class CTPhieuTraDAO {
    public List<CTPhieuTraDTO> getList() {
        List<CTPhieuTraDTO> danhsach = new ArrayList<>();
        String sql = "SELECT * FROM CTPhieuTra";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                danhsach.add(new CTPhieuTraDTO(
                        rs.getInt("TrangThai"),
                        rs.getInt("MaPhieuTra"),
                        rs.getString("MaSach"),
                        rs.getInt("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhsach;
    }

    public void themctpt(List<CTPhieuTraDTO> list) {
        if (list == null || list.isEmpty())
            return;
        String sql = "INSERT INTO CTPhieuTra (TrangThai, MaPhieuTra, MaSach, status) VALUES (?,?,?,?)";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (CTPhieuTraDTO ctpt : list) {
                stmt.setInt(1, ctpt.getTrangThai());
                stmt.setInt(2, ctpt.getMaPhieuTra());
                stmt.setString(3, ctpt.getMaSach());
                stmt.setInt(4, 1);
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
