package inventoryapp.app.controller;

import inventoryapp.app.dao.BarangDAO;
import inventoryapp.app.dao.BarangMasukDAO;
import inventoryapp.app.dao.SupplierDAO;
import inventoryapp.app.helper.AlertHelper;
import inventoryapp.app.helper.TableHelper;
import inventoryapp.app.helper.TextFieldHelper;
import inventoryapp.app.helper.UserSession;
import inventoryapp.app.model.Barang;
import inventoryapp.app.model.BarangMasuk;
import inventoryapp.app.model.BarangMasukDetail;
import inventoryapp.app.model.Pegawai;
import inventoryapp.app.model.Supplier;
import inventoryapp.app.view.BarangMasukView;
import inventoryapp.config.Database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 * Controller untuk mengelola Transaksi Barang Masuk.
 */
public class BarangMasukController {

    private final BarangMasukView view;
    private BarangMasukDAO barangMasukDao;
    private BarangDAO barangDao;
    private SupplierDAO supplierDao;

    private List<Barang> listBarang = new ArrayList<>();
    private List<Supplier> listSupplier = new ArrayList<>();

    public BarangMasukController(BarangMasukView view) {
        this.view = view;
        try {
            Connection conn = Database.getKoneksi();
            this.barangMasukDao = new BarangMasukDAO(conn);
            this.barangDao = new BarangDAO(conn);
            this.supplierDao = new SupplierDAO(conn);
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
            view.getjComboBox1().addItem(b.getKode() + " - " + b.getNama());
        }
    }

    public void loadSupplier() {
        view.getjComboBox2().removeAllItems();
        view.getjComboBox2().addItem("Pilih Supplier");
        if (supplierDao == null) return;

        listSupplier = supplierDao.getAll();
        for (Supplier s : listSupplier) {
            view.getjComboBox2().addItem(s.getKode() + " - " + s.getNama());
        }
    }

    private void tambahKeranjang() {
        int selectedBarangIndex = view.getjComboBox1().getSelectedIndex();
        String jumlahStr = view.getTxtJumlahBarangMasuk().getText().trim();

        if (selectedBarangIndex <= 0) {
            AlertHelper.warning(view, "Silakan pilih barang terlebih dahulu!");
            return;
        }

        if (jumlahStr.isEmpty() || "Jumlah Barang Masuk".equalsIgnoreCase(jumlahStr)) {
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
        DefaultTableModel model = TableHelper.getModel(view.getTblBarangMasuk());

        // Cek jika barang sudah ada di keranjang/tabel
        boolean found = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            int existingId = Integer.parseInt(model.getValueAt(i, 0).toString());
            if (existingId == b.getId()) {
                int existingJumlah = Integer.parseInt(model.getValueAt(i, 2).toString());
                model.setValueAt(existingJumlah + jumlah, i, 2);
                found = true;
                break;
            }
        }

        if (!found) {
            TableHelper.addRow(view.getTblBarangMasuk(), b.getId(), b.getNama(), jumlah);
        }

        // Reset input barang
        view.getjComboBox1().setSelectedIndex(0);
        view.getTxtJumlahBarangMasuk().setText("");
        TextFieldHelper.setPlaceholder(view.getTxtJumlahBarangMasuk(), "Jumlah Barang Masuk");
    }

    private void simpanTransaksi() {
        int selectedSupplierIndex = view.getjComboBox2().getSelectedIndex();
        if (selectedSupplierIndex <= 0) {
            AlertHelper.warning(view, "Silakan pilih supplier terlebih dahulu!");
            return;
        }

        DefaultTableModel model = TableHelper.getModel(view.getTblBarangMasuk());
        if (model.getRowCount() == 0) {
            AlertHelper.warning(view, "Keranjang barang masuk masih kosong! Tambahkan barang ke keranjang terlebih dahulu.");
            return;
        }

        Supplier s = listSupplier.get(selectedSupplierIndex - 1);

        int idPetugas = 1;
        Pegawai user = UserSession.getSession();
        if (user != null) {
            idPetugas = user.getId();
        }

        BarangMasuk bm = new BarangMasuk(0, Date.valueOf(LocalDate.now()), idPetugas, s.getId());
        List<BarangMasukDetail> details = new ArrayList<>();

        for (int i = 0; i < model.getRowCount(); i++) {
            int idBarang = Integer.parseInt(model.getValueAt(i, 0).toString());
            int jumlah = Integer.parseInt(model.getValueAt(i, 2).toString());
            details.add(new BarangMasukDetail(0, 0, idBarang, jumlah));
        }

        boolean success = barangMasukDao.simpanTransaksi(bm, details);
        if (success) {
            AlertHelper.success(view, "Transaksi Barang Masuk berhasil disimpan!");
            resetForm();
        } else {
            AlertHelper.error(view, "Gagal menyimpan transaksi Barang Masuk!");
        }
    }

    public void resetForm() {
        TableHelper.clearTable(view.getTblBarangMasuk());
        view.getjComboBox1().setSelectedIndex(0);
        view.getjComboBox2().setSelectedIndex(0);

        view.getTxtJumlahBarangMasuk().setText("");
        TextFieldHelper.setPlaceholder(view.getTxtJumlahBarangMasuk(), "Jumlah Barang Masuk");

        loadBarang();
        loadSupplier();
    }
}
