package com.bookstore.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import com.bookstore.controller.MainController;
// import com.bookstore.service.SetLogoService;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import com.bookstore.views.Component.MenuTaskbar;
import com.bookstore.views.Component.RoundedPanel;

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

        // Panel Trang chu
        JPanel TrangChuPanel = new JPanel();
        TrangChuPanel.setLayout(null);
        TrangChuPanel.setBackground(Color.white);
        TrangChuPanel.setSize(900, 760);

        JLabel TrangChuLabel = new JLabel(
                "<html><div style='text-align: center; font-weight: 700; font-size: larger;'>HỆ THỐNG QUẢN LÝ THƯ VIỆN</div><div style='text-align: center; font-weight: 400; font-size: smaller; margin-top: 4px;'>- SÁCH LÀ NGUỒN KIẾN THỨC VÔ TẬN -</div></html>",
                JLabel.CENTER);
        TrangChuLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        TrangChuLabel.setForeground(Color.black);
        TrangChuLabel.setBounds(0, 0, 900, 140);
        TrangChuPanel.add(TrangChuLabel);

        JPanel ThongKePanel = new JPanel();
        ThongKePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
        ThongKePanel.setBackground(Color.decode("#BDCDD6"));
        ThongKePanel.setBounds(0, 140, 900, 620);
        TrangChuPanel.add(ThongKePanel);

        RoundedPanel SoTauPanel = new RoundedPanel(20);
        SoTauPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        SoTauPanel.setBackground(Color.decode("#93BFCF"));
        SoTauPanel.setPreferredSize(new Dimension(250, 350));
        SoTauPanel.setBorder(new LineBorder(Color.black, 2));
        ThongKePanel.add(SoTauPanel);

        JLabel SoTauLabel = new JLabel("Số tàu", JLabel.CENTER);
        SoTauLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        SoTauLabel.setForeground(Color.black);
        SoTauPanel.add(SoTauLabel);

        RoundedPanel SoNguoiSuDungPanel = new RoundedPanel(20);
        SoNguoiSuDungPanel.setLayout(null);
        SoNguoiSuDungPanel.setBackground(Color.decode("#93BFCF"));
        SoNguoiSuDungPanel.setPreferredSize(new Dimension(250, 350));
        SoNguoiSuDungPanel.setBorder(new LineBorder(Color.black, 2));
        ThongKePanel.add(SoNguoiSuDungPanel);

        JLabel SoNguoiSuDungLabel = new JLabel("696 ĐẦU SÁCH", JLabel.CENTER);
        SoNguoiSuDungLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        SoNguoiSuDungLabel.setBounds(0, 200, 250, 50);
        SoNguoiSuDungLabel.setForeground(Color.black);
        SoNguoiSuDungPanel.add(SoNguoiSuDungLabel);

        FlatSVGIcon SoNugoiSuDungIcon = new FlatSVGIcon(getClass().getResource("/svg/book2.svg")).derive(150, 150);
        JLabel NguoiSuDungIcon = new JLabel(SoNugoiSuDungIcon, JLabel.CENTER);
        NguoiSuDungIcon.setBounds(50, 30, 150, 150);
        SoNguoiSuDungPanel.add(NguoiSuDungIcon);

        RoundedPanel SoTuyenDuongPanel = new RoundedPanel(20);
        SoTuyenDuongPanel.setLayout(null);
        SoTuyenDuongPanel.setBackground(Color.decode("#93BFCF"));
        SoTuyenDuongPanel.setPreferredSize(new Dimension(250, 350));
        SoTuyenDuongPanel.setBorder(new LineBorder(Color.black, 2));
        ThongKePanel.add(SoTuyenDuongPanel);

        JLabel SoTuyenDuongLabel = new JLabel("7749 TUYẾN ĐƯỜNG", JLabel.CENTER);
        SoTuyenDuongLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        SoTuyenDuongLabel.setBounds(0, 200, 250, 50);
        SoTuyenDuongLabel.setForeground(Color.black);
        SoTuyenDuongPanel.add(SoTuyenDuongLabel);

        // FlatSVGIcon SoTuyenDuongIcon = new
        // FlatSVGIcon(getClass().getResource("/svg/route2.svg")).derive(150, 150);
        // JLabel TuyenDuongIcon = new JLabel(SoTuyenDuongIcon, JLabel.CENTER);
        // TuyenDuongIcon.setBounds(50, 30, 150, 150);
        // SoTuyenDuongPanel.add(TuyenDuongIcon);

        this.setPanel(TrangChuPanel);
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
