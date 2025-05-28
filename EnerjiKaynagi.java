/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sehir;
public class EnerjiKaynagi extends Varlik {
    private String enerjiTuru;
    private float kapasite;

    public EnerjiKaynagi(String id, String konum, String sahibi, String enerjiTuru, float kapasite) {
        super(id, konum, sahibi);  // Varlık sınıfının constructor'ını çağırıyoruz
        this.enerjiTuru = enerjiTuru;
        this.kapasite = kapasite;
    }

    // Getter ve Setter metodları
    public String getEnerjiTuru() {
        return enerjiTuru;
    }
    @Override
    public String toString() {
        return super.toString() + ", Enerji Türü: " + enerjiTuru + ", Kapasite: " + kapasite;
    }

    public void setEnerjiTuru(String enerjiTuru) {
        this.enerjiTuru = enerjiTuru;
    }

    public float getKapasite() {
        return kapasite;
    }

    public void setKapasite(float kapasite) {
        this.kapasite = kapasite;
    }

    @Override
    public String durumOgren() {
        return "Enerji kaynağının durumu: " + enerjiTuru + ", Kapasite: " + kapasite;
    }
}
