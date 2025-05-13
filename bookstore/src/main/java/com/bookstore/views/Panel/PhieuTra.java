package com.bookstore.views.Panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;

import mdlaf.MaterialLookAndFeel;

import com.bookstore.DTO.PhieuTraDTO;
import com.bookstore.controller.PhieuTraController;
import com.bookstore.dao.PhieuTraDAO;

public class PhieuTra extends JPanel {

    private List<PhieuTraDTO> listpt = new PhieuTraDAO().layDanhSachPhieuTra();
    private JPanel search;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaPhieuTra, txtNgayTra, txtMaNV, txtMaDocGia, txtMaPhieuMuon;
    private JComboBox<String> cbLuaChonTK;
    private JComboBox<String> cbSapXep;
    private JTextField txtTimKiem;
    private PhieuTraController phieuTraController;
    private JButton btnReverse, btnThem, btnSua, btnXoa, btnXuatExcel;
    public FlatSVGIcon upIcon = new FlatSVGIcon(getClass().getResource("/svg/arrow_up.svg")).derive(25, 25);
    public FlatSVGIcon downIcon = new FlatSVGIcon(getClass().getResource("/svg/arrow_down.svg")).derive(25, 25);
    private Timer searchTimer;

    // khởi tạo
    public PhieuTra() {
        phieuTraController = new PhieuTraController(this);
        init();
    }

    public void init() {

        // sau khi hoàn chỉnh -> bỏ -> trong mainFrame đã tồn tại
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setLayout(new BorderLayout());

        // Panel tìm kiếm và sắp xếp
        search = new JPanel();
        search.setPreferredSize(new Dimension(900, 50));
        search.setLayout(null);

        String[] LuaChonTimKiem = {
                "Mã phiếu trả",
                "Mã nhân viên",
                "Mã độc giả",
                "Mã phiếu mượn"
        };

        String[] LuaChonSapXep = {
                "Mã phiếu trả",
                "Ngày trả",
                "Mã nhân viên",
                "Mã độc giả",
                "Mã phiếu mượn"
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
                        SwingUtilities.invokeLater(() -> phieuTraController.performSearch());
                    }
                }, 300);
            }
        });

        // phần sắp xếp
        cbSapXep = new JComboBox<>(LuaChonSapXep);
        JLabel lbSapXep = new JLabel("Sắp xếp theo ");
        lbSapXep.setBounds(590, 10, 100, 30);
        cbSapXep.setBounds(690, 10, 150, 30);

        cbSapXep.addItemListener(phieuTraController);

        // button đổi chiều
        btnReverse = new JButton(upIcon);
        btnReverse.setBounds(850, 10, 30, 30);
        btnReverse.addActionListener(phieuTraController);

        // add ...
        search.add(lbtimkiem);
        search.add(cbLuaChonTK);
        search.add(txtTimKiem);

        search.add(lbSapXep);
        search.add(cbSapXep);

        search.add(btnReverse);

        // Panel chứa dữ liệu
        // thông tin + thao tác hiển thị chi tiết phiếu trả -> xuất các chi tiết phiếu
        // trả
        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[] {
                "Mã phiếu trả", "Ngày trả", "Mã nhân viên", "Mã độc giả", "Mã phiếu mượn", "Thao tác"
        }, 0);

        // tạo table và sự kiện chi tiết
        table = new JTable(tableModel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                if (col == 5 && row >= 0) {
                    int maPhieuTra = (int) table.getValueAt(row, 0);
                    phieuTraController.hienThiChiTietTable(maPhieuTra);
                }
            }
        });

        // thanh scroll
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // panel chứa thêm sửa xóa,... có thể thêm ...
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topButtonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        btnThem = new JButton("Thêm");
        btnThem.setPreferredSize(new Dimension(100, 30));

        btnSua = new JButton("Sửa");
        btnSua.setPreferredSize(new Dimension(100, 30));

        btnXoa = new JButton("Xóa");
        btnXoa.setPreferredSize(new Dimension(100, 30));

        btnXuatExcel = new JButton("Xuất Excel");
        btnXuatExcel.setPreferredSize(new Dimension(100, 30));

        // add .
        topButtonPanel.add(btnThem);
        topButtonPanel.add(btnSua);
        topButtonPanel.add(btnXoa);
        topButtonPanel.add(btnXuatExcel);

        btnThem.addActionListener(phieuTraController);
        btnSua.addActionListener(phieuTraController);
        btnXoa.addActionListener(phieuTraController);
        btnXuatExcel.addActionListener(phieuTraController);

        // panel chứa thanh tìm kiếm và các button chức năng sử dụng borderlayout
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(search, BorderLayout.NORTH);
        headerPanel.add(topButtonPanel, BorderLayout.SOUTH);

        // add thanh header + bảng
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(table), BorderLayout.CENTER);

        // Load dữ liệu vào bảng
        loadTableData();
    }

    // hàm tải dữ liệu lên bảng, dùng chung cho thêm sửa xóa ở PhieuTraController
    public void loadTableData() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<PhieuTraDTO> tmplist = new PhieuTraDAO().layDanhSachPhieuTra();
        setListpt(tmplist);
        for (PhieuTraDTO pt : tmplist) {
            if (pt.getStatus() == 1) {
                tableModel.addRow(new Object[] {
                        pt.getMaPhieuTra(),
                        sdf.format(pt.getNgayTra()),
                        pt.getMaNV(),
                        pt.getMaDocGia(),
                        pt.getMaPhieuMuon(),
                        "Chi tiết"
                });
            }
        }
    }

    // hàm cập nhật bảng dùng chung cho PhieuTraController
    public void updateTable(List<PhieuTraDTO> danhsach) {
        tableModel.setRowCount(0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (PhieuTraDTO pt : danhsach) {
            tableModel.addRow(new Object[] {
                    pt.getMaPhieuTra(),
                    dateFormat.format(pt.getNgayTra()),
                    pt.getMaNV(),
                    pt.getMaDocGia(),
                    pt.getMaPhieuMuon(),
                    "Chi tiết"
            });
        }
    }

    // Getter Setter
    public List<PhieuTraDTO> getListpt() {
        return listpt;
    }

    public void setListpt(List<PhieuTraDTO> listpt) {
        this.listpt = listpt;
    }

    public JTable getTable() {
        return table;
    }

    public JTextField getTxtMaPhieuTra() {
        return txtMaPhieuTra;
    }

    public JTextField getTxtNgayTra() {
        return txtNgayTra;
    }

    public JTextField getTxtMaNV() {
        return txtMaNV;
    }

    public JTextField getTxtMaDocGia() {
        return txtMaDocGia;
    }

    public JTextField getTxtMaPhieuMuon() {
        return txtMaPhieuMuon;
    }

    public JComboBox<String> getCbLuaChonTK() {
        return cbLuaChonTK;
    }

    public JComboBox<String> getCbSapXep() {
        return cbSapXep;
    }

    public JTextField getTxtTimKiem() {
        return txtTimKiem;
    }

    public JButton getBtnReverse() {
        return btnReverse;
    }

    public JButton getBtnThem() {
        return btnThem;
    }

    public JButton getBtnXuatExcel() {
        return btnXuatExcel;
    }

    public JButton getBtnSua() {
        return btnSua;
    }

    public JButton getBtnXoa() {
        return btnXoa;
    }
}