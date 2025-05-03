package com.bookstore.views.Dialog;

import javax.swing.*;

import com.bookstore.DTO.NCCDTO;
import com.bookstore.dao.NCCDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class NCCDialog extends JDialog {

    private JTextField txtMaNCC, txtTenNCC, txtSoDienThoai, txtEmail, txtDiaChi;
    private JComboBox<String> cboTrangThai;
    private JButton btnLuu, btnHuy;
    private boolean isSaved = false;

    public enum Mode {
        ADD, EDIT, VIEW
    }

    public NCCDialog(Frame parent, Mode mode, NCCDTO ncc) {
        super(parent, "Nhà Cung Cấp", true);
        initComponents(mode, ncc);
        setLocationRelativeTo(parent);
    }

    private void initComponents(Mode mode, NCCDTO ncc) {
        setSize(400, 400);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        txtMaNCC = new JTextField();
        txtTenNCC = new JTextField();
        txtSoDienThoai = new JTextField();
        txtEmail = new JTextField();
        txtDiaChi = new JTextField();
        cboTrangThai = new JComboBox<>(new String[] { "Hoạt động", "Ngừng hoạt động" });

        formPanel.add(new JLabel("Mã NCC:"));
        formPanel.add(txtMaNCC);
        txtMaNCC.setEditable(false); // Mã NCC không cho phép chỉnh sửa
        formPanel.add(new JLabel("Tên NCC:"));
        formPanel.add(txtTenNCC);
        formPanel.add(new JLabel("Số điện thoại:"));
        formPanel.add(txtSoDienThoai);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(txtEmail);
        formPanel.add(new JLabel("Địa chỉ:"));
        formPanel.add(txtDiaChi);
        formPanel.add(new JLabel("Trạng thái"));
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
            setTitle("Thêm Nhà Cung Cấp");
            txtMaNCC.setText(String.valueOf(new NCCDAO().getMaxId()) + 1);
        } else if (mode == Mode.EDIT) {
            setTitle("Sửa Nhà Cung Cấp");
            txtMaNCC.setText(String.valueOf(ncc.getMaNCC()));
            txtTenNCC.setText(ncc.getTenNCC());
            txtSoDienThoai.setText(ncc.getSoDienThoai());
            txtEmail.setText(ncc.getEmail());
            txtDiaChi.setText(ncc.getDiaChi());
            cboTrangThai.setSelectedItem(ncc.getStatus() == 1 ? "Hoạt động" : "Ngừng hoạt động");
        } else if (mode == Mode.VIEW) {
            setTitle("Xem Nhà Cung Cấp");
            txtMaNCC.setText(String.valueOf(ncc.getMaNCC()));
            txtTenNCC.setText(ncc.getTenNCC());
            txtSoDienThoai.setText(ncc.getSoDienThoai());
            txtEmail.setText(ncc.getEmail());
            txtDiaChi.setText(ncc.getDiaChi());
            cboTrangThai.setSelectedItem(ncc.getStatus() == 1 ? "Hoạt động" : "Ngừng hoạt động");

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
        txtTenNCC.setEditable(false);
        txtSoDienThoai.setEditable(false);
        txtEmail.setEditable(false);
        txtDiaChi.setEditable(false);
        cboTrangThai.setEnabled(false);
        btnLuu.setEnabled(false);
    }

    private boolean validateForm() {
        String maNCC = txtMaNCC.getText().trim();
        String tenNCC = txtTenNCC.getText().trim();
        String sdt = txtSoDienThoai.getText().trim();
        String email = txtEmail.getText().trim();
        String diaChi = txtDiaChi.getText().trim();

        if (maNCC.isEmpty()) {
            showError("Vui lòng nhập Mã nhà cung cấp.");
            return false;
        }
        if (tenNCC.isEmpty()) {
            showError("Vui lòng nhập Tên nhà cung cấp.");
            return false;
        }
        if (!sdt.matches("^0\\d{9}$")) {
            showError("Số điện thoại phải gồm 10 chữ số và bắt đầu bằng 0.");
            return false;
        }
        if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", email)) {
            showError("Email không hợp lệ.");
            return false;
        }
        if (diaChi.isEmpty()) {
            showError("Vui lòng nhập Địa chỉ.");
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

    // Các phương thức getter để lấy dữ liệu khi lưu
    public int getMaNCC() {
        return Integer.parseInt(txtMaNCC.getText().trim());
    }

    public String getTenNCC() {
        return txtTenNCC.getText().trim();
    }

    public String getSoDienThoai() {
        return txtSoDienThoai.getText().trim();
    }

    public String getEmail() {
        return txtEmail.getText().trim();
    }

    public String getDiaChi() {
        return txtDiaChi.getText().trim();
    }

    public String getTrangThai() {
        return (String) cboTrangThai.getSelectedItem();
    }
}
