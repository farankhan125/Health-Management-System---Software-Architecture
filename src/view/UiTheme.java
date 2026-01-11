package view;

import javax.swing.*;
import java.awt.*;

public class UiTheme {

    public static void apply() {
        // default palette
        Color defaultBg = new Color(245, 247, 250);
        Color defaultAccent = new Color(57, 105, 138);
        Color defaultSoft = new Color(230, 240, 250);
        apply(defaultAccent, defaultSoft, defaultBg);
    }

    public static void apply(Color accent, Color soft, Color bgPanel) {
        // Try to use FlatLaf if available on classpath for a modern look
        boolean flatlafAvailable = false;
        try {
            Class<?> flat = Class.forName("com.formdev.flatlaf.FlatLightLaf");
            java.lang.reflect.Method install = flat.getMethod("install");
            install.invoke(null);
            flatlafAvailable = true;
        } catch (ClassNotFoundException ex) {
            // FlatLaf not on classpath — fall back to Nimbus
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception ignore) {}
        } catch (Exception ignore) {
            // any reflection/invoke error — fall back to Nimbus
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception ignore2) {}
        }
        Font base = new Font("Segoe UI", Font.PLAIN, 13);

        UIManager.put("Label.font", base);
        UIManager.put("Button.font", base);
        UIManager.put("TextField.font", base);
        UIManager.put("FormattedTextField.font", base);
        UIManager.put("TextArea.font", base);
        UIManager.put("Table.font", base);
        UIManager.put("TableHeader.font", base.deriveFont(Font.BOLD));

        UIManager.put("Panel.background", bgPanel);
        UIManager.put("TabbedPane.background", bgPanel);
        UIManager.put("TabbedPane.selected", soft);

        UIManager.put("Table.background", Color.WHITE);
        UIManager.put("Table.gridColor", new Color(220, 220, 220));
        UIManager.put("Table.selectionBackground", accent);
        UIManager.put("Table.selectionForeground", Color.WHITE);

        UIManager.put("Button.background", accent);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.focus", accent.darker());

        UIManager.put("TextField.background", Color.WHITE);
        UIManager.put("FormattedTextField.background", Color.WHITE);
        UIManager.put("TextArea.background", Color.WHITE);
        UIManager.put("ComboBox.background", Color.WHITE);

        UIManager.put("ScrollBar.thumb", accent);

        if (flatlafAvailable) {
            // Tweak some FlatLaf-specific properties for rounded controls
            UIManager.put("Component.arc", 8);
            UIManager.put("Button.arc", 8);
            UIManager.put("Component.focusWidth", 2);
            UIManager.put("TabbedPane.selectedBackground", soft);
        }
    }
}
