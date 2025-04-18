package com.bookstore.views.Panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import com.bookstore.DTO.TacGiaDTO;
import com.bookstore.dao.TacGiaDAO;
import com.bookstore.views.Component.Header;

public class TacGia extends JPanel {

    private TacGiaDTO selectedTacGia = null;
    private String currentSearchType = "Tất cả";
    private String currentKeyword = "";

    private JPanel contentPanel; // chứa danh sách tác giả

    public TacGia() {
        initComponent();
        loadData(currentSearchType, currentKeyword);
    }

    public void initComponent() {
        this.removeAll();   
        this.setBackground(new Color( 248, 249, 249 ));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(900, 600));

        Header header = new Header();
        header.setPreferredSize(new Dimension(900, 100));
        header.setMaximumSize(new Dimension(900, 100));
        header.setBackground(new Color(  234, 237, 237));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(15));
        add(header);

        // Nút Thêm
        header.getBtnAdd().addActionListener(e -> {
            new AddEditTacGiaDialog(null).setVisible(true); // ?? why setVible
            loadData(currentSearchType, currentKeyword);
        });

        // Nút Sửa
        header.getBtnEdit().addActionListener(e -> {
            if (selectedTacGia != null) {
                new AddEditTacGiaDialog(selectedTacGia).setVisible(true);
                loadData(currentSearchType, currentKeyword);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn tác giả để sửa.");
            }
        });

        // Nút Xóa
        header.getBtnDelete().addActionListener(e -> {
            if (selectedTacGia != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc chắn muốn xóa tác giả này?", "Xác nhận",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    TacGiaDAO dao = new TacGiaDAO();
                    int result = dao.delete(Integer.parseInt(selectedTacGia.getMaTacGia()));  // Chuyển MaTacGia thành int
                    if (result > 0) {
                        JOptionPane.showMessageDialog(this, "Xóa thành công!");
                        loadData(currentSearchType, currentKeyword);
                    } else {
                        JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn tác giả để xóa.");
            }
        });

        header.getBtnRefresh().addActionListener(e -> {
            String selectedSearchType = (String) header.getSortComboBox().getSelectedItem();
            String inputKeyword = header.getSearchField().getText().trim();
            currentSearchType = selectedSearchType;
            currentKeyword = inputKeyword;
            loadData(currentSearchType, currentKeyword);
        });

        header.getBtnDetail().addActionListener(e -> {
            if (selectedTacGia != null) {
                new Detail_TacGia(null, selectedTacGia.getMaTacGia(), selectedTacGia.getTenTacGia()).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,"Vui lòng chọn một tác giả để xem chi tiết.");
            }
        });

        // Tiêu đề
        JPanel titlePanel = new JPanel(new GridLayout(1, 4, 10, 0));
        titlePanel.setPreferredSize(new Dimension(900, 30));
        titlePanel.setMaximumSize(new Dimension(900, 30));
        titlePanel.setBackground(new Color(234, 237, 237));

        titlePanel.add(createTitleLabel("Mã TG"));
        titlePanel.add(createTitleLabel("Tên"));
        titlePanel.add(createTitleLabel("Năm sinh"));
        titlePanel.add(createTitleLabel("Quốc tịch"));

        add(Box.createVerticalStrut(10));
        add(titlePanel);
        add(Box.createVerticalStrut(2));

        // Panel chứa danh sách
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(234, 237, 237));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(900, 400));
        add(scrollPane);
    }

    public void loadData(String searchType, String keyword) {
        contentPanel.removeAll();
        selectedTacGia = null;

        List<TacGiaDTO> list = new TacGiaDAO().selectAll();

        if (!searchType.equals("Tất cả") && !keyword.isEmpty()) {
            list = filter(list, searchType, keyword);
        }   

        List<JPanel> rowPanels = new ArrayList<>();
        for (TacGiaDTO tg : list) {
            JPanel rowPanel = new JPanel(new GridLayout(1, 4, 10, 0));
            rowPanel.setPreferredSize(new Dimension(900, 35));
            rowPanel.setMaximumSize(new Dimension(900, 35));
            rowPanel.setBackground(Color.WHITE);

            JLabel lbl1 = createDataLabel(tg.getMaTacGia());
            JLabel lbl2 = createDataLabel(tg.getTenTacGia());
            JLabel lbl3 = createDataLabel(String.valueOf(tg.getNamSinh()));
            JLabel lbl4 = createDataLabel(tg.getQuocTich());

            rowPanel.add(lbl1);
            rowPanel.add(lbl2);
            rowPanel.add(lbl3);
            rowPanel.add(lbl4);

            rowPanels.add(rowPanel);

            rowPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    for (JPanel panel : rowPanels) {
                        panel.setBackground(Color.WHITE);
                        for (Component comp : panel.getComponents()) {
                            if (comp instanceof JLabel) {
                                JLabel label = (JLabel) comp;
                                label.setFont(new Font("Arial", Font.PLAIN, 14));
                                label.setBackground(Color.WHITE);
                            }
                        }
                    }

                    rowPanel.setBackground(new Color(234, 237, 237));
                    for (Component comp : rowPanel.getComponents()) {
                        if (comp instanceof JLabel) {
                            JLabel label = (JLabel) comp;
                            label.setFont(new Font("Arial", Font.BOLD, 14));
                            label.setBackground(new Color(234, 237, 237));
                        }
                    }
                    selectedTacGia = tg;
                }
            });

            contentPanel.add(rowPanel);
            contentPanel.add(Box.createVerticalStrut(3));
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private List<TacGiaDTO> filter(List<TacGiaDTO> list, String searchType, String keyword) {
        List<TacGiaDTO> filteredList = new ArrayList<>();
        for (TacGiaDTO tg : list) {
            if (searchType.equals("Tên TG") && tg.getTenTacGia().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(tg);
            } else if (searchType.equals("Mã TG") && tg.getMaTacGia().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(tg);
            } else if (searchType.equals("Ngày sinh") && String.valueOf(tg.getNamSinh()).contains(keyword)) {
                filteredList.add(tg);
            } else if (searchType.equals("Quốc tịch") && tg.getQuocTich().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(tg);
            }
        }
        return filteredList;
    }

    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setOpaque(true);
        label.setBackground(new Color(234, 237, 237));
        return label;
    }

    private JLabel createDataLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        return label;
    }
}
