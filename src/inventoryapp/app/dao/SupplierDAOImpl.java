package inventoryapp.app.dao;

import inventoryapp.app.model.Supplier;
import inventoryapp.config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public void insert(Supplier supplier) {
        String sql = "INSERT INTO tmsupplier (kode, nama, alamat, no_telp) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, supplier.getKode());
            pstmt.setString(2, supplier.getNama());
            pstmt.setString(3, supplier.getAlamat());
            pstmt.setString(4, supplier.getNoTelp());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Supplier supplier) {
        String sql = "UPDATE tmsupplier SET kode = ?, nama = ?, alamat = ?, no_telp = ? WHERE id = ?";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, supplier.getKode());
            pstmt.setString(2, supplier.getNama());
            pstmt.setString(3, supplier.getAlamat());
            pstmt.setString(4, supplier.getNoTelp());
            pstmt.setInt(5, supplier.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM tmsupplier WHERE id = ?";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Supplier> getAll() {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM tmsupplier";
        try (Connection conn = Database.getKoneksi();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Supplier(
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
    public Supplier getById(int id) {
        String sql = "SELECT * FROM tmsupplier WHERE id = ?";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Supplier(
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
