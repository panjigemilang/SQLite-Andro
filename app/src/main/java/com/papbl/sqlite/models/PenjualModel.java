package com.papbl.sqlite.models;

public class PenjualModel {

    private String nama, email, noTelp;
    private int id;

    public PenjualModel(){}

    public PenjualModel(int id, String nama, String email, String noTelp){
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.noTelp = noTelp;
    }

    public PenjualModel(String nama, String email, String noTelp){
        this.nama = nama;
        this.email = email;
        this.noTelp = noTelp;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }
}
