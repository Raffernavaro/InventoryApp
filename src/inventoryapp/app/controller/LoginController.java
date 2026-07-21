package inventoryapp.app.controller;

import inventoryapp.app.dao.PegawaiDAO;
import inventoryapp.app.helper.AlertHelper;
import inventoryapp.app.helper.UserSession;
import inventoryapp.app.model.Pegawai;
import inventoryapp.app.view.LoginView;
import inventoryapp.app.view.MenuView;
import inventoryapp.config.Database;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * @author Indruyy
 */
public class LoginController {

    private LoginView view;
    private PegawaiDAO dao;

    public LoginController(LoginView view) {
        this.view = view;
        try {
            this.dao = new PegawaiDAO(Database.getKoneksi());
        } catch (SQLException ex) {
            AlertHelper.error(view, ex.getMessage());
        }
        initController();
    }

    private void initController() {
        view.getBtnLogin().addActionListener(e -> login());
    }

    private void login() {
        String username = view.getTxtUsername().getText().trim();
        String password = new String(view.getTxtPassword().getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Username dan Password wajib diisi!");
            return;
        }

        // Cek ke database via PegawaiDAO
        Pegawai pegawai = dao.login(username, password);

        if (pegawai != null) {
            // 1. Simpan sesi login
            UserSession.setSession(pegawai);

            JOptionPane.showMessageDialog(view, "Selamat datang, " + pegawai.getNama() + "!");

            // 2. Tutup LoginView
            view.dispose();

            // 3. Buka MenuView beserta MenuController-nya
            MenuView menuView = new MenuView();
            menuView.setLocationRelativeTo(null);
            menuView.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(view, "Username/Password salah atau akun nonaktif!");
        }
    }
}
