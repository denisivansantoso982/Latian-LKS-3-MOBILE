package com.anew.denis.testproject;

public class Buku {
    private String kode_buku;
    private String judul;
    private String kategori;
    private String penulis;
    private String penerbit;
    private String tglPinjam;
    private String deskripsi;
    private int harga;
    private String tglKembali;

    public String getDeskripsi() {
        return deskripsi;
    }

    public int getHarga() {
        return harga;
    }

    public String getKode_buku() {
        return kode_buku;
    }

    public String getJudul() {
        return judul;
    }

    public String getKategori() {
        return kategori;
    }

    public String getPenulis() {
        return penulis;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public String getTglPinjam() {
        return tglPinjam;
    }

    public String getTglKembali() {
        return tglKembali;
    }

    public Buku(String kode_buku, String judul, String kategori, String penulis, String penerbit, String tglPinjam, String tglKembali){
        this.kode_buku = kode_buku;
        this.judul = judul;
        this.kategori = kategori;
        this.penerbit = penerbit;
        this.penulis = penulis;
        this.tglKembali = tglKembali;
        this.tglPinjam = tglPinjam;
    }

    public Buku(String kode_buku, String judul, String kategori, String penulis, String penerbit, int harga, String deskripsi){
        this.kode_buku = kode_buku;
        this.judul = judul;
        this.kategori = kategori;
        this.penerbit = penerbit;
        this.penulis = penulis;
        this.harga = harga;
        this.deskripsi = deskripsi;
    }
}
