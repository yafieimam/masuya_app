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
public class TransaksiDetail {
    private String kodeProduk;
    private String namaProduk;
    private int qty;
    private double harga;
    private double disc1;
    private double disc2;
    private double disc3;
    private double hargaNet;
    private double jumlah;

    public TransaksiDetail() {
    }

    public TransaksiDetail(String kodeProduk, String namaProduk, int qty, double harga,
                           double disc1, double disc2, double disc3) {
        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk;
        this.qty = qty;
        this.harga = harga;
        this.disc1 = disc1;
        this.disc2 = disc2;
        this.disc3 = disc3;
        this.hargaNet = hitungHargaNet(harga, disc1, disc2, disc3);
        this.jumlah = this.hargaNet * qty;
    }

    private double hitungHargaNet(double harga, double d1, double d2, double d3) {
        double h1 = harga - (harga * d1 / 100);
        double h2 = h1 - (h1 * d2 / 100);
        double h3 = h2 - (h2 * d3 / 100);
        return h3;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
        this.jumlah = this.hargaNet * qty;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
        this.hargaNet = hitungHargaNet(harga, disc1, disc2, disc3);
        this.jumlah = this.hargaNet * qty;
    }

    public double getDisc1() {
        return disc1;
    }

    public void setDisc1(double disc1) {
        this.disc1 = disc1;
        this.hargaNet = hitungHargaNet(harga, disc1, disc2, disc3);
        this.jumlah = this.hargaNet * qty;
    }

    public double getDisc2() {
        return disc2;
    }

    public void setDisc2(double disc2) {
        this.disc2 = disc2;
        this.hargaNet = hitungHargaNet(harga, disc1, disc2, disc3);
        this.jumlah = this.hargaNet * qty;
    }

    public double getDisc3() {
        return disc3;
    }

    public void setDisc3(double disc3) {
        this.disc3 = disc3;
        this.hargaNet = hitungHargaNet(harga, disc1, disc2, disc3);
        this.jumlah = this.hargaNet * qty;
    }

    public double getHargaNet() {
        return hargaNet;
    }

    public void setHargaNet(double hargaNet) {
        this.hargaNet = hargaNet;
        this.jumlah = hargaNet * qty;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }
}
