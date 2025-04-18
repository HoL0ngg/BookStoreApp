package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.PhieuMuonDTO;
import com.bookstore.utils.DatabaseUtils;

public class PhieuMuonDAO{

    public List<PhieuMuonDTO> layDanhSachPhieuMuon(){
        List<PhieuMuonDTO> danhsach = new ArrayList<>();
        String sql = "SELECT * FROM PhieuMuon";
        try (Connection conn = DatabaseUtils.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    danhsach.add(new PhieuMuonDTO(
                        rs.getInt("MaPhieuMuon"),
                        rs.getDate("NgayMuon"),
                        rs.getDate("NgayTraDuKien"),
                        rs.getInt("TrangThai"),
                        rs.getString("MaDocGia"),
                        rs.getString("MaNhanVien")
                        ));
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        return danhsach;
    }

}
