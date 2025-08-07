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
public class Customer {
    private String kodeCustomer;
    private String namaCustomer;
    private String alamatLengkap;
    private String provinsi;
    private String kota;
    private String kecamatan;
    private String kelurahan;
    private String kodePos;

    public Customer(String kodeCustomer, String namaCustomer, String alamatLengkap,
                    String provinsi, String kota, String kecamatan,
                    String kelurahan, String kodePos) {
        this.kodeCustomer = kodeCustomer;
        this.namaCustomer = namaCustomer;
        this.alamatLengkap = alamatLengkap;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kecamatan = kecamatan;
        this.kelurahan = kelurahan;
        this.kodePos = kodePos;
    }

    public String getKodeCustomer() { return kodeCustomer; }
    public String getNamaCustomer() { return namaCustomer; }
    public String getAlamatLengkap() { return alamatLengkap; }
    public String getProvinsi() { return provinsi; }
    public String getKota() { return kota; }
    public String getKecamatan() { return kecamatan; }
    public String getKelurahan() { return kelurahan; }
    public String getKodePos() { return kodePos; }

    public void setKodeCustomer(String kodeCustomer) { this.kodeCustomer = kodeCustomer; }
    public void setNamaCustomer(String namaCustomer) { this.namaCustomer = namaCustomer; }
    public void setAlamatLengkap(String alamatLengkap) { this.alamatLengkap = alamatLengkap; }
    public void setProvinsi(String provinsi) { this.provinsi = provinsi; }
    public void setKota(String kota) { this.kota = kota; }
    public void setKecamatan(String kecamatan) { this.kecamatan = kecamatan; }
    public void setKelurahan(String kelurahan) { this.kelurahan = kelurahan; }
    public void setKodePos(String kodePos) { this.kodePos = kodePos; }
}
