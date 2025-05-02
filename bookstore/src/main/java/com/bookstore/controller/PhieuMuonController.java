package com.bookstore.controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

import com.bookstore.BUS.PhieuMuonBUS;
import com.bookstore.DTO.CTPhieuMuonDTO;
import com.bookstore.DTO.PhieuMuonDTO;
import com.bookstore.dao.CTPhieuMuonDAO;
import com.bookstore.dao.PhieuMuonDAO;
import com.bookstore.DTO.TaiKhoanDTO;
import com.bookstore.views.Panel.PhieuMuon;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class PhieuMuonController implements ItemListener, ActionListener{

    private PhieuMuon pm;
    private PhieuMuonDAO pmdao = new PhieuMuonDAO();
    private PhieuMuonBUS pmbus = new PhieuMuonBUS();
    private TaiKhoanDTO tkDTO = new TaiKhoanDTO();
    private Comparator<PhieuMuonDTO> comparator = Comparator.comparing(PhieuMuonDTO:: getMaPhieuMuon);
    private List<PhieuMuonDTO> manggoc;
    private List<PhieuMuonDTO> mangtmp;
    private boolean isAscending = true;
    private CTPhieuMuonDAO ctpmdao = new CTPhieuMuonDAO();
    private FlatSVGIcon add_icon = new FlatSVGIcon(getClass().getResource("/svg/add_2.svg")).derive(12, 12);
    private FlatSVGIcon subtractIcon = new FlatSVGIcon(getClass().getResource("/svg/subtract.svg")).derive(25,25);


    public PhieuMuonController(PhieuMuon pm){
        this.pm = pm;
        manggoc = pm.getListpm();
        mangtmp = pm.getListpm();
    }


    // sự kiện button
    @Override
    public void actionPerformed(ActionEvent e) {

        // tìm kiếm
        if (e.getSource() == pm.getBtnTimKiem()){
            String str = (String) pm.getCbLuaChonTK().getSelectedItem();
            if (str != null){
                System.out.println("Da nhan btn tim kiem");
                String key =  pm.getTxtTimKiem().getText().trim();
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
        }

        // đảo ngược danh sách
        else if (e.getSource() == pm.getBtnReverse()){
            if (isAscending){
                pm.getBtnReverse().setIcon(pm.upIcon);
                Collections.sort(mangtmp, comparator);
            }
            else {
                pm.getBtnReverse().setIcon(pm.downIcon);
                Collections.sort(mangtmp, comparator.reversed());
            }

            isAscending = !isAscending;
            pm.updateTable(mangtmp);

        }

        // sửa
        else if (e.getSource() == pm.getBtnSua()){
            // tao Jdialog
        }

        // xóa
        else if (e.getSource() == pm.getBtnXoa()){
            if (pm.getTable().getSelectedRow() == -1){
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

            // panel chính
            JPanel mainPanel = new JPanel();
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // panel chứa nội dung
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

            // label thông báo
            JLabel message = new JLabel("Bạn chắc chắn muốn xóa phiếu mượn " + mpm);
            message.setFont(new Font("Segoe UI", Font.BOLD, 16));
            message.setAlignmentX(Component.CENTER_ALIGNMENT);
            message.setBorder(BorderFactory.createEmptyBorder( 0, 0, 30, 0));            

            // panel chứa button xác nhận và hủy
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

            // button xác nhận
            JButton confirm = new JButton("Xác nhận");
            styleButton(confirm, new Color(0, 120, 215));

            // button hủy
            JButton cancel = new JButton("Hủy");
            styleButton(cancel, new Color(100,100,100));

            // add .
            contentPanel.add(message);
            buttonPanel.add(confirm);
            buttonPanel.add(cancel);

            mainPanel.add(contentPanel, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            dialog.add(mainPanel);

            confirm.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    if (pmbus.xoaPhieuMuon(Integer.parseInt(mpm))){

                        pm.setListpm(pmdao.layDanhSachPhieuMuon());
                        pm.loadTableData();
                        JOptionPane.showMessageDialog(null, "Xóa phiếu mượn thành công","Thành công", JOptionPane.INFORMATION_MESSAGE);
            
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Xóa phiếu mượn thất bại", "Thất bại", JOptionPane.ERROR_MESSAGE);
                    }

                    dialog.dispose();
                    manggoc = pm.getListpm();
                    mangtmp = pm.getListpm();

                }
            });
            cancel.addActionListener(e1 -> dialog.dispose());

            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

        }

        // thêm
        else if (e.getSource() == pm.getBtnThem()) {
            System.out.println("Da nhan vao them");
            JDialog dialog = new JDialog((JFrame) null, "Thêm phiếu mượn", true);
            dialog.setSize(450, 600);
            dialog.setLocationRelativeTo(null);
            dialog.setLayout(null);
            
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
            mainPanel.setBounds(5,5,440,460);
        
            // Lấy dữ liệu mặc định
            // mã phiếu mượn
            int mpm = pm.getListpm().stream()
                    .mapToInt(PhieuMuonDTO::getMaPhieuMuon)
                    .max()
                    .orElse(0) + 1;
        
            // ngày mượn + gán ngày trả dự kiến
            LocalDate tmpday = LocalDate.now();
            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fomatedday = tmpday.format(f);
            String fomatedngaytradukien = tmpday.plusDays(15).format(f);
        
            // Tạo panel cho thông tin cơ bản
            JPanel infoPanel = new JPanel(new GridLayout(0, 2, 10, 10));
            

            // Label + TextField
            JLabel lbphieumuon = new JLabel("Mã phiếu mượn: ");
            JTextField txtfmaphieumuon = new JTextField(String.valueOf(mpm));
            txtfmaphieumuon.setEditable(false);
        
            JLabel lbngaymuon = new JLabel("Ngày mượn: ");
            JTextField txtfngaymuon = new JTextField(fomatedday);
            txtfngaymuon.setEditable(false);
        
            JLabel lbngaytradukien = new JLabel("Ngày trả dự kiến: ");
            JTextField txtfngaytradukien = new JTextField(fomatedngaytradukien);
            txtfngaytradukien.setEditable(false);
        
            JLabel lbmadocgia = new JLabel("Mã độc giả: ");
            JTextField txtfmadocgia = new JTextField();
        
            JLabel lbmanhanvien = new JLabel("Mã nhân viên: ");
            // sau khi sửa taikhoandto -> bỏ dòng này
            JTextField txtfmanhanvien = new JTextField("nv001");
            txtfmanhanvien.setEditable(false);
        
            // Thêm vào panel thông tin
            infoPanel.add(lbphieumuon);
            infoPanel.add(txtfmaphieumuon);
            infoPanel.add(lbngaymuon);
            infoPanel.add(txtfngaymuon);
            infoPanel.add(lbngaytradukien);
            infoPanel.add(txtfngaytradukien);
            infoPanel.add(lbmadocgia);
            infoPanel.add(txtfmadocgia);
            infoPanel.add(lbmanhanvien);
            infoPanel.add(txtfmanhanvien);
        
            mainPanel.add(infoPanel);
        
            // Panel chứa mã sách
            JPanel sachPanel = new JPanel();
            sachPanel.setLayout(new BoxLayout(sachPanel, BoxLayout.Y_AXIS));

            JPanel titlesach = new JPanel();
            JLabel title = new JLabel("Danh sách mã sách");
            JButton addbtn = new JButton(add_icon);

            titlesach.setLayout(new FlowLayout(FlowLayout.LEFT));

            title.setPreferredSize(new Dimension(350, 30));
            titlesach.add(title);

            titlesach.add(addbtn);

            mainPanel.add(titlesach);

            // Danh sách các dòng mã sách
            List<JPanel> bookRows = new ArrayList<>();
            List<JTextField> bookFields = new ArrayList<>();
        
            // Thêm dòng mã sách đầu tiên
            addBookRow(sachPanel, bookRows, bookFields);
        
            mainPanel.add(sachPanel);
            
            addbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    addBookRow(sachPanel, bookRows, bookFields);
                }                
            });

            // Panel nút
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

            buttonPanel.setBounds(5,490, 440, 80);

            JButton confirm = new JButton("Xác nhận");
            JButton cancel = new JButton("Hủy");
        
            styleButton(confirm, new Color(0, 120, 215));
            styleButton(cancel, new Color(100, 100, 100));
        
            buttonPanel.add(confirm);
            buttonPanel.add(cancel);
        
            JPanel rowsPanel = new JPanel();
            rowsPanel.setLayout(new BoxLayout(rowsPanel, BoxLayout.Y_AXIS));
            
            dialog.add(mainPanel);
            mainPanel.add(new JScrollPane(rowsPanel), BorderLayout.CENTER);
            dialog.add(buttonPanel);
        
            // Sự kiện nút xác nhận
            confirm.addActionListener(e2 -> {
                Date ngaymuon = Date.from(tmpday.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date ngaytradukien = Date.from(tmpday.plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
                PhieuMuonDTO newPM = new PhieuMuonDTO(mpm, ngaymuon, ngaytradukien, 0, txtfmadocgia.getText(), "nv001", true);
        
                // Lấy danh sách mã sách
                List<String> maSachList = new ArrayList<>();
                for (JTextField field : bookFields) {
                    if (!field.getText().trim().isEmpty()) {
                        maSachList.add(field.getText().trim());
                    }
                }
        
                if (maSachList.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập ít nhất một mã sách");
                    return;
                }
        
                if (pmbus.themPhieuMuon(newPM)) {
                    // Thêm chi tiết phiếu mượn cho từng mã sách
                    for (String maSach : maSachList) {
                        pmbus.themChiTietPhieuMuon(newPM.getMaPhieuMuon(), maSach);
                    }
                    
                    //load lại table
                    pm.setListpm(pmdao.layDanhSachPhieuMuon());
                    pm.loadTableData();
                    JOptionPane.showMessageDialog(null, "Thêm phiếu mượn thành công");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm phiếu mượn thất bại");
                }
            });
        
            cancel.addActionListener(e3 -> dialog.dispose());
        
            dialog.setVisible(true);
        }   
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'itemStateChanged'");
    }
    
    public void hienthichitiettable(int maPhieuMuon){
        JDialog dialog = new JDialog();
        dialog.setTitle("Chi tiết phiếu mượn");
        dialog.setLayout(new GridLayout(1,2, 10,10));
        dialog.setSize(new Dimension(600, 400));

        JPanel panelthongtin = new JPanel();
        panelthongtin.setLayout(new GridLayout(6, 1,10, 10));
        
        Date ngaymuon = null;
        Date ngaytradukien = null;
        int trangthai = -1;
        String madocgia = "";
        String manhanvien = "";

        //??

        for (PhieuMuonDTO i: manggoc){
            if (i.getMaPhieuMuon() == maPhieuMuon){
                ngaymuon = i.getNgayMuon();
                ngaytradukien = i.getNgayTraDuKien();
                trangthai = i.getTrangThai();
                madocgia = i.getMaDocGia();
                manhanvien = i.getMaNhanVien();

                break;
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // các thành phần bên trái
        JLabel lbmaphieumuon = new JLabel("Mã phiếu mượn: " + maPhieuMuon);
        lbmaphieumuon.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JLabel lbngaymuon = new JLabel("Ngày mượn " + sdf.format(ngaymuon));
        lbngaymuon.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        
        JLabel lbngaytradukien = new JLabel("Ngày trả dự kiến: " + sdf.format(ngaytradukien));
        lbngaytradukien.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JLabel lbtrangthai;
        if (trangthai == 1){
            lbtrangthai = new JLabel("Trạng thái: đã trả");
            lbtrangthai.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        }
        else {
            lbtrangthai = new JLabel("Trạng thái: chưa trả");
            lbtrangthai.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        }
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

        // các thành phần bên phải
        JPanel panelchitiet = new JPanel();
        panelchitiet.setLayout(new GridLayout(6, 1, 10,10));

        int count = 0;
        List<String> tongHopMaSach = new ArrayList<>();        
        List<CTPhieuMuonDTO> ctpmlist = ctpmdao.layDanhSachCTPhieuMuon();
        for (CTPhieuMuonDTO i: ctpmlist){
            if (i.getMaPhieuMuon() == maPhieuMuon){
                tongHopMaSach.add(i.getMaSach());
                count++;
            }
        }

        JLabel lbsoluong = new JLabel("Tổng số sách đã mượn: " + count);
        panelchitiet.add(lbsoluong);

        for (int i = 1; i <= count; i++){
            JPanel panelctsach = new JPanel();
            panelctsach.setLayout(new GridLayout(1,2));
            JLabel tmpLabel = new JLabel("Mã sách " + i + ": ");
            JLabel masachlabel = new JLabel(tongHopMaSach.get(i-1));

            panelctsach.add(tmpLabel);
            panelctsach.add(masachlabel);

            panelchitiet.add(panelctsach);
        }

        dialog.add(panelthongtin);
        dialog.add(panelchitiet);

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void styleButton(JButton button, Color color){
        button.setPreferredSize(new Dimension(120, 40));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setContentAreaFilled(true);
        button.setOpaque(true);        
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(color.darker()),
                        BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // các hàm tìm kiếm theo combobox
    public void timMPM(String key){
        mangtmp = manggoc.stream()
                            .filter(i -> (i.getMaPhieuMuon() + "").contains(key))
                            .collect(Collectors.toList());
        pm.updateTable(mangtmp);
    }

    public void timNM(String key){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        mangtmp = manggoc.stream()
                            .filter(i -> {
                                    String xx = sdf.format(i.getNgayMuon());
                                    return xx.contains(key);
                                    })
                            .collect(Collectors.toList());
        pm.updateTable(mangtmp);
    }

    public void timNTDK(String key){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        mangtmp = manggoc.stream()
                            .filter(i -> {
                                String xx = sdf.format(i.getNgayTraDuKien());
                                return xx.contains(key);
                                })
                            .collect(Collectors.toList());
        pm.updateTable(mangtmp);
    }

    public void timTrangThai(String key){
        mangtmp = manggoc.stream()
                            .filter(i -> (i.getTrangThai() + "").contains(key))
                            .collect(Collectors.toList());
        pm.updateTable(mangtmp);
    }

    public void timMDG(String key){
        mangtmp = manggoc.stream()
                            .filter(i -> (i.getMaDocGia() + "").contains(key))
                            .collect(Collectors.toList());
        pm.updateTable(mangtmp);
    }

    public void timMNV(String key){
        mangtmp = manggoc.stream()
                            .filter(i -> (i.getMaNhanVien() + "").contains(key))
                            .collect(Collectors.toList());
        pm.updateTable(mangtmp);
    }

    // Phương thức thêm dòng mã sách mới
    private void addBookRow(JPanel parentPanel, List<JPanel> bookRows, List<JTextField> bookFields) {
        if (bookRows.size() >= 5) {
            JOptionPane.showMessageDialog(null, "Chỉ được thêm tối đa 5 sách", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        // Tạo panel chứa dòng mới với BorderLayout
        JPanel rowPanel = new JPanel(new BorderLayout(10, 0));
        rowPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    
        // Tạo label với style đồng nhất
        JLabel label = new JLabel("Mã sách " + (bookRows.size() + 1) + ": ");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setPreferredSize(new Dimension(80, 25)); // Cố định width cho label
    
        // Tạo text field với style đồng nhất
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textField.setPreferredSize(new Dimension(200, 28));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(3, 5, 3, 5)));
        bookFields.add(textField);
    
        // Nút xóa với style đồng nhất
        JButton subtractButton = new JButton(subtractIcon);
        subtractButton.setPreferredSize(new Dimension(30, 28));
        subtractButton.setBorder(BorderFactory.createEmptyBorder());
        subtractButton.setContentAreaFilled(false);
        subtractButton.setToolTipText("Xóa dòng này");
        subtractButton.addActionListener(e -> {
            removeBookRow(parentPanel, bookRows, bookFields, rowPanel);
        });
    
        // Panel chứa label và text field
        JPanel labelFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        labelFieldPanel.add(label);
        labelFieldPanel.add(textField);
    
        // Thêm các thành phần vào rowPanel
        rowPanel.add(labelFieldPanel, BorderLayout.CENTER);
        rowPanel.add(subtractButton, BorderLayout.EAST);
    
        // Thêm khoảng cách giữa các dòng (trừ dòng đầu tiên)
        if (!bookRows.isEmpty()) {
            parentPanel.add(Box.createVerticalStrut(5));
        }
    
        bookRows.add(rowPanel);
        parentPanel.add(rowPanel);
    
        parentPanel.revalidate();
        parentPanel.repaint();
    }
    
     
    private void removeBookRow(JPanel parentPanel, List<JPanel> bookRows, List<JTextField> bookFields, JPanel rowToRemove) {
        int index = bookRows.indexOf(rowToRemove);
        if (index >= 0) {
            parentPanel.remove(rowToRemove);
            bookRows.remove(rowToRemove);
            bookFields.remove(index);
    
            // Cập nhật lại số thứ tự label
            for (int i = 0; i < bookRows.size(); i++) {
                JPanel panel = bookRows.get(i);
                JLabel label = (JLabel) ((JPanel) panel.getComponent(0)).getComponent(0);
                label.setText("Mã sách " + (i + 1) + ": ");
            }
    
            parentPanel.revalidate();
            parentPanel.repaint();
        }
    }
    
}
