package com.bookstore.views.Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PhieuHuy extends JPanel {
    Color BackgroundColor = new Color(0, 2, 2);

    public void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        JLabel lichTrinhLabel = new JLabel("Lich Trinh", JLabel.CENTER); // Đảm bảo chữ hiển thị rõ
        lichTrinhLabel.setForeground(Color.WHITE); // Đặt màu chữ để nổi trên nền tối
        lichTrinhLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        this.add(lichTrinhLabel, BorderLayout.CENTER);
    }

    public PhieuHuy() {
        initComponent();
    }

}
