/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author yiachmad
 */
public class Produk {
    private String kodeProduk;
    private String namaProduk;
    private double harga;
    private int stok;

    public Produk(String kodeProduk, String namaProduk, double harga, int stok) {
        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.stok = stok;
    }

    public String getKodeProduk() { return kodeProduk; }
    public String getNamaProduk() { return namaProduk; }
    public double getHarga() { return harga; }
    public int getStok() { return stok; }

    public void setKodeProduk(String kodeProduk) { this.kodeProduk = kodeProduk; }
    public void setNamaProduk(String namaProduk) { this.namaProduk = namaProduk; }
    public void setHarga(double harga) { this.harga = harga; }
    public void setStok(int stok) { this.stok = stok; }
}
