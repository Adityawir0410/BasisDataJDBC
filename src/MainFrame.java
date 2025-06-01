import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.List;

public class MainFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private BarangDAO barangDAO;

    public MainFrame(Connection conn) {
        super("Sistem Pelaporan Barang");
        this.barangDAO = new BarangDAO(conn);

        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // === Tabel ===
        model = new DefaultTableModel(new String[] { "ID", "Tanggal", "Nama Barang", "Harga" }, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // === Tombol ===
        JButton tambahBtn = new JButton("Tambah");
        JButton editBtn = new JButton("Edit");
        JButton hapusBtn = new JButton("Hapus");
        JButton exportBtn = new JButton("Export PDF");

        JPanel tombolPanel = new JPanel();
        tombolPanel.add(tambahBtn);
        tombolPanel.add(editBtn);
        tombolPanel.add(hapusBtn);
        tombolPanel.add(exportBtn);

        // === Layout ===
        add(scrollPane, BorderLayout.CENTER);
        add(tombolPanel, BorderLayout.SOUTH);

        // === Load Data ===
        refreshTable();

        // === Aksi Tombol ===
        tambahBtn.addActionListener(e -> openForm(null));
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Barang b = getSelectedBarang(row);
                openForm(b);
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris terlebih dahulu.");
            }
        });

        hapusBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus data?");
                if (confirm == JOptionPane.YES_OPTION) {
                    int id = (int) model.getValueAt(row, 0);
                    barangDAO.delete(id);
                    refreshTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris terlebih dahulu.");
            }
        });

        exportBtn.addActionListener(e -> {
            List<Barang> data = barangDAO.getAll();
            ExportPDF.export(data, "Laporan_Barang.pdf");
            JOptionPane.showMessageDialog(this, "Laporan berhasil dibuat: Laporan_Barang.pdf");
        });

    }

    private void refreshTable() {
        model.setRowCount(0);
        List<Barang> list = barangDAO.getAll();
        for (Barang b : list) {
            model.addRow(new Object[] {
                    b.getId(),
                    b.getTanggal(),
                    b.getNamaBarang(),
                    b.getHarga()
            });
        }
    }

    private Barang getSelectedBarang(int row) {
        int id = (int) model.getValueAt(row, 0);
        Date tanggal = Date.valueOf(model.getValueAt(row, 1).toString());
        String nama = model.getValueAt(row, 2).toString();
        int harga = Integer.parseInt(model.getValueAt(row, 3).toString());
        return new Barang(id, nama, tanggal, harga);
    }

    private void openForm(Barang barang) {
        JDialog dialog = new JDialog(this, barang == null ? "Tambah Barang" : "Edit Barang", true);
        dialog.setSize(300, 250);
        dialog.setLayout(new GridLayout(5, 2));

        JTextField namaField = new JTextField();
        JTextField tanggalField = new JTextField("2025-06-01");
        JTextField hargaField = new JTextField();

        if (barang != null) {
            namaField.setText(barang.getNamaBarang());
            tanggalField.setText(barang.getTanggal().toString());
            hargaField.setText(String.valueOf(barang.getHarga()));
        }

        dialog.add(new JLabel("Tanggal (yyyy-mm-dd):"));
        dialog.add(tanggalField);
        dialog.add(new JLabel("Nama Barang:"));
        dialog.add(namaField);
        dialog.add(new JLabel("Harga:"));
        dialog.add(hargaField);

        JButton simpanBtn = new JButton("Simpan");
        JButton batalBtn = new JButton("Batal");
        dialog.add(simpanBtn);
        dialog.add(batalBtn);

        simpanBtn.addActionListener(e -> {
            try {
                String nama = namaField.getText();
                Date tanggal = Date.valueOf(tanggalField.getText());
                int harga = Integer.parseInt(hargaField.getText());

                if (barang == null) {
                    barangDAO.insert(new Barang(nama, tanggal, harga));
                } else {
                    barang.setNamaBarang(nama);
                    barang.setTanggal(tanggal);
                    barang.setHarga(harga);
                    barangDAO.update(barang);
                }

                dialog.dispose();
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Input tidak valid!");
            }
        });

        batalBtn.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            String url = "jdbc:sqlserver://localhost:1433;" +
                    "databaseName=LatihanDB;" +
                    "user=sa;" +
                    "password=admin123;" +
                    "encrypt=true;" +
                    "trustServerCertificate=true;" +
                    "loginTimeout=30;";
            Connection conn = DriverManager.getConnection(url);
            SwingUtilities.invokeLater(() -> new MainFrame(conn).setVisible(true));
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal koneksi ke database");
        }
    }
}
