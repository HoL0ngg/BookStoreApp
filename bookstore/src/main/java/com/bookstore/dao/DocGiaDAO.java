package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.bookstore.DTO.DocGiaDTO;
import com.bookstore.utils.DatabaseUtils;

public class DocGiaDAO {

    public DocGiaDAO() {
    }

    public ArrayList<DocGiaDTO> getAllDocGia() {
        ArrayList<DocGiaDTO> list = new ArrayList<>();
        Connection conn = DatabaseUtils.getConnection();

        try {
            String sql = "SELECT * FROM DocGia";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new DocGiaDTO(
                        rs.getString("MaDocGia"),
                        rs.getString("TenDocGia"),
                        rs.getString("DiaChi"),
                        rs.getString("SDT"),
                        rs.getString("TrangThai"),
                        rs.getInt("Status") // Added the missing sixth argument
                ));
            }

            DatabaseUtils.closeConnection(conn); // đóng sau khi xài xong
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean insertDocGia(DocGiaDTO docGia) {
        try {
            String sql = "INSERT INTO DocGia (MaDocGia, TenDocGia, DiaChi, SDT, TrangThai, Status) VALUES (?, ?, ?, ?, ?, ?)";
            Connection conn = DatabaseUtils.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, docGia.getMaDocGia());
            stmt.setString(2, docGia.getTenDocGia());
            stmt.setString(3, docGia.getdiachi());
            stmt.setString(4, docGia.getSDT());
            stmt.setString(5, docGia.getTrangThai());
            stmt.setInt(6, docGia.getStatus());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDocGia(DocGiaDTO docGia) {
        String sql = "UPDATE DocGia SET TenDocGia=?, DiaChi=?, SDT=?, TrangThai=?, Status=? WHERE MaDocGia=?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            // thực hiện các thao tác trên conn và stmt
            stmt.setString(1, docGia.getTenDocGia());
            stmt.setString(2, docGia.getdiachi());
            stmt.setString(3, docGia.getSDT());
            stmt.setString(4, docGia.getTrangThai());
            stmt.setInt(5, docGia.getStatus());
            stmt.setString(6, docGia.getMaDocGia());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi ra console
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteDocGia(String maDocGia) {
        try {
            String sql = "UPDATE DocGia SET Status = 0 WHERE MaDocGia = ?";
            Connection conn = DatabaseUtils.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maDocGia);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
