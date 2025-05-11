package com.bookstore.views.Component;

import com.bookstore.views.MainFrame;
import com.bookstore.views.Panel.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuTaskbar extends JPanel {

    private MainFrame mainFrame;
    String[][] getSt = {
            { "Trang chủ", "homepage.svg", "trangchu" },
            { "Sách", "book.svg", "tau" },
            { "Tác giả", "author.svg", "tram" },
            { "Độc giả", "docgia.svg", "docgia" },
            { "Nhà cung cấp", "supplier.svg", "ncc" },
            { "Phiếu nhập", "import.svg", "nhap" },
            { "Phiếu mượn", "loan.svg", "loan" },
            { "Phiếu trả", "payment.svg", "tra" },
            { "Phiếu hủy", "phieuhuy.svg", "huy" },
            { "Phiếu phạt", "phieuphat.svg", "phat" },
            { "Phân quyền", "permission.svg", "phanquyen" },
            { "Tài khoản", "account.svg", "taikhoan" },
    };
    public itemTaskbar[] listItem;
    JPanel pnCenter;

    Color FontColor = new Color(0, 0, 0);
    Color DefaultColor = new Color(148, 183, 205);
    Color HoverFontColor = new Color(200, 222, 231);
    Color HoverBackgroundColor = new Color(80, 138, 170);

    public MenuTaskbar(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponent();
    }

    public void initComponent() {
        listItem = new itemTaskbar[getSt.length];
        this.setBackground(DefaultColor);
        this.setOpaque(true);
        this.setLayout(new BorderLayout(0, 0));

        pnCenter = new JPanel();
        pnCenter.setBackground(DefaultColor);
        pnCenter.setLayout(new GridLayout(getSt.length, 1, 0, 0)); // Không có khoảng cách giữa các hàng
        this.add(pnCenter, BorderLayout.CENTER); // Thêm pnCenter vào MenuTaskbar

        for (int i = 0; i < getSt.length; i++) {
            listItem[i] = new itemTaskbar(getSt[i][1], getSt[i][0]);
            listItem[i].setVisible(true);
            pnCenter.add(listItem[i]);
        }

        // Thêm MouseListener cho từng mục
        listItem[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnMenuTaskbarMousePress(e);
                TrangChu trangchu = new TrangChu();
                mainFrame.setPanel(trangchu);
            }
        });
        listItem[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnMenuTaskbarMousePress(e);
                Sach Sach = new Sach();
                mainFrame.setPanel(Sach);
            }
        });
        listItem[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnMenuTaskbarMousePress(e);
                TacGia TacGia = new TacGia();
                mainFrame.setPanel(TacGia);
            }
        });
        listItem[3].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnMenuTaskbarMousePress(e);
                DocGia DocGia = new DocGia();
                mainFrame.setPanel(DocGia);
            }
        });
        listItem[4].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnMenuTaskbarMousePress(e);
                NhaCungCap NhaCungCap = new NhaCungCap();
                mainFrame.setPanel(NhaCungCap);
            }
        });
        listItem[5].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnMenuTaskbarMousePress(e);
                PhieuNhap PhieuNhap = new PhieuNhap();
                mainFrame.setPanel(PhieuNhap);
            }
        });

        listItem[6].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnMenuTaskbarMousePress(e);
                PhieuMuon PhieuMuon = new PhieuMuon();
                mainFrame.setPanel(PhieuMuon);
            }
        });

        listItem[7].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnMenuTaskbarMousePress(e);
                PhieuTra PhieuTra = new PhieuTra();
                mainFrame.setPanel(PhieuTra);
            }
        });

        listItem[8].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnMenuTaskbarMousePress(e);
                PhieuHuyPanel PhieuHuy = new PhieuHuyPanel();
                mainFrame.setPanel(PhieuHuy);
            }
        });
        listItem[9].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnMenuTaskbarMousePress(e);
                PhieuPhat PhieuPhat = new PhieuPhat();
                mainFrame.setPanel(PhieuPhat);
            }
        });
        listItem[10].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnMenuTaskbarMousePress(e);
                PhanQuyen PhanQuyen = new PhanQuyen();
                mainFrame.setPanel(PhanQuyen);
            }
        });
        listItem[11].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnMenuTaskbarMousePress(e);
                TaiKhoan TaiKhoan = new TaiKhoan();
                mainFrame.setPanel(TaiKhoan);
            }
        });
    }

    public void pnMenuTaskbarMousePress(MouseEvent e) {
        for (int i = 0; i < getSt.length; i++) {
            if (e.getSource() == listItem[i]) {
                listItem[i].setBackground(HoverBackgroundColor);
                listItem[i].lblContent.setForeground(HoverFontColor); // Cập nhật màu chữ
            } else {
                listItem[i].setBackground(DefaultColor);//
                listItem[i].lblContent.setForeground(FontColor);
            }
        }
    }
}