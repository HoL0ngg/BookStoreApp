package com.bookstore.controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

import com.bookstore.DTO.PhieuTraDTO;
import com.bookstore.DTO.CTPhieuTraDTO;
import com.bookstore.views.Panel.PhieuTra;
import com.bookstore.dao.PhieuTraDAO;
import com.bookstore.DTO.TaiKhoanDTO;

public class PhieuTraController implements ItemListener, ActionListener{
    private PhieuTra pt;
    private PhieuTraDAO ptdao = new PhieuTraDAO();
    private TaiKhoanDTO tkDTO = new TaiKhoanDTO();
    private Comparator<PhieuTraDTO> comparator =  Comparator.comparing(PhieuTraDTO:: getMaPhieuTra);;
    private List<PhieuTraDTO> manggoc;
    List<PhieuTraDTO> mangtmp;

    public PhieuTraController(PhieuTra pt){
        this.pt = pt;
        manggoc = pt.getListpt();
        mangtmp = pt.getListpt();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pt.getBtnTimKiem()){
            String str = (String) pt.getCbLuaChonTK().getSelectedItem();
            if (str != null){
                System.out.println("Da chon tim kiem");
                String key = pt.getTxtTimKiem().getText().trim();
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
        else if (e.getSource() == pt.getSxtang()){
            Collections.sort(mangtmp, comparator);
            pt.updateTable(mangtmp);
        }
        else if (e.getSource() == pt.getSxgiam()){
            Collections.sort(mangtmp, comparator.reversed());
            pt.updateTable(mangtmp);
        }
        else if (e.getSource() == pt.getBtnSua()){
            int slt = pt.getTable().getSelectedRow();
            if (slt != -1){
                int mpt = Integer.parseInt(pt.getTxtMaPhieuTra().getText());
                String strnt = pt.getTxtNgayTra().getText();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date nt = new Date();
                try {
                    nt = sdf.parse(strnt);
                } catch (ParseException e1) {
                    System.out.println("khong hop le");
                    e1.printStackTrace();
                }
                String mnv = pt.getTxtMaNV().getText();
                String mdg = pt.getTxtMaDocGia().getText();
                int mpm = Integer.parseInt(pt.getTxtMaPhieuMuon().getText());
                PhieuTraDTO updatept = new PhieuTraDTO(mpt, nt, mnv, mdg, mpm);
                boolean kq = ptdao.capNhatPhieuTra(updatept);
                if (kq){
                    JOptionPane.showMessageDialog(null, "Cap nhat thanh cong");
                    int rowtmp = pt.getTable().getSelectedRow();
                    pt.setListpt(ptdao.layDanhSachPhieuTra());                    
                    pt.loadTableData();

                    if (rowtmp >= 0 && rowtmp < pt.getTable().getRowCount()){
                        pt.getTable().setRowSelectionInterval(rowtmp, rowtmp);
                    }

                    manggoc = pt.getListpt();
                    mangtmp = pt.getListpt();
                }
                else {
                    JOptionPane.showMessageDialog(null, "that bai");
                }
            }
            System.out.println("Da nhan vao btn Sua");
        }
        else if (e.getSource() == pt.getBtnXoa()){
            System.out.println("Da nhan vao btn Xoa");
            JDialog dialog = new JDialog((JFrame) null, "Xóa phiếu trả");
            dialog.setSize(400, 300);
            dialog.setLayout(new GridLayout(6, 2));

            String mpt = pt.getTxtMaPhieuTra().getText();

            JLabel message = new JLabel("Xác nhận xóa phiếu trả " + mpt);

            JButton confirm = new JButton("Xác nhận");
            JButton cancel = new JButton("Hủy");

            dialog.add(message);
            dialog.add(confirm);
            dialog.add(cancel);

            confirm.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    
                    int mpt = Integer.parseInt(pt.getTxtMaPhieuTra().getText());

                    if (ptdao.xoaPhieuTra(mpt)){
                        pt.setListpt(ptdao.layDanhSachPhieuTra());                    
                        pt.loadTableData();
                        JOptionPane.showMessageDialog(null, "Xoá thành công");
                        dialog.dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Xóa thất bại");
                        dialog.dispose();
                    }
                    manggoc = pt.getListpt();
                    mangtmp = pt.getListpt();

                    cancel.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e){
                            dialog.dispose();
                        }
                    });
                }
            });
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
        else if (e.getSource() == pt.getBtnThem()){
            System.out.println("Da nhan vao them");
            JDialog dialog = new JDialog((JFrame) null, "Thêm Phếu Trả", true);
            dialog.setSize(400,300);
            dialog.setLayout(new GridLayout(6, 2));

            JTextField txtfmaphieutra = new JTextField();
            JTextField txtfngaytra = new JTextField();
            JTextField txtfmanv = new JTextField();
            JTextField txtfmadocgia = new JTextField();
            JTextField txtfmaphieumuon = new JTextField();

            // Thời gian hiện tại
            LocalDate tmpday = LocalDate.now();
            DateTimeFormatter fomatdaytime = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fomatedday = tmpday.format(fomatdaytime);
            txtfngaytra.setText(fomatedday);
            txtfngaytra.setEditable(false);

            // Mã nhân viên hiện tại
            txtfmanv.setText("NV001");
            // txtfmanv.setText(tkDTO.getTenDangNhap());
            txtfmanv.setEditable(false);

            // Mã phiếu trả mới ?? có thể bỏ -> tự động thêm khi nhấn xác nhận
            int mptnew = 0;
            for (PhieuTraDTO i: pt.getListpt()){
                if (i.getMaPhieuTra() > mptnew ){
                    mptnew = i.getMaPhieuTra();
                }
            }
            txtfmaphieutra.setText(String.valueOf(mptnew + 1));
            txtfmaphieutra.setEditable(false);

            dialog.add(new JLabel("Mã phiếu trả: "));
            dialog.add(txtfmaphieutra);

            dialog.add(new JLabel("Ngày trả: "));
            dialog.add(txtfngaytra);

            dialog.add(new JLabel("Mã nhân viên: "));
            dialog.add(txtfmanv);

            dialog.add(new JLabel("Mã độc giả"));
            dialog.add(txtfmadocgia);

            dialog.add(new JLabel("Mã phiếu mượn: "));
            dialog.add(txtfmaphieumuon);

            JButton confirm = new JButton("Xác nhận");
            dialog.add(confirm);

            confirm.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try{
                        date = sdf.parse(fomatedday);
                    }
                    catch (ParseException e1){
                        System.out.println("loi ngay");
                    }
                    // tkDTO.getTenDangNhap() -> test
                    PhieuTraDTO insertPT = new PhieuTraDTO(Integer.parseInt(txtfmaphieutra.getText()), date, "NV001", txtfmadocgia.getText(), Integer.parseInt(txtfmaphieumuon.getText()));

                    if (ptdao.themPhieuTra(insertPT)){
                        pt.setListpt(ptdao.layDanhSachPhieuTra());                    
                        pt.loadTableData();
                        JOptionPane.showMessageDialog(null, "Thêm phiếu trả thành công");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Thêm phiếu trả thất bại");
                    }
                    manggoc = pt.getListpt();
                    mangtmp = pt.getListpt();

                    dialog.dispose();
                }
            }); 

            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

        }
    }

    public void timMPT(String MaPT){
        List<PhieuTraDTO> listtmp = new ArrayList<>();
        for (PhieuTraDTO i: pt.getListpt()){
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
