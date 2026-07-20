package inventoryapp.app.view;

import javax.swing.*;
import java.awt.*;

import inventoryapp.app.model.Pegawai;

public class MainMenuView extends JFrame {

    private JDesktopPane desktopPane;
    private Pegawai loggedInUser;

    public MainMenuView(Pegawai loggedInUser) {
        this.loggedInUser = loggedInUser;
        setTitle("Inventory Management System");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initUI();
    }

    private void initUI() {
        desktopPane = new JDesktopPane();
        desktopPane.setBackground(Color.DARK_GRAY);
        setContentPane(desktopPane);

        JMenuBar menuBar = new JMenuBar();

        // Menu Master Data
        JMenu menuMaster = new JMenu("Master Data");
        JMenuItem itemKategori = new JMenuItem("Data Kategori");
        JMenuItem itemBarang = new JMenuItem("Data Barang");
        JMenuItem itemSupplier = new JMenuItem("Data Supplier");
        JMenuItem itemPelanggan = new JMenuItem("Data Pelanggan");
        JMenuItem itemPegawai = new JMenuItem("Data Pegawai");

        menuMaster.add(itemKategori);
        menuMaster.add(itemBarang);
        menuMaster.add(itemSupplier);
        menuMaster.add(itemPelanggan);
        
        // Restrict user management to 'admin' only
        if (loggedInUser.getUsername().equalsIgnoreCase("admin")) {
            menuMaster.add(itemPegawai);
        }

        // Menu Transaksi
        JMenu menuTransaksi = new JMenu("Transaksi");
        JMenuItem itemMasuk = new JMenuItem("Barang Masuk");
        JMenuItem itemKeluar = new JMenuItem("Barang Keluar");

        menuTransaksi.add(itemMasuk);
        menuTransaksi.add(itemKeluar);

        // Menu Laporan
        JMenu menuLaporan = new JMenu("Laporan");
        JMenuItem itemLap = new JMenuItem("Cetak Laporan");
        menuLaporan.add(itemLap);

        // Menu Pengaturan
        JMenu menuPengaturan = new JMenu("Pengaturan");
        JMenuItem itemProfil = new JMenuItem("Profil Pegawai");
        menuPengaturan.add(itemProfil);

        menuBar.add(menuMaster);
        menuBar.add(menuTransaksi);
        menuBar.add(menuLaporan);
        menuBar.add(menuPengaturan);
        setJMenuBar(menuBar);

        // Action Listeners
        itemKategori.addActionListener(e -> showInternalFrame(new KategoriView()));
        itemBarang.addActionListener(e -> showInternalFrame(new BarangView()));
        itemSupplier.addActionListener(e -> showInternalFrame(new SupplierView()));
        itemPelanggan.addActionListener(e -> showInternalFrame(new PelangganView()));
        itemPegawai.addActionListener(e -> showInternalFrame(new PegawaiView()));
        
        itemMasuk.addActionListener(e -> showInternalFrame(new BarangMasukView()));
        itemKeluar.addActionListener(e -> showInternalFrame(new BarangKeluarView()));
        
        itemLap.addActionListener(e -> showInternalFrame(new ReportView()));
        itemProfil.addActionListener(e -> showInternalFrame(new ProfilPegawaiView(loggedInUser.getId()))); 
    }

    private void showInternalFrame(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }
}
