package com.bookstore.views.Panel;

import com.bookstore.DTO.TacGiaDTO;
import com.bookstore.dao.TacGiaDAO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;

public class AddEditTacGiaDialog extends JDialog {
    private JTextField txtMaTacGia, txtTenTacGia, txtNamSinh, txtQuocTich;
    private JButton btnSave;

    private TacGiaDTO tacGia; // Nếu null => thêm mới

    public AddEditTacGiaDialog(TacGiaDTO tacGia) {
        this.tacGia = tacGia;
        setTitle(tacGia == null ? "Thêm Tác Giả" : "Sửa Tác Giả");
        setSize(350, 350);  
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5)); 
        formPanel.add(new JLabel("Mã Tác Giả:"));
        txtMaTacGia = new JTextField();
        formPanel.add(txtMaTacGia);

        formPanel.add(new JLabel("Tên Tác Giả:"));
        txtTenTacGia = new JTextField();
        formPanel.add(txtTenTacGia);

        formPanel.add(new JLabel("Năm Sinh:"));
        txtNamSinh = new JTextField();
        formPanel.add(txtNamSinh);

        formPanel.add(new JLabel("Quốc Tịch:"));
        txtQuocTich = new JTextField();
        formPanel.add(txtQuocTich);

        add(formPanel, BorderLayout.CENTER); 


        btnSave = new JButton("LƯU");
        btnSave.setFont(new Font("Arial", Font.BOLD, 18));
        btnSave.setPreferredSize(new Dimension(100, 40));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSave);
        add(buttonPanel, BorderLayout.SOUTH); // network


        if (tacGia != null) { // Nếu thông tin đã tồn tại
            txtMaTacGia.setText(tacGia.getMaTacGia());
            txtMaTacGia.setEnabled(false); // Block primary key
            txtTenTacGia.setText(tacGia.getTenTacGia());
            txtNamSinh.setText(String.valueOf(tacGia.getNamSinh())); 
            txtQuocTich.setText(tacGia.getQuocTich()); 
        }

        btnSave.addActionListener(e -> saveData()); // 
    }

    private void saveData() {
        TacGiaDAO dao = new TacGiaDAO();
        String ma = txtMaTacGia.getText().trim();
        String ten = txtTenTacGia.getText().trim();
        String namSinhStr = txtNamSinh.getText().trim();
        String quocTich = txtQuocTich.getText().trim();

        StringBuilder errorMessage = new StringBuilder();

        if (ma.isEmpty() || ten.isEmpty() || namSinhStr.isEmpty() || quocTich.isEmpty()) {
            errorMessage.append( "- Không được để trống!\n");
        }

        // Check dinh dang and ton tai trong csdl
        if (!ma.matches("^TG\\d+$")) {
            errorMessage.append("- Mã tác giả phải bắt đầu bằng 'TG' theo sau là số.\n");
        } else if (tacGia == null && dao.selectById(ma) != null) {
            errorMessage.append("- Mã tác giả đã tồn tại trong hệ thống.\n");
        }

        // Kiểm tra tên không chứa ký tự đặc biệt
        if (!ten.matches("^[a-zA-ZÀ-ỹ\\s]+$")) {
            errorMessage.append("- Tên tác giả không được chứa ký hiệu đặc biệt.\n");
        }

        // Kiểm tra năm sinh
        int namSinh = -1;
        try {
            namSinh = Integer.parseInt(namSinhStr);
            if (namSinh < 1600 || namSinh > 2004) {
                errorMessage.append( "- Năm sinh phải từ 1600 đến 2004");
            }
        } catch (NumberFormatException e) {
            errorMessage.append( "- Năm sinh phải là số hợp lệ!");
        }

        // Kiểm tra quoc tich không chứa ký tự đặc biệt
        if (!quocTich.matches("^[a-zA-ZÀ-ỹ\\s]+$")) {
            errorMessage.append("- Quốc tịch không được chứa ký hiệu đặc biệt.\n");
        }

        // Tự động in hoa chữ cái đầu mỗi từ
        ten = capitalizeWords(ten);
        quocTich = capitalizeWords(quocTich);

        // Show error
        if (errorMessage.length() > 0) {
            JTextArea textArea = new JTextArea(errorMessage.toString());
            textArea.setEditable(false);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setFont(new Font("Arial", Font.PLAIN, 16)); 
            textArea.setRows(10); 
            textArea.setColumns(30); 

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(400, 150));

            JOptionPane.showMessageDialog(this, scrollPane, "Thông báo lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (tacGia == null) {
            // Thêm mới
            TacGiaDTO newTG = new TacGiaDTO(ma, ten, namSinh, quocTich);
            if (dao.insert(newTG) > 0) {
                errorMessage.append( "Thêm thành công!");
                dispose();
            } else {
                errorMessage.append( "Thêm thất bại!");
            }
        } else {
            // Sửa
            tacGia.setTenTacGia(ten);
            tacGia.setNamSinh(namSinh);
            tacGia.setQuocTich(quocTich);
            if (dao.update(tacGia) > 0) {
                errorMessage.append( "Cập nhật thành công!");
                dispose();
            } else {
                errorMessage.append( "Cập nhật thất bại!");
            }
        }
    }


    private String capitalizeWords(String input) {
        String[] words = input.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                  .append(word.substring(1).toLowerCase())
                  .append(" ");
            }
        }
        return sb.toString().trim();
    }
    
}
