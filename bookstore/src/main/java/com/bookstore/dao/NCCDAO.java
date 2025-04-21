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
        NCCDTO ncc = null;
        String sql = "SELECT * FROM NhaCungCap WHERE MaNCC = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ncc = new NCCDTO();
                    ncc.setMaNCC(rs.getInt("MaNCC"));
                    ncc.setTenNCC(rs.getString("TenNhaCC"));
                    ncc.setDiaChi(rs.getString("DiaChi"));
                    ncc.setEmail(rs.getString("Email"));
                    ncc.setSoDienThoai(rs.getString("SDT"));
                    ncc.setStatus(rs.getInt("Status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ncc;
    }

    public int getMaxId() {
        int maxId = 0;
        String sql = "SELECT MAX(MaNCC) AS MaxId FROM NhaCungCap";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                maxId = rs.getInt("MaxId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxId;
    }

}
