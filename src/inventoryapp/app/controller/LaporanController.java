package inventoryapp.app.controller;

import inventoryapp.app.helper.AlertHelper;
import inventoryapp.app.view.LaporanView;
import inventoryapp.config.Database;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Controller untuk menangani ekspor laporan menggunakan Jasper API dengan Progress Bar dialog.
 */
public class LaporanController {

    private final LaporanView view;

    public LaporanController(LaporanView view) {
        this.view = view;
    }

    private JDialog createLoadingDialog(String title, String message) {
        Frame owner = (Frame) SwingUtilities.getWindowAncestor(view);
        JDialog dialog = new JDialog(owner, title, true);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        panel.setBackground(Color.WHITE);

        JLabel lblMsg = new JLabel(message, JLabel.CENTER);
        lblMsg.setFont(new Font("Tahoma", Font.BOLD, 14));

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setPreferredSize(new Dimension(320, 24));

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(lblMsg);
        centerPanel.add(progressBar);

        panel.add(centerPanel, BorderLayout.CENTER);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(view);
        return dialog;
    }

    public void exportPdf() {
        int selectedIndex = view.getjComboBox1().getSelectedIndex();
        String selectedItem = (String) view.getjComboBox1().getSelectedItem();

        if (selectedIndex <= 0 || selectedItem == null || selectedItem.startsWith("Pilih") || selectedItem.startsWith("--")) {
            AlertHelper.warning(view, "Silakan pilih jenis laporan terlebih dahulu!");
            return;
        }

        String reportFileNameTemp = "";
        String defaultPdfNameTemp = "";

        if (selectedItem.contains("Persediaan")) {
            reportFileNameTemp = "ReportPersediaan.jrxml";
            defaultPdfNameTemp = "Laporan_Persediaan.pdf";
        } else if (selectedItem.contains("Masuk")) {
            reportFileNameTemp = "ReportBarangMasuk.jrxml";
            defaultPdfNameTemp = "Laporan_Barang_Masuk.pdf";
        } else if (selectedItem.contains("Keluar")) {
            reportFileNameTemp = "ReportBarangKeluar.jrxml";
            defaultPdfNameTemp = "Laporan_Barang_Keluar.pdf";
        } else {
            AlertHelper.warning(view, "Jenis laporan tidak dikenali!");
            return;
        }

        final String reportFileName = reportFileNameTemp;
        final String defaultPdfName = defaultPdfNameTemp;

        // JFileChooser for PDF save location
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan Export PDF Laporan");
        fileChooser.setSelectedFile(new File(defaultPdfName));
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Document (*.pdf)", "pdf"));

        int userSelection = fileChooser.showSaveDialog(view);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return; // User cancelled file chooser
        }

        File fileToSave = fileChooser.getSelectedFile();
        String selectedPath = fileToSave.getAbsolutePath();
        if (!selectedPath.toLowerCase().endsWith(".pdf")) {
            selectedPath += ".pdf";
        }
        final String finalPath = selectedPath;

        JDialog loadingDialog = createLoadingDialog("Proses Export Laporan", "Sedang Mengekspor Laporan... Harap Tunggu");

        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            private JasperPrint jasperPrint;
            private String errorMessage;

            @Override
            protected Boolean doInBackground() throws Exception {
                InputStream reportStream = getClass().getResourceAsStream("/inventoryapp/report/" + reportFileName);
                JasperReport jasperReport = null;

                if (reportStream != null) {
                    jasperReport = JasperCompileManager.compileReport(reportStream);
                } else {
                    File jrxmlFile = new File("src/inventoryapp/report/" + reportFileName);
                    if (jrxmlFile.exists()) {
                        jasperReport = JasperCompileManager.compileReport(jrxmlFile.getAbsolutePath());
                    } else {
                        errorMessage = "File template laporan (" + reportFileName + ") tidak ditemukan!";
                        return false;
                    }
                }

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("REPORT_GENERATED_AT", new java.util.Date());
                parameters.put("REPORT_SCOPE", "Semua Data");

                Connection conn = Database.getKoneksi();
                jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
                JasperExportManager.exportReportToPdfFile(jasperPrint, finalPath);

                return true;
            }

            @Override
            protected void done() {
                loadingDialog.dispose();
                try {
                    boolean success = get();
                    if (success) {
                        AlertHelper.success(view, "Laporan PDF berhasil diexport ke:\n" + finalPath);
                        JasperViewer.viewReport(jasperPrint, false);
                    } else {
                        AlertHelper.error(view, errorMessage != null ? errorMessage : "Gagal meng-export laporan.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    AlertHelper.error(view, "Gagal meng-export laporan: " + ex.getMessage());
                }
            }
        };

        worker.execute();
        loadingDialog.setVisible(true);
    }
}
