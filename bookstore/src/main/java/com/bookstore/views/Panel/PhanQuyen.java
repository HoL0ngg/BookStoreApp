package com.bookstore.views.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.bookstore.BUS.PhanQuyenBUS;
import com.bookstore.DTO.NhomQuyenDTO;
import com.bookstore.dao.NhomQuyenDAO;
import com.bookstore.utils.ExcelExporter;
import com.bookstore.views.Dialog.PhanQuyenDialog;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;

public class PhanQuyen extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtTimKiem;
    private JComboBox<String> cboFilter;
    private JPanel btnThem, btnSua, btnXoa, btnChiTiet, btnXuatExcel, btnLamMoi;
    private Timer searchTimer;

    private PhanQuyenBUS phanQuyenBUS = new PhanQuyenBUS();

    public PhanQuyen() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        // NORTH PANEL (buttons + filter)
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

        btnThem = createPanel("Thêm", "add");
        btnSua = createPanel("Sửa", "edit");
        btnXoa = createPanel("Xóa", "delete");
        btnChiTiet = createPanel("Chi tiết", "ChiTiet");
        btnXuatExcel = createPanel("Xuất Excel", "importExcel");
        btnLamMoi = createPanel("Làm mới", "refresh");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnChiTiet);
        buttonPanel.add(btnXuatExcel);

        // Right filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        cboFilter = new JComboBox<>(
                new String[] { "Tất cả", "Mã nhóm quyền", "Tên nhóm quyền" });
        txtTimKiem = new JTextField(15);
        txtTimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (searchTimer != null) {
                    searchTimer.cancel();
                }
                searchTimer = new Timer();
                searchTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        SwingUtilities.invokeLater(() -> performSearch());
                    }
                }, 300);
            }
        });

        filterPanel.add(cboFilter);
        filterPanel.add(txtTimKiem);
        filterPanel.add(btnLamMoi);

        northPanel.add(buttonPanel, BorderLayout.WEST);
        northPanel.add(filterPanel, BorderLayout.EAST);
        add(northPanel, BorderLayout.NORTH);

        // CENTER (table)
        String[] columns = {
                "Mã nhóm quyền", "Tên nhóm quyền"
        };
        Object[][] data = new Object[phanQuyenBUS.getList().size()][columns.length];
        for (int i = 0; i < phanQuyenBUS.getList().size(); i++) {
            data[i][0] = phanQuyenBUS.getList().get(i).getMaNhomQuyen();
            data[i][1] = phanQuyenBUS.getList().get(i).getTenNhomQuyen();
        }

        tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable editing
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PhanQuyenDialog dialog = new PhanQuyenDialog(((Frame) SwingUtilities.getWindowAncestor(PhanQuyen.this)),
                        PhanQuyenDialog.Mode.ADD, 0);
                dialog.setVisible(true);
                updateData(phanQuyenBUS.getList());
            }
        });

        btnSua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Integer maNhomQuyen = (Integer) tableModel.getValueAt(selectedRow, 0);
                    // NCCDTO ncc = new NCCDAO().selectById(maNCC);
                    PhanQuyenDialog dialog = new PhanQuyenDialog(
                            ((Frame) SwingUtilities.getWindowAncestor(PhanQuyen.this)),
                            PhanQuyenDialog.Mode.EDIT, maNhomQuyen);
                    dialog.setVisible(true);
                    updateData(phanQuyenBUS.getList());
                } else {
                    JOptionPane.showMessageDialog(PhanQuyen.this, "Vui lòng chọn nhóm quyền để sửa.");
                }
            }
        });

        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Integer maNhomQuyen = (Integer) tableModel.getValueAt(selectedRow, 0);
                    int confirm = JOptionPane.showConfirmDialog(PhanQuyen.this,
                            "Bạn có chắc chắn muốn xóa nhà cung cấp này không?", "Xác nhận",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        new NhomQuyenDAO().delete(maNhomQuyen);
                    }
                } else {
                    JOptionPane.showMessageDialog(PhanQuyen.this, "Vui lòng chọn nhóm quyền để xóa.");
                }
            }
        });

        btnChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Integer maNhomQuyen = (Integer) tableModel.getValueAt(selectedRow, 0);
                    PhanQuyenDialog dialog = new PhanQuyenDialog(
                            ((Frame) SwingUtilities.getWindowAncestor(PhanQuyen.this)),
                            PhanQuyenDialog.Mode.VIEW, maNhomQuyen);
                    dialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(PhanQuyen.this, "Vui lòng chọn nhóm quyền để xem chi tiết.");
                }
            }
        });

        btnLamMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTimKiem.setText("");
                updateData(phanQuyenBUS.getList());
            }
        });
        btnXuatExcel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!path.endsWith(".xlsx")) {
                        path += ".xlsx";
                    }
                    ExcelExporter.exportToExcel(phanQuyenBUS.getList(), path);
                    JOptionPane.showMessageDialog(PhanQuyen.this, "Xuất file thành công!", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

    }

    private void performSearch() {
        String type = (String) this.cboFilter.getSelectedItem();
        String txt = this.txtTimKiem.getText().trim();
        System.out.println("Đang tìm kiếm: " + txt + " - Loại: " + type);
        List<NhomQuyenDTO> result = phanQuyenBUS.search(txt, type);
        updateData(result);
    }

    private void updateData(List<NhomQuyenDTO> result) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        for (NhomQuyenDTO ncc : result) {
            Object[] row = new Object[6];
            row[0] = ncc.getMaNhomQuyen();
            row[1] = ncc.getTenNhomQuyen();
            tableModel.addRow(row);
        }
    }

    private JPanel createPanel(String text, String iconName) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(72, 80));
        panel.setBackground(Color.white);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        FlatSVGIcon icon = new FlatSVGIcon(getClass().getResource("/svg/" + iconName + ".svg")).derive(30, 30);
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(new JLabel(icon));
        panel.add(label);
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        return panel;
    }

    // Getter nếu cần thao tác từ bên ngoài
    public JTable getTable() {
        return table;
    }

    public JPanel getBtnThem() {
        return btnThem;
    }

    public JPanel getBtnSua() {
        return btnSua;
    }

    public JPanel getBtnXoa() {
        return btnXoa;
    }

    public JPanel getBtnChiTiet() {
        return btnChiTiet;
    }

    public JPanel getBtnXuatExcel() {
        return btnXuatExcel;
    }

    public JPanel getBtnLamMoi() {
        return btnLamMoi;
    }

    public JComboBox<String> getCboFilter() {
        return cboFilter;
    }

    public JTextField getTxtTimKiem() {
        return txtTimKiem;
    }
}
