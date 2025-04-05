package com.bookstore.views.Panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.*;
import javax.swing.border.MatteBorder;


import com.bookstore.DTO.TacGiaDTO;
import com.bookstore.dao.TacGiaDAO;
import com.bookstore.views.Component.Header;

public class TacGia extends JPanel {

    public TacGia() {
        initComponent();
    }

    public void initComponent() {
        this.setBackground(Color.BLUE);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(900, 600));
    
        // Thêm header
        Header header = new Header();
        header.setPreferredSize(new Dimension(850, 100));
        header.setMaximumSize(new Dimension(850, 100));
        header.setBackground(Color.RED);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        add(Box.createVerticalStrut(15));
        add(header);
        
        // Tạo tiêu đề với khoảng cách giữa các cột
        JPanel titlePanel = new JPanel(new GridLayout(1, 4, 10, 0)); // 1 dòng, 4 cột, cách nhau 10px
        titlePanel.setPreferredSize(new Dimension(850, 30));
        titlePanel.setMaximumSize(new Dimension(850, 30));
        titlePanel.setBackground(new Color(234, 237, 237));
    
        // Tạo các tiêu đề riêng lẻ
        JLabel lblMaTG = createTitleLabel("Mã TG");
        JLabel lblTen = createTitleLabel("Tên");
        JLabel lblNamSinh = createTitleLabel("Năm sinh");
        JLabel lblQuocTich = createTitleLabel("Quốc tịch");
    
        // Thêm vào titlePanel
        titlePanel.add(lblMaTG);
        titlePanel.add(lblTen);
        titlePanel.add(lblNamSinh);
        titlePanel.add(lblQuocTich);
    
        add(Box.createVerticalStrut(10)); 
        add(titlePanel);
        add(Box.createVerticalStrut(2));
    
        // Lấy danh sách tác giả từ database
        List<TacGiaDTO> list = new TacGiaDAO().selectAll();
        for (TacGiaDTO tg : list) {
            JPanel rowPanel = new JPanel(new GridLayout(1, 4, 10, 0));
            rowPanel.setPreferredSize(new Dimension(850, 25));
            rowPanel.setMaximumSize(new Dimension(850, 25));
            rowPanel.setBackground(Color.WHITE);
            rowPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.white));
    
            JLabel lblInfo1 = createDataLabel(tg.getMaTacGia());
            JLabel lblInfo2 = createDataLabel(tg.getTenTacGia());
            JLabel lblInfo3 = createDataLabel(String.valueOf(tg.getNamSinh()));
            JLabel lblInfo4 = createDataLabel(tg.getQuocTich());
    
            rowPanel.add(lblInfo1);
            rowPanel.add(lblInfo2);
            rowPanel.add(lblInfo3); 
            rowPanel.add(lblInfo4);
    
            add(rowPanel);
            add(Box.createVerticalStrut(3));
        }
    }
    
    // Hàm tạo label cho tiêu đề
    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setOpaque(true);
        label.setBackground(new Color(234, 237, 237));
        label.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(234, 237, 237)));
        return label;
    }
    
    // Hàm tạo label cho dữ liệu
    private JLabel createDataLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        return label;
    }
}