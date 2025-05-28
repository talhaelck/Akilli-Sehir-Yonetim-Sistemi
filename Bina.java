package com.mycompany.sehir;

public class Bina extends Varlik {
    private String binaTuru;
    private int katSayisi;
    private float enerjiVerimliligi;
    private boolean akilliBina;  // Akıllı bina özelliği

    public Bina(String id, String konum, String sahibi, String binaTuru, int katSayisi, float enerjiVerimliligi, boolean akilliBina) {
        super(id, konum, sahibi);  // Varlık sınıfının constructor'ını çağırıyoruz
        this.binaTuru = binaTuru;
        this.katSayisi = katSayisi;
        this.enerjiVerimliligi = enerjiVerimliligi;
        this.akilliBina = akilliBina;  // Yeni özelliği ekliyoruz
    }

    // Getter ve Setter metodları
    public String getBinaTuru() {
        return binaTuru;
    }

    @Override
public String toString() {
    return "Bina [ID=" + getId() + ", Konum=" + getKonum() + ", Sahibi=" + getSahibi() +
            ", Bina Türü=" + binaTuru + ", Kat Sayısı=" + katSayisi + ", Enerji Verimliliği=" + enerjiVerimliligi +
            ", Akıllı Bina=" + (akilliBina ? "Evet" : "Hayır") + "]";
}


    public void setBinaTuru(String binaTuru) {
        this.binaTuru = binaTuru;
    }

    public int getKatSayisi() {
        return katSayisi;
    }

    public void setKatSayisi(int katSayisi) {
        this.katSayisi = katSayisi;
    }

    public float getEnerjiVerimliligi() {
        return enerjiVerimliligi;
    }

    public void setEnerjiVerimliligi(float enerjiVerimliligi) {
        this.enerjiVerimliligi = enerjiVerimliligi;
    }

    public boolean isAkilliBina() {
        return akilliBina;
    }

    public void setAkilliBina(boolean akilliBina) {
        this.akilliBina = akilliBina;
    }

    @Override
    public String durumOgren() {
        return "Bina türü: " + binaTuru + ", Kat sayısı: " + katSayisi + ", Enerji verimliliği: " + enerjiVerimliligi + ", Akıllı Bina: " + (akilliBina ? "Evet" : "Hayır");
    }
}
