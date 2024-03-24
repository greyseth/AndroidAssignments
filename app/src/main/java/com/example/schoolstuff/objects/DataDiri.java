package com.example.schoolstuff.objects;

public class DataDiri {
    int id;
    String nik;
    String alamat;
    String kota;
    String kelamin;

    public DataDiri(int id, String nik, String alamat, String kota, String kelamin) {
        this.id = id;
        this.nik = nik;
        this.alamat = alamat;
        this.kota = kota;
        this.kelamin = kelamin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
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

    public String getKelamin() {
        return kelamin;
    }

    public void setKelamin(String kelamin) {
        this.kelamin = kelamin;
    }
}
