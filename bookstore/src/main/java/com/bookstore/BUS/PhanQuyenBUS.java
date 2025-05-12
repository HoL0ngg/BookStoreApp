package com.bookstore.BUS;

import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.NhomQuyenDTO;
import com.bookstore.dao.NhomQuyenDAO;

public class PhanQuyenBUS {
    private List<NhomQuyenDTO> listNhomQuyen;

    public PhanQuyenBUS() {
        listNhomQuyen = new NhomQuyenDAO().selectAll();
    }

    public List<NhomQuyenDTO> getList() {
        return new NhomQuyenDAO().selectAll();
    }

    public List<NhomQuyenDTO> search(String txt, String filter) {
        List<NhomQuyenDTO> result = new ArrayList<>();
        for (NhomQuyenDTO ncc : listNhomQuyen) {
            switch (filter) {
                case "Tất cả":
                    if (String.valueOf(ncc.getMaNhomQuyen()).toLowerCase().contains(txt.toLowerCase())
                            || ncc.getTenNhomQuyen().toLowerCase().contains(txt.toLowerCase())) {
                        result.add(ncc);
                    }
                    break;
                case "Mã nhóm quyền":
                    if (String.valueOf(ncc.getMaNhomQuyen()).toLowerCase().contains(txt.toLowerCase())) {
                        result.add(ncc);
                    }
                    break;
                case "Tên nhóm quyền":
                    if (ncc.getTenNhomQuyen().toLowerCase().contains(txt.toLowerCase())) {
                        result.add(ncc);
                    }
                    break;
            }
        }
        return result;
    }
}
