package inventoryapp.app.dao;

import inventoryapp.app.model.Kategori;
import inventoryapp.config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class KategoriDAOImpl implements KategoriDAO {

    @Override
    public void insert(Kategori kategori) {
        String sql = "INSERT INTO tmkategori (nama, no_rak) VALUES (?, ?)";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kategori.getNama());
            pstmt.setInt(2, kategori.getNoRak());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Kategori kategori) {
        String sql = "UPDATE tmkategori SET nama = ?, no_rak = ? WHERE id = ?";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kategori.getNama());
            pstmt.setInt(2, kategori.getNoRak());
            pstmt.setInt(3, kategori.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM tmkategori WHERE id = ?";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Kategori> getAll() {
        List<Kategori> list = new ArrayList<>();
        String sql = "SELECT * FROM tmkategori";
        try (Connection conn = Database.getKoneksi();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Kategori(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getInt("no_rak")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Kategori getById(int id) {
        String sql = "SELECT * FROM tmkategori WHERE id = ?";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Kategori(
                            rs.getInt("id"),
                            rs.getString("nama"),
                            rs.getInt("no_rak")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
