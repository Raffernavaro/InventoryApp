package inventoryapp.app.dao;

import inventoryapp.app.model.Barang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object untuk manajemen data Barang (tmbarang).
 */
public class BarangDAO {

    private final Connection conn;

    public BarangDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Barang> getAll() {
        String sql = "SELECT b.id, b.kode, b.nama, b.id_kategori, b.satuan, b.stok, k.nama AS nama_kategori " +
                     "FROM tmbarang b LEFT JOIN tmkategori k ON k.id = b.id_kategori ORDER BY b.id DESC";
        List<Barang> list = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Barang b = new Barang();
                b.setId(rs.getInt("id"));
                b.setKode(rs.getString("kode"));
                b.setNama(rs.getString("nama"));
                b.setIdKategori(rs.getInt("id_kategori"));
                b.setSatuan(rs.getString("satuan"));
                b.setStok(rs.getInt("stok"));
                b.setNamaKategori(rs.getString("nama_kategori"));
                list.add(b);
            }

        } catch (SQLException e) {
            System.err.println("Error getAll Barang: " + e.getMessage());
        }

        return list;
    }

    public Barang getDataById(int id) {
        String sql = "SELECT b.id, b.kode, b.nama, b.id_kategori, b.satuan, b.stok, k.nama AS nama_kategori " +
                     "FROM tmbarang b LEFT JOIN tmkategori k ON k.id = b.id_kategori WHERE b.id = ?";
        Barang b = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    b = new Barang();
                    b.setId(rs.getInt("id"));
                    b.setKode(rs.getString("kode"));
                    b.setNama(rs.getString("nama"));
                    b.setIdKategori(rs.getInt("id_kategori"));
                    b.setSatuan(rs.getString("satuan"));
                    b.setStok(rs.getInt("stok"));
                    b.setNamaKategori(rs.getString("nama_kategori"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getDataById Barang: " + e.getMessage());
        }

        return b;
    }

    public void insert(Barang b) {
        String sql = "INSERT INTO tmbarang (kode, nama, id_kategori, satuan, stok) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, b.getKode());
            stmt.setString(2, b.getNama());
            stmt.setInt(3, b.getIdKategori());
            stmt.setString(4, b.getSatuan());
            stmt.setInt(5, b.getStok());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error insert Barang: " + e.getMessage());
        }
    }

    public void update(Barang b) {
        String sql = "UPDATE tmbarang SET kode = ?, nama = ?, id_kategori = ?, satuan = ?, stok = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, b.getKode());
            stmt.setString(2, b.getNama());
            stmt.setInt(3, b.getIdKategori());
            stmt.setString(4, b.getSatuan());
            stmt.setInt(5, b.getStok());
            stmt.setInt(6, b.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error update Barang: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM tmbarang WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error delete Barang: " + e.getMessage());
        }
    }
}
