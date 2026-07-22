package inventoryapp.app.controller;

import inventoryapp.app.dao.BarangDAO;
import inventoryapp.app.dao.BarangKeluarDAO;
import inventoryapp.app.dao.PelangganDAO;
import inventoryapp.app.helper.AlertHelper;
import inventoryapp.app.helper.TableHelper;
import inventoryapp.app.helper.TextFieldHelper;
import inventoryapp.app.helper.UserSession;
import inventoryapp.app.model.Barang;
import inventoryapp.app.model.BarangKeluar;
import inventoryapp.app.model.BarangKeluarDetail;
import inventoryapp.app.model.Pegawai;
import inventoryapp.app.model.Pelanggan;
import inventoryapp.app.view.BarangKeluarView;
import inventoryapp.config.Database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 * Controller untuk mengelola Transaksi Barang Keluar.
 */
public class BarangKeluarController {

    private final BarangKeluarView view;
    private BarangKeluarDAO barangKeluarDao;
    private BarangDAO barangDao;
    private PelangganDAO pelangganDao;

    private List<Barang> listBarang = new ArrayList<>();
    private List<Pelanggan> listPelanggan = new ArrayList<>();

    public BarangKeluarController(BarangKeluarView view) {
        this.view = view;
        try {
            Connection conn = Database.getKoneksi();
            this.barangKeluarDao = new BarangKeluarDAO(conn);
            this.barangDao = new BarangDAO(conn);
            this.pelangganDao = new PelangganDAO(conn);
        } catch (SQLException ex) {
            AlertHelper.error(view, "Koneksi Database Gagal: " + ex.getMessage());
        }
        initController();
    }

    private void initController() {
        view.getBtnTambahKeranjang().addActionListener(e -> tambahKeranjang());
        view.getBtnSimpanTransaksi().addActionListener(e -> simpanTransaksi());
    }

    public void loadBarang() {
        view.getjComboBox1().removeAllItems();
        view.getjComboBox1().addItem("Pilih Barang");
        if (barangDao == null) return;

        listBarang = barangDao.getAll();
        for (Barang b : listBarang) {
            view.getjComboBox1().addItem(b.getKode() + " - " + b.getNama() + " (Stok: " + b.getStok() + ")");
        }
    }

    public void loadPelanggan() {
        view.getjComboBox2().removeAllItems();
        view.getjComboBox2().addItem("Pilih Pelanggan");
        if (pelangganDao == null) return;

        listPelanggan = pelangganDao.getAll();
        for (Pelanggan p : listPelanggan) {
            view.getjComboBox2().addItem(p.getKode() + " - " + p.getNama());
        }
    }

    private void tambahKeranjang() {
        int selectedBarangIndex = view.getjComboBox1().getSelectedIndex();
        String jumlahStr = view.getTxtJumlahBarangKeluar().getText().trim();

        if (selectedBarangIndex <= 0) {
            AlertHelper.warning(view, "Silakan pilih barang terlebih dahulu!");
            return;
        }

        if (jumlahStr.isEmpty() || "Jumlah Barang Keluar".equalsIgnoreCase(jumlahStr)) {
            AlertHelper.warning(view, "Masukkan jumlah barang!");
            return;
        }

        int jumlah;
        try {
            jumlah = Integer.parseInt(jumlahStr);
            if (jumlah <= 0) {
                AlertHelper.warning(view, "Jumlah barang harus lebih dari 0!");
                return;
            }
        } catch (NumberFormatException e) {
            AlertHelper.error(view, "Jumlah barang harus berupa angka!");
            return;
        }

        Barang b = listBarang.get(selectedBarangIndex - 1);
        DefaultTableModel model = TableHelper.getModel(view.getTblBarangKeluar());

        // Hitung total jumlah barang ini di keranjang saat ini
        int currentJumlahInCart = 0;
        int existingRowIndex = -1;
        for (int i = 0; i < model.getRowCount(); i++) {
            int existingId = Integer.parseInt(model.getValueAt(i, 0).toString());
            if (existingId == b.getId()) {
                currentJumlahInCart = Integer.parseInt(model.getValueAt(i, 2).toString());
                existingRowIndex = i;
                break;
            }
        }

        if (currentJumlahInCart + jumlah > b.getStok()) {
            AlertHelper.warning(view, "Stok barang tidak mencukupi!\nStok tersedia: " + b.getStok() + "\nJumlah di keranjang: " + currentJumlahInCart);
            return;
        }

        if (existingRowIndex != -1) {
            model.setValueAt(currentJumlahInCart + jumlah, existingRowIndex, 2);
        } else {
            TableHelper.addRow(view.getTblBarangKeluar(), b.getId(), b.getNama(), jumlah);
        }

        // Reset input barang
        view.getjComboBox1().setSelectedIndex(0);
        view.getTxtJumlahBarangKeluar().setText("");
        TextFieldHelper.setPlaceholder(view.getTxtJumlahBarangKeluar(), "Jumlah Barang Keluar");
    }

    private void simpanTransaksi() {
        int selectedPelangganIndex = view.getjComboBox2().getSelectedIndex();
        if (selectedPelangganIndex <= 0) {
            AlertHelper.warning(view, "Silakan pilih pelanggan terlebih dahulu!");
            return;
        }

        DefaultTableModel model = TableHelper.getModel(view.getTblBarangKeluar());
        if (model.getRowCount() == 0) {
            AlertHelper.warning(view, "Keranjang barang keluar masih kosong! Tambahkan barang ke keranjang terlebih dahulu.");
            return;
        }

        Pelanggan p = listPelanggan.get(selectedPelangganIndex - 1);

        int idPetugas = 1;
        Pegawai user = UserSession.getSession();
        if (user != null) {
            idPetugas = user.getId();
        }

        BarangKeluar bk = new BarangKeluar(0, Date.valueOf(LocalDate.now()), idPetugas, p.getId());
        List<BarangKeluarDetail> details = new ArrayList<>();

        for (int i = 0; i < model.getRowCount(); i++) {
            int idBarang = Integer.parseInt(model.getValueAt(i, 0).toString());
            int jumlah = Integer.parseInt(model.getValueAt(i, 2).toString());
            details.add(new BarangKeluarDetail(0, 0, idBarang, jumlah));
        }

        boolean success = barangKeluarDao.simpanTransaksi(bk, details);
        if (success) {
            AlertHelper.success(view, "Transaksi Barang Keluar berhasil disimpan!");
            resetForm();
        } else {
            AlertHelper.error(view, "Gagal menyimpan transaksi Barang Keluar!");
        }
    }

    public void resetForm() {
        TableHelper.clearTable(view.getTblBarangKeluar());
        view.getjComboBox1().setSelectedIndex(0);
        view.getjComboBox2().setSelectedIndex(0);

        view.getTxtJumlahBarangKeluar().setText("");
        TextFieldHelper.setPlaceholder(view.getTxtJumlahBarangKeluar(), "Jumlah Barang Keluar");

        loadBarang();
        loadPelanggan();
    }
}
