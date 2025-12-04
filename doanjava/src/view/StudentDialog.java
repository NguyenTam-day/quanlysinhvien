package view;

import model.Student;
import service.StudentService;

import javax.swing.*;
import java.awt.*;

public class StudentDialog extends JDialog {

    private JTextField txtMssv, txtHoTen, txtTuoi, txtGpa, txtSdt, txtEmail, txtDiaChi;
    private JRadioButton rdoNam, rdoNu;
    private ButtonGroup btnGroupGioiTinh;
    
    private StudentService studentService = new StudentService();
    private StudentPanel parentPanel;
    private boolean isEditMode;

    public StudentDialog(JFrame owner, StudentPanel parentPanel, Student student) {
        super(owner, student == null ? "Thêm Sinh Viên" : "Sửa Sinh Viên", true);
        this.parentPanel = parentPanel;
        this.isEditMode = (student != null);

        setSize(400, 550); // Tăng chiều cao lên chút nữa cho thoáng
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // --- Form nhập liệu ---
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. MSSV
        formPanel.add(new JLabel("MSSV:"));
        txtMssv = new JTextField();
        formPanel.add(txtMssv);

        // 2. Họ tên
        formPanel.add(new JLabel("Họ và tên:"));
        txtHoTen = new JTextField();
        formPanel.add(txtHoTen);

        // 3. Tuổi
        formPanel.add(new JLabel("Tuổi:"));
        txtTuoi = new JTextField();
        formPanel.add(txtTuoi);

        // 4. Giới tính
        formPanel.add(new JLabel("Giới tính:"));
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rdoNam = new JRadioButton("Nam");
        rdoNu = new JRadioButton("Nữ");
        btnGroupGioiTinh = new ButtonGroup();
        btnGroupGioiTinh.add(rdoNam);
        btnGroupGioiTinh.add(rdoNu);
        rdoNam.setSelected(true); // Mặc định là Nam
        genderPanel.add(rdoNam);
        genderPanel.add(rdoNu);
        formPanel.add(genderPanel);

        // 5. GPA
        formPanel.add(new JLabel("GPA:"));
        txtGpa = new JTextField();
        formPanel.add(txtGpa);

        // 6. SĐT
        formPanel.add(new JLabel("Số điện thoại:"));
        txtSdt = new JTextField();
        formPanel.add(txtSdt);

        // 7. Email
        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        // 8. Địa chỉ
        formPanel.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        formPanel.add(txtDiaChi);

        add(formPanel, BorderLayout.CENTER);

        // --- Các nút bấm ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");
        
        // Trang trí nút chút cho đẹp
        btnSave.setBackground(new Color(30, 144, 255));
        btnSave.setForeground(Color.WHITE);
        
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        // --- Điền dữ liệu cũ nếu là Edit Mode ---
        if (isEditMode) {
            txtMssv.setText(student.getMssv());
            txtMssv.setEditable(false); // Khóa không cho sửa MSSV
            txtHoTen.setText(student.getHoten());
            txtTuoi.setText(String.valueOf(student.getTuoi()));
            
            if (student.getGioitinh() != null && student.getGioitinh().equalsIgnoreCase("Nữ")) {
                rdoNu.setSelected(true);
            } else {
                rdoNam.setSelected(true);
            }
            
            txtGpa.setText(String.valueOf(student.getGpa()));
            // Sửa lỗi: SĐT giờ là String nên không cần String.valueOf, lấy trực tiếp
            txtSdt.setText(student.getSdt()); 
            txtEmail.setText(student.getEmail());
            txtDiaChi.setText(student.getDiachi());
        }

        // --- Xử lý sự kiện ---
        btnSave.addActionListener(e -> saveData());
        btnCancel.addActionListener(e -> dispose());
    }

    private void saveData() {
        try {
            // Lấy dữ liệu từ form
            String mssv = txtMssv.getText().trim();
            String ten = txtHoTen.getText().trim();
            String tuoiStr = txtTuoi.getText().trim();
            String gpaStr = txtGpa.getText().trim();
            String sdt = txtSdt.getText().trim(); // Sửa lỗi: Lấy String, không ép sang int
            String email = txtEmail.getText().trim();
            String diachi = txtDiaChi.getText().trim();
            String gioitinh = rdoNam.isSelected() ? "Nam" : "Nữ";

            // Validate dữ liệu trống
            if (mssv.isEmpty() || ten.isEmpty() || tuoiStr.isEmpty() || gpaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ các trường bắt buộc!");
                return;
            }

            int tuoi = Integer.parseInt(tuoiStr);
            double gpa = Double.parseDouble(gpaStr);

            // Tạo đối tượng Student
            // LƯU Ý: Thứ tự tham số phải khớp với Constructor trong model/Student.java
            // (Mssv, Hoten, Tuoi, Gioitinh, Sdt, Diachi, Email, Gpa)
            Student s = new Student(mssv, ten, tuoi, gioitinh, sdt, diachi, email, gpa);

            if (isEditMode) {
                studentService.updateStudent(s); 
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            } else {
                studentService.addStudent(s); 
                JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
            }

            // Load lại bảng ở StudentPanel
            parentPanel.loadStudentTable();
            dispose(); // Đóng dialog

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: Tuổi và GPA phải là số!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace(); // In lỗi ra console để debug
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage());
        }
    }
}