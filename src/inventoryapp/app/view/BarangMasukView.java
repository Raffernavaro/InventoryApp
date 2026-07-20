package inventoryapp.app.view;

import inventoryapp.app.controller.BarangMasukController;
import inventoryapp.app.model.Barang;
import inventoryapp.app.model.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class BarangMasukView extends JInternalFrame {

    private JComboBox<Supplier> cbSupplier;
    private JComboBox<Barang> cbBarang;
    private JTextField txtJumlah;
    
    private JTable tableCart;
    private DefaultTableModel tableModel;
    
    private JButton btnAddCart, btnSaveTransaction;
    private BarangMasukController controller;

    public BarangMasukView() {
        super("Transaksi Barang Masuk", true, true, true, true);
        setSize(800, 500);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        initComponents();
        controller = new BarangMasukController(this);
        controller.loadDropdowns();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Tanggal:"));
        JTextField txtTgl = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        txtTgl.setEditable(false);
        formPanel.add(txtTgl);

        formPanel.add(new JLabel("Pilih Supplier:"));
        cbSupplier = new JComboBox<>();
        formPanel.add(cbSupplier);

        formPanel.add(new JLabel("Pilih Barang:"));
        cbBarang = new JComboBox<>();
        formPanel.add(cbBarang);
        
        formPanel.add(new JLabel("Jumlah Masuk:"));
        txtJumlah = new JTextField();
        formPanel.add(txtJumlah);

        JPanel btnTopPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAddCart = new JButton("Tambah ke Keranjang");
        btnTopPanel.add(btnAddCart);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(formPanel, BorderLayout.CENTER);
        topContainer.add(btnTopPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new String[]{"ID Barang", "Nama Barang", "Jumlah"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tableCart = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableCart);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Keranjang Barang Masuk"));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSaveTransaction = new JButton("Simpan Transaksi");
        bottomPanel.add(btnSaveTransaction);

        setLayout(new BorderLayout());
        add(topContainer, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public JComboBox<Supplier> getCbSupplier() { return cbSupplier; }
    public JComboBox<Barang> getCbBarang() { return cbBarang; }
    public JTextField getTxtJumlah() { return txtJumlah; }
    public JTable getTableCart() { return tableCart; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnAddCart() { return btnAddCart; }
    public JButton getBtnSaveTransaction() { return btnSaveTransaction; }
}
