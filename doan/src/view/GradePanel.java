package view;

import model.Grade;
import model.Subject;
import service.GradeService;
import service.StudentService;
import service.SubjectService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GradePanel extends JPanel {
    private JComboBox<String> cboClass;
    private JComboBox<Subject> cboSubject; 
    private JTable table;
    private DefaultTableModel model;
    
    private GradeService gradeService = new GradeService();
    private StudentService studentService = new StudentService();
    private SubjectService subjectService = new SubjectService();

    public GradePanel() {
        setLayout(new BorderLayout());

        // === 1. TOP BAR ===
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        
        cboClass = new JComboBox<>();
        cboClass.setPreferredSize(new Dimension(100, 30));
        loadClasses();
        
        cboSubject = new JComboBox<>();
        cboSubject.setPreferredSize(new Dimension(200, 30));
        loadSubjects();
        
        JButton btnLoad = new JButton("Xem bảng điểm");
        JButton btnSave = new JButton("Lưu điểm");
        btnSave.setBackground(new Color(40, 167, 69)); 
        btnSave.setForeground(Color.WHITE);

        topPanel.add(new JLabel("Lớp:"));
        topPanel.add(cboClass);
        topPanel.add(new JLabel("Môn:"));
        topPanel.add(cboSubject);
        topPanel.add(btnLoad);
        topPanel.add(btnSave);

        add(topPanel, BorderLayout.NORTH);

        // === 2. TABLE (Cập nhật cột) ===
        // Thêm các cột mới. Cột "Tổng kết" để cuối cùng cho dễ nhìn
        String[] columns = {"MSSV", "Họ Tên", "Chuyên cần(10%)", "Bài tập(10%)", "Thực hành(10%)", "Giữa kì(20%)", "Cuối kì(50%)", "Tổng kết"};
        
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Chỉ cho sửa cột điểm thành phần (cột 2 đến 6)
                // Cột 0,1 (Info) và cột 7 (Tổng kết) không được sửa
                return column >= 2 && column <= 6;
            }
        };
        
        table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Chỉnh độ rộng cột cho đẹp
        table.getColumnModel().getColumn(0).setPreferredWidth(80); // MSSV
        table.getColumnModel().getColumn(1).setPreferredWidth(150); // Tên
        
        add(new JScrollPane(table), BorderLayout.CENTER);

        // === 3. EVENT ===
        btnLoad.addActionListener(e -> loadGrades());
        btnSave.addActionListener(e -> saveAllGrades());
    }

    private void loadClasses() {
        cboClass.removeAllItems();
        List<String> classes = studentService.getAllClasses();
        for (String c : classes) cboClass.addItem(c);
    }

    private void loadSubjects() {
        cboSubject.removeAllItems();
        List<Subject> subjects = subjectService.findAll();
        for (Subject s : subjects) cboSubject.addItem(s);
    }

    private void loadGrades() {
        String className = (String) cboClass.getSelectedItem();
        Subject selectedSubject = (Subject) cboSubject.getSelectedItem();
        
        if (className == null || selectedSubject == null) return;

        List<Grade> list = gradeService.getGradesByClassAndSubject(className, selectedSubject.getSubjectId());
        
        model.setRowCount(0);
        for (Grade g : list) {
            // Định dạng điểm tổng kết (làm tròn 2 số lẻ)
            double total = g.getTotal();
            String totalStr = String.format("%.2f", total);

            model.addRow(new Object[]{
                g.getStudentId(),
                g.getStudentName(),
                g.getAttendance(),
                g.getHomework(),
                g.getPractical(),
                g.getMidterm(),
                g.getFinalExam(),
                totalStr // Hiển thị tổng kết
            });
        }
    }

    private void saveAllGrades() {
        if (table.isEditing()) table.getCellEditor().stopCellEditing();

        Subject selectedSubject = (Subject) cboSubject.getSelectedItem();
        if (selectedSubject == null) return;
        
        try {
            for (int i = 0; i < model.getRowCount(); i++) {
                String mssv = model.getValueAt(i, 0).toString();
                
                // Lấy 5 cột điểm
                double att = parseScore(model.getValueAt(i, 2));
                double hw = parseScore(model.getValueAt(i, 3));
                double prac = parseScore(model.getValueAt(i, 4));
                double mid = parseScore(model.getValueAt(i, 5));
                double fin = parseScore(model.getValueAt(i, 6));

                Grade g = new Grade();
                g.setStudentId(mssv);
                g.setSubjectId(selectedSubject.getSubjectId());
                
                g.setAttendance(att);
                g.setHomework(hw);
                g.setPractical(prac);
                g.setMidterm(mid);
                g.setFinalExam(fin);

                gradeService.saveGrade(g);
            }
            
            JOptionPane.showMessageDialog(this, "Lưu thành công!");
            loadGrades(); // Load lại để cập nhật cột Tổng kết mới nhất
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private double parseScore(Object value) {
        if (value == null || value.toString().trim().isEmpty()) return 0.0;
        try {
            double s = Double.parseDouble(value.toString().trim());
            return (s < 0 || s > 10) ? 0.0 : s;
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}