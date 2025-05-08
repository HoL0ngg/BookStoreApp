package com.bookstore.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import com.bookstore.controller.MainController;
// import com.bookstore.service.SetLogoService;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import com.bookstore.views.Component.MenuTaskbar;
import com.bookstore.views.Panel.TrangChu;

public class MainFrame extends JFrame {
    private JPanel DangXuatPanel;
    private JPanel rightPanel;
    private JPanel navbarPanel;
    private JPanel ExitButton;
    private JPanel MinimizeButton;
    private JLabel ExitIcon;
    private JLabel MinimizeIcon;

    public JPanel MainContent;
    private MenuTaskbar menuTaskbar;

    public MainFrame() {
        setSize(1200, 800);
        setTitle("Quan ly thu vien");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        this.init();
        // SetLogoService.setLogo(this);
        setVisible(true);
    }

    private void init() {
        JPanel mainPanel = new JPanel();
        this.setContentPane(mainPanel);
        Color MainColor = Color.decode("#93BFCF");
        MainController controller = new MainController(this);
        mainPanel.setLayout(null);

        // Navbar
        navbarPanel = new JPanel();
        navbarPanel.setBounds(0, 0, 1200, 40);
        navbarPanel.setBackground(MainColor);
        navbarPanel.setLayout(new FlowLayout(2, 0, 0));
        mainPanel.add(navbarPanel);

        JPanel LogoPanel = new JPanel();
        LogoPanel.setLayout(new FlowLayout(0, 10, 0));

        // Image icon = new
        // ImageIcon(getClass().getResource("/svg/sach.png")).getImage().getScaledInstance(50,
        // 30,
        // Image.SCALE_SMOOTH);
        // JLabel LogoLabel = new JLabel(new ImageIcon(icon), JLabel.CENTER);
        // LogoLabel.setPreferredSize(new Dimension(50, 40));
        // LogoPanel.add(LogoLabel);

        JLabel TitleLabel = new JLabel("Quản lý thư viện");
        TitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        TitleLabel.setForeground(Color.black);
        LogoPanel.add(TitleLabel);

        LogoPanel.setPreferredSize(new Dimension(1110, 40));
        LogoPanel.setBackground(MainColor);
        navbarPanel.add(LogoPanel);

        MinimizeButton = new JPanel();
        MinimizeButton.setPreferredSize(new Dimension(40, 40));
        MinimizeButton.setBackground(MainColor);
        MinimizeButton.addMouseListener(controller);
        navbarPanel.add(MinimizeButton);

        MinimizeIcon = new JLabel("-");
        MinimizeIcon.setFont(new Font("Segoe UI", Font.BOLD, 20));
        MinimizeIcon.setForeground(Color.black);
        MinimizeButton.add(MinimizeIcon);

        ExitButton = new JPanel();
        ExitButton.setPreferredSize(new Dimension(40, 40));
        ExitButton.setBackground(MainColor);
        ExitButton.addMouseListener(controller);
        navbarPanel.add(ExitButton);

        ExitIcon = new JLabel("X");
        ExitIcon.setFont(new Font("Segoe UI", Font.BOLD, 20));
        ExitIcon.setForeground(Color.black);
        ExitButton.add(ExitIcon);

        // Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 40, 300, 760);
        leftPanel.setBackground(Color.white);
        leftPanel.setLayout(null);
        mainPanel.add(leftPanel);

        // Right Panel
        rightPanel = new JPanel();
        rightPanel.setBounds(300, 40, 900, 760);
        rightPanel.setLayout(new BorderLayout());
        mainPanel.add(rightPanel);

        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(new LineBorder(Color.black, 2));
        titlePanel.setLayout(null);
        titlePanel.setBackground(Color.decode("#BDCDD6"));
        titlePanel.setBounds(0, 0, 300, 100);

        FlatSVGIcon avatarIcon = new FlatSVGIcon(getClass().getResource("/svg/avatar.svg")).derive(60, 60);

        JLabel avatarLabel = new JLabel(avatarIcon, JLabel.CENTER);
        avatarLabel.setBounds(0, 0, 100, 100);
        titlePanel.add(avatarLabel);

        JLabel TenNguoiDungLabel = new JLabel("Ten nguoi dung", JLabel.LEADING);
        TenNguoiDungLabel.setForeground(Color.black);
        TenNguoiDungLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        TenNguoiDungLabel.setBounds(100, 0, 200, 80);

        JLabel ChucVuLabel = new JLabel("Vai tro", JLabel.LEADING);
        ChucVuLabel.setForeground(Color.black);
        ChucVuLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        ChucVuLabel.setBounds(100, 20, 200, 90);

        titlePanel.add(ChucVuLabel);
        titlePanel.add(TenNguoiDungLabel);
        leftPanel.add(titlePanel);

        // JPanel ChucNangPanel
        JPanel ChucNangPanel = new JPanel();
        ChucNangPanel.setBackground(Color.decode("#BDCDD6"));
        ChucNangPanel.setBounds(0, 100, 300, 660);
        ChucNangPanel.setLayout(new BorderLayout());
        leftPanel.add(ChucNangPanel);

        // Thêm MenuTaskbar vào CENTER
        menuTaskbar = new MenuTaskbar(this);
        menuTaskbar.setBorder(new LineBorder(Color.black, 2));
        ChucNangPanel.add(menuTaskbar, BorderLayout.CENTER);

        // DangXuatPanel
        DangXuatPanel = new JPanel();
        DangXuatPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Căn trái nội dung
        DangXuatPanel.setBackground(Color.decode("#93BFCF"));
        DangXuatPanel.addMouseListener(controller);

        FlatSVGIcon iconDangXuat = new FlatSVGIcon(getClass().getResource("/svg/back-button.svg")).derive(30, 30);
        JLabel DangXuatIcon = new JLabel(iconDangXuat, JLabel.CENTER);
        JLabel DangXuatLabel = new JLabel("ĐĂNG XUẤT");
        DangXuatLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        DangXuatPanel.add(DangXuatIcon);
        DangXuatPanel.add(DangXuatLabel);
        ChucNangPanel.add(DangXuatPanel, BorderLayout.SOUTH);

        this.setPanel(new TrangChu());
    }

    public void setPanel(JPanel pn) {
        rightPanel.removeAll();
        rightPanel.add(pn, BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }

    public JPanel getDangXuatPanel() {
        return DangXuatPanel;
    }

    public JPanel getExitButton() {
        return ExitButton;
    }

    public JPanel getMinimizeButton() {
        return MinimizeButton;
    }

    public JLabel getExitIcon() {
        return ExitIcon;
    }

    public JLabel getMinimizeIcon() {
        return MinimizeIcon;
    }

}
