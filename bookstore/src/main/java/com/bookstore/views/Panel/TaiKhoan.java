package com.bookstore.views.Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.bookstore.BUS.TaiKhoanBUS;
import com.bookstore.DTO.TaiKhoanDTO;
import com.bookstore.dao.NhomQuyenDAO;
import com.bookstore.dao.TaiKhoanDAO;
import com.bookstore.utils.ExcelExporter;
import com.bookstore.utils.NguoiDungDangNhap;
import com.bookstore.views.Dialog.TaiKhoanDialog;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Frame;

public class TaiKhoan extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtTimKiem;
    private JComboBox<String> cboFilter;
    private JPanel btnThem, btnSua, btnXoa, btnChiTiet, btnXuatExcel, btnLamMoi;
    private Timer searchTimer;

    private TaiKhoanBUS taikhoanBus;

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
                new String[] { "Tất cả", "Tên đăng nhập", "Email", "Tên nhóm quyền", "Trạng thái" });
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
                "Tên đăng nhập", "Mật khẩu", "Email", "Tên nhóm quyền", "Trạng thái"
        };
        Object[][] data = new Object[taikhoanBus.getList().size()][columns.length];
        for (int i = 0; i < taikhoanBus.getList().size(); i++) {
            data[i][0] = taikhoanBus.getList().get(i).getTenDangNhap();
            data[i][1] = taikhoanBus.getList().get(i).getMatKhau();
            data[i][2] = taikhoanBus.getList().get(i).getEmail();
            data[i][3] = new TaiKhoanDAO().getTenNhomQuyen(taikhoanBus.getList().get(i).getMaNhomQuyen());
            data[i][4] = taikhoanBus.getList().get(i).getTrangThai() == 1 ? "Hoạt động" : "Ngừng hoạt động";
        }

        tableModel = new DefaultTableModel(data, columns);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // if (new
                // NhomQuyenDAO().isAccessable(NguoiDungDangNhap.getInstance().getMaNhomQuyen(),
                // 11, 2)) {
                // JOptionPane.showMessageDialog(TaiKhoan.this, "Bạn không có quyền thêm tài
                // khoản.");
                // return;
                // }
                TaiKhoanDialog dialog = new TaiKhoanDialog((Frame) SwingUtilities.getWindowAncestor(TaiKhoan.this),
                        TaiKhoanDialog.Mode.ADD, null);
                dialog.setVisible(true);
                if (dialog.isSaved()) {
                    TaiKhoanDTO taikhoan = new TaiKhoanDTO();
                    taikhoan.setTenDangNhap(dialog.getTenDangNhap());
                    taikhoan.setMatKhau(dialog.getTxtMatKhau());
                    taikhoan.setEmail(dialog.getTxtEmail());
                    taikhoan.setMaNhomQuyen(dialog.getCboNhomQuyen());
                    taikhoan.setTrangThai(dialog.getCboTrangThai());
                    taikhoanBus.add(taikhoan);
                    updateData(taikhoanBus.getList());
                }
            }
        });

        btnSua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (new NhomQuyenDAO().isAccessable(NguoiDungDangNhap.getInstance().getMaNhomQuyen(), 11, 3)) {
                    JOptionPane.showMessageDialog(TaiKhoan.this, "Bạn không có quyền sửa tài khoản.");
                    return;
                }
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String TenDangNhap = (String) tableModel.getValueAt(selectedRow, 0);
                    TaiKhoanDTO taikhoan = new TaiKhoanDAO().selectById(TenDangNhap);
                    TaiKhoanDialog dialog = new TaiKhoanDialog(
                            ((Frame) SwingUtilities.getWindowAncestor(TaiKhoan.this)),
                            TaiKhoanDialog.Mode.EDIT, taikhoan);
                    dialog.setVisible(true);
                    if (dialog.isSaved()) {
                        taikhoan.setTenDangNhap(dialog.getTenDangNhap());
                        taikhoan.setMatKhau(dialog.getTxtMatKhau());
                        taikhoan.setEmail(dialog.getTxtEmail());
                        taikhoan.setMaNhomQuyen(dialog.getCboNhomQuyen());
                        taikhoan.setTrangThai(dialog.getCboTrangThai());
                        taikhoanBus.update(taikhoan);
                        updateData(taikhoanBus.getList());
                    }
                } else {
                    JOptionPane.showMessageDialog(TaiKhoan.this, "Vui lòng chọn nhà cung cấp để sửa.");
                }
            }
        });

        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (new NhomQuyenDAO().isAccessable(NguoiDungDangNhap.getInstance().getMaNhomQuyen(), 11, 4)) {
                    JOptionPane.showMessageDialog(TaiKhoan.this, "Bạn không có quyền xóa tài khoản.");
                    return;
                }
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String TenDangNhap = (String) tableModel.getValueAt(selectedRow, 0);
                    int confirm = JOptionPane.showConfirmDialog(TaiKhoan.this,
                            "Bạn có chắc chắn muốn xóa nhà cung cấp này không?", "Xác nhận",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        taikhoanBus.delete(TenDangNhap);
                        updateData(taikhoanBus.getList());
                    }
                } else {
                    JOptionPane.showMessageDialog(TaiKhoan.this, "Vui lòng chọn nhà cung cấp để xóa.");
                }
            }
        });

        btnChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (new NhomQuyenDAO().isAccessable(NguoiDungDangNhap.getInstance().getMaNhomQuyen(), 11, 1)) {
                    JOptionPane.showMessageDialog(TaiKhoan.this, "Bạn không có quyền xem chi tiết tài khoản.");
                    return;
                }
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String TenDangNhap = (String) tableModel.getValueAt(selectedRow, 0);
                    TaiKhoanDTO ncc = new TaiKhoanDAO().selectById(TenDangNhap);
                    TaiKhoanDialog dialog = new TaiKhoanDialog(
                            ((Frame) SwingUtilities.getWindowAncestor(TaiKhoan.this)),
                            TaiKhoanDialog.Mode.VIEW, ncc);
                    dialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(TaiKhoan.this, "Vui lòng chọn nhà cung cấp để xem chi tiết.");
                }
            }
        });

        btnLamMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTimKiem.setText("");
                updateData(taikhoanBus.getList());
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
                    ExcelExporter.exportToExcel(taikhoanBus.getList(), path);
                    JOptionPane.showMessageDialog(TaiKhoan.this, "Xuất file thành công!", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

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

    private void performSearch() {
        String type = (String) this.cboFilter.getSelectedItem();
        String txt = this.txtTimKiem.getText().trim();
        System.out.println("Đang tìm kiếm: " + txt + " - Loại: " + type);
        List<TaiKhoanDTO> result = taikhoanBus.search(txt, type);
        updateData(result);
    }

    private void updateData(List<TaiKhoanDTO> result) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        for (TaiKhoanDTO ncc : result) {
            Object[] row = new Object[5];
            row[0] = ncc.getTenDangNhap();
            row[1] = ncc.getMatKhau();
            row[2] = ncc.getEmail();
            row[3] = new TaiKhoanDAO().getTenNhomQuyen(ncc.getMaNhomQuyen());
            row[4] = ncc.getTrangThai() == 1 ? "Hoạt động" : "Ngừng hoạt động";
            tableModel.addRow(row);
        }
    }

    public TaiKhoan() {
        taikhoanBus = new TaiKhoanBUS();
        initComponents();
    }
}
