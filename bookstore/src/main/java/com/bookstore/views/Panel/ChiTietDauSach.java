package com.bookstore.views.Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bookstore.DTO.SachDTO;
import com.bookstore.dao.SachDAO;

public class ChiTietDauSach extends JPanel {
    private JLabel backButton;
    private int maDauSach;

    public ChiTietDauSach(int maDauSach) {
        init();
        this.maDauSach = maDauSach;
    }

    private void init() {
        List<SachDTO> list = new SachDAO().selectAll();
        this.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBackground(Color.white);
        headerPanel.setPreferredSize(new Dimension(900, 50));
        this.add(headerPanel, BorderLayout.NORTH);

        JPanel MainPanel = new JPanel();
        MainPanel.setLayout(new FlowLayout(0, 32, 40));
        MainPanel.setPreferredSize(new Dimension(900, ((list.size() + 5) / 3) * 300));
        MainPanel.setBackground(Color.white);

        ThongTinSach[] ThongTinSach = new ThongTinSach[list.size()];

        for (int i = 0; i < list.size(); ++i) {
            ThongTinSach[i] = new ThongTinSach(list.get(i));
            MainPanel.add(ThongTinSach[i]);
        }
    }

}
