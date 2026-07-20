package inventoryapp.app.controller;

import inventoryapp.app.dao.KategoriDAO;
import inventoryapp.app.dao.KategoriDAOImpl;
import inventoryapp.app.model.Kategori;
import inventoryapp.app.view.KategoriView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class KategoriController {

    private KategoriView view;
    private KategoriDAO dao;

    public KategoriController(KategoriView view) {
        this.view = view;
        this.dao = new KategoriDAOImpl();
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
                    view.getTxtNama().setText(view.getTableModel().getValueAt(row, 1).toString());
                    view.getTxtNoRak().setText(view.getTableModel().getValueAt(row, 2).toString());
                }
            }
        });
    }

    public void loadData() {
        view.getTableModel().setRowCount(0);
        List<Kategori> list = dao.getAll();
        for (Kategori k : list) {
            view.getTableModel().addRow(new Object[]{
                    k.getId(),
                    k.getNama(),
                    k.getNoRak()
            });
        }
    }

    private void tambah() {
        String nama = view.getTxtNama().getText();
        String noRakStr = view.getTxtNoRak().getText();

        if (nama.isEmpty() || noRakStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Data tidak boleh kosong!");
            return;
        }

        try {
            int noRak = Integer.parseInt(noRakStr);
            Kategori k = new Kategori(0, nama, noRak);
            dao.insert(k);
            JOptionPane.showMessageDialog(view, "Data berhasil ditambahkan!");
            resetForm();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "No Rak harus berupa angka!");
        }
    }

    private void ubah() {
        String idStr = view.getTxtId().getText();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih data terlebih dahulu!");
            return;
        }

        String nama = view.getTxtNama().getText();
        String noRakStr = view.getTxtNoRak().getText();

        if (nama.isEmpty() || noRakStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Data tidak boleh kosong!");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            int noRak = Integer.parseInt(noRakStr);
            Kategori k = new Kategori(id, nama, noRak);
            dao.update(k);
            JOptionPane.showMessageDialog(view, "Data berhasil diubah!");
            resetForm();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "No Rak harus berupa angka!");
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
        view.getTxtNama().setText("");
        view.getTxtNoRak().setText("");
        view.getTable().clearSelection();
        loadData();
    }
}
