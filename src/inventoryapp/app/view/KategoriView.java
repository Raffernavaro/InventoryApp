package inventoryapp.app.view;

import inventoryapp.app.controller.KategoriController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class KategoriView extends JInternalFrame {

    private JTextField txtId;
    private JTextField txtNama;
    private JTextField txtNoRak;
    private JTable table;
    private JButton btnTambah, btnUbah, btnHapus, btnRefresh;
    private DefaultTableModel tableModel;

    private KategoriController controller;

    public KategoriView() {
        super("Kelola Kategori", true, true, true, true);
        setSize(600, 400);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        initComponents();
        controller = new KategoriController(this);
        controller.loadData();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("ID (Otomatis):"));
        txtId = new JTextField();
        txtId.setEditable(false);
        formPanel.add(txtId);

        formPanel.add(new JLabel("Nama Kategori:"));
        txtNama = new JTextField();
        formPanel.add(txtNama);

        formPanel.add(new JLabel("No Rak:"));
        txtNoRak = new JTextField();
        formPanel.add(txtNoRak);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnTambah = new JButton("Tambah");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnRefresh = new JButton("Refresh");

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnUbah);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnRefresh);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nama Kategori", "No Rak"}, 0) {
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
    public JTextField getTxtNama() { return txtNama; }
    public JTextField getTxtNoRak() { return txtNoRak; }
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnTambah() { return btnTambah; }
    public JButton getBtnUbah() { return btnUbah; }
    public JButton getBtnHapus() { return btnHapus; }
    public JButton getBtnRefresh() { return btnRefresh; }
}
