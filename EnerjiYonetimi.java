package com.mycompany.sehir;

public class EnerjiYonetimi {
    private float enerjiUretimi;
    private float enerjiTuketimi;

    public EnerjiYonetimi(float enerjiUretimi, float enerjiTuketimi) {
        this.enerjiUretimi = enerjiUretimi;
        this.enerjiTuketimi = enerjiTuketimi;
    }

    public float getEnerjiUretimi() {
        return enerjiUretimi;
    }

    public float getEnerjiTuketimi() {
        return enerjiTuketimi;
    }
}
