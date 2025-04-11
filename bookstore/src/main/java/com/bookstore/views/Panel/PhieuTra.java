package com.bookstore.views.Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import com.bookstore.DTO.PhieuTraDTO;
import com.bookstore.controller.PhieuTraController;
import com.bookstore.dao.PhieuTraDAO;

public class PhieuTra extends JPanel {
    
    private List<PhieuTraDTO> listpt = new PhieuTraDAO().layDanhSachPhieuTra();
    private JPanel search;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaPhieuTra, txtNgayTra, txtMaNV, txtMaDocGia, txtMaPhieuMuon;
    private JButton btnTimKiem = new JButton("Tìm");
    private JComboBox<String> cbLuaChonTK;
    private JComboBox<String> cbSapXep;
    private JTextField txtTimKiem;
    private PhieuTraController phieuTraController;
    private JButton sxtang, sxgiam, btnThem, btnSua, btnXoa;

    public PhieuTra(){
        System.out.println("da nhan vao phieu tra");
        phieuTraController = new PhieuTraController(this);
        init();
    }

    
    
    public void init() {
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());

        // Panel chứa khung nhập và nút tìm kiếm
        search = new JPanel(new FlowLayout(FlowLayout.LEFT));
        search.setBackground(Color.WHITE);

        String[] LuaChonTimKiem = {
            "Mã phiếu trả",
            "Mã nhân viên",
            "Mã đọc giả",
            "Mã phiếu mượn"
        };

        String[] LuaChonSapXep = {
            "Mã phiếu trả",
            "Ngày trả",
            "Mã nhân viên",
            "Mã đọc giả",
            "Mã phiếu mượn"
        };

        cbLuaChonTK = new JComboBox<>(LuaChonTimKiem);
        txtTimKiem = new JTextField(15);
        JLabel labeltk = new JLabel("Tìm kiếm theo ");
        search.add(labeltk);
        search.add(cbLuaChonTK);
        search.add(txtTimKiem);
        search.add(btnTimKiem);

        search.add(Box.createHorizontalStrut(100));

        cbSapXep = new JComboBox<>(LuaChonSapXep);
        JLabel labelsapxep = new JLabel("Sắp xếp theo ");
        search.add(labelsapxep);
        search.add(cbSapXep);

        btnTimKiem.addActionListener(phieuTraController);
        cbSapXep.addItemListener(phieuTraController);

        sxtang = new JButton("^");
        sxgiam = new JButton("v");

        sxtang.setPreferredSize(new Dimension(50, 30));
        sxgiam.setPreferredSize(new Dimension(50,30));

        sxtang.addActionListener(phieuTraController);
        sxgiam.addActionListener(phieuTraController);

        search.add(sxtang);
        search.add(sxgiam);



        this.add(search, BorderLayout.NORTH);

        // Panel hiển thị thông tin phiếu trả (bên phải)
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setPreferredSize(new Dimension(100, 130));

        txtMaPhieuTra = new JTextField(16);
        txtNgayTra = new JTextField(16);
        txtMaNV = new JTextField(16);
        txtMaDocGia = new JTextField(16);
        txtMaPhieuMuon = new JTextField(16);

        infoPanel.add(new JLabel("Mã phiếu trả:"));
        infoPanel.add(Box.createHorizontalStrut(25));
        infoPanel.add(txtMaPhieuTra);
        txtMaPhieuTra.setEditable(false);

        infoPanel.add(new JLabel("Ngày trả:"));
        infoPanel.add(Box.createHorizontalStrut(50));
        infoPanel.add(txtNgayTra);
        txtNgayTra.setEditable(true);

        infoPanel.add(new JLabel("Mã nhân viên:"));
        infoPanel.add(Box.createHorizontalStrut(22));
        infoPanel.add(txtMaNV);
        txtMaNV.setEditable(false);

        infoPanel.add(new JLabel("Mã đọc giả:"));
        infoPanel.add(Box.createHorizontalStrut(35));
        infoPanel.add(txtMaDocGia);
        txtMaDocGia.setEditable(false);

        infoPanel.add(new JLabel("Mã phiếu mượn:"));
        infoPanel.add(Box.createHorizontalStrut(7));
        infoPanel.add(txtMaPhieuMuon);
        txtMaPhieuMuon.setEditable(false);

        // Panel chứa bảng (bên trái)
        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[] {
            "Mã phiếu trả", "Ngày trả", "Mã nhân viên", "Mã độc giả", "Mã phiếu mượn"
        }, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Sử dụng JSplitPane để chia không gian giữa bảng và thông tin
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tablePanel, infoPanel);
        splitPane.setDividerLocation(0.7);
        splitPane.setResizeWeight(0.7);
        splitPane.setOneTouchExpandable(true);

        tablePanel.setMinimumSize(new Dimension(400, 0));
        infoPanel.setMinimumSize(new Dimension(300, 0));

        this.add(splitPane, BorderLayout.CENTER);

        // btn thêm sửa xóa
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        topButtonPanel.setBackground(Color.WHITE);

        btnThem = new JButton("Thêm");
        btnThem.setPreferredSize(new Dimension(100, 30));
        topButtonPanel.add(btnThem);
        btnThem.addActionListener(phieuTraController);

        // Panel chứa nút Sửa/Xóa bên phải infoPanel
        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.setLayout(new FlowLayout(0,5,5));
        rightButtonPanel.setBackground(Color.WHITE);
        rightButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Thêm padding

        btnSua = new JButton("Sửa");
        btnSua.setPreferredSize(new Dimension(100, 35));
        btnSua.setMaximumSize(new Dimension(100, 35));
        btnSua.addActionListener(phieuTraController);

        btnXoa = new JButton("Xóa");
        btnXoa.setPreferredSize(new Dimension(100, 35));
        btnXoa.setMaximumSize(new Dimension(100, 35));
        btnXoa.addActionListener(phieuTraController);

        rightButtonPanel.add(btnSua);
        rightButtonPanel.add(Box.createVerticalStrut(10)); // Khoảng cách giữa 2 nút
        rightButtonPanel.add(btnXoa);

        // Thêm các panel vào layout chính
        tablePanel.add(topButtonPanel, BorderLayout.NORTH);
        infoPanel.add(rightButtonPanel, BorderLayout.EAST);
        // Load dữ liệu vào bảng
        loadTableData();

        // Bắt sự kiện khi click dòng trong bảng
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtMaPhieuTra.setText(tableModel.getValueAt(row, 0).toString());
                    txtNgayTra.setText(tableModel.getValueAt(row, 1).toString());
                    txtMaNV.setText(tableModel.getValueAt(row, 2).toString());
                    txtMaDocGia.setText(tableModel.getValueAt(row, 3).toString());
                    txtMaPhieuMuon.setText(tableModel.getValueAt(row, 4).toString());
                }
            }
        });
    }
    
    public void loadTableData() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<PhieuTraDTO> tmplist = new PhieuTraDAO().layDanhSachPhieuTra();
        setListpt(tmplist);
        for (PhieuTraDTO pt : tmplist) {
            tableModel.addRow(new Object[] {
                pt.getMaPhieuTra(),
                sdf.format(pt.getNgayTra()),
                pt.getMaNV(),
                pt.getMaDocGia(),
                pt.getMaPhieuMuon()
            });
        }
    }

    public void updateTable(List<PhieuTraDTO> danhsach) {
        tableModel.setRowCount(0); 
    
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
        for (PhieuTraDTO pt : danhsach) {
            tableModel.addRow(new Object[] {
                pt.getMaPhieuTra(),
                dateFormat.format(pt.getNgayTra()),
                pt.getMaNV(),
                pt.getMaDocGia(),
                pt.getMaPhieuMuon()
            });
        }
    }



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



    public JButton getBtnTimKiem() {
        return btnTimKiem;
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



    public JButton getSxtang() {
        return sxtang;
    }



    public JButton getSxgiam() {
        return sxgiam;
    }



    public JButton getBtnThem() {
        return btnThem;
    }



    public JButton getBtnSua() {
        return btnSua;
    }



    public JButton getBtnXoa() {
        return btnXoa;
    }
}    