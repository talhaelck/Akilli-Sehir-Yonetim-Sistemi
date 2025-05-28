/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sehir;
public class Kisi {
    protected String isim;
    protected String tcno;

    public Kisi(String isim, String tcno) {
        this.isim = isim;
        this.tcno = tcno;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getTcno() {
        return tcno;
    }

    public void setTcno(String tcno) {
        this.tcno = tcno;
    }
}

