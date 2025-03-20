package com.bookstore.views.Component;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class itemTaskbar extends JPanel implements MouseListener {
    // Biến tĩnh lưu taskbar đang được chọn
    private static itemTaskbar selectedTaskbar = null;

    // Màu sắc: mặc định, hover, và khi được chọn
    private final Color fontColor = new Color(0, 0, 0);
    private final Color defaultColor = new Color(148, 183, 205);
    private final Color hoverFontColor = new Color(255, 255, 255);
    private final Color hoverBackgroundColor = new Color(80, 138, 170);
    private final Color selectedBackgroundColor = new Color(60, 120, 160);
    private final Color selectedFontColor = new Color(255, 255, 255);

    public final JLabel lblIcon, lblContent;
    private boolean isSelected = false;

    public itemTaskbar(String linkIcon, String content) {
        // Khởi tạo giao diện
        this.setOpaque(true);
        this.setBackground(defaultColor);
        this.setLayout(new BorderLayout());
        this.addMouseListener(this);

        // Thiết lập icon
        lblIcon = new JLabel();
        lblIcon.setPreferredSize(new Dimension(60, 30));
        lblIcon.setBorder(new EmptyBorder(0, 20, 0, 0));
        URL iconUrl = getClass().getResource("/svg/" + linkIcon);
        if (iconUrl != null) {
            lblIcon.setIcon(new FlatSVGIcon(iconUrl).derive(30, 30));
        } else {
            System.err.println("Không tìm thấy icon: /svg/" + linkIcon);
            lblIcon.setText("X");
        }
        lblIcon.setHorizontalAlignment(JLabel.CENTER);
        this.add(lblIcon, BorderLayout.WEST);

        // Thiết lập nhãn nội dung
        lblContent = new JLabel(content);
        lblContent.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblContent.setForeground(fontColor);
        lblContent.setBorder(new EmptyBorder(0, 40, 0, 0));
        this.add(lblContent, BorderLayout.CENTER);
    }

    // Phương thức cập nhật trạng thái chọn
    public void setSelected(boolean selected) {
        this.isSelected = selected;
        if (selected) {
            this.setBackground(selectedBackgroundColor);
            lblContent.setForeground(selectedFontColor);
        } else {
            this.setBackground(defaultColor);
            lblContent.setForeground(fontColor);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Khi click, nếu taskbar hiện tại chưa được chọn, cập nhật trạng thái chọn
        if (selectedTaskbar != null && selectedTaskbar != this) {
            selectedTaskbar.setSelected(false);
        }
        setSelected(true);
        selectedTaskbar = this;
        // Nếu cần, có thể gọi một callback để thông báo sự thay đổi cho parent
        // container
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Nếu chưa được chọn, hiển thị màu hover
        if (!isSelected) {
            this.setBackground(hoverBackgroundColor);
            lblContent.setForeground(hoverFontColor);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Nếu chưa được chọn, trở về màu mặc định
        if (!isSelected) {
            this.setBackground(defaultColor);
            lblContent.setForeground(fontColor);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}
