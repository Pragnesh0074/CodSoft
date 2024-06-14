package com.example.dayquote;

public class QuoteModel {

    String q;
    String a;
    String h;

    public QuoteModel(String q, String a, String h) {
        this.q = q;
        this.a = a;
        this.h = h;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }
}
