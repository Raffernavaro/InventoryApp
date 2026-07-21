/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoryapp.app.helper;

/**
 *
 * @author Indruyy
 */
import javax.swing.JOptionPane;
import java.awt.Component;

public class AlertHelper {

    public static void success(Component parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void error(Component parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void info(Component parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Information",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void warning(Component parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Warning",
                JOptionPane.WARNING_MESSAGE
        );
    }

    public static boolean confirmation(Component parent, String message) {
        int result = JOptionPane.showConfirmDialog(
                parent,
                message,
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        return result == JOptionPane.YES_OPTION;
    }
}
