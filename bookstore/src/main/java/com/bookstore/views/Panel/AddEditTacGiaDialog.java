package com.bookstore.views.Panel;

import com.bookstore.DTO.TacGiaDTO;
import com.bookstore.dao.TacGiaDAO;

import java.awt.GridLayout;

import javax.swing.*;

public class AddEditTacGiaDialog extends JDialog {
    private JTextField txtMaTacGia, txtTenTacGia, txtNamSinh, txtQuocTich;
    private JButton btnSave;

    private TacGiaDTO tacGia; // Nếu null => thêm mới

    public AddEditTacGiaDialog(TacGiaDTO tacGia) {
        this.tacGia = tacGia;
        setTitle(tacGia == null ? "Thêm Tác Giả" : "Sửa Tác Giả");
        setSize(400, 350);  
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2));  

        add(new JLabel("Mã Tác Giả:"));
        txtMaTacGia = new JTextField();
        add(txtMaTacGia);

        add(new JLabel("Tên Tác Giả:"));
        txtTenTacGia = new JTextField();
        add(txtTenTacGia);

        add(new JLabel("Năm Sinh:"));
        txtNamSinh = new JTextField();
        add(txtNamSinh);

        add(new JLabel("Quốc Tịch:"));
        txtQuocTich = new JTextField();
        add(txtQuocTich);

        btnSave = new JButton("Lưu");
        add(btnSave);

        if (tacGia != null) {
            txtMaTacGia.setText(tacGia.getMaTacGia());
            txtMaTacGia.setEnabled(false);
            txtTenTacGia.setText(tacGia.getTenTacGia());
            txtNamSinh.setText(String.valueOf(tacGia.getNamSinh())); 
            txtQuocTich.setText(tacGia.getQuocTich()); 
        }

        btnSave.addActionListener(e -> saveData());
    }

    private void saveData() {
        TacGiaDAO dao = new TacGiaDAO();
        String ma = txtMaTacGia.getText().trim();
        String ten = txtTenTacGia.getText().trim();
        String namSinhStr = txtNamSinh.getText().trim();
        String quocTich = txtQuocTich.getText().trim();

        if (ma.isEmpty() || ten.isEmpty() || namSinhStr.isEmpty() || quocTich.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống!");
            return;
        }

        int namSinh = Integer.parseInt(namSinhStr); // EP KIEU

        if (tacGia == null) {
            // Thêm
            TacGiaDTO newTG = new TacGiaDTO(ma, ten, namSinh, quocTich);
            if (dao.insert(newTG) > 0) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        } else {
            // Sửa
            tacGia.setTenTacGia(ten);
            tacGia.setNamSinh(namSinh);
            tacGia.setQuocTich(quocTich);
            if (dao.update(tacGia) > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        }
    }
}
