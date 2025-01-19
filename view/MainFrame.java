package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.MainController;

public class MainFrame extends JFrame {
    private JPanel TuyenDuongPanel;
    private JPanel ThongKePanel;
    private JPanel DangXuatPanel;

    public MainFrame() {
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.init();
    }

    private void init() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 300, 800);
        leftPanel.setBackground(Color.white);
        leftPanel.setLayout(null);
        this.add(leftPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(300, 0, 900, 800);
        rightPanel.setLayout(null);
        this.add(rightPanel);

        Color MainColor = Color.decode("#5D7B6F");
        MainController controller = new MainController(this);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(null);
        titlePanel.setBackground(MainColor);
        titlePanel.setBounds(0, 0, 300, 100);

        JLabel metroLabel = new JLabel("<html><div style='text-align: center;'>QUẢN LÝ VẬN HÀNH<br>METRO</div></html>");
        metroLabel.setForeground(Color.white);
        metroLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        metroLabel.setBounds(30, 14, 270, 80);

        titlePanel.add(metroLabel);
        leftPanel.add(titlePanel);

        JPanel ChucNangPanel = new JPanel();
        ChucNangPanel.setBackground(Color.decode("#BDCDD6"));
        ChucNangPanel.setBounds(0, 100, 300, 700);
        ChucNangPanel.setLayout(null);
        leftPanel.add(ChucNangPanel);

        TuyenDuongPanel = new JPanel();
        TuyenDuongPanel.setBounds(10, 10, 280, 80);
        TuyenDuongPanel.setLayout(null);
        TuyenDuongPanel.setBackground(Color.decode("#93BFCF"));
        ChucNangPanel.add(TuyenDuongPanel);

        TuyenDuongPanel.addMouseListener(controller);

        JLabel TuyenDuongLabel = new JLabel("TUYẾN ĐƯỜNG");
        TuyenDuongLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        TuyenDuongLabel.setBounds(80, 22, 160, 30);
        TuyenDuongPanel.add(TuyenDuongLabel);

        ImageIcon iconTuyenDuong = new ImageIcon(
                new ImageIcon("./img/route.png").getImage().getScaledInstance(40, 40,
                        Image.SCALE_SMOOTH));
        JLabel TuyenDuongIcon = new JLabel(iconTuyenDuong, JLabel.CENTER);
        TuyenDuongIcon.setForeground(Color.white);
        TuyenDuongIcon.setBounds(20, 20, 40, 40);
        TuyenDuongPanel.add(TuyenDuongIcon);

        ThongKePanel = new JPanel();
        ThongKePanel.setBounds(10, 100, 280, 80);
        ThongKePanel.setLayout(null);
        ThongKePanel.setBackground(Color.decode("#93BFCF"));
        ChucNangPanel.add(ThongKePanel);

        ThongKePanel.addMouseListener(controller);

        JLabel ThongKeLabel = new JLabel("THỐNG KÊ");
        ThongKeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        ThongKeLabel.setBounds(80, 22, 160, 30);
        ThongKePanel.add(ThongKeLabel);

        ImageIcon iconThongKe = new ImageIcon(
                new ImageIcon("./img/diagram.png").getImage().getScaledInstance(40, 40,
                        Image.SCALE_SMOOTH));
        JLabel ThongKeIcon = new JLabel(iconThongKe, JLabel.CENTER);
        ThongKeIcon.setForeground(Color.white);
        ThongKeIcon.setBounds(20, 20, 40, 40);
        ThongKePanel.add(ThongKeIcon);

        DangXuatPanel = new JPanel();
        DangXuatPanel.setBounds(10, 550, 280, 80);
        DangXuatPanel.setLayout(null);
        DangXuatPanel.setBackground(Color.decode("#93BFCF"));
        DangXuatPanel.addMouseListener(controller);
        ChucNangPanel.add(DangXuatPanel);

        JLabel DangXuatLabel = new JLabel("ĐĂNG XUẤT");
        DangXuatLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        DangXuatLabel.setBounds(80, 22, 160, 30);
        DangXuatPanel.add(DangXuatLabel);

        ImageIcon iconDangXuat = new ImageIcon(
                new ImageIcon("./img/previous.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        JLabel DangXuatIcon = new JLabel(iconDangXuat, JLabel.CENTER);
        DangXuatIcon.setForeground(Color.white);
        DangXuatIcon.setBounds(20, 20, 40, 40);
        DangXuatPanel.add(DangXuatIcon);
    }

    public void resetPanel() {
        TuyenDuongPanel.setBackground(Color.decode("#93BFCF"));
        ThongKePanel.setBackground(Color.decode("#93BFCF"));
        DangXuatPanel.setBackground(Color.decode("#93BFCF"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }

    public JPanel getTuyenDuongPanel() {
        return TuyenDuongPanel;
    }

    public JPanel getThongKePanel() {
        return ThongKePanel;
    }

    public JPanel getDangXuatPanel() {
        return DangXuatPanel;
    }

}
