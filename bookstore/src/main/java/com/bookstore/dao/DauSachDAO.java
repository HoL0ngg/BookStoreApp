package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.DauSachDTO;
import com.bookstore.utils.DatabaseUtils;

public class DauSachDAO implements IBaseDAO<DauSachDTO> {

    @Override
    public int insert(DauSachDTO t) {
        return 0;
    }

    @Override
    public int update(DauSachDTO t) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public List<DauSachDTO> selectAll() {
        List<DauSachDTO> list = new ArrayList<>();
        String query = "SELECT * FROM dausach";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DauSachDTO kh = new DauSachDTO(rs.getString("MaDauSach"), rs.getString("TenDauSach"),
                        rs.getString("HinhAnh"),
                        rs.getString("NhaXuatBan"), rs.getInt("NamXuatBan"),
                        rs.getString("NgonNgu"), rs.getInt("SoTrang"));
                list.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public DauSachDTO selectById(int id) {
        return null;
    }

}
