package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.bookstore.DTO.PhanQuyenDTO;
import com.bookstore.utils.DatabaseUtils;

public class PhanQuyenDAO implements IBaseDAO<PhanQuyenDTO> {
    @Override
    public int update(PhanQuyenDTO entity) {
        return 0;
        // Implementation for updating a PhanQuyenDTO
    }

    @Override
    public int delete(int id) {
        return 0;
        // Implementation for deleting a PhanQuyenDTO by ID
    }

    @Override
    public int insert(PhanQuyenDTO t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public List<PhanQuyenDTO> selectAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectAll'");
    }

    @Override
    public PhanQuyenDTO selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

    public String getTenNhomQuyen(int maNhomQuyen) {
        try (Connection con = DatabaseUtils.getConnection()) {
            String sql = "SELECT TenNhomQuyen FROM nhomquyen WHERE MaNhomQuyen = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, maNhomQuyen);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getString("TenNhomQuyen") : null;
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return null;
    }

}
