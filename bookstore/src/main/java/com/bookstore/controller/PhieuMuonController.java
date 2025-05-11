package com.bookstore.controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import com.bookstore.BUS.PhieuMuonBUS;
import com.bookstore.DTO.CTPhieuMuonDTO;
import com.bookstore.DTO.DauSachDTO;
import com.bookstore.DTO.PhieuMuonDTO;
import com.bookstore.DTO.SachDTO;
import com.bookstore.DTO.TaiKhoanDTO;
import com.bookstore.dao.CTPhieuMuonDAO;
import com.bookstore.dao.PhieuMuonDAO;
import com.bookstore.views.Panel.PhieuMuon;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.bookstore.views.Panel.Sach;

public class PhieuMuonController implements ItemListener, ActionListener {

    private PhieuMuon pm;
    private PhieuMuonDAO pmdao = new PhieuMuonDAO();
    private PhieuMuonBUS pmbus = new PhieuMuonBUS();
    @SuppressWarnings("unused")
    private TaiKhoanDTO tkDTO = new TaiKhoanDTO();
    private Comparator<PhieuMuonDTO> comparator = (pm1, pm2) -> 0;
    private List<PhieuMuonDTO> manggoc;
    private List<PhieuMuonDTO> mangtmp;
    private boolean isAscending = true;
    private CTPhieuMuonDAO ctpmdao = new CTPhieuMuonDAO();
    private FlatSVGIcon add_icon = new FlatSVGIcon(getClass().getResource("/svg/add_2.svg")).derive(12, 12);
    private FlatSVGIcon subtractIcon = new FlatSVGIcon(getClass().getResource("/svg/subtract.svg")).derive(25, 25);
    private Timer searchDG;
    public PhieuMuonController(PhieuMuon pm) {
        this.pm = pm;
        manggoc = pm.getListpm();
        mangtmp = pm.getListpm();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pm.getBtnTimKiem()) {
            String str = (String) pm.getCbLuaChonTK().getSelectedItem();
            if (str != null) {
                System.out.println("Da nhan btn tim kiem");
                String key = pm.getTxtTimKiem().getText().trim();
                switch (str) {
                    case "Mã phiếu mượn":
                        timMPM(key);
                        break;
                    case "Ngày mượn":
                        timNM(key);
                        break;
                    case "Ngày trả dự kiến":
                        timNTDK(key);
                        break;
                    case "Mã độc giả":
                        timMDG(key);
                        break;
                    case "Mã nhân viên":
                        timMNV(key);
                        break;
                    default:
                        break;
                }
            }
        } else if (e.getSource() == pm.getBtnReverse()) {
            if (isAscending) {
                pm.getBtnReverse().setIcon(pm.upIcon);
                Collections.sort(mangtmp, comparator);
            } else {
                pm.getBtnReverse().setIcon(pm.downIcon);
                Collections.sort(mangtmp, comparator.reversed());
            }
            isAscending = !isAscending;
            pm.updateTable(mangtmp);
        } else if (e.getSource() == pm.getBtnSua()) {
            // tao Jdialog
        } else if (e.getSource() == pm.getBtnXoa()) {
            if (pm.getTable().getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn phiếu mượn", "Phiếu mượn trống", 0);
                return;
            }
            System.out.println("VUa nhan vao btn xoa");
            JDialog dialog = new JDialog((Frame) null, "Xóa phiếu mượn");
            dialog.setSize(400, 200);
            dialog.setLayout(new BorderLayout());
            dialog.setResizable(false);

            int row = pm.getTable().getSelectedRow();
            String mpm = pm.getTable().getValueAt(row, 0).toString();

            JPanel mainPanel = new JPanel();
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            JLabel message = new JLabel("Bạn chắc chắn muốn xóa phiếu mượn " + mpm);
            message.setFont(new Font("Segoe UI", Font.BOLD, 16));
            message.setAlignmentX(Component.CENTER_ALIGNMENT);
            message.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
            JButton confirm = new JButton("Xác nhận");
            styleButton(confirm, new Color(0, 120, 215));
            JButton cancel = new JButton("Hủy");
            styleButton(cancel, new Color(100, 100, 100));

            contentPanel.add(message);
            buttonPanel.add(confirm);
            buttonPanel.add(cancel);
            mainPanel.add(contentPanel, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            dialog.add(mainPanel);

            confirm.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (pmbus.xoaPhieuMuon(Integer.parseInt(mpm))) {
                        pm.setListpm(pmdao.layDanhSachPhieuMuon());
                        pm.loadTableData();
                        JOptionPane.showMessageDialog(null, "Xóa phiếu mượn thành công", "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa phiếu mượn thất bại", "Thất bại",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    dialog.dispose();
                    manggoc = pm.getListpm();
                    mangtmp = pm.getListpm();
                }
            });
            cancel.addActionListener(e1 -> dialog.dispose());
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        } else if (e.getSource() == pm.getBtnThem()) {
            Sach tmpSach = new Sach();
            List<SachDTO> s = tmpSach.getListSach();
            if (s == null) {
                s = new ArrayList<>();
            }
            List<DauSachDTO> ds = tmpSach.getListDauSach();
            if (ds == null) {
                ds = new ArrayList<>();
            }

            System.out.println("Da nhan vao them");
            JDialog dialog = new JDialog((JFrame) null, "Thêm phiếu mượn", true);
            dialog.setSize(900, 600); // Tương tự kích thước của dialog phiếu nhập
            dialog.setLayout(new BorderLayout());
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(null);

            // Panel chính chia làm 2 phần
            JPanel mainContainer = new JPanel(new GridLayout(1, 2, 15, 0));
            mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            mainContainer.setBackground(new Color(245, 245, 245));

            // ========= PHẦN BÊN TRÁI (THÔNG TIN PHIẾU MƯỢN) =========
            JPanel leftPanel = new JPanel(new BorderLayout(0, 10));
            leftPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                    "Thông tin phiếu mượn",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Segoe UI", Font.BOLD, 14)));
            leftPanel.setBackground(new Color(245, 245, 245));

            // Panel chứa các trường nhập liệu
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
            inputPanel.setBackground(new Color(245, 245, 245));

            // Các trường nhập liệu
            JPanel row1 = createInputRow("Mã phiếu mượn:", new JTextField());
            JTextField txtfMaPhieuMuon = (JTextField) row1.getComponent(1);
            txtfMaPhieuMuon.setEditable(false);

            JPanel row2 = createInputRow("Ngày mượn (yyyy-MM-dd):", new JTextField());
            JTextField txtfNgayMuon = (JTextField) row2.getComponent(1);
            txtfNgayMuon.setEditable(false);

            JPanel row3 = createInputRow("Ngày trả dự kiến (yyyy-MM-dd):", new JTextField());
            JTextField txtfNgayTraDuKien = (JTextField) row3.getComponent(1);
            txtfNgayTraDuKien.setEditable(false);

            // Panel cho phần chọn độc giả với JList
            JPanel row4 = new JPanel(new BorderLayout(0, 5));
            row4.setBackground(new Color(245, 245, 245));
            JLabel dgLabel = new JLabel("Độc giả:");
            dgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            row4.add(dgLabel, BorderLayout.NORTH);

            JTextField txtfSearchDG = new JTextField();
            txtfSearchDG.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            txtfSearchDG.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180)),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));

            JList<DocGiaDTO> listDG = new JList<>(); // Giả sử có class DocGiaDTO
            listDG.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            listDG.setVisibleRowCount(5);
            listDG.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
            JScrollPane dgScrollPane = new JScrollPane(listDG);
            dgScrollPane.setPreferredSize(new Dimension(0, 100));

            JPanel dgInputPanel = new JPanel(new BorderLayout(0, 5));
            dgInputPanel.setBackground(new Color(245, 245, 245));
            dgInputPanel.add(txtfSearchDG, BorderLayout.NORTH);
            dgInputPanel.add(dgScrollPane, BorderLayout.CENTER);
            row4.add(dgInputPanel, BorderLayout.CENTER);

            // Thêm các TextField chi tiết độc giả (tùy chọn, nếu cần)
            JPanel row5 = createInputRow("Tên độc giả:", new JTextField());
            JTextField txtfTenDG = (JTextField) row5.getComponent(1);
            txtfTenDG.setEditable(false);

            // Thêm các row vào inputPanel
            inputPanel.add(row1);
            inputPanel.add(Box.createVerticalStrut(15));
            inputPanel.add(row2);
            inputPanel.add(Box.createVerticalStrut(15));
            inputPanel.add(row3);
            inputPanel.add(Box.createVerticalStrut(15));
            inputPanel.add(row4);
            inputPanel.add(Box.createVerticalStrut(15));
            inputPanel.add(row5);

            // Thời gian hiện tại
            LocalDate tmpday = LocalDate.now();
            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fomatedday = tmpday.format(f);
            String fomatedngaytradukien = tmpday.plusDays(15).format(f);
            txtfNgayMuon.setText(fomatedday);
            txtfNgayTraDuKien.setText(fomatedngaytradukien);

            // Mã phiếu mượn mới
            int mpm = pm.getListpm().stream()
                    .mapToInt(PhieuMuonDTO::getMaPhieuMuon)
                    .max()
                    .orElse(0) + 1;
            txtfMaPhieuMuon.setText(String.valueOf(mpm));

            DocGiaBUS dgBus = new DocGiaBUS();
            List<DocGiaDTO> danhSachDG = dgBus.getList();
            System.out.println("Tổng số độc giả: " + danhSachDG.size());

            // Renderer cho JList
            listDG.setCellRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof DocGiaDTO) {
                        DocGiaDTO dg = (DocGiaDTO) value;
                        setText(dg.getMaDocGia() + " - " + dg.getTenDocGia()); // Giả sử có các phương thức này
                    }
                    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    setBackground(isSelected ? new Color(0, 120, 215) : Color.WHITE);
                    setForeground(isSelected ? Color.WHITE : Color.BLACK);
                    return this;
                }
            });

            // Hiển thị toàn bộ danh sách độc giả ngay khi mở dialog
            DefaultListModel<DocGiaDTO> initialModel = new DefaultListModel<>();
            for (DocGiaDTO dg : danhSachDG) {
                initialModel.addElement(dg);
            }
            listDG.setModel(initialModel);
            dgScrollPane.setVisible(true);
            dgInputPanel.revalidate();
            dgInputPanel.repaint();

            searchDG = new Timer();
            txtfSearchDG.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    searchDG.cancel();
                    searchDG.purge();
                    searchDG = new Timer();
                    searchDG.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            SwingUtilities.invokeLater(() -> {
                                String searchText = txtfSearchDG.getText().trim().toLowerCase();
                                DefaultListModel<DocGiaDTO> listModel = new DefaultListModel<>();

                                if (searchText.isEmpty()) {
                                    for (DocGiaDTO dg : danhSachDG) {
                                        listModel.addElement(dg);
                                    }
                                } else {
                                    for (DocGiaDTO dg : danhSachDG) {
                                        String maDG = String.valueOf(dg.getMaDocGia()).toLowerCase();
                                        String tenDG = dg.getTenDocGia().toLowerCase();
                                        if (maDG.contains(searchText) || tenDG.contains(searchText)) {
                                            listModel.addElement(dg);
                                        }
                                    }
                                }

                                listDG.setModel(listModel);
                                dgScrollPane.setVisible(!listModel.isEmpty());
                                dgInputPanel.revalidate();
                                dgInputPanel.repaint();
                                System.out.println("Số độc giả tìm thấy: " + listModel.getSize());
                            });
                        }
                    }, 300);
                }
            });

            // Xử lý khi chọn item từ JList
            listDG.addListSelectionListener(e1 -> {
                if (!e1.getValueIsAdjusting() && listDG.getSelectedValue() != null) {
                    DocGiaDTO selectedDG = listDG.getSelectedValue();
                    txtfSearchDG.setText(String.valueOf(selectedDG.getMaDocGia()));
                    txtfTenDG.setText(selectedDG.getTenDocGia());
                    dgScrollPane.setVisible(false);
                    dgInputPanel.revalidate();
                    dgInputPanel.repaint();
                }
            });

            leftPanel.add(inputPanel, BorderLayout.CENTER);

            // ========= PHẦN BÊN PHẢI (DANH SÁCH SÁCH MƯỢN) =========
            JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
            rightPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                    "Danh sách mượn",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Segoe UI", Font.BOLD, 14)));
            rightPanel.setBackground(new Color(245, 245, 245));

            // Panel header
            JPanel headerPanel = new JPanel(new GridLayout(1, 2, 5, 5)); // Chỉ có mã sách và nút xóa
            headerPanel.setBackground(new Color(245, 245, 245));
            JLabel maSachLabel = new JLabel("Mã sách");
            JLabel actionLabel = new JLabel("");
            maSachLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            headerPanel.add(maSachLabel);
            headerPanel.add(actionLabel);

            // Panel chứa các dòng nhập sách
            JPanel booksInputPanel = new JPanel();
            booksInputPanel.setLayout(new BoxLayout(booksInputPanel, BoxLayout.Y_AXIS));
            booksInputPanel.setBackground(new Color(245, 245, 245));

            // Hàm thêm dòng nhập sách mới
            Runnable addBookRow = () -> {
                JPanel bookRow = new JPanel(new GridBagLayout());
                bookRow.setBackground(new Color(245, 245, 245));
                bookRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(0, 5, 0, 5);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                JTextField txtMaSach = new JTextField();
                txtMaSach.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                txtMaSach.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(180, 180, 180)),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                txtMaSach.setPreferredSize(new Dimension(180, 30));
                txtMaSach.setMaximumSize(new Dimension(180, 30));

                JButton btnRemove = new JButton("Xóa");
                styleButton(btnRemove, new Color(200, 50, 50));
                btnRemove.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                btnRemove.setPreferredSize(new Dimension(50, 30));
                btnRemove.setMaximumSize(new Dimension(50, 30));

                gbc.gridx = 0;
                gbc.weightx = 0.85;
                bookRow.add(txtMaSach, gbc);

                gbc.gridx = 1;
                gbc.weightx = 0.15;
                bookRow.add(btnRemove, gbc);

                booksInputPanel.add(bookRow);
                booksInputPanel.add(Box.createVerticalStrut(5));

                btnRemove.addActionListener(e1 -> {
                    booksInputPanel.remove(bookRow);
                    booksInputPanel.remove(booksInputPanel.getComponentCount() - 1); // Xóa strut
                    booksInputPanel.revalidate();
                    booksInputPanel.repaint();
                });

                booksInputPanel.revalidate();
                booksInputPanel.repaint();
            };

            // Thêm 7 dòng mặc định
            for (int i = 0; i < 7; i++) {
                addBookRow.run();
            }

            // Thêm nút thêm dòng
            JButton btnAddBook = new JButton("+ Thêm sách");
            styleButton(btnAddBook, new Color(0, 120, 215));
            btnAddBook.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnAddBook.addActionListener(e2 -> addBookRow.run());

            JScrollPane booksScrollPane = new JScrollPane(booksInputPanel);
            booksScrollPane.setBorder(BorderFactory.createEmptyBorder());
            booksScrollPane.setBackground(new Color(245, 245, 245));

            rightPanel.add(headerPanel, BorderLayout.NORTH);
            rightPanel.add(booksScrollPane, BorderLayout.CENTER);
            rightPanel.add(btnAddBook, BorderLayout.SOUTH);

            // Thêm 2 panel vào main container
            mainContainer.add(leftPanel);
            mainContainer.add(rightPanel);

            // Panel chứa nút xác nhận/hủy
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            buttonPanel.setBackground(new Color(245, 245, 245));
            JButton confirm = new JButton("Xác nhận");
            JButton cancel = new JButton("Hủy");

            // Style cho nút
            styleButton(confirm, new Color(0, 120, 215));
            styleButton(cancel, new Color(100, 100, 100));
            confirm.setFont(new Font("Segoe UI", Font.BOLD, 14));
            cancel.setFont(new Font("Segoe UI", Font.BOLD, 14));

            buttonPanel.add(confirm);
            buttonPanel.add(cancel);

            dialog.add(mainContainer, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);

            // Xử lý nút Xác nhận
            confirm.addActionListener(e2 -> {
                DocGiaDTO selectedDG = listDG.getSelectedValue();
                if (selectedDG == null) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng chọn độc giả", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Lấy danh sách mã sách
                List<String> maSachList = new ArrayList<>();
                for (Component comp : booksInputPanel.getComponents()) {
                    if (comp instanceof JPanel) {
                        JPanel row = (JPanel) comp;
                        Component[] components = row.getComponents();
                        if (components.length >= 2) {
                            JTextField txtMaSach = (JTextField) components[0];
                            if (!txtMaSach.getText().trim().isEmpty()) {
                                maSachList.add(txtMaSach.getText().trim());
                            }
                        }
                    }
                }

                if (maSachList.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng nhập ít nhất một mã sách", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                java.sql.Date ngaymuon = java.sql.Date.valueOf(tmpday);
                java.sql.Date ngaytradukien = java.sql.Date.valueOf(tmpday.plusDays(15));
                PhieuMuonDTO newPM = new PhieuMuonDTO(mpm, ngaymuon, ngaytradukien, 0, selectedDG.getMaDocGia(),
                        "nv001",
                        true);
                if (pmbus.themPhieuMuon(newPM)) {
                    for (String maSach : maSachList) {
                        pmbus.themChiTietPhieuMuon(newPM.getMaPhieuMuon(), maSach);
                    }
                    pm.setListpm(pmdao.layDanhSachPhieuMuon());
                    pm.loadTableData();
                    JOptionPane.showMessageDialog(dialog, "Thêm phiếu mượn thành công", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Thêm phiếu mượn thất bại", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            // Xử lý nút Hủy
            cancel.addActionListener(e3 -> dialog.dispose());

            dialog.setVisible(true);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            @SuppressWarnings("unchecked")
            JComboBox<String> cbb = (JComboBox<String>) e.getSource();
            String str = (String) cbb.getSelectedItem();
            if (str != null) {
                System.out.println("da nhan vao sap xep" + str);
                switch (str) {
                    case "Tất cả":
                        comparator = Comparator.comparing(PhieuMuonDTO::getMaPhieuMuon);
                        break;

                    case "Mã phiếu mượn":
                        comparator = Comparator.comparing(PhieuMuonDTO::getMaPhieuMuon);
                        break;

                    case "Ngày mượn":
                        comparator = Comparator.comparing(PhieuMuonDTO::getNgayMuon);
                        break;

                    case "Ngày trả dự kiến":
                        comparator = Comparator.comparing(PhieuMuonDTO::getNgayTraDuKien);
                        break;

                    case "Trạng thái":
                        comparator = Comparator.comparing(PhieuMuonDTO::getTrangThai);
                        break;
                    case "Mã độc giả":
                        comparator = Comparator.comparing(PhieuMuonDTO::getMaDocGia);
                        break;
                    case "Mã nhân viên":
                        comparator = Comparator.comparing(PhieuMuonDTO::getMaNhanVien);
                        break;
                    default:
                        break;
                }

                Collections.sort(mangtmp, comparator);
                pm.updateTable(mangtmp);
            }
        }
    }

    public void hienthichitiettable(int maPhieuMuon) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Chi tiết phiếu mượn");
        dialog.setLayout(new GridLayout(1, 2, 10, 10));
        dialog.setSize(new Dimension(600, 400));

        JPanel panelthongtin = new JPanel();
        panelthongtin.setLayout(new GridLayout(6, 1, 10, 10));

        Date ngaymuon = null;
        Date ngaytradukien = null;
        int trangthai = -1;
        String madocgia = "";
        String manhanvien = "";

        for (PhieuMuonDTO i : manggoc) {
            if (i.getMaPhieuMuon() == maPhieuMuon) {
                ngaymuon = i.getNgayMuon();
                ngaytradukien = i.getNgayTraDuKien();
                trangthai = i.getTrangThai();
                madocgia = i.getMaDocGia();
                manhanvien = i.getMaNhanVien();
                break;
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JLabel lbmaphieumuon = new JLabel("Mã phiếu mượn: " + maPhieuMuon);
        lbmaphieumuon.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        JLabel lbngaymuon = new JLabel("Ngày mượn " + sdf.format(ngaymuon));
        lbngaymuon.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        JLabel lbngaytradukien = new JLabel("Ngày trả dự kiến: " + sdf.format(ngaytradukien));
        lbngaytradukien.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        JLabel lbtrangthai;
        if (trangthai == 1) {
            lbtrangthai = new JLabel("Trạng thái: đã trả");
        } else {
            lbtrangthai = new JLabel("Trạng thái: chưa trả");
        }
        lbtrangthai.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        JLabel lbmadocgia = new JLabel("Mã độc giả: " + madocgia);
        lbmadocgia.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        JLabel lbmanhanvien = new JLabel("Mã nhân viên: " + manhanvien);
        lbmanhanvien.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        panelthongtin.add(lbmaphieumuon);
        panelthongtin.add(lbngaymuon);
        panelthongtin.add(lbngaytradukien);
        panelthongtin.add(lbtrangthai);
        panelthongtin.add(lbmadocgia);
        panelthongtin.add(lbmanhanvien);

        JPanel panelchitiet = new JPanel();
        panelchitiet.setLayout(new GridLayout(6, 1, 10, 10));
        int count = 0;
        List<String> tongHopMaSach = new ArrayList<>();
        List<CTPhieuMuonDTO> ctpmlist = ctpmdao.layDanhSachCTPhieuMuon();
        for (CTPhieuMuonDTO i : ctpmlist) {
            if (i.getMaPhieuMuon() == maPhieuMuon) {
                tongHopMaSach.add(i.getMaSach());
                count++;
            }
        }

        JLabel lbsoluong = new JLabel("Tổng số sách đã mượn: " + count);
        panelchitiet.add(lbsoluong);
        for (int i = 1; i <= count; i++) {
            JPanel panelctsach = new JPanel();
            panelctsach.setLayout(new GridLayout(1, 2));
            JLabel tmpLabel = new JLabel("Mã sách " + i + ": ");
            JLabel masachlabel = new JLabel(tongHopMaSach.get(i - 1));
            panelctsach.add(tmpLabel);
            panelctsach.add(masachlabel);
            panelchitiet.add(panelctsach);
        }

        dialog.add(panelthongtin);
        dialog.add(panelchitiet);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void styleButton(JButton button, Color color) {
        button.setPreferredSize(new Dimension(120, 40));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker()),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void timMPM(String key) {
        mangtmp = manggoc.stream()
                .filter(i -> (i.getMaPhieuMuon() + "").contains(key))
                .collect(Collectors.toList());
        pm.updateTable(mangtmp);
    }

    public void timNM(String key) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        mangtmp = manggoc.stream()
                .filter(i -> sdf.format(i.getNgayMuon()).contains(key))
                .collect(Collectors.toList());
        pm.updateTable(mangtmp);
    }

    public void timNTDK(String key) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        mangtmp = manggoc.stream()
                .filter(i -> sdf.format(i.getNgayTraDuKien()).contains(key))
                .collect(Collectors.toList());
        pm.updateTable(mangtmp);
    }

    public void timTrangThai(String key) {
        mangtmp = manggoc.stream()
                .filter(i -> (i.getTrangThai() + "").contains(key))
                .collect(Collectors.toList());
        pm.updateTable(mangtmp);
    }

    public void timMDG(String key) {
        mangtmp = manggoc.stream()
                .filter(i -> i.getMaDocGia().contains(key))
                .collect(Collectors.toList());
        pm.updateTable(mangtmp);
    }

    public void timMNV(String key) {
        mangtmp = manggoc.stream()
                .filter(i -> i.getMaNhanVien().contains(key))
                .collect(Collectors.toList());
        pm.updateTable(mangtmp);
    }

    private JPanel createInputRow(String labelText, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setBackground(new Color(245, 245, 245));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(label, BorderLayout.WEST);
        component.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        component.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private void addBookRow(JPanel parentPanel, List<JPanel> bookRows, List<JTextField> bookFields,
            List<JComboBox<SachDTO>> suggestionBoxes) {
        if (bookRows.size() >= 5) {
            JOptionPane.showMessageDialog(null, "Chỉ được thêm tối đa 5 sách", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.Y_AXIS));
        rowPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        JLabel label = new JLabel("Mã sách " + (bookRows.size() + 1) + ": ");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setPreferredSize(new Dimension(80, 25));
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textField.setPreferredSize(new Dimension(200, 28));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(3, 5, 3, 5)));
        bookFields.add(textField);

        JButton subtractButton = new JButton(subtractIcon);
        subtractButton.setPreferredSize(new Dimension(30, 28));
        subtractButton.setBorder(BorderFactory.createEmptyBorder());
        subtractButton.setContentAreaFilled(false);
        subtractButton.setToolTipText("Xóa dòng này");
        subtractButton
                .addActionListener(e -> removeBookRow(parentPanel, bookRows, bookFields, suggestionBoxes, rowPanel));

        JPanel labelFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        labelFieldPanel.add(label);
        labelFieldPanel.add(textField);
        inputPanel.add(labelFieldPanel, BorderLayout.CENTER);
        inputPanel.add(subtractButton, BorderLayout.EAST);

        JComboBox<SachDTO> suggestionBox = new JComboBox<>();
        suggestionBox.setPreferredSize(new Dimension(280, 28));
        suggestionBox.setVisible(false);
        suggestionBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof SachDTO) {
                    SachDTO sach = (SachDTO) value;
                    setText(sach.getMaSach() + " - " + sach.getTrangThai());
                }
                return this;
            }
        });
        suggestionBoxes.add(suggestionBox);

        suggestionBox.addActionListener(e -> {
            SachDTO selectedSach = (SachDTO) suggestionBox.getSelectedItem();
            if (selectedSach != null) {
                textField.setText(selectedSach.getMaSach());
                suggestionBox.setVisible(false);
            }
        });

        rowPanel.add(inputPanel);
        rowPanel.add(suggestionBox);

        if (!bookRows.isEmpty()) {
            parentPanel.add(Box.createVerticalStrut(5));
        }

        bookRows.add(rowPanel);
        parentPanel.add(rowPanel);
        parentPanel.revalidate();
        parentPanel.repaint();

        textField.addKeyListener(new KeyAdapter() {
            private javax.swing.Timer searchTimer = new javax.swing.Timer(300,
                    e -> performSearch(textField, suggestionBox));

            @Override
            public void keyReleased(KeyEvent e) {
                searchTimer.restart(); // Khởi động lại timer mỗi khi nhập
            }
        });
    }

    private void removeBookRow(JPanel parentPanel, List<JPanel> bookRows, List<JTextField> bookFields,
            List<JComboBox<SachDTO>> suggestionBoxes, JPanel rowToRemove) {
        int index = bookRows.indexOf(rowToRemove);
        if (index >= 0) {
            parentPanel.remove(rowToRemove);
            bookRows.remove(rowToRemove);
            bookFields.remove(index);
            suggestionBoxes.remove(index);
            for (int i = 0; i < bookRows.size(); i++) {
                JPanel panel = bookRows.get(i);
                JLabel label = (JLabel) ((JPanel) ((JPanel) panel.getComponent(0)).getComponent(0)).getComponent(0);
                label.setText("Mã sách " + (i + 1) + ": ");
            }
            parentPanel.revalidate();
            parentPanel.repaint();
        }
    }

    private void performSearch(JTextField textField, JComboBox<SachDTO> suggestionBox) {
        String keyword = textField.getText().trim();
        Sach tmpSach = new Sach();
        List<SachDTO> sachList = tmpSach.getListSach();
        if (sachList == null) {
            JOptionPane.showMessageDialog(null, "Danh sách sách không khả dụng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            suggestionBox.setVisible(false);
            return;
        }

        List<SachDTO> result = sachList.stream()
                .filter(sach -> sach.getMaSach().toLowerCase().contains(keyword.toLowerCase()) ||
                        sach.getTrangThai().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        suggestionBox.removeAllItems();
        if (!keyword.isEmpty() && !result.isEmpty()) {
            for (SachDTO sach : result) {
                suggestionBox.addItem(sach);
            }
            suggestionBox.setVisible(true);
            suggestionBox.showPopup();
        } else {
            suggestionBox.setVisible(false);
        }
    }
}