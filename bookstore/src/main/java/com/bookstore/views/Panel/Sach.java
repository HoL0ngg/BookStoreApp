package com.bookstore.views.Panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseEvent;

import com.bookstore.BUS.DauSachBUS;
import com.bookstore.DTO.DauSachDTO;
import com.bookstore.DTO.SachDTO;
import com.bookstore.DTO.TheLoaiDTO;
import com.bookstore.controller.SachController;
import com.bookstore.dao.DauSachDAO;
import com.bookstore.dao.SachDAO;
import com.bookstore.views.Component.RoundedPanel;
import com.bookstore.views.Dialog.DauSachDialog;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.mysql.cj.x.protobuf.MysqlxNotice.Frame;

public class Sach extends JPanel {
    private SachController sachController = new SachController(this);
    private DauSachBUS dauSachBUS = new DauSachBUS();
    private JPanel DSDauSach;
    private CardLayout cardLayout;
    private JLabel back;
    private JTable table;
    private DefaultTableModel tableModel;
    private JPanel mainPanel;
    private JTextField MaDauSachTextfield;
    private JTextField TuaDeTextfield;
    private JLabel HinhAnhDauSach;
    private JComboBox<String> TrangthaiDauSachComboBox;
    private JComboBox<String> SortSachCbx;
    private JTextField NgayNhapSachTextfield;
    private JPanel DSDauSachPanel;
    private JLabel TongDauSachLabel;
    private JLabel TongSachLabel;
    private String currentCard = "DanhSach";
    private List<DauSachDTO> listDauSach;
    private List<SachDTO> listSach;
    private JTextField searchTextField;
    private JComboBox<String> SearchCbx;
    private JButton TimKiemButton;

    public void initComponent() {
        this.setBackground(Color.white);
        this.setLayout(new FlowLayout(1, 0, 0));
        this.setPreferredSize(new Dimension(900, 600));

        JPanel ThongTinDauSachPanel = new JPanel();
        ThongTinDauSachPanel.setBackground(Color.white);
        ThongTinDauSachPanel.setPreferredSize(new Dimension(900, 200));
        ThongTinDauSachPanel.setLayout(new FlowLayout(1, 0, 0));

        HinhAnhDauSach = new JLabel();
        HinhAnhDauSach.setPreferredSize(new Dimension(300, 200));
        HinhAnhDauSach.setOpaque(true);
        HinhAnhDauSach.setBackground(Color.white);
        ThongTinDauSachPanel.add(HinhAnhDauSach);

        JPanel ThongTinChiTiet = new JPanel();
        ThongTinChiTiet.setPreferredSize(new Dimension(600, 200));
        ThongTinChiTiet.setBackground(Color.white);
        ThongTinChiTiet.setLayout(new GridLayout(4, 3, 10, 10));

        JLabel MaTauLabel = new JLabel("Mã sách: ");
        MaTauLabel.setHorizontalAlignment(SwingConstants.CENTER);
        MaDauSachTextfield = new JTextField();
        MaDauSachTextfield.setPreferredSize(new Dimension(200, 30));
        MaDauSachTextfield.setEnabled(false);

        JLabel SoGheLabel = new JLabel("Tựa đề: ");
        SoGheLabel.setHorizontalAlignment(SwingConstants.CENTER);
        TuaDeTextfield = new JTextField();
        TuaDeTextfield.setPreferredSize(new Dimension(200, 30));
        TuaDeTextfield.setEnabled(false);

        JLabel TrangThaiTauLabel = new JLabel("Trạng thái: ");
        TrangThaiTauLabel.setHorizontalAlignment(SwingConstants.CENTER);
        TrangthaiDauSachComboBox = new JComboBox<String>(
                new String[] { "", "Bình thường", "Đã mượn", "Bị hư", "Đã xóa" });
        TrangthaiDauSachComboBox.setPreferredSize(new Dimension(200, 30));
        TrangthaiDauSachComboBox.setEnabled(false);

        JLabel NgayNhapTauLabel = new JLabel("Nhà xuất bản: ");
        NgayNhapTauLabel.setHorizontalAlignment(SwingConstants.CENTER);
        NgayNhapSachTextfield = new JTextField();
        NgayNhapSachTextfield.setPreferredSize(new Dimension(200, 30));
        NgayNhapSachTextfield.setEnabled(false);

        JButton ThemTauButton = new JButton("THÊM");
        ThemTauButton.setIcon(new FlatSVGIcon(getClass().getResource("/svg/add.svg")).derive(20, 20));
        ThemTauButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        ThemTauButton.setIconTextGap(25);
        List<String> dsNxb = Arrays.asList("NXB Kim Đồng", "NXB Giáo Dục", "NXB Trẻ");
        ThemTauButton.addActionListener(e -> {
            new DauSachDialog((java.awt.Frame) SwingUtilities.getWindowAncestor(Sach.this), 10,
                    dsNxb).setVisible(true);
        });
        JButton SuaTauButton = new JButton("SỬA");
        SuaTauButton.setIcon(new FlatSVGIcon(getClass().getResource("/svg/ChiTiet.svg")).derive(20, 20));
        SuaTauButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        SuaTauButton.setIconTextGap(25);
        SuaTauButton.addActionListener(e -> {
            setTextfieldEnable();
        });
        JButton XoaTauButton = new JButton("XÓA");
        XoaTauButton.setIcon(new FlatSVGIcon(getClass().getResource("/svg/delete.svg")).derive(20, 20));
        XoaTauButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        XoaTauButton.setIconTextGap(25);
        XoaTauButton.addActionListener(e -> {
            XoaDauSach();
        });
        JButton LuuButton = new JButton("LƯU");
        LuuButton.setIcon(new FlatSVGIcon(getClass().getResource("/svg/update.svg")).derive(20, 20));
        LuuButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        LuuButton.setIconTextGap(25);
        LuuButton.addActionListener(e -> {
            if (ValidateDuLieu() == true)
                setTextfieldDisable();
        });

        ThongTinChiTiet.add(MaTauLabel);
        ThongTinChiTiet.add(MaDauSachTextfield);
        ThongTinChiTiet.add(ThemTauButton);

        ThongTinChiTiet.add(SoGheLabel);
        ThongTinChiTiet.add(TuaDeTextfield);
        ThongTinChiTiet.add(SuaTauButton);

        ThongTinChiTiet.add(TrangThaiTauLabel);
        ThongTinChiTiet.add(TrangthaiDauSachComboBox);
        ThongTinChiTiet.add(XoaTauButton);

        ThongTinChiTiet.add(NgayNhapTauLabel);
        ThongTinChiTiet.add(NgayNhapSachTextfield);
        ThongTinChiTiet.add(LuuButton);

        ThongTinDauSachPanel.add(ThongTinChiTiet);
        this.add(ThongTinDauSachPanel);

        JPanel TongDauSachPanel = new JPanel();
        TongDauSachPanel.setPreferredSize(new Dimension(900, 100));
        TongDauSachPanel.setBorder(new MatteBorder(0, 0, 2, 0, Color.black));
        TongDauSachPanel.setBackground(Color.white);
        TongDauSachPanel.setLayout(null);
        TongDauSachLabel = new JLabel("Tổng số đầu sách (" + listDauSach.size() + ")");
        TongDauSachLabel.setFont(new Font("Arial", Font.BOLD, 22));
        TongDauSachLabel.setBounds(30, 55, 320, 40);
        TongDauSachPanel.add(TongDauSachLabel);
        JLabel SortDauSachLabel = new JLabel("Sắp xếp theo: ");
        SortDauSachLabel.setFont(new Font("Arial", Font.BOLD, 16));
        SortDauSachLabel.setBounds(650, 10, 200, 40);
        TongDauSachPanel.add(SortDauSachLabel);
        JComboBox<String> SortDauSachCBx = new JComboBox<>();
        SortDauSachCBx.addItem("Mã đầu sách");
        SortDauSachCBx.addItem("Số trang");
        SortDauSachCBx.addItem("Năm xuất bản");
        SortDauSachCBx.setBounds(760, 10, 100, 30);
        SortDauSachCBx.addItemListener(sachController);
        TongDauSachPanel.add(SortDauSachCBx);

        searchTextField = new JTextField();
        searchTextField.setBounds(130, 10, 200, 30);
        searchTextField.setBackground(Color.white);
        searchTextField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        TongDauSachPanel.add(searchTextField);

        SearchCbx = new JComboBox<String>(
                new String[] { "Tất cả", "Mã đầu sách", "Tên đầu sách", "Tác giả",
                        "Nhà xuất bản", "Năm xuất bản" });
        SearchCbx.setBounds(10, 10, 110, 30);
        SearchCbx.setBackground(Color.white);
        SearchCbx.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        SearchCbx.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        TongDauSachPanel.add(SearchCbx);

        TimKiemButton = new JButton("Tìm kiếm");
        TimKiemButton.setBounds(350, 10, 80, 30);
        // TimKiemButton.setBackground(Color.white);
        TimKiemButton.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        TimKiemButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        TimKiemButton.addActionListener(e -> {
            performSearch();
        });
        TongDauSachPanel.add(TimKiemButton);

        DSDauSach = new JPanel();
        DSDauSach.setLayout(new BorderLayout());
        DSDauSach.add(TongDauSachPanel, BorderLayout.NORTH);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        DSDauSachPanel = new JPanel();

        DSDauSachPanel.setLayout(new FlowLayout(0, 36, 40));
        DSDauSachPanel.setPreferredSize(new Dimension(900, ((listDauSach.size() + 5) / 3) * 260));
        DSDauSachPanel.setBackground(Color.white);

        for (int i = 0; i < listDauSach.size(); ++i) {
            RoundedPanel dauSachPanel = CreateDauSachPanel(listDauSach.get(i));
            DSDauSachPanel.add(dauSachPanel);
        }
        JScrollPane DSDauSachScollPane = new JScrollPane(DSDauSachPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        DSDauSachScollPane.setPreferredSize(new Dimension(900, 500));
        DSDauSach.add(DSDauSachScollPane, BorderLayout.CENTER);

        // Panel chứa bảng sách (chỉ có 1 bảng duy nhất)
        JPanel tablePanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBackground(Color.white);
        headerPanel.setPreferredSize(new Dimension(900, 100));
        tablePanel.add(headerPanel, BorderLayout.NORTH);

        JPanel TongSachPanel = new JPanel();
        TongSachPanel.setBounds(0, 0, 900, 40);
        TongSachPanel.setBorder(new MatteBorder(0, 0, 2, 0, Color.black));
        TongSachPanel.setBackground(Color.white);
        TongSachPanel.setLayout(null);

        TongSachLabel = new JLabel("Tổng số sách (" + listDauSach.size() + ")");
        TongSachLabel.setFont(new Font("Arial", Font.BOLD, 22));
        TongSachLabel.setBounds(30, 5, 320, 40);
        TongSachPanel.add(TongSachLabel);
        JLabel SortSachLabel = new JLabel("Sắp xếp theo: ");
        SortSachLabel.setFont(new Font("Arial", Font.BOLD, 16));
        SortSachLabel.setBounds(650, 10, 200, 40);
        TongSachPanel.add(SortSachLabel);
        SortSachCbx = new JComboBox<>();
        SortSachCbx.addItem("Mã sách");
        SortSachCbx.addItem("Ngày nhập");
        SortSachCbx.addItem("Trạng thái");
        SortSachCbx.setBounds(760, 5, 100, 30);
        SortSachCbx.addItemListener(sachController);
        TongSachPanel.add(SortSachCbx);

        headerPanel.add(TongSachPanel);

        FlatSVGIcon backIcon = new FlatSVGIcon(getClass().getResource("/svg/back-button.svg")).derive(30, 30);
        back = new JLabel(backIcon);
        back.setBounds(10, 50, 30, 30);
        headerPanel.add(back);

        JLabel backText = new JLabel("Quay lại");
        backText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        backText.setBounds(50, 50, 100, 30);
        headerPanel.add(backText);

        back.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "DanhSach");
                TrangthaiDauSachComboBox.setSelectedIndex(0);
                SortDauSachCBx.setSelectedIndex(0);
                currentCard = "DanhSach";
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setCursor(Cursor.getDefaultCursor());
            }
        });

        String column[] = new String[] { "Mã sách", "Ngày nhập", "Trạng thái" };
        tableModel = new DefaultTableModel(column, 0);
        table = new JTable(tableModel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    TrangthaiDauSachComboBox.setSelectedIndex(Integer.parseInt(table.getValueAt(row, 2).toString()));
                }
            }
        });
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        mainPanel.add(DSDauSach, "DanhSach");
        mainPanel.add(tablePanel, "Table");

        this.add(mainPanel, BorderLayout.CENTER);
    }

    public Sach() {
        listDauSach = dauSachBUS.getListDauSach();

        initComponent();
    }

    private void XoaDauSach() {
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa không?",
                "Xác nhận xóa", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            String maDauSach = MaDauSachTextfield.getText();
            dauSachBUS.delete(maDauSach);
            listDauSach = new DauSachDAO().selectAll();
            updateData(listDauSach);
        }
    }

    private RoundedPanel CreateDauSachPanel(DauSachDTO DauSach) {
        RoundedPanel panel = new RoundedPanel(20);
        panel.setPreferredSize(new Dimension(250, 250));
        panel.setBackground(Color.white);
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        // icon an hien
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/img1.jpg"));
        Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);

        JLabel HinhAnhSach = new JLabel(icon);
        HinhAnhSach.setBounds(75, 10, 100, 100);
        panel.add(HinhAnhSach);

        JLabel MaDauSach = new JLabel("Mã đầu sách: " + DauSach.getMaDauSach());
        MaDauSach.setBounds(10, 120, 240, 30);
        MaDauSach.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel TenDauSach = new JLabel("Tựa đề: " + DauSach.getTenDauSach());
        TenDauSach.setBounds(10, 145, 240, 30);
        TenDauSach.setFont(new Font("Arial", Font.BOLD, 14));

        // JLabel SoTrang = new JLabel("Số trang: " + DauSach.getSoTrang());
        // SoTrang.setBounds(10, 160, 200, 30);
        // SoTrang.setFont(new Font("Arial", Font.BOLD, 14));
        String Theloai = "";
        for (TheLoaiDTO tl : DauSach.getListTheLoai()) {
            Theloai += tl.getTenTheLoai() + ", ";
        }
        JLabel TheloaiLabel = new JLabel("Thể loại: " + Theloai.substring(0, Theloai.length() - 2));
        TheloaiLabel.setBounds(10, 170, 240, 30);
        TheloaiLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel NhaXuatBan = new JLabel("Nhà xuất bản: " + DauSach.getNhaXuatBan());
        NhaXuatBan.setBounds(10, 195, 240, 30);
        NhaXuatBan.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel NamXuatBan = new JLabel("Năm xuất bản: " + DauSach.getNamXuatBan());
        NamXuatBan.setBounds(10, 220, 240, 30);
        NamXuatBan.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(MaDauSach);
        panel.add(TheloaiLabel);
        panel.add(TenDauSach);
        panel.add(NhaXuatBan);
        panel.add(NamXuatBan);

        // Bắt sự kiện nhấn chuột
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateTable(DauSach.getMaDauSach());
                updateForm(DauSach);
                cardLayout.show(mainPanel, "Table");
                currentCard = "Table";
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setCursor(Cursor.getDefaultCursor());
            }
        });
        return panel;
    }

    private boolean ValidateDuLieu() {
        String soTrang = TuaDeTextfield.getText().trim();
        String nhaXuatBan = NgayNhapSachTextfield.getText().trim();
        String trangThai = (String) TrangthaiDauSachComboBox.getSelectedItem();

        if (soTrang.isEmpty() || nhaXuatBan.isEmpty() || trangThai.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return saveToDB();
    }

    private boolean saveToDB() {
        // String maDauSach = MaDauSachTextfield.getText().trim();
        // String tuaDe = TuaDeTextfield.getText().trim();
        // String nhaXuatBan = NgayNhapSachTextfield.getText().trim();
        // String trangThai = (String) TrangthaiDauSachComboBox.getSelectedItem();

        // DauSachDTO dausach = new DauSachDTO(maDauSach, tuaDe, null, nhaXuatBan, 0,
        // null, 0);
        // if (dauSachBUS.update(dausach)) {
        // javax.swing.JOptionPane.showMessageDialog(this, "Cập nhật thành công!",
        // "Thông báo",
        // javax.swing.JOptionPane.INFORMATION_MESSAGE);
        // return true;
        // } else {
        // javax.swing.JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi",
        // javax.swing.JOptionPane.ERROR_MESSAGE);
        // return false;
        // }
        return true;
    }

    private void performSearch() {
        String searchText = searchTextField.getText().trim();
        String selectedItem = (String) SearchCbx.getSelectedItem();
        if (searchText.isEmpty()) {
            updateData(listDauSach);
            return;
        }
        List<DauSachDTO> filteredList = dauSachBUS.searchDauSach(searchText, selectedItem);
        updateData(filteredList);
    }

    // Cập nhật dữ liệu bảng khi chọn đầu sách
    private void updateTable(String MadauSach) {
        SortSachCbx.setSelectedIndex(0);
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        // SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        listSach = new SachDAO().selectByDauSach(MadauSach);
        for (SachDTO sach : listSach) {
            tableModel.addRow(new Object[] {
                    sach.getMaSach(),
                    sach.getNgayNhap(),
                    sach.getTrangThai()
            });
        }
        this.TongSachLabel.setText("Tổng số sách (" + listSach.size() + ")");
    }

    public void updateTable(List<SachDTO> listSach) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        // SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (SachDTO sach : listSach) {
            tableModel.addRow(new Object[] {
                    sach.getMaSach(),
                    sach.getNgayNhap(),
                    sach.getTrangThai()
            });
        }
        this.TongSachLabel.setText("Tổng số sách (" + listSach.size() + ")");
    }

    private void updateForm(DauSachDTO dausach) {
        // Cập nhật thông tin sách vào form
        MaDauSachTextfield.setText(dausach.getMaDauSach());
        TuaDeTextfield.setText(dausach.getTenDauSach());
        NgayNhapSachTextfield.setText(dausach.getNhaXuatBan());
        setTextfieldDisable();

        // Cập nhật hình ảnh
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/" + dausach.getHinhAnh()));
        Image img = icon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        this.HinhAnhDauSach.setIcon(icon);
    }

    private void setTextfieldDisable() {
        this.NgayNhapSachTextfield.setEnabled(false);
        this.TuaDeTextfield.setEnabled(false);
        this.TrangthaiDauSachComboBox.setEnabled(false);
    }

    private void setTextfieldEnable() {
        this.MaDauSachTextfield.setEnabled(true);
        this.NgayNhapSachTextfield.setEnabled(true);
        this.TuaDeTextfield.setEnabled(true);
        this.TrangthaiDauSachComboBox.setEnabled(true);
    }

    public void updateData(List<DauSachDTO> list) {
        this.DSDauSachPanel.removeAll();
        for (int i = 0; i < list.size(); i++) {
            RoundedPanel panel = CreateDauSachPanel(list.get(i));
            DSDauSachPanel.add(panel);
        }
        this.DSDauSachPanel.revalidate();
        this.DSDauSachPanel.repaint();
    }

    public JComboBox<String> getSortComboBox() {
        return TrangthaiDauSachComboBox;
    }

    public List<DauSachDTO> getListDauSach() {
        return listDauSach;
    }

    public List<SachDTO> getListSach() {
        return listSach;
    }

    public String getCurrentCard() {
        return currentCard;
    }
}
