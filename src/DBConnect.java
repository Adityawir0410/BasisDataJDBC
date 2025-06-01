import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;" +
                "databaseName=LatihanDB;" +
                "user=sa;" +
                "password=admin123;" +
                "encrypt=true;" +
                "trustServerCertificate=true;" +
                "loginTimeout=30;";

        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("✅ Koneksi berhasil ke database!");
        } catch (SQLException e) {
            System.out.println("❌ Gagal koneksi:");
            e.printStackTrace();
        }
    }
}
