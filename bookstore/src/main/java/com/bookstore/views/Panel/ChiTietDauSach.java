package com.bookstore.views.Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.bookstore.DTO.SachDTO;
import com.bookstore.dao.SachDAO;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class ChiTietDauSach extends JPanel {
    private JLabel back;
    private JTable table;
    private DefaultTableModel tableModel;

    public ChiTietDauSach(int maDauSach) {
        init(maDauSach);
    }

    private void init(int maDauSach) {
        List<SachDTO> list = new SachDAO().selectAll();
        this.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBackground(Color.white);
        headerPanel.setPreferredSize(new Dimension(900, 50));
        this.add(headerPanel, BorderLayout.NORTH);

        FlatSVGIcon backIcon = new FlatSVGIcon(getClass().getResource("/svg/back-button.svg")).derive(30, 30);
        back = new JLabel(backIcon);
        back.setBounds(10, 10, 30, 30);
        headerPanel.add(back);

        JLabel backText = new JLabel("Quay lại");
        backText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        backText.setBounds(50, 10, 100, 30);
        headerPanel.add(backText);

        String column[] = new String[] { "Mã sách", "Ngày nhập", "Trạng thái" };
        tableModel = new DefaultTableModel(column, 0);
        table = new JTable(tableModel);

        // Định dạng ngày tháng
        // SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Thêm dữ liệu từ list vào bảng
        for (SachDTO sach : list) {
            tableModel.addRow(new Object[] {
                    sach.getMasach(),
                    sach.getNgayNhap(),
                    sach.getTrangThai()
            });
            System.out.println(sach.getMaSach());
        }

        // Đưa bảng vào JScrollPane để có thanh cuộn
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public JLabel getBack() {
        return back;
    }

}
