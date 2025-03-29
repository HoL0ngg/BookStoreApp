package com.bookstore.views.Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import com.bookstore.DTO.DauSachDTO;
import com.bookstore.dao.DauSachDAO;

public class Sach extends JPanel {

    public void initComponent() {
        this.setBackground(Color.white);
        this.setLayout(new FlowLayout(1, 0, 0));
        this.setPreferredSize(new Dimension(900, 600));
        List<DauSachDTO> list = new DauSachDAO().selectAll();

        JPanel ThongTinTauPanel = new JPanel();
        ThongTinTauPanel.setBackground(Color.pink);
        ThongTinTauPanel.setPreferredSize(new Dimension(900, 200));
        ThongTinTauPanel.setLayout(new FlowLayout(1, 0, 0));

        JLabel HinhAnhTau = new JLabel();
        HinhAnhTau.setPreferredSize(new Dimension(300, 200));
        HinhAnhTau.setBackground(Color.green);
        HinhAnhTau.setOpaque(true);
        // HinhAnhTau.setIcon(new ImageIcon(getClass().getResource("/svg/tau.png")));
        ThongTinTauPanel.add(HinhAnhTau);

        JPanel ThongTinChiTietTauPanel = new JPanel();
        ThongTinChiTietTauPanel.setPreferredSize(new Dimension(600, 200));
        ThongTinChiTietTauPanel.setLayout(new GridLayout(4, 3, 10, 10));

        JLabel MaTauLabel = new JLabel("Mã sách: ");
        JTextField MaTauTextField = new JTextField();
        MaTauTextField.setPreferredSize(new Dimension(200, 30));

        JLabel SoGheLabel = new JLabel("Tựa đề: ");
        JTextField SoGheTextField = new JTextField();
        SoGheTextField.setPreferredSize(new Dimension(200, 30));

        JLabel TrangThaiTauLabel = new JLabel("Trạng thái sách: ");
        JTextField TrangThaiTauTextField = new JTextField();
        TrangThaiTauTextField.setPreferredSize(new Dimension(200, 30));

        JLabel NgayNhapTauLabel = new JLabel("Nhà xuất bản: ");
        JTextField NgayNhapTauTextField = new JTextField();
        NgayNhapTauTextField.setPreferredSize(new Dimension(200, 30));

        JButton ThemTauButton = new JButton("THÊM");
        JButton SuaTauButton = new JButton("SỬA");
        JButton XoaTauButton = new JButton("XÓA");

        ThongTinChiTietTauPanel.add(MaTauLabel);
        ThongTinChiTietTauPanel.add(MaTauTextField);
        ThongTinChiTietTauPanel.add(ThemTauButton);

        ThongTinChiTietTauPanel.add(SoGheLabel);
        ThongTinChiTietTauPanel.add(SoGheTextField);
        ThongTinChiTietTauPanel.add(SuaTauButton);

        ThongTinChiTietTauPanel.add(TrangThaiTauLabel);
        ThongTinChiTietTauPanel.add(TrangThaiTauTextField);
        ThongTinChiTietTauPanel.add(XoaTauButton);

        ThongTinChiTietTauPanel.add(NgayNhapTauLabel);
        ThongTinChiTietTauPanel.add(NgayNhapTauTextField);

        ThongTinTauPanel.add(ThongTinChiTietTauPanel);
        this.add(ThongTinTauPanel);

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
        SortComboBox.addItem("Mã sách");
        SortComboBox.addItem("Số trang");
        SortComboBox.addItem("Năm xuất bản");
        SortComboBox.setBounds(760, 14, 100, 20);
        TongSoTauPanel.add(SortComboBox);

        JPanel DSDauSach = new DSDauSach();

        JScrollPane DanhSachTauScrollPane = new JScrollPane(DSDauSach, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        DanhSachTauScrollPane.setPreferredSize(new Dimension(900, 600));
        // DanhSachTauScrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(DanhSachTauScrollPane);

    }

    public Sach() {
        initComponent();
    }
}
