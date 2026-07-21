package inventoryapp;

import inventoryapp.app.controller.LoginController;
import inventoryapp.app.view.LoginView;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

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
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Gagal set Nimbus Look and Feel: " + e.getMessage());
        }
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            new LoginController(loginView); // Pasang controller di sini
            loginView.setLocationRelativeTo(null);
            loginView.setVisible(true);
        });
    }

}
