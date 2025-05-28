package com.mycompany.sehir;

public class Vatandas extends Kisi {
    public Vatandas(String isim, String tcno) {
        super(isim, tcno);
    }

    public String getTcno() {
        return super.getTcno();
    }

    public void setTcno(String tcno) {
        super.setTcno(tcno);
    }

    public String trafikKontrol() {
        return "Trafik durumu görüntüleniyor.";
    }

    public String enerjiDurumuKontrol() {
        return "Enerji durumu görüntüleniyor.";
    }

    public String suDurumuKontrol() {
        return "Su durumu görüntüleniyor.";
    }
}

