package inventoryapp.app.controller;

import inventoryapp.app.dao.PelangganDAO;
import inventoryapp.app.dao.PelangganDAOImpl;
import inventoryapp.app.model.Pelanggan;
import inventoryapp.app.view.PelangganView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PelangganController {

    private PelangganView view;
    private PelangganDAO dao;

    public PelangganController(PelangganView view) {
        this.view = view;
        this.dao = new PelangganDAOImpl();
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
                    view.getTxtAlamat().setText(view.getTableModel().getValueAt(row, 3).toString());
                    view.getTxtNoTelp().setText(view.getTableModel().getValueAt(row, 4).toString());
                }
            }
        });
    }

    public void loadData() {
        view.getTableModel().setRowCount(0);
        List<Pelanggan> list = dao.getAll();
        for (Pelanggan p : list) {
            view.getTableModel().addRow(new Object[]{
                    p.getId(),
                    p.getKode(),
                    p.getNama(),
                    p.getAlamat(),
                    p.getNoTelp()
            });
        }
    }

    private void tambah() {
        String kode = view.getTxtKode().getText();
        String nama = view.getTxtNama().getText();
        String alamat = view.getTxtAlamat().getText();
        String noTelp = view.getTxtNoTelp().getText();

        if (kode.isEmpty() || nama.isEmpty() || alamat.isEmpty() || noTelp.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Data tidak boleh kosong!");
            return;
        }

        Pelanggan p = new Pelanggan(0, kode, nama, alamat, noTelp);
        dao.insert(p);
        JOptionPane.showMessageDialog(view, "Data berhasil ditambahkan!");
        resetForm();
    }

    private void ubah() {
        String idStr = view.getTxtId().getText();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih data terlebih dahulu!");
            return;
        }

        String kode = view.getTxtKode().getText();
        String nama = view.getTxtNama().getText();
        String alamat = view.getTxtAlamat().getText();
        String noTelp = view.getTxtNoTelp().getText();

        if (kode.isEmpty() || nama.isEmpty() || alamat.isEmpty() || noTelp.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Data tidak boleh kosong!");
            return;
        }

        int id = Integer.parseInt(idStr);
        Pelanggan p = new Pelanggan(id, kode, nama, alamat, noTelp);
        dao.update(p);
        JOptionPane.showMessageDialog(view, "Data berhasil diubah!");
        resetForm();
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
        view.getTxtAlamat().setText("");
        view.getTxtNoTelp().setText("");
        view.getTable().clearSelection();
        loadData();
    }
}
