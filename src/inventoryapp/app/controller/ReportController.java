package inventoryapp.app.controller;

import inventoryapp.app.view.ReportView;
import inventoryapp.config.Database;

import javax.swing.*;
import java.sql.Connection;
// import net.sf.jasperreports.engine.*;
// import net.sf.jasperreports.view.JasperViewer;

public class ReportController {

    private ReportView view;

    public ReportController(ReportView view) {
        this.view = view;
        initController();
    }

    private void initController() {
        view.getBtnCetak().addActionListener(e -> cetakLaporan());
    }

    private void cetakLaporan() {
        String jenis = view.getCbJenisLaporan().getSelectedItem().toString();
        
        try {
            // Placeholder for JasperReports
            // Connection conn = Database.getKoneksi();
            // String reportPath = "";
            
            if (jenis.equals("Laporan Persediaan Barang")) {
                // reportPath = "src/inventoryapp/report/ReportPersediaan.jrxml";
                JOptionPane.showMessageDialog(view, "Memanggil JasperReport untuk: Persediaan Barang\n(Siapkan file .jrxml dan import JasperReports library)");
            } else if (jenis.equals("Laporan Barang Masuk")) {
                // reportPath = "src/inventoryapp/report/ReportMasuk.jrxml";
                JOptionPane.showMessageDialog(view, "Memanggil JasperReport untuk: Barang Masuk\n(Siapkan file .jrxml dan import JasperReports library)");
            } else if (jenis.equals("Laporan Barang Keluar")) {
                // reportPath = "src/inventoryapp/report/ReportKeluar.jrxml";
                JOptionPane.showMessageDialog(view, "Memanggil JasperReport untuk: Barang Keluar\n(Siapkan file .jrxml dan import JasperReports library)");
            }
            
            // JasperReport jr = JasperCompileManager.compileReport(reportPath);
            // JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            // JasperViewer.viewReport(jp, false);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error mencetak laporan: " + ex.getMessage());
        }
    }
}
