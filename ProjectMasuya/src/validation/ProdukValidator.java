/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

import util.ValidatorUtil;

/**
 *
 * @author yiachmad
 */
public class ProdukValidator {
    public static boolean validKode(String kode) {
        return ValidatorUtil.isAlphanumeric(kode);
    }

    public static boolean validStok(int stok) {
        return stok >= 0;
    }

    public static boolean validHarga(double harga) {
        return harga > 0;
    }
}
