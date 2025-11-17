package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import model.Student;
import service.GradeService;
import service.StudentService;
import service.SubjectService;

public class StudentFrame extends JFrame {
    private StudentService studentService;
    private SubjectService subjectService;
    private GradeService gradeService;
    private JTextField txtId, txtName, txtAge, txtGpa, txtClass, txtSearch;
    private JTable table;
    private DefaultTableModel tableModel;

    public StudentFrame() {
        studentService = new StudentService("outputstudent.txt");

        setTitle("Student Manager");
        setSize(950, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ===== LEFT FORM (NHẬP LIỆU) =====
        JPanel leftPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Student Info"));

        txtId = new JTextField();
        txtName = new JTextField();
        txtAge = new JTextField();
        txtGpa = new JTextField();
        txtClass = new JTextField();

        leftPanel.add(new JLabel("ID:"));
        leftPanel.add(txtId);
        leftPanel.add(new JLabel("Name:"));
        leftPanel.add(txtName);
        leftPanel.add(new JLabel("Age:"));
        leftPanel.add(txtAge);
        leftPanel.add(new JLabel("GPA:"));
        leftPanel.add(txtGpa);
        leftPanel.add(new JLabel("Class:"));
        leftPanel.add(txtClass);

        // ===== RIGHT PANEL (CÁC NÚT CHỨC NĂNG) =====
        JPanel rightPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Functions"));

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnShow = new JButton("Show All");
        JButton btnSearch = new JButton("Search");
        JButton btnSort = new JButton("Sort by Name");
        JButton btnImport = new JButton("Import File");
        JButton btnExport = new JButton("Export File");

        rightPanel.add(btnAdd);
        rightPanel.add(btnUpdate);
        rightPanel.add(btnDelete);
        rightPanel.add(btnShow);
        rightPanel.add(btnSearch);
        rightPanel.add(btnSort);
        rightPanel.add(btnImport);
        rightPanel.add(btnExport);

        // ===== TOP PANEL GỘP 2 BÊN =====
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.add(leftPanel);
        topPanel.add(rightPanel);
        // Trong phần Right Panel (bên phải)
        JButton btnSubjects = new JButton("Manage Subjects");

        rightPanel.add(btnSubjects);

        // Thêm sự kiện cho 2 nút
        btnSubjects.addActionListener(e -> {
            // Khi nhấn, mở SubjectFrame
            SwingUtilities.invokeLater(() -> {
                SubjectFrame sf = new SubjectFrame();
                sf.setVisible(true);
            });
        });

        // ===== TABLE HIỂN THỊ SINH VIÊN =====
        String[] columns = { "ID", "Name", "Age", "GPA", "Class" };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Student List"));

        // ===== ADD TO FRAME =====
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // ===== EVENTS =====
        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnShow.addActionListener(e -> loadTable(studentService.findAll()));
        btnSearch.addActionListener(e -> searchStudents());
        btnSort.addActionListener(e -> sortStudents());
        btnImport.addActionListener(e -> importFromFile());
        btnExport.addActionListener(e -> exportToTXT());

        loadTable(studentService.findAll());
    }

    // ===== CÁC CHỨC NĂNG =====
    private void addStudent() {
        try {
            String id = txtId.getText().trim();
            if (id.isEmpty())
                throw new Exception();
            String name = txtName.getText().trim();
            int age = Integer.parseInt(txtAge.getText().trim());
            double gpa = Double.parseDouble(txtGpa.getText().trim());
            String className = txtClass.getText().trim();
            Student s = new Student(id, name, age, gpa, className);
            studentService.addStudent(s);
            loadTable(studentService.findAll());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    private void updateStudent() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter ID!");
            return;
        }
        try {
            String name = txtName.getText().trim();
            int age = Integer.parseInt(txtAge.getText().trim());
            double gpa = Double.parseDouble(txtGpa.getText().trim());
            String className = txtClass.getText().trim();
            Student s = new Student(id, name, age, gpa, className);
            studentService.updateStudent(id, s);
            loadTable(studentService.findAll());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    private void deleteStudent() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter ID!");
            return;
        }
        studentService.deleteStudent(id);
        loadTable(studentService.findAll());
    }

    private void searchStudents() {
        String keyword = JOptionPane.showInputDialog(this, "Enter name to search:");
        if (keyword != null) {
            List<Student> list = studentService.searchByName(keyword);
            loadTable(list);
        }
    }

    private void sortStudents() {
        loadTable(studentService.sortByName());
    }

    private void importFromFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] p = line.trim().split("\\s+");
                    if (p.length == 5) {
                        studentService.addStudent(new Student(p[0], p[1],
                                Integer.parseInt(p[2]),
                                Double.parseDouble(p[3]),
                                p[4]));
                    }
                }
                JOptionPane.showMessageDialog(this, "Import success!");
                loadTable(studentService.findAll());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Import failed!");
            }
        }
    }

    private void exportToTXT() {
        File file = new File("outputstudents.txt");
        try (PrintWriter pw = new PrintWriter(file)) {
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                pw.print(tableModel.getColumnName(i));
                if (i < tableModel.getColumnCount() - 1)
                    pw.print(" ");
            }
            pw.println();

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    pw.print(tableModel.getValueAt(i, j));
                    if (j < tableModel.getColumnCount() - 1)
                        pw.print(" ");
                }
                pw.println();
            }

            JOptionPane.showMessageDialog(this, "Export success! File saved to src/outputstudents.txt");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Export failed!");
        }
    }

    private void loadTable(List<Student> list) {
        tableModel.setRowCount(0);
        for (Student s : list) {
            tableModel.addRow(new Object[] { s.getId(), s.getName(), s.getAge(), s.getGpa(), s.getClassName() });
        }
    }
}
