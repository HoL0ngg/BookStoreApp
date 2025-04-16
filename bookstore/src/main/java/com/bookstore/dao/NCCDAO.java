package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.NCCDTO;
import com.bookstore.DTO.SachDTO;
import com.bookstore.utils.DatabaseUtils;

public class NCCDAO implements IBaseDAO<NCCDTO> {
    @Override
    public int insert(NCCDTO ncc) {
        // Implement the insert logic here
        return 0;
    }

    @Override
    public int update(NCCDTO ncc) {
        // Implement the update logic here
        return 0;
    }

    @Override
    public int delete(int id) {
        // Implement the delete logic here
        return 0;
    }

    @Override
    public List<NCCDTO> selectAll() {
        List<NCCDTO> listNCC = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap WHERE Status = 1";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                NCCDTO kh = new NCCDTO();
                kh.setMaNCC(rs.getInt("MaNCC"));
                kh.setTenNCC(rs.getString("TenNhaCC"));
                kh.setDiaChi(rs.getString("DiaChi"));
                kh.setEmail(rs.getString("Email"));
                kh.setSoDienThoai(rs.getString("SDT"));
                kh.setStatus(rs.getInt("Status"));
                listNCC.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listNCC;
    }

    @Override
    public NCCDTO selectById(int id) {
        // Implement the selectById logic here
        return null;
    }

}
