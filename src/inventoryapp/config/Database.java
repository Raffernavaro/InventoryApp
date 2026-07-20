package inventoryapp.config;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Indruyy
 */
public class Database {

    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DB_NAME = "inventori";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";
    private static final String DB_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
    private static Connection koneksi;

    //method bikin koneksi.java
    public static Connection getKoneksi() throws SQLException {
        if (koneksi == null) {
            //bikin obj koneksi
            MysqlDataSource mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(DB_URL);
            mysqlDS.setUser(DB_USERNAME);
            mysqlDS.setPassword(DB_PASSWORD);

            koneksi = (Connection) mysqlDS.getConnection();
        }
        return koneksi;

    }
}
