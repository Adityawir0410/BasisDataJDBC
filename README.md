# 📘 Sistem Pelaporan Barang – Tugas Basis Data

Proyek ini merupakan implementasi **sistem pelaporan barang sederhana** sebagai pemenuhan tugas mata kuliah **Basis Data**. Sistem dibangun menggunakan **Java + SQL Server**, dan dilengkapi fitur **export laporan ke PDF** menggunakan **iText**. Program ini dapat dijalankan melalui **Visual Studio Code**.

---

## 👤 Identitas Mahasiswa

- **Nama**: Anak Agung Ngurah Aditya Wirayudha  
- **NIM**: (isi sesuai NIM Anda)  
- **Mata Kuliah**: Basis Data  
- **Topik**: Pelaporan menggunakan iText PDF

---

## 🛠 Teknologi & Library

- Java SE (JDK 8+)
- Java Swing
- SQL Server
- JDBC
- iText 5.5.13.2
- Visual Studio Code

---

## 🗂 Struktur Folder

```yaml
JavaJDBC/
├── src/                    # Kode sumber aplikasi
│   ├── Barang.java
│   ├── BarangDAO.java
│   ├── DBConnect.java
│   ├── ExportPDF.java
│   └── MainFrame.java
├── lib/                    # Library eksternal (.jar)
│   ├── mssql-jdbc-12.x.jar
│   └── itextpdf-5.5.13.2.jar
├── bin/                    # Output hasil kompilasi .class
