package com.bookstore.views.Panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.util.List;

import javax.swing.JPanel;

import java.awt.event.MouseEvent;

import com.bookstore.DTO.DauSachDTO;
import com.bookstore.dao.DauSachDAO;

public class DSDauSach extends JPanel {
    List<DauSachDTO> list = new DauSachDAO().selectAll();

    public DSDauSach() {
        init();
    }

    private void init() {
        CardLayout cardLayout = new CardLayout();
        this.setLayout(cardLayout);

        JPanel DSDauSachPanel = new JPanel();

        DSDauSachPanel.setLayout(new FlowLayout(0, 32, 40));
        DSDauSachPanel.setPreferredSize(new Dimension(900, ((list.size() + 5) / 3) * 300));
        DSDauSachPanel.setBackground(Color.white);

        ThongTinDauSach[] ThongTinDauSach = new ThongTinDauSach[list.size()];

        for (int i = 0; i < list.size(); ++i) {
            final int index = i;
            ThongTinDauSach[i] = new ThongTinDauSach(list.get(index));
            ThongTinDauSach[i].addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    hienThiChiTiet(ThongTinDauSach[index].getMaDauSach());
                }
            });
            DSDauSachPanel.add(ThongTinDauSach[i]);
        }

        ChiTietDauSach ChiTietPanel = new ChiTietDauSach(0);
        ChiTietPanel.setLayout(new BorderLayout());

        this.add(DSDauSachPanel, "Danhsach");
        this.add(ChiTietPanel, "Chitiet");
    }

    private void hienThiChiTiet(int maDauSach) {

    }
}
