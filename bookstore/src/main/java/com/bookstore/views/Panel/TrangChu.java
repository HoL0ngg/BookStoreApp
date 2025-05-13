package com.bookstore.views.Panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.bookstore.dao.DauSachDAO;
import com.bookstore.dao.NCCDAO;
import com.bookstore.views.Component.RoundedPanel;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class TrangChu extends JPanel {
    Color BackgroundColor = new Color(0, 2, 2);

    public void initComponent() {
        // Panel Trang chu
        this.setLayout(null);
        this.setBackground(Color.white);
        this.setSize(900, 760);

        JLabel TrangChuLabel = new JLabel(
                "<html><div style='text-align: center; font-weight: 700; font-size: larger;'>HỆ THỐNG QUẢN LÝ THƯ VIỆN</div><div style='text-align: center; font-weight: 400; font-size: smaller; margin-top: 4px;'>- SÁCH LÀ NGUỒN KIẾN THỨC VÔ TẬN -</div></html>",
                JLabel.CENTER);
        TrangChuLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        TrangChuLabel.setForeground(Color.black);
        TrangChuLabel.setBounds(0, 0, 900, 140);
        this.add(TrangChuLabel);

        JPanel ThongKePanel = new JPanel();
        ThongKePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
        ThongKePanel.setBackground(Color.decode("#BDCDD6"));
        ThongKePanel.setBounds(0, 140, 900, 620);
        this.add(ThongKePanel);

        RoundedPanel TongSoSachPanel = CreatePanel("ĐẦU SÁCH", "book2", new DauSachDAO().selectAll().size());
        ThongKePanel.add(TongSoSachPanel);

        RoundedPanel SoDocGiaPanel = CreatePanel("ĐỘC GIẢ", "reader", 69);
        ThongKePanel.add(SoDocGiaPanel);

        RoundedPanel SoTuyenDuongPanel = CreatePanel("NHÀ CUNG CẤP", "supplier", new NCCDAO().selectAll().size());
        ThongKePanel.add(SoTuyenDuongPanel);
    }

    public TrangChu() {
        initComponent();
    }

    private RoundedPanel CreatePanel(String title, String img, int soluong) {
        RoundedPanel TongSoSachPanel = new RoundedPanel(20);
        TongSoSachPanel.setLayout(new FlowLayout(1, 0, 50));
        TongSoSachPanel.setBackground(Color.decode("#93BFCF"));
        TongSoSachPanel.setPreferredSize(new Dimension(250, 350));
        TongSoSachPanel.setBorder(new LineBorder(Color.black, 2));

        JLabel TongSoSachLabel = new JLabel(soluong + " " + title, JLabel.CENTER);
        // TongSoSachLabel.setBounds(0, 200, 250, 50);
        TongSoSachLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        TongSoSachLabel.setForeground(Color.black);

        FlatSVGIcon icon2 = new FlatSVGIcon(getClass().getResource("/svg/" + img + ".svg")).derive(150, 150);
        JLabel hehe = new JLabel(icon2, JLabel.CENTER);
        // hehe.setBounds(50, 30, 150, 150);
        TongSoSachPanel.add(hehe);
        TongSoSachPanel.add(TongSoSachLabel);
        return TongSoSachPanel;
    }
}
