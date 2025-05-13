package com.bookstore.controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.bookstore.BUS.DauSachBUS;
import com.bookstore.BUS.DocGiaBUS;
import com.bookstore.BUS.PhieuMuonBUS;
import com.bookstore.DTO.CTPhieuMuonDTO;
import com.bookstore.DTO.DauSachDTO;
import com.bookstore.DTO.DocGiaDTO;
import com.bookstore.DTO.PhieuMuonDTO;
import com.bookstore.DTO.SachDTO;
import com.bookstore.DTO.TaiKhoanDTO;
import com.bookstore.dao.CTPhieuMuonDAO;
import com.bookstore.dao.NhomQuyenDAO;
import com.bookstore.dao.PhieuMuonDAO;
import com.bookstore.dao.SachDAO;
import com.bookstore.utils.NguoiDungDangNhap;
import com.bookstore.views.Panel.PhieuMuon;
import com.formdev.flatlaf.json.ParseException;

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
    private Timer searchDG;

    public PhieuMuonController(PhieuMuon pm) {
        this.pm = pm;
        manggoc = pm.getListpm();
        mangtmp = pm.getListpm();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pm.getBtnReverse()) {
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
            if (!new NhomQuyenDAO().isAccessable(NguoiDungDangNhap.getInstance().getMaNhomQuyen(), 6, 3)) {
                JOptionPane.showMessageDialog(null, "Bạn không có quyền sửa phiếu mượn", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            int slt = pm.getTable().getSelectedRow();
            if (slt == -1) {
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn phiếu muốn sửa", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Lấy MaPhieuMuon từ cột 0
            String mpmStr = pm.getTable().getValueAt(slt, 0).toString();
            int mpm;
            try {
                mpm = Integer.parseInt(mpmStr); // Chuyển String thành int
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Mã phiếu mượn không hợp lệ", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Giả sử các cột trong bảng lần lượt là: MaPhieuMuon, NgayMuon, NgayTraDuKien,
            // TrangThai, MaDocGia, MaNhanVien
            String ngayMuon = pm.getTable().getValueAt(slt, 1).toString();
            String ngayTraDuKien = pm.getTable().getValueAt(slt, 2).toString();
            String maDocGia = pm.getTable().getValueAt(slt, 4).toString();
            String maNhanVien = pm.getTable().getValueAt(slt, 5).toString();
            String TrangThai = pm.getTable().getValueAt(slt, 3).toString();
            int tmptrangthai = TrangThai.equals("Đã hoàn thành") ? 1 : 0;
            int currentTrangThai = 1;
            for (PhieuMuonDTO pmItem : pm.getListpm()) {
                if (pmItem.getMaPhieuMuon() == mpm) { // So sánh int với int
                    currentTrangThai = pmItem.getTrangThai();
                    break;
                }
            }

            if (currentTrangThai == 1) {
                JOptionPane.showMessageDialog(null, "Phiếu đã hoàn thành không thể sửa!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Tạo dialog chỉnh sửa
            JDialog dialog = new JDialog((JFrame) null, "Sửa phiếu mượn", true);
            dialog.setSize(400, 350);
            dialog.setLayout(new BorderLayout());
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(null);

            // Panel chính
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Panel chứa các trường nhập liệu
            JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));

            // Các trường nhập liệu
            JLabel lblMpm = new JLabel("Mã phiếu mượn:");
            JTextField txtMpm = new JTextField(mpmStr);
            txtMpm.setEditable(false); // Không cho sửa
            JLabel lblNgayMuon = new JLabel("Ngày mượn (yyyy-MM-dd):");
            JTextField txtNgayMuon = new JTextField(ngayMuon);
            txtNgayMuon.setEditable(false); // Không cho sửa
            JLabel lblNgayTraDuKien = new JLabel("Ngày trả dự kiến (yyyy-MM-dd):");
            JTextField txtNgayTraDuKien = new JTextField(ngayTraDuKien);
            txtNgayTraDuKien.setEditable(true); // Không cho sửa
            JLabel lblMaDocGia = new JLabel("Mã độc giả:");
            JTextField txtMaDocGia = new JTextField(maDocGia);
            txtMaDocGia.setEditable(true); // Không cho sửa
            JLabel lblMaNhanVien = new JLabel("Mã nhân viên:");
            JTextField txtMaNhanVien = new JTextField(maNhanVien);
            txtMaNhanVien.setEditable(true); // Không cho sửa
            JLabel lblTrangThai = new JLabel("Trạng thái:");
            JTextField txtTrangThai = new JTextField(TrangThai);
            txtTrangThai.setEditable(false);

            inputPanel.add(lblMpm);
            inputPanel.add(txtMpm);
            inputPanel.add(lblNgayMuon);
            inputPanel.add(txtNgayMuon);
            inputPanel.add(lblNgayTraDuKien);
            inputPanel.add(txtNgayTraDuKien);
            inputPanel.add(lblMaDocGia);
            inputPanel.add(txtMaDocGia);
            inputPanel.add(lblMaNhanVien);
            inputPanel.add(txtMaNhanVien);
            inputPanel.add(lblTrangThai);
            inputPanel.add(txtTrangThai);

            // Panel chứa nút
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            JButton btnConfirm = new JButton("Xác nhận");
            JButton btnCancel = new JButton("Hủy");

            // Style cho nút
            styleButton(btnConfirm, new Color(0, 120, 215));
            styleButton(btnCancel, new Color(100, 100, 100));

            buttonPanel.add(btnConfirm);
            buttonPanel.add(btnCancel);

            mainPanel.add(inputPanel, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            dialog.add(mainPanel);

            // Xử lý nút Xác nhận
            btnConfirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Lấy dữ liệu từ các trường
                    String newMpmStr = txtMpm.getText();
                    String newMaDocGia = txtMaDocGia.getText();
                    String newMaNhanVien = txtMaNhanVien.getText();

                    // Lấy dữ liệu ngày từ hàng được chọn và chuyển thành java.sql.Date
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.sql.Date ngayMuonDate = null;
                    java.sql.Date ngayTraDuKienDate = null;
                    try {
                        java.util.Date utilDate = sdf.parse(pm.getTable().getValueAt(slt, 1).toString());
                        ngayMuonDate = new java.sql.Date(utilDate.getTime());
                        utilDate = sdf.parse(pm.getTable().getValueAt(slt, 2).toString());
                        ngayTraDuKienDate = new java.sql.Date(utilDate.getTime());
                    } catch (ParseException | java.text.ParseException ex) {
                        ex.printStackTrace();
                    }

                    // Chuyển đổi MaPhieuMuon thành int
                    int newMpm;
                    try {
                        newMpm = Integer.parseInt(newMpmStr);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(dialog, "Mã phiếu mượn không hợp lệ", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Tạo đối tượng PhieuMuonDTO để cập nhật
                    PhieuMuonDTO updatePm = new PhieuMuonDTO(newMpm, ngayMuonDate,
                            ngayTraDuKienDate, tmptrangthai, newMaDocGia, newMaNhanVien, true);
                    boolean kq = pmbus.suaPhieuMuon(updatePm);
                    if (kq) {
                        JOptionPane.showMessageDialog(dialog, "Cập nhật thành công", "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
                        pm.setListpm(pmbus.getList());
                        pm.loadTableData();
                        if (slt >= 0 && slt < pm.getTable().getRowCount()) {
                            pm.getTable().setRowSelectionInterval(slt, slt);
                        }
                        manggoc = pm.getListpm();
                        mangtmp = pm.getListpm();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Cập nhật thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    dialog.dispose();
                }
            });

            // Xử lý nút Hủy
            btnCancel.addActionListener(e1 -> dialog.dispose());

            dialog.setVisible(true);
            System.out.println("Đã nhấn vào nút Sửa");
        } else if (e.getSource() == pm.getBtnXoa()) {
            if (!new NhomQuyenDAO().isAccessable(NguoiDungDangNhap.getInstance().getMaNhomQuyen(), 6, 4)) {
                JOptionPane.showMessageDialog(null, "Bạn không có quyền xóa phiếu mượn", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
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
            if (!new NhomQuyenDAO().isAccessable(NguoiDungDangNhap.getInstance().getMaNhomQuyen(), 6, 2)) {
                JOptionPane.showMessageDialog(null, "Bạn không có quyền thêm phiếu mượn", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            SachDAO sdao = new SachDAO();
            List<SachDTO> s = sdao.selectAll();
            if (s == null) {
                s = new ArrayList<>();
            }
            DauSachBUS dsbus = new DauSachBUS();
            List<DauSachDTO> ds = dsbus.getList();
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

            // Khởi tạo Timer với delay và null listener (sẽ thêm sau)
            searchDG = new Timer(300, null); // 300ms delay
            searchDG.setRepeats(false); // Chỉ chạy một lần mỗi khi kích hoạt

            txtfSearchDG.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    searchDG.stop(); // Dừng Timer trước đó (nếu có)

                    // Xóa tất cả listener cũ
                    for (ActionListener al : searchDG.getActionListeners()) {
                        searchDG.removeActionListener(al);
                    }

                    // Thêm listener mới
                    searchDG.addActionListener(evt -> {
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

                    searchDG.restart(); // Khởi động lại Timer
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
                // Tạo panel chính cho mỗi dòng sách
                JPanel bookRowContainer = new JPanel(new BorderLayout());
                bookRowContainer.setBackground(new Color(245, 245, 245));
                bookRowContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

                // Panel chứa ô nhập và nút xóa
                JPanel Ainput = new JPanel(new GridBagLayout());
                Ainput.setBackground(new Color(245, 245, 245));
                Ainput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(0, 5, 0, 5);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                JTextField txtMaSach = new JTextField();
                txtMaSach.setName("txtMaSach");
                txtMaSach.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                txtMaSach.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(180, 180, 180)),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                txtMaSach.setPreferredSize(new Dimension(180, 30));
                txtMaSach.setMaximumSize(new Dimension(180, 30));

                // Panel chứa kết quả tìm kiếm (sẽ được thêm riêng)
                JPanel resultPanel = new JPanel(new BorderLayout());
                resultPanel.setBackground(new Color(245, 245, 245));
                resultPanel.setVisible(false);
                resultPanel.setPreferredSize(new Dimension(180, 100));

                DefaultListModel<DauSachDTO> listModel = new DefaultListModel<>();
                JList<DauSachDTO> resultList = new JList<>(listModel);
                resultList.setFont(new Font("Segoe UI", Font.PLAIN, 14));

                resultList.setCellRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                            boolean isSelected, boolean cellHasFocus) {
                        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        if (value instanceof DauSachDTO) {
                            DauSachDTO ds = (DauSachDTO) value;
                            setText(ds.getMaDauSach() + " - " + ds.getTenDauSach());
                        }
                        return this;
                    }
                });

                JScrollPane resultScrollPane = new JScrollPane(resultList);
                resultPanel.add(resultScrollPane, BorderLayout.CENTER);

                // Tạo Timer cho tìm kiếm
                Timer searchTimer = new Timer(300, null);
                searchTimer.setRepeats(false);

                txtMaSach.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        searchTimer.stop();
                        for (ActionListener al : searchTimer.getActionListeners()) {
                            searchTimer.removeActionListener(al);
                        }

                        searchTimer.addActionListener(evt -> {
                            String searchText = txtMaSach.getText().trim().toLowerCase();
                            listModel.clear();

                            if (!searchText.isEmpty()) {
                                List<DauSachDTO> allDauSach = new DauSachBUS().getListDauSach();

                                for (DauSachDTO ds : allDauSach) {
                                    if (ds.getMaDauSach().toLowerCase().contains(searchText) ||
                                            ds.getTenDauSach().toLowerCase().contains(searchText)) {
                                        listModel.addElement(ds);
                                    }
                                }

                                resultPanel.setVisible(!listModel.isEmpty());
                                bookRowContainer.revalidate();
                                bookRowContainer.repaint();
                            } else {
                                resultPanel.setVisible(false);
                            }
                        });

                        searchTimer.restart();
                    }
                });

                resultList.addListSelectionListener(e1 -> {
                    if (!e1.getValueIsAdjusting() && resultList.getSelectedValue() != null) {
                        DauSachDTO selectedDS = resultList.getSelectedValue();
                        txtMaSach.setText(selectedDS.getMaDauSach());
                        resultPanel.setVisible(false);
                    }
                });

                JButton btnRemove = new JButton("Xóa");
                styleButton(btnRemove, new Color(200, 50, 50));
                btnRemove.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                btnRemove.setPreferredSize(new Dimension(50, 30));
                btnRemove.setMaximumSize(new Dimension(50, 30));

                gbc.gridx = 0;
                gbc.weightx = 0.85;
                Ainput.add(txtMaSach, gbc);

                gbc.gridx = 1;
                gbc.weightx = 0.15;
                Ainput.add(btnRemove, gbc);

                // Thêm các panel vào container chính
                bookRowContainer.add(Ainput, BorderLayout.NORTH);
                bookRowContainer.add(resultPanel, BorderLayout.CENTER);

                btnRemove.addActionListener(e1 -> {
                    booksInputPanel.remove(bookRowContainer);
                    booksInputPanel.remove(booksInputPanel.getComponentCount() - 1); // Xóa strut
                    booksInputPanel.revalidate();
                    booksInputPanel.repaint();
                });

                booksInputPanel.add(bookRowContainer);
                booksInputPanel.add(Box.createVerticalStrut(5));
                booksInputPanel.revalidate();
                booksInputPanel.repaint();
            };

            // Thêm 5 dòng mặc định
            for (int i = 0; i < 5; i++) {
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
                        JTextField txt = findTextField((Container) comp);
                        if (txt != null && !txt.getText().trim().isEmpty()) {
                            maSachList.add(txt.getText().trim());
                        }
                    }
                }

                // Log để kiểm tra
                System.out.println("maSachList: " + maSachList);

                if (maSachList.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng nhập ít nhất một mã sách", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                java.sql.Date ngaymuon = java.sql.Date.valueOf(tmpday);
                java.sql.Date ngaytradukien = java.sql.Date.valueOf(tmpday.plusDays(15));
                PhieuMuonDTO newPM = new PhieuMuonDTO(mpm, ngaymuon, ngaytradukien,
                        0, selectedDG.getMaDocGia(), "NV001", true);
                if (pmbus.themPhieuMuon(newPM)) {
                    for (String maDauSach : maSachList) {
                        System.out.println("abcabcabc");
                        pmbus.themChiTietPhieuMuon(newPM.getMaPhieuMuon(), maDauSach);
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

    public void hienThiChiTietTable(String mpm) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Chi tiết phiếu mượn");
        dialog.setSize(600, 500);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(null);

        PhieuMuonDTO phieuMuon = null;
        for (PhieuMuonDTO pm : pmbus.getList()) {
            if (pm.getMaPhieuMuon() == Integer.parseInt(mpm)) {
                phieuMuon = pm;
                break;
            }
        }

        if (phieuMuon != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Panel chính
            JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            mainPanel.setBackground(new Color(245, 245, 245));

            // Panel thông tin phiếu mượn
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(new Color(245, 245, 245));
            infoPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                    "Thông tin phiếu mượn",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Segoe UI", Font.BOLD, 14)));

            // Thêm các trường thông tin
            infoPanel.add(createInfoRow("Mã phiếu mượn:", String.valueOf(phieuMuon.getMaPhieuMuon())));
            infoPanel.add(Box.createVerticalStrut(10));
            String thoiGian = phieuMuon.getNgayMuon() != null ? sdf.format(phieuMuon.getNgayMuon())
                    : "Không có thời gian";
            infoPanel.add(createInfoRow("Thời gian:", thoiGian));
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(
                    createInfoRow("Mã nhân viên:",
                            phieuMuon.getMaNhanVien() != null ? phieuMuon.getMaNhanVien() : "Không có"));
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(createInfoRow("Mã độc giả:", String.valueOf(phieuMuon.getMaDocGia())));

            // Panel danh sách chi tiết phiếu mượn
            JPanel booksPanel = new JPanel(new BorderLayout());
            booksPanel.setBackground(new Color(245, 245, 245));
            booksPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                    "Chi tiết phiếu mượn",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Segoe UI", Font.BOLD, 14)));

            // Tạo JTable cho chi tiết phiếu mượn
            DefaultTableModel booksModel = new DefaultTableModel(
                    new Object[] { "Mã sách", "Tên sách" }, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            // Tạo Map từ SachDTO và DauSachDTO để tra cứu nhanh
            Map<String, SachDTO> sachMap = new HashMap<>();
            for (SachDTO s : new SachDAO().selectAll()) {
                sachMap.put(s.getMaSach(), s);
            }
            Map<String, DauSachDTO> dauSachMap = new HashMap<>();
            for (DauSachDTO ds : new DauSachBUS().getListDauSach()) {
                dauSachMap.put(ds.getMaDauSach(), ds);
            }

            // Lấy danh sách chi tiết từ BUS
            List<CTPhieuMuonDTO> chiTietList = pmbus.getCTPhieuMuon();
            try {
                int maPhieuMuon = Integer.parseInt(mpm);
                for (CTPhieuMuonDTO ct : chiTietList) {
                    String tendausach = "Không tìm thấy";
                    if (ct.getMaPhieuMuon() == maPhieuMuon) {
                        SachDTO sach = sachMap.get(ct.getMaSach());
                        if (sach != null) {
                            DauSachDTO dauSach = dauSachMap.get(sach.getMaDauSach());
                            if (dauSach != null) {
                                tendausach = dauSach.getTenDauSach();
                            }
                        }
                        booksModel.addRow(new Object[] { ct.getMaSach(), tendausach });
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Mã phiếu mượn không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                dialog.dispose();
                return;
            }

            JTable booksTable = new JTable(booksModel);
            booksTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            booksTable.setRowHeight(25);
            booksTable.setGridColor(new Color(200, 200, 200));
            booksTable.setShowGrid(true);
            booksTable.setBackground(new Color(245, 245, 245));
            booksTable.setSelectionBackground(new Color(0, 120, 215));
            booksTable.setSelectionForeground(Color.WHITE);

            JScrollPane booksScrollPane = new JScrollPane(booksTable);
            booksScrollPane.setBorder(BorderFactory.createEmptyBorder());
            booksPanel.add(booksScrollPane, BorderLayout.CENTER);

            // Thêm các panel vào mainPanel
            mainPanel.add(infoPanel, BorderLayout.NORTH);
            mainPanel.add(booksPanel, BorderLayout.CENTER);

            // Panel chứa nút Đóng
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            buttonPanel.setBackground(new Color(245, 245, 245));
            JButton closeButton = new JButton("Đóng");
            closeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            closeButton.setBackground(new Color(0, 120, 215));
            closeButton.setForeground(Color.WHITE);
            closeButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            closeButton.addActionListener(e -> dialog.dispose());
            buttonPanel.add(closeButton);

            // Thêm các panel vào dialog
            dialog.add(mainPanel, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy chi tiết phiếu mượn", "Lỗi", JOptionPane.ERROR_MESSAGE);
            dialog.dispose();
        }

        dialog.setVisible(true);
    }

    // Hàm hỗ trợ tạo hàng thông tin
    private JPanel createInfoRow(String label, String value) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowPanel.setBackground(new Color(245, 245, 245));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setPreferredSize(new Dimension(120, 25));
        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rowPanel.add(lbl);
        rowPanel.add(val);
        return rowPanel;
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

    // Phương thức hỗ trợ
    private JTextField findTextField(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JTextField && "txtMaSach".equals(comp.getName())) {
                return (JTextField) comp;
            }
            if (comp instanceof Container) {
                JTextField found = findTextField((Container) comp);
                if (found != null)
                    return found;
            }
        }
        return null;
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

    public void performSearch() {
        String type = (String) pm.getCbLuaChonTK().getSelectedItem();
        String txt = pm.getTxtTimKiem().getText().trim();
        System.out.println("dang tim kiem" + txt + "loai " + type);
        switch (type) {
            case "Mã phiếu mượn":
                timMPM(txt);
                break;

            case "Ngày mượn":
                timNM(txt);
                break;

            case "Ngày trả dự kiến":
                timNTDK(txt);
                break;

            case "Trạng thái":
                timTrangThai(txt);
                break;

            case "Mã độc giả":
                timMDG(txt);
                break;

            case "Mã nhân viên":
                timMNV(txt);
                break;

            default:
                break;
        }
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
}