package com.mycompany.sehir;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class Arac extends Varlik {
    private String aracTipi;  // Araç tipi (Elektrikli, Geleneksel)
    private int kapasite;
    private Integer sarjDurumu;   // Şarj durumu, sadece elektrikli araçlar için geçerli
    private boolean elektrikli;
    private Timer sarjTimer;  // Timer ekledik

    // Constructor'a şarj durumu eklendi, ancak elektrikli araçlar için opsiyonel
    public Arac(String id, String konum, String sahibi, String aracTipi, int kapasite, boolean elektrikli, Integer sarjDurumu) {
        super(id, konum, sahibi);  // Varlık sınıfının constructor'ını çağırıyoruz
        this.aracTipi = aracTipi;
        this.kapasite = kapasite;
        this.elektrikli = elektrikli;
        if (elektrikli) {
            this.sarjDurumu = sarjDurumu;
        } else {
            this.sarjDurumu = null;
        }
    }

    public void baslatSarjTimer() {
        if (elektrikli && sarjDurumu != null) {
            sarjTimer = new Timer(10000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        sarjDurumu -= 3;
                        if (sarjDurumu < 20) {
                            String errorMessage = "ID'si " + getId() + " olan aracın şarjı %20'nin altına düşmüştür. (" + 
                                sarjDurumu + "%) En yakın şarj istasyonuna yönlendiriliyor.";
                            throw new LowEnergyException(errorMessage);
                        }
                    } catch (LowEnergyException ex) {
                        sarjTimer.stop();
                        JOptionPane.showMessageDialog(null, 
                            ex.getMessage(),
                            "Düşük Şarj Uyarısı",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            sarjTimer.start();
        }
    }

    // Getter ve Setter metodları
    public String getAracTipi() {
        return aracTipi;
    }

    @Override
public String toString() {
    return "Arac [ID=" + getId() + ", Konum=" + getKonum() + ", Sahibi=" + getSahibi() +
            ", Araç Tipi=" + aracTipi + ", Kapasite=" + kapasite + ", Elektrikli=" + elektrikli +
            (elektrikli ? ", Şarj Durumu: " + sarjDurumu + "%" : "") + "]";
}


    public void setAracTipi(String aracTipi) {
        this.aracTipi = aracTipi;
    }

    public boolean isElektrikli() {
        return elektrikli;
    }

    public void setElektrikli(boolean elektrikli) {
        this.elektrikli = elektrikli;
    }

    public int getKapasite() {
        return kapasite;
    }

    public void setKapasite(int kapasite) {
        this.kapasite = kapasite;
    }

    public Integer getSarjDurumu() {
        return sarjDurumu;
    }

    public void setSarjDurumu(Integer sarjDurumu) {
        this.sarjDurumu = sarjDurumu;
        // Şarj durumu değiştiğinde timer'ı yeniden başlat
        if (sarjTimer != null) {
            sarjTimer.stop();
        }
        baslatSarjTimer();
    }

    // Timer'ı durdurmak için yeni metod
    public void durdurSarjTimer() {
        if (sarjTimer != null) {
            sarjTimer.stop();
        }
    }

    @Override
    public String durumOgren() {
        if (elektrikli) {
            return "Araç tipi: " + aracTipi + ", Kapasite: " + kapasite + ", Şarj Durumu: " + sarjDurumu + "%";
        } else {
            return "Araç tipi: " + aracTipi + ", Kapasite: " + kapasite;
        }
    }
}
