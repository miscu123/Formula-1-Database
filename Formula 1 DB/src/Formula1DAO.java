import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Formula1DAO {
        private static final String URL = "jdbc:mysql://127.0.0.1:3306/formula1";
        private static final String USER = "root";
        private static final String PASS = "1234";
        private final Connection conn;

        public Formula1DAO() throws SQLException {
            conn = DriverManager.getConnection(URL, USER, PASS);
        }
        public Connection getConnection() { return conn; }
        public void close() throws SQLException { conn.close(); }
    }