/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.CustomerController;
import model.Customer;
import validation.CustomerValidator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import util.DBConnection;

/**
 *
 * @author yiachmad
 */
public class FormCustomer extends JFrame {
    private JTextField tfKode = new JTextField(10);
    private JTextField tfNama = new JTextField(20);
    private JTextField tfAlamat = new JTextField(50);
    private JTextField tfProvinsi = new JTextField(20);
    private JTextField tfKota = new JTextField(20);
    private JTextField tfKecamatan = new JTextField(20);
    private JTextField tfKelurahan = new JTextField(20);
    private JTextField tfKodePos = new JTextField(10);

    private DefaultTableModel model = new DefaultTableModel(new String[]{"Kode", "Nama", "Alamat", "Kota"}, 0);
    private JTable table = new JTable(model);
    
    private JButton btnSimpan = new JButton("Simpan");
    private JButton btnHapus = new JButton("Hapus");
    private JButton btnBack = new JButton("Back");
    
    private boolean isEditMode = false;
    private String selectedKode = null;
    /**
     * Creates new form FormCustomer
     */
    public FormCustomer() {
        setTitle("Form Customer");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(9, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Kode Customer"));
        panel.add(tfKode);
        panel.add(new JLabel("Nama Customer"));
        panel.add(tfNama);
        panel.add(new JLabel("Alamat Lengkap"));
        panel.add(tfAlamat);
        panel.add(new JLabel("Provinsi"));
        panel.add(tfProvinsi);
        panel.add(new JLabel("Kota"));
        panel.add(tfKota);
        panel.add(new JLabel("Kecamatan"));
        panel.add(tfKecamatan);
        panel.add(new JLabel("Kelurahan"));
        panel.add(tfKelurahan);
        panel.add(new JLabel("Kode Pos"));
        panel.add(tfKodePos);

        
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(e -> {
            if (isEditMode) {
                updateCustomer();
            } else {
                simpanCustomer();
            }
        });
        panel.add(btnSimpan);
        
        btnHapus.setEnabled(false);
        btnHapus.addActionListener(e -> hapusCustomer());
        panel.add(btnHapus);
        add(panel, BorderLayout.NORTH);
        
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
                tfAlamat.setText(model.getValueAt(row, 2).toString());
                tfKota.setText(model.getValueAt(row, 3).toString());
                
                // Bisa juga load detail lengkap dari DB jika dibutuhkan
                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "SELECT provinsi, kecamatan, kelurahan, kode_pos FROM Customer WHERE kode_customer = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, selectedKode);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            tfProvinsi.setText(rs.getString("provinsi"));
                            tfKecamatan.setText(rs.getString("kecamatan"));
                            tfKelurahan.setText(rs.getString("kelurahan"));
                            tfKodePos.setText(rs.getString("kode_pos"));
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Gagal memuat detail data customer", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                tfKode.setEnabled(false);
                isEditMode = true;
                btnSimpan.setText("Update");
                btnHapus.setEnabled(true);
            }
        });

        loadTable();
        setVisible(true);
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

    private void loadTable() {
        try {
            List<Customer> data = CustomerController.getAllCustomer();
            model.setRowCount(0);
            for (Customer c : data) {
                model.addRow(new Object[]{
                        c.getKodeCustomer(),
                        c.getNamaCustomer(),
                        c.getAlamatLengkap(),
                        c.getKota()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal load customer: " + e.getMessage());
        }
    }

    private void simpanCustomer() {
        String kode = tfKode.getText().trim();
        String nama = tfNama.getText().trim();

        if (!CustomerValidator.validKode(kode)) {
            JOptionPane.showMessageDialog(this, "Kode customer hanya boleh alphanumeric.");
            return;
        }

        if (!CustomerValidator.validNama(nama)) {
            JOptionPane.showMessageDialog(this, "Nama customer wajib diisi.");
            return;
        }

        try {
            if (CustomerController.isKodeExist(kode)) {
                JOptionPane.showMessageDialog(this, "Kode customer sudah digunakan.");
                return;
            }

            Customer c = new Customer(
                    kode,
                    nama,
                    tfAlamat.getText().trim(),
                    tfProvinsi.getText().trim(),
                    tfKota.getText().trim(),
                    tfKecamatan.getText().trim(),
                    tfKelurahan.getText().trim(),
                    tfKodePos.getText().trim()
            );
            CustomerController.insertCustomer(c);
            JOptionPane.showMessageDialog(this, "Customer berhasil disimpan.");
            loadTable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal simpan: " + e.getMessage());
        }
    }
    
    private void updateCustomer() {
        String nama = tfNama.getText().trim();
        if (!CustomerValidator.validNama(nama)) {
            JOptionPane.showMessageDialog(this, "Nama customer wajib diisi.");
            return;
        }

        try {
            Customer c = new Customer(
                    selectedKode,
                    nama,
                    tfAlamat.getText().trim(),
                    tfProvinsi.getText().trim(),
                    tfKota.getText().trim(),
                    tfKecamatan.getText().trim(),
                    tfKelurahan.getText().trim(),
                    tfKodePos.getText().trim()
            );
            CustomerController.updateCustomer(c);
            JOptionPane.showMessageDialog(this, "Customer berhasil diupdate.");
            resetForm();
            loadTable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal update: " + e.getMessage());
        }
    }
    
    private void hapusCustomer() {
        try {
            boolean used = CustomerController.isCustomerUsedInTransaksi(selectedKode);
            if (used) {
                JOptionPane.showMessageDialog(this, "Customer sudah digunakan di transaksi. Tidak bisa dihapus.");
                return;
            }

            CustomerController.deleteCustomer(selectedKode);
            JOptionPane.showMessageDialog(this, "Customer berhasil dihapus.");
            resetForm();
            loadTable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal hapus: " + e.getMessage());
        }
    }
    
    private void resetForm() {
        tfKode.setText("");
        tfNama.setText("");
        tfAlamat.setText("");
        tfProvinsi.setText("");
        tfKota.setText("");
        tfKecamatan.setText("");
        tfKelurahan.setText("");
        tfKodePos.setText("");

        tfKode.setEnabled(true);
        isEditMode = false;
        selectedKode = null;
        btnSimpan.setText("Simpan");
        btnHapus.setEnabled(false);
        table.clearSelection();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
