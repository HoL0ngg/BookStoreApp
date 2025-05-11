package com.bookstore.BUS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.bookstore.utils.DatabaseUtils;
import com.bookstore.DTO.CTPhieuNhapDTO;
import com.bookstore.DTO.PhieuNhapDTO;
import com.bookstore.dao.CTPhieuNhapDAO;

public class PhieuNhapBUS {

    public PhieuNhapBUS() {
    }

    public boolean themPhieuNhap(PhieuNhapDTO PhieuNhap, List<CTPhieuNhapDTO> list) {
        String sql = "INSERT INTO PhieuNhap (MaPhieuNhap, thoigian, MaNhanVien, MaNCC, TrangThai) VALUES (?,?,?,?,?)";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, PhieuNhap.getMaPhieuNhap());
            stmt.setDate(2, PhieuNhap.getThoigian());
            stmt.setString(3, PhieuNhap.getMaNV());
            stmt.setInt(4, PhieuNhap.getMaNCC());
            stmt.setInt(5, PhieuNhap.getTrangThai());
            stmt.executeUpdate();
            themctphieunhap(PhieuNhap.getMaPhieuNhap(), list);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void themctphieunhap(String mpn, List<CTPhieuNhapDTO> list) {
        // Kiểm tra xem MaPhieuNhap có tồn tại trong bảng PhieuNhap không
        String checkSql = "SELECT COUNT(*) FROM PhieuNhap WHERE MaPhieuNhap = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, mpn);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                System.out.println("Lỗi: MaPhieuNhap " + mpn + " không tồn tại trong bảng PhieuNhap.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Thêm chi tiết phiếu nhập
        String sql = "INSERT INTO CTPhieuNhap (MaPhieuNhap, MaDauSach, SoLuong) VALUES (?,?,?)";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (CTPhieuNhapDTO ct : list) {
                stmt.setString(1, mpn);
                stmt.setString(2, ct.getMaDauSach());
                stmt.setInt(3, ct.getSoLuong());
                themdausach(ct.getMaDauSach(), ct.getSoLuong());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void themdausach(String MaDauSach, int SoLuong) throws SQLException {
        String sql = "UPDATE DauSach SET SoLuong = SoLuong + ? WHERE MaDauSach = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, SoLuong); // Đặt tham số SoLuong vào vị trí 1
            stmt.setString(2, MaDauSach); // Đặt tham số MaDauSach vào vị trí 2
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean suaPhieuNhap(PhieuNhapDTO phieuNhap) {
        String sql = "UPDATE PhieuNhap SET Thoigian = ?, MaNhanVien = ?, MaNCC = ? WHERE MaPhieuNhap = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(phieuNhap.getThoigian().getTime()));
            stmt.setString(2, phieuNhap.getMaNV());
            stmt.setInt(3, phieuNhap.getMaNCC());
            stmt.setString(4, phieuNhap.getMaPhieuNhap());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaPhieuNhap(String maPhieuNhap) {
        String sql = "DELETE FROM PhieuNhap WHERE MaPhieuNhap = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPhieuNhap);
            xoactphieunhap(maPhieuNhap);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void xoactphieunhap(String maPhieuNhap) {
        String sql = "DELETE FROM CTPhieuNhap Where MaPhieuNhap = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPhieuNhap);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }

    // private void xoa

    public PhieuNhapDTO themChiTietPhieuNhap(String mpn) {
        PhieuNhapDTO tmp = new PhieuNhapDTO(mpn, null, mpn, 0,0);
        return tmp;
    }

    public List<CTPhieuNhapDTO> getChiTietPhieuNhap(String mpn) {
        List<CTPhieuNhapDTO> ds = new CTPhieuNhapDAO().layDanhSachCTPhieuNhap();
        return ds;
    }

}
