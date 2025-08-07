/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Produk;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yiachmad
 */
public class ProdukController {
    public static boolean isKodeProdukExist(String kode) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Produk WHERE kode_produk = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kode);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public static void insertProduk(Produk p) throws SQLException {
        String sql = "INSERT INTO Produk (kode_produk, nama_produk, harga, stok) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getKodeProduk());
            stmt.setString(2, p.getNamaProduk());
            stmt.setDouble(3, p.getHarga());
            stmt.setInt(4, p.getStok());
            stmt.executeUpdate();
        }
    }

    public static List<Produk> getAllProduk() throws SQLException {
        List<Produk> list = new ArrayList<>();
        String sql = "SELECT * FROM Produk ORDER BY kode_produk";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Produk(
                    rs.getString("kode_produk"),
                    rs.getString("nama_produk"),
                    rs.getDouble("harga"),
                    rs.getInt("stok")
                ));
            }
        }
        return list;
    }
    
    public static Produk getProdukByKode(String kode) throws SQLException {
        String sql = "SELECT * FROM Produk WHERE kode_produk = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Produk(
                    rs.getString("kode_produk"),
                    rs.getString("nama_produk"),
                    rs.getDouble("harga"),
                    rs.getInt("stok")
                );
            }
        }
        return null;
    }
    
    public static void updateProduk(Produk p) throws SQLException {
        String sql = "UPDATE Produk SET nama_produk = ?, harga = ?, stok = ? WHERE kode_produk = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNamaProduk());
            stmt.setDouble(2, p.getHarga());
            stmt.setInt(3, p.getStok());
            stmt.setString(4, p.getKodeProduk());
            stmt.executeUpdate();
        }
    }
    
    public static void deleteProduk(String kode) throws SQLException {
        String sql = "DELETE FROM Produk WHERE kode_produk = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kode);
            stmt.executeUpdate();
        }
    }

    public static boolean isProdukUsedInTransaksi(String kode) throws SQLException {
        String sql = "SELECT COUNT(*) FROM TransaksiDetail WHERE kode_produk = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kode);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }
}
