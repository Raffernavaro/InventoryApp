package inventoryapp.app.controller;

import inventoryapp.app.dao.*;
import inventoryapp.app.model.*;
import inventoryapp.app.view.BarangMasukView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BarangMasukController {

    private BarangMasukView view;
    private BarangMasukDAO bmDAO;
    private SupplierDAO supplierDAO;
    private BarangDAO barangDAO;

    public BarangMasukController(BarangMasukView view) {
        this.view = view;
        this.bmDAO = new BarangMasukDAOImpl();
        this.supplierDAO = new SupplierDAOImpl();
        this.barangDAO = new BarangDAOImpl();
        initController();
    }

    private void initController() {
        view.getBtnAddCart().addActionListener(e -> addToCart());
        view.getBtnSaveTransaction().addActionListener(e -> saveTransaction());
    }

    public void loadDropdowns() {
        view.getCbSupplier().removeAllItems();
        for (Supplier s : supplierDAO.getAll()) {
            view.getCbSupplier().addItem(s);
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

        Supplier s = (Supplier) view.getCbSupplier().getSelectedItem();
        if (s == null) {
            JOptionPane.showMessageDialog(view, "Pilih Supplier!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Simpan Transaksi?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            
            java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
            int idPetugas = 2; // Hardcoded administrator ID
            
            BarangMasuk bm = new BarangMasuk(0, sqlDate, idPetugas, s.getId());
            List<BarangMasukDetail> details = new ArrayList<>();
            
            for (int i = 0; i < view.getTableModel().getRowCount(); i++) {
                int idBarang = (int) view.getTableModel().getValueAt(i, 0);
                int jumlah = (int) view.getTableModel().getValueAt(i, 2);
                details.add(new BarangMasukDetail(0, 0, idBarang, jumlah));
            }
            
            bm.setDetails(details);
            bmDAO.insertTransaction(bm);
            
            JOptionPane.showMessageDialog(view, "Transaksi berhasil disimpan!");
            view.getTableModel().setRowCount(0);
        }
    }
}
