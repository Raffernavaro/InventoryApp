package inventoryapp.app.dao;

import inventoryapp.app.model.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object untuk manajemen data Supplier (tmsupplier).
 */
public class SupplierDAO {

    private final Connection conn;

    public SupplierDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Supplier> getAll() {
        String sql = "SELECT * FROM tmsupplier ORDER BY id DESC";
        List<Supplier> list = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Supplier s = new Supplier();
                s.setId(rs.getInt("id"));
                s.setKode(rs.getString("kode"));
                s.setNama(rs.getString("nama"));
                s.setAlamat(rs.getString("alamat"));
                s.setNoTelp(rs.getString("no_telp"));
                list.add(s);
            }

        } catch (SQLException e) {
            System.err.println("Error getAll Supplier: " + e.getMessage());
        }

        return list;
    }

    public Supplier getDataById(int id) {
        String sql = "SELECT * FROM tmsupplier WHERE id = ?";
        Supplier s = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    s = new Supplier();
                    s.setId(rs.getInt("id"));
                    s.setKode(rs.getString("kode"));
                    s.setNama(rs.getString("nama"));
                    s.setAlamat(rs.getString("alamat"));
                    s.setNoTelp(rs.getString("no_telp"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getDataById Supplier: " + e.getMessage());
        }

        return s;
    }

    public void insert(Supplier s) {
        String sql = "INSERT INTO tmsupplier (kode, nama, alamat, no_telp) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, s.getKode());
            stmt.setString(2, s.getNama());
            stmt.setString(3, s.getAlamat());
            stmt.setString(4, s.getNoTelp());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error insert Supplier: " + e.getMessage());
        }
    }

    public void update(Supplier s) {
        String sql = "UPDATE tmsupplier SET kode = ?, nama = ?, alamat = ?, no_telp = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, s.getKode());
            stmt.setString(2, s.getNama());
            stmt.setString(3, s.getAlamat());
            stmt.setString(4, s.getNoTelp());
            stmt.setInt(5, s.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error update Supplier: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM tmsupplier WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error delete Supplier: " + e.getMessage());
        }
    }
}
