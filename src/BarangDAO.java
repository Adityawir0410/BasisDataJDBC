import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarangDAO {
    private final Connection conn;

    public BarangDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Barang> getAll() {
        List<Barang> list = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Barang ORDER BY tanggal DESC")) {
            while (rs.next()) {
                list.add(new Barang(
                    rs.getInt("id"),
                    rs.getString("nama_barang"),
                    rs.getDate("tanggal"),
                    rs.getInt("harga")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Barang barang) {
        String sql = "INSERT INTO Barang (tanggal, nama_barang, harga) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, barang.getTanggal());
            stmt.setString(2, barang.getNamaBarang());
            stmt.setInt(3, barang.getHarga());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Barang barang) {
        String sql = "UPDATE Barang SET tanggal=?, nama_barang=?, harga=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, barang.getTanggal());
            stmt.setString(2, barang.getNamaBarang());
            stmt.setInt(3, barang.getHarga());
            stmt.setInt(4, barang.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM Barang WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
