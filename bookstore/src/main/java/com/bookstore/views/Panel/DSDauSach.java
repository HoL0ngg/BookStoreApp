package com.bookstore.views.Panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import com.bookstore.DTO.DauSachDTO;
import com.bookstore.DTO.SachDTO;
import com.bookstore.dao.DauSachDAO;
import com.bookstore.dao.SachDAO;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class DSDauSach extends JPanel {
    List<DauSachDTO> list = new DauSachDAO().selectAll();
    private CardLayout cardLayout;
    private JLabel back;
    private JTable table;
    private DefaultTableModel tableModel;
    private JPanel mainPanel;

    public DSDauSach() {
        init();
    }

    private void init() {
        this.setLayout(new BorderLayout());
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel DSDauSachPanel = new JPanel();

        DSDauSachPanel.setLayout(new FlowLayout(0, 32, 40));
        DSDauSachPanel.setPreferredSize(new Dimension(900, ((list.size() + 5) / 3) * 300));
        DSDauSachPanel.setBackground(Color.white);

        ThongTinDauSach[] ThongTinDauSach = new ThongTinDauSach[list.size()];

        for (int i = 0; i < list.size(); ++i) {
            final int index = i;
            ThongTinDauSach[i] = new ThongTinDauSach(list.get(index));
            ThongTinDauSach[i].addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    updateTable(list.get(index).getMaDauSach());
                    cardLayout.show(mainPanel, "Table");
                }
            });
            DSDauSachPanel.add(ThongTinDauSach[i]);
        }
        JScrollPane DSDauSach = new JScrollPane(DSDauSachPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        DSDauSach.setPreferredSize(new Dimension(900, 600));

        // Panel chứa bảng sách (chỉ có 1 bảng duy nhất)
        JPanel tablePanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBackground(Color.white);
        headerPanel.setPreferredSize(new Dimension(900, 50));
        tablePanel.add(headerPanel, BorderLayout.NORTH);

        FlatSVGIcon backIcon = new FlatSVGIcon(getClass().getResource("/svg/back-button.svg")).derive(30, 30);
        back = new JLabel(backIcon);
        back.setBounds(10, 10, 30, 30);
        headerPanel.add(back);

        JLabel backText = new JLabel("Quay lại");
        backText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        backText.setBounds(50, 10, 100, 30);
        headerPanel.add(backText);

        back.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "DanhSach");
            }
        });

        String column[] = new String[] { "Mã sách", "Ngày nhập", "Trạng thái" };
        tableModel = new DefaultTableModel(column, 0);
        table = new JTable(tableModel);
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // CardLayout để chuyển đổi giữa danh sách đầu sách và bảng sách
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(DSDauSach, "DanhSach");
        mainPanel.add(tablePanel, "Table");

        this.add(mainPanel, BorderLayout.CENTER);
    }

    // Cập nhật dữ liệu bảng khi chọn đầu sách
    private void updateTable(int MadauSach) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<SachDTO> hihi = new SachDAO().selectByDauSach(MadauSach);
        for (SachDTO sach : hihi) {
            tableModel.addRow(new Object[] {
                    sach.getMaSach(),
                    sach.getNgayNhap(),
                    sach.getTrangThai()
            });
        }
    }
}
