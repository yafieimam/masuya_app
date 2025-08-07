/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.CustomerController;
import controller.ProdukController;
import controller.TransaksiController;
import helper.InvoiceGenerator;
import model.Customer;
import model.Produk;
import model.TransaksiDetail;
import model.TransaksiHeader;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 *
 * @author yiachmad
 */
public class FormTransaksi extends JFrame {
    private JTextField tfNoInv, tfTanggal, tfNamaCustomer, tfAlamat;
    private JComboBox<String> cbCustomer, cbProduk;
    private JTextField tfQty, tfHarga, tfDisc1, tfDisc2, tfDisc3;
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel lblTotal;
    private List<TransaksiDetail> detailList = new ArrayList<>();
    
    private boolean isEditMode = false;
    private String originalNoInv = null;
    
    /**
     * Creates new form FormTransaksi
     */
    public FormTransaksi() {
        setTitle("Form Transaksi");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelHeader = new JPanel(new GridLayout(5, 2, 5, 5));
        panelHeader.setBorder(BorderFactory.createTitledBorder("Header Transaksi"));

        tfNoInv = new JTextField();
        tfNoInv.setEditable(false);
        tfTanggal = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        tfTanggal.setEditable(false);

        cbCustomer = new JComboBox<>();
        cbCustomer.addItem("Pilih Customer...");
        tfNamaCustomer = new JTextField();
        tfAlamat = new JTextField();
        tfNamaCustomer.setEditable(false);
        tfAlamat.setEditable(false);

        panelHeader.add(new JLabel("No. Invoice:"));
        panelHeader.add(tfNoInv);
        panelHeader.add(new JLabel("Tanggal:"));
        panelHeader.add(tfTanggal);
        panelHeader.add(new JLabel("Kode Customer:"));
        panelHeader.add(cbCustomer);
        panelHeader.add(new JLabel("Nama Customer:"));
        panelHeader.add(tfNamaCustomer);
        panelHeader.add(new JLabel("Alamat:"));
        panelHeader.add(tfAlamat);
        
        JButton btnCariTransaksi = new JButton("Cari / Edit Transaksi");
        btnCariTransaksi.addActionListener(e -> new FormCariTransaksi());
        
        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
            dispose();
            new HomePage();
        });


        JPanel panelHeaderBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelHeaderBottom.add(btnCariTransaksi);
        panelHeaderBottom.add(btnBack);

        JPanel panelHeaderWrapper = new JPanel(new BorderLayout());
        panelHeaderWrapper.add(panelHeader, BorderLayout.CENTER);
        panelHeaderWrapper.add(panelHeaderBottom, BorderLayout.SOUTH);

        add(panelHeaderWrapper, BorderLayout.NORTH);

        try {
            for (Customer c : CustomerController.getAllCustomer()) {
                cbCustomer.addItem(c.getKodeCustomer());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal load customer: " + e.getMessage());
        }
        
        cbCustomer.setSelectedIndex(0);

        cbCustomer.addActionListener(e -> {
            try {
                Customer c = CustomerController.getCustomerByKode(cbCustomer.getSelectedItem().toString());
                tfNamaCustomer.setText(c.getNamaCustomer());
                tfAlamat.setText(c.getAlamatLengkap());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal ambil customer");
            }
        });

        JPanel panelProduk = new JPanel(new BorderLayout());
        panelProduk.setBorder(BorderFactory.createTitledBorder("Input Produk"));

        JPanel row1 = new JPanel(new GridLayout(1, 6, 10, 5));
        row1.add(new JLabel("Kode Produk:"));
        row1.add(new JLabel("Qty:"));
        row1.add(new JLabel("Harga:"));
        row1.add(new JLabel("Disc1 (%):"));
        row1.add(new JLabel("Disc2 (%):"));
        row1.add(new JLabel("Disc3 (%):"));

        JPanel row2 = new JPanel(new GridLayout(1, 6, 10, 5));
        cbProduk = new JComboBox<>();
        cbProduk.addItem("Pilih Produk...");
        tfQty = new JTextField();
        tfHarga = new JTextField();
        tfDisc1 = new JTextField("0");
        tfDisc2 = new JTextField("0");
        tfDisc3 = new JTextField("0");
        
        row2.add(cbProduk);
        row2.add(tfQty);
        row2.add(tfHarga);
        row2.add(tfDisc1);
        row2.add(tfDisc2);
        row2.add(tfDisc3);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnTambah = new JButton("Tambah Produk");
        row3.add(btnTambah);

        panelProduk.add(row1, BorderLayout.NORTH);
        panelProduk.add(row2, BorderLayout.CENTER);
        panelProduk.add(row3, BorderLayout.SOUTH);

        add(panelProduk, BorderLayout.CENTER);

        try {
            for (Produk p : ProdukController.getAllProduk()) {
                cbProduk.addItem(p.getKodeProduk());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal load produk: " + e.getMessage());
        }
        
        cbProduk.setSelectedIndex(0);

        cbProduk.addActionListener(e -> {
            try {
                Produk p = ProdukController.getProdukByKode(cbProduk.getSelectedItem().toString());
                tfHarga.setText(String.valueOf(p.getHarga()));
            } catch (Exception ex) {
                tfHarga.setText("0");
            }
        });

        String[] columnNames = {"Kode", "Nama", "Qty", "Harga", "Disc1", "Disc2", "Disc3", "Net", "Jumlah"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(950, 200));

        JPanel panelBottom = new JPanel(new BorderLayout());
        lblTotal = new JLabel("Total: 0");
        JButton btnSimpan = new JButton("Simpan Transaksi");

        panelBottom.add(lblTotal, BorderLayout.WEST);
        panelBottom.add(btnSimpan, BorderLayout.EAST);

        JPanel panelTabelDanTotal = new JPanel(new BorderLayout());
        panelTabelDanTotal.add(scrollPane, BorderLayout.CENTER);
        panelTabelDanTotal.add(panelBottom, BorderLayout.SOUTH);

        add(panelTabelDanTotal, BorderLayout.SOUTH);

        btnTambah.addActionListener(e -> tambahProduk());
//        btnSimpan.addActionListener(e -> simpanTransaksi());
        
        btnSimpan.addActionListener(e -> {
            if (cbCustomer.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Silakan pilih customer terlebih dahulu!");
                return;
            }
            
            if (cbProduk.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Silakan pilih produk terlebih dahulu!");
                return;
            }
            
            simpanTransaksi();
        });

        try {
            tfNoInv.setText(InvoiceGenerator.generate());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal generate invoice");
        }

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

    private void tambahProduk() {
        try {
            String kode = cbProduk.getSelectedItem().toString();
            Produk p = ProdukController.getProdukByKode(kode);
            int qty = Integer.parseInt(tfQty.getText());
            if (qty > p.getStok()) {
                JOptionPane.showMessageDialog(this, "Stok tidak cukup!");
                return;
            }

            double harga = Double.parseDouble(tfHarga.getText());
            double d1 = Double.parseDouble(tfDisc1.getText());
            double d2 = Double.parseDouble(tfDisc2.getText());
            double d3 = Double.parseDouble(tfDisc3.getText());

            TransaksiDetail td = new TransaksiDetail(kode, p.getNamaProduk(), qty, harga, d1, d2, d3);
            detailList.add(td);
            Object[] row = {
                    td.getKodeProduk(), td.getNamaProduk(), td.getQty(), td.getHarga(),
                    td.getDisc1(), td.getDisc2(), td.getDisc3(), td.getHargaNet(), td.getJumlah()
            };
            tableModel.addRow(row);
            updateTotal();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Input tidak valid");
        }
    }

    private void updateTotal() {
        double total = 0;
        for (TransaksiDetail td : detailList) {
            total += td.getJumlah();
        }
        lblTotal.setText("Total: " + total);
    }

    private void simpanTransaksi() {
        try {
            String noInv = isEditMode ? originalNoInv : tfNoInv.getText();
            String kodeCust = cbCustomer.getSelectedItem().toString();
            Customer cust = CustomerController.getCustomerByKode(kodeCust);
            Date tgl = new SimpleDateFormat("yyyy-MM-dd").parse(tfTanggal.getText());
            double total = detailList.stream().mapToDouble(TransaksiDetail::getJumlah).sum();

            TransaksiHeader header = new TransaksiHeader(
                    noInv, kodeCust, cust.getNamaCustomer(), cust.getAlamatLengkap(),
                    tgl, total, detailList
            );

            if (isEditMode) {
                TransaksiController.deleteTransaksi(originalNoInv); // hapus lama
            }

            TransaksiController.simpanTransaksi(header);
            JOptionPane.showMessageDialog(this, isEditMode ? "Transaksi berhasil diupdate!" : "Transaksi berhasil disimpan!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal simpan transaksi: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void loadData(TransaksiHeader header) {
        this.isEditMode = true;
        this.originalNoInv = header.getNoInv();

        tfNoInv.setText(header.getNoInv());
        tfTanggal.setText(new SimpleDateFormat("yyyy-MM-dd").format(header.getTanggal()));
        cbCustomer.setSelectedItem(header.getKodeCustomer());
        tfNamaCustomer.setText(header.getNamaCustomer());
        tfAlamat.setText(header.getAlamat());

        detailList.clear();
        tableModel.setRowCount(0);

        for (TransaksiDetail d : header.getDetails()) {
            detailList.add(d);
            Object[] row = {
                d.getKodeProduk(), d.getNamaProduk(), d.getQty(), d.getHarga(),
                d.getDisc1(), d.getDisc2(), d.getDisc3(), d.getHargaNet(), d.getJumlah()
            };
            tableModel.addRow(row);
        }

        updateTotal();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
