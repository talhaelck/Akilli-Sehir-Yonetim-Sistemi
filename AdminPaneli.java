package com.mycompany.sehir;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class AdminPaneli extends JFrame {
    private final Admin admin;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private Map<Integer, String> sorunlar;

    public AdminPaneli(Admin admin) {
        if (admin == null) {
            throw new IllegalArgumentException("Admin nesnesi null olamaz!");
        }
        System.out.println("AdminPaneli oluşturuluyor, admin: " + admin.getIsim()); // Debug için
        
        this.admin = admin;
        this.sorunlar = new HashMap<>();
        initializeUI();
        setVisible(true); // Pencereyi görünür yap
    }

    private void initializeUI() {
        setTitle("Admin Paneli - " + admin.getIsim());
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ana panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 30));

        // Header panel
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        // Content container
        JPanel contentContainer = new JPanel(new BorderLayout());
        contentContainer.setBackground(new Color(30, 30, 30));

        // Sol menü
        contentContainer.add(createSideMenu(), BorderLayout.WEST);

        // İçerik alanı
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(45, 45, 45));
        
        // Panelleri ekle
        contentPanel.add(createDashboardPanel(), "DASHBOARD");
        contentPanel.add(createVarlikPanel(), "VARLIK");
        contentPanel.add(createVatandasPanel(), "VATANDAS");
        contentPanel.add(createYonetimPanel(), "YONETIM");

        contentContainer.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(contentContainer, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel() {
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
        header.setPreferredSize(new Dimension(0, 80));
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(15, 25, 15, 25));

        // Sol tarafta başlık
        JLabel title = new JLabel("Akıllı Şehir Yönetim Sistemi");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);

        // Sağ tarafta admin bilgisi ve saat
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.setOpaque(false);
        
        JLabel adminLabel = new JLabel("Admin: " + admin.getIsim());
        adminLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        adminLabel.setForeground(Color.WHITE);

        JLabel timeLabel = new JLabel();
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        timeLabel.setForeground(Color.WHITE);

        Timer timer = new Timer(1000, e -> 
            timeLabel.setText(new SimpleDateFormat("HH:mm:ss").format(new Date())));
        timer.start();

        rightPanel.add(adminLabel);
        rightPanel.add(new JSeparator(JSeparator.VERTICAL) {{
            setPreferredSize(new Dimension(1, 20));
            setForeground(new Color(255, 255, 255, 100));
        }});
        rightPanel.add(timeLabel);

        header.add(title, BorderLayout.WEST);
        header.add(rightPanel, BorderLayout.EAST);

        return header;
    }

    private JPanel createSideMenu() {
        JPanel sideMenu = new JPanel();
        sideMenu.setPreferredSize(new Dimension(250, 0));
        sideMenu.setBackground(new Color(25, 32, 40));  // Daha koyu arka plan
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
        sideMenu.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        String[] menuItems = {
            "Dashboard",
            "Varlık İşlemleri",
            "Vatandaş İşlemleri",
            "Yönetim İşlemleri"
        };

        String[] cardNames = {"DASHBOARD", "VARLIK", "VATANDAS", "YONETIM"};
        JPanel[] menuPanels = new JPanel[menuItems.length];
        
        // Aktif menü takibi için
        final int[] activeIndex = {0};

        for (int i = 0; i < menuItems.length; i++) {
            final int index = i;
            JPanel menuItemPanel = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Aktif menü için gradient arka plan
                    if (activeIndex[0] == index) {
                        GradientPaint gp = new GradientPaint(0, 0, 
                            new Color(41, 128, 185, 200), 
                            getWidth(), 0, 
                            new Color(44, 62, 80, 200));
                        g2d.setPaint(gp);
                        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                        
                        // Sol tarafta parlak çizgi
                        g2d.setColor(new Color(52, 152, 219));
                        g2d.fillRoundRect(0, 0, 4, getHeight(), 2, 2);
                    }
                }
            };
            menuItemPanel.setOpaque(false);
            menuItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            menuItemPanel.setMaximumSize(new Dimension(220, 50));
            
            // Menü etiketi
            JLabel menuLabel = new JLabel(menuItems[i]);
            menuLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            menuLabel.setForeground(Color.WHITE);
            
            menuItemPanel.add(menuLabel);
            menuPanels[i] = menuItemPanel;

            // Tıklama ve hover efektleri
            menuItemPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (activeIndex[0] != index) {
                        menuItemPanel.setBackground(new Color(52, 73, 94, 100));
                    }
                    menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    menuItemPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (activeIndex[0] != index) {
                        menuItemPanel.setBackground(new Color(25, 32, 40));
                    }
                    if (activeIndex[0] != index) {
                        menuLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    }
                    menuItemPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    // Önceki aktif menüyü sıfırla
                    menuPanels[activeIndex[0]].repaint();
                    
                    // Yeni aktif menüyü ayarla
                    activeIndex[0] = index;
                    
                    // Tüm menüleri yeniden çiz
                    for (JPanel panel : menuPanels) {
                        panel.repaint();
                    }
                    
                    // İçerik panelini değiştir
                    cardLayout.show(contentPanel, cardNames[index]);
                    
                    // Animasyonlu geçiş efekti
                    contentPanel.setVisible(false);
                    Timer timer = new Timer(50, event -> contentPanel.setVisible(true));
                    timer.setRepeats(false);
                    timer.start();
                }
            });

            sideMenu.add(menuItemPanel);
            sideMenu.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        sideMenu.add(Box.createVerticalGlue());

        // Çıkış butonu
        JPanel exitPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(231, 76, 60, 150));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        exitPanel.setOpaque(false);
        exitPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        exitPanel.setMaximumSize(new Dimension(220, 50));

        JLabel exitLabel = new JLabel("Çıkış");
        exitLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        exitLabel.setForeground(Color.WHITE);
        exitPanel.add(exitLabel);

        exitPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitPanel.setBackground(new Color(192, 57, 43, 150));
                exitPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitPanel.setBackground(new Color(231, 76, 60, 150));
                exitPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                new AnaGirisEkrani().setVisible(true); 
            dispose();
            }
        });

        sideMenu.add(exitPanel);

        return sideMenu;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        panel.add(createDashboardCard("Toplam Varlık", String.valueOf(admin.getVarlikListesi().size())));
        panel.add(createDashboardCard("Toplam Vatandaş", String.valueOf(admin.getVatandasListesi().size())));
        panel.add(createDashboardCard("Enerji Durumu", String.format("%.1f/%.1f", admin.getToplamEnerjiUretimi(), admin.getEnerjiTuketimi())));
        panel.add(createDashboardCard("Trafik Durumu", admin.getTrafiktekiAracSayisi() + "/" + admin.getToplamAracSayisi()));

        return panel;
    }

    private JPanel createDashboardCard(String title, String value) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(52, 73, 94));
                g2d.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
            }
        };
        card.setLayout(new BorderLayout(10, 10));
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        card.setOpaque(false);

        // Başlık
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(200, 200, 200));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Değer
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Monospaced", Font.BOLD, 32)); // Monospaced font kullanıyoruz
        valueLabel.setForeground(new Color(255, 255, 255));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // İç panel (dikey hizalama için)
        JPanel innerPanel = new JPanel(new GridLayout(2, 1, 5, 10));
        innerPanel.setOpaque(false);
        innerPanel.add(titleLabel);
        innerPanel.add(valueLabel);

        // Kartın ortasına yerleştir
        card.add(Box.createVerticalGlue(), BorderLayout.NORTH);
        card.add(innerPanel, BorderLayout.CENTER);
        card.add(Box.createVerticalGlue(), BorderLayout.SOUTH);

        // Hover efekti
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                valueLabel.setForeground(new Color(46, 204, 113)); // Yeşilimsi renk
            }

            @Override
            public void mouseExited(MouseEvent e) {
                valueLabel.setForeground(new Color(255, 255, 255));
            }
        });

        return card;
    }

    private JPanel createVarlikPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(45, 45, 45));
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Başlık
        JLabel titleLabel = new JLabel("VARLIK İŞLEMLERİ");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // İşlem kartları paneli
        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        cardsPanel.setOpaque(false);

        // İşlem kartları
        cardsPanel.add(createActionCard("Varlık Ekle", 
            "Yeni bir varlık ekleyin", 
            new Color(41, 128, 185), 
            e -> varlikEkle()));
        
        cardsPanel.add(createActionCard("Varlıkları Listele", 
            "Mevcut varlıkları görüntüleyin", 
            new Color(46, 204, 113), 
            e -> varlikListele()));
        
        cardsPanel.add(createActionCard("Varlık Güncelle", 
            "Varlık bilgilerini güncelleyin", 
            new Color(230, 126, 34), 
            e -> varlikGuncelle()));
        
        cardsPanel.add(createActionCard("Varlık Sil", 
            "Varlık kaydını silin", 
            new Color(231, 76, 60), 
            e -> varlikSil()));

        mainPanel.add(cardsPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel createVatandasPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(45, 45, 45));
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Başlık
        JLabel titleLabel = new JLabel("VATANDAŞ İŞLEMLERİ");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // İşlem kartları paneli
        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        cardsPanel.setOpaque(false);

        cardsPanel.add(createActionCard("Vatandaş Ekle", 
            "Yeni vatandaş kaydı oluşturun", 
            new Color(41, 128, 185), 
            e -> vatandasEkle()));
        
        cardsPanel.add(createActionCard("Vatandaşları Listele", 
            "Kayıtlı vatandaşları görüntüleyin", 
            new Color(46, 204, 113), 
            e -> vatandasListele()));
        
        cardsPanel.add(createActionCard("Vatandaş Güncelle", 
            "Vatandaş bilgilerini güncelleyin", 
            new Color(230, 126, 34), 
            e -> vatandasGuncelle()));
        
        cardsPanel.add(createActionCard("Vatandaş Paneli", 
            "Vatandaş paneline erişin", 
            new Color(142, 68, 173), 
            e -> vatandasPanelineEris()));

        mainPanel.add(cardsPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel createYonetimPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(45, 45, 45));
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Başlık
        JLabel titleLabel = new JLabel("YÖNETİM İŞLEMLERİ");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // İşlem kartları paneli
        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        cardsPanel.setOpaque(false);

        cardsPanel.add(createActionCard("Enerji Yönetimi", 
            "Enerji üretim ve tüketimini yönetin", 
            new Color(241, 196, 15), 
            e -> enerjiYonetimi()));
        
        cardsPanel.add(createActionCard("Su Yönetimi", 
            "Su rezervi ve tüketimini yönetin", 
            new Color(52, 152, 219), 
            e -> suYonetimi()));
        
        cardsPanel.add(createActionCard("Trafik Yönetimi", 
            "Trafik durumunu kontrol edin", 
            new Color(46, 204, 113), 
            e -> trafikYonetimi()));
        
        cardsPanel.add(createActionCard("Durum Görüntüle", 
            "Genel sistem durumunu görüntüleyin", 
            new Color(155, 89, 182), 
            e -> durumGoruntule()));

        mainPanel.add(cardsPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel createActionCard(String title, String description, Color color, ActionListener action) {
        JPanel card = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Gradient arka plan
                GradientPaint gp = new GradientPaint(
                    0, 0, 
                    new Color(color.getRed(), color.getGreen(), color.getBlue(), 200),
                    getWidth(), getHeight(),
                    new Color(color.getRed()/2, color.getGreen()/2, color.getBlue()/2, 200)
                );
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // İçerik paneli
        JPanel contentPanel = new JPanel(new BorderLayout(5, 5));
        contentPanel.setOpaque(false);

        // Başlık
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        // Açıklama
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setForeground(new Color(240, 240, 240));

        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(descLabel, BorderLayout.CENTER);
        card.add(contentPanel);

        // Tıklama efekti
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                action.actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, null));
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createEmptyBorder(15, 20, 25, 20)); // Hafif yükselme efekti
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            }
        });

        return card;
    }

    // Varlık işlemleri metodları
    private void varlikEkle() {
        String[] varlikTurleri = {"Enerji Kaynağı", "Araç", "Bina"};
        String varlikTipi = (String) JOptionPane.showInputDialog(this, "Hangi varlık türünü eklemek istersiniz?",
                "Varlık Türü Seçin", JOptionPane.PLAIN_MESSAGE, null, varlikTurleri, varlikTurleri[0]);

        if (varlikTipi != null) {
            String id = JOptionPane.showInputDialog("Varlık ID'sini girin:");
            String konum = JOptionPane.showInputDialog("Varlık konumunu girin:");
            String sahibi = null;
            if (!varlikTipi.equals("Enerji Kaynağı")) {
                sahibi = JOptionPane.showInputDialog("Varlık sahibini girin:");
            }

            Varlik yeniVarlik = null;
            if (varlikTipi.equals("Enerji Kaynağı")) {
                String[] enerjiTurleri = {
                    "Hidrotermal Enerji",
                    "Kimyasal Enerji",
                    "Nükleer Enerji",
                    "Güneş Enerjisi",
                    "Rüzgar Enerjisi"
                };
                
                String enerjiTuru = (String) JOptionPane.showInputDialog(this, 
                    "Enerji türünü seçin:",
                    "Enerji Türü Seçimi",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    enerjiTurleri,
                    enerjiTurleri[0]);

                float kapasite = Float.parseFloat(JOptionPane.showInputDialog("Kapasiteyi girin:"));
                yeniVarlik = new EnerjiKaynagi(id, konum, "Belediye", enerjiTuru, kapasite);

            } else if (varlikTipi.equals("Araç")) {
                String aracTipi = JOptionPane.showInputDialog("Araç tipini girin:");
                int kapasite = Integer.parseInt(JOptionPane.showInputDialog("Araç kapasitesini girin:"));
                boolean elektrikli = JOptionPane.showConfirmDialog(this, "Araç elektrikli mi?", 
                    "Araç Tipi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

                Integer sarjDurumu = null;
                if (elektrikli) {
                    sarjDurumu = Integer.parseInt(JOptionPane.showInputDialog("Elektrikli araç şarj durumunu girin (0-100):"));
                }

                yeniVarlik = new Arac(id, konum, sahibi, aracTipi, kapasite, elektrikli, sarjDurumu);
            } else if (varlikTipi.equals("Bina")) {
                String binaTuru = JOptionPane.showInputDialog("Bina türünü girin:");
                int katSayisi = Integer.parseInt(JOptionPane.showInputDialog("Kat sayısını girin:"));
                boolean akilliBina = JOptionPane.showConfirmDialog(this, "Akıllı bina mı?", 
                    "Bina Tipi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                
                float enerjiVerimliligi = 0.0f;
                if (akilliBina) {
                    enerjiVerimliligi = Float.parseFloat(JOptionPane.showInputDialog("Enerji verimliliği oranını girin:"));
                }

                yeniVarlik = new Bina(id, konum, sahibi, binaTuru, katSayisi, enerjiVerimliligi, akilliBina);
            }

            if (yeniVarlik != null) {
                admin.getVarlikListesi().add(yeniVarlik);
                JOptionPane.showMessageDialog(this, "Yeni " + varlikTipi + " eklendi.");
                updateDashboard();
            }
        }
    }

    private void varlikListele() {
        StringBuilder sb = new StringBuilder("Varlıklar:\n\n");
        for (Varlik varlik : admin.getVarlikListesi()) {
                sb.append(varlik.toString()).append("\n");
        }
        
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        textArea.setBackground(new Color(45, 45, 45));
        textArea.setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.getViewport().setBackground(new Color(45, 45, 45));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Varlık Listesi", 
                JOptionPane.PLAIN_MESSAGE);
    }

    private void varlikGuncelle() {
        String id = JOptionPane.showInputDialog("Güncellenecek varlığın ID'sini girin:");
        for (Varlik varlik : admin.getVarlikListesi()) {
            if (varlik.getId().equals(id)) {
                String yeniKonum = JOptionPane.showInputDialog("Yeni konumu girin:", varlik.getKonum());
                if (yeniKonum != null) varlik.setKonum(yeniKonum);
                
                if (varlik instanceof EnerjiKaynagi) {
                    EnerjiKaynagi ek = (EnerjiKaynagi) varlik;
                    String yeniKapasiteStr = JOptionPane.showInputDialog("Yeni kapasiteyi girin:", ek.getKapasite());
                    if (yeniKapasiteStr != null) {
                        try {
                            float yeniKapasite = Float.parseFloat(yeniKapasiteStr);
                            ek.setKapasite(yeniKapasite);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(this, "Geçersiz kapasite değeri!");
                        }
                    }
                } else if (varlik instanceof Arac) {
                    Arac arac = (Arac) varlik;
                    String yeniSahibi = JOptionPane.showInputDialog("Yeni sahibi girin:", varlik.getSahibi());
                    if (yeniSahibi != null) arac.setSahibi(yeniSahibi);
                    
                    // Elektrikli araç ise şarj durumunu güncelleme seçeneği sun
                    if (arac.isElektrikli()) {
                        String yeniSarjStr = JOptionPane.showInputDialog(
                            "Yeni şarj durumunu girin (0-100):", 
                            arac.getSarjDurumu());
                        if (yeniSarjStr != null) {
                            try {
                                int yeniSarj = Integer.parseInt(yeniSarjStr);
                                if (yeniSarj >= 0 && yeniSarj <= 100) {
                                    arac.setSarjDurumu(yeniSarj);
                                } else {
                                    JOptionPane.showMessageDialog(this, 
                                        "Şarj değeri 0-100 arasında olmalıdır!", 
                                        "Geçersiz Değer", 
                                        JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(this, 
                                    "Geçersiz şarj değeri!", 
                                    "Hata", 
                                    JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                } else {
                    String yeniSahibi = JOptionPane.showInputDialog("Yeni sahibi girin:", varlik.getSahibi());
                    if (yeniSahibi != null) varlik.setSahibi(yeniSahibi);
                }

                JOptionPane.showMessageDialog(this, "Varlık güncellendi.");
                updateDashboard();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Varlık bulunamadı!");
    }

    private void varlikSil() {
        String id = JOptionPane.showInputDialog("Silinecek varlığın ID'sini girin:");
        for (int i = 0; i < admin.getVarlikListesi().size(); i++) {
            if (admin.getVarlikListesi().get(i).getId().equals(id)) {
                admin.getVarlikListesi().remove(i);
                JOptionPane.showMessageDialog(this, "Varlık silindi.");
                updateDashboard();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Varlık bulunamadı!");
    }

    // Vatandaş işlemleri metodları
    private void vatandasEkle() {
        String isim = JOptionPane.showInputDialog("Vatandaş ismini giriniz:");
        String tcno = JOptionPane.showInputDialog("Vatandaş TC No giriniz:");
        
        if (isim != null && tcno != null && !isim.trim().isEmpty() && !tcno.trim().isEmpty()) {
            if (tcnoKontrol(tcno)) {
                JOptionPane.showMessageDialog(this, "Bu TC No ile kayıtlı bir vatandaş zaten var!");
                return;
            }
            
            Vatandas yeniVatandas = new Vatandas(isim.trim(), tcno.trim());
            admin.getVatandasListesi().add(yeniVatandas);
            JOptionPane.showMessageDialog(this, "Vatandaş başarıyla eklendi.");
            updateDashboard();
        }
    }

    private void vatandasListele() {
        StringBuilder sb = new StringBuilder("Vatandaşlar:\n\n");
        for (Vatandas v : admin.getVatandasListesi()) {
            sb.append("İsim: ").append(v.getIsim())
               .append(", TC No: ").append(v.getTcno())
               .append("\n");
        }
        
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        textArea.setBackground(new Color(45, 45, 45));
        textArea.setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.getViewport().setBackground(new Color(45, 45, 45));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Vatandaş Listesi", 
                JOptionPane.PLAIN_MESSAGE);
    }

    private void vatandasGuncelle() {
        String tcno = JOptionPane.showInputDialog("Güncellenecek vatandaşın TC No'sunu giriniz:");
        
        if (tcno != null && !tcno.trim().isEmpty()) {
            for (Vatandas v : admin.getVatandasListesi()) {
                if (v.getTcno().equals(tcno.trim())) {
                    String yeniIsim = JOptionPane.showInputDialog("Yeni ismi giriniz:", v.getIsim());
                    if (yeniIsim != null && !yeniIsim.trim().isEmpty()) {
                        v.isim = yeniIsim.trim();
                        JOptionPane.showMessageDialog(this, "Vatandaş bilgileri güncellendi.");
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Vatandaş bulunamadı!");
        }
    }

    private void vatandasPanelineEris() {
        Vatandas adminVatandas = new Vatandas(admin.getIsim(), admin.getTcno());
        VatandasPaneli vatandasPaneli = new VatandasPaneli(adminVatandas, admin);
        vatandasPaneli.setVisible(true);
    }

    private boolean tcnoKontrol(String tcno) {
        for (Vatandas v : admin.getVatandasListesi()) {
            if (v.getTcno().equals(tcno)) {
                return true;
            }
        }
        return false;
    }

    // Yönetim işlemleri metodları
    private void enerjiYonetimi() {
        try {
            float toplamUretim = admin.getToplamEnerjiUretimi();
            String tuketimStr = JOptionPane.showInputDialog(this, 
                "Toplam enerji üretimi: " + toplamUretim + "\nEnerji tüketimini giriniz:");
            
            if (tuketimStr != null) {
                float tuketim = Float.parseFloat(tuketimStr);
                admin.setEnerjiTuketimi(tuketim);
                
                if (tuketim > toplamUretim) {
                    updateDashboard();
                    throw new Exception("Enerji seviyesi kritik düzeyde! Üretim: " + toplamUretim + ", Tüketim: " + tuketim);
                }
                
                JOptionPane.showMessageDialog(this, 
                    "Enerji Durumu:\nÜretim: " + toplamUretim + 
                    "\nTüketim: " + tuketim + 
                    "\nDurum: İyi");
                updateDashboard();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Enerji Uyarısı", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void suYonetimi() {
        try {
            float mevcutRezerv = admin.getSuRezervi();
            float mevcutTuketim = admin.getSuTuketimi();
            
            String rezervStr = JOptionPane.showInputDialog(this, 
                String.format("Mevcut Durum:\nSu Rezervi: %.2f\nSu Tüketimi: %.2f\n\nYeni su rezervini giriniz:", 
                    mevcutRezerv, mevcutTuketim));
            
            if (rezervStr != null) {
                float rezerv = Float.parseFloat(rezervStr);
                admin.setSuRezervi(rezerv);
                
                String tuketimStr = JOptionPane.showInputDialog(this, 
                    String.format("Su Rezervi: %.2f\nMevcut Tüketim: %.2f\n\nYeni su tüketimini giriniz:", 
                        rezerv, mevcutTuketim));
                
                if (tuketimStr != null) {
                    float tuketim = Float.parseFloat(tuketimStr);
                    admin.setSuTuketimi(tuketim);
                    
                    if (tuketim > rezerv) {
                        updateDashboard();
                        throw new Exception("Su seviyesi kritik düzeyde! Rezerv: " + rezerv + ", Tüketim: " + tuketim);
                    }
                    
                    JOptionPane.showMessageDialog(this, 
                        String.format("Su Durumu:\nRezerv: %.2f\nTüketim: %.2f\nDurum: İyi", 
                            rezerv, tuketim));
                    updateDashboard();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Su Durumu Uyarısı", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void trafikYonetimi() {
        try {
            // Trafik durumu paneli oluştur
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBackground(new Color(45, 45, 45));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panel.setPreferredSize(new Dimension(800, 600)); // Panel boyutunu ayarla

            // Araç listesi paneli
            JPanel aracListesiPanel = new JPanel();
            aracListesiPanel.setLayout(new BoxLayout(aracListesiPanel, BoxLayout.Y_AXIS));
            aracListesiPanel.setBackground(new Color(45, 45, 45));

            // Başlık
            JLabel baslik = new JLabel("Araç Trafik Durumu");
            baslik.setFont(new Font("Segoe UI", Font.BOLD, 18));
            baslik.setForeground(Color.WHITE);
            panel.add(baslik, BorderLayout.NORTH);

            // Durum bilgisi label'ı
            JLabel durumLabel = new JLabel();
            durumLabel.setForeground(Color.WHITE);
            panel.add(durumLabel, BorderLayout.SOUTH);

            // Timer ile sürekli güncelleme
            Timer updateTimer = new Timer(1000, e -> {
                // Araç listesini güncelle
                aracListesiPanel.removeAll();
                
                for (Varlik varlik : admin.getVarlikListesi()) {
                    if (varlik instanceof Arac) {
                        Arac arac = (Arac) varlik;
                        JPanel aracPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                        aracPanel.setBackground(new Color(52, 73, 94));
                        aracPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                        
                        // Araç bilgisi
                        String aracBilgisi = String.format("ID: %s | %s | %s | Şarj: %s", 
                            arac.getId(), 
                            arac.getAracTipi(),
                            arac.isElektrikli() ? "Elektrikli" : "Fosil Yakıt",
                            arac.isElektrikli() ? arac.getSarjDurumu() + "%" : "N/A");
                        
                        JLabel aracLabel = new JLabel(aracBilgisi);
                        aracLabel.setForeground(Color.WHITE);
                        aracPanel.add(aracLabel);

                        // Trafiğe ekle/çıkar butonu
                        boolean trafikteMi = admin.aracTrafikteMi(arac.getId());
                        JButton trafikButton = new JButton(trafikteMi ? "Trafikten Çıkar" : "Trafiğe Ekle");
                        trafikButton.setBackground(trafikteMi ? new Color(231, 76, 60) : new Color(46, 204, 113));
                        trafikButton.setForeground(Color.BLACK);
                        trafikButton.setFocusPainted(false);
                        trafikButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                        
                        trafikButton.addActionListener(event -> {
                            if (admin.aracTrafikteMi(arac.getId())) {
                                admin.aracTrafiktenCikar(arac.getId());
                                trafikButton.setText("Trafiğe Ekle");
                                trafikButton.setBackground(new Color(46, 204, 113));
                            } else {
                                admin.aracTrafigeEkle(arac.getId());
                                trafikButton.setText("Trafikten Çıkar");
                                trafikButton.setBackground(new Color(231, 76, 60));
                            }
                            updateDashboard();
                        });

                        aracPanel.add(trafikButton);
                        aracListesiPanel.add(aracPanel);
                        aracListesiPanel.add(Box.createVerticalStrut(5));
                    }
                }
                
                // Durum bilgisini güncelle
                durumLabel.setText(String.format("Toplam Araç: %d | Trafikteki Araç: %d", 
                    admin.getToplamAracSayisi(), admin.getTrafiktekiAracSayisi()));
                
                aracListesiPanel.revalidate();
                aracListesiPanel.repaint();
            });
            updateTimer.start();

            // Scroll panel
            JScrollPane scrollPane = new JScrollPane(aracListesiPanel);
            scrollPane.setBackground(new Color(45, 45, 45));
            scrollPane.getViewport().setBackground(new Color(45, 45, 45));
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            panel.add(scrollPane, BorderLayout.CENTER);

            // Dialog göster
            Object[] options = {"Kapat"};
            int result = JOptionPane.showOptionDialog(this, panel, "Trafik Yönetimi",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

            // Timer'ı durdur
            updateTimer.stop();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Trafik Yönetimi Hatası", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void durumGoruntule() {
        StringBuilder rapor = new StringBuilder();
        
        // Enerji Durumu
        rapor.append("=== ENERJİ DURUMU ===\n");
        rapor.append("Enerji Kaynakları:\n");
        for (Varlik varlik : admin.getVarlikListesi()) {
            if (varlik instanceof EnerjiKaynagi) {
                EnerjiKaynagi ek = (EnerjiKaynagi) varlik;
                rapor.append("- ").append(ek.getEnerjiTuru())
                     .append(" (Kapasite: ").append(ek.getKapasite()).append(")\n");
            }
        }
        rapor.append("Toplam Üretim: ").append(admin.getToplamEnerjiUretimi()).append("\n");
        rapor.append("Toplam Tüketim: ").append(admin.getEnerjiTuketimi()).append("\n\n");

        // Su Durumu
        rapor.append("=== SU DURUMU ===\n");
        rapor.append("Su Rezervi: ").append(admin.getSuRezervi()).append("\n");
        rapor.append("Su Tüketimi: ").append(admin.getSuTuketimi()).append("\n\n");

        // Trafik Durumu
        rapor.append("=== TRAFİK DURUMU ===\n");
        rapor.append("Araçlar:\n");
        for (Varlik varlik : admin.getVarlikListesi()) {
            if (varlik instanceof Arac) {
                Arac arac = (Arac) varlik;
                rapor.append("- ").append(arac.getAracTipi())
                     .append(" (").append(arac.isElektrikli() ? "Elektrikli" : "Fosil Yakıt").append(")\n");
            }
        }
        rapor.append("Toplam Araç Sayısı: ").append(admin.getToplamAracSayisi()).append("\n");
        rapor.append("Trafikteki Araç Sayısı: ").append(admin.getTrafiktekiAracSayisi());

        JTextArea textArea = new JTextArea(rapor.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        textArea.setBackground(new Color(45, 45, 45));
        textArea.setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(800, 600));
        scrollPane.getViewport().setBackground(new Color(45, 45, 45));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Sistem Durum Raporu", 
                JOptionPane.PLAIN_MESSAGE);
    }

    private void updateDashboard() {
        contentPanel.remove(contentPanel.getComponent(0));
        contentPanel.add(createDashboardPanel(), "DASHBOARD", 0);
        cardLayout.show(contentPanel, "DASHBOARD");
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}

