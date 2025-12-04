package view;

import model.Subject;
import service.SubjectService;

import javax.swing.*;
import java.awt.*;

public class SubjectDialog extends JDialog {

    private JTextField txtId, txtName, txtCredit, txtTeacher, txtDept;
    private JButton btnSave, btnCancel;
    
    private SubjectService subjectService;
    private SubjectPanel parentPanel; // Để gọi hàm load lại bảng sau khi lưu
    private Subject existingSubject;  // Lưu môn cần sửa (nếu có)

    public SubjectDialog(Frame owner, SubjectPanel parentPanel, Subject subjectToEdit) {
        super(owner, subjectToEdit == null ? "Thêm môn học mới" : "Sửa môn học", true);
        this.parentPanel = parentPanel;
        this.existingSubject = subjectToEdit;
        this.subjectService = new SubjectService();

        setSize(400, 350);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // --- FORM INPUT ---
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtId = new JTextField();
        txtName = new JTextField();
        txtCredit = new JTextField();
        txtTeacher = new JTextField();
        txtDept = new JTextField();

        formPanel.add(new JLabel("Mã môn:"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("Tên môn:"));
        formPanel.add(txtName);
        formPanel.add(new JLabel("Số tín chỉ:"));
        formPanel.add(txtCredit);
        formPanel.add(new JLabel("Giảng viên:"));
        formPanel.add(txtTeacher);
        formPanel.add(new JLabel("Khoa/Bộ môn:"));
        formPanel.add(txtDept);

        add(formPanel, BorderLayout.CENTER);

        // --- BUTTONS ---
        JPanel buttonPanel = new JPanel();
        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");
        
        // Style nút cho đẹp
        btnSave.setBackground(new Color(30, 144, 255));
        btnSave.setForeground(Color.WHITE);

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        // --- XỬ LÝ LOGIC ---
        
        // 1. Nếu là chế độ SỬA -> Điền dữ liệu cũ vào
        if (existingSubject != null) {
            txtId.setText(existingSubject.getSubjectId());
            txtId.setEditable(false); // Không cho sửa khóa chính
            txtId.setBackground(Color.LIGHT_GRAY);
            
            txtName.setText(existingSubject.getSubjectName());
            txtCredit.setText(String.valueOf(existingSubject.getCredit()));
            txtTeacher.setText(existingSubject.getTeacherName());
            txtDept.setText(existingSubject.getDepartment());
        }

        // 2. Sự kiện nút Lưu
        btnSave.addActionListener(e -> saveSubject());

        // 3. Sự kiện nút Hủy
        btnCancel.addActionListener(e -> dispose());
    }

    private void saveSubject() {
        // Lấy dữ liệu từ form
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String creditStr = txtCredit.getText().trim();
        String teacher = txtTeacher.getText().trim();
        String dept = txtDept.getText().trim();

        // Validate cơ bản
        if (id.isEmpty() || name.isEmpty() || creditStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        int credit = 0;
        try {
            credit = Integer.parseInt(creditStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số tín chỉ phải là số!");
            return;
        }

        Subject s = new Subject(id, name, credit, teacher, dept);

        if (existingSubject == null) {
            // === CHẾ ĐỘ THÊM ===
            try {
                subjectService.addSubject(s);
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
            } catch (Exception e) {
                // Thường lỗi do trùng ID
                JOptionPane.showMessageDialog(this, "Lỗi thêm môn (Có thể trùng Mã môn)!");
                return;
            }
        } else {
            // === CHẾ ĐỘ SỬA ===
            subjectService.updateSubject(id, s);
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
        }

        // Load lại bảng ở giao diện cha và đóng form
        parentPanel.loadData(); 
        dispose();
    }
}