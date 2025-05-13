package com.bookstore.BUS;

import java.util.List;

import com.bookstore.DTO.DocGiaDTO;
import com.bookstore.dao.DocGiaDAO;

public class DocGiaBUS {
    List<DocGiaDTO> listtmp;

    public DocGiaBUS() {
        listtmp = new DocGiaDAO().getAllDocGia();
    }

    public List<DocGiaDTO> getList() {
        return listtmp;
    }
}
