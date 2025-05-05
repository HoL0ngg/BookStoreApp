package com.bookstore.controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JComboBox;

import com.bookstore.DTO.DauSachDTO;
import com.bookstore.DTO.SachDTO;
import com.bookstore.views.Panel.Sach;

public class SachController implements ItemListener {
    private Sach sach;

    public SachController(Sach sach) {
        this.sach = sach;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            @SuppressWarnings("unchecked")
            JComboBox<String> cbb = (JComboBox<String>) e.getSource();
            String str = (String) cbb.getSelectedItem();
            if (str != null) {
                System.out.println("Bạn đã chọn" + sach.getSortComboBox().getSelectedItem());
                if (sach.getCurrentCard() == "DanhSach") {
                    Comparator<DauSachDTO> comparator;
                    switch (str) {
                        case "Mã đầu sách":
                            comparator = Comparator.comparing(DauSachDTO::getMaDauSach);
                            break;
                        case "Năm xuất bản":
                            comparator = Comparator.comparing(DauSachDTO::getNamXuatBan);
                            break;
                        case "Số trang":
                            comparator = Comparator.comparingInt(DauSachDTO::getSoTrang);
                            break;
                        default:
                            return;
                    }
                    Collections.sort(sach.getListDauSach(), comparator);
                    sach.updateData(sach.getListDauSach());
                } else if (sach.getCurrentCard() == "Table") {
                    Comparator<SachDTO> comparator;
                    switch (str) {
                        case "Mã sách":
                            comparator = Comparator.comparing(SachDTO::getMaSach);
                            break;
                        case "Trạng thái":
                            comparator = Comparator.comparing(SachDTO::getTrangThai);
                            break;
                        case "Ngày nhập":
                            comparator = Comparator.comparing(SachDTO::getNgayNhap);
                            break;
                        default:
                            return;
                    }
                    Collections.sort(sach.getListSach(), comparator);
                    sach.updateTable(sach.getListSach());
                }
            }
        }
    }

}
