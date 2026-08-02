package inventoryapp;

import inventoryapp.app.controller.LoginController;
import inventoryapp.app.view.LoginView;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;
import inventoryapp.app.view.SplashScreen;

/**
 *
 * @author THINKPAD X240
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.err.println("Gagal set FlatLaf: " + e.getMessage());
        }

        UIManager.put("defaultFont",
                new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));

        SplashScreen splash = new SplashScreen();
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);

        new Thread(() -> {
            try {
                String[] tasks = {"Menghubungkan ke database...", "Memuat modul...", "Memuat resource...", "Memulai aplikasi..."};
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(30); 
                    final int progress = i;
                    
                    int taskIndex = (i / 25) < tasks.length ? (i / 25) : tasks.length - 1;
                    final String status = tasks[taskIndex];
                    
                    SwingUtilities.invokeLater(() -> {
                        splash.getProgressBar().setValue(progress);
                        splash.getStatusLabel().setText(status + " (" + progress + "%)");
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            SwingUtilities.invokeLater(() -> {
                splash.dispose(); // Tutup splash

                LoginView loginView = new LoginView();
                new LoginController(loginView);
                loginView.setLocationRelativeTo(null);
                loginView.setVisible(true);
            });

        }).start();
    }

}
