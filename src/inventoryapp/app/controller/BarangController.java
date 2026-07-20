package inventoryapp.app.controller;

import inventoryapp.app.dao.BarangDAO;
import inventoryapp.app.dao.BarangDAOImpl;
import inventoryapp.app.dao.KategoriDAO;
import inventoryapp.app.dao.KategoriDAOImpl;
import inventoryapp.app.model.Barang;
import inventoryapp.app.model.Kategori;
import inventoryapp.app.view.BarangView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BarangController {

    private BarangView view;
    private BarangDAO dao;
    private KategoriDAO kategoriDAO;

    public BarangController(BarangView view) {
        this.view = view;
        this.dao = new BarangDAOImpl();
        this.kategoriDAO = new KategoriDAOImpl();
        initController();
    }

    private void initController() {
        view.getBtnTambah().addActionListener(e -> tambah());
        view.getBtnUbah().addActionListener(e -> ubah());
        view.getBtnHapus().addActionListener(e -> hapus());
        view.getBtnRefresh().addActionListener(e -> resetForm());

        view.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getTable().getSelectedRow();
                if (row != -1) {
                    view.getTxtId().setText(view.getTableModel().getValueAt(row, 0).toString());
                    view.getTxtKode().setText(view.getTableModel().getValueAt(row, 1).toString());
                    view.getTxtNama().setText(view.getTableModel().getValueAt(row, 2).toString());
                    
                    int idKategori = (int) view.getTableModel().getValueAt(row, 3);
                    setComboBoxKategori(idKategori);
                    
                    view.getTxtSatuan().setText(view.getTableModel().getValueAt(row, 4).toString());
                    view.getTxtStok().setText(view.getTableModel().getValueAt(row, 5).toString());
                }
            }
        });
    }

    public void loadKategori() {
        view.getCbKategori().removeAllItems();
        List<Kategori> list = kategoriDAO.getAll();
        for (Kategori k : list) {
            view.getCbKategori().addItem(k);
        }
    }

    public void loadData() {
        view.getTableModel().setRowCount(0);
        List<Barang> list = dao.getAll();
        for (Barang b : list) {
            view.getTableModel().addRow(new Object[]{
                    b.getId(),
                    b.getKode(),
                    b.getNama(),
                    b.getIdKategori(),
                    b.getSatuan(),
                    b.getStok()
            });
        }
    }

    private void setComboBoxKategori(int idKategori) {
        for (int i = 0; i < view.getCbKategori().getItemCount(); i++) {
            Kategori k = view.getCbKategori().getItemAt(i);
            if (k.getId() == idKategori) {
                view.getCbKategori().setSelectedIndex(i);
                break;
            }
        }
    }

    private void tambah() {
        String kode = view.getTxtKode().getText();
        String nama = view.getTxtNama().getText();
        Kategori kategori = (Kategori) view.getCbKategori().getSelectedItem();
        String satuan = view.getTxtSatuan().getText();
        String stokStr = view.getTxtStok().getText();

        if (kode.isEmpty() || nama.isEmpty() || kategori == null || satuan.isEmpty() || stokStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Data tidak boleh kosong!");
            return;
        }

        try {
            int stok = Integer.parseInt(stokStr);
            Barang b = new Barang(0, kode, nama, kategori.getId(), satuan, stok);
            dao.insert(b);
            JOptionPane.showMessageDialog(view, "Data berhasil ditambahkan!");
            resetForm();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Stok harus berupa angka!");
        }
    }

    private void ubah() {
        String idStr = view.getTxtId().getText();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih data terlebih dahulu!");
            return;
        }

        String kode = view.getTxtKode().getText();
        String nama = view.getTxtNama().getText();
        Kategori kategori = (Kategori) view.getCbKategori().getSelectedItem();
        String satuan = view.getTxtSatuan().getText();
        String stokStr = view.getTxtStok().getText();

        if (kode.isEmpty() || nama.isEmpty() || kategori == null || satuan.isEmpty() || stokStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Data tidak boleh kosong!");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            int stok = Integer.parseInt(stokStr);
            Barang b = new Barang(id, kode, nama, kategori.getId(), satuan, stok);
            dao.update(b);
            JOptionPane.showMessageDialog(view, "Data berhasil diubah!");
            resetForm();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Stok harus berupa angka!");
        }
    }

    private void hapus() {
        String idStr = view.getTxtId().getText();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih data terlebih dahulu!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Yakin hapus data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(idStr);
            dao.delete(id);
            JOptionPane.showMessageDialog(view, "Data berhasil dihapus!");
            resetForm();
        }
    }

    private void resetForm() {
        view.getTxtId().setText("");
        view.getTxtKode().setText("");
        view.getTxtNama().setText("");
        view.getTxtSatuan().setText("");
        view.getTxtStok().setText("");
        if (view.getCbKategori().getItemCount() > 0) {
            view.getCbKategori().setSelectedIndex(0);
        }
        view.getTable().clearSelection();
        loadData();
    }
}
