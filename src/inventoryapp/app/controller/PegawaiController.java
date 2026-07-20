package inventoryapp.app.controller;

import inventoryapp.app.dao.PegawaiDAO;
import inventoryapp.app.dao.PegawaiDAOImpl;
import inventoryapp.app.model.Pegawai;
import inventoryapp.app.view.PegawaiView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PegawaiController {

    private PegawaiView view;
    private PegawaiDAO dao;

    public PegawaiController(PegawaiView view) {
        this.view = view;
        this.dao = new PegawaiDAOImpl();
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
                    view.getTxtUsername().setText(view.getTableModel().getValueAt(row, 2).toString());
                    // Don't show password for security, or fetch from DB if necessary
                    view.getTxtPassword().setText("");
                    view.getCbStatus().setSelectedItem(view.getTableModel().getValueAt(row, 3).toString());
                }
            }
        });
    }

    public void loadData() {
        view.getTableModel().setRowCount(0);
        List<Pegawai> list = dao.getAll();
        for (Pegawai p : list) {
            view.getTableModel().addRow(new Object[]{
                    p.getId(),
                    p.getNama(),
                    p.getUsername(),
                    p.getStatus()
            });
        }
    }

    private void tambah() {
        String nama = view.getTxtNama().getText();
        String username = view.getTxtUsername().getText();
        String password = new String(view.getTxtPassword().getPassword());
        String status = view.getCbStatus().getSelectedItem().toString();

        if (nama.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Data tidak boleh kosong!");
            return;
        }

        Pegawai p = new Pegawai(0, nama, username, password, status);
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

        String nama = view.getTxtNama().getText();
        String username = view.getTxtUsername().getText();
        String password = new String(view.getTxtPassword().getPassword());
        String status = view.getCbStatus().getSelectedItem().toString();

        if (nama.isEmpty() || username.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nama & Username tidak boleh kosong!");
            return;
        }

        int id = Integer.parseInt(idStr);
        // If password is empty, keep old password. We fetch old one first.
        if (password.isEmpty()) {
            Pegawai old = dao.getById(id);
            if(old != null) {
                password = old.getPassword();
            }
        }

        Pegawai p = new Pegawai(id, nama, username, password, status);
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
        view.getTxtNama().setText("");
        view.getTxtUsername().setText("");
        view.getTxtPassword().setText("");
        view.getCbStatus().setSelectedIndex(0);
        view.getTable().clearSelection();
        loadData();
    }
}
