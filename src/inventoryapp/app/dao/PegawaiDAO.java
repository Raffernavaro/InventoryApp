package inventoryapp.app.dao;

import inventoryapp.app.model.Pegawai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Indruyy
 */
public class PegawaiDAO {

    private final Connection conn;

    public PegawaiDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Cocokkan username, password, dan pastikan status 'aktif'
     * @return Objek Pegawai jika berhasil, atau null jika gagal/nonaktif/salah
     */
    public Pegawai login(String username, String password) {
        String sql = "SELECT * FROM tmpetugas WHERE username = ? AND password = ? AND status = 'aktif'";
        Pegawai pegawai = null;

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pegawai = mapResultSetToPegawai(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error Login Pegawai: " + e.getMessage());
        }

        return pegawai;
    }

    /**
     * Ambil data pegawai berdasarkan ID
     */
    public Pegawai getById(int id) {
        String sql = "SELECT * FROM tmpetugas WHERE id = ?";
        Pegawai pegawai = null;

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pegawai = mapResultSetToPegawai(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getById Pegawai: " + e.getMessage());
        }

        return pegawai;
    }

    /**
     * Ambil semua data pegawai
     */
    public List<Pegawai> getAll() {
        String sql = "SELECT * FROM tmpetugas";
        List<Pegawai> listPegawai = new ArrayList<>();

        try (PreparedStatement stmt = this.conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listPegawai.add(mapResultSetToPegawai(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getAll Pegawai: " + e.getMessage());
        }

        return listPegawai;
    }

    /**
     * Helper privat untuk memetakan ResultSet ke Objek Pegawai
     */
    private Pegawai mapResultSetToPegawai(ResultSet rs) throws SQLException {
        Pegawai p = new Pegawai();
        p.setId(rs.getInt("id"));
        p.setNama(rs.getString("nama"));
        p.setUsername(rs.getString("username"));
        p.setPassword(rs.getString("password"));
        p.setStatus(rs.getString("status"));
        return p;
    }
}