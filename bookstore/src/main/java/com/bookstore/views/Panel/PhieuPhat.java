package com.bookstore.views.Panel;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.poi.ss.usermodel.Color;

public class PhieuPhat extends JPanel {
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
