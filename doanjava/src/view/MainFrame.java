package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel mainPanelContainer; 
    private String currentUsername;
    
    public MainFrame(String username) {
        this.currentUsername=  username;
        setTitle("Hệ thống Quản lý Đào tạo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== 1. MENU BÊN TRÁI =====
        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 0, 10));
        menuPanel.setBackground(new Color(30, 144, 255));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] menuItems = { "Trang chủ", "Sinh viên", "Môn học", "Thời Khóa Biểu","Quản Lí Điểm" };
        
        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(30, 144, 255));
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            
            // --- XỬ LÝ SỰ KIỆN CHUYỂN TAB ---
            btn.addActionListener(e -> {
                switch (item) {
                    case "Sinh viên":
                        // Gọi StudentPanel thật (Code ở phần 2)
                        setMainPanel(new StudentPanel()); 
                        break;
                    case "Môn học":
                        // Gọi SubjectPanel (Code ở các câu hỏi trước)
                        setMainPanel(new SubjectPanel()); 
                        break;
                    case "Thời Khóa Biểu":
                        setMainPanel(new TimetablePanel());
                        break;
                    case "Quản Lí Điểm":
                        setMainPanel(new GradePanel());
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Chức năng " + item + " đang phát triển!");
                        break;
                }
            });
            
            menuPanel.add(btn);
        }

        // ===== 2. HEADER =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel title = new JLabel("QUẢN LÝ SINH VIÊN ");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(30, 144, 255));
        headerPanel.add(title, BorderLayout.WEST);

        // Nút User
        JButton userButton = new JButton("Xin chào: " + this.currentUsername); 
        userButton.setFocusPainted(false);
        userButton.setBackground(Color.WHITE);
        
        JPopupMenu popup = new JPopupMenu();
        JMenuItem logoutItem = new JMenuItem("Đăng xuất");
        logoutItem.addActionListener(e -> {
            this.dispose(); 
            new LoginForm().setVisible(true); 
        });
        popup.add(logoutItem);

        userButton.addActionListener(e -> popup.show(userButton, 0, userButton.getHeight()));
        headerPanel.add(userButton, BorderLayout.EAST);

        // ===== 3. PANEL CHỨA NỘI DUNG (CENTER) =====
        mainPanelContainer = new JPanel(new BorderLayout());
        
        // Mặc định vào là hiện ngay bảng Sinh viên
        mainPanelContainer.add(new StudentPanel(), BorderLayout.CENTER);

        add(menuPanel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanelContainer, BorderLayout.CENTER);
    }

    public void setMainPanel(JPanel newPanel) {
        mainPanelContainer.removeAll();
        mainPanelContainer.add(newPanel, BorderLayout.CENTER);
        mainPanelContainer.revalidate();
        mainPanelContainer.repaint();
    }
}