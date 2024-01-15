package com.example.schoolstuff.models;

public class User {
    String nama;
    String alamat;
    String kota;
    String jenis;

    public User(String nama, String alamat, String kota, String jenis) {
        this.nama = nama;
        this.alamat = alamat;
        this.kota = kota;
        this.jenis = jenis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
}
