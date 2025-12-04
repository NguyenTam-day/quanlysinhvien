package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.Student;
import service.StudentService;

public class MainFrame extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private String username;

    public MainFrame(String username) {
        this.username = username;

        setTitle("Quản lý sinh viên");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== Menu bên trái =====
        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 0, 10));
        menuPanel.setBackground(new Color(30, 144, 255));
        String[] menuItems = {"Trang chủ", "Sinh viên", "Môn học", "Thời khóa biểu"};
        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(30, 144, 255));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            menuPanel.add(btn);
        }

        // ===== Header =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Quản lý sinh viên");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(title, BorderLayout.WEST);

        JButton userButton = new JButton(username);
        userButton.setFocusPainted(false);

        JPopupMenu popup = new JPopupMenu();
        JMenuItem logoutItem = new JMenuItem("Đăng xuất");
        logoutItem.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });
        popup.add(logoutItem);

        userButton.addActionListener(e -> popup.show(userButton, 0, userButton.getHeight()));
        headerPanel.add(userButton, BorderLayout.EAST);

        // ===== Panel chính =====
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel topButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // ===== Nút thêm sinh viên =====
        JButton btnAdd = new JButton("Thêm sinh viên");
        topButtons.add(btnAdd);

        btnAdd.addActionListener(e -> {
            AddStudentFrame addFrame = new AddStudentFrame(this); // Truyền MainFrame hiện tại
            addFrame.setVisible(true);
        });

        topButtons.add(new JButton("Nhập Excel"));
        topButtons.add(new JButton("Xuất Excel"));

        // ===== Bảng dữ liệu =====
        String[] columns = {"MSSV", "Họ và tên", "Tuổi", "Giới tính", "GPA", "SĐT", "Email"};

        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        mainPanel.add(topButtons, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(menuPanel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // Load dữ liệu ngay khi mở form
        loadStudentTable();
    }

    // ===== TẢI LẠI DỮ LIỆU SINH VIÊN =====
    public void loadStudentTable() {
        StudentService service = new StudentService();
        List<Student> list = service.findAll();

        model.setRowCount(0);

        for (Student s : list) {
            model.addRow(new Object[]{
                    s.getMssv(),
                    s.getHoten(),
                    s.getTuoi(),
                    s.getGioitinh(),
                    s.getGpa(),
                    s.getSdt(),
                    s.getEmail()
            });
        }
    }
}
