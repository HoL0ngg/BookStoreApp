package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.DauSachDTO;
import com.bookstore.DTO.TheLoaiDTO;
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
                TheLoaiDAO theloaiDAO = new TheLoaiDAO();
                List<TheLoaiDTO> listTheLoai = theloaiDAO.getTheLoaiByMaSach(rs.getString("MaDauSach"));
                DauSachDTO kh = new DauSachDTO(rs.getString("MaDauSach"), rs.getString("TenDauSach"),
                        rs.getString("HinhAnh"), rs.getString("NhaXuatBan"), rs.getInt("NamXuatBan"),
                        rs.getString("NgonNgu"), rs.getInt("SoTrang"), listTheLoai);
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

    public int delete(String id) {
        String query = "DELETE FROM dausach WHERE MaDauSach = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    // show books 
    public List<DauSachDTO> selectDauSachByMaTacGia(String maTacGia) {
        List<DauSachDTO> list = new ArrayList<>();
        String query = "SELECT DISTINCT ds.* FROM tacgia_sach ts " +
                       "JOIN sach s ON ts.MaSach = s.MaSach " +
                       "JOIN dausach ds ON s.MaDauSach = ds.MaDauSach " +
                       "WHERE ts.MaTacGia = ? AND ts.Status = 1";
    
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maTacGia);
    
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TheLoaiDAO theloaiDAO = new TheLoaiDAO();
                    List<TheLoaiDTO> listTheLoai = theloaiDAO.getTheLoaiByMaSach(rs.getString("MaDauSach"));
                    DauSachDTO ds = new DauSachDTO(
                        rs.getString("MaDauSach"),
                        rs.getString("TenDauSach"),
                        rs.getString("HinhAnh"),
                        rs.getString("NhaXuatBan"),
                        rs.getInt("NamXuatBan"),
                        rs.getString("NgonNgu"),
                        rs.getInt("SoTrang"),
                        listTheLoai
                    );
                    list.add(ds);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return list;
    }
}
