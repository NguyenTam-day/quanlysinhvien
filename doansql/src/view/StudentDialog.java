package view;

import model.Student;
import service.StudentService;

import javax.swing.*;
import java.awt.*;

public class StudentDialog extends JDialog {

    private JTextField txtMssv, txtHoTen, txtTuoi, txtGpa, txtSdt, txtEmail, txtDiaChi; // thêm txtDiaChi
    private JRadioButton rdoNam, rdoNu;
    private ButtonGroup btnGroupGioiTinh;
    
    private StudentService studentService = new StudentService();
    private StudentPanel parentPanel;
    private boolean isEditMode;

    public StudentDialog(JFrame owner, StudentPanel parentPanel, Student student) {
        super(owner, student == null ? "Thêm Sinh Viên" : "Sửa Sinh Viên", true);
        this.parentPanel = parentPanel;
        this.isEditMode = (student != null);

        setSize(400, 500); // tăng chiều cao để vừa txtDiaChi
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // --- Form nhập liệu ---
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10)); // tăng hàng từ 7->8
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
        rdoNam.setSelected(true);
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
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        // --- Điền dữ liệu nếu là Edit Mode ---
        if (isEditMode) {
            txtMssv.setText(student.getMssv());
            txtMssv.setEditable(false);
            txtHoTen.setText(student.getHoten());
            txtTuoi.setText(String.valueOf(student.getTuoi()));
            
            if (student.getGioitinh().equalsIgnoreCase("Nam")) {
                rdoNam.setSelected(true);
            } else {
                rdoNu.setSelected(true);
            }
            
            txtGpa.setText(String.valueOf(student.getGpa()));
            txtSdt.setText(String.valueOf(student.getSdt()));
            txtEmail.setText(student.getEmail());
            txtDiaChi.setText(student.getDiachi()); // điền địa chỉ
        }

        // --- Xử lý sự kiện ---
        btnSave.addActionListener(e -> saveData());
        btnCancel.addActionListener(e -> dispose());
    }

    private void saveData() {
        try {
            String mssv = txtMssv.getText().trim();
            String ten = txtHoTen.getText().trim();
            int tuoi = Integer.parseInt(txtTuoi.getText().trim());
            String gioitinh = rdoNam.isSelected() ? "Nam" : "Nữ";
            double gpa = Double.parseDouble(txtGpa.getText().trim());
            int sdt = Integer.parseInt(txtSdt.getText().trim());
            String email = txtEmail.getText().trim();
            String diachi = txtDiaChi.getText().trim(); // lấy địa chỉ

            if (mssv.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            Student s = new Student(mssv, ten, tuoi, gioitinh, sdt, email,diachi, gpa);

            if (isEditMode) {
                studentService.updateStudent(s); 
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            } else {
                studentService.addStudent(s); 
                JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
            }

            parentPanel.loadStudentTable();
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tuổi và GPA phải là số!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }
}
