package com.bookstore;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.bookstore.views.LoginFrame;
import com.formdev.flatlaf.FlatLightLaf;

// import mdlaf.MaterialLookAndFeel;

public class App {
    public static void main(String[] args) {
        // Đặt encoding toàn cục cho ứng dụng
        System.setProperty("file.encoding", "UTF-8");

        try {
            // Thiết lập Material Look and Feel
            UIManager.setLookAndFeel(new FlatLightLaf());

        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame();
            }
        });
    }
}