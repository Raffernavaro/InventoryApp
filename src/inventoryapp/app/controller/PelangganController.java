package inventoryapp.app.controller;

import inventoryapp.app.dao.PelangganDAO;
import inventoryapp.app.helper.AlertHelper;
import inventoryapp.app.helper.TableHelper;
import inventoryapp.app.helper.TextFieldHelper;
import inventoryapp.app.model.Pelanggan;
import inventoryapp.app.view.PelangganView;
import inventoryapp.config.Database;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller untuk menangani logika CRUD Master Data Pelanggan.
 */
public class PelangganController {

    private final PelangganView view;
    private PelangganDAO dao;

    public PelangganController(PelangganView view) {
        this.view = view;
        try {
            this.dao = new PelangganDAO(Database.getKoneksi());
        } catch (SQLException ex) {
            AlertHelper.error(view, "Koneksi Database Gagal: " + ex.getMessage());
        }
        initController();
    }

    private void initController() {
        view.getBtnSimpan().addActionListener(e -> simpan());
        view.getBtnHapus().addActionListener(e -> hapus());
        view.getBtnRefresh().addActionListener(e -> resetForm());

        // Event klik pada tabel untuk memilih data
        view.getTblPelanggan().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getTblPelanggan().getSelectedRow();
                if (row != -1) {
                    Object idVal = TableHelper.getModel(view.getTblPelanggan()).getValueAt(row, 0);
                    Object kodeVal = TableHelper.getModel(view.getTblPelanggan()).getValueAt(row, 1);
                    Object namaVal = TableHelper.getModel(view.getTblPelanggan()).getValueAt(row, 2);
                    Object alamatVal = TableHelper.getModel(view.getTblPelanggan()).getValueAt(row, 3);
                    Object noTelpVal = TableHelper.getModel(view.getTblPelanggan()).getValueAt(row, 4);

                    view.getTxtIdPelanggan().setText(idVal != null ? idVal.toString() : "");
                    view.getTxtKodePelanggan().setText(kodeVal != null ? kodeVal.toString() : "");
                    view.getTxtNamaPelanggan().setText(namaVal != null ? namaVal.toString() : "");
                    view.getTxtAlamat().setText(alamatVal != null ? alamatVal.toString() : "");
                    view.getTxtNoTelp().setText(noTelpVal != null ? noTelpVal.toString() : "");
                }
            }
        });
    }

    public void loadData() {
        TableHelper.clearTable(view.getTblPelanggan());
        if (dao == null) return;

        List<Pelanggan> list = dao.getAll();
        for (Pelanggan p : list) {
            TableHelper.addRow(
                view.getTblPelanggan(),
                p.getId(),
                p.getKode(),
                p.getNama(),
                p.getAlamat(),
                p.getNoTelp()
            );
        }
    }

    private void simpan() {
        String idStr = view.getTxtIdPelanggan().getText().trim();
        String kode = view.getTxtKodePelanggan().getText().trim();
        String nama = view.getTxtNamaPelanggan().getText().trim();
        String alamat = view.getTxtAlamat().getText().trim();
        String noTelp = view.getTxtNoTelp().getText().trim();

        // Validasi input kosong / placeholder
        if (kode.isEmpty() || "Kode Pelanggan".equalsIgnoreCase(kode) ||
            nama.isEmpty() || "Nama Pelanggan".equalsIgnoreCase(nama) ||
            alamat.isEmpty() || "Alamat".equalsIgnoreCase(alamat) ||
            noTelp.isEmpty() || "No. Telp".equalsIgnoreCase(noTelp)) {
            AlertHelper.warning(view, "Semua data (Kode, Nama, Alamat, No Telp) wajib diisi!");
            return;
        }

        if (idStr.isEmpty() || "ID Pelanggan (OTOMATIS)".equalsIgnoreCase(idStr)) {
            tambah(kode, nama, alamat, noTelp);
        } else {
            ubah(idStr, kode, nama, alamat, noTelp);
        }
    }

    private void tambah(String kode, String nama, String alamat, String noTelp) {
        Pelanggan p = new Pelanggan(0, kode, nama, alamat, noTelp);
        dao.insert(p);
        AlertHelper.success(view, "Data pelanggan berhasil ditambahkan!");
        resetForm();
    }

    private void ubah(String idStr, String kode, String nama, String alamat, String noTelp) {
        try {
            int id = Integer.parseInt(idStr);
            Pelanggan p = new Pelanggan(id, kode, nama, alamat, noTelp);
            dao.update(p);
            AlertHelper.success(view, "Data pelanggan berhasil diperbarui!");
            resetForm();
        } catch (NumberFormatException e) {
            AlertHelper.error(view, "ID Pelanggan tidak valid!");
        }
    }

    private void hapus() {
        int selectedRow = view.getTblPelanggan().getSelectedRow();
        String idStr = view.getTxtIdPelanggan().getText().trim();

        if (selectedRow == -1 && (idStr.isEmpty() || "ID Pelanggan (OTOMATIS)".equalsIgnoreCase(idStr))) {
            AlertHelper.warning(view, "Silakan pilih data pelanggan yang ingin dihapus pada tabel!");
            return;
        }

        boolean confirm = AlertHelper.confirmation(view, "Apakah Anda yakin ingin menghapus data pelanggan ini?");
        if (confirm) {
            try {
                int id;
                if (selectedRow != -1) {
                    id = Integer.parseInt(TableHelper.getModel(view.getTblPelanggan()).getValueAt(selectedRow, 0).toString());
                } else {
                    id = Integer.parseInt(idStr);
                }

                dao.delete(id);
                AlertHelper.success(view, "Data pelanggan berhasil dihapus!");
                resetForm();
            } catch (Exception ex) {
                AlertHelper.error(view, "Gagal menghapus data: " + ex.getMessage());
            }
        }
    }

    public void resetForm() {
        view.getTxtIdPelanggan().setText("");
        TextFieldHelper.setPlaceholder(view.getTxtIdPelanggan(), "ID Pelanggan (OTOMATIS)");
        
        view.getTxtKodePelanggan().setText("");
        TextFieldHelper.setPlaceholder(view.getTxtKodePelanggan(), "Kode Pelanggan");

        view.getTxtNamaPelanggan().setText("");
        TextFieldHelper.setPlaceholder(view.getTxtNamaPelanggan(), "Nama Pelanggan");

        view.getTxtAlamat().setText("");
        TextFieldHelper.setPlaceholder(view.getTxtAlamat(), "Alamat");

        view.getTxtNoTelp().setText("");
        TextFieldHelper.setPlaceholder(view.getTxtNoTelp(), "No. Telp");

        view.getTblPelanggan().clearSelection();
        loadData();
    }
}
