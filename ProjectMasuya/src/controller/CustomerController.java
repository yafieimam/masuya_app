/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Customer;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yiachmad
 */
public class CustomerController {
    public static boolean isKodeExist(String kode) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Customer WHERE kode_customer = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kode);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public static void insertCustomer(Customer c) throws SQLException {
        String sql = "INSERT INTO Customer (kode_customer, nama_customer, alamat_lengkap, provinsi, kota, kecamatan, kelurahan, kode_pos) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getKodeCustomer());
            stmt.setString(2, c.getNamaCustomer());
            stmt.setString(3, c.getAlamatLengkap());
            stmt.setString(4, c.getProvinsi());
            stmt.setString(5, c.getKota());
            stmt.setString(6, c.getKecamatan());
            stmt.setString(7, c.getKelurahan());
            stmt.setString(8, c.getKodePos());
            stmt.executeUpdate();
        }
    }

    public static List<Customer> getAllCustomer() throws SQLException {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM Customer ORDER BY kode_customer";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Customer(
                        rs.getString("kode_customer"),
                        rs.getString("nama_customer"),
                        rs.getString("alamat_lengkap"),
                        rs.getString("provinsi"),
                        rs.getString("kota"),
                        rs.getString("kecamatan"),
                        rs.getString("kelurahan"),
                        rs.getString("kode_pos")
                ));
            }
        }
        return list;
    }
    
    public static Customer getCustomerByKode(String kode) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE kode_customer = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Customer(
                    rs.getString("kode_customer"),
                    rs.getString("nama_customer"),
                    rs.getString("alamat_lengkap"),
                    rs.getString("provinsi"),
                    rs.getString("kota"),
                    rs.getString("kecamatan"),
                    rs.getString("kelurahan"),
                    rs.getString("kode_pos")
                );
            }
        }
        return null;
    }
    
    public static void updateCustomer(Customer c) throws SQLException {
        String sql = "UPDATE Customer SET nama_customer = ?, alamat_lengkap = ?, provinsi = ?, kota = ?, kecamatan = ?, kelurahan = ?, kode_pos = ? WHERE kode_customer = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNamaCustomer());
            stmt.setString(2, c.getAlamatLengkap());
            stmt.setString(3, c.getProvinsi());
            stmt.setString(4, c.getKota());
            stmt.setString(5, c.getKecamatan());
            stmt.setString(6, c.getKelurahan());
            stmt.setString(7, c.getKodePos());
            stmt.setString(8, c.getKodeCustomer());
            stmt.executeUpdate();
        }
    }

    public static void deleteCustomer(String kode) throws SQLException {
        String sql = "DELETE FROM Customer WHERE kode_customer = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kode);
            stmt.executeUpdate();
        }
    }

    public static boolean isCustomerUsedInTransaksi(String kode) throws SQLException {
        String sql = "SELECT COUNT(*) FROM TransaksiHeader WHERE kode_customer = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kode);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }
}
