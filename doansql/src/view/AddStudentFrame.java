//package view;
//
//import javax.swing.*;
//import java.awt.*;
//import service.StudentService;
//import model.Student;
//
//public class AddStudentFrame extends JFrame {
//
//    private JTextField txtId, txtName, txtAge, txtPhone, txtAddress, txtEmail, txtGpa;
//    private JComboBox<String> cbGender;
//    private MainFrame mainFrame; // Tham chiếu tới frame chính
//
//    public AddStudentFrame(MainFrame mainFrame) {
//        this.mainFrame = mainFrame;
//
//        setTitle("Thêm sinh viên");
//        setSize(400, 550);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLayout(new GridBagLayout());
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 10, 5, 10);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        // ===== Title =====
//        JLabel title = new JLabel("Thêm sinh viên", SwingConstants.CENTER);
//        title.setFont(new Font("Arial", Font.BOLD, 20));
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.gridwidth = 2;
//        add(title, gbc);
//        gbc.gridwidth = 1;
//
//        // ===== Row 1: MSSV =====
//        gbc.gridy++;
//        add(new JLabel("MSSV:"), gbc);
//        txtId = new JTextField();
//        gbc.gridx = 1;
//        add(txtId, gbc);
//        gbc.gridx = 0;
//
//        // ===== Row 2: Họ và tên =====
//        gbc.gridy++;
//        add(new JLabel("Họ và tên:"), gbc);
//        txtName = new JTextField();
//        gbc.gridx = 1;
//        add(txtName, gbc);
//        gbc.gridx = 0;
//
//        // ===== Row 3: Tuổi =====
//        gbc.gridy++;
//        add(new JLabel("Tuổi:"), gbc);
//        txtAge = new JTextField();
//        gbc.gridx = 1;
//        add(txtAge, gbc);
//        gbc.gridx = 0;
//
//        // ===== Row 4: Giới tính =====
//        gbc.gridy++;
//        add(new JLabel("Giới tính:"), gbc);
//        cbGender = new JComboBox<>(new String[]{"Nam", "Nữ"});
//        gbc.gridx = 1;
//        add(cbGender, gbc);
//        gbc.gridx = 0;
//
//        // ===== Row 5: Số điện thoại =====
//        gbc.gridy++;
//        add(new JLabel("Số điện thoại:"), gbc);
//        txtPhone = new JTextField();
//        gbc.gridx = 1;
//        add(txtPhone, gbc);
//        gbc.gridx = 0;
//
//        // ===== Row 6: Địa chỉ =====
//        gbc.gridy++;
//        add(new JLabel("Địa chỉ:"), gbc);
//        txtAddress = new JTextField();
//        gbc.gridx = 1;
//        add(txtAddress, gbc);
//        gbc.gridx = 0;
//
//        // ===== Row 7: Email =====
//        gbc.gridy++;
//        add(new JLabel("Email:"), gbc);
//        txtEmail = new JTextField();
//        gbc.gridx = 1;
//        add(txtEmail, gbc);
//        gbc.gridx = 0;
//
//        // ===== Row 8: GPA =====
//        gbc.gridy++;
//        add(new JLabel("GPA:"), gbc);
//        txtGpa = new JTextField("0");
//        gbc.gridx = 1;
//        add(txtGpa, gbc);
//        gbc.gridx = 0;
//
//        // ===== Buttons =====
//        gbc.gridy++;
//        JPanel btnPanel = new JPanel(new FlowLayout());
//        JButton btnSave = new JButton("Lưu");
//        JButton btnCancel = new JButton("Hủy");
//        btnPanel.add(btnSave);
//        btnPanel.add(btnCancel);
//        gbc.gridx = 0;
//        gbc.gridwidth = 2;
//        add(btnPanel, gbc);
//
//        // ===== HANDLE BUTTON SAVE =====
//        btnSave.addActionListener(e -> saveStudent());
//
//        // ===== HANDLE BUTTON CANCEL =====
//        btnCancel.addActionListener(e -> dispose());
//    }
//
//    private void saveStudent() {
//        try {
//            StudentService service = new StudentService();
//
//            // Lưu sinh viên
//            Student student = new Student(
//                    txtId.getText(),
//                    txtName.getText(),
//                    Integer.parseInt(txtAge.getText()),
//                    cbGender.getSelectedItem().toString(),
//                    Integer.parseInt(txtPhone.getText()),
//                    txtAddress.getText(),
//                    txtEmail.getText(),
//                    Double.parseDouble(txtGpa.getText())
//            );
//
//            service.addStudent(student);
//
//            JOptionPane.showMessageDialog(this, "Thêm sinh viên thành công!");
//
//            // Cập nhật bảng trong MainFrame hiện tại
//            mainFrame.loadStudentTable();
//
//            // Đóng AddStudentFrame
//            this.dispose();
//
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
//        }
//    }
//}
