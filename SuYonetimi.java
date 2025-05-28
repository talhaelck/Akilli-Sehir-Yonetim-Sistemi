package com.mycompany.sehir;

public class SuYonetimi {
    private float suRezervi;
    private float suTuketimi;

    public SuYonetimi(float suRezervi, float suTuketimi) {
        this.suRezervi = suRezervi;
        this.suTuketimi = suTuketimi;
    }

    public float getSuRezervi() {
        return suRezervi;
    }

    public float getSuTuketimi() {
        return suTuketimi;
    }
}
