package inventoryapp.config;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.SQLException;

public class Database {

    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DB_NAME = "inventori";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";
    private static final String DB_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
    
    private static Connection koneksi;

    public static Connection getKoneksi() throws SQLException {
        if (koneksi == null || koneksi.isClosed()) {
            MysqlDataSource mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(DB_URL);
            mysqlDS.setUser(DB_USERNAME);
            mysqlDS.setPassword(DB_PASSWORD);

            koneksi = (Connection) mysqlDS.getConnection();
        }
        return koneksi;
    }
}
