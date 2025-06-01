import java.sql.Date;

public class Barang {
    private int id;
    private String namaBarang;
    private Date tanggal;
    private int harga;

    public Barang(int id, String namaBarang, Date tanggal, int harga) {
        this.id = id;
        this.namaBarang = namaBarang;
        this.tanggal = tanggal;
        this.harga = harga;
    }

    public Barang(String namaBarang, Date tanggal, int harga) {
        this.namaBarang = namaBarang;
        this.tanggal = tanggal;
        this.harga = harga;
    }

    // Getter & Setter
    public int getId() { return id; }
    public String getNamaBarang() { return namaBarang; }
    public Date getTanggal() { return tanggal; }
    public int getHarga() { return harga; }

    public void setId(int id) { this.id = id; }
    public void setNamaBarang(String namaBarang) { this.namaBarang = namaBarang; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }
    public void setHarga(int harga) { this.harga = harga; }
}
