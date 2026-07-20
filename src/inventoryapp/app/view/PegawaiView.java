package inventoryapp.app.view;

import inventoryapp.app.controller.PegawaiController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PegawaiView extends JInternalFrame {

    private JTextField txtId;
    private JTextField txtNama;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cbStatus;
    
    private JTable table;
    private JButton btnTambah, btnUbah, btnHapus, btnRefresh;
    private DefaultTableModel tableModel;

    private PegawaiController controller;

    public PegawaiView() {
        super("Kelola Pegawai", true, true, true, true);
        setSize(700, 450);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        initComponents();
        controller = new PegawaiController(this);
        controller.loadData();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("ID (Otomatis):"));
        txtId = new JTextField();
        txtId.setEditable(false);
        formPanel.add(txtId);

        formPanel.add(new JLabel("Nama Pegawai:"));
        txtNama = new JTextField();
        formPanel.add(txtNama);

        formPanel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        formPanel.add(txtUsername);

        formPanel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        formPanel.add(txtPassword);

        formPanel.add(new JLabel("Status:"));
        cbStatus = new JComboBox<>(new String[]{"aktif", "nonaktif"});
        formPanel.add(cbStatus);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnTambah = new JButton("Tambah");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnRefresh = new JButton("Refresh");

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnUbah);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnRefresh);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Username", "Status"}, 0) {
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
    public JTextField getTxtUsername() { return txtUsername; }
    public JPasswordField getTxtPassword() { return txtPassword; }
    public JComboBox<String> getCbStatus() { return cbStatus; }
    
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnTambah() { return btnTambah; }
    public JButton getBtnUbah() { return btnUbah; }
    public JButton getBtnHapus() { return btnHapus; }
    public JButton getBtnRefresh() { return btnRefresh; }
}
