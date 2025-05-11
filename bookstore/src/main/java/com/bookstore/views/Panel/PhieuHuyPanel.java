package com.bookstore.views.Panel;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.bookstore.BUS.PhieuHuyBUS;
import com.bookstore.DTO.ChiTietPhieuHuyDTO;
import com.bookstore.DTO.PhieuHuyDTO;
import com.bookstore.DTO.SachDTO;
import com.bookstore.dao.ChiTietPhieuHuyDAO;
import com.bookstore.dao.DauSachDAO;
import com.bookstore.dao.PhieuHuyDAO;
import com.bookstore.dao.SachDAO;

public class PhieuHuyPanel extends JPanel {

    private JTable tableSach;
    private DefaultTableModel tableModel;
    private JButton btnTimKiem, btnGuiPhieuHuy;
    private JTextField txtMaPhieuHuy;

    private List<SachDTO> listSachHuMat;

    public PhieuHuyPanel() {
        setLayout(new BorderLayout());
        initComponents();
        loadSachHuMat();
    }

    private void initComponents() {
        // Panel trên cùng: mã phiếu hủy và nút tìm
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtMaPhieuHuy = new JTextField(15);
        txtMaPhieuHuy.setEditable(false);
        txtMaPhieuHuy.setText(sinhMaPhieuHuy());

        btnTimKiem = new JButton("Tìm sách hư/mất");
        btnTimKiem.addActionListener(e -> loadSachHuMat());

        topPanel.add(new JLabel("Mã Phiếu Hủy:"));
        topPanel.add(txtMaPhieuHuy);
        topPanel.add(btnTimKiem);

        // Bảng hiển thị sách
        tableModel = new DefaultTableModel(new String[] {
            "Chọn", "Mã Sách", "Tình Trạng", "Tiêu Đề"
        }, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // chỉ cho phép check
            }
        };

        tableSach = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableSach);

        // Nút gửi phiếu hủy
        btnGuiPhieuHuy = new JButton("Gửi phiếu hủy");
        btnGuiPhieuHuy.addActionListener(e -> guiPhieuHuy());

        // Layout tổng thể
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnGuiPhieuHuy, BorderLayout.SOUTH);
    }

    private void loadSachHuMat() {
        tableModel.setRowCount(0);
        listSachHuMat = new SachDAO().selectByTrangThaiHuOrMat(); // tạo hàm này

        DauSachDAO dauSachDAO = new DauSachDAO();

        for (SachDTO sach : listSachHuMat) {
            String tenDauSach = dauSachDAO.getTenDauSachByMa(sach.getMaDauSach());
            tableModel.addRow(new Object[] {
                false,
                sach.getMaSach(),
                sach.getTrangThai().equals("0") ? "Mất" : "Hư",
                tenDauSach
            });
        }
    }

    private void guiPhieuHuy() {
        List<SachDTO> sachDuocChon = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            boolean isChecked = (boolean) tableModel.getValueAt(i, 0);
            if (isChecked) {
                sachDuocChon.add(listSachHuMat.get(i));
            }
        }

        if (sachDuocChon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một sách.");
            return;
        }

       String maPhieu = txtMaPhieuHuy.getText();
        PhieuHuyDTO phieu = new PhieuHuyDTO(maPhieu, LocalDate.now(), "NV001", PhieuHuyDTO.CHO_DUYET);
        PhieuHuyDAO phieuDAO = new PhieuHuyDAO();
        ChiTietPhieuHuyDAO ctDAO = new ChiTietPhieuHuyDAO();

        if (phieuDAO.insert(phieu) > 0) {
            for (SachDTO sach : sachDuocChon) {
                ctDAO.insert(new ChiTietPhieuHuyDTO(maPhieu, sach.getMaSach()));
            }
            JOptionPane.showMessageDialog(this, "\u0110\u00e3 g\u1eedi phi\u1ebfu h\u1ee7y \u0111\u1ebfn qu\u1ea3n l\u00fd.");
            txtMaPhieuHuy.setText(sinhMaPhieuHuy());
            loadSachHuMat();
        } else {
            JOptionPane.showMessageDialog(this, "G\u1eedi phi\u1ebfu th\u1ea5t b\u1ea1i.");
        }
    }
    private String sinhMaPhieuHuy() {
        int soLuong = new PhieuHuyDAO().selectCount();
        return "PH" + String.format("%03d", soLuong + 1);
    }
}

// Ở giao diện QUẢN LÝ THÌ PHIẾU HỦY LẠI KHÁC, CÒN NHÂN VIÊN LẠI KHACS