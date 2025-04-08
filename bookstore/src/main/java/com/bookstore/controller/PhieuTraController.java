package com.bookstore.controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

import com.bookstore.DTO.PhieuTraDTO;
import com.bookstore.DTO.CTPhieuTraDTO;
import com.bookstore.views.Panel.PhieuTra;

public class PhieuTraController implements ItemListener, ActionListener{
    private PhieuTra pt;
    private Comparator<PhieuTraDTO> comparator =  Comparator.comparing(PhieuTraDTO:: getMaPhieuTra);;
    private List<PhieuTraDTO> manggoc;
    List<PhieuTraDTO> mangtmp;

    public PhieuTraController(PhieuTra pt){
        this.pt = pt;
        manggoc = pt.getListPhieuTra();
        mangtmp = pt.getListPhieuTra();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pt.btnTimKiem){
            String str = (String) pt.cbLuaChonTK.getSelectedItem();
            if (str != null){
                System.out.println("Da chon tim kiem");
                String key = pt.txtTimKiem.getText().trim();
                switch (str) {
                    case "Mã phiếu trả":
                        timMPT(key);
                        break;
                    case "Mã nhân viên":
                        timMNV(key);
                        break;
                    
                    case "Mã đọc giả":
                        timMDG(key);
                        break;
                    case "Mã phiếu mượn":
                        timMPM(key);
                        break;    
                    default:
                        break;
                }
            }
        }
        else if (e.getSource() == pt.sxtang){
            Collections.sort(mangtmp, comparator);
            pt.updateTable(mangtmp);
        }
        else if (e.getSource() == pt.sxgiam){
            Collections.sort(mangtmp, comparator.reversed());
            pt.updateTable(mangtmp);
        }
    }

    public void timMPT(String MaPT){
        List<PhieuTraDTO> listtmp = new ArrayList<>();
        for (PhieuTraDTO i: manggoc){
            if ((i.getMaPhieuTra() + "").contains(MaPT)){
                listtmp.add(i);
            }
        }
        mangtmp = listtmp;
        pt.updateTable(mangtmp);
    }   

    public void timMNV(String MaNV){
        List<PhieuTraDTO> listtmp = new ArrayList<>();
        for (PhieuTraDTO i: manggoc){
            if ((i.getMaNV()+ "").contains(MaNV)){
                listtmp.add(i);
            }
        }
        mangtmp = listtmp;
        pt.updateTable(mangtmp);
    }

    public void timMDG(String MaDG){

    }

    public void timMPM(String MaPhieuMuon){

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED){
            JComboBox<String> cbb = (JComboBox<String>) e.getSource();
            String str = (String) cbb.getSelectedItem();
            if (str != null){
                System.out.println("da nhan vao sap xep"+ str);
                switch (str) {
                    case "Mã phiếu trả":
                        comparator = Comparator.comparing(PhieuTraDTO:: getMaPhieuTra);
                        break;
                    
                    case "Ngày trả":
                        comparator = Comparator.comparing(PhieuTraDTO:: getNgayTra);
                        break;
                    
                    case "Mã nhân viên":
                        comparator = Comparator.comparing(PhieuTraDTO:: getMaNV);
                        break;

                    case "Mã đọc giả":System.out.println("1231231");
                        comparator = Comparator.comparing(PhieuTraDTO:: getMaDocGia);
                        break;

                    case "Mã phiếu mượn":
                        comparator = Comparator.comparing(PhieuTraDTO:: getMaPhieuMuon);
                        break;
                    
                    default:
                        break;
                }
                
                Collections.sort(mangtmp, comparator);
                pt.updateTable(mangtmp);
            }
        }
    }

}
