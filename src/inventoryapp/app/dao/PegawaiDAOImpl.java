package inventoryapp.app.dao;

import inventoryapp.app.model.Pegawai;
import inventoryapp.config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PegawaiDAOImpl implements PegawaiDAO {

    @Override
    public void insert(Pegawai pegawai) {
        String sql = "INSERT INTO tmpetugas (nama, username, password, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pegawai.getNama());
            pstmt.setString(2, pegawai.getUsername());
            pstmt.setString(3, pegawai.getPassword());
            pstmt.setString(4, pegawai.getStatus());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Pegawai pegawai) {
        String sql = "UPDATE tmpetugas SET nama = ?, username = ?, password = ?, status = ? WHERE id = ?";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pegawai.getNama());
            pstmt.setString(2, pegawai.getUsername());
            pstmt.setString(3, pegawai.getPassword());
            pstmt.setString(4, pegawai.getStatus());
            pstmt.setInt(5, pegawai.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM tmpetugas WHERE id = ?";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pegawai> getAll() {
        List<Pegawai> list = new ArrayList<>();
        String sql = "SELECT * FROM tmpetugas";
        try (Connection conn = Database.getKoneksi();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Pegawai(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Pegawai getById(int id) {
        String sql = "SELECT * FROM tmpetugas WHERE id = ?";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Pegawai(
                            rs.getInt("id"),
                            rs.getString("nama"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Pegawai login(String username, String password) {
        String sql = "SELECT * FROM tmpetugas WHERE username = ? AND password = ? AND status = 'aktif'";
        try (Connection conn = Database.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Pegawai(
                            rs.getInt("id"),
                            rs.getString("nama"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
