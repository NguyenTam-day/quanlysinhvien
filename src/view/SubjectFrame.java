package view;

import model.Subject;
import service.SubjectService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;

public class SubjectFrame extends JFrame {

    private SubjectService subjectService;
    private JTextField txtMaMon, txtTenMon, txtSoTinChi;
    private JTable table;
    private DefaultTableModel tableModel;

    public SubjectFrame() {
        subjectService = new SubjectService();

        setTitle("Subject Manager");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ===== LEFT FORM =====
        JPanel leftPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Subject Info"));

        txtMaMon = new JTextField();
        txtTenMon = new JTextField();
        txtSoTinChi = new JTextField();

        leftPanel.add(new JLabel("Mã môn:"));
        leftPanel.add(txtMaMon);
        leftPanel.add(new JLabel("Tên môn:"));
        leftPanel.add(txtTenMon);
        leftPanel.add(new JLabel("Số tín chỉ:"));
        leftPanel.add(txtSoTinChi);

        // ===== RIGHT PANEL =====
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

        // ===== TOP PANEL =====
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.add(leftPanel);
        topPanel.add(rightPanel);
        // Trong phần Right Panel (bên phải)
        JButton btnGrades = new JButton("Manage Grades");

        rightPanel.add(btnGrades);

        // Thêm sự kiện cho 2 nút
        btnGrades.addActionListener(e -> {
            // Khi nhấn, mở SubjectFrame
            SwingUtilities.invokeLater(() -> {
                GradeFrame sf = new GradeFrame();
                sf.setVisible(true);
            });
        });

        // ===== TABLE =====
        String[] columns = { "Mã môn", "Tên môn", "Số tín chỉ" };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Subject List"));

        // ===== ADD TO FRAME =====
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // ===== EVENTS =====
        btnAdd.addActionListener(e -> addSubject());
        btnUpdate.addActionListener(e -> updateSubject());
        btnDelete.addActionListener(e -> deleteSubject());
        btnShow.addActionListener(e -> loadTable(subjectService.getAllSubjects()));
        btnSearch.addActionListener(e -> searchSubject());
        btnSort.addActionListener(e -> sortSubjects());
        btnImport.addActionListener(e -> importFromFile());
        btnExport.addActionListener(e -> exportToTXT());

        loadTable(subjectService.getAllSubjects());
    }

    private void addSubject() {
        try {
            String maMon = txtMaMon.getText().trim();
            String tenMon = txtTenMon.getText().trim();
            int soTinChi = Integer.parseInt(txtSoTinChi.getText().trim());

            if (maMon.isEmpty() || tenMon.isEmpty())
                throw new Exception();

            Subject s = new Subject(maMon, tenMon, soTinChi);
            subjectService.addSubject(s);
            loadTable(subjectService.getAllSubjects());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    private void updateSubject() {
        try {
            String maMon = txtMaMon.getText().trim();
            String tenMon = txtTenMon.getText().trim();
            int soTinChi = Integer.parseInt(txtSoTinChi.getText().trim());

            if (maMon.isEmpty() || tenMon.isEmpty())
                throw new Exception();

            subjectService.updateSubject(maMon, tenMon, soTinChi);
            loadTable(subjectService.getAllSubjects());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    private void deleteSubject() {
        String maMon = txtMaMon.getText().trim();
        if (maMon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Mã môn!");
            return;
        }
        subjectService.removeSubject(maMon);
        loadTable(subjectService.getAllSubjects());
    }

    private void searchSubject() {
        String keyword = JOptionPane.showInputDialog(this, "Enter name to search:");
        if (keyword != null) {
            List<Subject> list = subjectService.findByName(keyword);
            loadTable(list);
        }
    }

    private void sortSubjects() {
        List<Subject> list = subjectService.getAllSubjects();
        list.sort((s1, s2) -> s1.getTenMon().compareToIgnoreCase(s2.getTenMon()));
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
                    if (p.length == 3) {
                        subjectService.addSubject(new Subject(p[0], p[1], Integer.parseInt(p[2])));
                    }
                }
                JOptionPane.showMessageDialog(this, "Import success!");
                loadTable(subjectService.getAllSubjects());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Import failed!");
            }
        }
    }

    private void exportToTXT() {
        File file = new File("outputsubject.txt");
        try (PrintWriter pw = new PrintWriter(file)) {
            for (Subject s : subjectService.getAllSubjects()) {
                pw.println(s.getMaMon() + " " + s.getTenMon() + " " + s.getSoTinChi());
            }
            JOptionPane.showMessageDialog(this, "Export success!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Export failed!");
        }
    }

    private void loadTable(List<Subject> list) {
        tableModel.setRowCount(0);
        for (Subject s : list) {
            tableModel.addRow(new Object[] { s.getMaMon(), s.getTenMon(), s.getSoTinChi() });
        }
    }
}
