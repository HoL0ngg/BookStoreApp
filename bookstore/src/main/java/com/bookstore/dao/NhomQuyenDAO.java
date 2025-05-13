package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.NhomQuyenDTO;
import com.bookstore.utils.DatabaseUtils;

public class NhomQuyenDAO implements IBaseDAO<NhomQuyenDTO> {

    @Override
    public int insert(NhomQuyenDTO t) {
        try (Connection con = DatabaseUtils.getConnection()) {
            String sql = "INSERT INTO nhomquyen (TenNhomQuyen) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getTenNhomQuyen());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getMAXID() {
        int maxId = 0;
        try (Connection con = DatabaseUtils.getConnection()) {
            String sql = "SELECT MAX(MaNhomQuyen) AS MaxID FROM nhomquyen";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                maxId = rs.getInt("MaxID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxId;
    }

    public int insert(int maNhomQuyen, int maChucNang, boolean xem, boolean them, boolean sua, boolean xoa) {
        int result = 0;
        try (Connection con = DatabaseUtils.getConnection()) {
            String sql = "INSERT INTO nhomquyen_chucnang (maNhomQuyen, maChucNang, Xem, Them, Sua, Xoa) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, maNhomQuyen);
            ps.setInt(2, maChucNang);
            ps.setBoolean(3, xem);
            ps.setBoolean(4, them);
            ps.setBoolean(5, sua);
            ps.setBoolean(6, xoa);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(NhomQuyenDTO t) {
        return 0;
    }

    public int update(int maNhomQuyen, boolean xem, boolean them, boolean sua, boolean xoa, int maChucNang) {
        int result = 0;
        try (Connection con = DatabaseUtils.getConnection()) {
            String sql = "UPDATE nhomquyen_chucnang SET Xem = ?, Them = ?, Sua = ?, Xoa = ? WHERE MaNhomQuyen = ? AND MaChucNang = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBoolean(1, xem);
            ps.setBoolean(2, them);
            ps.setBoolean(3, sua);
            ps.setBoolean(4, xoa);
            ps.setInt(5, maNhomQuyen);
            ps.setInt(6, maChucNang);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int update(int maNhomQuyen, String tenNhomQuyen) {
        int result = 0;
        try (Connection con = DatabaseUtils.getConnection()) {
            String sql = "UPDATE nhomquyen SET TenNhomQuyen = ? WHERE MaNhomQuyen = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tenNhomQuyen);
            ps.setInt(2, maNhomQuyen);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(int id) {
        int result = 0;
        try (Connection con = DatabaseUtils.getConnection()) {
            String sql = "DELETE FROM nhomquyen WHERE MaNhomQuyen = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<NhomQuyenDTO> selectAll() {
        List<NhomQuyenDTO> list = null;
        try (Connection con = DatabaseUtils.getConnection()) {
            list = new ArrayList<>();
            String sql = "SELECT * FROM nhomquyen WHERE status = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhomQuyenDTO nhomQuyen = new NhomQuyenDTO();
                nhomQuyen.setMaNhomQuyen(rs.getInt("MaNhomQuyen"));
                nhomQuyen.setTenNhomQuyen(rs.getString("TenNhomQuyen"));
                nhomQuyen.setTrangThai(rs.getInt("status"));
                list.add(nhomQuyen);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public NhomQuyenDTO selectById(int id) {
        return null;
    }

    public boolean isVisible(int maNhomQuyen, int maChucNang) {
        boolean result = false;
        try (Connection con = DatabaseUtils.getConnection()) {
            String sql = "SELECT * FROM nhomquyen_chucnang WHERE MaNhomQuyen = ? AND MaChucNang = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, maNhomQuyen);
            ps.setInt(2, maChucNang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                for (int i = 1; i <= 4; i++) {
                    if (rs.getBoolean(i + 2) == true) {
                        result = true;
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean isAccessable(int maNhomQuyen, int maChucNang, int chucNang) {
        boolean result = false;
        try (Connection con = DatabaseUtils.getConnection()) {
            String sql = "SELECT * FROM nhomquyen_chucnang WHERE MaNhomQuyen = ? AND MaChucNang = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, maNhomQuyen);
            ps.setInt(2, maChucNang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getBoolean(chucNang + 2);
                System.out.println(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getTenNhomQuyen(int maNhomQuyen) {
        String tenNhomQuyen = "";
        try (Connection con = DatabaseUtils.getConnection()) {
            String sql = "SELECT TenNhomQuyen FROM nhomquyen WHERE MaNhomQuyen = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, maNhomQuyen);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tenNhomQuyen = rs.getString("TenNhomQuyen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tenNhomQuyen;
    }

    public boolean[] loadQuyenTheoMaNhom(int maNhomQuyen, int maChucNang) {
        boolean[] quyen = new boolean[] { false, false, false, false };
        try (Connection con = DatabaseUtils.getConnection()) {
            String sql = "SELECT * FROM nhomquyen_chucnang WHERE MaNhomQuyen = ? AND MaChucNang = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, maNhomQuyen);
            ps.setInt(2, maChucNang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                quyen[0] = rs.getBoolean("Xem");
                quyen[1] = rs.getBoolean("Them");
                quyen[2] = rs.getBoolean("Sua");
                quyen[3] = rs.getBoolean("Xoa");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quyen;
    }

}
