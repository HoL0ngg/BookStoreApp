package com.bookstore.views.Panel;
import com.bookstore.DTO.TacGiaDTO;
import com.bookstore.dao.TacGiaDAO;

import javax.swing.*;
import java.awt.*;

public class EventTacGia extends JDialog {

    private JTextField txtMaTG;
    private JTextField txtTenTG;
    private JTextField txtNamSinh;
    private JTextField txtQuocTich;

    private JButton btnSave;
    private JButton btnCancel;

    private TacGiaDTO tacGia; 

    public EventTacGia(TacGiaDTO tg) {
        this.tacGia = tg;
        setTitle(tg == null ? "Thêm tác giả" : "Sửa tác giả");
        setModal(true);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initComponent();

        if (tg != null) {
            fillData(tg);
        }

        btnSave.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> dispose());
    }

    private void initComponent() {
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Mã TG:"));
        txtMaTG = new JTextField();
        formPanel.add(txtMaTG);

        formPanel.add(new JLabel("Tên TG:"));
        txtTenTG = new JTextField();
        formPanel.add(txtTenTG);

        formPanel.add(new JLabel("Năm sinh:"));
        txtNamSinh = new JTextField();
        formPanel.add(txtNamSinh);

        formPanel.add(new JLabel("Quốc tịch:"));
        txtQuocTich = new JTextField();
        formPanel.add(txtQuocTich);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        if (tacGia != null) {
            txtMaTG.setEditable(false); // khóa nếu sửa
        }
    }

    private void fillData(TacGiaDTO tg) {
        txtMaTG.setText(tg.getMaTacGia());
        txtTenTG.setText(tg.getTenTacGia());
        txtNamSinh.setText(String.valueOf(tg.getNamSinh()));
        txtQuocTich.setText(tg.getQuocTich());
    }

    private void onSave() {
        String maTG = txtMaTG.getText().trim();
        String tenTG = txtTenTG.getText().trim();
        String namSinhStr = txtNamSinh.getText().trim();
        String quocTich = txtQuocTich.getText().trim();

        if (maTG.isEmpty() || tenTG.isEmpty() || namSinhStr.isEmpty() || quocTich.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        try {
            int namSinh = Integer.parseInt(namSinhStr);

            TacGiaDAO dao = new TacGiaDAO();
            if (tacGia == null) {
                // thêm mới
                TacGiaDTO newTG = new TacGiaDTO(maTG, tenTG, namSinh, quocTich);
                dao.insert(newTG);
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
            } else {
                // cập nhật
                tacGia.setTenTacGia(tenTG);
                tacGia.setNamSinh(namSinh);
                tacGia.setQuocTich(quocTich);
                dao.update(tacGia);
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            }

            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Năm sinh phải là số.");
        }
    }
}

