package inventoryapp.app.controller;

import inventoryapp.app.dao.SupplierDAO;
import inventoryapp.app.helper.AlertHelper;
import inventoryapp.app.helper.TableHelper;
import inventoryapp.app.helper.TextFieldHelper;
import inventoryapp.app.model.Supplier;
import inventoryapp.app.view.SupplierView;
import inventoryapp.config.Database;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller untuk menangani logika CRUD Master Data Supplier.
 */
public class SupplierController {

    private final SupplierView view;
    private SupplierDAO dao;

    public SupplierController(SupplierView view) {
        this.view = view;
        try {
            this.dao = new SupplierDAO(Database.getKoneksi());
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
        view.getTblSupplier().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getTblSupplier().getSelectedRow();
                if (row != -1) {
                    Object idVal = TableHelper.getModel(view.getTblSupplier()).getValueAt(row, 0);
                    Object kodeVal = TableHelper.getModel(view.getTblSupplier()).getValueAt(row, 1);
                    Object namaVal = TableHelper.getModel(view.getTblSupplier()).getValueAt(row, 2);
                    Object alamatVal = TableHelper.getModel(view.getTblSupplier()).getValueAt(row, 3);
                    Object noTelpVal = TableHelper.getModel(view.getTblSupplier()).getValueAt(row, 4);

                    view.getTxtIdSupplier().setText(idVal != null ? idVal.toString() : "");
                    view.getTxtKodeSupplier().setText(kodeVal != null ? kodeVal.toString() : "");
                    view.getTxtNamaSupplier().setText(namaVal != null ? namaVal.toString() : "");
                    view.getTxtAlamat().setText(alamatVal != null ? alamatVal.toString() : "");
                    view.getTxtNoTelp().setText(noTelpVal != null ? noTelpVal.toString() : "");
                }
            }
        });
    }

    public void loadData() {
        TableHelper.clearTable(view.getTblSupplier());
        if (dao == null) return;

        List<Supplier> list = dao.getAll();
        for (Supplier s : list) {
            TableHelper.addRow(
                view.getTblSupplier(),
                s.getId(),
                s.getKode(),
                s.getNama(),
                s.getAlamat(),
                s.getNoTelp()
            );
        }
    }

    private void simpan() {
        String idStr = view.getTxtIdSupplier().getText().trim();
        String kode = view.getTxtKodeSupplier().getText().trim();
        String nama = view.getTxtNamaSupplier().getText().trim();
        String alamat = view.getTxtAlamat().getText().trim();
        String noTelp = view.getTxtNoTelp().getText().trim();

        // Validasi input kosong / placeholder
        if (kode.isEmpty() || "Kode Supplier".equalsIgnoreCase(kode) ||
            nama.isEmpty() || "Nama Supplier".equalsIgnoreCase(nama) ||
            alamat.isEmpty() || "Alamat".equalsIgnoreCase(alamat) ||
            noTelp.isEmpty() || "No. Telp".equalsIgnoreCase(noTelp)) {
            AlertHelper.warning(view, "Semua data (Kode, Nama, Alamat, No Telp) wajib diisi!");
            return;
        }

        if (idStr.isEmpty() || idStr.contains("OTOMATIS") || idStr.contains("Otomatis")) {
            tambah(kode, nama, alamat, noTelp);
        } else {
            ubah(idStr, kode, nama, alamat, noTelp);
        }
    }

    private void tambah(String kode, String nama, String alamat, String noTelp) {
        Supplier s = new Supplier(0, kode, nama, alamat, noTelp);
        dao.insert(s);
        AlertHelper.success(view, "Data supplier berhasil ditambahkan!");
        resetForm();
    }

    private void ubah(String idStr, String kode, String nama, String alamat, String noTelp) {
        try {
            int id = Integer.parseInt(idStr);
            Supplier s = new Supplier(id, kode, nama, alamat, noTelp);
            dao.update(s);
            AlertHelper.success(view, "Data supplier berhasil diperbarui!");
            resetForm();
        } catch (NumberFormatException e) {
            AlertHelper.error(view, "ID Supplier tidak valid!");
        }
    }

    private void hapus() {
        int selectedRow = view.getTblSupplier().getSelectedRow();
        String idStr = view.getTxtIdSupplier().getText().trim();

        if (selectedRow == -1 && (idStr.isEmpty() || idStr.contains("OTOMATIS") || idStr.contains("Otomatis"))) {
            AlertHelper.warning(view, "Silakan pilih data supplier yang ingin dihapus pada tabel!");
            return;
        }

        boolean confirm = AlertHelper.confirmation(view, "Apakah Anda yakin ingin menghapus data supplier ini?");
        if (confirm) {
            try {
                int id;
                if (selectedRow != -1) {
                    id = Integer.parseInt(TableHelper.getModel(view.getTblSupplier()).getValueAt(selectedRow, 0).toString());
                } else {
                    id = Integer.parseInt(idStr);
                }

                dao.delete(id);
                AlertHelper.success(view, "Data supplier berhasil dihapus!");
                resetForm();
            } catch (Exception ex) {
                AlertHelper.error(view, "Gagal menghapus data: " + ex.getMessage());
            }
        }
    }

    public void resetForm() {
        view.getTxtIdSupplier().setText("");
        TextFieldHelper.setPlaceholder(view.getTxtIdSupplier(), "ID SUPPLIER (OTOMATIS)");

        view.getTxtKodeSupplier().setText("");
        TextFieldHelper.setPlaceholder(view.getTxtKodeSupplier(), "Kode Supplier");

        view.getTxtNamaSupplier().setText("");
        TextFieldHelper.setPlaceholder(view.getTxtNamaSupplier(), "Nama Supplier");

        view.getTxtAlamat().setText("");
        TextFieldHelper.setPlaceholder(view.getTxtAlamat(), "Alamat");

        view.getTxtNoTelp().setText("");
        TextFieldHelper.setPlaceholder(view.getTxtNoTelp(), "No. Telp");

        view.getTblSupplier().clearSelection();
        loadData();
    }
}
