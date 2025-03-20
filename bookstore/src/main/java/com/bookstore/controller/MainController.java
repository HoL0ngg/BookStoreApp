package com.bookstore.controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.bookstore.views.LoginFrame;
import com.bookstore.views.MainFrame;

public class MainController implements MouseInputListener {
    private MainFrame frame;

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
        if (e.getSource() == frame.getExitButton()) {
            frame.dispose();
        } else if (e.getSource() == frame.getMinimizeButton()) {
            frame.setState(Frame.ICONIFIED);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        e.getComponent().setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        e.getComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
