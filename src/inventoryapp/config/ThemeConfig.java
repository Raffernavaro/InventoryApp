package inventoryapp.config;

import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import java.awt.Color;
import java.awt.Font;

public class ThemeConfig {

    public static void applyTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

            // Cyberpunk Dark Theme Colors
            Color bgColor = new Color(13, 13, 26); // Sangat gelap, biru kehitaman
            Color fgColor = new Color(0, 243, 255); // Cyan / Neon Blue
            Color panelColor = new Color(20, 20, 35);
            Color buttonBg = new Color(30, 30, 50);
            Color buttonHover = new Color(0, 243, 255);
            Color inputBg = new Color(25, 25, 45);
            Color borderCol = new Color(0, 243, 255);

            Font mainFont = new Font("Consolas", Font.PLAIN, 14);
            Font boldFont = new Font("Consolas", Font.BOLD, 14);

            // Set global font
            UIManager.put("Button.font", boldFont);
            UIManager.put("Label.font", mainFont);
            UIManager.put("TextField.font", mainFont);
            UIManager.put("ComboBox.font", mainFont);
            UIManager.put("Table.font", mainFont);
            UIManager.put("TableHeader.font", boldFont);
            UIManager.put("Menu.font", mainFont);
            UIManager.put("MenuItem.font", mainFont);

            // Set global colors
            UIManager.put("Panel.background", new ColorUIResource(bgColor));
            UIManager.put("Label.foreground", new ColorUIResource(fgColor));
            
            // Buttons
            UIManager.put("Button.background", new ColorUIResource(buttonBg));
            UIManager.put("Button.foreground", new ColorUIResource(fgColor));
            UIManager.put("Button.border", javax.swing.BorderFactory.createLineBorder(borderCol, 1));
            UIManager.put("Button.focus", new ColorUIResource(buttonHover));
            
            // TextFields
            UIManager.put("TextField.background", new ColorUIResource(inputBg));
            UIManager.put("TextField.foreground", new ColorUIResource(fgColor));
            UIManager.put("TextField.caretForeground", new ColorUIResource(fgColor));
            UIManager.put("TextField.border", javax.swing.BorderFactory.createCompoundBorder(
                    javax.swing.BorderFactory.createLineBorder(borderCol, 1),
                    javax.swing.BorderFactory.createEmptyBorder(2, 5, 2, 5)
            ));

            // ComboBox
            UIManager.put("ComboBox.background", new ColorUIResource(inputBg));
            UIManager.put("ComboBox.foreground", new ColorUIResource(fgColor));
            UIManager.put("ComboBox.selectionBackground", new ColorUIResource(fgColor));
            UIManager.put("ComboBox.selectionForeground", new ColorUIResource(bgColor));
            
            // Tables
            UIManager.put("Table.background", new ColorUIResource(inputBg));
            UIManager.put("Table.foreground", new ColorUIResource(fgColor));
            UIManager.put("Table.selectionBackground", new ColorUIResource(fgColor));
            UIManager.put("Table.selectionForeground", new ColorUIResource(bgColor));
            UIManager.put("Table.gridColor", new ColorUIResource(new Color(50, 50, 80)));
            UIManager.put("TableHeader.background", new ColorUIResource(buttonBg));
            UIManager.put("TableHeader.foreground", new ColorUIResource(fgColor));

            // Menus
            UIManager.put("MenuBar.background", new ColorUIResource(bgColor));
            UIManager.put("Menu.background", new ColorUIResource(bgColor));
            UIManager.put("Menu.foreground", new ColorUIResource(fgColor));
            UIManager.put("MenuItem.background", new ColorUIResource(bgColor));
            UIManager.put("MenuItem.foreground", new ColorUIResource(fgColor));
            UIManager.put("MenuItem.selectionBackground", new ColorUIResource(fgColor));
            UIManager.put("MenuItem.selectionForeground", new ColorUIResource(bgColor));
            UIManager.put("PopupMenu.background", new ColorUIResource(bgColor));
            UIManager.put("PopupMenu.border", javax.swing.BorderFactory.createLineBorder(borderCol, 1));

            // ScrollPane
            UIManager.put("ScrollPane.background", new ColorUIResource(bgColor));
            UIManager.put("ScrollPane.border", javax.swing.BorderFactory.createLineBorder(borderCol, 1));

            // OptionPane
            UIManager.put("OptionPane.background", new ColorUIResource(bgColor));
            UIManager.put("OptionPane.messageForeground", new ColorUIResource(fgColor));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
