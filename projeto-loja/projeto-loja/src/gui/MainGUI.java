package gui;

import javax.swing.SwingUtilities;

public class MainGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LojaUI ui = new LojaUI();
            ui.setVisible(true);
        });
    }
}
