package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.TheLoaiDTO;
import com.bookstore.utils.DatabaseUtils;

public class TheLoaiDAO {
    public List<TheLoaiDTO> getTheLoaiByMaSach(String maSach) {
        List<TheLoaiDTO> list = new ArrayList<TheLoaiDTO>();
        try {
            Connection conn = DatabaseUtils.getConnection();
            String sql = "SELECT tl.MaTheLoai, tl.TenTheLoai " +
                    "FROM theloaidausach stl " +
                    "JOIN TheLoai tl ON stl.MaTheLoai = tl.MaTheLoai " +
                    "WHERE stl.MaDauSach = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maSach);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TheLoaiDTO tl = new TheLoaiDTO();
                tl.setMaTheLoai(rs.getString("MaTheLoai"));
                tl.setTenTheLoai(rs.getString("TenTheLoai"));
                list.add(tl);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}