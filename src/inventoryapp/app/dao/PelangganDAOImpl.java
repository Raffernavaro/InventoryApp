package inventoryapp.app.dao;

import inventoryapp.app.model.Pelanggan;
import inventoryapp.config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PelangganDAOImpl implements PelangganDAO {

    @Override
    public void insert(Pelanggan pelanggan) {
        String sql = "INSERT INTO tmpelanggan (kode, nama, alamat, no_telp) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pelanggan.getKode());
            pstmt.setString(2, pelanggan.getNama());
            pstmt.setString(3, pelanggan.getAlamat());
            pstmt.setString(4, pelanggan.getNoTelp());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Pelanggan pelanggan) {
        String sql = "UPDATE tmpelanggan SET kode = ?, nama = ?, alamat = ?, no_telp = ? WHERE id = ?";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pelanggan.getKode());
            pstmt.setString(2, pelanggan.getNama());
            pstmt.setString(3, pelanggan.getAlamat());
            pstmt.setString(4, pelanggan.getNoTelp());
            pstmt.setInt(5, pelanggan.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM tmpelanggan WHERE id = ?";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pelanggan> getAll() {
        List<Pelanggan> list = new ArrayList<>();
        String sql = "SELECT * FROM tmpelanggan";
        try (Connection conn = Database.getKoneksi();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Pelanggan(
                        rs.getInt("id"),
                        rs.getString("kode"),
                        rs.getString("nama"),
                        rs.getString("alamat"),
                        rs.getString("no_telp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Pelanggan getById(int id) {
        String sql = "SELECT * FROM tmpelanggan WHERE id = ?";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Pelanggan(
                            rs.getInt("id"),
                            rs.getString("kode"),
                            rs.getString("nama"),
                            rs.getString("alamat"),
                            rs.getString("no_telp")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
