package inventoryapp.app.dao;

import inventoryapp.app.model.BarangMasuk;
import inventoryapp.app.model.BarangMasukDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Data Access Object untuk Transaksi Barang Masuk (trbarang_masuk & trbarang_masuk_detail).
 */
public class BarangMasukDAO {

    private final Connection conn;

    public BarangMasukDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean simpanTransaksi(BarangMasuk bm, List<BarangMasukDetail> details) {
        String sqlHeader = "INSERT INTO trbarang_masuk (tgl, id_petugas, id_supplier) VALUES (?, ?, ?)";
        String sqlDetail = "INSERT INTO trbarang_masuk_detail (id_barang_masuk, id_barang, jumlah) VALUES (?, ?, ?)";
        String sqlUpdateStok = "UPDATE tmbarang SET stok = stok + ? WHERE id = ?";

        boolean oldAutoCommit = true;
        try {
            oldAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            int idBarangMasuk = 0;
            try (PreparedStatement stmtHeader = conn.prepareStatement(sqlHeader, Statement.RETURN_GENERATED_KEYS)) {
                stmtHeader.setDate(1, bm.getTgl());
                stmtHeader.setInt(2, bm.getIdPetugas());
                stmtHeader.setInt(3, bm.getIdSupplier());
                stmtHeader.executeUpdate();

                try (ResultSet rs = stmtHeader.getGeneratedKeys()) {
                    if (rs.next()) {
                        idBarangMasuk = rs.getInt(1);
                    }
                }
            }

            if (idBarangMasuk == 0) {
                conn.rollback();
                return false;
            }

            try (PreparedStatement stmtDetail = conn.prepareStatement(sqlDetail);
                 PreparedStatement stmtUpdateStok = conn.prepareStatement(sqlUpdateStok)) {

                for (BarangMasukDetail d : details) {
                    stmtDetail.setInt(1, idBarangMasuk);
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
            System.err.println("Error simpanTransaksi BarangMasuk: " + e.getMessage());
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
