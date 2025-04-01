package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.bookstore.DTO.TacGiaDTO;
import com.bookstore.utils.DatabaseUtils;

public class TacGiaDAO implements IBaseDAO<TacGiaDTO>{

    @Override
    public int insert(TacGiaDTO t) {
       int result = 0;
       try  {
        Connection con = (Connection) DatabaseUtils.getConnection();
        String sql = "InSERT INTO `TacGia`(`MaTacGia`, `TenTacGia`, `NamSinh`, `QuocTich`) VALUES (?,?,?,?)";
        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql); 
            pst.setInt(1, t.getMaTacGia());
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
            String sql = "UPDATE `TacGia` SET `MaTacGia`=?,`TenTacGia`=?,`NamSinh`=?,`QuocTich`=? WHERE MaTacGia=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql); // Chuan bi du lieu 
            pst.setInt(1, t.getMaTacGia());
            pst.setString(2, t.getTenTacGia());
            pst.setInt(3, t.getNamSinh());
            pst.setString(4, t.getQuocTich());

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
            Connection con = (Connection) DatabaseUtils.getConnection();
            String sql = "UPDATE `TacGia` SET TrangThai= 0 WHERE `MaTacGia` = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, id);
            
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
            String sql = "SELECT * FROM TacGia WHERE TrangThai = 1 ";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int MaTacGia = rs.getInt("MaTacGia");
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
            String sql = "SELECT * FROM TacGia WHERE MaTacGia=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int MaTacGia = rs.getInt("MaTacGia");
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
}

