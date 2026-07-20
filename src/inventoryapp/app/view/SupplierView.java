package inventoryapp.app.view;

import inventoryapp.app.controller.SupplierController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SupplierView extends JInternalFrame {

    private JTextField txtId;
    private JTextField txtKode;
    private JTextField txtNama;
    private JTextField txtAlamat;
    private JTextField txtNoTelp;
    
    private JTable table;
    private JButton btnTambah, btnUbah, btnHapus, btnRefresh;
    private DefaultTableModel tableModel;

    private SupplierController controller;

    public SupplierView() {
        super("Kelola Supplier", true, true, true, true);
        setSize(700, 450);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        initComponents();
        controller = new SupplierController(this);
        controller.loadData();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("ID (Otomatis):"));
        txtId = new JTextField();
        txtId.setEditable(false);
        formPanel.add(txtId);

        formPanel.add(new JLabel("Kode Supplier:"));
        txtKode = new JTextField();
        formPanel.add(txtKode);

        formPanel.add(new JLabel("Nama Supplier:"));
        txtNama = new JTextField();
        formPanel.add(txtNama);

        formPanel.add(new JLabel("Alamat:"));
        txtAlamat = new JTextField();
        formPanel.add(txtAlamat);

        formPanel.add(new JLabel("No Telp:"));
        txtNoTelp = new JTextField();
        formPanel.add(txtNoTelp);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnTambah = new JButton("Tambah");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnRefresh = new JButton("Refresh");

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnUbah);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnRefresh);

        tableModel = new DefaultTableModel(new String[]{"ID", "Kode", "Nama", "Alamat", "No Telp"}, 0) {
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
    public JTextField getTxtAlamat() { return txtAlamat; }
    public JTextField getTxtNoTelp() { return txtNoTelp; }
    
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnTambah() { return btnTambah; }
    public JButton getBtnUbah() { return btnUbah; }
    public JButton getBtnHapus() { return btnHapus; }
    public JButton getBtnRefresh() { return btnRefresh; }
}
