package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bookstore.DTO.SachDTO;
import com.bookstore.DTO.TacGiaDTO;
import com.bookstore.utils.DatabaseUtils;

public class TacGiaDAO implements IBaseDAO<TacGiaDTO>{
  
    public static TacGiaDAO getInstance() {
        return new TacGiaDAO();
    }


    @Override
    public int insert(TacGiaDTO t) {
       int result = 0;
       try  {
        Connection con = (Connection) DatabaseUtils.getConnection();
        String sql = "InSERT INTO `tacgia`(`MaTacGia`, `TenTacGia`, `NamSinh`, `QuocTich`, `Status`) VALUES (?,?,?,?,1)";
        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql); 
            pst.setString(1, t.getMaTacGia());
            pst.setString(2, t.getTenTacGia());
            pst.setInt(3, t.getNamSinh());
            pst.setString(4, t.getQuocTich());
            result = pst.executeUpdate();
            DatabaseUtils.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(TacGiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(TacGiaDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) DatabaseUtils.getConnection();
            String sql = "UPDATE `tacgia` SET `MaTacGia`=?,`TenTacGia`=?,`NamSinh`=?,`QuocTich`=? WHERE MaTacGia=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql); // Chuan bi du lieu 
            pst.setString(1, t.getMaTacGia());
            pst.setString(2, t.getTenTacGia());
            pst.setInt(3, t.getNamSinh());
            pst.setString(4, t.getQuocTich());
            pst.setString(5, t.getMaTacGia());
            result = pst.executeUpdate();
            DatabaseUtils.closeConnection(con); // Ngac ket noi khi hanh dong ending
        } catch (Exception e) {
            Logger.getLogger(TacGiaDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    @Override
public int delete(int id) {
    int result = 0;
    try {
       // change dtype 
        String maTacGia = String.valueOf(id);  

        Connection con = (Connection) DatabaseUtils.getConnection();
        String sql = "UPDATE `tacgia` SET Status= 0 WHERE `MaTacGia` = ?";
        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
        pst.setString(1, maTacGia);  // Sử dụng setString vì MaTacGia là String

        result = pst.executeUpdate();
        DatabaseUtils.closeConnection(con);
    } catch (Exception e) {
        Logger.getLogger(TacGiaDAO.class.getName()).log(Level.SEVERE, null, e);
    }
    return result;
}


    @Override
    public List<TacGiaDTO> selectAll() {
        ArrayList<TacGiaDTO> result = new ArrayList<TacGiaDTO>();
        try {
            Connection con = (Connection) DatabaseUtils.getConnection();
            String sql = "SELECT * FROM tacgia WHERE Status = 1 ";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                String MaTacGia = rs.getString("MaTacGia");
                String TenTacGia = rs.getString("TenTacGia");
                int NamSinh = rs.getInt("NamSinh");
                String QuocTich = rs.getString("QuocTich");
                TacGiaDTO tg = new TacGiaDTO(MaTacGia, TenTacGia, NamSinh, QuocTich);
                result.add(tg);
            }
            DatabaseUtils.closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
        
    }

    @Override
    public TacGiaDTO selectById(int id) {
        TacGiaDTO result = null;
        try {
            Connection con = (Connection) DatabaseUtils.getConnection();
            String sql = "SELECT * FROM tacgia WHERE MaTacGia=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                String MaTacGia = rs.getString("MaTacGia");
                String TenTacGia = rs.getString("TenTacGia");
                int NamSinh = rs.getInt("NamSinh");
                String QuocTich = rs.getString("QuocTich");
                result = new TacGiaDTO(MaTacGia, TenTacGia, NamSinh, QuocTich);
            }
            DatabaseUtils.closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
    
    // Get data Sach and TacGia
    public List<String> selectByMaTacGia(String maTacGia) {
        List<String> listTenSach = new ArrayList<>();
        String query = "SELECT ds.TenDauSach FROM tacgia_sach as tgs " +
               "JOIN sach as s ON tgs.MaSach = s.MaSach " +
               "JOIN dausach as ds ON s.MaDauSach = ds.MaDauSach " +
               "WHERE tgs.MaTacGia = ? AND tgs.Status = 1";


                   try (Connection conn = DatabaseUtils.getConnection();
                   PreparedStatement stmt = conn.prepareStatement(query)) {
                  stmt.setString(1, maTacGia);
          
                  try (ResultSet rs = stmt.executeQuery()) {
                      while (rs.next()) {
                          listTenSach.add(rs.getString("TenDauSach"));
                      }
                  }
              } catch (SQLException e) {
                  e.printStackTrace();
              }
          
              return listTenSach;
    }

    
}

