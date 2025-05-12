
package com.bookstore.BUS;

import java.util.List;
import java.util.Map;

import com.bookstore.DTO.TacGiaDTO;
import com.bookstore.dao.TacGiaDAO;

public class TacGiaBUS {
    private TacGiaDAO dao;

    public TacGiaBUS() {
        dao = TacGiaDAO.getInstance();
    }

    // Lấy toàn bộ danh sách tác giả còn hoạt động
    public List<TacGiaDTO> getAllTacGia() {
        return dao.selectAll();
    }

    // Tìm kiếm tác giả theo mã
    public TacGiaDTO getTacGiaById(String maTacGia) {
        return dao.selectById(maTacGia);
    }

    // Thêm tác giả
    public boolean addTacGia(TacGiaDTO tg) {
        return dao.insert(tg) > 0;
    }

    // Sửa tác giả
    public boolean updateTacGia(TacGiaDTO tg) {
        return dao.update(tg) > 0;
    }

    // Xóa mềm tác giả
    public boolean deleteTacGia(String maTacGia) {
        return dao.delete(maTacGia) > 0;
    }

    // Lấy danh sách tên đầu sách mà tác giả đã sáng tác
    public List<String> getDauSachByTacGia(String maTacGia) {
        return dao.selectByMaTacGia(maTacGia);
    }

    // Lấy thông tin mã - tên tác giả để đổ vào combobox
    public Map<String, String> getMapTacGia() {
        return dao.getThongTin();
    }

    // Thêm quan hệ giữa tác giả và đầu sách (nếu cần)
    public boolean addTacGiaDauSach(String maDauSach, String maTacGia) {
        return dao.insertDauSach(maDauSach, maTacGia) > 0;
    }
}

