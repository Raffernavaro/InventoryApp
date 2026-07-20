package inventoryapp.app.dao;

import inventoryapp.app.model.BarangKeluar;
import inventoryapp.app.model.BarangKeluarDetail;
import inventoryapp.config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BarangKeluarDAOImpl implements BarangKeluarDAO {

    @Override
    public void insertTransaction(BarangKeluar bk) {
        String sqlHeader = "INSERT INTO trbarang_keluar (tgl, id_petugas, id_pelanggan) VALUES (?, ?, ?)";
        String sqlDetail = "INSERT INTO trbarang_keluar_detail (id_barang_keluar, id_barang, jumlah) VALUES (?, ?, ?)";
        String sqlUpdateStok = "UPDATE tmbarang SET stok = stok - ? WHERE id = ?";

        Connection conn = null;
        try {
            conn = Database.getKoneksi();
            conn.setAutoCommit(false);

            try (PreparedStatement pstmtHeader = conn.prepareStatement(sqlHeader, Statement.RETURN_GENERATED_KEYS)) {
                pstmtHeader.setDate(1, bk.getTgl());
                pstmtHeader.setInt(2, bk.getIdPetugas());
                pstmtHeader.setInt(3, bk.getIdPelanggan());
                pstmtHeader.executeUpdate();

                int generatedId = 0;
                try (ResultSet rs = pstmtHeader.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }

                if (bk.getDetails() != null) {
                    try (PreparedStatement pstmtDetail = conn.prepareStatement(sqlDetail);
                         PreparedStatement pstmtUpdateStok = conn.prepareStatement(sqlUpdateStok)) {
                        for (BarangKeluarDetail detail : bk.getDetails()) {
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

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
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
