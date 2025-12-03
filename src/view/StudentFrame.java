package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import model.Student;
import service.ClassroomService;
import service.GradeService;
import service.StudentService;
import service.SubjectService;
import service.MajorService;

public class StudentFrame extends JFrame {
    private StudentService studentService;
    private SubjectService subjectService;
    private GradeService gradeService;
    private MajorService majorService;
    private ClassroomService classroomService;
    private JTextField txtId, txtName, txtAge, txtGpa, txtClass, txtSearch;
    private JTable table;
    private DefaultTableModel tableModel;

    public StudentFrame() {

        studentService = new StudentService();
        subjectService = new SubjectService();
        gradeService = new GradeService();
        majorService = new MajorService();
        classroomService = new ClassroomService(); 

        setTitle("Student Manager");
        setSize(1000, 650); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

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
        leftPanel.add(new JLabel("Class ID:")); 
        leftPanel.add(txtClass);

        JPanel rightPanel = new JPanel(new GridLayout(4, 3, 10, 10)); 
        rightPanel.setBorder(BorderFactory.createTitledBorder("Functions"));

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnShow = new JButton("Show All");
        JButton btnSearch = new JButton("Search by Name"); 
        JButton btnSort = new JButton("Sort by Name");
        JButton btnImport = new JButton("Import File");
        JButton btnExport = new JButton("Export File");

        JButton btnSubjects = new JButton("Manage Subjects");
        JButton btnMajor = new JButton("Manage Majors"); 
        JButton btnClassroom = new JButton("Manage Classrooms"); 

        rightPanel.add(btnAdd);
        rightPanel.add(btnUpdate);
        rightPanel.add(btnDelete);
        
        rightPanel.add(btnShow);
        rightPanel.add(btnSearch);
        rightPanel.add(btnSort);
        
        rightPanel.add(btnImport);
        rightPanel.add(btnExport);

        rightPanel.add(btnSubjects);
        rightPanel.add(btnMajor);
        rightPanel.add(btnClassroom);

        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.add(leftPanel);
        topPanel.add(rightPanel);

        
        btnSubjects.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {

                SubjectFrame sf = new SubjectFrame(); 
                sf.setVisible(true);
            });
        });
        
        btnMajor.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                MajorFrame mf = new MajorFrame(); 
                mf.setVisible(true);
            });
        });
        
        btnClassroom.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {

                ClassroomFrame clf = new ClassroomFrame(classroomService, majorService); 
                clf.setVisible(true);
            });
        });

        String[] columns = { "ID", "Name", "Age", "GPA", "Class" }; 
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Student List"));

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                txtId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtName.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtAge.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtGpa.setText(tableModel.getValueAt(selectedRow, 3).toString());

                txtClass.setText(tableModel.getValueAt(selectedRow, 4).toString()); 

            }
        });
        
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

    private void addStudent() {
        try {
            String id = txtId.getText().trim();
            if (id.isEmpty())
                throw new Exception("ID cannot be empty");
            String name = txtName.getText().trim();
            int age = Integer.parseInt(txtAge.getText().trim());
            double gpa = Double.parseDouble(txtGpa.getText().trim());
            String classId = txtClass.getText().trim(); 

            Student s = new Student(id, name, age, gpa, classId, null); 
            studentService.addStudent(s);
            loadTable(studentService.findAll());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input or missing ID/Class ID: " + e.getMessage());
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
            String classId = txtClass.getText().trim();

            Student s = new Student(id, name, age, gpa, classId, null); 
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
                                p[4], 
                                null)); 
                    }
                }
                JOptionPane.showMessageDialog(this, "Import success!");
                loadTable(studentService.findAll());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Import failed: " + e.getMessage());
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

            tableModel.addRow(new Object[] { 
                s.getId(), 
                s.getName(), 
                s.getAge(), 
                s.getGpa(), 
                s.getClassName() 
            });
        }
    }
}