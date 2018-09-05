package appdevelope.imamalys.id.pariwisatayogyakarta;

/**
 * Created by ASUS on 05/09/2018.
 */

import java.util.ArrayList;

public class ArticlePariwisata {
    private String nama, gambar, alamat, detail;

    public ArticlePariwisata() {
    }

    public ArticlePariwisata(String nama, String gambar, String alamat, String detail) {
        this.nama = nama;
        this.gambar = gambar;
        this.alamat = alamat;
        this.detail = detail;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
