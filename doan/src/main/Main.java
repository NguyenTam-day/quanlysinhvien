package main;

import javax.swing.SwingUtilities;
import view.LoginForm;

public class Main {
    public static void main(String[] args) {
        // Load Driver MySQL (Chỉ cần làm 1 lần ở đây)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Chưa thêm thư viện MySQL JDBC!");
        }

        SwingUtilities.invokeLater(() -> {
            // Chạy form Login đầu tiên
            new LoginForm().setVisible(true);
        });
    }
}