package inventoryapp.app.dao;

import inventoryapp.app.model.BarangMasuk;
import inventoryapp.app.model.BarangMasukDetail;
import inventoryapp.config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BarangMasukDAOImpl implements BarangMasukDAO {

    @Override
    public void insertTransaction(BarangMasuk bm) {
        String sqlHeader = "INSERT INTO trbarang_masuk (tgl, id_petugas, id_supplier) VALUES (?, ?, ?)";
        String sqlDetail = "INSERT INTO trbarang_masuk_detail (id_barang_masuk, id_barang, jumlah) VALUES (?, ?, ?)";
        String sqlUpdateStok = "UPDATE tmbarang SET stok = stok + ? WHERE id = ?";

        Connection conn = null;
        try {
            conn = Database.getKoneksi();
            conn.setAutoCommit(false); // Start transaction

            // Insert Header
            try (PreparedStatement pstmtHeader = conn.prepareStatement(sqlHeader, Statement.RETURN_GENERATED_KEYS)) {
                pstmtHeader.setDate(1, bm.getTgl());
                pstmtHeader.setInt(2, bm.getIdPetugas());
                pstmtHeader.setInt(3, bm.getIdSupplier());
                pstmtHeader.executeUpdate();

                // Get generated ID
                int generatedId = 0;
                try (ResultSet rs = pstmtHeader.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }

                // Insert Details & Update Stok
                if (bm.getDetails() != null) {
                    try (PreparedStatement pstmtDetail = conn.prepareStatement(sqlDetail);
                         PreparedStatement pstmtUpdateStok = conn.prepareStatement(sqlUpdateStok)) {
                        for (BarangMasukDetail detail : bm.getDetails()) {
                            pstmtDetail.setInt(1, generatedId);
                            pstmtDetail.setInt(2, detail.getIdBarang());
                            pstmtDetail.setInt(3, detail.getJumlah());
                            pstmtDetail.addBatch();

                            pstmtUpdateStok.setInt(1, detail.getJumlah());
                            pstmtUpdateStok.setInt(2, detail.getIdBarang());
                            pstmtUpdateStok.addBatch();
                        }
                        pstmtDetail.executeBatch();
                        pstmtUpdateStok.executeBatch();
                    }
                }
            }

            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
