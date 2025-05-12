package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.bookstore.DTO.ChucNangDTO;
import com.bookstore.utils.DatabaseUtils;

public class ChucNangDAO implements IBaseDAO<ChucNangDTO> {

    @Override
    public int insert(ChucNangDTO t) {
        return 0;
    }

    @Override
    public int update(ChucNangDTO t) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public List<ChucNangDTO> selectAll() {
        return null;
    }

    public List<ChucNangDTO> selectAll(int maNhomQuyen) {
        List<ChucNangDTO> list = null;
        try {
            String sql = "SELECT * FROM chucnang WHERE MaChucNang = ? AND TrangThai = 1";
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    @Override
    public ChucNangDTO selectById(int id) {
        return null;
    }

    public String getTenChucNang(int maChucNang) {
        String tenChucNang = "";
        try (Connection con = DatabaseUtils.getConnection()) {
            String sql = "SELECT TenChucNang FROM chucnang WHERE MaChucNang = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, maChucNang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tenChucNang = rs.getString("TenChucNang");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tenChucNang;
    }

}
