package main;

import javax.swing.SwingUtilities;
import view.StudentFrame;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            new StudentFrame().setVisible(true);
        });
    }
}
