/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoryapp.app.helper;

import inventoryapp.app.model.Pegawai;

/**
 *
 * @author Indruyy
 */
public class UserSession {

    private static Pegawai currentUser;

    public static void setSession(Pegawai user) {
        currentUser = user;
    }

    public static Pegawai getSession() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void clearSession() {
        currentUser = null;
    }
}
