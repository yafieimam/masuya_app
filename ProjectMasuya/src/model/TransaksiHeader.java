/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author yiachmad
 */
public class TransaksiHeader {
    private String noInv;
    private String kodeCustomer;
    private String namaCustomer;
    private String alamat;
    private Date tanggal;
    private double total;
    private List<TransaksiDetail> details;

    public TransaksiHeader() {
    }

    public TransaksiHeader(String noInv, String kodeCustomer, String namaCustomer,
                           String alamat, Date tanggal, double total,
                           List<TransaksiDetail> details) {
        this.noInv = noInv;
        this.kodeCustomer = kodeCustomer;
        this.namaCustomer = namaCustomer;
        this.alamat = alamat;
        this.tanggal = tanggal;
        this.total = total;
        this.details = details;
    }

    public String getNoInv() {
        return noInv;
    }

    public void setNoInv(String noInv) {
        this.noInv = noInv;
    }

    public String getKodeCustomer() {
        return kodeCustomer;
    }

    public void setKodeCustomer(String kodeCustomer) {
        this.kodeCustomer = kodeCustomer;
    }

    public String getNamaCustomer() {
        return namaCustomer;
    }

    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer = namaCustomer;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<TransaksiDetail> getDetails() {
        return details;
    }

    public void setDetails(List<TransaksiDetail> details) {
        this.details = details;
    }
}
