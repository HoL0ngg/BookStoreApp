package com.bookstore.BUS;

import java.util.List;

import com.bookstore.DTO.PhieuPhatDTO;
import com.bookstore.dao.PhieuPhatDAO;

public class PhieuPhatBUS {
    List<PhieuPhatDTO> listpp;

    public PhieuPhatBUS() {
        listpp = new PhieuPhatDAO().getList();
    }

    public List<PhieuPhatDTO> getList() {
        return listpp;
    }

    public boolean thempp(PhieuPhatDTO pp) {
        listpp.add(pp);
        if (new PhieuPhatDAO().thempp(pp)) {
            return true;
        }
        return false;
    }

    public boolean suapp(PhieuPhatDTO pp) {
        if (new PhieuPhatDAO().suapp(pp)) {
            return true;
        }
        return false;
    }

    public boolean xoapp(int mapp) {
        if (new PhieuPhatDAO().xoapp(mapp)) {
            return true;
        }
        return false;
    }

}
