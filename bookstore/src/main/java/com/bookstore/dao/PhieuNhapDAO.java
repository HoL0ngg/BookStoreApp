package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.PhieuNhapDTO;
import com.bookstore.utils.DatabaseUtils;

public class PhieuNhapDAO {

    public List<PhieuNhapDTO> layDanhSachPhieuNhap(){
        List<PhieuNhapDTO> danhsach = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhap";
        try (Connection conn = DatabaseUtils.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    danhsach.add(new PhieuNhapDTO(
                        rs.getString("MaPhieuNhap"),
                        rs.getDate("thoigian"),
                        rs.getString("MaNhanVien"),
                        rs.getInt("MaNCC"),
                        rs.getInt("TrangThai")
                        ));
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        return danhsach;
    }
}
