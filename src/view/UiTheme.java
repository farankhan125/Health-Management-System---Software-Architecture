package view;

import javax.swing.*;
import java.awt.*;

public class UiTheme {

    public static void apply() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignore) {}

        Font base = new Font("Segoe UI", Font.PLAIN, 13);

        UIManager.put("Label.font", base);
        UIManager.put("Button.font", base);
        UIManager.put("TextField.font", base);
        UIManager.put("FormattedTextField.font", base);
        UIManager.put("TextArea.font", base);
        UIManager.put("Table.font", base);
        UIManager.put("TableHeader.font", base.deriveFont(Font.BOLD));

        Color bgPanel = new Color(245, 247, 250);
        Color accent = new Color(57, 105, 138);
        Color soft = new Color(230, 240, 250);

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
    }
}
