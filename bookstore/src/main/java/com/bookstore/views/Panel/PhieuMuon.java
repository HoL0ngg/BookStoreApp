package com.bookstore.views.Panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import java.text.SimpleDateFormat;

import com.bookstore.DTO.PhieuMuonDTO;
import com.bookstore.controller.PhieuMuonController;
import com.bookstore.dao.PhieuMuonDAO;

public class PhieuMuon extends JPanel {

    private List<PhieuMuonDTO> listpm = new PhieuMuonDAO().layDanhSachPhieuMuon();
    private JPanel search;
    private JTable table;
    private DefaultTableModel tableModel;
    @SuppressWarnings("unused")
    private JTextField txtTimKiem, txtMaPhieuTra, txtNgayTra, txtMaNV, txtMaDocGia, txtMaPhieuMuon;
    private JComboBox<String> cbLuaChonTK;
    private JComboBox<String> cbSapXep;
    private PhieuMuonController phieumuoncontroller;
    private JButton btnReverse, btnThem, btnSua, btnXoa;
    public FlatSVGIcon upIcon = new FlatSVGIcon(getClass().getResource("/svg/arrow_up.svg")).derive(25, 25);
    public FlatSVGIcon downIcon = new FlatSVGIcon(getClass().getResource("/svg/arrow_down.svg")).derive(25, 25);
    private Timer searchTimer;

    public PhieuMuon() {
        phieumuoncontroller = new PhieuMuonController(this);
        init();
    }

    @SuppressWarnings("unused")
    public void init() {
        this.setLayout(new BorderLayout());

        // panel tìm kiếm và sắp xếp
        search = new JPanel();
        search.setPreferredSize(new Dimension(900, 50));

        // setBound cho thành phần
        search.setLayout(null);

        String[] LuaChonTimKiem = {
                "Mã phiếu mượn",
                "Ngày mượn",
                "Ngày trả dự kiến",
                "Trạng thái",
                "Mã độc giả",
                "Mã nhân viên"
        };

        String[] LuaChonSapXep = {
                "Mã phiếu mượn",
                "Ngày mượn",
                "Ngày trả dự kiến",
                "Trạng thái",
                "Mã độc giả",
                "Mã nhân viên"
        };

        // phần tìm kiếm
        cbLuaChonTK = new JComboBox<>(LuaChonTimKiem);
        txtTimKiem = new JTextField();
        JLabel lbtimkiem = new JLabel("Tìm kiếm theo ");
        lbtimkiem.setBounds(20, 10, 100, 30);
        cbLuaChonTK.setBounds(120, 10, 150, 30);
        txtTimKiem.setBounds(280, 10, 150, 30);

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
                        SwingUtilities.invokeLater(() -> phieumuoncontroller.performSearch());
                    }
                }, 300);
            }
        });

        // phần sắp xếp
        cbSapXep = new JComboBox<>(LuaChonSapXep);
        JLabel lbSapXep = new JLabel("Sắp xếp theo ");
        lbSapXep.setBounds(590, 10, 100, 30);
        cbSapXep.setBounds(690, 10, 150, 30);

        cbSapXep.addItemListener(phieumuoncontroller);

        // button đổi chiều
        btnReverse = new JButton(upIcon);
        btnReverse.setBounds(850, 10, 30, 30);
        btnReverse.addActionListener(phieumuoncontroller);

        // add ...
        search.add(lbtimkiem);
        search.add(cbLuaChonTK);
        search.add(txtTimKiem);

        search.add(lbSapXep);
        search.add(cbSapXep);

        search.add(btnReverse);

        // panel chính chứa dữ liệu, table

        // panel chức năng
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topButtonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        btnThem = new JButton("Thêm");
        btnThem.setPreferredSize(new Dimension(100, 30));

        btnSua = new JButton("Sửa");
        btnSua.setPreferredSize(new Dimension(100, 30));

        btnXoa = new JButton("Xóa");
        btnXoa.setPreferredSize(new Dimension(100, 30));

        // add .
        topButtonPanel.add(btnThem);
        topButtonPanel.add(btnSua);
        topButtonPanel.add(btnXoa);

        btnThem.addActionListener(phieumuoncontroller);
        btnSua.addActionListener(phieumuoncontroller);
        btnXoa.addActionListener(phieumuoncontroller);

        // panel chứa thanh tìm kiếm và các button chức năng sử dụng borderlayout
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(search, BorderLayout.NORTH);
        headerPanel.add(topButtonPanel, BorderLayout.SOUTH);

        // panel chứa bảng
        JPanel tablePanel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[] {
                "Mã phiếu mượn", "Ngày mượn", "Ngày trả dự kiến", "Trạng thái", "Mã độc giả", "Mã nhân viên", "Thao tác"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Jtable
        table = new JTable(tableModel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                if (col == 6 && row >= 0) {
                    int maPhieuMuon = (int) table.getValueAt(row, 0);
                    phieumuoncontroller.hienThiChiTietTable(String.valueOf(maPhieuMuon));
                }
            }
        });

        // // thanh scroll
        // JScrollPane scrollPane = new JScrollPane(table);
        // tablePanel.add(scrollPane, BorderLayout.CENTER);

        // add thanh header + bảng
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(table), BorderLayout.CENTER);

        // tải dữ liệu vào bảng
        loadTableData();

    }

    public void loadTableData() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<PhieuMuonDTO> tmplist = new PhieuMuonDAO().layDanhSachPhieuMuon();
        setListpm(tmplist);
        for (PhieuMuonDTO pm : tmplist) {
            if (pm.getStatus()) {
                tableModel.addRow(new Object[] {
                        pm.getMaPhieuMuon(),
                        sdf.format(pm.getNgayMuon()),
                        sdf.format(pm.getNgayTraDuKien()),
                        pm.getTrangThai() == 1 ? "Đã hoàn thành" : "Chưa hoàn thành",
                        pm.getMaDocGia(),
                        pm.getMaNhanVien(),
                        "Chi tiết"
                });
            }
        }
    }

    public void updateTable(List<PhieuMuonDTO> danhsach) {
        tableModel.setRowCount(0);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (PhieuMuonDTO pm : danhsach) {
            tableModel.addRow(new Object[] {
                    pm.getMaPhieuMuon(),
                    sdf.format(pm.getNgayMuon()),
                    sdf.format(pm.getNgayTraDuKien()),
                    pm.getTrangThai() == 1 ? "Đã hoàn thành" : "Chưa hoàn thành",
                    pm.getMaDocGia(),
                    pm.getMaNhanVien(),
                    "Chi tiết"
            });
        }
    }

    public List<PhieuMuonDTO> getListpm() {
        return listpm;
    }

    public void setListpm(List<PhieuMuonDTO> listpm) {
        this.listpm = listpm;
    }

    public JPanel getSearch() {
        return search;
    }

    public void setSearch(JPanel search) {
        this.search = search;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public JTextField getTxtMaPhieuTra() {
        return txtMaPhieuTra;
    }

    public void setTxtMaPhieuTra(JTextField txtMaPhieuTra) {
        this.txtMaPhieuTra = txtMaPhieuTra;
    }

    public JTextField getTxtNgayTra() {
        return txtNgayTra;
    }

    public void setTxtNgayTra(JTextField txtNgayTra) {
        this.txtNgayTra = txtNgayTra;
    }

    public JTextField getTxtMaNV() {
        return txtMaNV;
    }

    public void setTxtMaNV(JTextField txtMaNV) {
        this.txtMaNV = txtMaNV;
    }

    public JTextField getTxtMaDocGia() {
        return txtMaDocGia;
    }

    public void setTxtMaDocGia(JTextField txtMaDocGia) {
        this.txtMaDocGia = txtMaDocGia;
    }

    public JComboBox<String> getCbLuaChonTK() {
        return cbLuaChonTK;
    }

    public void setCbLuaChonTK(JComboBox<String> cbLuaChonTK) {
        this.cbLuaChonTK = cbLuaChonTK;
    }

    public JComboBox<String> getCbSapXep() {
        return cbSapXep;
    }

    public void setCbSapXep(JComboBox<String> cbSapXep) {
        this.cbSapXep = cbSapXep;
    }

    public JButton getBtnReverse() {
        return btnReverse;
    }

    public void setBtnReverse(JButton btnReverse) {
        this.btnReverse = btnReverse;
    }

    public JButton getBtnThem() {
        return btnThem;
    }

    public void setBtnThem(JButton btnThem) {
        this.btnThem = btnThem;
    }

    public JButton getBtnSua() {
        return btnSua;
    }

    public void setBtnSua(JButton btnSua) {
        this.btnSua = btnSua;
    }

    public JButton getBtnXoa() {
        return btnXoa;
    }

    public void setBtnXoa(JButton btnXoa) {
        this.btnXoa = btnXoa;
    }

    public JTextField getTxtTimKiem() {
        return txtTimKiem;
    }

    public void setTxtTimKiem(JTextField txtTimKiem) {
        this.txtTimKiem = txtTimKiem;
    }
}
