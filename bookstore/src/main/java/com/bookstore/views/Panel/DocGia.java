package com.bookstore.views.Panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.bookstore.DTO.DocGiaDTO;
import com.bookstore.dao.DocGiaDAO;

public class DocGia extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtMaDocGia, txtTenDocGia, txtDiachi, txtSĐT, txtTrangThai, txtStatus;
    private JButton btnThem, btnSua, btnXoa;

    private DocGiaDAO docGiaDAO = new DocGiaDAO();

    public DocGia() {
        initComponents();
        loadDocGiaToTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Table
        tableModel = new DefaultTableModel(
                new String[] { "Mã độc giả", "Tên độc giả", "Đỉa chỉ", "SĐT", "Trạng thái", "Status" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Form input
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        txtMaDocGia = new JTextField();
        txtTenDocGia = new JTextField();
        txtDiachi = new JTextField();
        txtSĐT = new JTextField();
        txtTrangThai = new JTextField();
        txtStatus = new JTextField();
        formPanel.add(new JLabel("Mã độc giả:"));
        formPanel.add(txtMaDocGia);
        formPanel.add(new JLabel("Tên độc giả:"));
        formPanel.add(txtTenDocGia);
        formPanel.add(new JLabel("Địa chỉ:"));
        formPanel.add(txtDiachi);
        formPanel.add(new JLabel("SĐT:"));
        formPanel.add(txtSĐT);
        formPanel.add(new JLabel("Trạng thái:"));
        formPanel.add(txtTrangThai);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(txtStatus);

        // Buttons
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);

        // South panel
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add to main panel
        add(scrollPane, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        // Events
        btnThem.addActionListener(e -> themDocGia());
        btnSua.addActionListener(e -> suaDocGia());
        btnXoa.addActionListener(e -> xoaDocGia());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaDocGia.setText(tableModel.getValueAt(row, 0).toString());
                    txtTenDocGia.setText(tableModel.getValueAt(row, 1).toString());
                    txtDiachi.setText(tableModel.getValueAt(row, 2).toString());
                    txtSĐT.setText(tableModel.getValueAt(row, 3).toString());
                    txtTrangThai.setText(tableModel.getValueAt(row, 4).toString());
                    txtStatus.setText(tableModel.getValueAt(row, 5).toString());
                }
            }
        });
    }

    private void loadDocGiaToTable() {
        tableModel.setRowCount(0);
        ArrayList<DocGiaDTO> list = docGiaDAO.getAllDocGia();
        for (DocGiaDTO dg : list) {
            if (dg.getStatus() == 0)
                continue;
            tableModel.addRow(new Object[] {
                    dg.getMaDocGia(),
                    dg.getTenDocGia(),
                    dg.getdiachi(),
                    dg.getSDT(),
                    dg.getTrangThai(),
                    dg.getStatus(),
            });
        }
    }

    private void themDocGia() {
        DocGiaDTO dg = new DocGiaDTO(
                txtMaDocGia.getText(),
                txtTenDocGia.getText(),
                txtDiachi.getText(),
                txtSĐT.getText(),
                txtTrangThai.getText(),
                Integer.parseInt(txtStatus.getText()));

        if (docGiaDAO.insertDocGia(dg)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            loadDocGiaToTable();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
        }
    }

    private void suaDocGia() {
        DocGiaDTO dg = new DocGiaDTO(
                txtMaDocGia.getText(),
                txtTenDocGia.getText(),
                txtDiachi.getText(),
                txtSĐT.getText(),
                txtTrangThai.getText(),
                Integer.parseInt(txtStatus.getText()));

        if (docGiaDAO.updateDocGia(dg)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loadDocGiaToTable();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }
    }

    private void xoaDocGia() {
        String maDocGia = txtMaDocGia.getText();
        if (maDocGia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn độc giả để xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (docGiaDAO.deleteDocGia(maDocGia)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadDocGiaToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }
}
