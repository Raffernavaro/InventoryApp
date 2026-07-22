package inventoryapp.app.controller;

import inventoryapp.app.dao.BarangDAO;
import inventoryapp.app.dao.KategoriDAO;
import inventoryapp.app.helper.AlertHelper;
import inventoryapp.app.helper.TableHelper;
import inventoryapp.app.helper.TextFieldHelper;
import inventoryapp.app.model.Barang;
import inventoryapp.app.model.Kategori;
import inventoryapp.app.view.BarangView;
import inventoryapp.config.Database;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller untuk menangani logika CRUD Master Data Barang.
 */
public class BarangController {

    private final BarangView view;
    private BarangDAO barangDao;
    private KategoriDAO kategoriDao;
    private List<Kategori> listKategori = new ArrayList<>();

    public BarangController(BarangView view) {
        this.view = view;
        try {
            Connection conn = Database.getKoneksi();
            this.barangDao = new BarangDAO(conn);
            this.kategoriDao = new KategoriDAO(conn);
        } catch (SQLException ex) {
            AlertHelper.error(view, "Koneksi Database Gagal: " + ex.getMessage());
        }
        initController();
    }

    private void initController() {
        view.getBtnSimpan().addActionListener(e -> simpan());
        view.getBtnHapus().addActionListener(e -> hapus());
        view.getBtnRefresh().addActionListener(e -> resetForm());

        // Event klik tabel untuk memilih data
        view.getTblBarang().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getTblBarang().getSelectedRow();
                if (row != -1) {
                    Object idVal = TableHelper.getModel(view.getTblBarang()).getValueAt(row, 0);
                    Object kodeVal = TableHelper.getModel(view.getTblBarang()).getValueAt(row, 1);
                    Object namaVal = TableHelper.getModel(view.getTblBarang()).getValueAt(row, 2);
                    Object satuanVal = TableHelper.getModel(view.getTblBarang()).getValueAt(row, 3);
                    Object stokVal = TableHelper.getModel(view.getTblBarang()).getValueAt(row, 4);
                    Object kategoriVal = TableHelper.getModel(view.getTblBarang()).getValueAt(row, 5);

                    view.getTxtIdBarang().setText(idVal != null ? idVal.toString() : "");
                    view.getTxtKodeBarang().setText(kodeVal != null ? kodeVal.toString() : "");
                    view.getTxtNamaBarang().setText(namaVal != null ? namaVal.toString() : "");
                    view.getTxtSatuan().setText(satuanVal != null ? satuanVal.toString() : "");
                    view.getTxtStok().setText(stokVal != null ? stokVal.toString() : "");

                    if (kategoriVal != null) {
                        String katNama = kategoriVal.toString();
                        for (int i = 0; i < listKategori.size(); i++) {
                            if (listKategori.get(i).getNama().equalsIgnoreCase(katNama)) {
                                view.getCmbKategori().setSelectedIndex(i + 1);
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    public void loadKategori() {
        view.getCmbKategori().removeAllItems();
        view.getCmbKategori().addItem("Pilih Kategori");

        if (kategoriDao == null) return;
        listKategori = kategoriDao.getAll();

        for (Kategori k : listKategori) {
            view.getCmbKategori().addItem(k.getNama());
        }
    }

    public void loadData() {
        TableHelper.clearTable(view.getTblBarang());
        if (barangDao == null) return;

        List<Barang> list = barangDao.getAll();
        for (Barang b : list) {
            TableHelper.addRow(
                view.getTblBarang(),
                b.getId(),
                b.getKode(),
                b.getNama(),
                b.getSatuan(),
                b.getStok(),
                b.getNamaKategori() != null ? b.getNamaKategori() : ""
            );
        }
    }

    private void simpan() {
        String idStr = view.getTxtIdBarang().getText().trim();
        String kode = view.getTxtKodeBarang().getText().trim();
        String nama = view.getTxtNamaBarang().getText().trim();
        String satuan = view.getTxtSatuan().getText().trim();
        String stokStr = view.getTxtStok().getText().trim();
        int selectedKatIndex = view.getCmbKategori().getSelectedIndex();

        if (kode.isEmpty() || "Kode Barang".equalsIgnoreCase(kode) ||
            nama.isEmpty() || "Nama Barang".equalsIgnoreCase(nama) ||
            satuan.isEmpty() || "Satuan".equalsIgnoreCase(satuan) ||
            stokStr.isEmpty() || "Stok".equalsIgnoreCase(stokStr) ||
            selectedKatIndex <= 0) {
            AlertHelper.warning(view, "Semua data (Kode, Nama, Satuan, Stok, Kategori) wajib diisi!");
            return;
        }

        int stok;
        try {
            stok = Integer.parseInt(stokStr);
        } catch (NumberFormatException e) {
            AlertHelper.error(view, "Stok harus berupa angka!");
            return;
        }

        int idKategori = listKategori.get(selectedKatIndex - 1).getId();

        if (idStr.isEmpty() || "ID (Otomatis)".equalsIgnoreCase(idStr) || "ID (OTOMATIS)".equalsIgnoreCase(idStr)) {
            tambah(kode, nama, idKategori, satuan, stok);
        } else {
            ubah(idStr, kode, nama, idKategori, satuan, stok);
        }
    }

    private void tambah(String kode, String nama, int idKategori, String satuan, int stok) {
        Barang b = new Barang(0, kode, nama, idKategori, satuan, stok);
        barangDao.insert(b);
        AlertHelper.success(view, "Data barang berhasil ditambahkan!");
        resetForm();
    }

    private void ubah(String idStr, String kode, String nama, int idKategori, String satuan, int stok) {
        try {
            int id = Integer.parseInt(idStr);
            Barang b = new Barang(id, kode, nama, idKategori, satuan, stok);
            barangDao.update(b);
            AlertHelper.success(view, "Data barang berhasil diperbarui!");
            resetForm();
        } catch (NumberFormatException e) {
            AlertHelper.error(view, "ID Barang tidak valid!");
        }
    }

    private void hapus() {
        int selectedRow = view.getTblBarang().getSelectedRow();
        String idStr = view.getTxtIdBarang().getText().trim();

        if (selectedRow == -1 && (idStr.isEmpty() || idStr.contains("Otomatis") || idStr.contains("OTOMATIS"))) {
            AlertHelper.warning(view, "Silakan pilih data barang yang ingin dihapus pada tabel!");
            return;
        }

        boolean confirm = AlertHelper.confirmation(view, "Apakah Anda yakin ingin menghapus data barang ini?");
        if (confirm) {
            try {
                int id;
                if (selectedRow != -1) {
                    id = Integer.parseInt(TableHelper.getModel(view.getTblBarang()).getValueAt(selectedRow, 0).toString());
                } else {
                    id = Integer.parseInt(idStr);
                }

                barangDao.delete(id);
                AlertHelper.success(view, "Data barang berhasil dihapus!");
                resetForm();
            } catch (Exception ex) {
                AlertHelper.error(view, "Gagal menghapus data: " + ex.getMessage());
            }
        }
    }

    public void resetForm() {
        view.getTxtIdBarang().setText("");
        TextFieldHelper.setPlaceholder(view.getTxtIdBarang(), "ID (Otomatis)");

        view.getTxtKodeBarang().setText("");
        TextFieldHelper.setPlaceholder(view.getTxtKodeBarang(), "Kode Barang");

        view.getTxtNamaBarang().setText("");
        TextFieldHelper.setPlaceholder(view.getTxtNamaBarang(), "Nama Barang");

        view.getTxtSatuan().setText("");
        TextFieldHelper.setPlaceholder(view.getTxtSatuan(), "Satuan");

        view.getTxtStok().setText("");
        TextFieldHelper.setPlaceholder(view.getTxtStok(), "Stok");

        view.getCmbKategori().setSelectedIndex(0);

        view.getTblBarang().clearSelection();
        loadKategori();
        loadData();
    }
}
