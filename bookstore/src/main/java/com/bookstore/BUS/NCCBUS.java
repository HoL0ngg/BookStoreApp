package com.bookstore.BUS;

import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.NCCDTO;
import com.bookstore.dao.NCCDAO;

public class NCCBUS {
    private List<NCCDTO> listNCC;

    public NCCBUS() {
        listNCC = new NCCDAO().selectAll();
    }

    public List<NCCDTO> getList() {
        return listNCC;
    }

    public List<NCCDTO> search(String txt, String filter) {
        List<NCCDTO> result = new ArrayList<>();
        for (NCCDTO ncc : listNCC) {
            switch (filter) {
                case "Tất cả":
                    if (String.valueOf(ncc.getMaNCC()).toLowerCase().contains(txt.toLowerCase())
                            || ncc.getTenNCC().toLowerCase().contains(txt.toLowerCase())
                            || ncc.getDiaChi().toLowerCase().contains(txt.toLowerCase())
                            || ncc.getEmail().toLowerCase().contains(txt.toLowerCase())
                            || ncc.getSoDienThoai().toLowerCase().contains(txt.toLowerCase())) {
                        result.add(ncc);
                    }
                    break;
                case "Mã NCC":
                    if (String.valueOf(ncc.getMaNCC()).toLowerCase().contains(txt.toLowerCase())) {
                        result.add(ncc);
                    }
                    break;
                case "Tên NCC":
                    if (ncc.getTenNCC().toLowerCase().contains(txt.toLowerCase())) {
                        result.add(ncc);
                    }
                    break;
                case "Địa chỉ":
                    if (ncc.getDiaChi().toLowerCase().contains(txt.toLowerCase())) {
                        result.add(ncc);
                    }
                    break;
                case "Email":
                    if (ncc.getEmail().toLowerCase().contains(txt.toLowerCase())) {
                        result.add(ncc);
                    }
                    break;
                case "Số điện thoại":
                    if (ncc.getSoDienThoai().toLowerCase().contains(txt.toLowerCase())) {
                        result.add(ncc);
                    }
                    break;
            }
        }
        return result;
    }
}
