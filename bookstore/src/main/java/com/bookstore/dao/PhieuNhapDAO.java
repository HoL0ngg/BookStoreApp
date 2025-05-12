package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.CTPhieuNhapDTO;
import com.bookstore.DTO.PhieuNhapDTO;
import com.bookstore.utils.DatabaseUtils;
import com.bookstore.dao.CTPhieuNhapDAO;

@SuppressWarnings("unused")
public class PhieuNhapDAO {

    // lấy list
    public List<PhieuNhapDTO> layDanhSachPhieuNhap() {
        List<PhieuNhapDTO> danhsach = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhap";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                danhsach.add(new PhieuNhapDTO(
                        rs.getString("MaPhieuNhap"),
                        rs.getDate("thoigian"),
                        rs.getString("MaNhanVien"),
                        rs.getInt("MaNCC"),
                        rs.getInt("status"),
                        rs.getInt("TrangThai")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhsach;
    }

    // thêm phiếu nhập mới
    public boolean themPhieuNhap(PhieuNhapDTO PhieuNhap, List<CTPhieuNhapDTO> list) {
        String sql = "INSERT INTO PhieuNhap (MaPhieuNhap, thoigian, MaNhanVien, MaNCC) VALUES (?,?,?,?)";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, PhieuNhap.getMaPhieuNhap());
            stmt.setDate(2, PhieuNhap.getThoigian());
            stmt.setString(3, PhieuNhap.getMaNV());
            stmt.setInt(4, PhieuNhap.getMaNCC());
            stmt.executeUpdate();
            themctphieunhap(PhieuNhap.getMaPhieuNhap(), list);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // thêm danh sách chi tiết phiếu nhập tương ứng với số sách trong phiếu tạo
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
        String sql = "INSERT INTO CTPhieuNhap (MaPhieuNhap, MaDauSach, SoLuong, status) VALUES (?,?,?,?)";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (CTPhieuNhapDTO ct : list) {
                stmt.setString(1, mpn);
                stmt.setString(2, ct.getMaDauSach());
                stmt.setInt(3, ct.getSoLuong());
                stmt.setInt(4, 1);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // thêm số lượng mới vào kho sách
    public void themdausach(String MaDauSach, int SoLuong) throws SQLException {
        String sql = "UPDATE DauSach SET SoLuong = SoLuong + ? WHERE MaDauSach = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, SoLuong);
            stmt.setString(2, MaDauSach);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // sửa thông tin phiếu nhập ( không set Trạng thái, hàm của nv )
    public boolean suaThongTinPhieuNhap(PhieuNhapDTO phieuNhap) {
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

    // sửa phiếu nhập ( cả trạng thái -> quản lý )
    public boolean suaPhieuNhap(PhieuNhapDTO phieuNhap) {
        String selectSql = "SELECT TrangThai FROM PhieuNhap WHERE MaPhieuNhap = ?";
        String updateSql = "UPDATE PhieuNhap SET Thoigian = ?, MaNhanVien = ?, MaNCC = ?, TrangThai = ? WHERE MaPhieuNhap = ?";
        try (Connection conn = DatabaseUtils.getConnection()) {
            int oldTrangThai = -1;

            // 1. Lấy TrangThai hiện tại từ DB
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setString(1, phieuNhap.getMaPhieuNhap());
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    oldTrangThai = rs.getInt("TrangThai");
                }
            }

            // so sánh
            int newTrangThai = phieuNhap.getTrangThai(); // bạn cần có getTrangThai() trong DTO
            boolean trangThaiThayDoi = oldTrangThai != newTrangThai;

            // 3. Cập nhật
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setDate(1, new java.sql.Date(phieuNhap.getThoigian().getTime()));
                updateStmt.setString(2, phieuNhap.getMaNV());
                updateStmt.setInt(3, phieuNhap.getMaNCC());
                updateStmt.setInt(4, newTrangThai);
                updateStmt.setString(5, phieuNhap.getMaPhieuNhap());

                int rows = updateStmt.executeUpdate();

                // 4. Gọi abc nếu cần
                if (rows > 0 && trangThaiThayDoi) {
                    List<CTPhieuNhapDTO> tmplist = new CTPhieuNhapDAO().layDanhSachCTPhieuNhap();
                    for (CTPhieuNhapDTO ctpn : tmplist) {
                        themdausach(ctpn.getMaDauSach(), ctpn.getSoLuong());
                    }

                    return rows > 0;
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // xóa phiếu nhập ( set status = 0 )
    public boolean xoaPhieuNhap(String maPhieuNhap) {
        String sql = "UPDATE PhieuNhap SET status = 0 WHERE MaPhieuNhap = ?";
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

    // xóa chi tiết phiếu nhập ( set status = 0 )
    private void xoactphieunhap(String maPhieuNhap) {
        String sql = "UPDATE CTPhieuNhap SET status = 0 WHERE MaPhieuNhap = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPhieuNhap);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }

    // ?..
    public List<CTPhieuNhapDTO> getChiTietPhieuNhap(String mpn) {
        List<CTPhieuNhapDTO> ds = new CTPhieuNhapDAO().layDanhSachCTPhieuNhap();
        return ds;
    }
}
