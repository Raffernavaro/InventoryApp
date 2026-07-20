package inventoryapp.app.controller;

import inventoryapp.app.dao.PegawaiDAO;
import inventoryapp.app.dao.PegawaiDAOImpl;
import inventoryapp.app.model.Pegawai;
import inventoryapp.app.view.ProfilPegawaiView;

import javax.swing.*;

public class ProfilPegawaiController {

    private ProfilPegawaiView view;
    private PegawaiDAO dao;

    public ProfilPegawaiController(ProfilPegawaiView view) {
        this.view = view;
        this.dao = new PegawaiDAOImpl();
        initController();
    }

    private void initController() {
        view.getBtnSimpan().addActionListener(e -> simpan());
        view.getBtnBatal().addActionListener(e -> view.dispose());
    }

    public void loadData(int userId) {
        Pegawai p = dao.getById(userId);
        if (p != null) {
            view.getTxtId().setText(String.valueOf(p.getId()));
            view.getTxtNama().setText(p.getNama());
            view.getTxtUsername().setText(p.getUsername());
            view.getCbStatus().setSelectedItem(p.getStatus());
        }
    }

    private void simpan() {
        String idStr = view.getTxtId().getText();
        if (idStr.isEmpty()) return;

        String nama = view.getTxtNama().getText();
        String username = view.getTxtUsername().getText();
        String password = new String(view.getTxtPassword().getPassword());
        String status = view.getCbStatus().getSelectedItem().toString();

        if (nama.isEmpty() || username.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nama & Username tidak boleh kosong!");
            return;
        }

        int id = Integer.parseInt(idStr);
        if (password.isEmpty()) {
            Pegawai old = dao.getById(id);
            if(old != null) {
                password = old.getPassword();
            }
        }

        Pegawai p = new Pegawai(id, nama, username, password, status);
        dao.update(p);
        JOptionPane.showMessageDialog(view, "Profil berhasil diperbarui!");
        view.dispose();
    }
}
