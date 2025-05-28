package com.mycompany.sehir;

import java.util.ArrayList;

public class Admin extends Kisi {
    private String kullaniciAdi;
    private String sifre;
    private ArrayList<Varlik> varlikListesi;
    private ArrayList<Vatandas> vatandasListesi;
    private float enerjiTuketimi;
    private float suRezervi;
    private float suTuketimi;
    private int trafiktekiAracSayisi;
    private float toplamEnerjiUretimi;
    private int toplamAracSayisi;
    private ArrayList<String> trafiktekiAracIdleri;

    public Admin(String isim, String tcno, String kullaniciAdi, String sifre) {
        super(isim, tcno);
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
        
        // Listeleri başlat
        this.varlikListesi = new ArrayList<>();
        this.vatandasListesi = new ArrayList<>();
        this.trafiktekiAracIdleri = new ArrayList<>();
        
        // Varsayılan değerleri ayarla
        this.enerjiTuketimi = 0.0f;
        this.suRezervi = 0.0f;
        this.suTuketimi = 0.0f;
        
        // Varsayılan varlıkları ekle
        initializeDefaultVarliklar();
        
        // Varsayılan olarak ID'si 6 olan aracı trafiğe ekleyelim
        trafiktekiAracIdleri.add("6");
        // Trafikteki araç sayısını trafiktekiAracIdleri listesinin boyutuna göre ayarla
        this.trafiktekiAracSayisi = trafiktekiAracIdleri.size();
    }

    private void initializeDefaultVarliklar() {
        // Enerji Kaynakları
        varlikListesi.add(new EnerjiKaynagi("1", "İstanbul", "Belediye", "Güneş Enerjisi", 750.0f));
        varlikListesi.add(new EnerjiKaynagi("2", "Ankara", "Belediye", "Rüzgar Enerjisi", 680.0f));
        varlikListesi.add(new EnerjiKaynagi("3", "İzmir", "Belediye", "Hidrotermal Enerji", 920.0f));
        varlikListesi.add(new EnerjiKaynagi("4", "Antalya", "Belediye", "Nükleer Enerji", 950.0f));
        varlikListesi.add(new EnerjiKaynagi("5", "Bursa", "Belediye", "Kimyasal Enerji", 850.0f));
        
        // Araçlar
        varlikListesi.add(new Arac("6", "Eskişehir", "Ahmet Yılmaz", "Sedan", 5, true, 40));
        varlikListesi.add(new Arac("7", "Konya", "Mehmet Demir", "Hatchback", 5, false, null));
        varlikListesi.add(new Arac("8", "Trabzon", "Ayşe Kaya", "Sedan", 5, true, 90));
        varlikListesi.add(new Arac("9", "Samsun", "Fatma Çelik", "Hatchback", 5, false, null));
        varlikListesi.add(new Arac("10", "Adana", "Mustafa Öztürk", "Sedan", 5, true, 95));
        varlikListesi.add(new Arac("11", "Mersin", "Zeynep Yıldız", "Hatchback", 5, false, null));
        
        // Binalar
        varlikListesi.add(new Bina("12", "Kayseri", "Ali Şahin", "Yönetim Binası", 5, 0.85f, true));
        varlikListesi.add(new Bina("13", "Gaziantep", "Hüseyin Aydın", "Hizmet Binası", 3, 0.75f, true));
    }

    // Getter ve Setter metodları
    public ArrayList<Varlik> getVarlikListesi() {
        return varlikListesi;
    }

    public ArrayList<Vatandas> getVatandasListesi() {
        return vatandasListesi;
    }

    public float getEnerjiTuketimi() {
        return enerjiTuketimi;
    }

    public void setEnerjiTuketimi(float enerjiTuketimi) {
        this.enerjiTuketimi = enerjiTuketimi;
    }

    public float getSuRezervi() {
        return suRezervi;
    }

    public void setSuRezervi(float suRezervi) {
        this.suRezervi = suRezervi;
    }

    public float getSuTuketimi() {
        return suTuketimi;
    }

    public void setSuTuketimi(float suTuketimi) {
        this.suTuketimi = suTuketimi;
    }

    public int getTrafiktekiAracSayisi() {
        return trafiktekiAracIdleri.size(); // Her zaman gerçek sayıyı döndür
    }

    public void setTrafiktekiAracSayisi(int trafiktekiAracSayisi) {
        this.trafiktekiAracSayisi = trafiktekiAracSayisi;
    }

    public float getToplamEnerjiUretimi() {
        float toplam = 0;
        for (Varlik varlik : varlikListesi) {
            if (varlik instanceof EnerjiKaynagi) {
                EnerjiKaynagi ek = (EnerjiKaynagi) varlik;
                toplam += ek.getKapasite();
            }
        }
        return toplam;
    }

    public void setToplamEnerjiUretimi(float toplamEnerjiUretimi) {
        this.toplamEnerjiUretimi = toplamEnerjiUretimi;
    }

    public int getToplamAracSayisi() {
        int toplam = 0;
        for (Varlik varlik : varlikListesi) {
            if (varlik instanceof Arac) {
                toplam++;
            }
        }
        return toplam;
    }

    public void setToplamAracSayisi(int toplamAracSayisi) {
        this.toplamAracSayisi = toplamAracSayisi;
    }

    public void aracTrafigeEkle(String aracId) {
        if (!trafiktekiAracIdleri.contains(aracId)) {
            trafiktekiAracIdleri.add(aracId);
            // Aracın timer'ını başlat
            for (Varlik varlik : varlikListesi) {
                if (varlik instanceof Arac && varlik.getId().equals(aracId)) {
                    ((Arac) varlik).baslatSarjTimer();
                    break;
                }
            }
            // Trafikteki araç sayısını güncelle
            this.trafiktekiAracSayisi = trafiktekiAracIdleri.size();
        }
    }

    public void aracTrafiktenCikar(String aracId) {
        if (trafiktekiAracIdleri.remove(aracId)) {  // remove metodu başarılı olursa true döner
            // Aracın timer'ını durdur
            for (Varlik varlik : varlikListesi) {
                if (varlik instanceof Arac && varlik.getId().equals(aracId)) {
                    ((Arac) varlik).durdurSarjTimer();
                    break;
                }
            }
            // Trafikteki araç sayısını güncelle
            this.trafiktekiAracSayisi = trafiktekiAracIdleri.size();
        }
    }

    public boolean aracTrafikteMi(String aracId) {
        return trafiktekiAracIdleri.contains(aracId);
    }
}
