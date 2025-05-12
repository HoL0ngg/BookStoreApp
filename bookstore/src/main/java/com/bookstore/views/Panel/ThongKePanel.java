package com.bookstore.views.Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThongKePanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JPanel thongKeSoPanel;
    private JPanel bieuDoPanel;

    public ThongKePanel() {
        setLayout(new BorderLayout());

        // Top: Bộ lọc tháng năm
        JPanel filterPanel = new JPanel();
        JComboBox<String> cbThang = new JComboBox<>();
        JComboBox<String> cbNam = new JComboBox<>();

        for (int i = 1; i <= 12; i++)
            cbThang.addItem("Tháng " + i);
        for (int year = 2024; year <= 2025; year++)
            cbNam.addItem("Năm " + year);

        JButton btnXemSoLieu = new JButton("Xem Số Liệu");
        JButton btnXemBieuDo = new JButton("Xem Biểu Đồ");

        filterPanel.add(cbThang);
        filterPanel.add(cbNam);
        filterPanel.add(btnXemSoLieu);
        filterPanel.add(btnXemBieuDo);

        // Card layout panel
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // Thống kê số liệu
        thongKeSoPanel = createThongKeSoLieuPanel(1234, 500, 876); // giả lập

        // Biểu đồ
        bieuDoPanel = createBieuDoPanel();

        contentPanel.add(thongKeSoPanel, "soLieu");
        contentPanel.add(bieuDoPanel, "bieuDo");

        add(filterPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        // Event chuyển tab
        btnXemSoLieu.addActionListener(e -> cardLayout.show(contentPanel, "soLieu"));
        btnXemBieuDo.addActionListener(e -> cardLayout.show(contentPanel, "bieuDo"));
    }

    private JPanel createThongKeSoLieuPanel(int tongSach, int tongDocGia, int tongLuotMuon) {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JLabel lblTongSach = new JLabel("Tổng số sách: " + tongSach);
        JLabel lblTongDocGia = new JLabel("Tổng số độc giả: " + tongDocGia);
        JLabel lblTongLuotMuon = new JLabel("Tổng số lượt mượn: " + tongLuotMuon);

        Font font = new Font("Segoe UI", Font.BOLD, 18);
        lblTongSach.setFont(font);
        lblTongDocGia.setFont(font);
        lblTongLuotMuon.setFont(font);

        panel.add(lblTongSach);
        panel.add(lblTongDocGia);
        panel.add(lblTongLuotMuon);

        return panel;
    }

    private JPanel createBieuDoPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Tạo biểu đồ bằng JFreeChart
        org.jfree.data.category.DefaultCategoryDataset dataset = new org.jfree.data.category.DefaultCategoryDataset();
        dataset.addValue(1234, "Sách", "Thống kê");
        dataset.addValue(500, "Độc giả", "Thống kê");
        dataset.addValue(876, "Lượt mượn", "Thống kê");

        org.jfree.chart.JFreeChart chart = org.jfree.chart.ChartFactory.createBarChart(
                "Biểu đồ Thống kê", "Loại", "Số lượng", dataset);

        org.jfree.chart.ChartPanel chartPanel = new org.jfree.chart.ChartPanel(chart);
        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }
}
