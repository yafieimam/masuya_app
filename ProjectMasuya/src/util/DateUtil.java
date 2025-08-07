/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author yiachmad
 */
public class DateUtil {
    public static String getInvoicePrefix() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        return "INV/" + formatter.format(now);
    }
}
