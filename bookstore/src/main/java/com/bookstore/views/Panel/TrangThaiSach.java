package com.bookstore.views.Panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class TrangThaiSach extends JPanel {
    private String trangthai;

    public TrangThaiSach(String trangthai) {
        super();
        this.trangthai = trangthai;
        initComponent(trangthai);
    }

    private void initComponent(String trangthai) {
        this.setPreferredSize(new Dimension(200, 30));
        this.setBackground(Color.white);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Bật khử răng cưa để vẽ đẹp hơn
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Vẽ vòng tròn trạng thái
        switch (trangthai) {
            case "Đang hoạt động":
                g2d.setColor(Color.green);
                break;
            case "Đang bảo trì":
                g2d.setColor(Color.orange);
                break;
            case "Ngừng hoạt động":
                g2d.setColor(Color.red);
                break;
        }
        g2d.fillOval(10, 10, 20, 20);

        // Vẽ chữ trạng thái
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString(trangthai, 40, 25);
    }
}
