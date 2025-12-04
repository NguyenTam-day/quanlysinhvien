package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginForm extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;

    public LoginForm() {
        setTitle("Login");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        txtUser = new JTextField();
        txtPass = new JPasswordField();
        btnLogin = new JButton("Đăng nhập");

        add(new JLabel("Username:"));
        add(txtUser);
        add(new JLabel("Password:"));
        add(txtPass);
        add(new JLabel());
        add(btnLogin);

        btnLogin.addActionListener(e -> login());
    }

    private void login() {
        String username = txtUser.getText();
        String password = new String(txtPass.getPassword());

        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/quanlysinhvien", "root", "Nguyenductam123!"
            );

            String sql = "SELECT user_name FROM account WHERE user_name=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String userNameFromDB = rs.getString("user_name");
                new MainFrame(userNameFromDB).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sai username hoặc password");
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
