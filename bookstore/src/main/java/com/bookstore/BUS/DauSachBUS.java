package com.bookstore.BUS;

import java.util.ArrayList;
import java.util.List;

import com.bookstore.DTO.DauSachDTO; // Ensure this is the correct package for DauSachDTO
import com.bookstore.dao.DauSachDAO;

public class DauSachBUS {
    private List<DauSachDTO> listDauSach;

    public DauSachBUS() {
        listDauSach = new DauSachDAO().selectAll();
    }

    public List<DauSachDTO> searchDauSach(String txt, String filter) {
        List<DauSachDTO> result = new ArrayList<>();
        for (DauSachDTO ds : listDauSach) {
            switch (filter) {
                case "Tất cả":
                    if (ds.getMaDauSach().toLowerCase().contains(txt.toLowerCase())
                            || ds.getTenDauSach().toLowerCase().contains(txt.toLowerCase())
                            || ds.getNhaXuatBan().toLowerCase().contains(txt.toLowerCase())) {
                        result.add(ds);
                    }
                    break;
                case "Mã đầu sách":
                    if (ds.getMaDauSach().toLowerCase().contains(txt.toLowerCase())) {
                        result.add(ds);
                    }
                    break;
                case "Tên đầu sách":
                    if (ds.getTenDauSach().toLowerCase().contains(txt.toLowerCase())) {
                        result.add(ds);
                    }
                    break;
                // case "Tác giả":
                // if (ds.get().toLowerCase().contains(txt.toLowerCase())) {
                // result.add(ds);
                // }
                // break;
                case "Nhà xuất bản":
                    if (ds.getNhaXuatBan().toLowerCase().contains(txt.toLowerCase())) {
                        result.add(ds);
                    }
                    break;
            }
        }
        return result;
    }

    public boolean delete(String maDauSach) {
        for (DauSachDTO ds : listDauSach) {
            if (ds.getMaDauSach().equals(maDauSach)) {
                listDauSach.remove(ds);
                DauSachDAO dsDAO = new DauSachDAO();
                if (dsDAO.delete(maDauSach) > 0) {
                    return true;
                } else {
                    listDauSach.add(ds); // Add back if delete fails
                }
            }
        }
        return false;
    }

    public boolean update(DauSachDTO ds) {
        for (int i = 0; i < listDauSach.size(); i++) {
            if (listDauSach.get(i).getMaDauSach().equals(ds.getMaDauSach())) {
                listDauSach.set(i, ds);
                DauSachDAO dsDAO = new DauSachDAO();
                if (dsDAO.update(ds) > 0) {
                    return true;
                } else {
                    listDauSach.set(i, ds); // Add back if update fails
                }
            }
        }
        return false;
    }
}
