package main;

import javax.swing.SwingUtilities;
import view.StudentFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentFrame().setVisible(true);
        });
    }
}
