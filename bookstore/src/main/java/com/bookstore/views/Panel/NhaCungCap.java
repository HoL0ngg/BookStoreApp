package com.bookstore.views.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.bookstore.BUS.NCCBUS;
import com.bookstore.DTO.NCCDTO;
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
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
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
        }

        tableModel = new DefaultTableModel(data, columns);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
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
