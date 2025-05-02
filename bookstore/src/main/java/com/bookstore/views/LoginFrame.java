package com.bookstore.views;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.bookstore.BUS.TaiKhoanBUS;
import com.bookstore.controller.LoginController;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame extends JFrame {
    private JButton DangNhapButton;
    private JLabel TaoTaiKhoan;
    private JPanel ExitButton;
    private JLabel ExitIcon;
    private JPanel MinimizeButton;
    private JLabel MinimizeIcon;
    private TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();

    public LoginFrame() {
        this.setTitle("Quan ly Thu vien");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setVisible(true);
        // SetLogoService.setLogo(this);
        this.init();
    }

    private void init() {
        Color MainColor = Color.decode("#6096B4");

        // Them controller
        LoginController controller = new LoginController(this);

        // Navbar content
        ExitButton = new JPanel();
        ExitIcon = new JLabel("X", JLabel.CENTER);
        ExitIcon.setForeground(MainColor);
        ExitIcon.setFont(new Font("Segoe UI", Font.BOLD, 18));
        ExitButton.add(ExitIcon);
        ExitButton.setBounds(760, 0, 40, 40);
        ExitButton.setBackground(Color.white);
        ExitButton.addMouseListener(controller);
        this.add(ExitButton);

        MinimizeButton = new JPanel();
        MinimizeIcon = new JLabel("-", JLabel.CENTER);
        MinimizeIcon.setForeground(MainColor);
        MinimizeIcon.setFont(new Font("Segoe UI", Font.BOLD, 20));
        MinimizeButton.add(MinimizeIcon);
        MinimizeButton.setBounds(720, 0, 40, 40);
        MinimizeButton.setBackground(Color.white);
        MinimizeButton.addMouseListener(controller);
        this.add(MinimizeButton);

        // Left content
        JPanel leftContent = new JPanel();
        leftContent.setBackground(MainColor);
        leftContent.setBounds(0, 0, 350, 600);
        leftContent.setLayout(null);

        FlatSVGIcon iconMetro = new FlatSVGIcon(getClass().getResource("/svg/thuvien.svg")).derive(220, 220);
        JLabel Metro = new JLabel(iconMetro, JLabel.CENTER);
        Metro.setBounds(0, 120, 350, 220);
        leftContent.add(Metro);

        JLabel title = new JLabel("QUẢN LÝ THƯ VIỆN", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.white);
        title.setBounds(0, 350, 350, 80);
        leftContent.add(title);

        // Right content
        JPanel rightContent = new JPanel();
        rightContent.setBackground(Color.white);
        rightContent.setBounds(350, 0, 450, 600);
        rightContent.setLayout(null);

        // Label dang nhap
        JLabel DangNhapLabel = new JLabel("ĐĂNG NHẬP");
        DangNhapLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        DangNhapLabel.setBounds(140, 110, 160, 36);
        DangNhapLabel.setForeground(MainColor);
        rightContent.add(DangNhapLabel);

        // Form dang nhap
        JLabel TenDangNhapLabel = new JLabel("Tên đăng nhập");
        TenDangNhapLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        TenDangNhapLabel.setBounds(60, 180, 200, 30);
        TenDangNhapLabel.setForeground(MainColor);
        rightContent.add(TenDangNhapLabel);

        JTextField TenDangNhapField = new JTextField("Nhập tên đăng nhập...");
        rightContent.add(TenDangNhapField);
        TenDangNhapField.setBounds(60, 210, 310, 40);
        TenDangNhapField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        TenDangNhapField.setForeground(Color.GRAY);

        JLabel MatKhauLabel = new JLabel(
                "Mật khẩu");
        MatKhauLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        MatKhauLabel.setBounds(60, 260, 200, 30);
        MatKhauLabel.setForeground(MainColor);
        rightContent.add(MatKhauLabel);

        // layout chua password field + icon an hien
        JLayeredPane matkhauPane = new JLayeredPane(); // layout cho phep cac phan tu xep chong len nhau
        matkhauPane.setBounds(60, 290, 310, 50);

        JPasswordField MatKhauField = new JPasswordField("Nhập mật khẩu ...");
        MatKhauField.setBounds(0, 0, 310, 40);
        // rightContent.add(MatKhauField);
        // MatKhauField.setBounds(60, 280, 310, 30);
        MatKhauField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        MatKhauField.setForeground(Color.GRAY);
        MatKhauField.setEchoChar((char) 0);

        // icon an hien
        ImageIcon openEye = new ImageIcon(
                new ImageIcon("bookstore/src/main/java/com/bookstore/assets/icons/view.png").getImage()
                        .getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        JLabel openEyeLabel = new JLabel(openEye);
        openEyeLabel.setVisible(true);
        openEyeLabel.setBounds(270, 5, 30, 30);

        ImageIcon closeEye = new ImageIcon(
                new ImageIcon("bookstore/src/main/java/com/bookstore/assets/icons/hide.png").getImage()
                        .getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        JLabel closeEyeLabel = new JLabel(closeEye);
        closeEyeLabel.setVisible(false);
        closeEyeLabel.setBounds(270, 5, 30, 30);

        matkhauPane.add(MatKhauField, JLayeredPane.DEFAULT_LAYER);
        matkhauPane.add(openEyeLabel, JLayeredPane.PALETTE_LAYER);
        matkhauPane.add(closeEyeLabel, JLayeredPane.PALETTE_LAYER);

        rightContent.add(matkhauPane);

        // goi function
        showPassword(MatKhauField, openEyeLabel, closeEyeLabel);

        DangNhapButton = new JButton("ĐĂNG NHẬP");
        DangNhapButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        DangNhapButton.setBackground(MainColor);
        DangNhapButton.setForeground(Color.white);
        DangNhapButton.setBounds(60, 360, 310, 50);
        DangNhapButton.addMouseListener(controller);
        rightContent.add(DangNhapButton);

        TaoTaiKhoan = new JLabel("<html><u><i>Chưa có tài khoản ?</i></u></html>");
        TaoTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        TaoTaiKhoan.setBounds(265, 415, 120, 20);
        TaoTaiKhoan.addMouseListener(controller);
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

        DangNhapButton.addActionListener(e -> {
            String tenDangNhap = TenDangNhapField.getText();
            String matKhau = String.valueOf(MatKhauField.getPassword());

            JOptionPane.showMessageDialog(this, "Đăng nhập thành công", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); // Đóng cửa sổ đăng nhập
            new MainFrame(); // Mở cửa sổ chính
            // if (taiKhoanBUS.dangNhap(tenDangNhap, matKhau)) {
            // } else {
            // JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không đúng",
            // "Thông báo",
            // JOptionPane.ERROR_MESSAGE);
            // }
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
            setSize(500, 500);
            setLocationRelativeTo(null);
            this.init();
        }

        private void init() {
            JPanel contentPane = new JPanel();
            contentPane.setLayout(null);
            contentPane.setBackground(Color.white);
            this.setContentPane(contentPane);

            Color MainColor = Color.decode("#6096B4");

            JLabel TaoTaiKhoanLabel = new JLabel("TẠO TÀI KHOẢN");
            TaoTaiKhoanLabel.setBounds(170, 12, 200, 30);
            TaoTaiKhoanLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
            contentPane.add(TaoTaiKhoanLabel);

            JLabel SDTLabel = new JLabel("Số điện thoại:");
            SDTLabel.setForeground(MainColor);
            SDTLabel.setBounds(100, 60, 200, 30);
            SDTLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            contentPane.add(SDTLabel);

            JLabel TenDangNhapLabel = new JLabel("Tên đăng nhập :");
            TenDangNhapLabel.setForeground(MainColor);
            TenDangNhapLabel.setBounds(100, 120, 200, 30);
            TenDangNhapLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            contentPane.add(TenDangNhapLabel);

            JLabel MatKhauLabel = new JLabel("Mật khẩu:");
            MatKhauLabel.setForeground(MainColor);
            MatKhauLabel.setBounds(100, 180, 200, 30);
            MatKhauLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            contentPane.add(MatKhauLabel);

            JLabel NhatLaiMatKhauLabel = new JLabel("Nhập lại mật khẩu:");
            NhatLaiMatKhauLabel.setForeground(MainColor);
            NhatLaiMatKhauLabel.setBounds(100, 240, 200, 30);
            NhatLaiMatKhauLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            contentPane.add(NhatLaiMatKhauLabel);

            // text field from sign up
            JTextField SDTField = new JTextField("Nhập số điện thoại....");
            SDTField.setForeground(Color.GRAY);
            SDTField.setBounds(100, 90, 300, 30);
            SDTField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            contentPane.add(SDTField);

            JTextField TenDangNhapField = new JTextField("Nhập tên đăng nhập...");
            TenDangNhapField.setForeground(Color.GRAY);
            TenDangNhapField.setBounds(100, 150, 300, 30);
            TenDangNhapField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            contentPane.add(TenDangNhapField);

            JPasswordField MatKhauField = new JPasswordField("Nhập mật khẩu...");
            MatKhauField.setForeground(Color.GRAY);
            MatKhauField.setBounds(100, 210, 300, 30);
            MatKhauField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            MatKhauField.setEchoChar((char) 0);
            contentPane.add(MatKhauField);

            JPasswordField NhapLaiMatKhauField = new JPasswordField("Nhập lại mật khẩu...");
            NhapLaiMatKhauField.setForeground(Color.GRAY);
            NhapLaiMatKhauField.setBounds(100, 270, 300, 30);
            NhapLaiMatKhauField.setEchoChar((char) 0);
            NhapLaiMatKhauField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            contentPane.add(NhapLaiMatKhauField);

            JButton DangKyButton = new JButton("ĐĂNG KÝ");
            DangKyButton.setBounds(100, 315, 300, 40);
            DangKyButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
            DangKyButton.setForeground(Color.white);
            DangKyButton.setBackground(MainColor);
            contentPane.add(DangKyButton);

            // DangKyButton.addActionListener(e -> {
            // String sodienthoai = SDTField.getText();
            // String tenDangNhap = TenDangNhapField.getText();
            // String matKhau = String.valueOf(MatKhauField.getPassword());
            // String nhaplaimatkhau = String.valueOf(NhapLaiMatKhauField.getPassword());

            // if (!matKhau.equals(nhaplaimatkhau)) {
            // JOptionPane.showMessageDialog(this, "mật khẩu không khớp", "Thông báo",
            // JOptionPane.ERROR_MESSAGE);
            // return;
            // }

            // DangkyDao user = new DangkyDao();
            // boolean check = user.DangKy(tenDangNhap, matKhau, sodienthoai);

            // if (check) {
            // JOptionPane.showMessageDialog(this, "Đăng ký thành công ", "thông báo",
            // JOptionPane.INFORMATION_MESSAGE);
            // this.dispose();
            // } else {
            // JOptionPane.showMessageDialog(this, "Đăng ký không thành công", "thông báo",
            // JOptionPane.ERROR_MESSAGE);
            // }
            // });

            SDTField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (SDTField.getText().equals("Nhập số điện thoại....")) {
                        SDTField.setText("");
                        SDTField.setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (SDTField.getText().equals("")) {
                        SDTField.setText("Nhập số điện thoại....");
                        SDTField.setForeground(Color.GRAY);
                    }
                }
            });

            TenDangNhapField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (TenDangNhapField.getText().equals("Nhập tên đăng nhập...")) {
                        TenDangNhapField.setText("");
                        TenDangNhapField.setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (TenDangNhapField.getText().equals("")) {
                        TenDangNhapField.setText("Nhập tên đăng nhập...");
                        TenDangNhapField.setForeground(Color.GRAY);
                    }
                }
            });

            MatKhauField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (String.valueOf(MatKhauField.getPassword()).equals("Nhập mật khẩu...")) {
                        MatKhauField.setText("");
                        MatKhauField.setForeground(Color.BLACK);
                        MatKhauField.setEchoChar('*');
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (String.valueOf(MatKhauField.getPassword()).equals("")) {
                        MatKhauField.setText("Nhập mật khẩu...");
                        MatKhauField.setForeground(Color.GRAY);
                        MatKhauField.setEchoChar((char) 0);
                    }
                }
            });

            NhapLaiMatKhauField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (String.valueOf(NhapLaiMatKhauField.getPassword()).equals("Nhập lại mật khẩu...")) {
                        NhapLaiMatKhauField.setText("");
                        NhapLaiMatKhauField.setForeground(Color.BLACK);
                        NhapLaiMatKhauField.setEchoChar('*');
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (String.valueOf(NhapLaiMatKhauField.getPassword()).equals("")) {
                        NhapLaiMatKhauField.setText("Nhập lại mật khẩu...");
                        NhapLaiMatKhauField.setForeground(Color.GRAY);
                        NhapLaiMatKhauField.setEchoChar((char) 0);
                    }
                }
            });
        }
    }

    // ham hien thi mat khau
    public static void showPassword(JPasswordField pf, JLabel show, JLabel hide) {
        hide.setVisible(false);
        show.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pf.setEchoChar((char) 0);
                show.setVisible(false);
                hide.setVisible(true);
                pf.requestFocusInWindow();
            }
        });

        hide.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pf.setEchoChar('*');
                hide.setVisible(false);
                show.setVisible(true);
                pf.requestFocusInWindow();
            }
        });
    }

    public JButton getDangNhapButton() {
        return DangNhapButton;
    }

    public JLabel getTaoTaiKhoan() {
        return TaoTaiKhoan;
    }

    public void HienTaoTaiKhoan() {
        new TaoTaiKhoanDialog(this).setVisible(true);
    }

    public JPanel getExitButton() {
        return ExitButton;
    }

    public JLabel getExitIcon() {
        return ExitIcon;
    }

    public JPanel getMinimizeButton() {
        return MinimizeButton;
    }

    public JLabel getMinimizeIcon() {
        return MinimizeIcon;
    }

}