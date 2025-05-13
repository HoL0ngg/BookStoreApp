package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.bookstore.DTO.CTPhieuTraDTO;
import com.bookstore.DTO.PhieuMuonDTO;
import com.bookstore.DTO.PhieuPhatDTO;
import com.bookstore.DTO.PhieuTraDTO;
import com.bookstore.utils.DatabaseUtils;

public class PhieuTraDAO {

    // Thêm phiếu trả
    public boolean themPhieuTra(PhieuTraDTO phieuTra, List<CTPhieuTraDTO> tmplist, int mpm) {
        String sql = "INSERT INTO PhieuTra (MaPhieuTra, NgayTra, MaNhanVien, MaDocGia, MaPhieuMuon, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, phieuTra.getMaPhieuTra());
            stmt.setDate(2, new java.sql.Date(phieuTra.getNgayTra().getTime()));
            stmt.setString(3, phieuTra.getMaNV());
            stmt.setString(4, phieuTra.getMaDocGia());
            stmt.setInt(5, phieuTra.getMaPhieuMuon());
            stmt.setInt(6, 1);
            stmt.executeUpdate();
            new CTPhieuTraDAO().themctpt(tmplist, phieuTra);
            ktrapp(mpm, phieuTra.getMaPhieuTra());
            if (kiemTraTatCaSachDaTra(phieuTra.getMaPhieuMuon())) {
                capNhatTrangThaiPhieuMuon(phieuTra.getMaPhieuMuon());;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật phiếu trả
    public boolean capNhatPhieuTra(PhieuTraDTO phieuTra) {
        String sql = "UPDATE PhieuTra SET NgayTra = ?, MaNhanVien = ?, MaDocGia = ?, MaPhieuMuon = ? WHERE MaPhieuTra = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(phieuTra.getNgayTra().getTime()));
            stmt.setString(2, phieuTra.getMaNV());
            stmt.setString(3, phieuTra.getMaDocGia());
            stmt.setInt(4, phieuTra.getMaPhieuMuon());
            stmt.setInt(5, phieuTra.getMaPhieuTra());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa phiếu trả
    public boolean xoaPhieuTra(int maPhieuTra) {
        String sql = "UPDATE PhieuTra SET status = 0 WHERE MaPhieuTra = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maPhieuTra);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách tất cả phiếu trả -> list
    public List<PhieuTraDTO> layDanhSachPhieuTra() {
        List<PhieuTraDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM PhieuTra";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                danhSach.add(new PhieuTraDTO(
                        rs.getInt("MaPhieuTra"),
                        rs.getDate("NgayTra"),
                        rs.getString("MaNhanVien"),
                        rs.getString("MaDocGia"),
                        rs.getInt("MaPhieuMuon"),
                        rs.getInt("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    // kiểm tra và so sánh trả toàn bộ sách -> gọi cập nhật phiếu mượn else skip
    private boolean kiemTraTatCaSachDaTra(int maPhieuMuon) throws SQLException {
        String checkSQL = "SELECT COUNT(*) as total, SUM(CASE WHEN TrangThai = 1 THEN 1 ELSE 0 END) as returned FROM CTPhieuMuon WHERE MaPhieuMuon = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement checkStmt = conn.prepareStatement(checkSQL)) {
            checkStmt.setInt(1, maPhieuMuon);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                int totalBooks = rs.getInt("total");
                int returnedBooks = rs.getInt("returned");
                return totalBooks > 0 && returnedBooks == totalBooks;
            }
            return false;
        }
    }

    // gọi sửa phiếu mượn set Trạng thái != set status
    private void capNhatTrangThaiPhieuMuon(int maPhieuMuon) throws SQLException {
        PhieuMuonDAO pmDao = new PhieuMuonDAO();
        PhieuMuonDTO pm = new PhieuMuonDTO();
        pm.setMaPhieuMuon(maPhieuMuon);

        List<PhieuMuonDTO> danhSachPhieuMuon = pmDao.layDanhSachPhieuMuon();
        for (PhieuMuonDTO existingPm : danhSachPhieuMuon) {
            if (existingPm.getMaPhieuMuon() == maPhieuMuon) {
                pm.setNgayMuon(existingPm.getNgayMuon());
                pm.setNgayTraDuKien(existingPm.getNgayTraDuKien());
                pm.setTrangThai(1);
                pm.setMaDocGia(existingPm.getMaDocGia());
                pm.setMaNhanVien(existingPm.getMaNhanVien());
                break;
            }
        }

        pmDao.suaPhieuMuon(pm);
    }

    public boolean ktrapp(int mpm, int mpt){
        List<PhieuMuonDTO> pm = new PhieuMuonDAO().layDanhSachPhieuMuon();
        List<PhieuTraDTO> pt = layDanhSachPhieuTra();
        PhieuMuonDTO ntdk = null;
        for (PhieuMuonDTO pmdto : pm){
            if (pmdto.getMaPhieuMuon() == mpm){
                ntdk = pmdto;
            }
        }
        PhieuTraDTO ntht = null;
        for (PhieuTraDTO ptdto : pt){
            if (ptdto.getMaPhieuTra() == mpt){
                ntht = ptdto;
            }
        }
        List<CTPhieuTraDTO> ctptdto = new CTPhieuTraDAO().getList();
        CTPhieuTraDTO mctpt = null;
        for (CTPhieuTraDTO tmp : ctptdto){
            if (tmp.getMaPhieuTra() == ntht.getMaPhieuTra()){
                mctpt = tmp;
            }
        }
        Date ntdkd = ntdk.getNgayTraDuKien();
        Date nthtd = ntht.getNgayTra();

        if (ntdkd.before(nthtd)){
            new PhieuPhatDAO().thempp(new PhieuPhatDTO(0, 50000, nthtd, 0, ntht.getMaDocGia(), mctpt.getMaCTPhieuTra(), 1));
            return true;
        }
        return false;
    }
}
