package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.utils.DatabaseUtils;

public class NhanVienDAO {
    public List<String> selectAll() {
        List<String> list = new ArrayList<>();
        try (Connection con = DatabaseUtils.getConnection()) {
            String sql = "SELECT * FROM nhanvien WHERE status = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maNhanVien = rs.getString("MaNhanVien") + " - " + rs.getString("TenNhanVien");
                list.add(maNhanVien);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return list;
    }
}
