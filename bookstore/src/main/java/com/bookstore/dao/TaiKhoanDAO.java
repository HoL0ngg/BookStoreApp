package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.TaiKhoanDTO;
import com.bookstore.utils.BCryptUtils;
import com.bookstore.utils.DatabaseUtils;
import com.bookstore.utils.NguoiDungDangNhap;

public class TaiKhoanDAO implements IBaseDAO<TaiKhoanDTO> {
    public boolean kiemTraDangNhap(String tenDangNhap, String matKhau) {
        boolean ketQua = false;

        try (Connection con = DatabaseUtils.getConnection()) {
            String sql = "SELECT * FROM taikhoan tk " +
                    "WHERE tk.TenDangNhap = ? AND tk.TrangThai = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tenDangNhap);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("MatKhau");

                if (BCryptUtils.checkPassword(matKhau, hashedPassword)) {
                    // Lấy thông tin người dùng từ kết quả truy vấn
                    NguoiDungDangNhap.getInstance().setThongTin(
                            rs.getString("TenDangNhap"),
                            rs.getInt("MaNhomQuyen"));
                    ketQua = true;
                }
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    public List<TaiKhoanDTO> getList() {
        List<TaiKhoanDTO> list = new ArrayList<>();
        try (Connection con = DatabaseUtils.getConnection()) {
            String sql = "SELECT * FROM taikhoan tk WHERE tk.trangthai = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TaiKhoanDTO taiKhoan = new TaiKhoanDTO();
                taiKhoan.setTenDangNhap(rs.getString("TenDangNhap"));
                taiKhoan.setEmail(rs.getString("Email"));
                taiKhoan.setMatKhau(rs.getString("MatKhau"));
                taiKhoan.setMaNhomQuyen(rs.getInt("MaNhomQuyen"));
                taiKhoan.setTrangThai(rs.getInt("TrangThai"));
                list.add(taiKhoan);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int insert(TaiKhoanDTO t) {
        try {
            Connection con = DatabaseUtils.getConnection();
            String sql = "INSERT INTO taikhoan(TenDangNhap, MatKhau, TrangThai, Email, MaNhomQuyen) values (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            String hashPW = BCryptUtils.hashPassword(t.getMatKhau());
            pst.setString(1, t.getTenDangNhap());
            pst.setString(2, hashPW);
            pst.setInt(3, t.getTrangThai());
            pst.setString(4, t.getEmail());
            pst.setInt(5, t.getMaNhomQuyen());
            return pst.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
            return 0;
        }
    }

    @Override
    public int update(TaiKhoanDTO t) {
        try {
            Connection con = DatabaseUtils.getConnection();
            String sql = "UPDATE taikhoan SET MatKhau = ?, TrangThai = ?, Email = ?, MaNhomQuyen = ? WHERE TenDangNhap = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMatKhau());
            pst.setInt(2, t.getTrangThai());
            pst.setString(3, t.getEmail());
            pst.setInt(4, t.getMaNhomQuyen());
            pst.setString(5, t.getTenDangNhap());
            return pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi update");
            return 0;
        }
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public List<TaiKhoanDTO> selectAll() {
        return null;
    }

    @Override
    public TaiKhoanDTO selectById(int id) {
        TaiKhoanDTO result = null;
        try {
            Connection con = DatabaseUtils.getConnection();
            String sql = "SELECT * FROM taikhoan WHERE tendangnhap = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

            }
            DatabaseUtils.closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    public TaiKhoanDTO selectById(String id) {
        TaiKhoanDTO result = null;
        try {
            Connection con = DatabaseUtils.getConnection();
            String sql = "SELECT * FROM taikhoan WHERE tendangnhap = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                result = new TaiKhoanDTO();
                result.setTenDangNhap(rs.getString("TenDangNhap"));
                result.setMatKhau(rs.getString("MatKhau"));
                result.setEmail(rs.getString("Email"));
                result.setTrangThai(rs.getInt("TrangThai"));
                result.setMaNhomQuyen(rs.getInt("MaNhomQuyen"));
            }
            DatabaseUtils.closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    public int delete(String TenDangNhap) {
        String sql = "Update taikhoan set trangthai = 0 where TenDangNhap = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, TenDangNhap);
            return pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("SQL Error during delete: " + ex.getMessage());
            ex.printStackTrace();
            return 0;
        }
    }

    public String getTenNhomQuyen(int maNhomQuyen) {
        String sql = "SELECT TenNhomQuyen FROM NhomQuyen WHERE MaNhomQuyen = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maNhomQuyen);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("TenNhomQuyen");
            }
        } catch (SQLException ex) {
            System.err.println("SQL Error during delete: " + ex.getMessage());
            ex.printStackTrace();
        }
        return "";
    }

    public String[] getTenNhomQuyenList() {
        String sql = "SELECT TenNhomQuyen FROM NhomQuyen";
        List<String> result = new ArrayList<>();
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("TenNhomQuyen"));
            }
        } catch (SQLException ex) {
            System.err.println("SQL Error during delete: " + ex.getMessage());
            ex.printStackTrace();
        }
        String[] result2 = result.toArray(new String[0]);
        return result2;
    }
}
