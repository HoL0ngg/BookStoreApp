package com.bookstore.views.Panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import java.text.SimpleDateFormat;
import mdlaf.MaterialLookAndFeel;
import com.bookstore.DTO.PhieuNhapDTO;
import com.bookstore.controller.PhieuNhapController;
import com.bookstore.dao.PhieuNhapDAO;

public class PhieuNhap extends JPanel {
    
    private List<PhieuNhapDTO> listpn = new PhieuNhapDAO().layDanhSachPhieuNhap();
    private JPanel search;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaPhieuNhap, txtThoigian, txtMaNV, txtMaNCC;
    private JComboBox<String> cbLuaChonTK;
    private JComboBox<String> cbSapXep;
    private JTextField txtTimKiem;
    private PhieuNhapController phieuNhapController;
    private JButton btnReverse, btnThem, btnSua, btnXoa, btnTimKiem;
    public FlatSVGIcon upIcon = new FlatSVGIcon(getClass().getResource("/svg/arrow_up.svg")).derive(25, 25);
    public FlatSVGIcon downIcon = new FlatSVGIcon(getClass().getResource("/svg/arrow_down.svg")).derive(25, 25);
    
    // Khởi tạo
    public PhieuNhap() {
        phieuNhapController = new PhieuNhapController(this);
        init();
    }
    
    public void init() {
        // Cấu hình Look and Feel (bỏ trong ứng dụng thực tế nếu đã có trong MainFrame)
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
            "Mã phiếu nhập",
            "Mã nhân viên",
            "Mã nhà cung cấp"
        };

        String[] LuaChonSapXep = {
            "Tất cả",
            "Mã phiếu nhập",
            "Thời gian",
            "Mã nhân viên",
            "Mã nhà cung cấp"
        };

        // Phần tìm kiếm
        cbLuaChonTK = new JComboBox<>(LuaChonTimKiem);
        txtTimKiem = new JTextField();
        JLabel lbTimKiem = new JLabel("Tìm kiếm theo ");
        btnTimKiem = new JButton("Tìm");
        lbTimKiem.setBounds(20, 10, 100, 30);
        cbLuaChonTK.setBounds(120, 10, 150, 30);
        txtTimKiem.setBounds(280, 10, 150, 30);
        btnTimKiem.setBounds(440, 10, 80, 30);

        // Phần sắp xếp
        cbSapXep = new JComboBox<>(LuaChonSapXep);
        JLabel lbSapXep = new JLabel("Sắp xếp theo ");
        lbSapXep.setBounds(590, 10, 100, 30);
        cbSapXep.setBounds(690, 10, 150, 30);

        btnTimKiem.addActionListener(phieuNhapController);
        cbSapXep.addItemListener(phieuNhapController);

        // Button đổi chiều
        btnReverse = new JButton(upIcon);
        btnReverse.setBounds(850, 10, 30, 30);
        btnReverse.addActionListener(phieuNhapController);

        // Thêm các thành phần vào panel tìm kiếm
        search.add(lbTimKiem);
        search.add(cbLuaChonTK);
        search.add(txtTimKiem);
        search.add(btnTimKiem);
        search.add(lbSapXep);
        search.add(cbSapXep);
        search.add(btnReverse);

        // Panel chứa bảng dữ liệu
        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[] {
            "Mã phiếu nhập", "Thời gian", "Mã nhân viên", "Mã nhà cung cấp", "Thao tác"
        }, 0);
        
        // Tạo bảng và sự kiện chi tiết
        table = new JTable(tableModel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                if (col == 4 && row >= 0) {
                    String maPhieuNhap = (String) table.getValueAt(row, 0);
                    phieuNhapController.hienThiChiTietTable(maPhieuNhap);
                }   
            }
        });

        // Thanh cuộn
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);  

        // Panel chứa các nút thêm, sửa, xóa
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topButtonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        btnThem = new JButton("Thêm");
        btnThem.setPreferredSize(new Dimension(100, 30));

        btnSua = new JButton("Sửa");
        btnSua.setPreferredSize(new Dimension(100, 30));

        btnXoa = new JButton("Xóa");
        btnXoa.setPreferredSize(new Dimension(100, 30));

        // Thêm các nút vào panel
        topButtonPanel.add(btnThem);
        topButtonPanel.add(btnSua);
        topButtonPanel.add(btnXoa);

        btnThem.addActionListener(phieuNhapController);
        btnSua.addActionListener(phieuNhapController);
        btnXoa.addActionListener(phieuNhapController);

        // Panel chứa thanh tìm kiếm và các nút chức năng
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(search, BorderLayout.NORTH);
        headerPanel.add(topButtonPanel, BorderLayout.SOUTH);

        // Thêm header và bảng vào panel chính
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(table), BorderLayout.CENTER);

        // Load dữ liệu vào bảng
        loadTableData();
    }
    
    // Hàm tải dữ liệu lên bảng
    public void loadTableData() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<PhieuNhapDTO> tmpList = new PhieuNhapDAO().layDanhSachPhieuNhap();
        setListpn(tmpList);
        for (PhieuNhapDTO pn : tmpList) {
            tableModel.addRow(new Object[] {
                pn.getMaPhieuNhap(),
                sdf.format(pn.getThoigian()),
                pn.getMaNV(),
                pn.getMaNCC(),
                ""
            });
        }
    }

    // Hàm cập nhật bảng
    public void updateTable(List<PhieuNhapDTO> danhSach) {
        tableModel.setRowCount(0); 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
        for (PhieuNhapDTO pn : danhSach) {
            tableModel.addRow(new Object[] {
                pn.getMaPhieuNhap(),
                dateFormat.format(pn.getThoigian()),
                pn.getMaNV(),
                pn.getMaNCC(),
                ""
            });
        }
    }

    // Getter và Setter
    public List<PhieuNhapDTO> getListpn() {
        return listpn;
    }

    public void setListpn(List<PhieuNhapDTO> listpn) {
        this.listpn = listpn;
    }

    public JTable getTable() {
        return table;
    }

    public JTextField getTxtMaPhieuNhap() {
        return txtMaPhieuNhap;
    }

    public JTextField getTxtThoigian() {
        return txtThoigian;
    }

    public JTextField getTxtMaNV() {
        return txtMaNV;
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

    public JButton getBtnTimKiem() {
        return btnTimKiem;
    }

    public void setBtnTimKiem(JButton btnTimKiem) {
        this.btnTimKiem = btnTimKiem;
    }

    public JTextField getTxtTimKiem() {
        return txtTimKiem;
    }

    public void setTxtTimKiem(JTextField txtTimKiem) {
        this.txtTimKiem = txtTimKiem;
    }

    public JTextComponent getTxtMaNCC() {
        return txtMaNCC;
    }

}