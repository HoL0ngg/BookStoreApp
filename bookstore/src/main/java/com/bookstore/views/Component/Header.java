package com.bookstore.views.Component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.bookstore.views.Panel.SearchText;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class Header extends JPanel {
    private JButton btnAdd, btnEdit, btnDelete, btnDetail, btnImport, btnExport;
    private JComboBox<String> sortComboBox;
    private JButton btnRefresh;
    private SearchText searchField;

    public JButton getBtnRefresh() {
        return btnRefresh;
    }

    public SearchText getSearchField() {
        return searchField;
    }

    public JComboBox<String> getSortComboBox() {
        return sortComboBox;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnDetail() {
        return btnDetail;
    }

    public JButton getBtnImport() {
        return btnImport;
    }

    public JButton getBtnExport() {
        return btnExport;
    }

    public Header() {
        initComponent();
        setOpaque((false)); // Luon trong suot de background color
    }

    public void initComponent() {
        // Tạo các nút JButton
        btnAdd = createButton("THÊM", "add");
        btnEdit = createButton("SỬA", "edit");
        btnDelete = createButton("XÓA", "delete");
        btnDetail = createButton("CHI TIẾT", "ChiTiet");
        btnExport = createButton("XUẤT EXCEL", "importExcel");

        // add
        add(btnAdd);
        add(btnEdit);
        add(btnDelete);
        add(btnDetail);
        add(btnExport);

        setLayout(new FlowLayout(FlowLayout.LEFT, 8, 10)); // can goc trai 20px và height 10px

        // Sort and search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setOpaque(false);

        // Sort ComboBox
        sortComboBox = new JComboBox<>();
        sortComboBox.addItem("Tất cả");
        sortComboBox.addItem("Mã TG");
        sortComboBox.addItem("Tên TG");
        sortComboBox.addItem("Ngày sinh");
        sortComboBox.addItem("Quốc tịch");
        sortComboBox.setPreferredSize(new Dimension(135, 40));
        sortComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        sortComboBox.setBackground(Color.WHITE);
        sortComboBox.setFocusable(false); // close tab
        sortComboBox.setOpaque(false);
        sortComboBox.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));

        // Custom UI bo góc
        sortComboBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(sortComboBox.getBackground());
                g2.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 5, 5);
            }
        });

        // Renderer để style các item trong danh sách dropdown
        sortComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
                        cellHasFocus);
                label.setOpaque(true);
                label.setBackground(isSelected ? new Color(220, 220, 220) : Color.WHITE);
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return label;
            }
        });

        // Search
        searchField = new SearchText();
        searchField.setPreferredSize(new Dimension(180, 40));

        searchPanel.add(sortComboBox);
        searchPanel.add(searchField);

        add(searchPanel);

        btnRefresh = new JButton("LÀM MỚI"); // ADD CENTER
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 12));
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBorderPainted(false);
        btnRefresh.setBackground(new Color(215, 219, 221));
        btnRefresh.setPreferredSize(new Dimension(100, 40));

        add(btnRefresh);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        // g2.fillRect(0, 0, 25, getHeight());
        // g2.fillRect(getWidth() - 25, getHeight() - 25, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }

    private JButton createButton(String text, String iconName) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(77, 80));
        button.setBackground(Color.white);
        button.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        FlatSVGIcon icon = new FlatSVGIcon(getClass().getResource("/svg/" + iconName + ".svg")).derive(30, 30);
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 11));

        button.add(new JLabel(icon));
        button.add(label);

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        return button;
    }

}
