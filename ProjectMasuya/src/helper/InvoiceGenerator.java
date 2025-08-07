/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import util.DBConnection;

/**
 *
 * @author yiachmad
 */
public class InvoiceGenerator {
    public static String generate() throws SQLException {
        String prefix = "INV";
        String monthYear = new SimpleDateFormat("YYMM").format(new Date());
        String like = prefix + "/" + monthYear + "/%";
        String sql = "SELECT MAX(no_inv) FROM TransaksiHeader WHERE no_inv LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, like);
            ResultSet rs = stmt.executeQuery();
            int next = 1;
            if (rs.next() && rs.getString(1) != null) {
                String last = rs.getString(1);
                String[] parts = last.split("/");
                next = Integer.parseInt(parts[2]) + 1;
            }
            return String.format("%s/%s/%04d", prefix, monthYear, next);
        }
    }
}
