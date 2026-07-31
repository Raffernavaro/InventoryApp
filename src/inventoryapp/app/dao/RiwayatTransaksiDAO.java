package inventoryapp.app.dao;

import inventoryapp.app.model.RiwayatTransaksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RiwayatTransaksiDAO {

    private final Connection conn;

    public RiwayatTransaksiDAO(Connection conn) {
        this.conn = conn;
    }

    public List<RiwayatTransaksi> getAll() {
        String sql = "SELECT\n" +
                     "  tanggal,\n" +
                     "  jenis_transaksi AS \"Jenis Transaksi\",\n" +
                     "  kode_barang AS \"Kode Barang\",\n" +
                     "  nama_barang AS \"Nama Barang \",\n" +
                     "  kategori AS \"Kategori\",\n" +
                     "  satuan AS \"Tipe Satuan\",\n" +
                     "  jumlah AS \"Jumlah Unit\",\n" +
                     "  relasi AS \"Supplier/Pembeli\"\n" +
                     "FROM\n" +
                     "  `v_transaksi_barang`\n" +
                     "ORDER BY tanggal DESC";
                     
        List<RiwayatTransaksi> daftarRiwayat = new ArrayList<>();

        try (PreparedStatement stmt = this.conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RiwayatTransaksi riwayat = new RiwayatTransaksi();
                // Menggunakan index kolom agar lebih aman dari alias yang memiliki spasi
                riwayat.setTanggal(rs.getTimestamp(1));
                riwayat.setJenisTransaksi(rs.getString(2));
                riwayat.setKodeBarang(rs.getString(3));
                riwayat.setNamaBarang(rs.getString(4));
                riwayat.setKategori(rs.getString(5));
                riwayat.setSatuan(rs.getString(6));
                riwayat.setJumlah(rs.getInt(7));
                riwayat.setRelasi(rs.getString(8));
                
                daftarRiwayat.add(riwayat);
            }

        } catch (SQLException e) {
            System.err.println("Error getAll: " + e.getMessage());
        }

        return daftarRiwayat;
    }
}
