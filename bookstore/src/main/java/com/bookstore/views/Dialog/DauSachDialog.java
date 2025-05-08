package com.bookstore.views.Dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

public class DauSachDialog extends JDialog {
    private JTextField txtMaDauSach;
    private JTextField txtTenDauSach;
    private JLabel lblHinhAnh;
    private JButton btnChonHinh;
    private JComboBox<String> cboNhaXuatBan;
    private JTextField txtNamXuatBan;
    private JComboBox<String> cboNgonNgu;
    private JTextField txtSoTrang;

    private String hinhAnhPath = null;

    public DauSachDialog(Frame parent, int nextMaDauSach, List<String> dsNhaXuatBan) {
        super(parent, "Thêm đầu sách mới", true);
        setSize(500, 600);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Mã đầu sách
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Mã đầu sách:"), gbc);
        gbc.gridx = 1;
        txtMaDauSach = new JTextField(String.valueOf(nextMaDauSach));
        txtMaDauSach.setEditable(false);
        add(txtMaDauSach, gbc);

        // Tên đầu sách
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Tên đầu sách:"), gbc);
        gbc.gridx = 1;
        txtTenDauSach = new JTextField();
        add(txtTenDauSach, gbc);

        // Hình ảnh
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Hình ảnh:"), gbc);
        gbc.gridx = 1;
        JPanel panelHinh = new JPanel(new BorderLayout());
        lblHinhAnh = new JLabel("Chưa chọn", JLabel.CENTER);
        lblHinhAnh.setPreferredSize(new Dimension(150, 150));
        lblHinhAnh.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelHinh.add(lblHinhAnh, BorderLayout.CENTER);
        btnChonHinh = new JButton("Chọn ảnh");
        btnChonHinh.addActionListener(e -> chonHinh());
        panelHinh.add(btnChonHinh, BorderLayout.SOUTH);
        add(panelHinh, gbc);

        // Nhà xuất bản
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Nhà xuất bản:"), gbc);
        gbc.gridx = 1;
        cboNhaXuatBan = new JComboBox<>();
        for (String nxb : dsNhaXuatBan) {
            cboNhaXuatBan.addItem(nxb);
        }
        add(cboNhaXuatBan, gbc);

        // Năm xuất bản
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Năm xuất bản:"), gbc);
        gbc.gridx = 1;
        txtNamXuatBan = new JTextField();
        add(txtNamXuatBan, gbc);

        // Ngôn ngữ
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Ngôn ngữ:"), gbc);
        gbc.gridx = 1;
        cboNgonNgu = new JComboBox<>(new String[] { "Tiếng Việt", "Tiếng Anh", "Pháp", "Trung", "Khác" });
        add(cboNgonNgu, gbc);

        // Số trang
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Số trang:"), gbc);
        gbc.gridx = 1;
        txtSoTrang = new JTextField();
        add(txtSoTrang, gbc);

        // Nút thao tác
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnLuu = new JButton("Lưu");
        btnLuu.addActionListener(e -> luuDauSach());
        add(btnLuu, gbc);
    }

    private void chonHinh() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            hinhAnhPath = file.getAbsolutePath();
            ImageIcon icon = new ImageIcon(
                    new ImageIcon(hinhAnhPath).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
            lblHinhAnh.setText("");
            lblHinhAnh.setIcon(icon);
        }
    }

    private void luuDauSach() {
        String ten = txtTenDauSach.getText().trim();
        String nxb = (String) cboNhaXuatBan.getSelectedItem();
        String nam = txtNamXuatBan.getText().trim();
        String ngonNgu = (String) cboNgonNgu.getSelectedItem();
        String soTrang = txtSoTrang.getText().trim();

        // Validate đơn giản
        if (ten.isEmpty() || nam.isEmpty() || soTrang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        // TODO: Gọi DAO hoặc BUS để lưu dữ liệu

        JOptionPane.showMessageDialog(this, "Thêm đầu sách thành công!");
        dispose();
    }
}
