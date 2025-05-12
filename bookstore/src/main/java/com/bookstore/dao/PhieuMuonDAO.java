package com.bookstore.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.PhieuMuonDTO;
import com.bookstore.DTO.SachDTO;
import com.bookstore.utils.DatabaseUtils;
import com.bookstore.dao.SachDAO;

@SuppressWarnings("unused")
public class PhieuMuonDAO {

    public List<PhieuMuonDTO> layDanhSachPhieuMuon() {
        List<PhieuMuonDTO> danhsach = new ArrayList<>();
        String sql = "SELECT * FROM PhieuMuon";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                danhsach.add(new PhieuMuonDTO(
                        rs.getInt("MaPhieuMuon"),
                        rs.getDate("NgayMuon"),
                        rs.getDate("NgayTraDuKien"),
                        rs.getInt("TrangThai"),
                        rs.getString("MaDocGia"),
                        rs.getString("MaNhanVien"),
                        rs.getBoolean("Status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhsach;
    }

    // thêm phiếu mượn
    public boolean themPhieuMuon(PhieuMuonDTO phieuMuon) {
        String sql = "INSERT INTO PhieuMuon (MaPhieuMuon, NgayMuon, NgayTraDuKien, TrangThai, MaDocGia, MaNhanVien) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, phieuMuon.getMaPhieuMuon());
            stmt.setDate(2, new java.sql.Date(phieuMuon.getNgayMuon().getTime()));
            stmt.setDate(3, (Date) phieuMuon.getNgayTraDuKien());
            stmt.setInt(4, phieuMuon.getTrangThai());
            stmt.setString(5, phieuMuon.getMaDocGia());
            stmt.setString(6, phieuMuon.getMaNhanVien());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa phiếu mượn ( chỉ cho sửa ngày trả, mã nhân viên, mã độc giả )
    public boolean suaPhieuMuon(PhieuMuonDTO phieuMuon) {
        System.out.println("aaa" + phieuMuon.getTrangThai());
        String sql = "UPDATE PhieuMuon SET NgayMuon = ?, NgayTraDuKien = ?, TrangThai = ?, MaDocGia = ?, MaNhanVien = ?, Status = ? WHERE MaPhieuMuon = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Chuyển đổi java.util.Date thành java.sql.Date cho NgayMuon
            if (phieuMuon.getNgayMuon() != null) {
                stmt.setDate(1, new java.sql.Date(phieuMuon.getNgayMuon().getTime()));
            } else {
                stmt.setDate(1, null);
            }

            // Chuyển đổi java.util.Date thành java.sql.Date cho NgayTraDuKien
            if (phieuMuon.getNgayTraDuKien() != null) {
                stmt.setDate(2, new java.sql.Date(phieuMuon.getNgayTraDuKien().getTime()));
            } else {
                stmt.setDate(2, null);
            }

            stmt.setInt(3, phieuMuon.getTrangThai());
            stmt.setString(4, phieuMuon.getMaDocGia());
            stmt.setString(5, phieuMuon.getMaNhanVien());
            stmt.setBoolean(6, true);
            stmt.setInt(7, phieuMuon.getMaPhieuMuon());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa phiếu mượn -> status = 0
    public boolean xoaPhieuMuon(int maPhieuMuon) {
        String sql = "UPDATE PhieuMuon SET status = 0 WHERE MaPhieuMuon = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maPhieuMuon);
            xoactPhieuMuon(maPhieuMuon);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    // xóa chi tiết phiếu mượn kèm theo khi xóa 1 phiếu mượn
    public void xoactPhieuMuon(int MaPhieuMuon) {
        String sql = "UPDATE CTPhieuMuon SET status = 0 WHERE MaPhieuMuon = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, MaPhieuMuon);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }

    public void themChiTietPhieuMuon(int mpm, String mds) {
        List<SachDTO> listsach = new SachDAO().selectAll();
        String maSachSeMuon = null;
        for (SachDTO tmpsach : listsach) {
            if (tmpsach.getMaDauSach().equals(mds)) {
                maSachSeMuon = tmpsach.getMaSach();
            }
        }
        String sql1 = "UPDATE sach SET TrangThai = ? WHERE MaSach = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql1)) {
            stmt.setInt(1, 1);
            stmt.setString(2, maSachSeMuon);
            stmt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        String sql2 = "UPDATE dausach SET SoLuong = SoLuong - 1 WHERE MaDauSach = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql2)) {
            stmt.setString(1, mds);
            stmt.executeUpdate();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }

        String sql = "INSERT INTO CTPhieuMuon (MaPhieuMuon, MaSach) VALUES (?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, mpm);
            stmt.setString(2, maSachSeMuon);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm chi tiết phiếu mượn: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
