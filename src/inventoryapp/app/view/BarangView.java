package inventoryapp.app.view;

import inventoryapp.app.controller.BarangController;
import inventoryapp.app.model.Kategori;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BarangView extends JInternalFrame {

    private JTextField txtId;
    private JTextField txtKode;
    private JTextField txtNama;
    private JComboBox<Kategori> cbKategori;
    private JTextField txtSatuan;
    private JTextField txtStok;
    
    private JTable table;
    private JButton btnTambah, btnUbah, btnHapus, btnRefresh;
    private DefaultTableModel tableModel;

    private BarangController controller;

    public BarangView() {
        super("Kelola Barang", true, true, true, true);
        setSize(800, 500);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        initComponents();
        controller = new BarangController(this);
        controller.loadKategori();
        controller.loadData();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("ID (Otomatis):"));
        txtId = new JTextField();
        txtId.setEditable(false);
        formPanel.add(txtId);

        formPanel.add(new JLabel("Kode Barang:"));
        txtKode = new JTextField();
        formPanel.add(txtKode);

        formPanel.add(new JLabel("Nama Barang:"));
        txtNama = new JTextField();
        formPanel.add(txtNama);

        formPanel.add(new JLabel("Kategori:"));
        cbKategori = new JComboBox<>();
        formPanel.add(cbKategori);

        formPanel.add(new JLabel("Satuan:"));
        txtSatuan = new JTextField();
        formPanel.add(txtSatuan);

        formPanel.add(new JLabel("Stok:"));
        txtStok = new JTextField();
        formPanel.add(txtStok);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnTambah = new JButton("Tambah");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnRefresh = new JButton("Refresh");

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnUbah);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnRefresh);

        tableModel = new DefaultTableModel(new String[]{"ID", "Kode", "Nama", "Kategori ID", "Satuan", "Stok"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtKode() { return txtKode; }
    public JTextField getTxtNama() { return txtNama; }
    public JComboBox<Kategori> getCbKategori() { return cbKategori; }
    public JTextField getTxtSatuan() { return txtSatuan; }
    public JTextField getTxtStok() { return txtStok; }
    
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnTambah() { return btnTambah; }
    public JButton getBtnUbah() { return btnUbah; }
    public JButton getBtnHapus() { return btnHapus; }
    public JButton getBtnRefresh() { return btnRefresh; }
}
