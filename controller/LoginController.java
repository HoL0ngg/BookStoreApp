package controller;

import java.awt.Cursor;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;

import view.LoginFrame;
import view.MainFrame;

public class LoginController implements MouseInputListener {
    private LoginFrame loginFrame;

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
