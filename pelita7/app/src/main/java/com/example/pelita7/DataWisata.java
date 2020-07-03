package com.example.pelita7;

public class DataWisata {
    private String idwisata, nama, harga, alamat;

    public DataWisata() {
    }

    public DataWisata (String idwisata, String nama ,String harga, String alamat) {
        this.idwisata = idwisata;
        this.nama = nama;
this.alamat = alamat ;
        this.harga = harga;


    }

    public String getIdWisata() {
        return idwisata;
    }

    public void setIdWisata(String idwisata) {
        this.idwisata = idwisata;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }


    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }


}