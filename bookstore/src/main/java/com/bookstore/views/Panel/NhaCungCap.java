package com.bookstore.views.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.bookstore.BUS.NCCBUS;
import com.bookstore.DTO.NCCDTO;
import com.bookstore.dao.NCCDAO;
import com.bookstore.utils.ExcelExporter;
import com.bookstore.views.Dialog.NCCDialog;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;

public class NhaCungCap extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtTimKiem;
    private JComboBox<String> cboFilter;
    private JPanel btnThem, btnSua, btnXoa, btnChiTiet, btnXuatExcel, btnLamMoi;
    private Timer searchTimer;

    private NCCBUS nccBUS = new NCCBUS();

    public NhaCungCap() {
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
                new String[] { "Tất cả", "Mã NCC", "Tên NCC", "Số điện thoại", "Email", "Địa chỉ" });
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
                "Mã NCC", "Tên NCC", "Số điện thoại", "Email", "Địa chỉ", "Trạng thái"
        };
        Object[][] data = new Object[nccBUS.getList().size()][columns.length];
        for (int i = 0; i < nccBUS.getList().size(); i++) {
            data[i][0] = nccBUS.getList().get(i).getMaNCC();
            data[i][1] = nccBUS.getList().get(i).getTenNCC();
            data[i][2] = nccBUS.getList().get(i).getSoDienThoai();
            data[i][3] = nccBUS.getList().get(i).getEmail();
            data[i][4] = nccBUS.getList().get(i).getDiaChi();
            data[i][5] = nccBUS.getList().get(i).getStatus() == 1 ? "Hoạt động" : "Ngừng hoạt động";
        }

        tableModel = new DefaultTableModel(data, columns);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NCCDialog dialog = new NCCDialog(((Frame) SwingUtilities.getWindowAncestor(NhaCungCap.this)),
                        NCCDialog.Mode.ADD, null);
                dialog.setVisible(true);
                if (dialog.isSaved()) {
                    NCCDTO ncc = new NCCDTO(dialog.getMaNCC(), dialog.getTenNCC(), dialog.getSoDienThoai(),
                            dialog.getEmail(), dialog.getDiaChi(), dialog.getTrangThai() == "Hoạt động" ? 1 : 0);
                    nccBUS.add(ncc);
                    updateData(nccBUS.getList());
                }
            }
        });

        btnSua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int maNCC = (int) tableModel.getValueAt(selectedRow, 0);
                    NCCDTO ncc = new NCCDAO().selectById(maNCC);
                    NCCDialog dialog = new NCCDialog(((Frame) SwingUtilities.getWindowAncestor(NhaCungCap.this)),
                            NCCDialog.Mode.EDIT, ncc);
                    dialog.setVisible(true);
                    if (dialog.isSaved()) {
                        ncc.setTenNCC(dialog.getTenNCC());
                        ncc.setSoDienThoai(dialog.getSoDienThoai());
                        ncc.setEmail(dialog.getEmail());
                        ncc.setDiaChi(dialog.getDiaChi());
                        ncc.setStatus(dialog.getTrangThai() == "Hoạt động" ? 1 : 0);
                        nccBUS.update(ncc);
                        updateData(nccBUS.getList());
                    }
                } else {
                    JOptionPane.showMessageDialog(NhaCungCap.this, "Vui lòng chọn nhà cung cấp để sửa.");
                }
            }
        });

        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int maNCC = (int) tableModel.getValueAt(selectedRow, 0);
                    int confirm = JOptionPane.showConfirmDialog(NhaCungCap.this,
                            "Bạn có chắc chắn muốn xóa nhà cung cấp này không?", "Xác nhận",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        nccBUS.delete(maNCC);
                        updateData(nccBUS.getList());
                    }
                } else {
                    JOptionPane.showMessageDialog(NhaCungCap.this, "Vui lòng chọn nhà cung cấp để xóa.");
                }
            }
        });

        btnChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int maNCC = (int) tableModel.getValueAt(selectedRow, 0);
                    NCCDTO ncc = new NCCDAO().selectById(maNCC);
                    NCCDialog dialog = new NCCDialog(((Frame) SwingUtilities.getWindowAncestor(NhaCungCap.this)),
                            NCCDialog.Mode.VIEW, ncc);
                    dialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(NhaCungCap.this, "Vui lòng chọn nhà cung cấp để xem chi tiết.");
                }
            }
        });

        btnLamMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTimKiem.setText("");
                updateData(nccBUS.getList());
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
                    ExcelExporter.exportToExcel(nccBUS.getList(), path);
                    JOptionPane.showMessageDialog(NhaCungCap.this, "Xuất file thành công!", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

    }

    private void performSearch() {
        String type = (String) this.cboFilter.getSelectedItem();
        String txt = this.txtTimKiem.getText().trim();
        System.out.println("Đang tìm kiếm: " + txt + " - Loại: " + type);
        List<NCCDTO> result = nccBUS.search(txt, type);
        updateData(result);
    }

    private void updateData(List<NCCDTO> result) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        for (NCCDTO ncc : result) {
            Object[] row = new Object[6];
            row[0] = ncc.getMaNCC();
            row[1] = ncc.getTenNCC();
            row[2] = ncc.getSoDienThoai();
            row[3] = ncc.getEmail();
            row[4] = ncc.getDiaChi();
            row[5] = ncc.getStatus() == 1 ? "Hoạt động" : "Ngừng hoạt động";
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
