package inventoryapp.app.dao;

import inventoryapp.app.model.BarangKeluar;
import inventoryapp.app.model.BarangKeluarDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Data Access Object untuk Transaksi Barang Keluar (trbarang_keluar & trbarang_keluar_detail).
 */
public class BarangKeluarDAO {

    private final Connection conn;

    public BarangKeluarDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean simpanTransaksi(BarangKeluar bk, List<BarangKeluarDetail> details) {
        String sqlHeader = "INSERT INTO trbarang_keluar (tgl, id_petugas, id_pelanggan) VALUES (?, ?, ?)";
        String sqlDetail = "INSERT INTO trbarang_keluar_detail (id_barang_keluar, id_barang, jumlah) VALUES (?, ?, ?)";
        String sqlUpdateStok = "UPDATE tmbarang SET stok = stok - ? WHERE id = ?";

        boolean oldAutoCommit = true;
        try {
            oldAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            int idBarangKeluar = 0;
            try (PreparedStatement stmtHeader = conn.prepareStatement(sqlHeader, Statement.RETURN_GENERATED_KEYS)) {
                stmtHeader.setDate(1, bk.getTgl());
                stmtHeader.setInt(2, bk.getIdPetugas());
                stmtHeader.setInt(3, bk.getIdPelanggan());
                stmtHeader.executeUpdate();

                try (ResultSet rs = stmtHeader.getGeneratedKeys()) {
                    if (rs.next()) {
                        idBarangKeluar = rs.getInt(1);
                    }
                }
            }

            if (idBarangKeluar == 0) {
                conn.rollback();
                return false;
            }

            try (PreparedStatement stmtDetail = conn.prepareStatement(sqlDetail);
                 PreparedStatement stmtUpdateStok = conn.prepareStatement(sqlUpdateStok)) {

                for (BarangKeluarDetail d : details) {
                    stmtDetail.setInt(1, idBarangKeluar);
                    stmtDetail.setInt(2, d.getIdBarang());
                    stmtDetail.setInt(3, d.getJumlah());
                    stmtDetail.executeUpdate();

                    stmtUpdateStok.setInt(1, d.getJumlah());
                    stmtUpdateStok.setInt(2, d.getIdBarang());
                    stmtUpdateStok.executeUpdate();
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error rollback: " + ex.getMessage());
            }
            System.err.println("Error simpanTransaksi BarangKeluar: " + e.getMessage());
            return false;
        } finally {
            try {
                conn.setAutoCommit(oldAutoCommit);
            } catch (SQLException e) {
                System.err.println("Error setAutoCommit: " + e.getMessage());
            }
        }
    }
}
