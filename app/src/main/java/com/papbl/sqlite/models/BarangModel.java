package com.papbl.sqlite.models;

public class BarangModel {

    private String nama, kategori, penjual;
    private int stock, id;

    public BarangModel(){}

    public BarangModel(String nama, String kategori, int stock, String penjual) {
        this.nama = nama;
        this.kategori = kategori;
        this.stock = stock;
        this.penjual = penjual;
    }

    public BarangModel(String nama, String kategori, int stock) {
        this.nama = nama;
        this.kategori = kategori;
        this.stock = stock;
    }

    public BarangModel(int id, String nama, String kategori, int stock){
        this.id = id;
        this.nama = nama;
        this.kategori = kategori;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getPenjual() {
        return penjual;
    }

    public void setPenjual(String penjual) {
        this.penjual = penjual;
    }
}
