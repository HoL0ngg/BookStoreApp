package com.bookstore.views.Panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import com.bookstore.DTO.SachDTO;
import com.bookstore.views.Component.RoundedPanel;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class ThongTinSach extends RoundedPanel {
    private SachDTO dausach;

    public ThongTinSach(SachDTO dausach) {
        super(20);
        this.dausach = dausach;
        initComponent(dausach);
    }

    private void initComponent(SachDTO Sach) {
        this.setPreferredSize(new Dimension(240, 240));
        this.setBackground(Color.white);
        this.setLayout(null);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        FlatSVGIcon icon = new FlatSVGIcon(getClass().getResource("/svg/book.svg")).derive(100, 100);
        JLabel HinhAnhSach = new JLabel(icon);
        HinhAnhSach.setBounds(75, 10, 100, 100);
        this.add(HinhAnhSach);

        JLabel MaDauSach = new JLabel("Mã sách: " + Sach.getMaDauSach());
        MaDauSach.setBounds(10, 120, 200, 30);
        MaDauSach.setFont(new Font("Arial", Font.BOLD, 14));

        TrangThaiSach trangthai = new TrangThaiSach(Sach.getTrangThai());
        trangthai.setBounds(10, 190, 200, 30);
        trangthai.setFont(new Font("Arial", Font.BOLD, 14));

        this.add(MaDauSach);
        this.add(trangthai);
    }
}
