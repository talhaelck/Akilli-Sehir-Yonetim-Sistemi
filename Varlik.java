package com.mycompany.sehir;
public abstract class Varlik {
    private String id;
    private String konum;
    private String sahibi;

    public Varlik(String id, String konum, String sahibi) {
        this.id = id;
        this.konum = konum;
        this.sahibi = sahibi;
    }

    // Getter ve Setter metodları
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Konum: " + konum + ", Sahibi: " + sahibi;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKonum() {
        return konum;
    }

    public void setKonum(String konum) {
        this.konum = konum;
    }

    public String getSahibi() {
        return sahibi;
    }

    public void setSahibi(String sahibi) {
        this.sahibi = sahibi;
    }

    // Abstract method
    public abstract String durumOgren();
}
