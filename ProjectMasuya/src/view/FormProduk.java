/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ProdukController;
import model.Produk;
import validation.ProdukValidator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author yiachmad
 */
public class FormProduk extends JFrame {
    private JTextField tfKode = new JTextField(10);
    private JTextField tfNama = new JTextField(20);
    private JTextField tfHarga = new JTextField(10);
    private JTextField tfStok = new JTextField(5);
    
    private DefaultTableModel model = new DefaultTableModel(new String[]{"Kode", "Nama", "Harga", "Stok"}, 0);
    private JTable table = new JTable(model);
    
    private JButton btnSimpan = new JButton("Simpan");
    private JButton btnHapus = new JButton("Hapus");
    private JButton btnBack = new JButton("Back");
    
    private boolean isEditMode = false;
    private String selectedKode = null;

    /**
     * Creates new form FormProduk
     */
    public FormProduk() {
        setTitle("Form Produk");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelForm.add(new JLabel("Kode Produk"));
        panelForm.add(tfKode);
        panelForm.add(new JLabel("Nama Produk"));
        panelForm.add(tfNama);
        panelForm.add(new JLabel("Harga"));
        panelForm.add(tfHarga);
        panelForm.add(new JLabel("Stok"));
        panelForm.add(tfStok);

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(e -> {
            if (isEditMode) {
                updateProduk();
            } else {
                simpanProduk();
            }
        });
        panelForm.add(btnSimpan);
        
        btnHapus.setEnabled(false);
        btnHapus.addActionListener(e -> hapusProduk());
        panelForm.add(btnHapus);

        add(panelForm, BorderLayout.NORTH);
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnBack.addActionListener(e -> {
            dispose();
            new HomePage();
        });
        panelBottom.add(btnBack);
        add(panelBottom, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                selectedKode = model.getValueAt(row, 0).toString();
                tfKode.setText(selectedKode);
                tfNama.setText(model.getValueAt(row, 1).toString());
                tfHarga.setText(model.getValueAt(row, 2).toString());
                tfStok.setText(model.getValueAt(row, 3).toString());
                tfKode.setEnabled(false);
                isEditMode = true;
                btnSimpan.setText("Update");
                btnHapus.setEnabled(true);
            }
        });

        loadTable();
        setVisible(true);
    }
    
    private void loadTable() {
        try {
            List<Produk> data = ProdukController.getAllProduk();
            model.setRowCount(0);
            for (Produk p : data) {
                model.addRow(new Object[]{p.getKodeProduk(), p.getNamaProduk(), p.getHarga(), p.getStok()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal load data: " + e.getMessage());
        }
    }
    
    private void simpanProduk() {
        String kode = tfKode.getText().trim();
        String nama = tfNama.getText().trim();
        double harga;
        int stok;

        // Validasi dasar
        try {
            harga = Double.parseDouble(tfHarga.getText().trim());
            stok = Integer.parseInt(tfStok.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga dan Stok harus angka!");
            return;
        }

        if (!ProdukValidator.validKode(kode)) {
            JOptionPane.showMessageDialog(this, "Kode hanya boleh alphanumeric!");
            return;
        }

        try {
            if (ProdukController.isKodeProdukExist(kode)) {
                JOptionPane.showMessageDialog(this, "Kode produk sudah ada!");
                return;
            }
            Produk p = new Produk(kode, nama, harga, stok);
            ProdukController.insertProduk(p);
            JOptionPane.showMessageDialog(this, "Produk berhasil disimpan.");
            loadTable();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal simpan: " + ex.getMessage());
        }
    }
    
    private void updateProduk() {
        String nama = tfNama.getText().trim();
        double harga;
        int stok;

        try {
            harga = Double.parseDouble(tfHarga.getText().trim());
            stok = Integer.parseInt(tfStok.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga dan Stok harus angka!");
            return;
        }

        try {
            Produk p = new Produk(selectedKode, nama, harga, stok);
            ProdukController.updateProduk(p);
            JOptionPane.showMessageDialog(this, "Produk berhasil diupdate.");
            resetForm();
            loadTable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal update: " + e.getMessage());
        }
    }
    
    private void hapusProduk() {
        try {
            boolean used = ProdukController.isProdukUsedInTransaksi(selectedKode);
            if (used) {
                JOptionPane.showMessageDialog(this, "Produk sudah digunakan di transaksi. Tidak bisa dihapus.");
                return;
            }

            ProdukController.deleteProduk(selectedKode);
            JOptionPane.showMessageDialog(this, "Produk berhasil dihapus.");
            resetForm();
            loadTable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal hapus: " + e.getMessage());
        }
    }
    
    private void resetForm() {
        tfKode.setText("");
        tfNama.setText("");
        tfHarga.setText("");
        tfStok.setText("");
        tfKode.setEnabled(true);
        isEditMode = false;
        selectedKode = null;
        btnSimpan.setText("Simpan");
        btnHapus.setEnabled(false);
        table.clearSelection();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
