package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.LoginController;

import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        this.setTitle("Quan ly thu vien");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.init();
    }

    private void init() {
        Color MainColor = Color.decode("#5D7B6F");

        // Left content
        JPanel leftContent = new JPanel();
        leftContent.setBackground(MainColor);
        leftContent.setBounds(0, 0, 350, 600);
        leftContent.setLayout(null);

        ImageIcon iconThuVien = new ImageIcon(
                new ImageIcon("./img/library.png").getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
        JLabel ThuVien = new JLabel(iconThuVien, JLabel.CENTER);
        ThuVien.setBounds(80, 100, 200, 200);
        leftContent.add(ThuVien);

        JLabel title = new JLabel("QUẢN LÝ THƯ VIỆN");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.white);
        title.setBounds(60, 330, 400, 50);
        leftContent.add(title);

        // Them controller
        LoginController controller = new LoginController(this);

        // Right content
        JPanel rightContent = new JPanel();
        rightContent.setBounds(350, 0, 450, 600);
        rightContent.setLayout(null);

        // Label dang nhap
        JLabel DangNhapLabel = new JLabel("ĐĂNG NHẬP");
        DangNhapLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        DangNhapLabel.setBounds(140, 80, 160, 36);
        DangNhapLabel.setForeground(MainColor);
        rightContent.add(DangNhapLabel);

        // Form dang nhap
        JLabel TenDangNhapLabel = new JLabel("Tên đăng nhập");
        TenDangNhapLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        TenDangNhapLabel.setBounds(60, 150, 200, 30);
        TenDangNhapLabel.setForeground(MainColor);
        rightContent.add(TenDangNhapLabel);

        JTextField TenDangNhapField = new JTextField("Nhập tên đăng nhập...");
        rightContent.add(TenDangNhapField);
        TenDangNhapField.setBounds(60, 180, 310, 30);
        TenDangNhapField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        TenDangNhapField.setForeground(Color.GRAY);

        JLabel MatKhauLabel = new JLabel(
                "Mật khẩu");
        MatKhauLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        MatKhauLabel.setBounds(60, 220, 200, 30);
        MatKhauLabel.setForeground(MainColor);
        rightContent.add(MatKhauLabel);

        JPasswordField MatKhauField = new JPasswordField("Nhập mật khẩu ...");
        rightContent.add(MatKhauField);
        MatKhauField.setBounds(60, 250, 310, 30);
        MatKhauField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        MatKhauField.setForeground(Color.GRAY);
        MatKhauField.setEchoChar((char) 0);

        JCheckBox HienMatKhau = new JCheckBox("Hiện mật khẩu");
        HienMatKhau.setBounds(60, 280, 140, 30);
        HienMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rightContent.add(HienMatKhau);

        JButton DangNhapButton = new JButton("ĐĂNG NHẬP");
        DangNhapButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        DangNhapButton.setForeground(Color.white);
        DangNhapButton.setBackground(MainColor);
        DangNhapButton.setBounds(60, 330, 310, 50);
        DangNhapButton.addMouseListener(controller);
        rightContent.add(DangNhapButton);

        JLabel TaoTaiKhoan = new JLabel("<html><u><i>Chưa có tài khoản ?</i></u></html>");
        TaoTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        TaoTaiKhoan.setBounds(265, 385, 120, 20);
        TaoTaiKhoan.addMouseListener(controller);
        // TaoTaiKhoan.addMouseListener();
        rightContent.add(TaoTaiKhoan);

        this.add(leftContent);
        this.add(rightContent);

        TenDangNhapField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(TenDangNhapField.getText()).equals("Nhập tên đăng nhập...")) {
                    TenDangNhapField.setText(""); // Xóa placeholder khi focus
                    TenDangNhapField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(TenDangNhapField.getText()).isEmpty()) {
                    TenDangNhapField.setText("Nhập tên đăng nhập..."); // Hiển thịlạiplaceholder khi không có dữ liệu
                    TenDangNhapField.setForeground(Color.GRAY);
                }
            }

        });

        MatKhauField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(MatKhauField.getPassword()).equals("Nhập mật khẩu ...")) {
                    MatKhauField.setText(""); // Xóa placeholder khi focus
                    MatKhauField.setForeground(Color.BLACK);
                    MatKhauField.setEchoChar('*'); // Hiển thị ký tự ẩn khi nhập mật khẩu
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(MatKhauField.getPassword()).isEmpty()) {
                    MatKhauField.setText("Nhập mật khẩu ..."); // Hiển thị lại placeholder khi không có dữ liệu
                    MatKhauField.setForeground(Color.GRAY);
                    MatKhauField.setEchoChar((char) 0); // Hiển thị văn bản bình thường cho placeholder
                }
            }

        });

        // Tranh focus vao JTextfield tu ban dau
        JPanel emptyJPanel = new JPanel();
        emptyJPanel.setBounds(0, 0, 0, 0);
        this.add(emptyJPanel);
        setVisible(true);
        emptyJPanel.requestFocusInWindow();
    }

    private class TaoTaiKhoanDialog extends JDialog {
        public TaoTaiKhoanDialog(JFrame parent) {
            super(parent, "Dang ky", true);
            setSize(300, 400);
            // setLocationRelativeTo(null);
            this.init();
        }

        private void init() {

        }
    }
}
