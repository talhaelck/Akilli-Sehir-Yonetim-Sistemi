package com.mycompany.sehir;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class AnaGirisEkrani extends JFrame {
    private static Admin admin;

    public AnaGirisEkrani() {
        if (admin == null) {
            admin = new Admin("Talha", "12345", "talha", "talha123");
        }
        setupUI();
        setVisible(true);
    }

    private void setupUI() {
        setTitle("Akıllı Şehir Yönetim Sistemi");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ana panel - futuristik arka plan
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Koyu gradient arka plan
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

        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = createContentPanel();
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(0, 20));
        headerPanel.setOpaque(false);

        // Ana başlık
        JLabel titleLabel = new JLabel("Akıllı Şehir Yönetim Sistemi");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Alt başlık
        JLabel subtitleLabel = new JLabel("Geleceğin Şehri, Bugünün Teknolojisi");
        subtitleLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 24));
        subtitleLabel.setForeground(new Color(236, 240, 241));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        return headerPanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.add(Box.createVerticalGlue());

        // Menü kartları container
        JPanel cardsPanel = new JPanel(new GridLayout(1, 2, 40, 0));
        cardsPanel.setOpaque(false);
        cardsPanel.setMaximumSize(new Dimension(1000, 400));

        // Admin Giriş Kartı
        cardsPanel.add(createMenuCard(
            "Admin Girişi",
            "Sistem yönetimi ve kontrol paneline erişim sağlayın",
            new Color(41, 128, 185),
            e -> {
                new AdminGirisEkrani(admin).setVisible(true);
                dispose();
            }
        ));

        // Vatandaş Giriş Kartı
        cardsPanel.add(createMenuCard(
            "Vatandaş Girişi",
            "Şehir hizmetleri ve durum bilgilerine erişin",
            new Color(46, 204, 113),
            e -> {
                new VatandasGirisEkrani(admin).setVisible(true);
                dispose();
            }
        ));

        contentPanel.add(cardsPanel);
        contentPanel.add(Box.createVerticalGlue());

        // Çıkış kartı
        JPanel exitPanel = new JPanel();
        exitPanel.setLayout(new BoxLayout(exitPanel, BoxLayout.X_AXIS));
        exitPanel.setOpaque(false);

        JPanel exitCard = createMenuCard(
            "Çıkış",
            "Uygulamadan güvenli çıkış yapın",
            new Color(231, 76, 60),
            e -> System.exit(0)
        );
        exitCard.setPreferredSize(new Dimension(300, 100));
        exitCard.setMaximumSize(new Dimension(300, 100));

        exitPanel.add(Box.createHorizontalGlue());
        exitPanel.add(exitCard);
        exitPanel.add(Box.createHorizontalGlue());

        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(exitPanel);

        return contentPanel;
    }

    private JPanel createMenuCard(String title, String description, Color baseColor, ActionListener action) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Gradient arka plan
                GradientPaint gp = new GradientPaint(
                    0, 0, 
                    new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 200),
                    getWidth(), getHeight(),
                    new Color(baseColor.getRed()/2, baseColor.getGreen()/2, baseColor.getBlue()/2, 200)
                );
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            }
        };
        
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // İçerik paneli
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        // Başlık
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Açıklama
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        descLabel.setForeground(new Color(240, 240, 240));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(descLabel);
        contentPanel.add(Box.createVerticalGlue());

        card.add(contentPanel, BorderLayout.CENTER);

        // Hover ve tıklama efektleri
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                action.actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, null));
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createEmptyBorder(20, 25, 30, 25));
                contentPanel.setBackground(new Color(255, 255, 255, 30));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
                contentPanel.setBackground(new Color(0, 0, 0, 0));
            }
        });

        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                System.err.println("Failed to initialize LaF");
            }
            new AnaGirisEkrani();
        });
    }
}