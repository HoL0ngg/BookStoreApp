package com.bookstore.controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.bookstore.BUS.PhieuPhatBUS;
import com.bookstore.DTO.PhieuPhatDTO;
import com.bookstore.dao.PhieuPhatDAO;
import com.bookstore.views.Panel.PhieuPhat;

public class PhieuPhatController implements ActionListener, ItemListener {

    private PhieuPhat pp;
    private PhieuPhatDAO ppdao = new PhieuPhatDAO();
    private PhieuPhatBUS ppbus = new PhieuPhatBUS();
    private Comparator<PhieuPhatDTO> comparator = (pp1, pp2) -> 0;
    private List<PhieuPhatDTO> manggoc;
    private List<PhieuPhatDTO> mangtmp;
    private boolean isAscending = true;

    public PhieuPhatController(PhieuPhat pp) {
        this.pp = pp;
        manggoc = pp.getListpp();
        mangtmp = pp.getListpp();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pp.getBtnReverse()) {
            if (isAscending) {
                pp.getBtnReverse().setIcon(pp.upIcon);
                Collections.sort(mangtmp, comparator);
            } else {
                pp.getBtnReverse().setIcon(pp.downIcon);
                Collections.sort(mangtmp, comparator.reversed());
            }
            isAscending = !isAscending;
            pp.updateTable(mangtmp);
        } else if (e.getSource() == pp.getBtnSua()) {
            int slt = pp.getTable().getSelectedRow();
            if (slt == -1) {
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn phiếu phạt muốn sửa", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String mppStr = pp.getTable().getValueAt(slt, 0).toString();
            int mpp;
            try {
                mpp = Integer.parseInt(mppStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Mã phiếu phạt không hợp lệ", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String tienPhat = pp.getTable().getValueAt(slt, 1).toString();
            String ngayPhat = pp.getTable().getValueAt(slt, 2).toString();
            String trangThai = pp.getTable().getValueAt(slt, 3).toString();
            String maDocGia = pp.getTable().getValueAt(slt, 4).toString();
            String maCTPhieuTra = pp.getTable().getValueAt(slt, 5).toString();

            JDialog dialog = new JDialog((JFrame) null, "Sửa phiếu phạt", true);
            dialog.setSize(400, 350);
            dialog.setLayout(new BorderLayout());
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));

            JLabel lblMpp = new JLabel("Mã phiếu phạt:");
            JTextField txtMpp = new JTextField(mppStr);
            txtMpp.setEditable(false);
            JLabel lblTienPhat = new JLabel("Tiền phạt:");
            JTextField txtTienPhat = new JTextField(tienPhat);
            JLabel lblNgayPhat = new JLabel("Ngày phạt (yyyy-MM-dd):");
            JTextField txtNgayPhat = new JTextField(ngayPhat);
            JLabel lblTrangThai = new JLabel("Trạng thái:");
            String[] trangThaiOptions = { "Chưa hoàn thành", "Đã hoàn thành" };
            JComboBox<String> cbTrangThai = new JComboBox<>(trangThaiOptions);
            cbTrangThai.setSelectedIndex(trangThai.equals("1") ? 1 : 0); // Thiết lập giá trị ban đầu
            JLabel lblMaDocGia = new JLabel("Mã độc giả:");
            JTextField txtMaDocGia = new JTextField(maDocGia);
            JLabel lblMaCTPhieuTra = new JLabel("Mã chi tiết phiếu trả:");
            JTextField txtMaCTPhieuTra = new JTextField(maCTPhieuTra);

            inputPanel.add(lblMpp);
            inputPanel.add(txtMpp);
            inputPanel.add(lblTienPhat);
            inputPanel.add(txtTienPhat);
            inputPanel.add(lblNgayPhat);
            inputPanel.add(txtNgayPhat);
            inputPanel.add(lblTrangThai);
            inputPanel.add(cbTrangThai);
            inputPanel.add(lblMaDocGia);
            inputPanel.add(txtMaDocGia);
            inputPanel.add(lblMaCTPhieuTra);
            inputPanel.add(txtMaCTPhieuTra);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            JButton btnConfirm = new JButton("Xác nhận");
            JButton btnCancel = new JButton("Hủy");

            styleButton(btnConfirm, new Color(0, 120, 215));
            styleButton(btnCancel, new Color(100, 100, 100));

            buttonPanel.add(btnConfirm);
            buttonPanel.add(btnCancel);

            mainPanel.add(inputPanel, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            dialog.add(mainPanel);

            btnConfirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String newTienPhatStr = txtTienPhat.getText();
                    String newNgayPhat = txtNgayPhat.getText();
                    String newMaDocGia = txtMaDocGia.getText();
                    String newMaCTPhieuTra = txtMaCTPhieuTra.getText();

                    int newTienPhat;
                    int newTrangThai;
                    int newMaCTPhieuTraInt;
                    try {
                        newTienPhat = Integer.parseInt(newTienPhatStr);
                        newTrangThai = cbTrangThai.getSelectedIndex(); // 1: Đã hoàn thành, 0: Chưa hoàn thành
                        newMaCTPhieuTraInt = Integer.parseInt(newMaCTPhieuTra);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(dialog, "Dữ liệu không hợp lệ", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.sql.Date ngayPhatDate;
                    try {
                        java.util.Date utilDate = sdf.parse(newNgayPhat);
                        ngayPhatDate = new java.sql.Date(utilDate.getTime());
                    } catch (java.text.ParseException ex) {
                        JOptionPane.showMessageDialog(dialog, "Ngày phạt không hợp lệ", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    PhieuPhatDTO updatePp = new PhieuPhatDTO(mpp, newTienPhat, ngayPhatDate, newTrangThai, newMaDocGia,
                            newMaCTPhieuTraInt, 1);
                    boolean kq = ppbus.suapp(updatePp);
                    if (kq) {
                        JOptionPane.showMessageDialog(dialog, "Cập nhật thành công", "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
                        pp.setListpp(ppbus.getList());
                        pp.loadTableData();
                        if (slt >= 0 && slt < pp.getTable().getRowCount()) {
                            pp.getTable().setRowSelectionInterval(slt, slt);
                        }
                        manggoc = pp.getListpp();
                        mangtmp = pp.getListpp();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Cập nhật thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    dialog.dispose();
                }
            });

            btnCancel.addActionListener(e1 -> dialog.dispose());
            dialog.setVisible(true);
        } else if (e.getSource() == pp.getBtnXoa()) {
            if (pp.getTable().getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn phiếu phạt", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            JDialog dialog = new JDialog((Frame) null, "Xóa phiếu phạt");
            dialog.setSize(400, 200);
            dialog.setLayout(new BorderLayout());
            dialog.setResizable(false);

            int row = pp.getTable().getSelectedRow();
            String mpp = pp.getTable().getValueAt(row, 0).toString();

            JPanel mainPanel = new JPanel();
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            JLabel message = new JLabel("Bạn chắc chắn muốn xóa phiếu phạt " + mpp + "?");
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
                    if (ppbus.xoapp(Integer.parseInt(mpp))) {
                        pp.setListpp(ppdao.getList());
                        pp.loadTableData();
                        JOptionPane.showMessageDialog(null, "Xóa phiếu phạt thành công", "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa phiếu phạt thất bại", "Thất bại",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    dialog.dispose();
                    manggoc = pp.getListpp();
                    mangtmp = pp.getListpp();
                }
            });
            cancel.addActionListener(e1 -> dialog.dispose());
            dialog.setLocationRelativeTo(null);
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
                switch (str) {
                    case "Mã phiếu phạt":
                        comparator = Comparator.comparing(PhieuPhatDTO::getMaPhieuPhat);
                        break;
                    case "Tiền phạt":
                        comparator = Comparator.comparing(PhieuPhatDTO::getTienPhat);
                        break;
                    case "Ngày phạt":
                        comparator = Comparator.comparing(PhieuPhatDTO::getNgayPhat);
                        break;
                    case "Trạng thái":
                        comparator = Comparator.comparing(PhieuPhatDTO::getTrangThai);
                        break;
                    case "Mã độc giả":
                        comparator = Comparator.comparing(PhieuPhatDTO::getMaDocGia);
                        break;
                    case "Mã chi tiết phiếu trả":
                        comparator = Comparator.comparing(PhieuPhatDTO::getMaCTPhieuTra);
                        break;
                    default:
                        break;
                }
                Collections.sort(mangtmp, comparator);
                pp.updateTable(mangtmp);
            }
        }
    }

    public void hienThiChiTietTable(int mpp) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Chi tiết phiếu phạt");
        dialog.setSize(400, 350);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(null);

        PhieuPhatDTO phieuPhat = null;
        for (PhieuPhatDTO pp : ppbus.getList()) {
            if (pp.getMaPhieuPhat() == mpp) {
                phieuPhat = pp;
                break;
            }
        }

        if (phieuPhat != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            mainPanel.setBackground(new Color(245, 245, 245));

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(new Color(245, 245, 245));
            infoPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                    "Thông tin phiếu phạt",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Segoe UI", Font.BOLD, 14)));

            infoPanel.add(createInfoRow("Mã phiếu phạt:", String.valueOf(phieuPhat.getMaPhieuPhat())));
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(createInfoRow("Tiền phạt:", String.valueOf(phieuPhat.getTienPhat())));
            infoPanel.add(Box.createVerticalStrut(10));
            String ngayPhat = phieuPhat.getNgayPhat() != null ? sdf.format(phieuPhat.getNgayPhat()) : "Không có";
            infoPanel.add(createInfoRow("Ngày phạt:", ngayPhat));
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(
                    createInfoRow("Trạng thái:", phieuPhat.getTrangThai() == 1 ? "Đã hoàn thành" : "Chưa hoàn thành"));
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(createInfoRow("Mã độc giả:", phieuPhat.getMaDocGia()));
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(createInfoRow("Mã chi tiết phiếu trả:", String.valueOf(phieuPhat.getMaCTPhieuTra())));

            mainPanel.add(infoPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            buttonPanel.setBackground(new Color(245, 245, 245));
            JButton closeButton = new JButton("Đóng");
            closeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            closeButton.setBackground(new Color(0, 120, 215));
            closeButton.setForeground(Color.WHITE);
            closeButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            closeButton.addActionListener(e -> dialog.dispose());
            buttonPanel.add(closeButton);

            dialog.add(mainPanel, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy chi tiết phiếu phạt", "Lỗi", JOptionPane.ERROR_MESSAGE);
            dialog.dispose();
        }

        dialog.setVisible(true);
    }

    private JPanel createInfoRow(String label, String value) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowPanel.setBackground(new Color(245, 245, 245));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setPreferredSize(new Dimension(150, 25));
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

    public void timMPP(String key) {
        mangtmp = manggoc.stream()
                .filter(i -> (i.getMaPhieuPhat() + "").contains(key))
                .collect(Collectors.toList());
        pp.updateTable(mangtmp);
    }

    public void timMDG(String key) {
        mangtmp = manggoc.stream()
                .filter(i -> i.getMaDocGia().contains(key))
                .collect(Collectors.toList());
        pp.updateTable(mangtmp);
    }

    public void timMCTPT(String key) {
        mangtmp = manggoc.stream()
                .filter(i -> (i.getMaCTPhieuTra() + "").contains(key))
                .collect(Collectors.toList());
        pp.updateTable(mangtmp);
    }

    public void performSearch() {
        String type = (String) pp.getCbLuaChonTK().getSelectedItem();
        String txt = pp.getTxtTimKiem().getText().trim();
        switch (type) {
            case "Mã phiếu phạt":
                timMPP(txt);
                break;
            case "Mã độc giả":
                timMDG(txt);
                break;
            case "Mã chi tiết phiếu trả":
                timMCTPT(txt);
                break;
            default:
                break;
        }
    }
}