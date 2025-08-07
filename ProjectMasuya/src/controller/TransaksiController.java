/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.TransaksiHeader;
import model.TransaksiDetail;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yiachmad
 */
public class TransaksiController {
    public static void simpanTransaksi(TransaksiHeader header) throws SQLException {
        String insertHeader = "INSERT INTO TransaksiHeader VALUES (?, ?, ?, ?, ?, ?)";
        String insertDetail = "INSERT INTO TransaksiDetail VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Simpan header
            try (PreparedStatement stmt = conn.prepareStatement(insertHeader)) {
                stmt.setString(1, header.getNoInv());
                stmt.setString(2, header.getKodeCustomer());
                stmt.setString(3, header.getNamaCustomer());
                stmt.setString(4, header.getAlamat());
                stmt.setDate(5, new java.sql.Date(header.getTanggal().getTime()));
                stmt.setDouble(6, header.getTotal());
                stmt.executeUpdate();
            }

            // Simpan detail + update stok
            for (TransaksiDetail d : header.getDetails()) {
                try (PreparedStatement stmt = conn.prepareStatement(insertDetail)) {
                    stmt.setString(1, header.getNoInv());
                    stmt.setString(2, d.getKodeProduk());
                    stmt.setString(3, d.getNamaProduk());
                    stmt.setInt(4, d.getQty());
                    stmt.setDouble(5, d.getHarga());
                    stmt.setDouble(6, d.getDisc1());
                    stmt.setDouble(7, d.getDisc2());
                    stmt.setDouble(8, d.getDisc3());
                    stmt.setDouble(9, d.getHargaNet());
                    stmt.setDouble(10, d.getJumlah());
                    stmt.executeUpdate();
                }

                // Update stok
                try (PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE Produk SET stok = stok - ? WHERE kode_produk = ?")) {
                    stmt.setInt(1, d.getQty());
                    stmt.setString(2, d.getKodeProduk());
                    stmt.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public static TransaksiHeader getTransaksiByNoInv(String noInv) throws SQLException {
        String sqlHeader = "SELECT * FROM TransaksiHeader WHERE no_inv = ?";
        String sqlDetail = "SELECT * FROM TransaksiDetail WHERE no_inv = ?";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sqlHeader);
            ps.setString(1, noInv);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                TransaksiHeader header = new TransaksiHeader();
                header.setNoInv(noInv);
                header.setKodeCustomer(rs.getString("kode_customer"));
                header.setNamaCustomer(rs.getString("nama_customer"));
                header.setAlamat(rs.getString("alamat"));
                header.setTanggal(rs.getDate("tgl_inv"));
                header.setTotal(rs.getDouble("total"));

                List<TransaksiDetail> detailList = new ArrayList<>();
                PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
                psDetail.setString(1, noInv);
                ResultSet rsDetail = psDetail.executeQuery();

                while (rsDetail.next()) {
                    TransaksiDetail d = new TransaksiDetail(
                        rsDetail.getString("kode_produk"),
                        rsDetail.getString("nama_produk"),
                        rsDetail.getInt("qty"),
                        rsDetail.getDouble("harga"),
                        rsDetail.getDouble("disc1"),
                        rsDetail.getDouble("disc2"),
                        rsDetail.getDouble("disc3")
                    );
                    detailList.add(d);
                }

                header.setDetails(detailList);
                return header;
            }
        }
        return null;
    }
    
    public static void deleteTransaksi(String noInv) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            // 1. Kembalikan stok
            String getDetails = "SELECT kode_produk, qty FROM TransaksiDetail WHERE no_inv = ?";
            try (PreparedStatement ps = conn.prepareStatement(getDetails)) {
                ps.setString(1, noInv);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    try (PreparedStatement psUpdate = conn.prepareStatement(
                            "UPDATE Produk SET stok = stok + ? WHERE kode_produk = ?")) {
                        psUpdate.setInt(1, rs.getInt("qty"));
                        psUpdate.setString(2, rs.getString("kode_produk"));
                        psUpdate.executeUpdate();
                    }
                }
            }

            // 2. Hapus detail & header
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM TransaksiDetail WHERE no_inv = ?")) {
                ps.setString(1, noInv);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM TransaksiHeader WHERE no_inv = ?")) {
                ps.setString(1, noInv);
                ps.executeUpdate();
            }

            conn.commit();
        }
    }
}
