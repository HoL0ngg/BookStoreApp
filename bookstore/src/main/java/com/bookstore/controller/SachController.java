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
        }
    }

}
