package inventoryapp.app.controller;

import inventoryapp.app.dao.*;
import inventoryapp.app.model.*;
import inventoryapp.app.view.BarangKeluarView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BarangKeluarController {

    private BarangKeluarView view;
    private BarangKeluarDAO bkDAO;
    private PelangganDAO pelangganDAO;
    private BarangDAO barangDAO;

    public BarangKeluarController(BarangKeluarView view) {
        this.view = view;
        this.bkDAO = new BarangKeluarDAOImpl();
        this.pelangganDAO = new PelangganDAOImpl();
        this.barangDAO = new BarangDAOImpl();
        initController();
    }

    private void initController() {
        view.getBtnAddCart().addActionListener(e -> addToCart());
        view.getBtnSaveTransaction().addActionListener(e -> saveTransaction());
    }

    public void loadDropdowns() {
        view.getCbPelanggan().removeAllItems();
        for (Pelanggan p : pelangganDAO.getAll()) {
            view.getCbPelanggan().addItem(p);
        }

        view.getCbBarang().removeAllItems();
        for (Barang b : barangDAO.getAll()) {
            view.getCbBarang().addItem(b);
        }
    }

    private void addToCart() {
        Barang b = (Barang) view.getCbBarang().getSelectedItem();
        String jumlahStr = view.getTxtJumlah().getText();

        if (b == null || jumlahStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih barang dan isi jumlah!");
            return;
        }

        try {
            int qty = Integer.parseInt(jumlahStr);
            if (qty <= 0) {
                JOptionPane.showMessageDialog(view, "Jumlah harus lebih dari 0!");
                return;
            }
            if (qty > b.getStok()) {
                JOptionPane.showMessageDialog(view, "Stok tidak mencukupi! Stok saat ini: " + b.getStok());
                return;
            }

            view.getTableModel().addRow(new Object[]{
                    b.getId(),
                    b.getNama(),
                    qty
            });
            view.getTxtJumlah().setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Jumlah harus berupa angka!");
        }
    }

    private void saveTransaction() {
        if (view.getTableModel().getRowCount() == 0) {
            JOptionPane.showMessageDialog(view, "Keranjang kosong!");
            return;
        }

        Pelanggan p = (Pelanggan) view.getCbPelanggan().getSelectedItem();
        if (p == null) {
            JOptionPane.showMessageDialog(view, "Pilih Pelanggan!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Simpan Transaksi?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            
            java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
            int idPetugas = 2; // Hardcoded administrator ID
            
            BarangKeluar bk = new BarangKeluar(0, sqlDate, idPetugas, p.getId());
            List<BarangKeluarDetail> details = new ArrayList<>();
            
            for (int i = 0; i < view.getTableModel().getRowCount(); i++) {
                int idBarang = (int) view.getTableModel().getValueAt(i, 0);
                int jumlah = (int) view.getTableModel().getValueAt(i, 2);
                details.add(new BarangKeluarDetail(0, 0, idBarang, jumlah));
            }
            
            bk.setDetails(details);
            bkDAO.insertTransaction(bk);
            
            JOptionPane.showMessageDialog(view, "Transaksi berhasil disimpan!");
            view.getTableModel().setRowCount(0);
        }
    }
}
