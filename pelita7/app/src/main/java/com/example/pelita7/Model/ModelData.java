package com.example.pelita7.Model;

public class ModelData {
    String idkatalog, namakatalog , idtransport , idhotel, idwisata, hargakatalog, status;

    public ModelData(){}

    public ModelData(String idkatalog , String namakatalog, String idtransport, String idhotel, String idwisata, String hargakatalog, String status) {
        this.idkatalog = idkatalog;
        this.namakatalog = namakatalog;
        this.idtransport= idtransport;
        this.idhotel = idhotel;
        this.idwisata = idwisata;
        this.hargakatalog = hargakatalog;
        this.status = status;
    }

    public String getIdkatalog() {
        return idkatalog;
    }

    public void setIdkatalog(String idkatalog) {
        this.idkatalog = idkatalog;
    }

    public String getNamakatalog() {
        return namakatalog;
    }

    public void setNamakatalog(String namakatalog) {
        this.namakatalog = namakatalog;
    }

    public String getIdtransport() {
        return idtransport;
    }

    public void setIdtransport(String idtransport) {
        this.idtransport = idtransport;
    }

    public String getIdhotel() {
        return idhotel;
    }

    public void setIdhotel(String idhotel) {
        this.idhotel = idhotel;
    }

    public String getIdwisata() {
        return idwisata;
    }

    public void setIdwisata(String idwisata) {
        this.idwisata = idwisata;
    }

    public String getHargakatalog() {
        return hargakatalog;
    }

    public void setHargakatalog(String hargakatalog) {
        this.hargakatalog = hargakatalog;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
