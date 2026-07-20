package inventoryapp.app.view;

import inventoryapp.app.controller.ProfilPegawaiController;
import javax.swing.*;
import java.awt.*;

public class ProfilPegawaiView extends JInternalFrame {

    private JTextField txtId;
    private JTextField txtNama;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cbStatus;
    
    private JButton btnSimpan, btnBatal;
    
    private ProfilPegawaiController controller;
    private int loggedInUserId;

    public ProfilPegawaiView(int loggedInUserId) {
        super("Ubah Profil Pegawai", true, true, true, true);
        this.loggedInUserId = loggedInUserId;
        setSize(400, 300);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        initComponents();
        controller = new ProfilPegawaiController(this);
        controller.loadData(loggedInUserId);
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        formPanel.add(txtId);

        formPanel.add(new JLabel("Nama:"));
        txtNama = new JTextField();
        formPanel.add(txtNama);

        formPanel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        formPanel.add(txtUsername);

        formPanel.add(new JLabel("Password Baru:"));
        txtPassword = new JPasswordField();
        formPanel.add(txtPassword);

        formPanel.add(new JLabel("Status:"));
        cbStatus = new JComboBox<>(new String[]{"aktif", "nonaktif"});
        formPanel.add(cbStatus);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSimpan = new JButton("Simpan");
        btnBatal = new JButton("Batal");

        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnBatal);

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtNama() { return txtNama; }
    public JTextField getTxtUsername() { return txtUsername; }
    public JPasswordField getTxtPassword() { return txtPassword; }
    public JComboBox<String> getCbStatus() { return cbStatus; }
    
    public JButton getBtnSimpan() { return btnSimpan; }
    public JButton getBtnBatal() { return btnBatal; }
}
