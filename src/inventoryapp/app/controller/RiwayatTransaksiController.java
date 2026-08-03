package inventoryapp.app.controller;

import inventoryapp.app.dao.RiwayatTransaksiDAO;
import inventoryapp.app.helper.AlertHelper;
import inventoryapp.app.helper.TableHelper;
import inventoryapp.app.model.RiwayatTransaksi;
import inventoryapp.app.view.RiwayatView;
import inventoryapp.config.Database;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class RiwayatTransaksiController {
    
    private RiwayatView view;
    private RiwayatTransaksiDAO dao;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public RiwayatTransaksiController(RiwayatView view) {
        this.view = view;
        try {
            this.dao = new RiwayatTransaksiDAO(Database.getKoneksi());
        } catch (SQLException ex) {
            AlertHelper.error(view, ex.getMessage());
        }
        initController();
    }
    
    private void initController() {
        view.getTxtCari().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = view.getTxtCari().getText();
                if (keyword.equals("Cari Barang...")) {
                    keyword = "";
                }
                filterData(keyword);
            }
        });

        view.getCmbKategori().addActionListener(e -> {
            String selected = view.getCmbKategori().getSelectedItem().toString();
            if (selected.equals("Jenis Transaksi")) {
                filterData(""); // Tampilkan semua
            } else {
                if (selected == "Barang Masuk") {
                    filterData("Masuk");
                } else if (selected == "Barang Keluar") {
                    filterData("Keluar");
                }
            }
        });

        loadData();
    }
    
    private void filterData(String keyword) {
        DefaultTableModel model = (DefaultTableModel) view.getTblBarang().getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
        view.getTblBarang().setRowSorter(tr);

        tr.setRowFilter(RowFilter.regexFilter("(?i)" + keyword));
    }
    
    public void loadData() {
        TableHelper.clearTable(view.getTblBarang());
        
        List<RiwayatTransaksi> list = dao.getAll();
        
        for (RiwayatTransaksi r : list) {
            String tanggalStr = (r.getTanggal() != null) ? sdf.format(r.getTanggal()) : "";
            
            TableHelper.addRow(
                    view.getTblBarang(),
                    tanggalStr,
                    r.getJenisTransaksi(),
                    r.getKodeBarang(),
                    r.getNamaBarang(),
                    r.getKategori(),
                    r.getSatuan(),
                    r.getJumlah(),
                    r.getRelasi()
            );
        }
    }
}
