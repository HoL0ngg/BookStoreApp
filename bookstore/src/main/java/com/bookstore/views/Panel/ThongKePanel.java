package com.bookstore.views.Panel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.bookstore.BUS.PhieuMuonBUS;
import com.bookstore.BUS.PhieuNhapBUS;
import com.bookstore.BUS.PhieuTraBUS;
import com.bookstore.BUS.PhieuPhatBUS;
import com.bookstore.DTO.*;
import com.bookstore.dao.SachDAO;

public class ThongKePanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JPanel thongKeSoPanel;
    private JPanel bieuDoPanel;
    private List<PhieuMuonDTO> phieumuonlist;
    private List<PhieuPhatDTO> phieuphatlist;
    private List<PhieuTraDTO> phieutralist;

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
        cardLayout.show(contentPanel, "soLieu");

    }

    private JPanel createThongKeSoLieuPanel(int tongSach, int tongDocGia, int tongLuotMuon) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
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

        // Giả sử bạn có CTPhieuMuonBUS và SachBUS
        PhieuMuonBUS ctpmBUS = new PhieuMuonBUS();
        SachDAO sachDAO = new SachDAO();
        System.out.println(ctpmBUS.getCTPhieuMuon().size());
        System.out.println(sachDAO.selectAll().size());
        List<CTPhieuMuonDTO> dsCTPM = ctpmBUS.getCTPhieuMuon();
        List<SachDTO> dsSach = sachDAO.selectAll();

        // Map maSach -> tenSach
        Map<String, String> maSachToTenSach = new HashMap<>();
        for (SachDTO sach : dsSach) {
            maSachToTenSach.put(sach.getMaSach(), sach.getMaDauSach());
        }

        // Thống kê số lượt mượn mỗi sách
        Map<String, Integer> thongKe = new HashMap<>();
        for (CTPhieuMuonDTO ct : dsCTPM) {
            String maSach = ct.getMaSach();
            String tenSach = maSachToTenSach.getOrDefault(maSach, "Không rõ");

            thongKe.put(tenSach, thongKe.getOrDefault(tenSach, 0) + 1);
        }

        // Tạo PieDataset
        org.jfree.data.general.DefaultPieDataset<String> dataset = new org.jfree.data.general.DefaultPieDataset<>();
        for (Map.Entry<String, Integer> entry : thongKe.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        // Tạo PieChart
        org.jfree.chart.JFreeChart pieChart = org.jfree.chart.ChartFactory.createPieChart(
                "Thống kê lượt mượn theo đầu sách",
                dataset,
                true, true, false);

        org.jfree.chart.ChartPanel chartPanel = new org.jfree.chart.ChartPanel(pieChart);
        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }

    // private JPanel createBieuDoPanel() {
    // JPanel panel = new JPanel(new BorderLayout());

    // // Tạo biểu đồ bằng JFreeChart
    // org.jfree.data.category.DefaultCategoryDataset dataset = new
    // org.jfree.data.category.DefaultCategoryDataset();
    // dataset.addValue(1234, "Sách", "Thống kê");
    // dataset.addValue(500, "Độc giả", "Thống kê");
    // dataset.addValue(876, "Lượt mượn", "Thống kê");

    // org.jfree.chart.JFreeChart chart =
    // org.jfree.chart.ChartFactory.createBarChart(
    // "Biểu đồ Thống kê", "Loại", "Số lượng", dataset);

    // org.jfree.chart.ChartPanel chartPanel = new
    // org.jfree.chart.ChartPanel(chart);
    // panel.add(chartPanel, BorderLayout.CENTER);

    // return panel;
    // }
}
