
package com.mycompany.sehir;
public class TrafikYonetimi implements Yonetim {
    private int aracSayisi; // Trafikteki araç sayısı

    public TrafikYonetimi(int aracSayisi) {
        this.aracSayisi = aracSayisi;
    }

    public int getAracSayisi() {
        return aracSayisi;
    }

    public void setAracSayisi(int aracSayisi) {
        this.aracSayisi = aracSayisi;
    }

    // Trafik durumunu analiz etme
    public void trafikDurumuAnalizEt() {
        try {
            if (aracSayisi > 100) {
                // Yoğun trafik durumu
                throw new TrafficJamException("Trafik sıkışıklığı başladı.");
            } else {
                System.out.println("Trafik normal seviyede.");
            }
        } catch (TrafficJamException e) {
            System.out.println(e.getMessage());
        }
    }

    // Trafik tıkanıklığı kontrolü
    public void trafikTikanikligiKontrolEt() {
        if (aracSayisi > 150) {
            System.out.println("Trafik tıkanıklığı var.");
        } else {
            System.out.println("Trafik akışı düzgün.");
        }
    }

    @Override
    public String raporOlustur() {
        return "Trafik raporu oluşturuluyor... Araç sayısı: " + aracSayisi;
    }

    @Override
    public String durumGoruntule() {
        return "Trafik durumu görüntüleniyor... Araç sayısı: " + aracSayisi;
    }
}
