package com.mycompany.sehir.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

import com.mycompany.sehir.utils.UIConstants;

public class ModernButton extends JButton {
    private Color normalColor = new Color(41, 128, 185);    // Varsayılan mavi
    private Color hoverColor = new Color(52, 152, 219);     // Açık mavi
    private Color textColor = Color.WHITE;                  // Beyaz yazı

    public ModernButton(String text, Icon icon) {
        super(text);
        setIcon(icon);
        setupStyle();
    }
    
    private void setupStyle() {
        setFont(UIConstants.BUTTON_FONT);
        setForeground(textColor);
        setBackground(normalColor);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Arka plan çizimi
        if (getModel().isPressed()) {
            g2d.setColor(hoverColor.darker());
        } else if (getModel().isRollover()) {
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(normalColor);
        }

        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

        // Yazı rengini ayarla
        setForeground(textColor);
        
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        size.height = Math.max(size.height, 40); // Minimum yükseklik
        return size;
    }

    // Renk ayarlama metodları
    public void setButtonColors(Color normal, Color hover, Color text) {
        this.normalColor = normal;
        this.hoverColor = hover;
        this.textColor = text;
        setForeground(textColor);
        repaint();
    }
} 