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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.bookstore.BUS.DauSachBUS;
import com.bookstore.DTO.DauSachDTO;
import com.bookstore.dao.DauSachDAO;
import com.bookstore.dao.NhomQuyenDAO;
import com.bookstore.utils.ExcelExporter;
import com.bookstore.utils.NguoiDungDangNhap;
import com.bookstore.views.Dialog.DauSachDialog;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Frame;

public class Sach extends JPanel {
    // private SachController sachController = new SachController(this);
    private DauSachBUS dauSachBUS = new DauSachBUS();
    private JPanel btnThem, btnSua, btnXoa, btnChiTiet, btnXuatExcel, btnNhapExcel, btnLamMoi;
    private JComboBox<String> cboFilter;
    private JTextField txtTimKiem;
    private JTable table;
    private DefaultTableModel tableModel;
    private Timer searchTimer;

    private void initComponent() {
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
        btnNhapExcel = createPanel("Nhập Excel", "importExcel2");

        btnLamMoi = createPanel("Làm mới", "refresh");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnChiTiet);
        buttonPanel.add(btnNhapExcel);
        buttonPanel.add(btnXuatExcel);

        // Right filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        cboFilter = new JComboBox<>(
                new String[] { "Tất cả", "Mã đầu sách", "Tên đầu sách", "Nhà xuất bản", "Năm xuất bản", "Tác giả",
                        "Thể loại", "Ngôn ngữ" });
        txtTimKiem = new JTextField(12);
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
                "Mã đầu sách", "Tên đầu sách", "Số lượng", "Nhà xuất bản", "Năm xuất bản", "Ngôn ngữ"
        };

        Object[][] data = new Object[dauSachBUS.getList().size()][columns.length];
        for (int i = 0; i < dauSachBUS.getList().size(); i++) {
            DauSachDTO sach = dauSachBUS.getList().get(i);
            // System.out.println(i + " " + sach.getListTacGia());
            data[i][0] = sach.getMaDauSach();
            data[i][1] = sach.getTenDauSach();
            data[i][2] = sach.getSoLuong();
            data[i][3] = sach.getNhaXuatBan();
            data[i][4] = sach.getNamXuatBan();
            data[i][5] = sach.getNgonNgu();
        }

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(20);
        table.getColumnModel().getColumn(4).setPreferredWidth(20);
        for (int i = 0; i < columns.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (!new NhomQuyenDAO().isAccessable(NguoiDungDangNhap.getInstance().getMaNhomQuyen(), 1, 2)) {
                    JOptionPane.showMessageDialog(Sach.this, "Bạn không có quyền thêm đầu sách.");
                    return;
                }
                new DauSachDialog(((Frame) SwingUtilities.getWindowAncestor(Sach.this)),
                        DauSachDialog.Mode.ADD, null).setVisible(true);
            }
        });

        btnSua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (!new NhomQuyenDAO().isAccessable(NguoiDungDangNhap.getInstance().getMaNhomQuyen(), 1, 3)) {
                    JOptionPane.showMessageDialog(Sach.this, "Bạn không có quyền sửa đầu sách.");
                    return;
                }
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String maDauSach = (String) tableModel.getValueAt(selectedRow, 0);
                    DauSachDTO sach = new DauSachDAO().selectById(maDauSach);
                    new DauSachDialog(((Frame) SwingUtilities.getWindowAncestor(Sach.this)),
                            DauSachDialog.Mode.EDIT, sach).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(Sach.this, "Vui lòng chọn đầu sách để sửa.");
                }
            }
        });

        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (!new NhomQuyenDAO().isAccessable(NguoiDungDangNhap.getInstance().getMaNhomQuyen(), 1, 4)) {
                    JOptionPane.showMessageDialog(Sach.this, "Bạn không có quyền xóa đầu sách.");
                    return;
                }
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String maDauSach = (String) tableModel.getValueAt(selectedRow, 0);
                    int confirm = JOptionPane.showConfirmDialog(Sach.this,
                            "Bạn có chắc chắn muốn xóa đầu sách này không?", "Xác nhận",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        dauSachBUS.delete(maDauSach);
                        updateData(dauSachBUS.getList());
                    }
                } else {
                    JOptionPane.showMessageDialog(Sach.this, "Vui lòng chọn đầu sách để xóa.");
                }
            }
        });

        btnChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (!new NhomQuyenDAO().isAccessable(NguoiDungDangNhap.getInstance().getMaNhomQuyen(),
                        1, 1)) {
                    JOptionPane.showMessageDialog(Sach.this, "Bạn không có quyền xem chi tiết đầu sách.");
                    return;
                }
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String maDauSach = (String) tableModel.getValueAt(selectedRow, 0);
                    DauSachDTO dauSachDTO = new DauSachDAO().selectById(maDauSach);
                    new DauSachDialog(((Frame) SwingUtilities.getWindowAncestor(Sach.this)),
                            DauSachDialog.Mode.VIEW, dauSachDTO).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(Sach.this, "Vui lòng chọn đầu sách để xem chi tiết.");
                }
            }
        });

        btnLamMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTimKiem.setText("");
                updateData(dauSachBUS.getList());
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
                    ExcelExporter.exportToExcel(dauSachBUS.getList(), path);
                    JOptionPane.showMessageDialog(Sach.this, "Xuất file thành công!", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

    }

    public Sach() {
        initComponent();
    }

    private JPanel createPanel(String text, String iconName) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(74, 80));
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
        String searchText = txtTimKiem.getText().trim();
        String selectedItem = (String) cboFilter.getSelectedItem();
        if (searchText.isEmpty()) {
            updateData(dauSachBUS.getList());
            return;
        }
        List<DauSachDTO> filteredList = dauSachBUS.searchDauSach(searchText, selectedItem);
        updateData(filteredList);
    }

    private void updateData(List<DauSachDTO> list) {
        tableModel.setRowCount(0);
        for (DauSachDTO sach : list) {
            tableModel.addRow(new Object[] { sach.getMaDauSach(), sach.getTenDauSach(), sach.getSoLuong(),
                    sach.getNhaXuatBan(), sach.getNamXuatBan(), sach.getNgonNgu() });
        }
    }
}
