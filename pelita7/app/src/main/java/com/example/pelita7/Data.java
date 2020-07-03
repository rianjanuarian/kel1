package com.example.pelita7;

public class Data {
    private String idkatalog, namakatalog,  hargakatalog, namatransport;

    public Data() {
    }

    public Data(String idkatalog, String namakatalog, String hargakatalog, String namatransport) {
        this.idkatalog = idkatalog;
        this.namakatalog = namakatalog;

        this.hargakatalog = hargakatalog;
        this.namatransport = namatransport;
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



    public String getHargakatalog() { return hargakatalog; }

    public void setHargakatalog(String hargakatalog) {
        this.hargakatalog = hargakatalog;
    }

    public String getNamatransport() { return namatransport; }

    public void setNamatransport(String namatransport) {
        this.namatransport = namatransport;
    }
}
