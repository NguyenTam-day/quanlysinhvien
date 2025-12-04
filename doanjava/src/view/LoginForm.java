package view;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginForm extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin, btnExit;

    public LoginForm() {
        setTitle("Đăng nhập hệ thống");
        setSize(400, 280); // Kích thước vừa vặn
        setLocationRelativeTo(null); // Căn giữa màn hình
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // ===== 1. HEADER (TIÊU ĐỀ) =====
        JLabel lblTitle = new JLabel("LOGIN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(30, 144, 255)); // Màu xanh dương đẹp
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        // ===== 2. FORM NHẬP LIỆU (CENTER) =====
        JPanel panelCenter = new JPanel(new GridLayout(2, 2, 10, 20)); // Cách dòng thoáng hơn
        panelCenter.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); // Căn lề 2 bên

        // Dòng User
        JLabel lblUser = new JLabel("Tên đăng nhập:");
        lblUser.setFont(new Font("Arial", Font.PLAIN, 14));
        panelCenter.add(lblUser);
        
        txtUser = new JTextField();
        txtUser.setFont(new Font("Arial", Font.PLAIN, 14));
        panelCenter.add(txtUser);

        // Dòng Pass
        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(new Font("Arial", Font.PLAIN, 14));
        panelCenter.add(lblPass);
        
        txtPass = new JPasswordField();
        txtPass.setFont(new Font("Arial", Font.PLAIN, 14));
        panelCenter.add(txtPass);

        add(panelCenter, BorderLayout.CENTER);

        // ===== 3. BUTTONS (BOTTOM) =====
        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        btnLogin = new JButton("Đăng Nhập");
        btnLogin.setBackground(new Color(30, 144, 255));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 12));
        btnLogin.setPreferredSize(new Dimension(120, 35));
        btnLogin.setFocusPainted(false);

        btnExit = new JButton("Thoát");
        btnExit.setBackground(new Color(220, 53, 69)); // Màu đỏ nhẹ
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Arial", Font.BOLD, 12));
        btnExit.setPreferredSize(new Dimension(100, 35));
        btnExit.setFocusPainted(false);

        panelBottom.add(btnLogin);
        panelBottom.add(btnExit);
        panelBottom.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        add(panelBottom, BorderLayout.SOUTH);

        // ===== 4. XỬ LÝ SỰ KIỆN =====
        btnLogin.addActionListener(e -> handleLogin());
        btnExit.addActionListener(e -> System.exit(0));

        // Cho phép ấn phím Enter để đăng nhập luôn
        getRootPane().setDefaultButton(btnLogin);
    }

    private void handleLogin() {
        String username = txtUser.getText().trim();
        String password = new String(txtPass.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tài khoản và mật khẩu!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // --- KẾT NỐI DATABASE ---
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/quanlysinhvien", 
                "root", 
                "Nguyenductam123!" // Đảm bảo đúng pass của bạn
            );

            String sql = "SELECT user_name FROM account WHERE user_name=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Lấy đúng tên user từ DB
                String userDB = rs.getString("user_name");
                
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
                
                // Mở MainFrame và truyền user vào
                new MainFrame(userDB).setVisible(true);
                
                // Đóng form login
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối CSDL: " + ex.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
        }
    }
}