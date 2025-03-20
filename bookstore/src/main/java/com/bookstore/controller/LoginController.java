package com.bookstore.controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;

import com.bookstore.views.LoginFrame;
import com.bookstore.views.MainFrame;

public class LoginController implements MouseInputListener {
    private com.bookstore.views.LoginFrame loginFrame;

    public LoginController(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == loginFrame.getDangNhapButton()) {
            JOptionPane.showMessageDialog(loginFrame, "Bạn vừa nhấn vào nút đăng nhập",
                    "Thông báo", 1);
            loginFrame.dispose();
            new MainFrame();
        } else if (e.getSource() == loginFrame.getTaoTaiKhoan()) {
            loginFrame.HienTaoTaiKhoan();
        } else if (e.getSource() == loginFrame.getExitButton()) {
            loginFrame.dispose();
        } else if (e.getSource() == loginFrame.getMinimizeButton()) {
            loginFrame.setState(Frame.ICONIFIED);
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
        if (e.getSource() == loginFrame.getExitButton()) {
            loginFrame.getExitButton().setBackground(Color.decode("#FE2020"));
            loginFrame.getExitIcon().setForeground(Color.white);
        } else if (e.getSource() == loginFrame.getMinimizeButton()) {
            loginFrame.getMinimizeButton().setBackground(Color.decode("#6096B4"));
            loginFrame.getMinimizeIcon().setForeground(Color.white);
        }
        // try {
        // ((JPanel) e.getSource()).setBackground(Color.decode("#6F9FBF"));
        // } catch (Exception ex) {
        // System.err.println("Loi ep kieu: " + ex.getMessage());
        // }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        e.getComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        if (e.getSource() == loginFrame.getExitButton()) {
            loginFrame.getExitButton().setBackground(Color.white);
            loginFrame.getExitIcon().setForeground(Color.decode("#6096B4"));
        } else if (e.getSource() == loginFrame.getMinimizeButton()) {
            loginFrame.getMinimizeButton().setBackground(Color.white);
            loginFrame.getMinimizeIcon().setForeground(Color.decode("#6096B4"));
        }
        // try {
        // ((JPanel) e.getSource()).setBackground(Color.decode("#6096B4"));
        // } catch (Exception ex) {
        // System.err.println("Loi ep kieu: " + ex.getMessage());
        // }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
