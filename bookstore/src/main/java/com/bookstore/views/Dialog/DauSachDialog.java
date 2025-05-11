package com.bookstore.views.Dialog;

import javax.swing.*;

import com.bookstore.DTO.DauSachDTO;
import com.bookstore.DTO.SachDTO;
import com.bookstore.DTO.TacGiaDTO;
import com.bookstore.DTO.TheLoaiDTO;
import com.bookstore.dao.DauSachDAO;
import com.bookstore.dao.NCCDAO;
import com.bookstore.dao.SachDAO;
import com.bookstore.dao.TacGiaDAO;
import com.bookstore.dao.TheLoaiDAO;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class DauSachDialog extends JDialog {
    public enum Mode {
        ADD, EDIT, VIEW
    }

    private JLabel lblHinhAnh;
    private JTextField txtTenDauSach, txtNamXuatBan;
    private JComboBox<String> cboNhaXuatBan, cboNgonNgu, cboTacGia, cboTheLoai;
    private JButton btnChonHinh, btnLuu, btnHuy, btnThemTacGia, btnXoaTacGia, btnThemTheLoai, btnXoaTheLoai;
    private JList<String> listTacGia, listTheLoai;
    private DefaultListModel<String> listModelTacGia, listModelTheLoai;
    private String duongDanHinh;
    private Map<String, String> mapTacGia; // tên -> id
    private Map<String, Integer> mapTheLoai; // tên -> id

    public DauSachDialog(Frame parent, Mode mode, DauSachDTO ds) {
        super(parent, "Thêm Đầu Sách", true);
        setSize(950, 750);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        String MaDauSach = this.generateMaDauSach();

        JPanel panelMain = new JPanel(new BorderLayout(10, 10));
        panelMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // === HÌNH ẢNH ===
        if (ds == null) {
            duongDanHinh = "D:\\VSCode\\Lap trinh java\\BookStoreApp\\bookstore\\src\\main\\resources\\images\\default.png";
        } else {
            duongDanHinh = "D:\\VSCode\\Lap trinh java\\BookStoreApp\\bookstore\\src\\main\\resources\\images\\"
                    + ds.getHinhAnh();
        }
        ImageIcon icon = new ImageIcon(
                new ImageIcon(duongDanHinh).getImage().getScaledInstance(200, 250, Image.SCALE_SMOOTH));
        System.out.println(duongDanHinh);
        lblHinhAnh = new JLabel(icon, SwingConstants.CENTER);
        lblHinhAnh.setPreferredSize(new Dimension(300, 250));
        lblHinhAnh.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        btnChonHinh = new JButton("Chọn hình ảnh");
        btnChonHinh.setBackground(new Color(60, 179, 113));
        btnChonHinh.setForeground(Color.WHITE);
        btnChonHinh.setFocusPainted(false);

        JPanel panelHinh = new JPanel(new BorderLayout(5, 5));
        panelHinh.setPreferredSize(new Dimension(300, 250));
        panelHinh.add(lblHinhAnh, BorderLayout.CENTER);
        if (mode != Mode.VIEW)
            panelHinh.add(btnChonHinh, BorderLayout.SOUTH);

        // === THÔNG TIN ===
        JPanel panelThongTin = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblMa = new JLabel("Mã đầu sách:");
        JLabel lblTen = new JLabel("Tên đầu sách:");
        JLabel lblNXB = new JLabel("Nhà xuất bản:");
        JLabel lblNam = new JLabel("Năm xuất bản:");
        JLabel lblNgonNgu = new JLabel("Ngôn ngữ:");

        JTextField txtMa = new JTextField(String.valueOf(MaDauSach), 20);
        txtMa.setEnabled(false);
        txtTenDauSach = new JTextField(20);
        txtNamXuatBan = new JTextField(20);
        cboNhaXuatBan = new JComboBox<>();
        cboNgonNgu = new JComboBox<>(new String[] { "Tiếng Việt", "Tiếng Anh", "Tiếng Pháp" });

        List<String> dsNXB = new NCCDAO().getTenNCC();

        for (String nxb : dsNXB) {
            cboNhaXuatBan.addItem(nxb);
        }

        // Map<String, Integer> dsTacGia = new LinkedHashMap<>();
        // dsTacGia.put("Nguyễn Nhật Ánh", 1);
        // dsTacGia.put("Nam Cao", 2);
        // dsTacGia.put("Tô Hoài", 3);
        mapTacGia = new TacGiaDAO().getThongTin();
        mapTheLoai = new TheLoaiDAO().getThongTin();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0; // Các JTextField không giãn theo chiều ngang
        gbc.weighty = 0;
        panelThongTin.add(lblMa, gbc);
        gbc.gridx = 1;
        panelThongTin.add(txtMa, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelThongTin.add(lblTen, gbc);
        gbc.gridx = 1;
        panelThongTin.add(txtTenDauSach, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelThongTin.add(lblNXB, gbc);
        gbc.gridx = 1;
        panelThongTin.add(cboNhaXuatBan, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelThongTin.add(lblNam, gbc);
        gbc.gridx = 1;
        panelThongTin.add(txtNamXuatBan, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelThongTin.add(lblNgonNgu, gbc);
        gbc.gridx = 1;
        panelThongTin.add(cboNgonNgu, gbc);

        // === THỂ LOẠI ===
        JPanel panelTheLoai = new JPanel(new BorderLayout(5, 5));
        panelTheLoai.setBorder(BorderFactory.createTitledBorder("Thể loại"));

        cboTheLoai = new JComboBox<>();
        for (String tenTL : mapTheLoai.keySet()) {
            cboTheLoai.addItem(tenTL);
        }
        btnThemTheLoai = new JButton("Thêm");
        btnXoaTheLoai = new JButton("Xóa");

        JPanel panelChonTheLoai = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelChonTheLoai.add(cboTheLoai);
        panelChonTheLoai.add(btnThemTheLoai);
        panelChonTheLoai.add(btnXoaTheLoai);

        listModelTheLoai = new DefaultListModel<>();
        listTheLoai = new JList<>(listModelTheLoai);
        JScrollPane scrollTheLoai = new JScrollPane(listTheLoai);

        panelTheLoai.add(panelChonTheLoai, BorderLayout.NORTH);
        panelTheLoai.add(scrollTheLoai, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH; // giúp panelTacGia co giãn tốt
        gbc.weightx = 1;
        gbc.weighty = 1; // để tác giả có không gian dọc
        panelThongTin.add(panelTheLoai, gbc);
        gbc.gridwidth = 1;

        // === TÁC GIẢ ===
        JPanel panelTacGia = new JPanel(new BorderLayout(5, 5));
        panelTacGia.setBorder(BorderFactory.createTitledBorder("Tác giả"));

        cboTacGia = new JComboBox<>();
        for (String tenTG : mapTacGia.keySet()) {
            cboTacGia.addItem(tenTG);
        }

        btnThemTacGia = new JButton("Thêm");
        btnXoaTacGia = new JButton("Xóa");

        JPanel panelChonTG = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelChonTG.add(cboTacGia);
        panelChonTG.add(btnThemTacGia);
        panelChonTG.add(btnXoaTacGia);

        listModelTacGia = new DefaultListModel<>();
        listTacGia = new JList<>(listModelTacGia);
        JScrollPane scrollTG = new JScrollPane(listTacGia);

        panelTacGia.add(panelChonTG, BorderLayout.NORTH);
        panelTacGia.add(scrollTG, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 6; // Vì bạn có 5 dòng phía trên (0-4): Mã, Tên, NXB, Năm, Ngôn ngữ
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH; // giúp panelTacGia co giãn tốt
        gbc.weightx = 1;
        gbc.weighty = 1; // để tác giả có không gian dọc
        panelThongTin.add(panelTacGia, gbc);

        if (mode == Mode.VIEW)
            disableAll();

        if (ds != null) {
            txtTenDauSach.setText(ds.getTenDauSach());
            txtNamXuatBan.setText(String.valueOf(ds.getNamXuatBan()));
            cboNhaXuatBan.setSelectedItem(ds.getNhaXuatBan());
            cboNgonNgu.setSelectedItem(ds.getNgonNgu());
            for (TacGiaDTO tacgia : ds.getListTacGia()) {
                String tenTacGia = tacgia.getTenTacGia();
                listModelTacGia.addElement(tenTacGia);
            }
            for (TheLoaiDTO tl : ds.getListTheLoai()) {
                String tenTheLoai = tl.getTenTheLoai();
                listModelTheLoai.addElement(tenTheLoai);
            }
        }

        // Lấy danh sách sách theo mã đầu sách
        if (ds != null) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setPreferredSize(new Dimension(0, 200));
            panel.setBorder(BorderFactory.createTitledBorder("Danh sách sách thuộc đầu sách"));
            List<SachDTO> danhSach = new SachDAO().selectByDauSach(ds.getMaDauSach());

            // Tạo dữ liệu cho bảng
            String[] columnNames = { "Mã sách", "Ngày nhập" };
            Object[][] data = new Object[danhSach.size()][columnNames.length];

            for (int i = 0; i < danhSach.size(); i++) {
                SachDTO s = danhSach.get(i);
                data[i][0] = s.getMaSach();
                data[i][1] = s.getNgayNhap();
            }

            // Tạo JTable và thêm vào JScrollPane
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);

            panel.add(scrollPane, BorderLayout.CENTER);
            panelMain.add(panel, BorderLayout.SOUTH);
        }
        // === BOTTOM BUTTON ===
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");

        btnLuu.setBackground(new Color(30, 144, 255));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setFocusPainted(false);

        btnHuy.setBackground(Color.GRAY);
        btnHuy.setForeground(Color.WHITE);

        JPanel panelBtn = new JPanel();
        panelBtn.add(btnLuu);
        panelBtn.add(btnHuy);

        panelMain.add(panelHinh, BorderLayout.WEST);
        panelMain.add(panelThongTin, BorderLayout.CENTER);
        add(panelMain, BorderLayout.CENTER);
        add(panelBtn, BorderLayout.SOUTH);

        // === SỰ KIỆN ===
        btnChonHinh.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                String duongDanHinhRill = file.getAbsolutePath();
                System.out.println(duongDanHinhRill);
                // File file2 = new File(duongDanHinh);
                duongDanHinh = file.getName(); // Ví dụ: \img2.jpg
                ImageIcon icon2 = new ImageIcon(
                        new ImageIcon(duongDanHinhRill).getImage().getScaledInstance(300, 250, Image.SCALE_SMOOTH));
                lblHinhAnh.setIcon(icon2);
                lblHinhAnh.setText(null);
            }
        });
        // Sửa lại hành động khi hover vào nút
        btnChonHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnChonHinh.setBackground(new Color(46, 139, 87)); // Màu khi hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnChonHinh.setBackground(new Color(60, 179, 113)); // Màu khi không hover
            }
        });

        btnThemTacGia.addActionListener(e -> {
            String ten = (String) cboTacGia.getSelectedItem();
            if (ten != null && !listModelTacGia.contains(ten)) {
                listModelTacGia.addElement(ten);
            }
        });

        btnXoaTacGia.addActionListener(e -> {
            String selected = listTacGia.getSelectedValue();
            if (selected != null) {
                listModelTacGia.removeElement(selected);
            }
        });

        btnHuy.addActionListener(e -> dispose());

        btnLuu.addActionListener(e -> {
            String ten = txtTenDauSach.getText().trim();
            String nam = txtNamXuatBan.getText().trim();
            String ngonngu = (String) cboNgonNgu.getSelectedItem();
            String NXB = (String) cboNhaXuatBan.getSelectedItem();

            if (ten.isEmpty() || nam.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
                return;
            }
            int namxuatban = 0;
            try {
                namxuatban = Integer.parseInt(nam);
            } catch (Exception es) {
                JOptionPane.showMessageDialog(this, "Nhập đúng định dạng năm");
                return;
            }

            List<String> dsIDTacGia = new ArrayList<>();
            for (int i = 0; i < listModelTacGia.size(); i++) {
                String tenTacGia = listModelTacGia.getElementAt(i);
                dsIDTacGia.add(mapTacGia.get(tenTacGia));
            }

            List<Integer> dsIDTheLoai = new ArrayList<>();
            for (int i = 0; i < listModelTheLoai.size(); i++) {
                String tenTheLoai = listModelTheLoai.getElementAt(i);
                dsIDTheLoai.add(mapTheLoai.get(tenTheLoai));
            }

            // In ra kiểm tra (thay thế bằng truyền cho BUS/DAO)
            System.out.println("Tên sách: " + ten);
            System.out.println("Năm: " + nam);
            System.out.println("Hình ảnh: " + duongDanHinh);
            System.out.println("Tác giả ID: " + dsIDTacGia);
            DauSachDTO dauSach = new DauSachDTO();
            dauSach.setHinhAnh(duongDanHinh);
            dauSach.setMaDauSach(MaDauSach);
            dauSach.setTenDauSach(ten);
            dauSach.setNamXuatBan(namxuatban);
            dauSach.setNgonNgu(ngonngu);
            dauSach.setNhaXuatBan(NXB);
            new DauSachDAO().insert(dauSach);
            for (String idTacGia : dsIDTacGia) {
                new TacGiaDAO().insertDauSach(MaDauSach, idTacGia);
            }
            for (int idTheLoai : dsIDTheLoai) {
                new TheLoaiDAO().insertDauSach(MaDauSach, idTheLoai);
            }

            JOptionPane.showMessageDialog(this, "Lưu thành công!");
            dispose();
        });
        btnLuu.setBackground(new Color(30, 144, 255));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLuu.setBackground(new Color(0, 123, 255)); // Màu khi hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLuu.setBackground(new Color(30, 144, 255)); // Màu khi không hover
            }
        });

        btnHuy.setBackground(Color.GRAY);
        btnHuy.setForeground(Color.WHITE);
        btnHuy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHuy.setBackground(new Color(169, 169, 169)); // Màu khi hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHuy.setBackground(Color.GRAY); // Màu khi không hover
            }
        });

        btnThemTheLoai.addActionListener(e -> {
            String tl = (String) cboTheLoai.getSelectedItem();
            if (tl != null && !listModelTheLoai.contains(tl)) {
                listModelTheLoai.addElement(tl);
            }
        });

        btnXoaTheLoai.addActionListener(e -> {
            String selected = listTheLoai.getSelectedValue();
            if (selected != null) {
                listModelTheLoai.removeElement(selected);
            }
        });

    }

    private String generateMaDauSach() {
        int count = new DauSachDAO().selectID(); // Ví dụ: trả về 1 nếu có 1 đầu sách
        int nextId = count + 1; // Tăng ID tiếp theo
        return String.format("DS%03d", nextId); // Format thành DS001, DS010, DS123,...
    }

    private void disableAll() {
        txtTenDauSach.setEnabled(false);
        txtNamXuatBan.setEnabled(false);
        cboNhaXuatBan.setEnabled(false);
        cboNgonNgu.setEnabled(false);
        cboTacGia.setEnabled(false);
        cboTheLoai.setEnabled(false);
        btnChonHinh.setEnabled(false);
        btnThemTacGia.setEnabled(false);
        btnXoaTacGia.setEnabled(false);
        btnThemTheLoai.setEnabled(false);
        btnXoaTheLoai.setEnabled(false);
    }
}
