package com.mycompany.sehir;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mycompany.sehir.components.ModernButton;

public class VatandasPaneli extends JFrame {
    private final Vatandas vatandas;
    private final Admin admin;
    private JPanel contentPanel;

    public VatandasPaneli(Vatandas vatandas, Admin admin) {
        this.vatandas = vatandas;
        this.admin = admin;
        setupUI();
        setVisible(true);
    }

    private void setupUI() {
        setTitle("Vatandaş Paneli - " + vatandas.getIsim());
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header Panel
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        // Content Panel - Dashboard
        contentPanel = createDashboardPanel();
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(41, 128, 185), getWidth(), 0, new Color(44, 62, 80));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 80));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(15, 25, 15, 25));

        // Sol tarafta başlık
        JLabel titleLabel = new JLabel("Hoş Geldiniz, " + vatandas.getIsim());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        // Sağ tarafta butonlar
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        navPanel.setOpaque(false);

        // Geri butonu
        ModernButton btnGeri = new ModernButton("← Geri", null);
        btnGeri.setPreferredSize(new Dimension(100, 30));
        btnGeri.addActionListener(e -> dispose());

        // Ana Menü butonu
        ModernButton btnAnaMenu = new ModernButton("Ana Menü", null);
        btnAnaMenu.setPreferredSize(new Dimension(120, 30));
        btnAnaMenu.addActionListener(e -> {
            new AnaGirisEkrani().setVisible(true);
            dispose();
        });

        navPanel.add(btnGeri);
        navPanel.add(btnAnaMenu);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(navPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createDashboardPanel() {
        JPanel dashboardPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        dashboardPanel.setOpaque(false);
        dashboardPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Durum kartları
        dashboardPanel.add(createStatusCard("Enerji Durumu", 
            "⚡ Enerji üretim ve tüketim durumunu görüntüleyin", 
            new Color(41, 128, 185), 
            () -> enerjiDurumuKontrol()));

        dashboardPanel.add(createStatusCard("Su Durumu", 
            "💧 Su rezervi ve tüketim durumunu görüntüleyin", 
            new Color(46, 204, 113), 
            () -> suDurumuKontrol()));

        dashboardPanel.add(createStatusCard("Trafik Durumu", 
            "🚗 Anlık trafik durumunu görüntüleyin", 
            new Color(230, 126, 34), 
            () -> trafikKontrol()));

        // Bilgi kartı
        dashboardPanel.add(createInfoCard());

        return dashboardPanel;
    }

    private JPanel createStatusCard(String title, String description, Color baseColor, Runnable action) {
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
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        card.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // İçerik paneli
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        // Başlık
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(0.5f);

        // Açıklama
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setForeground(new Color(240, 240, 240));
        descLabel.setAlignmentX(0.5f);

        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(descLabel);
        contentPanel.add(Box.createVerticalGlue());

        card.add(contentPanel, BorderLayout.CENTER);

        // Tıklama efekti
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                action.run();
            }
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                contentPanel.setBackground(new Color(255, 255, 255, 30));
                card.setBorder(BorderFactory.createEmptyBorder(15, 20, 25, 20));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                contentPanel.setBackground(new Color(0, 0, 0, 0));
                card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            }
        });

        return card;
    }

    private JPanel createInfoCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Yarı saydam siyah arka plan
                g2d.setColor(new Color(0, 0, 0, 150));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        card.setOpaque(false);
        card.setLayout(new GridLayout(3, 2, 10, 10));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Bilgi etiketleri
        addInfoLabel(card, "Toplam Enerji Üretimi:", String.format("%.1f", admin.getToplamEnerjiUretimi()));
        addInfoLabel(card, "Enerji Tüketimi:", String.format("%.1f", admin.getEnerjiTuketimi()));
        addInfoLabel(card, "Su Rezervi:", String.format("%.1f", admin.getSuRezervi()));
        addInfoLabel(card, "Su Tüketimi:", String.format("%.1f", admin.getSuTuketimi()));
        addInfoLabel(card, "Toplam Araç:", String.valueOf(admin.getToplamAracSayisi()));
        addInfoLabel(card, "Trafikteki Araç:", String.valueOf(admin.getTrafiktekiAracSayisi()));

        return card;
    }

    private void addInfoLabel(JPanel panel, String title, String value) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(new Color(200, 200, 200));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        valueLabel.setForeground(Color.WHITE);
        
        panel.add(titleLabel);
        panel.add(valueLabel);
    }

    private void showStatusDialog(String title, String message, Color statusColor) {
        JPanel dialogPanel = new JPanel(new BorderLayout(10, 10));
        dialogPanel.setBackground(new Color(45, 45, 45));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel messageLabel = new JLabel("<html>" + message.replace("\n", "<br>") + "</html>");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        messageLabel.setForeground(Color.WHITE);
        
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(statusColor);
        statusPanel.setPreferredSize(new Dimension(20, 20));
        
        dialogPanel.add(messageLabel, BorderLayout.CENTER);
        dialogPanel.add(statusPanel, BorderLayout.EAST);
        
        JOptionPane.showMessageDialog(this, dialogPanel, title, JOptionPane.PLAIN_MESSAGE);
    }

    private void enerjiDurumuKontrol() {
        float uretim = admin.getToplamEnerjiUretimi();
        float tuketim = admin.getEnerjiTuketimi();
        String durum = uretim > tuketim ? "İyi" : "Kritik";
        
        showStatusDialog("Enerji Durumu",
            String.format("Üretim: %.2f\nTüketim: %.2f\nDurum: %s", 
                uretim, tuketim, durum),
            durum.equals("İyi") ? new Color(46, 204, 113) : new Color(231, 76, 60));
    }

    private void suDurumuKontrol() {
        float rezerv = admin.getSuRezervi();
        float tuketim = admin.getSuTuketimi();
        String durum = rezerv > tuketim ? "İyi" : "Kritik";
        
        showStatusDialog("Su Durumu",
            String.format("Rezerv: %.2f\nTüketim: %.2f\nDurum: %s", 
                rezerv, tuketim, durum),
            durum.equals("İyi") ? new Color(46, 204, 113) : new Color(231, 76, 60));
    }

    private void trafikKontrol() {
        int toplamArac = admin.getToplamAracSayisi();
        int trafiktekiArac = admin.getTrafiktekiAracSayisi();
        String durum = trafiktekiArac > toplamArac/2 ? "Sıkışık" : "Normal";
        
        showStatusDialog("Trafik Durumu",
            String.format("Toplam Araç: %d\nTrafikteki Araç: %d\nDurum: %s", 
                toplamArac, trafiktekiArac, durum),
            durum.equals("Normal") ? new Color(46, 204, 113) : new Color(231, 76, 60));
    }
}
