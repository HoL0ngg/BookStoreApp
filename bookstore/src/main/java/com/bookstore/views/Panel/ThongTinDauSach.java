package com.bookstore.views.Panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import com.bookstore.DTO.DauSachDTO;
import com.bookstore.views.Component.RoundedPanel;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class ThongTinDauSach extends RoundedPanel {
    private DauSachDTO dausach;

    public ThongTinDauSach(DauSachDTO dausach) {
        super(20);
        this.dausach = dausach;
        initComponent(dausach);
    }

    private void initComponent(DauSachDTO DauSach) {
        this.setPreferredSize(new Dimension(240, 240));
        this.setBackground(Color.white);
        this.setLayout(null);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        FlatSVGIcon icon = new FlatSVGIcon(getClass().getResource("/svg/book.svg")).derive(100, 100);
        JLabel HinhAnhSach = new JLabel(icon);
        HinhAnhSach.setBounds(75, 10, 100, 100);
        this.add(HinhAnhSach);

        JLabel MaDauSach = new JLabel("Mã đầu sách: " + DauSach.getMaDauSach());
        MaDauSach.setBounds(10, 120, 200, 30);
        MaDauSach.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel TenDauSach = new JLabel("Tựa đề: " + DauSach.getTenDauSach());
        TenDauSach.setBounds(10, 140, 200, 30);
        TenDauSach.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel SoTrang = new JLabel("Số trang: " + DauSach.getSoTrang());
        SoTrang.setBounds(10, 160, 200, 30);
        SoTrang.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel NhaXuatBan = new JLabel("Nhà xuất bản: " + DauSach.getNhaXuatBan());
        NhaXuatBan.setBounds(10, 180, 230, 30);
        NhaXuatBan.setFont(new Font("Arial", Font.BOLD, 14));

        this.add(MaDauSach);
        this.add(SoTrang);
        this.add(TenDauSach);
        this.add(NhaXuatBan);
    }

    public int getMaDauSach() {
        return this.dausach.getMaDauSach();
    }
}
