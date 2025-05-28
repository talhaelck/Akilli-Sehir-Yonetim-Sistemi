package com.mycompany.sehir;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class AdminGirisEkrani extends JFrame {
    private Admin admin;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public AdminGirisEkrani(Admin admin) {
        if (admin == null) {
            throw new IllegalArgumentException("Admin nesnesi null olamaz!");
        }
        this.admin = admin;
        setupUI();
        setVisible(true);
    }

    private void setupUI() {
        setTitle("Admin Giriş");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ana panel - futuristik arka plan
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Gradient arka plan
                GradientPaint gp = new GradientPaint(
                    0, 0, new Color(20, 30, 48),
                    getWidth(), getHeight(), new Color(36, 59, 85)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Dekoratif grid çizgileri
                g2d.setColor(new Color(255, 255, 255, 15));
                for (int i = 0; i < getHeight(); i += 30) {
                    g2d.drawLine(0, i, getWidth(), i);
                }
                for (int i = 0; i < getWidth(); i += 30) {
                    g2d.drawLine(i, 0, i, getHeight());
                }
            }
        };
        mainPanel.setBorder(new EmptyBorder(50, 50, 50, 50));

        // Login kartı
        JPanel loginCard = createLoginCard();
        
        // Login kartını ortalamak için wrapper panel
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(loginCard);

        mainPanel.add(wrapperPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private JPanel createLoginCard() {
        JPanel card = new JPanel(new BorderLayout(0, 30)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Yarı saydam siyah arka plan
                g2d.setColor(new Color(0, 0, 0, 150));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Parlak kenar
                g2d.setColor(new Color(255, 255, 255, 50));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 20, 20);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        card.setPreferredSize(new Dimension(500, 600));

        // Form içeriği
        JPanel formContent = new JPanel();
        formContent.setLayout(new BoxLayout(formContent, BoxLayout.Y_AXIS));
        formContent.setOpaque(false);

        // Başlık
        JLabel titleLabel = new JLabel("Admin Girişi");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Alt başlık
        JLabel subtitleLabel = new JLabel("Sistem yönetimi için giriş yapın");
        subtitleLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(200, 200, 200));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form alanları için wrapper panel
        JPanel fieldsWrapper = new JPanel();
        fieldsWrapper.setLayout(new BoxLayout(fieldsWrapper, BoxLayout.Y_AXIS));
        fieldsWrapper.setOpaque(false);
        fieldsWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        fieldsWrapper.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));

        // Kullanıcı adı alanı
        txtUsername = new JTextField();
        styleTextField(txtUsername);
        JPanel usernamePanel = createFormField("Kullanıcı Adı", txtUsername);
        fieldsWrapper.add(usernamePanel);
        fieldsWrapper.add(Box.createVerticalStrut(20));

        // Şifre alanı
        txtPassword = new JPasswordField();
        styleTextField(txtPassword);
        JPanel passwordPanel = createFormField("Şifre", txtPassword);
        fieldsWrapper.add(passwordPanel);

        // Butonlar
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setMaximumSize(new Dimension(400, 40));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnGiris = createStyledButton("Giriş", new Color(41, 128, 185));
        JButton btnGeri = createStyledButton("Geri", new Color(231, 76, 60));

        buttonPanel.add(btnGiris);
        buttonPanel.add(btnGeri);

        // Form elemanlarını panele ekle
        formContent.add(titleLabel);
        formContent.add(Box.createVerticalStrut(10));
        formContent.add(subtitleLabel);
        formContent.add(Box.createVerticalStrut(50));
        formContent.add(fieldsWrapper);
        formContent.add(Box.createVerticalStrut(40));
        formContent.add(buttonPanel);

        card.add(formContent, BorderLayout.CENTER);

        // Buton aksiyonları
        setupButtonActions(btnGiris, btnGeri);

        return card;
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.WHITE);
        field.setBackground(new Color(44, 62, 80));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 50)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        field.setCaretColor(Color.WHITE);
        field.setMaximumSize(new Dimension(400, 40));
        field.setOpaque(true);
    }

    private JPanel createFormField(String labelText, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setMaximumSize(new Dimension(400, 80));
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(200, 200, 200));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(field);
        
        return panel;
    }

    private JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(baseColor.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(baseColor.brighter());
                } else {
                    g2d.setColor(baseColor);
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(getText(), x, y);
            }
        };
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 40));
        
        return button;
    }

    private void setupButtonActions(JButton btnGiris, JButton btnGeri) {
        btnGiris.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            
            if (username.equals("talha") && password.equals("talha123")) {
                JOptionPane.showMessageDialog(this, "Giriş başarılı!");
                dispose();
                SwingUtilities.invokeLater(() -> new AdminPaneli(admin));
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Hatalı kullanıcı adı veya şifre!", 
                    "Giriş Hatası", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        btnGeri.addActionListener(e -> {
            new AnaGirisEkrani().setVisible(true);
            dispose();
        });
    }
}