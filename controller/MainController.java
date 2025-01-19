package controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import view.LoginFrame;
import view.MainFrame;

public class MainController implements MouseInputListener {
    private MainFrame frame;
    private JPanel PanelDangChon;

    public MainController(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == frame.getDangXuatPanel()) {
            frame.dispose();
            new LoginFrame();
            return;
        }
        PanelDangChon = ((JPanel) e.getSource());
        frame.resetPanel();
        ((JPanel) e.getSource()).setBackground(Color.decode("#6096B4"));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ((JPanel) e.getSource()).setBackground(Color.decode("#80A6C4"));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ((JPanel) e.getSource()).setBackground(Color.decode("#6096B4"));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        e.getComponent().setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (e.getSource() != PanelDangChon)
            ((JPanel) e.getSource()).setBackground(Color.decode("#6096B4"));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        e.getComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        if (e.getSource() != PanelDangChon)
            ((JPanel) e.getSource()).setBackground(Color.decode("#93BFCF"));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
