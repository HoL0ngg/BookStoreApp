package com.bookstore.controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    public PhieuNhapController(PhieuNhap pn) {
        this.pn = pn;
        manggoc = pn.getListpn();
        mangtmp = pn.getListpn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pn.getBtnTimKiem()) {
            String str = (String) pn.getCbLuaChonTK().getSelectedItem();
            if (str != null) {
                System.out.println("Đã chọn tìm kiếm");
                String key = pn.getTxtTimKiem().getText().trim();
                switch (str) {
                    case "Mã phiếu nhập":
                        timMPN(key);
                        break;
                    case "Mã nhân viên":
                        timMNV(key);
                        break;
                    case "Mã nhà cung cấp":
                        timMNCC(key);
                        break;
                    default:
                        break;
                }
            }
        } else if (e.getSource() == pn.getBtnReverse()) {
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
            if (slt != -1) {
                String mpn = pn.getTxtMaPhieuNhap().getText();
                String strThoigian = pn.getTxtThoigian().getText();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.sql.Date thoigian = null;
                try {
                    java.util.Date utilDate = sdf.parse(strThoigian);
                    thoigian = new java.sql.Date(utilDate.getTime());
                } catch (ParseException e1) {
                    System.out.println("Ngày không hợp lệ");
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Ngày không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String mnv = pn.getTxtMaNV().getText();
                int mncc;
                try {
                    mncc = Integer.parseInt(pn.getTxtMaNCC().getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Mã nhà cung cấp phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                PhieuNhapDTO updatePn = new PhieuNhapDTO(mpn, thoigian, mnv, mncc);
                boolean kq = pnbus.suaPhieuNhap(updatePn);
                if (kq) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                    int rowtmp = pn.getTable().getSelectedRow();
                    pn.setListpn(pndao.layDanhSachPhieuNhap());
                    pn.loadTableData();
                    if (rowtmp >= 0 && rowtmp < pn.getTable().getRowCount()) {
                        pn.getTable().setRowSelectionInterval(rowtmp, rowtmp);
                    }
                    manggoc = pn.getListpn();
                    mangtmp = pn.getListpn();
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại");
                }
            }
            System.out.println("Đã nhấn vào nút Sửa");
        } else if (e.getSource() == pn.getBtnXoa()) {
            if (pn.getTxtMaPhieuNhap().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn phiếu nhập", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            System.out.println("Đã nhấn vào nút Xóa");
            JDialog dialog = new JDialog((JFrame) null, "Xóa phiếu nhập", true);
            dialog.setSize(400, 200);
            dialog.setLayout(new BorderLayout());
            dialog.setResizable(false);

            String mpn = pn.getTxtMaPhieuNhap().getText();

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
                    String mpn = pn.getTxtMaPhieuNhap().getText();
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
            dialog.setLayout(new GridLayout(5, 2));

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
            txtfMaNV.setText("NV001");
            // txtfMaNV.setText(tkDTO.getTenDangNhap());
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

            dialog.add(new JLabel("Mã phiếu nhập: "));
            dialog.add(txtfMaPhieuNhap);

            dialog.add(new JLabel("Thời gian: "));
            dialog.add(txtfThoigian);

            dialog.add(new JLabel("Mã nhân viên: "));
            dialog.add(txtfMaNV);

            dialog.add(new JLabel("Mã nhà cung cấp: "));
            dialog.add(txtfMaNCC);

            JButton confirm = new JButton("Xác nhận");
            dialog.add(confirm);

            confirm.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int maNCC;
                    try {
                        maNCC = Integer.parseInt(txtfMaNCC.getText().trim());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Mã nhà cung cấp phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.sql.Date date = null;
                    try {
                        java.util.Date utilDate = sdf.parse(fomatedday);
                        date = new java.sql.Date(utilDate.getTime());
                    } catch (ParseException e1) {
                        System.out.println("Lỗi ngày");
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Ngày không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
                        JOptionPane.showMessageDialog(null, "Thêm phiếu nhập thành công");
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm phiếu nhập thất bại");
                    }
                    manggoc = pn.getListpn();
                    mangtmp = pn.getListpn();
                    dialog.dispose();
                }
            });

            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }

    public void timMPN(String MaPN) {
        mangtmp = manggoc.stream()
                .filter(i -> i.getMaPhieuNhap().contains(MaPN))
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
}