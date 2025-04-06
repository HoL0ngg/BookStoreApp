package com.bookstore.views.Panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import com.bookstore.DTO.DauSachDTO;
import com.bookstore.DTO.SachDTO;
import com.bookstore.controller.SachController;
import com.bookstore.dao.DauSachDAO;
import com.bookstore.dao.SachDAO;
import com.bookstore.views.Component.RoundedPanel;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class Sach extends JPanel {
    private SachController sachController = new SachController(this);
    private JPanel DSDauSach;
    private CardLayout cardLayout;
    private JLabel back;
    private JTable table;
    private DefaultTableModel tableModel;
    private JPanel mainPanel;
    private JTextField MaDauSachTextfield;
    private JTextField SoTrangTextfield;
    private JLabel HinhAnhDauSach;
    private JComboBox<String> TrangThaiSachComboBox;
    private JTextField NgayNhapSachTextfield;
    private JPanel DSDauSachPanel;
    List<DauSachDTO> list;

    public void initComponent() {
        this.setBackground(Color.white);
        this.setLayout(new FlowLayout(1, 0, 0));
        this.setPreferredSize(new Dimension(900, 600));

        JPanel ThongTinDauSachPanel = new JPanel();
        ThongTinDauSachPanel.setBackground(Color.pink);
        ThongTinDauSachPanel.setPreferredSize(new Dimension(900, 200));
        ThongTinDauSachPanel.setLayout(new FlowLayout(1, 0, 0));

        HinhAnhDauSach = new JLabel();
        HinhAnhDauSach.setPreferredSize(new Dimension(300, 200));
        HinhAnhDauSach.setOpaque(true);
        ThongTinDauSachPanel.add(HinhAnhDauSach);

        JPanel ThongTinChiTiet = new JPanel();
        ThongTinChiTiet.setPreferredSize(new Dimension(600, 200));
        ThongTinChiTiet.setLayout(new GridLayout(4, 3, 10, 10));

        JLabel MaTauLabel = new JLabel("Mã sách: ");
        MaDauSachTextfield = new JTextField();
        MaDauSachTextfield.setPreferredSize(new Dimension(200, 30));

        JLabel SoGheLabel = new JLabel("Tựa đề: ");
        SoTrangTextfield = new JTextField();
        SoTrangTextfield.setPreferredSize(new Dimension(200, 30));

        JLabel TrangThaiTauLabel = new JLabel("Trạng thái sách: ");
        TrangThaiSachComboBox = new JComboBox<String>(new String[] { "", "Bình thường", "Đã mượn", "Bị hư", "Đã xóa" });
        TrangThaiSachComboBox.setPreferredSize(new Dimension(200, 30));

        JLabel NgayNhapTauLabel = new JLabel("Nhà xuất bản: ");
        NgayNhapSachTextfield = new JTextField();
        NgayNhapSachTextfield.setPreferredSize(new Dimension(200, 30));

        JButton ThemTauButton = new JButton("THÊM");
        JButton SuaTauButton = new JButton("SỬA");
        JButton XoaTauButton = new JButton("XÓA");
        JButton LuuButton = new JButton("LƯU");

        ThongTinChiTiet.add(MaTauLabel);
        ThongTinChiTiet.add(MaDauSachTextfield);
        ThongTinChiTiet.add(ThemTauButton);

        ThongTinChiTiet.add(SoGheLabel);
        ThongTinChiTiet.add(SoTrangTextfield);
        ThongTinChiTiet.add(SuaTauButton);

        ThongTinChiTiet.add(TrangThaiTauLabel);
        ThongTinChiTiet.add(TrangThaiSachComboBox);
        ThongTinChiTiet.add(XoaTauButton);

        ThongTinChiTiet.add(NgayNhapTauLabel);
        ThongTinChiTiet.add(NgayNhapSachTextfield);
        ThongTinChiTiet.add(LuuButton);

        ThongTinDauSachPanel.add(ThongTinChiTiet);
        this.add(ThongTinDauSachPanel);

        JPanel TongSoTauPanel = new JPanel();
        TongSoTauPanel.setPreferredSize(new Dimension(900, 40));
        TongSoTauPanel.setBorder(new MatteBorder(0, 0, 2, 0, Color.black));
        TongSoTauPanel.setBackground(Color.white);
        TongSoTauPanel.setLayout(null);
        JLabel TongSoTauLabel = new JLabel("Tổng số đầu sách (" + list.size() + ")");
        TongSoTauLabel.setFont(new Font("Arial", Font.BOLD, 22));
        TongSoTauLabel.setBounds(30, 5, 320, 40);
        TongSoTauPanel.add(TongSoTauLabel);
        this.add(TongSoTauPanel);
        JLabel SortLabel = new JLabel("Sắp xếp theo: ");
        SortLabel.setFont(new Font("Arial", Font.BOLD, 16));
        SortLabel.setBounds(650, 5, 200, 40);
        TongSoTauPanel.add(SortLabel);
        JComboBox<String> SortComboBox = new JComboBox<>();
        SortComboBox.addItem("Mã đầu sách");
        SortComboBox.addItem("Số trang");
        SortComboBox.addItem("Năm xuất bản");
        SortComboBox.setBounds(760, 14, 100, 20);
        SortComboBox.addItemListener(sachController);
        TongSoTauPanel.add(SortComboBox);

        DSDauSach = new JPanel();
        DSDauSach.setLayout(new BorderLayout());
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        DSDauSachPanel = new JPanel();

        DSDauSachPanel.setLayout(new FlowLayout(0, 36, 40));
        DSDauSachPanel.setPreferredSize(new Dimension(900, ((list.size() + 5) / 3) * 260));
        DSDauSachPanel.setBackground(Color.white);

        for (int i = 0; i < list.size(); ++i) {
            RoundedPanel dauSachPanel = CreateDauSachPanel(list.get(i));
            DSDauSachPanel.add(dauSachPanel);
        }
        JScrollPane DSDauSachScollPane = new JScrollPane(DSDauSachPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        DSDauSachScollPane.setPreferredSize(new Dimension(900, 560));

        // Panel chứa bảng sách (chỉ có 1 bảng duy nhất)
        JPanel tablePanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBackground(Color.white);
        headerPanel.setPreferredSize(new Dimension(900, 50));
        tablePanel.add(headerPanel, BorderLayout.NORTH);

        FlatSVGIcon backIcon = new FlatSVGIcon(getClass().getResource("/svg/back-button.svg")).derive(30, 30);
        back = new JLabel(backIcon);
        back.setBounds(10, 10, 30, 30);
        headerPanel.add(back);

        JLabel backText = new JLabel("Quay lại");
        backText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        backText.setBounds(50, 10, 100, 30);
        headerPanel.add(backText);

        back.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "DanhSach");
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
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        mainPanel.add(DSDauSachScollPane, "DanhSach");
        mainPanel.add(tablePanel, "Table");

        this.add(mainPanel, BorderLayout.CENTER);

        // DanhSachTauScrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(DSDauSach);

    }

    public Sach() {
        list = new DauSachDAO().selectAll();
        initComponent();
    }

    private RoundedPanel CreateDauSachPanel(DauSachDTO DauSach) {
        RoundedPanel panel = new RoundedPanel(20);
        panel.setPreferredSize(new Dimension(240, 240));
        panel.setBackground(Color.white);
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        FlatSVGIcon icon = new FlatSVGIcon(getClass().getResource("/svg/book.svg")).derive(100, 100);
        JLabel HinhAnhSach = new JLabel(icon);
        HinhAnhSach.setBounds(75, 10, 100, 100);
        panel.add(HinhAnhSach);

        JLabel MaDauSach = new JLabel("Mã đầu sách: " + DauSach.getMaDauSach());
        MaDauSach.setBounds(10, 120, 200, 30);
        MaDauSach.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel TenDauSach = new JLabel("Tựa đề: " + DauSach.getTenDauSach());
        TenDauSach.setBounds(10, 140, 200, 30);
        TenDauSach.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel SoTrang = new JLabel("Số trang: " + DauSach.getSoTrang());
        SoTrang.setBounds(10, 160, 200, 30);
        SoTrang.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel NhaXuatBan = new JLabel("Nhà xuất bản: " + DauSach.getNhaXuatBan());
        NhaXuatBan.setBounds(10, 180, 230, 30);
        NhaXuatBan.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel NamXuatBan = new JLabel("Năm xuất bản: " + DauSach.getNamXuatBan());
        NamXuatBan.setBounds(10, 200, 230, 30);
        NamXuatBan.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(MaDauSach);
        panel.add(SoTrang);
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

    // Cập nhật dữ liệu bảng khi chọn đầu sách
    private void updateTable(String MadauSach) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<SachDTO> hihi = new SachDAO().selectByDauSach(MadauSach);
        for (SachDTO sach : hihi) {
            tableModel.addRow(new Object[] {
                    sach.getMaSach(),
                    sach.getNgayNhap(),
                    sach.getTrangThai()
            });
        }
    }

    private void updateForm(DauSachDTO dausach) {
        // Cập nhật thông tin sách vào form
        MaDauSachTextfield.setText(dausach.getMaDauSach());
        SoTrangTextfield.setText(dausach.getTenDauSach());
        NgayNhapSachTextfield.setText(dausach.getNhaXuatBan());
        setTextfieldDisable();
        FlatSVGIcon icon = new FlatSVGIcon(getClass().getResource("/svg/Book.svg")).derive(200, 200);
        this.HinhAnhDauSach.setIcon(icon);
    }

    private void setTextfieldDisable() {
        this.MaDauSachTextfield.setEnabled(false);
        this.NgayNhapSachTextfield.setEnabled(false);
        this.SoTrangTextfield.setEnabled(false);
        this.TrangThaiSachComboBox.setEnabled(false);
    }

    private void setTextfieldEnable() {
        this.MaDauSachTextfield.setEnabled(true);
        this.NgayNhapSachTextfield.setEnabled(true);
        this.SoTrangTextfield.setEnabled(true);
        this.TrangThaiSachComboBox.setEnabled(true);
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
        return TrangThaiSachComboBox;
    }

    public List<DauSachDTO> getListDauSach() {
        return list;
    }
}
