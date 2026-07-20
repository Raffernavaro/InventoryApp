package inventoryapp.app.dao;

import inventoryapp.app.model.Barang;
import inventoryapp.config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BarangDAOImpl implements BarangDAO {

    @Override
    public void insert(Barang barang) {
        String sql = "INSERT INTO tmbarang (kode, nama, id_kategori, satuan, stok) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, barang.getKode());
            pstmt.setString(2, barang.getNama());
            pstmt.setInt(3, barang.getIdKategori());
            pstmt.setString(4, barang.getSatuan());
            pstmt.setInt(5, barang.getStok());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Barang barang) {
        String sql = "UPDATE tmbarang SET kode = ?, nama = ?, id_kategori = ?, satuan = ?, stok = ? WHERE id = ?";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, barang.getKode());
            pstmt.setString(2, barang.getNama());
            pstmt.setInt(3, barang.getIdKategori());
            pstmt.setString(4, barang.getSatuan());
            pstmt.setInt(5, barang.getStok());
            pstmt.setInt(6, barang.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM tmbarang WHERE id = ?";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Barang> getAll() {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM tmbarang";
        try (Connection conn = Database.getKoneksi();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Barang(
                        rs.getInt("id"),
                        rs.getString("kode"),
                        rs.getString("nama"),
                        rs.getInt("id_kategori"),
                        rs.getString("satuan"),
                        rs.getInt("stok")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Barang getById(int id) {
        String sql = "SELECT * FROM tmbarang WHERE id = ?";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Barang(
                            rs.getInt("id"),
                            rs.getString("kode"),
                            rs.getString("nama"),
                            rs.getInt("id_kategori"),
                            rs.getString("satuan"),
                            rs.getInt("stok")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
