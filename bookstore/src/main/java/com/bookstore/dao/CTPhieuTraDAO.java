package com.bookstore.dao;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bookstore.DTO.CTPhieuTraDTO;
import com.bookstore.DTO.PhieuPhatDTO;
import com.bookstore.DTO.PhieuTraDTO;
import com.bookstore.utils.DatabaseUtils;
import com.bookstore.BUS.PhieuPhatBUS;

public class CTPhieuTraDAO {
    public List<CTPhieuTraDTO> getList() {
        List<CTPhieuTraDTO> danhsach = new ArrayList<>();
        String sql = "SELECT * FROM CTPhieuTra";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                danhsach.add(new CTPhieuTraDTO(
                        rs.getInt("TrangThai"),
                        rs.getInt("MaPhieuTra"),
                        rs.getString("MaSach"),
                        rs.getInt("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhsach;
    }

    public void themctpt(List<CTPhieuTraDTO> list, PhieuTraDTO pt) {
        if (list == null || list.isEmpty()) {
            return;
        }

        String sqlCTPhieuTra = "INSERT INTO CTPhieuTra (TrangThai, MaPhieuTra, MaSach, status) VALUES (?, ?, ?, ?)";
        String sqlPhieuPhat = "INSERT INTO PhieuPhat (TienPhat, NgayPhat, TrangThai, MaDocGia, MaCTPhieuTra, Status) VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = DatabaseUtils.getConnection();
        try {
            conn.setAutoCommit(false); // Bắt đầu transaction

            PreparedStatement stmtCTPhieuTra = conn.prepareStatement(sqlCTPhieuTra, java.sql.Statement.RETURN_GENERATED_KEYS);
            PreparedStatement stmtPhieuPhat = conn.prepareStatement(sqlPhieuPhat);

            for (CTPhieuTraDTO ctpt : list) {
                // Thêm chi tiết phiếu trả
                stmtCTPhieuTra.setInt(1, ctpt.getTrangThai());
                stmtCTPhieuTra.setInt(2, ctpt.getMaPhieuTra());
                stmtCTPhieuTra.setString(3, ctpt.getMaSach());
                stmtCTPhieuTra.setInt(4, 1);
                int rowsAffected = stmtCTPhieuTra.executeUpdate();

                if (rowsAffected == 0) {
                    throw new SQLException("Không thể thêm CTPhieuTra");
                }

                // Lấy MaCTPhieuTra vừa tạo
                ResultSet rs = stmtCTPhieuTra.getGeneratedKeys();
                int maCTPhieuTra;
                if (rs.next()) {
                    maCTPhieuTra = rs.getInt(1);
                    System.out.println("MaCTPhieuTra vừa tạo: " + maCTPhieuTra);
                } else {
                    throw new SQLException("Không lấy được MaCTPhieuTra");
                }

                // Cập nhật trạng thái phiếu mượn
                new CTPhieuMuonDAO().suapm(ctpt.getMaSach());

                // Thêm phiếu phạt nếu cần
                if (ctpt.getTrangThai() == 1 || ctpt.getTrangThai() == 2) {
                    int soTien = ctpt.getTrangThai() == 1 ? 50000 : 100000;
                    stmtPhieuPhat.setDouble(1, soTien);
                    stmtPhieuPhat.setDate(2, new java.sql.Date(pt.getNgayTra().getTime()));
                    stmtPhieuPhat.setInt(3, ctpt.getTrangThai());
                    stmtPhieuPhat.setString(4, pt.getMaDocGia());
                    stmtPhieuPhat.setInt(5, maCTPhieuTra); // Sử dụng MaCTPhieuTra vừa tạo
                    stmtPhieuPhat.setInt(6, 1);
                    rowsAffected = stmtPhieuPhat.executeUpdate();
                    
                    if (rowsAffected == 0) {
                        throw new SQLException("Không thể thêm PhieuPhat");
                    }
                }
            }

            conn.commit(); // Commit transaction
            System.out.println("Thêm CTPhieuTra và PhieuPhat thành công");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback nếu có lỗi
                    System.out.println("Rollback giao dịch");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
