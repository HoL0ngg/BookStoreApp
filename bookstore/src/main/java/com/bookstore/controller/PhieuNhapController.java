package com.bookstore.controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Collections;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

import com.bookstore.BUS.PhieuNhapBUS;
import com.bookstore.DTO.PhieuNhapDTO;
import com.bookstore.views.Panel.PhieuNhap;
import com.bookstore.dao.PhieuNhapDAO;
import com.bookstore.DTO.TaiKhoanDTO;

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
            JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

            // Các trường nhập liệu
            JLabel lblMpn = new JLabel("Mã phiếu nhập:");
            JTextField txtMpn = new JTextField(mpn);
            txtMpn.setEditable(false); // Không cho sửa mã phiếu nhập
            JLabel lblThoigian = new JLabel("Thời gian (yyyy-MM-dd):");
            JTextField txtThoigian = new JTextField(strThoigian);
            JLabel lblMnv = new JLabel("Mã nhân viên:");
            JTextField txtMnv = new JTextField(mnv);
            JLabel lblMncc = new JLabel("Mã nhà cung cấp:");
            JTextField txtMncc = new JTextField(mncc);

            inputPanel.add(lblMpn);
            inputPanel.add(txtMpn);
            inputPanel.add(lblThoigian);
            inputPanel.add(txtThoigian);
            inputPanel.add(lblMnv);
            inputPanel.add(txtMnv);
            inputPanel.add(lblMncc);
            inputPanel.add(txtMncc);

            // Panel chứa nút
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
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
                    String newMnv = txtMnv.getText();
                    String newMnccStr = txtMncc.getText();

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

                    // Tạo đối tượng PhieuNhapDTO để cập nhật
                    PhieuNhapDTO updatePn = new PhieuNhapDTO(newMpn, thoigian, newMnv, newMncc);
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
        } else if (e.getSource() == pn.getBtnXoa()) {
            int slt = pn.getTable().getSelectedRow();
            if (slt == -1) {
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn phiếu nhập", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String mpn = pn.getTable().getValueAt(slt, 0).toString();

            // Tạo dialog xác nhận xóa
            JDialog dialog = new JDialog((JFrame) null, "Xóa phiếu nhập", true);
            dialog.setSize(400, 200);
            dialog.setLayout(new BorderLayout());
            dialog.setResizable(false);

            // Panel chính
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Panel nội dung
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

            // Thông báo
            JLabel message = new JLabel("Bạn chắc chắn muốn xóa phiếu nhập " + mpn);
            message.setFont(new Font("Segoe UI", Font.BOLD, 16));
            message.setAlignmentX(Component.CENTER_ALIGNMENT);
            message.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

            // Panel chứa button xác nhận và hủy
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

            // Button xác nhận
            JButton confirm = new JButton("Xác nhận");
            styleButton(confirm, new Color(0, 120, 215));

            // Button hủy
            JButton cancel = new JButton("Hủy");
            styleButton(cancel, new Color(100, 100, 100));

            // Thêm các thành phần
            contentPanel.add(message);
            buttonPanel.add(confirm);
            buttonPanel.add(cancel);

            mainPanel.add(contentPanel, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            dialog.add(mainPanel);

            confirm.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (pnbus.xoaPhieuNhap(mpn)) {
                        pn.setListpn(pndao.layDanhSachPhieuNhap());
                        pn.loadTableData();
                        JOptionPane.showMessageDialog(null, "Xóa thành công phiếu nhập " + mpn, "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    dialog.dispose();
                    manggoc = pn.getListpn();
                    mangtmp = pn.getListpn();
                }
            });
            cancel.addActionListener(e1 -> dialog.dispose());

            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        } else if (e.getSource() == pn.getBtnThem()) {
            System.out.println("Đã nhấn vào nút Thêm");
            JDialog dialog = new JDialog((JFrame) null, "Thêm Phiếu Nhập", true);
            dialog.setSize(400, 300);
            dialog.setLayout(new BorderLayout());
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(null);

            // Panel chính
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Panel chứa các trường nhập liệu
            JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

            // Các trường nhập liệu
            JTextField txtfMaPhieuNhap = new JTextField();
            JTextField txtfThoigian = new JTextField();
            JTextField txtfMaNV = new JTextField();
            JTextField txtfMaNCC = new JTextField();

            // Thời gian hiện tại
            LocalDate tmpday = LocalDate.now();
            DateTimeFormatter fomatdaytime = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fomatedday = tmpday.format(fomatdaytime);
            txtfThoigian.setText(fomatedday);
            txtfThoigian.setEditable(false);

            // Mã nhân viên hiện tại
            txtfMaNV.setText("NV001"); // Có thể thay bằng tkDTO.getTenDangNhap()
            txtfMaNV.setEditable(false);

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
            txtfMaPhieuNhap.setEditable(false);

            // Thêm các nhãn và trường vào inputPanel
            inputPanel.add(new JLabel("Mã phiếu nhập:"));
            inputPanel.add(txtfMaPhieuNhap);
            inputPanel.add(new JLabel("Thời gian (yyyy-MM-dd):"));
            inputPanel.add(txtfThoigian);
            inputPanel.add(new JLabel("Mã nhân viên:"));
            inputPanel.add(txtfMaNV);
            inputPanel.add(new JLabel("Mã nhà cung cấp:"));
            inputPanel.add(txtfMaNCC);

            // Panel chứa nút
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
            JButton confirm = new JButton("Xác nhận");
            JButton cancel = new JButton("Hủy");

            // Style cho nút (giả sử phương thức styleButton đã được định nghĩa)
            styleButton(confirm, new Color(0, 120, 215));
            styleButton(cancel, new Color(100, 100, 100));

            buttonPanel.add(confirm);
            buttonPanel.add(cancel);

            mainPanel.add(inputPanel, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            dialog.add(mainPanel);

            // Xử lý nút Xác nhận
            confirm.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int maNCC;
                    try {
                        maNCC = Integer.parseInt(txtfMaNCC.getText().trim());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(dialog, "Mã nhà cung cấp phải là số nguyên", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

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
                            maNCC);
                    if (pnbus.themPhieuNhap(insertPN)) {
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
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2));
        dialog.setLocationRelativeTo(null);

        PhieuNhapDTO phieuNhap = pnbus.themChiTietPhieuNhap(mpn);
        if (phieuNhap != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dialog.add(new JLabel("Mã phiếu nhập: "));
            dialog.add(new JLabel(phieuNhap.getMaPhieuNhap()));
            dialog.add(new JLabel("Thời gian: "));
            dialog.add(new JLabel(sdf.format(phieuNhap.getThoigian())));
            dialog.add(new JLabel("Mã nhân viên: "));
            dialog.add(new JLabel(phieuNhap.getMaNV()));
            dialog.add(new JLabel("Mã nhà cung cấp: "));
            dialog.add(new JLabel(String.valueOf(phieuNhap.getMaNCC())));

            JButton close = new JButton("Đóng");
            close.addActionListener(e -> dialog.dispose());
            dialog.add(new JLabel(""));
            dialog.add(close);
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy chi tiết phiếu nhập", "Lỗi", JOptionPane.ERROR_MESSAGE);
            dialog.dispose();
        }

        dialog.setVisible(true);
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
                ;
                break;
            default:
                break;
        }
    }
}