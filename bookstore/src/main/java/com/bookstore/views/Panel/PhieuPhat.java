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

import com.bookstore.BUS.PhieuPhatBUS;
import com.bookstore.DTO.PhieuPhatDTO;
import com.bookstore.controller.PhieuPhatController;

public class PhieuPhat extends JPanel {

    private List<PhieuPhatDTO> listpp = new PhieuPhatBUS().getList();
    private JPanel search;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaPhieuPhat, txtTienPhat, txtNgayPhat, txtTrangThai, txtMaDocGia, txtMaCTPhieuTra;
    private JComboBox<String> cbLuaChonTK;
    private JComboBox<String> cbSapXep;
    private JTextField txtTimKiem;
    private PhieuPhatController phieuPhatController;
    private JButton btnReverse, btnSua, btnXoa;
    public FlatSVGIcon upIcon = new FlatSVGIcon(getClass().getResource("/svg/arrow_up.svg")).derive(25, 25);
    public FlatSVGIcon downIcon = new FlatSVGIcon(getClass().getResource("/svg/arrow_down.svg")).derive(25, 25);
    private Timer searchTimer;

    // Constructor
    public PhieuPhat() {
        phieuPhatController = new PhieuPhatController(this);
        init();
    }

    public void init() {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setLayout(new BorderLayout());

        // Search and sort panel
        search = new JPanel();
        search.setPreferredSize(new Dimension(900, 50));
        search.setLayout(null);

        String[] LuaChonTimKiem = {
                "Mã phiếu phạt",
                "Mã độc giả",
                "Mã chi tiết phiếu trả"
        };

        String[] LuaChonSapXep = {
                "Mã phiếu phạt",
                "Tiền phạt",
                "Ngày phạt",
                "Trạng thái",
                "Mã độc giả",
                "Mã chi tiết phiếu trả"
        };

        // Search section
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
                        SwingUtilities.invokeLater(() -> phieuPhatController.performSearch());
                    }
                }, 300);
            }
        });

        // Sort section
        cbSapXep = new JComboBox<>(LuaChonSapXep);
        JLabel lbSapXep = new JLabel("Sắp xếp theo ");
        lbSapXep.setBounds(590, 10, 100, 30);
        cbSapXep.setBounds(690, 10, 150, 30);

        cbSapXep.addItemListener(phieuPhatController);

        // Reverse button
        btnReverse = new JButton(upIcon);
        btnReverse.setBounds(850, 10, 30, 30);
        btnReverse.addActionListener(phieuPhatController);

        // Add components to search panel
        search.add(lbtimkiem);
        search.add(cbLuaChonTK);
        search.add(txtTimKiem);
        search.add(lbSapXep);
        search.add(cbSapXep);
        search.add(btnReverse);

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[] {
                "Mã phiếu phạt", "Tiền phạt", "Ngày phạt", "Trạng thái", "Mã độc giả", "Mã chi tiết phiếu trả",
                "Thao tác"
        }, 0);

        // Create table and handle click events
        table = new JTable(tableModel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                if (col == 6 && row >= 0) {
                    int maPhieuPhat = (int) table.getValueAt(row, 0);
                    phieuPhatController.hienThiChiTietTable(maPhieuPhat);
                }
            }
        });

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel (edit and delete only)
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topButtonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        btnSua = new JButton("Sửa");
        btnSua.setPreferredSize(new Dimension(100, 30));

        btnXoa = new JButton("Xóa");
        btnXoa.setPreferredSize(new Dimension(100, 30));

        topButtonPanel.add(btnSua);
        topButtonPanel.add(btnXoa);

        btnSua.addActionListener(phieuPhatController);
        btnXoa.addActionListener(phieuPhatController);

        // Header panel (search + buttons)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(search, BorderLayout.NORTH);
        headerPanel.add(topButtonPanel, BorderLayout.SOUTH);

        // Add header and table to main panel
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(table), BorderLayout.CENTER);

        // Load table data
        loadTableData();
    }

    // Load data into table
    public void loadTableData() {
        System.out.println("aaaaa");
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<PhieuPhatDTO> tmplist = new PhieuPhatBUS().getList();
        System.out.println(tmplist.size());
        setListpp(tmplist);
        for (PhieuPhatDTO pp : tmplist) {
            if (pp.getStatus() == 1) {
                tableModel.addRow(new Object[] {
                        pp.getMaPhieuPhat(),
                        pp.getTienPhat(),
                        sdf.format(pp.getNgayPhat()),
                        pp.getTrangThai() == 1 ? "Đã hoàn thành" : "Chưa hoàn thành",
                        pp.getMaDocGia(),
                        pp.getMaCTPhieuTra(),
                        "Chi tiết"
                });
            }
        }
    }

    // Update table with provided list
    public void updateTable(List<PhieuPhatDTO> danhsach) {
        tableModel.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (PhieuPhatDTO pp : danhsach) {
            if (pp.getStatus() == 1) {
                tableModel.addRow(new Object[] {
                        pp.getMaPhieuPhat(),
                        pp.getTienPhat(),
                        dateFormat.format(pp.getNgayPhat()),
                        pp.getTrangThai() == 1 ? "Đã hoàn thành" : "Chưa hoàn thành",
                        pp.getMaDocGia(),
                        pp.getMaCTPhieuTra(),
                        "Chi tiết"
                });
            }
        }
    }

    // Getters and Setters
    public List<PhieuPhatDTO> getListpp() {
        return listpp;
    }

    public void setListpp(List<PhieuPhatDTO> listpp) {
        this.listpp = listpp;
    }

    public JTable getTable() {
        return table;
    }

    public JTextField getTxtMaPhieuPhat() {
        return txtMaPhieuPhat;
    }

    public JTextField getTxtTienPhat() {
        return txtTienPhat;
    }

    public JTextField getTxtNgayPhat() {
        return txtNgayPhat;
    }

    public JTextField getTxtTrangThai() {
        return txtTrangThai;
    }

    public JTextField getTxtMaDocGia() {
        return txtMaDocGia;
    }

    public JTextField getTxtMaCTPhieuTra() {
        return txtMaCTPhieuTra;
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

    public JButton getBtnSua() {
        return btnSua;
    }

    public JButton getBtnXoa() {
        return btnXoa;
    }
}