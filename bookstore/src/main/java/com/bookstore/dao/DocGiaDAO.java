package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.DocGiaDTO;
import com.bookstore.utils.DatabaseUtils;

public class DocGiaDAO {

    public List<DocGiaDTO> layDanhSachPhieuNhap() {
        List<DocGiaDTO> danhsach = new ArrayList<>();
        String sql = "SELECT * FROM DocGia";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                danhsach.add(new DocGiaDTO(
                        rs.getString("MaDocGia"),
                        rs.getString("TenDocGia"),
                        rs.getString("DiaChi"),
                        rs.getString("SDT"),
                        rs.getInt("TrangThai"),
                        rs.getInt("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhsach;
    }
}
