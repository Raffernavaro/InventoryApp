package inventoryapp.app.controller;

import inventoryapp.app.dao.KategoriDAO;
import inventoryapp.app.helper.AlertHelper;
import inventoryapp.app.helper.TableHelper;
import inventoryapp.app.helper.TextFieldHelper;
import inventoryapp.app.model.Kategori;
import inventoryapp.app.view.KategoriView;
import inventoryapp.config.Database;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;

/**
 *
 * @author Indruyy GANTENG SEDUNIA AHH
 */
public class KategoriController {

    private KategoriView view;
    private KategoriDAO dao;

    public KategoriController(KategoriView view) {
        System.out.println(">>> CONTROLLER BERHASIL DIBUAT! <<<");
        this.view = view;
        try {
            this.dao = new KategoriDAO(Database.getKoneksi());
        } catch (SQLException ex) {
            AlertHelper.error(view, ex.getMessage());
        }
        initController();
    }

    private void initController() {
        view.getBtnSimpan().addActionListener(e -> simpan());
        view.getBtnHapus().addActionListener(e -> hapus());
        view.getBtnRefresh().addActionListener(e -> resetForm());

        // Event klik item di tabel
        view.getTblKategori().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getTblKategori().getSelectedRow();
                // Konversi row index jika menggunakan TableRowSorter (Penting saat tabel difilter)
                if (row != -1) {
                    int modelRow = view.getTblKategori().convertRowIndexToModel(row);
                    view.getTxtIdKategori().setText(TableHelper.getModel(view.getTblKategori()).getValueAt(modelRow, 0).toString());
                    view.getTxtNamaKategori().setText(TableHelper.getModel(view.getTblKategori()).getValueAt(modelRow, 1).toString());
                    view.getTxtNoRak().setText(TableHelper.getModel(view.getTblKategori()).getValueAt(modelRow, 2).toString());
                }
            }
        });

        // Event pencarian
        view.getTxtCari().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String keyword = view.getTxtCari().getText();
                if (keyword.equals("Cari Kategori...")) {
                    keyword = "";
                }
                filterData(keyword);
            }
        });
    }

    private void filterData(String keyword) {
        DefaultTableModel model = (DefaultTableModel) view.getTblKategori().getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
        view.getTblKategori().setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter("(?i)" + keyword));
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

    private void simpan() {
        String idStr = view.getTxtIdKategori().getText().trim();

        if (idStr.isEmpty() || "ID Kategori (OTOMATIS)".equals(idStr)) {
            tambah();
        } else {
            ubah();
        }
    }

    private void tambah() {
        String nama = view.getTxtNamaKategori().getText().trim();
        String noRakStr = view.getTxtNoRak().getText().trim();

        // Cek apakah input masih berupa placeholder bawaan helper
        if (nama.isEmpty() || noRakStr.isEmpty() || nama.equals("Nama Kategori") || noRakStr.equals("No.Rak")) {
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

        if (nama.isEmpty() || noRakStr.isEmpty() || nama.equals("Nama Kategori") || noRakStr.equals("No.Rak")) {
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
        if (idStr.isEmpty() || "ID Kategori (OTOMATIS)".equals(idStr)) {
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
        // Mengembalikan placeholder seperti saat form pertama kali dibuka
        TextFieldHelper.setPlaceholder(view.getTxtIdKategori(), "ID Kategori (OTOMATIS)");
        TextFieldHelper.setReadOnly(view.getTxtIdKategori());
        TextFieldHelper.setPlaceholder(view.getTxtNamaKategori(), "Nama Kategori");
        TextFieldHelper.setPlaceholder(view.getTxtNoRak(), "No.Rak");
    }
}