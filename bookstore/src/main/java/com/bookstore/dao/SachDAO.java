package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.SachDTO;
import com.bookstore.utils.DatabaseUtils;

public class SachDAO implements IBaseDAO<SachDTO> {

    public int getSoluong() {
        int soluong = 0;
        String sql = "SELECT COUNT(*) AS Soluong FROM sach";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                soluong = rs.getInt("Soluong") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return soluong;
    }

    @Override
    public int insert(SachDTO t) {
        String sql = "INSERT INTO sach (MaSach, TrangThai, MaDauSach) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, t.getMaSach());
            stmt.setString(2, t.getTrangThai());
            stmt.setString(3, t.getMaDauSach());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                SachDTO kh = new SachDTO(rs.getString("MaSach"), rs.getString("TrangThai"),
                        rs.getString("MaDauSach"));
                list.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get data Sach and TacGia
    public List<SachDTO> selectByMaTacGia(String maTacGia) {
        List<SachDTO> list = new ArrayList<>();
        String query = "SELECT s.* FROM tacgia_sach ts " +
                "JOIN sach s ON ts.MaSach = s.MaSach " +
                "WHERE ts.MaTacGia = ? AND ts.Status = 1";

        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maTacGia);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SachDTO sach = new SachDTO(
                            rs.getString("MaSach"),
                            rs.getString("TrangThai"),
                            rs.getString("MaDauSach"));
                    list.add(sach);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<SachDTO> selectByDauSach(String MaDauSach) {
        List<SachDTO> list = new ArrayList<>();
        String query = "SELECT * " +
                "FROM sach JOIN dausach ON sach.MaDauSach = dausach.MaDauSach " +
                "WHERE sach.MaDauSach = ?";

        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, MaDauSach); // Gán giá trị cho tham số ?

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SachDTO sach = new SachDTO(rs.getString("MaSach"),
                            rs.getString("TrangThai"), rs.getDate("NgayNhap").toLocalDate());

                    list.add(sach);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Hoặc log lỗi
        }

        return list;
    }

    @Override
    public SachDTO selectById(int id) {
        return null;
    }

    public List<SachDTO> selectByTrangThaiHuOrMat() {
        List<SachDTO> list = new ArrayList<>();
        String query = "SELECT * FROM sach WHERE TrangThai = '0' OR TrangThai = '3'";

        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                SachDTO sach = new SachDTO(
                        rs.getString("MaSach"),
                        rs.getString("TrangThai"),
                        rs.getString("MaDauSach"),
                        rs.getDate("NgayNhap").toLocalDate());
                list.add(sach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
