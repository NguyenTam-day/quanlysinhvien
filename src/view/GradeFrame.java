package view;

import model.Grade;
import service.FileGradeService;
import service.GradeService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;

public class GradeFrame extends JFrame {

    private GradeService gradeService;
    private JTextField txtStudentId, txtSubjectId, txtCC, txtBT, txtGK, txtTH, txtCK;
    private JTable table;
    private DefaultTableModel tableModel;

    public GradeFrame() {
        gradeService = new GradeService(new FileGradeService("outputgrade.txt"));

        setTitle("Grade Manager");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ===== LEFT FORM =====
        JPanel leftPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Grade Info"));

        txtStudentId = new JTextField();
        txtSubjectId = new JTextField();
        txtCC = new JTextField();
        txtBT = new JTextField();
        txtGK = new JTextField();
        txtTH = new JTextField();
        txtCK = new JTextField();

        leftPanel.add(new JLabel("Student ID:"));
        leftPanel.add(txtStudentId);
        leftPanel.add(new JLabel("Subject ID:"));
        leftPanel.add(txtSubjectId);
        leftPanel.add(new JLabel("Điểm CC:"));
        leftPanel.add(txtCC);
        leftPanel.add(new JLabel("Điểm BT:"));
        leftPanel.add(txtBT);
        leftPanel.add(new JLabel("Điểm GK:"));
        leftPanel.add(txtGK);
        leftPanel.add(new JLabel("Điểm TH:"));
        leftPanel.add(txtTH);
        leftPanel.add(new JLabel("Điểm CK:"));
        leftPanel.add(txtCK);

        // ===== RIGHT PANEL =====
        JPanel rightPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Functions"));

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnShow = new JButton("Show All");
        JButton btnSearch = new JButton("Search");
        JButton btnSort = new JButton("Sort by Student ID");
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

        // ===== TOP PANEL =====
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.add(leftPanel);
        topPanel.add(rightPanel);

        // ===== TABLE =====
        String[] columns = { "Student ID", "Subject ID", "CC", "BT", "GK", "TH", "CK" };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Grade List"));

        // ===== ADD TO FRAME =====
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // ===== EVENTS =====
        btnAdd.addActionListener(e -> addGrade());
        btnUpdate.addActionListener(e -> updateGrade());
        btnDelete.addActionListener(e -> deleteGrade());
        btnShow.addActionListener(e -> loadTable(gradeService.getAllGrades()));
        btnSearch.addActionListener(e -> searchGrade());
        btnSort.addActionListener(e -> sortGrades());
        btnImport.addActionListener(e -> importFromFile());
        btnExport.addActionListener(e -> exportToTXT());

        loadTable(gradeService.getAllGrades());
    }

    private void addGrade() {
        try {
            String studentId = txtStudentId.getText().trim();
            String subjectId = txtSubjectId.getText().trim();
            double cc = Double.parseDouble(txtCC.getText().trim());
            double bt = Double.parseDouble(txtBT.getText().trim());
            double gk = Double.parseDouble(txtGK.getText().trim());
            double th = Double.parseDouble(txtTH.getText().trim());
            double ck = Double.parseDouble(txtCK.getText().trim());

            if (studentId.isEmpty() || subjectId.isEmpty())
                throw new Exception();

            Grade g = new Grade(studentId, subjectId, cc, bt, gk, th, ck);
            gradeService.addGrade(g);
            loadTable(gradeService.getAllGrades());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    private void updateGrade() {
        try {
            String studentId = txtStudentId.getText().trim();
            String subjectId = txtSubjectId.getText().trim();
            double cc = Double.parseDouble(txtCC.getText().trim());
            double bt = Double.parseDouble(txtBT.getText().trim());
            double gk = Double.parseDouble(txtGK.getText().trim());
            double th = Double.parseDouble(txtTH.getText().trim());
            double ck = Double.parseDouble(txtCK.getText().trim());

            if (studentId.isEmpty() || subjectId.isEmpty())
                throw new Exception();

            gradeService.updateGrade(studentId, subjectId, cc, bt, gk, th, ck);
            loadTable(gradeService.getAllGrades());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    private void deleteGrade() {
        String studentId = txtStudentId.getText().trim();
        String subjectId = txtSubjectId.getText().trim();

        if (studentId.isEmpty() || subjectId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Student ID and Subject ID!");
            return;
        }
        gradeService.removeGrade(studentId, subjectId);
        loadTable(gradeService.getAllGrades());
    }

    private void searchGrade() {
        String studentId = JOptionPane.showInputDialog(this, "Enter Student ID to search:");
        if (studentId != null) {
            List<Grade> list = gradeService.getAllGrades();
            list.removeIf(g -> !g.getStudentId().equalsIgnoreCase(studentId));
            loadTable(list);
        }
    }

    private void sortGrades() {
        List<Grade> list = gradeService.getAllGrades();
        list.sort((g1, g2) -> g1.getStudentId().compareToIgnoreCase(g2.getStudentId()));
        loadTable(list);
    }

    private void importFromFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] p = line.trim().split("\\s+");
                    if (p.length == 7) {
                        Grade g = new Grade(p[0], p[1],
                                Double.parseDouble(p[2]),
                                Double.parseDouble(p[3]),
                                Double.parseDouble(p[4]),
                                Double.parseDouble(p[5]),
                                Double.parseDouble(p[6]));
                        gradeService.addGrade(g);
                    }
                }
                JOptionPane.showMessageDialog(this, "Import success!");
                loadTable(gradeService.getAllGrades());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Import failed!");
            }
        }
    }

    private void exportToTXT() {
        File file = new File("outputgrade.txt");
        try (PrintWriter pw = new PrintWriter(file)) {
            for (Grade g : gradeService.getAllGrades()) {
                pw.println(g.getStudentId() + " " + g.getSubjectId() + " " +
                        g.getDiemChuyenCan() + " " + g.getDiemBaiTap() + " " +
                        g.getDiemGiuaKy() + " " + g.getDiemThucHanh() + " " +
                        g.getDiemCuoiKy());
            }
            JOptionPane.showMessageDialog(this, "Export success!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Export failed!");
        }
    }

    private void loadTable(List<Grade> list) {
        tableModel.setRowCount(0);
        for (Grade g : list) {
            tableModel.addRow(new Object[] { g.getStudentId(), g.getSubjectId(),
                    g.getDiemChuyenCan(), g.getDiemBaiTap(),
                    g.getDiemGiuaKy(), g.getDiemThucHanh(), g.getDiemCuoiKy() });
        }
    }
}
