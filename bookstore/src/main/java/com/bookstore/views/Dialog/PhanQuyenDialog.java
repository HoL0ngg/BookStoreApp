package com.bookstore.views.Dialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.util.HashMap;
import java.util.Map;

public class PhanQuyenDialog extends JDialog {

    public enum Mode {
        ADD, EDIT, VIEW
    }

    private JTable table;
    private JButton btnLuu, btnDong;

    public PhanQuyenDialog(Frame parent, Mode mode, String maNhomQuyen) {
        super(parent, "Phân quyền nhân viên", true);
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        String[] columnNames = { "Tên chức năng", "Xem", "Sửa", "Chi tiết", "Xóa" };
        Object[][] data = {
                { "Quản lý nhân viên", false, false, false, false },
                { "Quản lý độc giả", false, false, false, false },
                { "Quản lý sách", false, false, false, false },
                { "Thống kê", false, false, false, false }
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
            dispose();
        });

        btnDong = new JButton("Đóng");
        btnDong.addActionListener(e -> dispose());

        if (mode == Mode.ADD || mode == Mode.EDIT) {
            bottomPanel.add(btnLuu);
        }
        bottomPanel.add(btnDong);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Giả lập dữ liệu quyền theo mã nhóm
    private Map<String, boolean[]> loadQuyenTheoMaNhom(String maNhom) {
        Map<String, boolean[]> map = new HashMap<>();

        if ("admin".equalsIgnoreCase(maNhom)) {
            map.put("Quản lý nhân viên", new boolean[] { true, true, true, true });
            map.put("Quản lý độc giả", new boolean[] { true, true, true, true });
            map.put("Quản lý sách", new boolean[] { true, true, false, true });
            map.put("Thống kê", new boolean[] { true, false, false, false });
        } else if ("nhanvien".equalsIgnoreCase(maNhom)) {
            map.put("Quản lý độc giả", new boolean[] { true, true, false, false });
            map.put("Quản lý sách", new boolean[] { true, false, false, false });
        }

        return map;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PhanQuyenDialog dialog = new PhanQuyenDialog(null, Mode.VIEW, "admin"); // thử "admin", "nhanvien"
            dialog.setVisible(true);
        });
    }
}
