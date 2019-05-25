package com.anisa.uts.model;

public class Barang {

    private int id;
    private String kdbarang;
    private String nmbarang;
    private String merek;
    private double harga;

    public Barang() {
    }

    public Barang(int id) {
        this.id = id;
    }

    public Barang(int id, String kdBarang, String nmBarang, String merek, double harga) {
        this(kdBarang, nmBarang, merek, harga);
        this.id = id;
    }

    public Barang(String kdBarang, String nmBarang, String merek, double harga) {
        this.kdbarang = kdBarang;
        this.nmbarang = nmBarang;
        this.merek = merek;
        this.harga = harga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKdbarang() {
        return kdbarang;
    }

    public void setKdbarang(String kdbarang) {
        this.kdbarang = kdbarang;
    }

    public String getNmbarang() {
        return nmbarang;
    }

    public void setNmbarang(String nmbarang) {
        this.nmbarang = nmbarang;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }
}
