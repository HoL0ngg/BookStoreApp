package controller;

import java.awt.Cursor;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import view.LoginFrame;

public class LoginController implements MouseInputListener {
    private LoginFrame loginFrame;

    public LoginController(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
