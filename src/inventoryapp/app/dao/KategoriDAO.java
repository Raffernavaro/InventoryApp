package inventoryapp.app.dao;

import inventoryapp.app.model.Kategori;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Indruyy
 */
public class KategoriDAO {

    private final Connection conn;

    public KategoriDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Kategori> getAll() {
        String sql = "SELECT * FROM tmkategori";
        List<Kategori> daftarKategori = new ArrayList<>();

        // Try-with-resources otomatis menutup PreparedStatement dan ResultSet
        try (PreparedStatement stmt = this.conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Kategori kategori = new Kategori();
                kategori.setId(rs.getInt("id"));
                kategori.setNama(rs.getString("nama"));
                kategori.setNoRak(rs.getInt("no_rak"));
                daftarKategori.add(kategori);
            }

        } catch (SQLException e) {
            System.err.println("Error getAll: " + e.getMessage());
        }

        return daftarKategori;
    }

    public Kategori getDataById(int id) {
        String sql = "SELECT * FROM tmkategori WHERE id = ?";
        Kategori kategori = null;

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    kategori = new Kategori();
                    kategori.setId(rs.getInt("id"));
                    kategori.setNama(rs.getString("nama"));
                    kategori.setNoRak(rs.getInt("no_rak"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getDataById: " + e.getMessage());
        }

        return kategori;
    }

    public void insert(Kategori kategori) {
        String sql = "INSERT INTO tmkategori (nama, no_rak) VALUES (?, ?)";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, kategori.getNama());
            stmt.setInt(2, kategori.getNoRak());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error insert: " + e.getMessage());
        }
    }

    public void update(Kategori kategori) {
        String sql = "UPDATE tmkategori SET nama = ?, no_rak = ? WHERE id = ?";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, kategori.getNama());
            stmt.setInt(2, kategori.getNoRak());
            stmt.setInt(3, kategori.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error update: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM tmkategori WHERE id = ?";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error delete: " + e.getMessage());
        }
    }
}