package inventoryapp.app.controller;

import inventoryapp.app.dao.KategoriDAO;
import inventoryapp.app.helper.AlertHelper;
import inventoryapp.app.helper.TableHelper;
import inventoryapp.app.model.Kategori;
import inventoryapp.app.view.KategoriView;
import inventoryapp.config.Database;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Indruyy GANTENG SEDUNIA AHH
 */
public class KategoriController {

    private KategoriView view;
    private KategoriDAO dao;

    public KategoriController(KategoriView view) {
        System.out.println(">>> CONTROLLER BERHASIL DIBUAT! <<<"); // Tes ini dulu!
        this.view = view;
        try {
            this.dao = new KategoriDAO(Database.getKoneksi());
        } catch (SQLException ex) {
            AlertHelper.error(view, ex.getMessage());
        }
        initController();
    }

    private void initController() {
        // Tombol Simpan sekarang memanggil logika pengecekan simpan()
        view.getBtnSimpan().addActionListener(e -> simpan());
        view.getBtnHapus().addActionListener(e -> hapus());
        view.getBtnRefresh().addActionListener(e -> resetForm());

        // Event klik item di tabel
        view.getTblKategori().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getTblKategori().getSelectedRow();
                if (row != -1) {
                    view.getTxtIdKategori().setText(TableHelper.getModel(view.getTblKategori()).getValueAt(row, 0).toString());
                    view.getTxtNamaKategori().setText(TableHelper.getModel(view.getTblKategori()).getValueAt(row, 1).toString());
                    view.getTxtNoRak().setText(TableHelper.getModel(view.getTblKategori()).getValueAt(row, 2).toString());
                }
            }
        });
    }

    public void loadData() {
        TableHelper.clearTable(view.getTblKategori());

        List<Kategori> list = dao.getAll();

        for (Kategori k : list) {
            TableHelper.addRow(
                    view.getTblKategori(),
                    k.getId(),
                    k.getNama(),
                    k.getNoRak()
            );
        }
    }

    // LOGIKA UTAMA: Cek apakah ID ada atau kosong
    private void simpan() {
        String idStr = view.getTxtIdKategori().getText().trim();

        System.out.println(idStr);
        if ("ID Kategori (OTOMATIS)".equals(idStr)) {
            tambah();
        } else {
            System.out.println("Aku DIubah ");
            ubah();
        }
    }

    private void tambah() {
        String nama = view.getTxtNamaKategori().getText().trim();
        String noRakStr = view.getTxtNoRak().getText().trim();

        if (nama.isEmpty() || noRakStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Data tidak boleh kosong!");
            return;
        }
        String cleanNoRak = noRakStr.replaceAll("[^0-9]", "");
        try {
            int noRak = Integer.parseInt(cleanNoRak);
            Kategori k = new Kategori(0, nama, noRak);
            dao.insert(k);
            JOptionPane.showMessageDialog(view, "Data berhasil ditambahkan!");
            resetForm();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "No Rak harus berupa angka!");
        }
    }

    private void ubah() {
        String idStr = view.getTxtIdKategori().getText().trim();
        String nama = view.getTxtNamaKategori().getText().trim();
        String noRakStr = view.getTxtNoRak().getText().trim();

        if (nama.isEmpty() || noRakStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Data tidak boleh kosong!");
            return;
        }
        String cleanNoRak = noRakStr.replaceAll("[^0-9]", "");

        try {
            int id = Integer.parseInt(idStr);
            int noRak = Integer.parseInt(cleanNoRak);
            Kategori k = new Kategori(id, nama, noRak);
            dao.update(k);
            JOptionPane.showMessageDialog(view, "Data berhasil diubah!");
            resetForm();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "No Rak harus berupa angka!");
        }
    }

    private void hapus() {
        String idStr = view.getTxtIdKategori().getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih data di tabel terlebih dahulu!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Yakin hapus data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(idStr);
                dao.delete(id);
                JOptionPane.showMessageDialog(view, "Data berhasil dihapus!");
                resetForm();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "ID tidak valid!");
            }
        }
    }

    private void resetForm() {
        view.getTxtIdKategori().setText("");
        view.getTxtNamaKategori().setText("");
        view.getTxtNoRak().setText("");
        view.getTblKategori().clearSelection(); 
        loadData();
    }
}
