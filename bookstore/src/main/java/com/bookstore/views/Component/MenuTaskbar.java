package com.bookstore.views.Component;

import com.bookstore.dao.NhomQuyenDAO;
import com.bookstore.utils.NguoiDungDangNhap;
import com.bookstore.views.MainFrame;
import com.bookstore.views.Panel.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MenuTaskbar extends JPanel {

    private MainFrame mainFrame;
    String[][] getSt = {
            { "Trang chủ", "homepage.svg", "trangchu" },
            { "Sách", "book.svg", "sach" },
            { "Tác giả", "author.svg", "tacgia" },
            { "Độc giả", "docgia.svg", "docgia" },
            { "Nhà cung cấp", "supplier.svg", "ncc" },
            { "Phiếu nhập", "import.svg", "nhap" },
            { "Phiếu mượn", "loan.svg", "loan" },
            { "Phiếu trả", "payment.svg", "tra" },
            { "Phiếu hủy", "phieuhuy.svg", "huy" },
            { "Phiếu phạt", "phieuphat.svg", "phat" },
            { "Phân quyền", "permission.svg", "phanquyen" },
            { "Tài khoản", "account.svg", "taikhoan" },
            { "Thống kê", "statistic.svg", "thongke" }
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

        NhomQuyenDAO nhomQuyenDAO = new NhomQuyenDAO();
        listItem[0] = new itemTaskbar(getSt[0][1], getSt[0][0]);
        listItem[0].setVisible(true);
        pnCenter.add(listItem[0]);
        System.out.println(getSt.length);
        for (int i = 1; i < getSt.length - 1; i++) {
            // if (!nhomQuyenDAO.isVisible(NguoiDungDangNhap.getInstance().getMaNhomQuyen(),
            //         i)) {
            //     continue;
            // }
            listItem[i] = new itemTaskbar(getSt[i][1], getSt[i][0]);
            listItem[i].setVisible(true);
            pnCenter.add(listItem[i]);
        }
        listItem[12] = new itemTaskbar(getSt[12][1], getSt[12][0]);
        listItem[12].setVisible(true);
        pnCenter.add(listItem[12]);

        // Thêm MouseListener cho từng mục
        listItem[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnMenuTaskbarMousePress(e);
                TrangChu trangchu = new TrangChu();
                mainFrame.setPanel(trangchu);
            }
        });
        if (listItem[1] != null)
            listItem[1].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    pnMenuTaskbarMousePress(e);
                    Sach Sach = new Sach();
                    mainFrame.setPanel(Sach);
                }
            });
        if (listItem[2] != null)
            listItem[2].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    pnMenuTaskbarMousePress(e);
                    TacGia TacGia = new TacGia();
                    mainFrame.setPanel(TacGia);
                }
            });
        if (listItem[3] != null)
            listItem[3].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    pnMenuTaskbarMousePress(e);
                    DocGia DocGia = new DocGia();
                    mainFrame.setPanel(DocGia);
                }
            });
        if (listItem[4] != null)
            listItem[4].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    pnMenuTaskbarMousePress(e);
                    NhaCungCap NhaCungCap = new NhaCungCap();
                    mainFrame.setPanel(NhaCungCap);
                }
            });
        if (listItem[5] != null)
            listItem[5].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    pnMenuTaskbarMousePress(e);
                    PhieuNhap PhieuNhap = new PhieuNhap();
                    mainFrame.setPanel(PhieuNhap);
                }
            });

        if (listItem[6] != null)
            listItem[6].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    pnMenuTaskbarMousePress(e);
                    PhieuMuon PhieuMuon = new PhieuMuon();
                    mainFrame.setPanel(PhieuMuon);
                }
            });

        if (listItem[7] != null)
            listItem[7].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    pnMenuTaskbarMousePress(e);
                    PhieuTra PhieuTra = new PhieuTra();
                    mainFrame.setPanel(PhieuTra);
                }
            });

        if (listItem[8] != null)
            listItem[8].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    pnMenuTaskbarMousePress(e);
                    PhieuHuyPanel PhieuHuy = new PhieuHuyPanel();
                    mainFrame.setPanel(PhieuHuy);
                }
            });
        if (listItem[9] != null)
            listItem[9].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    pnMenuTaskbarMousePress(e);
                    PhieuPhat PhieuPhat = new PhieuPhat();
                    mainFrame.setPanel(PhieuPhat);
                }
            });
        if (listItem[10] != null)
            listItem[10].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    pnMenuTaskbarMousePress(e);
                    PhanQuyen PhanQuyen = new PhanQuyen();
                    mainFrame.setPanel(PhanQuyen);
                }
            });
        if (listItem[11] != null)
            listItem[11].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    pnMenuTaskbarMousePress(e);
                    TaiKhoan TaiKhoan = new TaiKhoan();
                    mainFrame.setPanel(TaiKhoan);
                }
            });
        listItem[12].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnMenuTaskbarMousePress(e);
                ThongKePanel thongke = new ThongKePanel();
                mainFrame.setPanel(thongke);
            }
        });
    }

    public void pnMenuTaskbarMousePress(MouseEvent e) {
        for (int i = 0; i < getSt.length; i++) {
            if (listItem[i] == null)
                continue;
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