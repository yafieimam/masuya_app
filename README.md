# Aplikasi Manajemen Data Pelanggan & Produk Masuya - Java Swing

Aplikasi ini dibuat dengan Java Swing untuk mengelola data pelanggan dan transaksi produk. Aplikasi ini memiliki fitur tampilan tabel, form input/edit data, serta integrasi data dropdown (ComboBox) dari database.

## 🧩 Teknologi yang Digunakan

- Java 8+
- Java Swing (GUI)
- JDBC
- SQL Server (bisa disesuaikan)

## 📦 Fitur Utama

✅ Tabel pelanggan dengan 4 kolom utama (kode, nama, alamat, kota)  
✅ Form input/edit pelanggan dengan detail lengkap (provinsi, kecamatan, kelurahan, kode pos) yang diambil dari database  
✅ ComboBox produk dengan placeholder dan auto-load dari database  
✅ Mode tambah & edit data  
✅ Validasi input  
✅ Tabel transaksi dengan desain baris yang ringkas dan tidak menggembung  
✅ Button aksi: Simpan, Hapus

## 🚀 Cara Menjalankan

1. **Clone repositori**
    ```bash
    git clone https://github.com/yafieimam/masuya_app.git
    cd nama-repo

2. **Import ke IDE (NetBeans / IntelliJ / Eclipse)**
3. **Atur koneksi database**
    Pastikan file konfigurasi DBConnection.java sesuai dengan:
    String url = "jdbc:sqlserver://localhost:1433;databaseName=nama_db;encrypt=true;trustServerCertificate=true;";
    String user = "sa";
    String password = "";
4. **Jalankan Main.java**