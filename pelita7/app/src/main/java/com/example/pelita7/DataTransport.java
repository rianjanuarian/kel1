package com.example.pelita7;

public class DataTransport {
    private String idtransport, nama, alamat, kapasitas, harga;

    public DataTransport() {
    }

    public DataTransport (String idtransport, String nama, String alamat, String kapasitas, String harga) {
        this.idtransport = idtransport;
        this.nama = nama;
        this.alamat = alamat;

        this.kapasitas = kapasitas;
        this.harga = harga;


    }

    public String getIdtransport() {
        return idtransport;
    }

    public void setIdtransport(String idtransport) {
        this.idtransport = idtransport;
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





    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }


}




