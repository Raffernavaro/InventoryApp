package inventoryapp;

import inventoryapp.app.controller.LoginController;
import inventoryapp.app.view.LoginView;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;

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
    UIManager.put("defaultFont", new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
    
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            new LoginController(loginView);
            loginView.setLocationRelativeTo(null);
            loginView.setVisible(true);
        });
    }

}
