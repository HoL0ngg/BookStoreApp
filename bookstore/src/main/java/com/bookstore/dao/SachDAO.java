package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.DauSachDTO;
import com.bookstore.DTO.SachDTO;
import com.bookstore.utils.DatabaseUtils;

public class SachDAO implements IBaseDAO<SachDTO> {

    @Override
    public int insert(SachDTO t) {
        return 0;
    }

    @Override
    public int update(SachDTO t) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public List<SachDTO> selectAll() {
        List<SachDTO> list = new ArrayList<>();
        String query = "SELECT * FROM sach";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                SachDTO kh = new SachDTO(rs.getInt("MaSach"), rs.getString("TrangThai"),
                        rs.getInt("MaDauSach"));
                list.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public SachDTO selectById(int id) {
        return null;
    }

}
