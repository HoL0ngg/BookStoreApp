package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.CTPhieuNhapDTO;
import com.bookstore.utils.DatabaseUtils;

public class CTPhieuNhapDAO {

    @SuppressWarnings("unused")
    public List<CTPhieuNhapDTO> layDanhSachCTPhieuNhap() {
        List<CTPhieuNhapDTO> danhsach = new ArrayList<>();
        String sql = "SELECT * FROM CTPhieuNhap";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                danhsach.add(new CTPhieuNhapDTO(
                        rs.getString("MaPhieuNhap"),
                        rs.getString("MaDauSach"),
                        rs.getInt("SoLuong")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhsach;
    }
}
