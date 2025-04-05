package com.bookstore.views.Component;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class RoundedLabel extends JLabel {
    private int cornerRadius;

    public RoundedLabel(String text, int cornerRadius) {
        super(text, SwingConstants.CENTER);
        this.cornerRadius = cornerRadius;
        setOpaque(false); // Để ta tự vẽ background bo tròn
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Vẽ viền bo tròn nếu muốn
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getForeground());
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, cornerRadius, cornerRadius);
        g2.dispose();
    }
}
