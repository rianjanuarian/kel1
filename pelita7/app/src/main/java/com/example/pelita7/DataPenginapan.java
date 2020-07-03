package com.example.pelita7;

public class DataPenginapan {
    private String idhotel, nama, alamat, fasilitas , harga;

    public DataPenginapan() {
    }

    public DataPenginapan (String idhotel, String nama, String alamat, String fasilitas, String harga) {
        this.idhotel = idhotel;
        this.nama = nama;
        this.alamat = alamat;

        this.fasilitas = fasilitas;
        this.harga = harga;


    }

    public String getIdhotel() {
        return idhotel;
    }

    public void setIdhotel(String idhotel) {
        this.idhotel = idhotel;
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

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }





    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }


}