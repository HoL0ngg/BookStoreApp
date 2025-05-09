package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public Map<String, String> getThongTin() {
        Map<String, String> hehe = new LinkedHashMap<>();
        try {
            Connection con = DatabaseUtils.getConnection();
            String sql = "SELECT MaTheLoai, TenTheLoai FROM theloai";
            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                hehe.put(rs.getString("tentheloai"), rs.getString("matheloai"));
            }
            DatabaseUtils.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(TacGiaDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return hehe;
    }

    public int insertDauSach(String madausach, String MaTheLoai) {
        try {
            Connection con = DatabaseUtils.getConnection();
            String sql = "INSERT INTO theloaidausach(madausach, matheloai) values(?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, madausach);
            pst.setString(2, MaTheLoai);
            return pst.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(TacGiaDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }
}