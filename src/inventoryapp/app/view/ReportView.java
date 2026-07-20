package inventoryapp.app.view;

import inventoryapp.app.controller.ReportController;
import javax.swing.*;
import java.awt.*;

public class ReportView extends JInternalFrame {

    private JComboBox<String> cbJenisLaporan;
    private JButton btnCetak;
    
    private ReportController controller;

    public ReportView() {
        super("Cetak Laporan", true, true, true, true);
        setSize(400, 200);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        initComponents();
        controller = new ReportController(this);
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Pilih Laporan:"));
        cbJenisLaporan = new JComboBox<>(new String[]{
            "Laporan Persediaan Barang",
            "Laporan Barang Masuk",
            "Laporan Barang Keluar"
        });
        formPanel.add(cbJenisLaporan);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnCetak = new JButton("Cetak (Jasper Ready)");
        buttonPanel.add(btnCetak);

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JComboBox<String> getCbJenisLaporan() { return cbJenisLaporan; }
    public JButton getBtnCetak() { return btnCetak; }
}
