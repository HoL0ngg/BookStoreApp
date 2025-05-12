package com.bookstore.controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Timer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.bookstore.BUS.DocGiaBUS;
import com.bookstore.BUS.PhieuMuonBUS;
import com.bookstore.BUS.PhieuTraBUS;
import com.bookstore.DTO.DocGiaDTO;
import com.bookstore.DTO.PhieuMuonDTO;
import com.bookstore.DTO.PhieuTraDTO;
import com.bookstore.DTO.SachDTO;
import com.bookstore.views.Panel.PhieuTra;
import com.bookstore.dao.PhieuTraDAO;
import com.bookstore.dao.SachDAO;
import com.bookstore.DTO.TaiKhoanDTO;
import com.bookstore.DTO.CTPhieuMuonDTO;
import com.bookstore.DTO.CTPhieuTraDTO;
import com.bookstore.DTO.DauSachDTO;
import com.bookstore.BUS.DauSachBUS;

public class PhieuTraController implements ItemListener, ActionListener {
    private PhieuTra pt;
    private PhieuTraDAO ptdao = new PhieuTraDAO();
    private PhieuTraBUS ptbus = new PhieuTraBUS();
    @SuppressWarnings("unused")
    private TaiKhoanDTO tkDTO = new TaiKhoanDTO();
    private Comparator<PhieuTraDTO> comparator = (pt1, pt2) -> 0;
    private List<PhieuTraDTO> manggoc;
    private List<PhieuTraDTO> mangtmp;
    private boolean isAscending = true;
    private Timer searchPhieuMuon;

    // constructor
    public PhieuTraController(PhieuTra pt) {
        this.pt = pt;
        manggoc = pt.getListpt();
        mangtmp = pt.getListpt();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pt.getBtnReverse()) {
            if (isAscending) {
                pt.getBtnReverse().setIcon(pt.upIcon);
                Collections.sort(mangtmp, comparator);
            } else {
                pt.getBtnReverse().setIcon(pt.downIcon);
                Collections.sort(mangtmp, comparator.reversed());
            }
            isAscending = !isAscending;
            pt.updateTable(mangtmp);
        } else if (e.getSource() == pt.getBtnSua()) {
            int slt = pt.getTable().getSelectedRow();
            if (slt == -1) {
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn phiếu muốn sửa", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Lấy MaPhieuTra từ cột 1 (cột 0 trong JTable)
            String mptStr = pt.getTable().getValueAt(slt, 0).toString();
            int mpt;
            try {
                mpt = Integer.parseInt(mptStr.trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Mã phiếu trả không hợp lệ", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Giả sử các cột khác trong bảng lần lượt là: MaPhieuTra, NgayTra, MaNhanVien,
            // MaDocGia, MaPhieuMuon, Status
            String strNgayTra = pt.getTable().getValueAt(slt, 1).toString();
            String mnv = pt.getTable().getValueAt(slt, 2).toString();
            String mdg = pt.getTable().getValueAt(slt, 3).toString();
            String mpmStr = pt.getTable().getValueAt(slt, 4).toString();
            int mpm;
            try {
                mpm = Integer.parseInt(mpmStr.trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Mã phiếu mượn không hợp lệ", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo dialog chỉnh sửa
            JDialog dialog = new JDialog((JFrame) null, "Sửa phiếu trả", true);
            dialog.setSize(500, 400); // Tăng kích thước dialog
            dialog.setLayout(new BorderLayout());
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(null);

            // Panel chính
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            mainPanel.setBackground(new Color(245, 245, 245));

            // Panel chứa các trường nhập liệu
            JPanel inputPanel = new JPanel(new GridBagLayout());
            inputPanel.setBackground(new Color(245, 245, 245));
            inputPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                    "Thông tin phiếu trả",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Segoe UI", Font.BOLD, 14)));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10); // Khoảng cách giữa các thành phần
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;

            // Các trường nhập liệu
            JLabel lblMpt = new JLabel("Mã phiếu trả:");
            lblMpt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            JTextField txtMpt = new JTextField(String.valueOf(mpt));
            txtMpt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            txtMpt.setEditable(false);
            txtMpt.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180)),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));

            JLabel lblNgayTra = new JLabel("Ngày trả (yyyy-MM-dd):");
            lblNgayTra.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            JTextField txtNgayTra = new JTextField(strNgayTra);
            txtNgayTra.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            txtNgayTra.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180)),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));

            JLabel lblMnv = new JLabel("Mã nhân viên:");
            lblMnv.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            JTextField txtMnv = new JTextField(mnv);
            txtMnv.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            txtMnv.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180)),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));

            JLabel lblMdg = new JLabel("Mã độc giả:");
            lblMdg.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            JTextField txtMdg = new JTextField(mdg);
            txtMdg.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            txtMdg.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180)),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));

            JLabel lblMpm = new JLabel("Mã phiếu mượn:");
            lblMpm.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            JTextField txtMpm = new JTextField(String.valueOf(mpm));
            txtMpm.setEditable(false);
            txtMpm.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            txtMpm.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180)),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));

            // Thêm các trường vào inputPanel
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0.3;
            inputPanel.add(lblMpt, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            inputPanel.add(txtMpt, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 0.3;
            inputPanel.add(lblNgayTra, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            inputPanel.add(txtNgayTra, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.weightx = 0.3;
            inputPanel.add(lblMnv, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            inputPanel.add(txtMnv, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.weightx = 0.3;
            inputPanel.add(lblMdg, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            inputPanel.add(txtMdg, gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.weightx = 0.3;
            inputPanel.add(lblMpm, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            inputPanel.add(txtMpm, gbc);

            // Panel chứa nút
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            buttonPanel.setBackground(new Color(245, 245, 245));
            JButton btnConfirm = new JButton("Xác nhận");
            JButton btnCancel = new JButton("Hủy");

            // Style cho nút
            btnConfirm.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnConfirm.setBackground(new Color(0, 120, 215));
            btnConfirm.setForeground(Color.WHITE);
            btnConfirm.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            btnConfirm.setPreferredSize(new Dimension(100, 35));

            btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnCancel.setBackground(new Color(100, 100, 100));
            btnCancel.setForeground(Color.WHITE);
            btnCancel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            btnCancel.setPreferredSize(new Dimension(100, 35));

            buttonPanel.add(btnConfirm);
            buttonPanel.add(btnCancel);

            mainPanel.add(inputPanel, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            dialog.add(mainPanel);

            // Xử lý nút Xác nhận
            btnConfirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Lấy dữ liệu từ các trường nhập liệu
                    int newMpt = Integer.parseInt(txtMpt.getText().trim());
                    String newNgayTraStr = txtNgayTra.getText().trim();
                    String newMnv = txtMnv.getText().trim();
                    String newMdg = txtMdg.getText().trim();
                    int newMpm;
                    try {
                        newMpm = Integer.parseInt(txtMpm.getText().trim());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(dialog, "Mã phiếu mượn phải là số nguyên", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Kiểm tra và chuyển đổi ngày trả
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date ngayTra = null;
                    try {
                        java.util.Date utilDate = sdf.parse(newNgayTraStr);
                        ngayTra = new Date(utilDate.getTime());
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(dialog, "Ngày không hợp lệ (định dạng yyyy-MM-dd)", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Tạo đối tượng PhieuTraDTO để cập nhật
                    PhieuTraDTO updatePt = new PhieuTraDTO(newMpt, ngayTra, newMnv, newMdg, newMpm, 1);
                    boolean kq = ptbus.suapt(updatePt);
                    if (kq) {
                        JOptionPane.showMessageDialog(dialog, "Cập nhật thành công", "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
                        pt.setListpt(ptdao.layDanhSachPhieuTra());
                        pt.loadTableData();
                        // Giữ dòng được chọn
                        if (slt >= 0 && slt < pt.getTable().getRowCount()) {
                            pt.getTable().setRowSelectionInterval(slt, slt);
                        }
                        manggoc = pt.getListpt();
                        mangtmp = pt.getListpt();
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
        } else if (e.getSource() == pt.getBtnThem()) {
            System.out.println("Đã nhấn vào nút Thêm");
            JDialog dialog = new JDialog((JFrame) null, "Thêm Phiếu Trả", true);
            dialog.setSize(900, 600);
            dialog.setLayout(new BorderLayout());
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(null);

            // Panel chính chia làm 2 phần
            JPanel mainContainer = new JPanel(new GridLayout(1, 2, 15, 0));
            mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            mainContainer.setBackground(new Color(245, 245, 245));

            // ========= PHẦN BÊN TRÁI (THÔNG TIN PHIẾU TRẢ VÀ TÌM KIẾM PHIẾU MƯỢN)
            JPanel leftPanel = new JPanel(new BorderLayout(0, 10));
            leftPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                    "Thông tin phiếu trả",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Segoe UI", Font.BOLD, 14)));
            leftPanel.setBackground(new Color(245, 245, 245));

            // Panel chứa các trường nhập liệu thông tin phiếu trả
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(new Color(245, 245, 245));

            // Các trường nhập liệu
            JPanel row1 = createInputRow("Mã phiếu trả:", new JTextField());
            JTextField txtfMaPhieuTra = (JTextField) row1.getComponent(1);
            txtfMaPhieuTra.setEditable(false);

            JPanel row2 = createInputRow("Ngày trả (yyyy-MM-dd):", new JTextField());
            JTextField txtfNgayTra = (JTextField) row2.getComponent(1);
            txtfNgayTra.setEditable(false);

            JPanel row3 = createInputRow("Mã nhân viên:", new JTextField());
            JTextField txtfMaNV = (JTextField) row3.getComponent(1);
            txtfMaNV.setEditable(false);

            // Panel cho phần chọn phiếu mượn với JList
            JPanel row4 = new JPanel(new BorderLayout(0, 5));
            row4.setBackground(new Color(245, 245, 245));
            JLabel phieuMuonLabel = new JLabel("Chọn phiếu mượn:");
            phieuMuonLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            row4.add(phieuMuonLabel, BorderLayout.NORTH);

            JTextField txtfSearchPhieuMuon = new JTextField();
            txtfSearchPhieuMuon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            txtfSearchPhieuMuon.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180)),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));

            JList<PhieuMuonDTO> listPhieuMuon = new JList<>();
            listPhieuMuon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            listPhieuMuon.setVisibleRowCount(5);
            listPhieuMuon.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
            JScrollPane phieuMuonScrollPane = new JScrollPane(listPhieuMuon);
            phieuMuonScrollPane.setPreferredSize(new Dimension(0, 100));

            JPanel phieuMuonInputPanel = new JPanel(new BorderLayout(0, 5));
            phieuMuonInputPanel.setBackground(new Color(245, 245, 245));
            phieuMuonInputPanel.add(txtfSearchPhieuMuon, BorderLayout.NORTH);
            phieuMuonInputPanel.add(phieuMuonScrollPane, BorderLayout.CENTER);
            row4.add(phieuMuonInputPanel, BorderLayout.CENTER);

            // Thêm các row vào infoPanel
            infoPanel.add(row1);
            infoPanel.add(Box.createVerticalStrut(15));
            infoPanel.add(row2);
            infoPanel.add(Box.createVerticalStrut(15));
            infoPanel.add(row3);
            infoPanel.add(Box.createVerticalStrut(15));
            infoPanel.add(row4);

            // Đảm bảo infoPanel được thêm vào leftPanel
            leftPanel.removeAll(); // Xóa bất kỳ thành phần cũ nào
            leftPanel.add(infoPanel, BorderLayout.CENTER);
            leftPanel.revalidate();
            leftPanel.repaint();

            // Ngày trả hiện tại
            LocalDate tmpday = LocalDate.now();
            DateTimeFormatter fomatdaytime = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fomatedday = tmpday.format(fomatdaytime);
            txtfNgayTra.setText(fomatedday);

            // Mã nhân viên hiện tại
            txtfMaNV.setText("NV001");

            // Mã phiếu trả mới
            String mptNew = "PT001";
            int maxNum = 0;
            for (PhieuTraDTO i : pt.getListpt()) {
                int num = i.getMaPhieuTra();
                if (num > maxNum) {
                    maxNum = num;
                }
            }
            mptNew = String.format("PT%03d", maxNum + 1);
            txtfMaPhieuTra.setText(mptNew);

            // Lấy danh sách phiếu mượn từ PhieuMuonBUS
            PhieuMuonBUS phieuMuonBus = new PhieuMuonBUS();
            List<PhieuMuonDTO> danhSachPhieuMuon = phieuMuonBus.getList();
            System.out.println("Tổng số phiếu mượn: " + danhSachPhieuMuon.size());

            // Renderer cho JList
            listPhieuMuon.setCellRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof PhieuMuonDTO) {
                        PhieuMuonDTO pm = (PhieuMuonDTO) value;
                        setText(pm.getMaPhieuMuon() + " - " + pm.getMaDocGia());
                    }
                    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    setBackground(isSelected ? new Color(0, 120, 215) : Color.WHITE);
                    setForeground(isSelected ? Color.WHITE : Color.BLACK);
                    return this;
                }
            });

            // Hiển thị toàn bộ danh sách phiếu mượn ngay khi mở dialog
            DefaultListModel<PhieuMuonDTO> initialModel = new DefaultListModel<>();
            for (PhieuMuonDTO pm : danhSachPhieuMuon) {
                initialModel.addElement(pm);
            }
            listPhieuMuon.setModel(initialModel);
            phieuMuonScrollPane.setVisible(true);
            phieuMuonInputPanel.revalidate();
            phieuMuonInputPanel.repaint();

            // Timer cho tìm kiếm phiếu mượn
            searchPhieuMuon = new Timer();

            txtfSearchPhieuMuon.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    // Hủy Timer cũ nếu tồn tại
                    if (searchPhieuMuon != null) {
                        searchPhieuMuon.cancel();
                        searchPhieuMuon.purge();
                    }
                    // Tạo Timer mới
                    searchPhieuMuon = new Timer();
                    searchPhieuMuon.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            SwingUtilities.invokeLater(() -> {
                                String searchText = txtfSearchPhieuMuon.getText().trim().toLowerCase();
                                DefaultListModel<PhieuMuonDTO> listModel = new DefaultListModel<>();
                                String maPhieuMuon = null;
                                if (searchText.isEmpty()) {
                                    for (PhieuMuonDTO pm : danhSachPhieuMuon) {
                                        listModel.addElement(pm);
                                    }
                                } else {
                                    for (PhieuMuonDTO pm : danhSachPhieuMuon) {
                                        maPhieuMuon = String.valueOf(pm.getMaPhieuMuon()).toLowerCase();
                                        String mdg = pm.getMaDocGia() != null
                                                ? pm.getMaDocGia().toString().toLowerCase()
                                                : "";
                                        if (maPhieuMuon.contains(searchText) || mdg.contains(searchText)) {
                                            listModel.addElement(pm);
                                        }
                                    }
                                }
                                // Không set maPhieuMuon trực tiếp vào txtfSearchPhieuMuon ở đây
                                // Vì maPhieuMuon chỉ là biến tạm, sẽ set khi chọn từ list
                                listPhieuMuon.setModel(listModel);
                                phieuMuonScrollPane.setVisible(!listModel.isEmpty());
                                phieuMuonInputPanel.revalidate();
                                phieuMuonInputPanel.repaint();
                                System.out.println("Số phiếu mượn tìm thấy: " + listModel.getSize());
                            });
                        }
                    }, 300);
                }
            });

            listPhieuMuon.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) { // Đảm bảo sự kiện chỉ xử lý khi chọn xong
                        PhieuMuonDTO selectedPhieuMuon = listPhieuMuon.getSelectedValue();
                        if (selectedPhieuMuon != null) {
                            String maPhieuMuon = String.valueOf(selectedPhieuMuon.getMaPhieuMuon());
                            txtfSearchPhieuMuon.setText(maPhieuMuon); // Set mã phiếu mượn vào text field
                        }
                    }
                }
            });
            // ========= PHẦN BÊN PHẢI (CHI TIẾT PHIẾU MƯỢN VÀ DANH SÁCH SÁCH TRẢ) =========
            JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
            rightPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                    "Danh sách sách trả",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Segoe UI", Font.BOLD, 14)));
            rightPanel.setBackground(new Color(245, 245, 245));

            // Thêm JScrollPane chứa danh sách sách trả ban đầu (trống)
            JScrollPane booksScrollPane = createChiTietPhieuMuonPanel(null); // Trả về JScrollPane
            rightPanel.add(booksScrollPane, BorderLayout.CENTER);

            listPhieuMuon.addListSelectionListener(e1 -> {
                if (!e1.getValueIsAdjusting()) {
                    PhieuMuonDTO selectedPhieuMuon = listPhieuMuon.getSelectedValue();
                    rightPanel.removeAll();
                    rightPanel.add(createChiTietPhieuMuonPanel(selectedPhieuMuon), BorderLayout.CENTER);
                    rightPanel.revalidate();
                    rightPanel.repaint();
                }
            });

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

            confirm.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    PhieuMuonDTO selectedPhieuMuon = listPhieuMuon.getSelectedValue();
                    if (selectedPhieuMuon == null) {
                        JOptionPane.showMessageDialog(dialog, "Vui lòng chọn phiếu mượn", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.sql.Date date = null;
                    try {
                        java.util.Date utilDate = sdf.parse(txtfNgayTra.getText().trim());
                        date = new java.sql.Date(utilDate.getTime());
                    } catch (ParseException e1) {
                        JOptionPane.showMessageDialog(dialog, "Ngày không hợp lệ (định dạng yyyy-MM-dd)", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int maPhieuTra;
                    try {
                        maPhieuTra = Integer.parseInt(txtfMaPhieuTra.getText().replace("PT", "").trim());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(dialog, "Mã phiếu trả không hợp lệ", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Debug cấu trúc của rightPanel
                    System.out.println("Số thành phần trong rightPanel: " + rightPanel.getComponentCount());
                    for (int i = 0; i < rightPanel.getComponentCount(); i++) {
                        Component comp = rightPanel.getComponent(i);
                        System.out.println("Thành phần " + i + ": " + comp.getClass().getName());
                    }

                    // Lấy booksInputPanel từ rightPanel (thông qua JScrollPane)
                    Component rightPanelComponent = null;
                    if (rightPanel.getComponentCount() > 0) {
                        rightPanelComponent = rightPanel.getComponent(0); // Lấy thành phần đầu tiên trong rightPanel
                    } else {
                        JOptionPane.showMessageDialog(dialog, "rightPanel không chứa thành phần nào", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!(rightPanelComponent instanceof JScrollPane)) {
                        JOptionPane.showMessageDialog(dialog,
                                "Không tìm thấy danh sách sách trả, thành phần là: "
                                        + rightPanelComponent.getClass().getName(),
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    JScrollPane booksScrollPane = (JScrollPane) rightPanelComponent;
                    Component viewportComponent = booksScrollPane.getViewport().getView();
                    if (!(viewportComponent instanceof JPanel)) {
                        JOptionPane.showMessageDialog(dialog, "Không tìm thấy danh sách sách trả trong JScrollPane",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    JPanel booksInputPanel = (JPanel) viewportComponent;

                    // Lấy danh sách sách được chọn từ booksInputPanel
                    List<CTPhieuTraDTO> chiTietList = new ArrayList<>();
                    Component[] components = booksInputPanel.getComponents();
                    System.out.println("Số thành phần trong booksInputPanel: " + components.length); // Debug
                    for (Component comp : components) {
                        if (comp instanceof JPanel) {
                            JPanel row = (JPanel) comp;
                            Component[] rowComponents = row.getComponents();
                            System.out.println("Số thành phần trong row: " + rowComponents.length); // Debug
                            if (rowComponents.length >= 3 &&
                                    rowComponents[0] instanceof JTextField &&
                                    rowComponents[2] instanceof JCheckBox) {
                                JTextField txtMaSach = (JTextField) rowComponents[0];
                                JCheckBox checkBox = (JCheckBox) rowComponents[2];
                                System.out.println("Mã sách: " + txtMaSach.getText() + ", Checkbox selected: "
                                        + checkBox.isSelected()); // Debug
                                if (checkBox.isSelected() && !txtMaSach.getText().trim().isEmpty()) {
                                    chiTietList.add(new CTPhieuTraDTO(0, maPhieuTra, txtMaSach.getText().trim(), 0));
                                }
                            }
                        }
                    }

                    if (chiTietList.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, "Vui lòng chọn ít nhất một sách để trả", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    DocGiaDTO selectedDocGia = null;
                    for (DocGiaDTO dg : new DocGiaBUS().getList()) {
                        if (dg.getMaDocGia().equals(selectedPhieuMuon.getMaDocGia())) {
                            selectedDocGia = dg;
                            break;
                        }
                    }
                    if (selectedDocGia == null) {
                        JOptionPane.showMessageDialog(dialog, "Không tìm thấy độc giả", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    PhieuTraDTO insertPT = new PhieuTraDTO(
                            maPhieuTra,
                            date,
                            txtfMaNV.getText().trim(),
                            selectedDocGia.getMaDocGia(),
                            selectedPhieuMuon.getMaPhieuMuon(),
                            1); // Trạng thái mặc định là 1 (Đang xử lý)

                    if (ptbus.thempt(insertPT, chiTietList)) {
                        pt.setListpt(ptdao.layDanhSachPhieuTra());
                        pt.loadTableData();
                        JOptionPane.showMessageDialog(dialog, "Thêm phiếu trả thành công", "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
                        manggoc = pt.getListpt();
                        mangtmp = pt.getListpt();
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Thêm phiếu trả thất bại", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            // Xử lý nút Hủy
            cancel.addActionListener(e1 -> dialog.dispose());
            dialog.setVisible(true);

        } else if (e.getSource() == pt.getBtnXoa()) {
            int slt = pt.getTable().getSelectedRow();
            if (slt == -1) {
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn phiếu muốn xóa", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            int mpt = Integer.parseInt(pt.getTable().getValueAt(slt, 0).toString());

            // Tạo JDialog xác nhận xóa
            JDialog dialog = new JDialog((JFrame) null, "Xác nhận xóa phiếu trả", true);
            dialog.setSize(400, 200);
            dialog.setLayout(new BorderLayout());
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(null);

            // Panel chính chứa thông báo
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            mainPanel.setBackground(new Color(245, 245, 245));

            // Thông báo
            JLabel messageLabel = new JLabel("Bạn muốn xóa phiếu trả " + mpt + "?", SwingConstants.CENTER);
            messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            mainPanel.add(messageLabel, BorderLayout.CENTER);

            // Panel chứa nút Xác nhận và Hủy
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            buttonPanel.setBackground(new Color(245, 245, 245));

            JButton confirmButton = new JButton("Xác nhận");
            JButton cancelButton = new JButton("Hủy");

            // Style cho nút
            styleButton(confirmButton, new Color(0, 120, 215));
            styleButton(cancelButton, new Color(100, 100, 100));
            confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

            buttonPanel.add(confirmButton);
            buttonPanel.add(cancelButton);

            // Thêm các panel vào dialog
            dialog.add(mainPanel, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);

            // Xử lý nút Xác nhận
            confirmButton.addActionListener(e1 -> {
                // Gọi BUS để xóa phiếu trả
                if (ptbus.xoapt(mpt)) {
                    // Cập nhật danh sách và bảng
                    pt.setListpt(ptdao.layDanhSachPhieuTra());
                    pt.loadTableData();
                    JOptionPane.showMessageDialog(dialog, "Xóa phiếu trả thành công", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    manggoc = pt.getListpt();
                    mangtmp = pt.getListpt();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Xóa phiếu trả thất bại", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
                dialog.dispose();
            });

            // Xử lý nút Hủy
            cancelButton.addActionListener(e1 -> dialog.dispose());

            dialog.setVisible(true);
        }
    }

    public void timMPT(String MaPT) {
        mangtmp = manggoc.stream()
                .filter(i -> String.valueOf(i.getMaPhieuTra()).contains(MaPT))
                .collect(Collectors.toList());
        pt.updateTable(mangtmp);
    }

    public void timNgayTra(String ngay) {
        if (ngay == null || ngay.trim().isEmpty()) {
            mangtmp = manggoc;
            pt.updateTable(mangtmp);
            return;
        }

        SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdfMonthDay = new SimpleDateFormat("MM-dd");
        SimpleDateFormat sdfYearMonth = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sdfFull = new SimpleDateFormat("yyyy-MM-dd");

        mangtmp = manggoc.stream()
                .filter(i -> {
                    Date itemDate = (Date) i.getNgayTra();
                    String day = sdfDay.format(itemDate);
                    String month = sdfMonth.format(itemDate);
                    String year = sdfYear.format(itemDate);
                    String monthDay = sdfMonthDay.format(itemDate);
                    String yearMonth = sdfYearMonth.format(itemDate);
                    String fullDate = sdfFull.format(itemDate);

                    // Phân tích định dạng của ngay
                    if (ngay.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        // Định dạng yyyy-MM-dd
                        return fullDate.equals(ngay);
                    } else if (ngay.matches("\\d{4}-\\d{2}")) {
                        // Định dạng yyyy-MM
                        return yearMonth.equals(ngay);
                    } else if (ngay.matches("\\d{2}-\\d{2}")) {
                        // Định dạng MM-dd
                        return monthDay.equals(ngay);
                    } else if (ngay.matches("\\d{4}")) {
                        // Định dạng yyyy
                        return year.equals(ngay);
                    } else if (ngay.matches("\\d{1,2}")) {
                        // Định dạng MM hoặc dd (ưu tiên tháng nếu <= 12, ngày nếu > 12)
                        int value = Integer.parseInt(ngay);
                        if (value <= 12) {
                            // Có thể là tháng hoặc ngày
                            return month.equals(String.format("%02d", value))
                                    || day.equals(String.format("%02d", value));
                        } else {
                            // Chỉ là ngày
                            return day.equals(String.format("%02d", value));
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
        pt.updateTable(mangtmp);
    }

    public void timMNV(String MaNV) {
        mangtmp = manggoc.stream()
                .filter(i -> i.getMaNV().contains(MaNV))
                .collect(Collectors.toList());
        pt.updateTable(mangtmp);
    }

    public void timMDG(String MaDocGia) {
        mangtmp = manggoc.stream()
                .filter(i -> i.getMaDocGia().contains(MaDocGia))
                .collect(Collectors.toList());
        pt.updateTable(mangtmp);
    }

    public void timMPM(String MaPhieuMuon) {
        mangtmp = manggoc.stream()
                .filter(i -> String.valueOf(i.getMaPhieuMuon()).contains(MaPhieuMuon))
                .collect(Collectors.toList());
        pt.updateTable(mangtmp);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            @SuppressWarnings("unchecked")
            JComboBox<String> cbb = (JComboBox<String>) e.getSource();
            String str = (String) cbb.getSelectedItem();
            if (str != null) {
                System.out.println("Đã nhận vào sắp xếp: " + str);
                switch (str) {
                    case "Tất cả":
                        comparator = Comparator.comparingInt(PhieuTraDTO::getMaPhieuTra);
                        break;
                    case "Mã phiếu trả":
                        comparator = Comparator.comparingInt(PhieuTraDTO::getMaPhieuTra);
                        break;
                    case "Ngày trả":
                        comparator = Comparator.comparing(PhieuTraDTO::getNgayTra);
                        break;
                    case "Mã nhân viên":
                        comparator = Comparator.comparing(PhieuTraDTO::getMaNV);
                        break;
                    case "Mã độc giả":
                        comparator = Comparator.comparing(PhieuTraDTO::getMaDocGia);
                        break;
                    case "Mã phiếu mượn":
                        comparator = Comparator.comparingInt(PhieuTraDTO::getMaPhieuMuon);
                        break;
                    default:
                        break;
                }
                Collections.sort(mangtmp, comparator);
                pt.updateTable(mangtmp);
            }
        }
    }

    private void styleButton(JButton button, Color color) {
        button.setPreferredSize(new Dimension(120, 40));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker()),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
    }

    public void hienThiChiTietTable(int mpt) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Chi tiết phiếu trả");
        dialog.setSize(800, 500);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(null);

        PhieuTraDTO phieuTra = null;
        for (PhieuTraDTO pt : pt.getListpt()) {
            if (pt.getMaPhieuTra() == mpt) {
                phieuTra = pt;
                break;
            }
        }

        if (phieuTra != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Panel chính
            JPanel mainPanel = new JPanel(new BorderLayout(10, 0)); // Khoảng cách ngang giữa 2 panel
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            mainPanel.setBackground(new Color(245, 245, 245));

            // Panel thông tin phiếu trả (bên trái)
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(new Color(245, 245, 245));
            infoPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                    "Thông tin phiếu trả",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Segoe UI", Font.BOLD, 14)));
            infoPanel.setPreferredSize(new Dimension(230, 0)); // Chiều rộng cố định 200px

            // Thêm các trường thông tin
            infoPanel.add(createInfoRow("Mã phiếu trả:", String.valueOf(phieuTra.getMaPhieuTra())));
            infoPanel.add(Box.createVerticalStrut(10));
            String ngayTra = phieuTra.getNgayTra() != null ? sdf.format(phieuTra.getNgayTra())
                    : "Không có ngày trả";
            infoPanel.add(createInfoRow("Ngày trả:", ngayTra));
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(
                    createInfoRow("Mã nhân viên:",
                            phieuTra.getMaNV() != null ? phieuTra.getMaNV() : "Không có"));
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(createInfoRow("Mã độc giả:", phieuTra.getMaDocGia()));
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(createInfoRow("Mã phiếu mượn:", String.valueOf(phieuTra.getMaPhieuMuon())));
            infoPanel.add(Box.createVerticalStrut(10));

            // Panel danh sách chi tiết phiếu trả (bên phải)
            JPanel booksPanel = new JPanel(new BorderLayout());
            booksPanel.setBackground(new Color(245, 245, 245));
            booksPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                    "Danh sách sách trả",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Segoe UI", Font.BOLD, 14)));

            // Tạo JTable cho chi tiết phiếu trả
            DefaultTableModel booksModel = new DefaultTableModel(
                    new Object[] { "Mã sách", "Tên sách" }, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            // Lấy danh sách chi tiết từ BUS
            List<CTPhieuTraDTO> chiTietList = ptbus.getListctpt(mpt);
            Map<String, DauSachDTO> dauSachMap = new HashMap<>();
            for (DauSachDTO ds : new DauSachBUS().getListDauSach()) {
                dauSachMap.put(ds.getMaDauSach(), ds);
            }
            Map<String, SachDTO> sachMap = new HashMap<>();
            for (SachDTO s : new SachDAO().selectAll()) {
                sachMap.put(s.getMaSach(), s);
            }
            for (CTPhieuTraDTO ct : chiTietList) {
                if (ct.getMaPhieuTra() == mpt) {
                    String tenDauSach = "Không tìm thấy";
                    SachDTO sach = sachMap.get(ct.getMaSach());
                    if (sach != null) {
                        DauSachDTO dauSach = dauSachMap.get(sach.getMaDauSach());
                        if (dauSach != null) {
                            tenDauSach = dauSach.getTenDauSach();
                        }
                    }
                    booksModel.addRow(new Object[] { ct.getMaSach(), tenDauSach });
                }
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

            // Thêm các panel vào mainPanel (bên trái và bên phải)
            mainPanel.add(infoPanel, BorderLayout.WEST);
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
            JOptionPane.showMessageDialog(null, "Không tìm thấy chi tiết phiếu trả", "Lỗi", JOptionPane.ERROR_MESSAGE);
            dialog.dispose();
        }

        dialog.setVisible(true);
    }

    private JPanel createInfoRow(String labelText, String value) {
        JPanel row = new JPanel(new GridLayout(1, 2, 10, 10));
        row.setBackground(new Color(245, 245, 245));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField textField = new JTextField(value);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setEditable(false);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        row.add(label);
        row.add(textField);
        return row;
    }

    private JScrollPane createChiTietPhieuMuonPanel(PhieuMuonDTO phieuMuon) {
        JPanel booksInputPanel = new JPanel();
        booksInputPanel.setLayout(new BoxLayout(booksInputPanel, BoxLayout.Y_AXIS));
        booksInputPanel.setBackground(new Color(245, 245, 245));

        if (phieuMuon == null) {
            JLabel emptyLabel = new JLabel("Vui lòng chọn phiếu mượn để xem chi tiết", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            booksInputPanel.add(emptyLabel);
        } else {
            PhieuMuonBUS pmbus = new PhieuMuonBUS();
            List<CTPhieuMuonDTO> chiTietList = (pmbus.getCTPhieuMuon() != null)
                    ? pmbus.getCTPhieuMuon().stream()
                            .filter(ct -> ct != null && ct.getMaPhieuMuon() == phieuMuon.getMaPhieuMuon())
                            .collect(Collectors.toList())
                    : new ArrayList<>();

            // Tạo map sách với kiểm tra null
            Map<String, SachDTO> sachMap = new HashMap<>();
            List<SachDTO> dsSach = new SachDAO().selectAll();
            if (dsSach != null) {
                for (SachDTO s : dsSach) {
                    if (s != null && s.getMaSach() != null) {
                        sachMap.put(s.getMaSach(), s);
                    }
                }
            }

            // Tạo map đầu sách với kiểm tra null
            Map<String, DauSachDTO> dauSachMap = new HashMap<>();
            List<DauSachDTO> dsDauSach = new DauSachBUS().getListDauSach();
            if (dsDauSach != null) {
                for (DauSachDTO ds : dsDauSach) {
                    if (ds != null && ds.getMaDauSach() != null) {
                        dauSachMap.put(ds.getMaDauSach(), ds);
                    }
                }
            }

            if (chiTietList.isEmpty()) {
                JLabel emptyLabel = new JLabel("Phiếu mượn này không có sách", SwingConstants.CENTER);
                emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                booksInputPanel.add(emptyLabel);
            } else {
                for (CTPhieuMuonDTO ct : chiTietList) {
                    if (ct == null)
                        continue;

                    JPanel bookRow = new JPanel(new GridBagLayout());
                    bookRow.setBackground(new Color(245, 245, 245));
                    bookRow.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(0, 5, 0, 5);
                    gbc.fill = GridBagConstraints.HORIZONTAL;

                    // Tạo các component
                    JTextField txtMaSach = createReadOnlyTextField(8);
                    JTextField txtTenDauSach = createReadOnlyTextField(15);
                    JCheckBox checkBox = new JCheckBox();
                    checkBox.setBackground(new Color(245, 245, 245));

                    // ComboBox trạng thái sách
                    String[] trangThaiSachOptions = { "Bình thường", "Hư hỏng", "Mất" };
                    JComboBox<String> cbTrangThaiSach = new JComboBox<>(trangThaiSachOptions);
                    cbTrangThaiSach.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    cbTrangThaiSach.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(180, 180, 180)),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                    cbTrangThaiSach.setVisible(false);

                    // Thiết lập trạng thái
                    int trangThai = ct.getTrangThai(); // 0: chưa trả, 1: đã trả
                    if (trangThai == 1) {
                        checkBox.setSelected(true);
                        checkBox.setEnabled(false);
                        cbTrangThaiSach.setVisible(true);
                        cbTrangThaiSach.setSelectedIndex(ct.getTrangThai()); // Giả sử có field này
                    }

                    checkBox.addActionListener(e -> {
                        cbTrangThaiSach.setVisible(checkBox.isSelected());
                    });

                    // Đặt dữ liệu
                    txtMaSach.setText(ct.getMaSach() != null ? ct.getMaSach() : "");

                    SachDTO sach = (ct.getMaSach() != null) ? sachMap.get(ct.getMaSach()) : null;
                    if (sach != null) {
                        DauSachDTO dauSach = (sach.getMaDauSach() != null) ? dauSachMap.get(sach.getMaDauSach()) : null;
                        txtTenDauSach.setText(dauSach != null ? dauSach.getTenDauSach() : "Không tìm thấy đầu sách");
                    } else {
                        txtTenDauSach.setText("Không tìm thấy sách");
                    }

                    // Bố cục
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.weightx = 0.25;
                    bookRow.add(txtMaSach, gbc);

                    gbc.gridx = 1;
                    gbc.weightx = 0.6;
                    bookRow.add(txtTenDauSach, gbc);

                    gbc.gridx = 1;
                    gbc.gridy = 1;
                    bookRow.add(cbTrangThaiSach, gbc);

                    gbc.gridx = 2;
                    gbc.gridy = 0;
                    gbc.weightx = 0.15;
                    gbc.gridheight = 2;
                    gbc.fill = GridBagConstraints.NONE;
                    bookRow.add(checkBox, gbc);

                    booksInputPanel.add(bookRow);
                    booksInputPanel.add(Box.createVerticalStrut(5));
                }
            }
        }

        JScrollPane scrollPane = new JScrollPane(booksInputPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(400, 200));
        return scrollPane;
    }

    // Hàm phụ tạo text field chỉ đọc
    private JTextField createReadOnlyTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        textField.setEditable(false);
        return textField;
    }

    private JPanel createInputRow(String labelText, JTextField textField) {
        JPanel row = new JPanel(new GridLayout(1, 2, 10, 10));
        row.setBackground(new Color(245, 245, 245));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        row.add(label);
        row.add(textField);
        return row;
    }

    public void performSearch() {
        String type = (String) pt.getCbLuaChonTK().getSelectedItem();
        String txt = pt.getTxtTimKiem().getText().trim();
        System.out.println("dang tim kiem" + txt + "loai " + type);
        switch (type) {
            case "Mã phiếu trả":
                timMPT(txt);
                break;
            case "Ngày trả":
                timNgayTra(txt);
                break;
            case "Mã nhân viên":
                timMNV(txt);
                break;
            case "Mã độc giả":
                timMDG(txt);
                break;
            case "Mã phiếu mượn":
                timMPM(txt);
                break;
            default:
                break;
        }
    }
}