package com.bookstore.views.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import java.awt.*;

public class NhaCungCap extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtTimKiem;
    private JComboBox<String> cboFilter;
    private JPanel btnThem, btnSua, btnXoa, btnChiTiet, btnXuatExcel, btnLamMoi;

    public NhaCungCap() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        // NORTH PANEL (buttons + filter)
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

        btnThem = createPanel("Thêm", "add");
        btnSua = createPanel("Sửa", "edit");
        btnXoa = createPanel("Xóa", "delete");
        btnChiTiet = createPanel("Chi tiết", "ChiTiet");
        btnXuatExcel = createPanel("Xuất Excel", "importExcel");
        btnLamMoi = createPanel("Làm mới", "refresh");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnChiTiet);
        buttonPanel.add(btnXuatExcel);

        // Right filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        cboFilter = new JComboBox<>(
                new String[] { "Tất cả", "Mã NCC", "Tên NCC", "Số điện thoại", "Email", "Địa chỉ" });
        txtTimKiem = new JTextField(15);

        filterPanel.add(cboFilter);
        filterPanel.add(txtTimKiem);
        filterPanel.add(btnLamMoi);

        northPanel.add(buttonPanel, BorderLayout.WEST);
        northPanel.add(filterPanel, BorderLayout.EAST);
        add(northPanel, BorderLayout.NORTH);

        // CENTER (table)
        String[] columns = {
                "Mã NCC", "Tên NCC", "Số điện thoại", "Email", "Địa chỉ"
        };
        Object[][] data = {
                { "NCC01", "Nhà sách Trẻ", "0901234567", "ncc1@mail.com", "Q1, TP.HCM" },
                { "NCC02", "Alpha Books", "0912345678", "ncc2@mail.com", "Q3, TP.HCM" },
                { "NCC03", "NXB Giáo Dục", "0923456789", "ncc3@mail.com", "Hà Nội" },
        };

        tableModel = new DefaultTableModel(data, columns);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createPanel(String text, String iconName) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(72, 80));
        panel.setBackground(Color.white);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        FlatSVGIcon icon = new FlatSVGIcon(getClass().getResource("/svg/" + iconName + ".svg")).derive(30, 30);
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(new JLabel(icon));
        panel.add(label);
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        return panel;
    }

    // Getter nếu cần thao tác từ bên ngoài
    public JTable getTable() {
        return table;
    }

    public JPanel getBtnThem() {
        return btnThem;
    }

    public JPanel getBtnSua() {
        return btnSua;
    }

    public JPanel getBtnXoa() {
        return btnXoa;
    }

    public JPanel getBtnChiTiet() {
        return btnChiTiet;
    }

    public JPanel getBtnXuatExcel() {
        return btnXuatExcel;
    }

    public JPanel getBtnLamMoi() {
        return btnLamMoi;
    }

    public JComboBox<String> getCboFilter() {
        return cboFilter;
    }

    public JTextField getTxtTimKiem() {
        return txtTimKiem;
    }
}
