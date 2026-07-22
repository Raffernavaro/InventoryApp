package inventoryapp.app.dao;

import inventoryapp.app.model.Pelanggan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object untuk manajemen data Pelanggan (tmpelanggan).
 */
public class PelangganDAO {

    private final Connection conn;

    public PelangganDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Pelanggan> getAll() {
        String sql = "SELECT * FROM tmpelanggan ORDER BY id DESC";
        List<Pelanggan> list = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pelanggan p = new Pelanggan();
                p.setId(rs.getInt("id"));
                p.setKode(rs.getString("kode"));
                p.setNama(rs.getString("nama"));
                p.setAlamat(rs.getString("alamat"));
                p.setNoTelp(rs.getString("no_telp"));
                list.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Error getAll Pelanggan: " + e.getMessage());
        }

        return list;
    }

    public Pelanggan getDataById(int id) {
        String sql = "SELECT * FROM tmpelanggan WHERE id = ?";
        Pelanggan p = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    p = new Pelanggan();
                    p.setId(rs.getInt("id"));
                    p.setKode(rs.getString("kode"));
                    p.setNama(rs.getString("nama"));
                    p.setAlamat(rs.getString("alamat"));
                    p.setNoTelp(rs.getString("no_telp"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getDataById Pelanggan: " + e.getMessage());
        }

        return p;
    }

    public void insert(Pelanggan p) {
        String sql = "INSERT INTO tmpelanggan (kode, nama, alamat, no_telp) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getKode());
            stmt.setString(2, p.getNama());
            stmt.setString(3, p.getAlamat());
            stmt.setString(4, p.getNoTelp());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error insert Pelanggan: " + e.getMessage());
        }
    }

    public void update(Pelanggan p) {
        String sql = "UPDATE tmpelanggan SET kode = ?, nama = ?, alamat = ?, no_telp = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getKode());
            stmt.setString(2, p.getNama());
            stmt.setString(3, p.getAlamat());
            stmt.setString(4, p.getNoTelp());
            stmt.setInt(5, p.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error update Pelanggan: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM tmpelanggan WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error delete Pelanggan: " + e.getMessage());
        }
    }
}
