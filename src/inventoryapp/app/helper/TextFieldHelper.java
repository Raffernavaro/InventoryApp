/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoryapp.app.helper;

import java.awt.Color;
import javax.swing.JTextField;

/**
 *
 * @author Indruyy
 */
public class TextFieldHelper {

    public static void setPlaceholder(JTextField txt, String hint) {

        txt.setText(hint);
        txt.setForeground(Color.GRAY);

        txt.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (txt.getText().equals(hint)) {
                    txt.setText("");
                    txt.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (txt.getText().trim().isEmpty()) {
                    txt.setText(hint);
                    txt.setForeground(Color.GRAY);
                }
            }
        });
    }

    public static void setReadOnly(JTextField txt) {
        txt.setEditable(false);
    }
}
