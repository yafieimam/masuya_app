/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author yiachmad
 */
public class ValidatorUtil {
    public static boolean isAlphanumeric(String input) {
        return input != null && input.matches("^[a-zA-Z0-9]+$");
    }
}
