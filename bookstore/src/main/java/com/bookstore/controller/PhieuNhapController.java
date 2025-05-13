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
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Timer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

import com.bookstore.BUS.NCCBUS;
import com.bookstore.BUS.PhieuNhapBUS;
import com.bookstore.DTO.NCCDTO;
import com.bookstore.DTO.PhieuNhapDTO;
import com.bookstore.views.Panel.PhieuNhap;
import com.bookstore.views.Panel.Sach;
import com.bookstore.dao.NCCDAO;
import com.bookstore.dao.NhanVienDAO;
import com.bookstore.dao.NhomQuyenDAO;
import com.bookstore.dao.PhieuNhapDAO;
import com.bookstore.utils.ExcelExporter;
import com.bookstore.utils.NguoiDungDangNhap;
import com.bookstore.DTO.TaiKhoanDTO;
import com.bookstore.DTO.CTPhieuNhapDTO;
import com.bookstore.DTO.DauSachDTO;
import com.bookstore.BUS.DauSachBUS;

public class PhieuNhapController implements ItemListener, ActionListener {
    private PhieuNhap pn;
    private PhieuNhapDAO pndao = new PhieuNhapDAO();
    private PhieuNhapBUS pnbus = new PhieuNhapBUS();
    @SuppressWarnings("unused")
    private TaiKhoanDTO tkDTO = new TaiKhoanDTO();
    private Comparator<PhieuNhapDTO> comparator = (pn1, pn2) -> 0;
    private List<PhieuNhapDTO> manggoc;
    private List<PhieuNhapDTO> mangtmp;
    private boolean isAscending = true;
    private Timer searchNCC;

    // constructor
    public PhieuNhapController(PhieuNhap pn) {
        this.pn = pn;
        manggoc = pn.getListpn();
        mangtmp = pn.getListpn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pn.getBtnReverse()) {
            if (isAscending) {
                pn.getBtnReverse().setIcon(pn.upIcon);
                Collections.sort(mangtmp, comparator);
            } else {
                pn.getBtnReverse().setIcon(pn.downIcon);
                Collections.sort(mangtmp, comparator.reversed());
            }
            isAscending = !isAscending;
            pn.updateTable(mangtmp);
        } else if (e.getSource() == pn.getBtnSua()) {
            if (!new NhomQuyenDAO().isAccessable(NguoiDungDangNhap.getInstance().getMaNhomQuyen(), 5, 3)) {
                JOptionPane.showMessageDialog(null,
                        "Bạn không có quyền sửa phiếu nhập", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int slt = pn.getTable().getSelectedRow();
            if (slt == -1) {
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn phiếu muốn sửa", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Lấy MaPhieuNhap từ cột 1 (cột 0 trong JTable)
            String mpn = pn.getTable().getValueAt(slt, 0).toString();
            // Giả sử các cột khác trong bảng lần lượt là: MaPhieuNhap, ThoiGian, MaNV,
            // MaNCC
            String strThoigian = pn.getTable().getValueAt(slt, 1).toString();
            String mnv = pn.getTable().getValueAt(slt, 2).toString();
            String mncc = pn.getTable().getValueAt(slt, 3).toString();

            // Lấy trạng thái hiện tại từ danh sách hoặc bảng
            int currentTrangThai = 1; // Giả sử mặc định
            for (PhieuNhapDTO pnItem : pn.getListpn()) {
                if (pnItem.getMaPhieuNhap().equals(mpn)) {
                    currentTrangThai = pnItem.getTrangThai(); // Lấy trạng thái hiện tại (int)
                    break;
                }
            }

            // Kiểm tra nếu trạng thái là "Đã hoàn thành" (2)
            if (currentTrangThai == 2) {
                JOptionPane.showMessageDialog(null, "Phiếu đã hoàn thành không thể sửa!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Tạo dialog chỉnh sửa
            JDialog dialog = new JDialog((JFrame) null, "Sửa phiếu nhập", true);
            dialog.setSize(400, 300);
            dialog.setLayout(new BorderLayout());
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(null);

            // Panel chính
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Panel chứa các trường nhập liệu
            JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));

            // Các trường nhập liệu
            JLabel lblMpn = new JLabel("Mã phiếu nhập:");
            JTextField txtMpn = new JTextField(mpn);
            txtMpn.setEditable(false); // Không cho sửa mã phiếu nhập
            JLabel lblThoigian = new JLabel("Thời gian (yyyy-MM-dd):");
            JTextField txtThoigian = new JTextField(strThoigian);
            JLabel lblMnv = new JLabel("Mã nhân viên:");
            JComboBox<String> cboMaNV = new JComboBox<>();
            List<String> dsMaNV = new NhanVienDAO().selectAll();
            for (String maNV : dsMaNV) {
                cboMaNV.addItem(maNV);
            }
            JLabel lblMncc = new JLabel("Mã nhà cung cấp:");
            JComboBox<String> cboNCC = new JComboBox<>();
            List<NCCDTO> dsNCC = new NCCDAO().selectAll();
            for (NCCDTO ncc : dsNCC) {
                cboNCC.addItem(ncc.getMaNCC() + " - " + ncc.getTenNCC());
            }
            JLabel lbTrangThai = new JLabel("Trạng thái");
            String[] TT = {
                    "Đang xử lý",
                    "Đã hủy",
                    "Đã hoàn thành",
            };
            JComboBox<String> cbtrangthai = new JComboBox<>(TT);
            inputPanel.add(lblMpn);
            inputPanel.add(txtMpn);
            inputPanel.add(lblThoigian);
            inputPanel.add(txtThoigian);
            inputPanel.add(lblMnv);
            inputPanel.add(cboMaNV);
            inputPanel.add(lblMncc);
            inputPanel.add(cboNCC);
            inputPanel.add(lbTrangThai);
            inputPanel.add(cbtrangthai);

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
                    // Lấy dữ liệu từ các trường nhập liệu
                    String newMpn = txtMpn.getText();
                    String newThoigianStr = txtThoigian.getText();
                    String newMnv = cboMaNV.getSelectedItem().toString().split(" - ")[0];
                    String newMnccStr = cboNCC.getSelectedItem().toString().split(" - ")[0];

                    // Kiểm tra và chuyển đổi thời gian
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date thoigian = null;
                    try {
                        java.util.Date utilDate = sdf.parse(newThoigianStr);
                        thoigian = new Date(utilDate.getTime());
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(dialog, "Ngày không hợp lệ (định dạng yyyy-MM-dd)", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Kiểm tra và chuyển đổi mã nhà cung cấp
                    int newMncc;
                    try {
                        newMncc = Integer.parseInt(newMnccStr);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(dialog, "Mã nhà cung cấp phải là số nguyên", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int selectedTT = cbtrangthai.getSelectedIndex();
                    // Tạo đối tượng PhieuNhapDTO để cập nhật
                    PhieuNhapDTO updatePn = new PhieuNhapDTO(newMpn, thoigian, newMnv, newMncc, 1, selectedTT);
                    boolean kq = pnbus.suaPhieuNhap(updatePn);
                    if (kq) {
                        JOptionPane.showMessageDialog(dialog, "Cập nhật thành công", "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
                        pn.setListpn(pndao.layDanhSachPhieuNhap());
                        pn.loadTableData();
                        // Giữ dòng được chọn
                        if (slt >= 0 && slt < pn.getTable().getRowCount()) {
                            pn.getTable().setRowSelectionInterval(slt, slt);
                        }
                        manggoc = pn.getListpn();
                        mangtmp = pn.getListpn();
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
        } else if (e.getSource() == pn.getBtnThem()) {
            if (!new NhomQuyenDAO().isAccessable(NguoiDungDangNhap.getInstance().getMaNhomQuyen(), 5, 2)) {
                JOptionPane.showMessageDialog(null,
                        "Bạn không có quyền thêm phiếu nhập", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            System.out.println("Đã nhấn vào nút Thêm");
            JDialog dialog = new JDialog((JFrame) null, "Thêm Phiếu Nhập", true);
            dialog.setSize(900, 600);
            dialog.setLayout(new BorderLayout());
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(null);

            // Panel chính chia làm 2 phần
            JPanel mainContainer = new JPanel(new GridLayout(1, 2, 15, 0));
            mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            mainContainer.setBackground(new Color(245, 245, 245));

            // ========= PHẦN BÊN TRÁI (THÔNG TIN PHIẾU NHẬP) =========
            JPanel leftPanel = new JPanel(new BorderLayout(0, 10));
            leftPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                    "Thông tin phiếu nhập",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Segoe UI", Font.BOLD, 14)));
            leftPanel.setBackground(new Color(245, 245, 245));

            // Panel chứa các trường nhập liệu
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
            inputPanel.setBackground(new Color(245, 245, 245));

            // Các trường nhập liệu
            JPanel row1 = createInputRow("Mã phiếu nhập:", new JTextField());
            JTextField txtfMaPhieuNhap = (JTextField) row1.getComponent(1);
            txtfMaPhieuNhap.setEditable(false);

            JPanel row2 = createInputRow("Thời gian (yyyy-MM-dd):", new JTextField());
            JTextField txtfThoigian = (JTextField) row2.getComponent(1);
            txtfThoigian.setEditable(false);

            JPanel row3 = createInputRow("Mã nhân viên:", new JTextField());
            JTextField txtfMaNV = (JTextField) row3.getComponent(1);
            txtfMaNV.setEditable(false);

            // Panel cho phần chọn nhà cung cấp với JList
            JPanel row4 = new JPanel(new BorderLayout(0, 5));
            row4.setBackground(new Color(245, 245, 245));
            JLabel nccLabel = new JLabel("Nhà cung cấp:");
            nccLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            row4.add(nccLabel, BorderLayout.NORTH);

            JTextField txtfSearchNCC = new JTextField();
            txtfSearchNCC.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            txtfSearchNCC.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180)),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));

            JList<NCCDTO> listNCC = new JList<>();
            listNCC.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            listNCC.setVisibleRowCount(5);
            listNCC.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
            JScrollPane nccScrollPane = new JScrollPane(listNCC);
            nccScrollPane.setPreferredSize(new Dimension(0, 100));

            JPanel nccInputPanel = new JPanel(new BorderLayout(0, 5));
            nccInputPanel.setBackground(new Color(245, 245, 245));
            nccInputPanel.add(txtfSearchNCC, BorderLayout.NORTH);
            nccInputPanel.add(nccScrollPane, BorderLayout.CENTER);
            row4.add(nccInputPanel, BorderLayout.CENTER);

            // Thêm các TextField chi tiết nhà cung cấp
            JPanel row5 = createInputRow("Tên NCC:", new JTextField());
            JTextField txtfTenNCC = (JTextField) row5.getComponent(1);
            txtfTenNCC.setEditable(false);

            JPanel row6 = createInputRow("Địa chỉ:", new JTextField());
            JTextField txtfDiaChi = (JTextField) row6.getComponent(1);
            txtfDiaChi.setEditable(false);

            JPanel row7 = createInputRow("Email:", new JTextField());
            JTextField txtfEmail = (JTextField) row7.getComponent(1);
            txtfEmail.setEditable(false);

            JPanel row8 = createInputRow("SĐT:", new JTextField());
            JTextField txtfSDT = (JTextField) row8.getComponent(1);
            txtfSDT.setEditable(false);

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
            inputPanel.add(Box.createVerticalStrut(15));
            inputPanel.add(row6);
            inputPanel.add(Box.createVerticalStrut(15));
            inputPanel.add(row7);
            inputPanel.add(Box.createVerticalStrut(15));
            inputPanel.add(row8);

            // Thời gian hiện tại
            LocalDate tmpday = LocalDate.now();
            DateTimeFormatter fomatdaytime = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fomatedday = tmpday.format(fomatdaytime);
            txtfThoigian.setText(fomatedday);

            // Mã nhân viên hiện tại
            txtfMaNV.setText("NV001");

            // Mã phiếu nhập mới
            String mpnNew = "PN001";
            int maxNum = 0;
            for (PhieuNhapDTO i : pn.getListpn()) {
                String numPart = i.getMaPhieuNhap().replace("PN", "");
                try {
                    int num = Integer.parseInt(numPart);
                    if (num > maxNum) {
                        maxNum = num;
                    }
                } catch (NumberFormatException ex) {
                    // Bỏ qua nếu không parse được
                }
            }
            mpnNew = String.format("PN%03d", maxNum + 1);
            txtfMaPhieuNhap.setText(mpnNew);

            // Lấy danh sách nhà cung cấp từ NCCBUS
            NCCBUS nccBus = new NCCBUS();
            List<NCCDTO> danhSachNCC = nccBus.getList();
            System.out.println("Tổng số NCC: " + danhSachNCC.size());

            // Renderer cho JList
            listNCC.setCellRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof NCCDTO) {
                        NCCDTO ncc = (NCCDTO) value;
                        setText(ncc.getMaNCC() + " - " + ncc.getTenNCC());
                    }
                    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    setBackground(isSelected ? new Color(0, 120, 215) : Color.WHITE);
                    setForeground(isSelected ? Color.WHITE : Color.BLACK);
                    return this;
                }
            });

            // Hiển thị toàn bộ danh sách nhà cung cấp ngay khi mở dialog
            DefaultListModel<NCCDTO> initialModel = new DefaultListModel<>();
            for (NCCDTO ncc : danhSachNCC) {
                initialModel.addElement(ncc);
            }
            listNCC.setModel(initialModel);
            nccScrollPane.setVisible(true);
            nccInputPanel.revalidate();
            nccInputPanel.repaint();

            // Quản lý Timer cho tìm kiếm liên tục
            searchNCC = new Timer();
            txtfSearchNCC.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    searchNCC.cancel();
                    searchNCC.purge();
                    searchNCC = new Timer();
                    searchNCC.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            SwingUtilities.invokeLater(() -> {
                                String searchText = txtfSearchNCC.getText().trim().toLowerCase();
                                DefaultListModel<NCCDTO> listModel = new DefaultListModel<>();

                                // Nếu TextField rỗng, hiển thị toàn bộ danh sách
                                if (searchText.isEmpty()) {
                                    for (NCCDTO ncc : danhSachNCC) {
                                        listModel.addElement(ncc);
                                    }
                                } else {
                                    // Lọc danh sách nhà cung cấp
                                    for (NCCDTO ncc : danhSachNCC) {
                                        String maNCC = String.valueOf(ncc.getMaNCC()).toLowerCase();
                                        String tenNCC = ncc.getTenNCC().toLowerCase();
                                        if (maNCC.contains(searchText) || tenNCC.contains(searchText)) {
                                            listModel.addElement(ncc);
                                        }
                                    }
                                }

                                // Cập nhật JList
                                listNCC.setModel(listModel);
                                nccScrollPane.setVisible(!listModel.isEmpty());
                                nccInputPanel.revalidate();
                                nccInputPanel.repaint();
                                System.out.println("Số NCC tìm thấy: " + listModel.getSize());
                            });
                        }
                    }, 300);
                }
            });

            // Xử lý khi chọn item từ JList
            listNCC.addListSelectionListener(e1 -> {
                if (!e1.getValueIsAdjusting() && listNCC.getSelectedValue() != null) {
                    NCCDTO selectedNCC = listNCC.getSelectedValue();
                    txtfSearchNCC.setText(String.valueOf(selectedNCC.getMaNCC()));
                    txtfTenNCC.setText(selectedNCC.getTenNCC());
                    txtfDiaChi.setText(selectedNCC.getDiaChi());
                    txtfEmail.setText(selectedNCC.getEmail());
                    txtfSDT.setText(selectedNCC.getSoDienThoai());
                    nccScrollPane.setVisible(false);
                    nccInputPanel.revalidate();
                    nccInputPanel.repaint();
                }
            });

            leftPanel.add(inputPanel, BorderLayout.CENTER);

            // ========= PHẦN BÊN PHẢI (DANH SÁCH SÁCH NHẬP) =========
            JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
            rightPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                    "Danh sách nhập",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Segoe UI", Font.BOLD, 14)));
            rightPanel.setBackground(new Color(245, 245, 245));

            // Panel header
            JPanel headerPanel = new JPanel(new GridLayout(1, 3, 5, 5));
            headerPanel.setBackground(new Color(245, 245, 245));
            JLabel maSachLabel = new JLabel("Mã sách");
            JLabel soLuongLabel = new JLabel("Số lượng");
            JLabel actionLabel = new JLabel("");
            maSachLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            soLuongLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            headerPanel.add(maSachLabel);
            headerPanel.add(soLuongLabel);
            headerPanel.add(actionLabel);

            // Panel chứa các dòng nhập sách
            JPanel booksInputPanel = new JPanel();
            booksInputPanel.setLayout(new BoxLayout(booksInputPanel, BoxLayout.Y_AXIS));
            booksInputPanel.setBackground(new Color(245, 245, 245));

            // Hàm thêm dòng nhập sách mới
            Runnable addBookRow = () -> {
                // Sử dụng GridBagLayout thay vì GridLayout để kiểm soát kích thước tốt hơn
                JPanel bookRow = new JPanel(new GridBagLayout());
                bookRow.setBackground(new Color(245, 245, 245));
                bookRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(0, 5, 0, 5); // Khoảng cách giữa các thành phần
                gbc.fill = GridBagConstraints.HORIZONTAL;

                // Panel chứa JComboBox
                JPanel dsInputPanel = new JPanel(new BorderLayout(0, 5));
                dsInputPanel.setBackground(new Color(245, 245, 245));
                List<DauSachDTO> tmplist = new DauSachBUS().getListDauSach();
                JComboBox<DauSachDTO> comboBox = new JComboBox<>();
                comboBox.setEditable(true); // Cho phép nhập để lọc
                comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                comboBox.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(180, 180, 180)),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));

                // Đặt kích thước cố định cho JComboBox
                comboBox.setPreferredSize(new Dimension(180, 30)); // Giảm chiều rộng để nhường chỗ cho các thành phần
                                                                   // khác
                comboBox.setMaximumSize(new Dimension(180, 30)); // Khóa kích thước tối đa

                comboBox.setRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                            boolean isSelected, boolean cellHasFocus) {
                        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        if (value instanceof DauSachDTO) {
                            DauSachDTO ds = (DauSachDTO) value;
                            setText(ds.getMaDauSach() + " - " + ds.getTenDauSach());
                        } else {
                            setText(value != null ? value.toString() : "0 - Chọn mã đầu sách");
                        }
                        return this;
                    }
                });

                DauSachDTO defaultItem = new DauSachDTO("0", "Chọn mã đầu sách", null, null, 0, null, 0, null);
                comboBox.addItem(defaultItem);
                comboBox.setSelectedItem(defaultItem); // Đảm bảo hiển thị đúng giá trị mặc định ban đầu

                comboBox.addPopupMenuListener(new PopupMenuListener() {
                    @Override
                    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                        comboBox.removeAllItems();
                        comboBox.addItem(new DauSachDTO("0", "Chọn mã đầu sách", null, null, 0, null, 0, null));
                        if (tmplist != null) {
                            for (DauSachDTO ds : tmplist) {
                                if (ds != null) {
                                    comboBox.addItem(ds);
                                }
                            }
                        }
                    }

                    @Override
                    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                        // comboBox.setSelectedItem(
                        // new DauSachDTO(null, "Chọn mã đầu sách", null, null, 0, null, 0, null));
                    }

                    @Override
                    public void popupMenuCanceled(PopupMenuEvent e) {
                        // Không cần xử lý khi hủy popup
                    }
                });

                JSpinner spinnerSoLuong = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
                spinnerSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                spinnerSoLuong.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(180, 180, 180)),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));

                // Đặt kích thước cố định cho JSpinner
                spinnerSoLuong.setPreferredSize(new Dimension(40, 30)); // Giảm chiều rộng xuống 40px
                spinnerSoLuong.setMaximumSize(new Dimension(40, 30)); // Khóa kích thước tối đa
                spinnerSoLuong.setMinimumSize(new Dimension(40, 30)); // Ép kích thước tối thiểu

                JButton btnRemove = new JButton("Xóa");
                styleButton(btnRemove, new Color(200, 50, 50));
                btnRemove.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                btnRemove.setPreferredSize(new Dimension(50, 30)); // Đặt kích thước cố định cho nút Xóa
                btnRemove.setMaximumSize(new Dimension(50, 30));

                // Thêm các thành phần vào bookRow với GridBagLayout
                gbc.gridx = 0;
                gbc.weightx = 0.7; // JComboBox chiếm 70% không gian
                bookRow.add(comboBox, gbc);

                gbc.gridx = 1;
                gbc.weightx = 0.15; // JSpinner chiếm 15% không gian
                bookRow.add(spinnerSoLuong, gbc);

                gbc.gridx = 2;
                gbc.weightx = 0.15; // Nút Xóa chiếm 15% không gian
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
            confirm.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    NCCDTO selectedNCC = listNCC.getSelectedValue();
                    if (selectedNCC == null) {
                        JOptionPane.showMessageDialog(dialog, "Vui lòng chọn nhà cung cấp", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Lấy danh sách sách nhập
                    List<CTPhieuNhapDTO> chiTietList = new ArrayList<>();
                    for (Component comp : booksInputPanel.getComponents()) {
                        if (comp instanceof JPanel) {
                            JPanel row = (JPanel) comp;
                            // Lấy các thành phần từ bookRow
                            Component[] components = row.getComponents();
                            if (components.length >= 3) { // Đảm bảo có đủ 3 thành phần: JComboBox, JSpinner, JButton
                                @SuppressWarnings("unchecked")
                                JComboBox<DauSachDTO> comboBox = (JComboBox<DauSachDTO>) components[0]; // Lấy JComboBox
                                JSpinner spinner = (JSpinner) components[1]; // Lấy JSpinner
                                DauSachDTO selectedDauSach = (DauSachDTO) comboBox.getSelectedItem();
                                if (selectedDauSach != null && !selectedDauSach.getMaDauSach().equals("0")) {
                                    String maSach = selectedDauSach.getMaDauSach();
                                    int soLuong = (int) spinner.getValue();
                                    chiTietList.add(new CTPhieuNhapDTO(
                                            txtfMaPhieuNhap.getText(),
                                            maSach,
                                            soLuong));
                                }
                            }
                        }
                    }

                    if (chiTietList.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, "Vui lòng nhập ít nhất một sách", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int maNCC = selectedNCC.getMaNCC();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.sql.Date date = null;
                    try {
                        java.util.Date utilDate = sdf.parse(fomatedday);
                        date = new java.sql.Date(utilDate.getTime());
                    } catch (ParseException e1) {
                        JOptionPane.showMessageDialog(dialog, "Ngày không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    PhieuNhapDTO insertPN = new PhieuNhapDTO(
                            txtfMaPhieuNhap.getText(),
                            date,
                            txtfMaNV.getText(),
                            maNCC,
                            1,
                            1);

                    if (pnbus.themPhieuNhap(insertPN, chiTietList)) {
                        pn.setListpn(pndao.layDanhSachPhieuNhap());
                        pn.loadTableData();
                        JOptionPane.showMessageDialog(dialog, "Thêm phiếu nhập thành công", "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
                        manggoc = pn.getListpn();
                        mangtmp = pn.getListpn();
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Thêm phiếu nhập thất bại", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            // Xử lý nút Hủy
            cancel.addActionListener(e1 -> dialog.dispose());
            dialog.setVisible(true);
        } else if (e.getSource() == pn.getBtnXoa()) {
            if (!new NhomQuyenDAO().isAccessable(NguoiDungDangNhap.getInstance().getMaNhomQuyen(), 5, 4)) {
                JOptionPane.showMessageDialog(null,
                        "Bạn không có quyền xóa phiếu nhập", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int slt = pn.getTable().getSelectedRow();
            if (slt == -1) {
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn phiếu muốn xóa", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            String mpn = pn.getTable().getValueAt(slt, 0).toString();

            // Tạo JDialog xác nhận xóa
            JDialog dialog = new JDialog((JFrame) null, "Xác nhận xóa phiếu nhập", true);
            dialog.setSize(400, 200);
            dialog.setLayout(new BorderLayout());
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(null);

            // Panel chính chứa thông báo
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            mainPanel.setBackground(new Color(245, 245, 245));

            // Thông báo
            JLabel messageLabel = new JLabel("Bạn muốn xóa phiếu nhập " + mpn + "?", SwingConstants.CENTER);
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
                // Gọi BUS để xóa phiếu nhập
                if (pnbus.xoaPhieuNhap(mpn)) {
                    // Cập nhật danh sách và bảng
                    pn.setListpn(pndao.layDanhSachPhieuNhap());
                    pn.loadTableData();
                    JOptionPane.showMessageDialog(dialog, "Xóa phiếu nhập thành công", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    manggoc = pn.getListpn();
                    mangtmp = pn.getListpn();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Xóa phiếu nhập thất bại", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
                dialog.dispose();
            });

            // Xử lý nút Hủy
            cancelButton.addActionListener(e1 -> dialog.dispose());

            dialog.setVisible(true);
        } else if (e.getSource() == pn.geButtontBtnXuatExcel()) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                if (!path.endsWith(".xlsx")) {
                    path += ".xlsx";
                }
                ExcelExporter.exportToExcel(manggoc, path);
                JOptionPane.showMessageDialog(pn, "Xuất file thành công!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void timMPN(String MaPN) {
        mangtmp = manggoc.stream()
                .filter(i -> i.getMaPhieuNhap().contains(MaPN))
                .collect(Collectors.toList());
        pn.updateTable(mangtmp);
    }

    public void timTG(String tg) {
        if (tg == null || tg.trim().isEmpty()) {
            mangtmp = manggoc;
            pn.updateTable(mangtmp);
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
                    Date itemDate = i.getThoigian();
                    String day = sdfDay.format(itemDate);
                    String month = sdfMonth.format(itemDate);
                    String year = sdfYear.format(itemDate);
                    String monthDay = sdfMonthDay.format(itemDate);
                    String yearMonth = sdfYearMonth.format(itemDate);
                    String fullDate = sdfFull.format(itemDate);

                    // Phân tích định dạng của tg
                    if (tg.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        // Định dạng yyyy-MM-dd
                        return fullDate.equals(tg);
                    } else if (tg.matches("\\d{4}-\\d{2}")) {
                        // Định dạng yyyy-MM
                        return yearMonth.equals(tg);
                    } else if (tg.matches("\\d{2}-\\d{2}")) {
                        // Định dạng MM-dd
                        return monthDay.equals(tg);
                    } else if (tg.matches("\\d{4}")) {
                        // Định dạng yyyy
                        return year.equals(tg);
                    } else if (tg.matches("\\d{1,2}")) {
                        // Định dạng MM hoặc dd (ưu tiên tháng nếu <= 12, ngày nếu > 12)
                        int value = Integer.parseInt(tg);
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
        pn.updateTable(mangtmp);
    }

    public void timMNV(String MaNV) {
        mangtmp = manggoc.stream()
                .filter(i -> i.getMaNV().contains(MaNV))
                .collect(Collectors.toList());
        pn.updateTable(mangtmp);
    }

    public void timMNCC(String MaNCC) {
        mangtmp = manggoc.stream()
                .filter(i -> String.valueOf(i.getMaNCC()).contains(MaNCC))
                .collect(Collectors.toList());
        pn.updateTable(mangtmp);
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
                        comparator = Comparator.comparing(PhieuNhapDTO::getMaPhieuNhap);
                        break;
                    case "Mã phiếu nhập":
                        comparator = Comparator.comparing(PhieuNhapDTO::getMaPhieuNhap);
                        break;
                    case "Thời gian":
                        comparator = Comparator.comparing(PhieuNhapDTO::getThoigian);
                        break;
                    case "Mã nhân viên":
                        comparator = Comparator.comparing(PhieuNhapDTO::getMaNV);
                        break;
                    case "Mã nhà cung cấp":
                        comparator = Comparator.comparingInt(PhieuNhapDTO::getMaNCC);
                        break;
                    default:
                        break;
                }
                Collections.sort(mangtmp, comparator);
                pn.updateTable(mangtmp);
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

    public void hienThiChiTietTable(String mpn) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Chi tiết phiếu nhập");
        dialog.setSize(600, 500);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(null);

        PhieuNhapDTO phieuNhap = null;
        for (PhieuNhapDTO pn : pn.getListpn()) {
            if (pn.getMaPhieuNhap().equals(mpn)) {
                phieuNhap = pn;
                break;
            }
        }

        if (phieuNhap != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Panel chính
            JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            mainPanel.setBackground(new Color(245, 245, 245));

            // Panel thông tin phiếu nhập
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(new Color(245, 245, 245));
            infoPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                    "Thông tin phiếu nhập",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Segoe UI", Font.BOLD, 14)));

            // Thêm các trường thông tin
            infoPanel.add(createInfoRow("Mã phiếu nhập:", phieuNhap.getMaPhieuNhap()));
            infoPanel.add(Box.createVerticalStrut(10));
            String thoiGian = phieuNhap.getThoigian() != null ? sdf.format(phieuNhap.getThoigian())
                    : "Không có thời gian";
            infoPanel.add(createInfoRow("Thời gian:", thoiGian));
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(
                    createInfoRow("Mã nhân viên:", phieuNhap.getMaNV() != null ? phieuNhap.getMaNV() : "Không có"));
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(createInfoRow("Mã nhà cung cấp:", String.valueOf(phieuNhap.getMaNCC())));

            // Panel danh sách chi tiết phiếu nhập
            JPanel booksPanel = new JPanel(new BorderLayout());
            booksPanel.setBackground(new Color(245, 245, 245));
            booksPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                    "Chi tiết phiếu nhập",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Segoe UI", Font.BOLD, 14)));

            // Tạo JTable cho chi tiết phiếu nhập
            DefaultTableModel booksModel = new DefaultTableModel(
                    new Object[] { "Mã sách", "Số lượng" }, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            // Lấy danh sách chi tiết từ BUS
            List<CTPhieuNhapDTO> chiTietList = pnbus.getChiTietPhieuNhap(mpn);
            for (CTPhieuNhapDTO ct : chiTietList) {
                if (ct.getMaPhieuNhap().equals(mpn)) {
                    booksModel.addRow(new Object[] { ct.getMaDauSach(), ct.getSoLuong() });
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
            JOptionPane.showMessageDialog(null, "Không tìm thấy chi tiết phiếu nhập", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

    public void performSearch() {
        String type = (String) pn.getCbLuaChonTK().getSelectedItem();
        String txt = pn.getTxtTimKiem().getText().trim();
        System.out.println("dang tim kiem" + txt + "loai " + type);
        switch (type) {
            case "Mã phiếu nhập":
                timMPN(txt);
                break;

            case "Thời gian":
                timTG(txt);
                break;
            case "Mã nhân viên":
                timMNV(txt);
                break;

            case "Mã nhà cung cấp":
                timMNCC(txt);
                break;
            default:
                break;
        }
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
}