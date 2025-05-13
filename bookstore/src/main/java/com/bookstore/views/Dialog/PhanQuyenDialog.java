package com.bookstore.views.Dialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.bookstore.DTO.NhomQuyenDTO;
import com.bookstore.dao.ChucNangDAO;
import com.bookstore.dao.NhomQuyenDAO;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PhanQuyenDialog extends JDialog {

    public enum Mode {
        ADD, EDIT, VIEW
    }

    private JTextField txtTenNhomQuyen;
    private JTable table;
    private JButton btnLuu, btnDong;

    public PhanQuyenDialog(Frame parent, Mode mode, int maNhomQuyen) {
        super(parent, "Phân quyền nhân viên", true);
        setSize(600, 440);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        String[] columnNames = { "Tên chức năng", "Xem", "Sửa", "Chi tiết", "Xóa" };
        Object[][] data = {
                { "Quản lý sách", false, false, false, false },
                { "Quản lý tác giả", false, false, false, false },
                { "Quản lý độc giả", false, false, false, false },
                { "Quản lý nhà cung cấp", false, false, false, false },
                { "Quản lý phiếu nhập", false, false, false, false },
                { "Quản lý phiếu mượn", false, false, false, false },
                { "Quản lý phiếu trả", false, false, false, false },
                { "Quản lý phiếu hủy", false, false, false, false },
                { "Quản lý phiếu phạt", false, false, false, false },
                { "Quản lý phân quyền", false, false, false, false },
                { "Quản lý tài khoản", false, false, false, false }
        };

        // Nạp dữ liệu phân quyền từ "DB" hoặc nơi lưu trữ theo mã nhóm quyền
        Map<String, boolean[]> quyenMap = loadQuyenTheoMaNhom(maNhomQuyen);

        for (int i = 0; i < data.length; i++) {
            String chucNang = (String) data[i][0];
            if (quyenMap.containsKey(chucNang)) {
                boolean[] q = quyenMap.get(chucNang);
                for (int j = 0; j < q.length; j++) {
                    data[i][j + 1] = q[j];
                }
            }
        }

        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JLabel lblTitle = new JLabel("Tên nhóm quyền", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        header.add(lblTitle);
        txtTenNhomQuyen = new JTextField(12);
        txtTenNhomQuyen.setText(new NhomQuyenDAO().getTenNhomQuyen(maNhomQuyen));
        txtTenNhomQuyen.setFont(new Font("Arial", Font.PLAIN, 14));
        txtTenNhomQuyen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        header.setPreferredSize(new Dimension(0, 40));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        header.add(txtTenNhomQuyen);
        add(header, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? String.class : Boolean.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return mode != Mode.VIEW && column > 0;
            }
        };

        table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();

        btnLuu = new JButton("Lưu");
        btnLuu.addActionListener(e -> {
            if (txtTenNhomQuyen.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên nhóm quyền không được để trống!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Lưu dữ liệu
            for (int i = 0; i < table.getRowCount(); i++) {
                String chucNang = (String) table.getValueAt(i, 0);
                boolean xem = (Boolean) table.getValueAt(i, 1);
                boolean sua = (Boolean) table.getValueAt(i, 2);
                boolean chiTiet = (Boolean) table.getValueAt(i, 3);
                boolean xoa = (Boolean) table.getValueAt(i, 4);
                System.out.printf("[%s] %s - Xem: %b, Sửa: %b, Chi tiết: %b, Xóa: %b%n",
                        maNhomQuyen, chucNang, xem, sua, chiTiet, xoa);
            }
            if (mode == Mode.ADD) {
                // Thêm nhóm quyền mới
                NhomQuyenDAO nhomQuyenDAO = new NhomQuyenDAO();
                nhomQuyenDAO.insert(new NhomQuyenDTO(txtTenNhomQuyen.getText()));
                int hihi = nhomQuyenDAO.getMAXID();
                for (int i = 0; i < table.getRowCount(); i++) {
                    boolean xem = (Boolean) table.getValueAt(i, 1);
                    boolean sua = (Boolean) table.getValueAt(i, 2);
                    boolean chiTiet = (Boolean) table.getValueAt(i, 3);
                    boolean xoa = (Boolean) table.getValueAt(i, 4);
                    nhomQuyenDAO.insert(hihi, i + 1, xem, sua, chiTiet, xoa);
                }
            } else if (mode == Mode.EDIT) {
                // Cập nhật nhóm quyền
                NhomQuyenDAO nhomQuyenDAO = new NhomQuyenDAO();
                for (int i = 0; i < table.getRowCount(); i++) {
                    // String chucNang = (String) table.getValueAt(i, 0);
                    boolean xem = (Boolean) table.getValueAt(i, 1);
                    boolean sua = (Boolean) table.getValueAt(i, 2);
                    boolean chiTiet = (Boolean) table.getValueAt(i, 3);
                    boolean xoa = (Boolean) table.getValueAt(i, 4);
                    nhomQuyenDAO.update(maNhomQuyen, xem, sua, chiTiet, xoa, i + 1);
                    nhomQuyenDAO.update(maNhomQuyen, txtTenNhomQuyen.getText());
                }
            }
            dispose();
        });

        btnDong = new JButton("Đóng");
        btnDong.addActionListener(e -> dispose());

        if (mode == Mode.ADD || mode == Mode.EDIT) {
            bottomPanel.add(btnLuu);
        }
        bottomPanel.add(btnDong);

        add(bottomPanel, BorderLayout.SOUTH);

        if (mode == Mode.VIEW)
            disableAll();
    }

    // Giả lập dữ liệu quyền theo mã nhóm
    private Map<String, boolean[]> loadQuyenTheoMaNhom(Integer maNhom) {
        Map<String, boolean[]> map = new HashMap<>();
        ChucNangDAO chucNangDAO = new ChucNangDAO();
        NhomQuyenDAO nhomQuyenDAO = new NhomQuyenDAO();

        for (int i = 1; i <= 11; ++i) {
            String tenChucNang = chucNangDAO.getTenChucNang(i);
            boolean[] quyen = nhomQuyenDAO.loadQuyenTheoMaNhom(maNhom, i);

            // System.out.println("Chuc nang: " + tenChucNang);
            // System.out.println("Quyen: " + Arrays.toString(quyen));

            map.put(tenChucNang, quyen);
        }
        return map;
    }

    private void disableAll() {
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellEditor(null);
        }
        table.setEnabled(false);
        txtTenNhomQuyen.setEditable(false);
        btnLuu.setEnabled(false);
    }
}
