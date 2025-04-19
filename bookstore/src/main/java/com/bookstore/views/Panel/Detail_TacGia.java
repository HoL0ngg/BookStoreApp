package com.bookstore.views.Panel;

import com.bookstore.DTO.DauSachDTO;
import com.bookstore.DTO.SachDTO;
import com.bookstore.DTO.TacGiaDTO;
import com.bookstore.dao.DauSachDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;

public class Detail_TacGia extends JDialog {
    private JTable tableDauSach;
    private DefaultTableModel tableModel;
    
    public Detail_TacGia(Frame parent, String maTacGia, String tenTacGia) {
        super(parent, "Chi tiết tác giả", true);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel hiển thị thông tin tác giả 
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3, 2));
        infoPanel.add(new JLabel("Mã Tác Giả:"));
        infoPanel.add(new JLabel(maTacGia));
        infoPanel.add(new JLabel("Tên Tác Giả:"));
        infoPanel.add(new JLabel(tenTacGia));
        add(infoPanel, BorderLayout.NORTH); // ??

        // show book 
        tableModel = new DefaultTableModel(new Object[]{"Mã đầu sách", "Tên đầu sách", "Nhà xuất bản", "Năm XB"}, 0);
        tableDauSach = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableDauSach);
        add(scrollPane, BorderLayout.CENTER);

        // get data 'dausach'
        loadDauSachByTacGia(maTacGia);
    }

    private void loadDauSachByTacGia(String maTacGia) {
        DauSachDAO dao = new DauSachDAO();
        List<DauSachDTO> list = dao.selectDauSachByMaTacGia(maTacGia);
        for (DauSachDTO ds : list) {
            tableModel.addRow(new Object[]{
                ds.getMaDauSach(),
                ds.getTenDauSach(),
                ds.getNhaXuatBan(),
                ds.getNamXuatBan()
            });
        }
    }
}