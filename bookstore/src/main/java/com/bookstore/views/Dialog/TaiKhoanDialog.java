package com.bookstore.views.Dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import com.bookstore.DTO.TaiKhoanDTO;
import com.bookstore.dao.TaiKhoanDAO;

public class TaiKhoanDialog extends JDialog {
    private JTextField txtTenDangNhap, txtMatKhau, txtEmail;
    private JComboBox<String> cboTrangThai, cboNhomQuyen;
    private JButton btnLuu, btnHuy;
    private boolean isSaved = false;

    public enum Mode {
        ADD, EDIT, VIEW
    }

    public TaiKhoanDialog(Frame parent, Mode mode, TaiKhoanDTO TaiKhoan) {
        super(parent, "Tài khoản", true);
        initComponents(mode, TaiKhoan);
        setLocationRelativeTo(parent);
    }

    private void initComponents(Mode mode, TaiKhoanDTO taikhoan) {
        setSize(400, 400);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        txtTenDangNhap = new JTextField();
        txtMatKhau = new JTextField();
        txtEmail = new JTextField();
        cboNhomQuyen = new JComboBox<>(new TaiKhoanDAO().getTenNhomQuyenList());

        cboTrangThai = new JComboBox<>(new String[] { "Hoạt động", "Ngừng hoạt động" });

        formPanel.add(new JLabel("Tên đăng nhập:"));
        formPanel.add(txtTenDangNhap);
        formPanel.add(new JLabel("Mật khẩu:"));
        formPanel.add(txtMatKhau);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(txtEmail);
        formPanel.add(new JLabel("Nhóm quyền:"));
        formPanel.add(cboNhomQuyen);
        formPanel.add(new JLabel("Trạng thái: "));
        formPanel.add(cboTrangThai);

        add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        add(buttonPanel, BorderLayout.SOUTH);

        if (mode == Mode.ADD) {
            setTitle("Thêm Tài Khoản");
        } else if (mode == Mode.EDIT) {
            setTitle("Sửa Tài Khoản");
            txtTenDangNhap.setText(String.valueOf(taikhoan.getTenDangNhap()));
            txtTenDangNhap.setEditable(false);
            txtMatKhau.setText(taikhoan.getMatKhau());
            txtEmail.setText(taikhoan.getEmail());
            cboNhomQuyen.setSelectedItem(taikhoan.getMaNhomQuyen() == 1 ? "Hoạt động" : "Ngừng hoạt động");
            cboTrangThai.setSelectedItem(taikhoan.getTrangThai() == 1 ? "Hoạt động" : "Ngừng hoạt động");
        } else if (mode == Mode.VIEW) {
            setTitle("Xem Tài Khoản");
            txtTenDangNhap.setText(String.valueOf(taikhoan.getTenDangNhap()));
            txtTenDangNhap.setEditable(false);
            txtMatKhau.setText(taikhoan.getMatKhau());
            txtEmail.setText(taikhoan.getEmail());
            cboNhomQuyen.setSelectedItem(taikhoan.getMaNhomQuyen() == 1 ? "Hoạt động" : "Ngừng hoạt động");
            cboTrangThai.setSelectedItem(taikhoan.getTrangThai() == 1 ? "Hoạt động" : "Ngừng hoạt động");

            disableEditing();
        }

        // Action buttons
        btnLuu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Có thể thêm kiểm tra dữ liệu ở đây
                if (validateForm()) {
                    isSaved = true;
                    dispose();
                }
            }
        });

        btnHuy.addActionListener(e -> dispose());
    }

    private void disableEditing() {
        txtTenDangNhap.setEditable(false);
        txtMatKhau.setEditable(false);
        txtEmail.setEditable(false);
        cboNhomQuyen.setEditable(false);
        cboTrangThai.setEnabled(false);
        btnLuu.setEnabled(false);
    }

    private boolean validateForm() {
        String TenDangNhap = txtTenDangNhap.getText().trim();
        String MatKhau = txtMatKhau.getText().trim();
        String email = txtEmail.getText().trim();

        if (TenDangNhap.isEmpty()) {
            showError("Vui lòng nhập Mã nhà cung cấp.");
            return false;
        }
        if (MatKhau.isEmpty()) {
            showError("Vui lòng nhập Tên nhà cung cấp.");
            return false;
        }

        if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", email)) {
            showError("Email không hợp lệ.");
            return false;
        }
        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
    }

    public boolean isSaved() {
        return isSaved;
    }

    public String getTenDangNhap() {
        return txtTenDangNhap.getText().trim();
    }

    public String getTxtMatKhau() {
        return txtMatKhau.getText().trim();
    }

    public String getTxtEmail() {
        return txtEmail.getText().trim();
    }

    public int getCboTrangThai() {
        return cboTrangThai.getSelectedIndex() + 1;
    }

    public int getCboNhomQuyen() {
        return cboNhomQuyen.getSelectedIndex() + 1;
    }

}
