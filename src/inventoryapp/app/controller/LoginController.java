package inventoryapp.app.controller;

import inventoryapp.app.dao.PegawaiDAO;
import inventoryapp.app.dao.PegawaiDAOImpl;
import inventoryapp.app.model.Pegawai;
import inventoryapp.app.view.LoginView;
import inventoryapp.app.view.MainMenuView;

import javax.swing.*;

public class LoginController {

    private LoginView view;
    private PegawaiDAO dao;

    public LoginController(LoginView view) {
        this.view = view;
        this.dao = new PegawaiDAOImpl();
        initController();
    }

    private void initController() {
        view.getBtnLogin().addActionListener(e -> login());
    }

    private void login() {
        String username = view.getTxtUsername().getText();
        String password = new String(view.getTxtPassword().getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Username dan Password tidak boleh kosong!");
            return;
        }

        Pegawai p = dao.login(username, password);
        if (p != null) {
            JOptionPane.showMessageDialog(view, "Login berhasil!\nSelamat datang, " + p.getNama());
            view.dispose();
            new MainMenuView(p).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(view, "Username, Password salah, atau Akun Nonaktif!");
        }
    }
}
