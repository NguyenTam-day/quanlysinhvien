package view;

import javax.swing.*;

import model.Student;

import java.awt.*;
import java.awt.event.*;
import service.StudentService;

public class StudentFrame extends JFrame {
    private StudentService studentService;
    private JTextField txtId, txtName, txtAge, txtGpa, txtClass;
    private JTextArea txtDisplay;
    private JButton btnAdd, btnShow;

    public StudentFrame() {
        studentService = new StudentService("students.csv");

        setTitle("Student Manager");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Giao diện
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.add(new JLabel("ID:"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Age:"));
        txtAge = new JTextField();
        inputPanel.add(txtAge);

        inputPanel.add(new JLabel("GPA:"));
        txtGpa = new JTextField();
        inputPanel.add(txtGpa);

        inputPanel.add(new JLabel("Class:"));
        txtClass = new JTextField();
        inputPanel.add(txtClass);

        btnAdd = new JButton("Add Student");
        btnShow = new JButton("Show All");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnShow);

        txtDisplay = new JTextArea();
        txtDisplay.setEditable(false);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(new JScrollPane(txtDisplay), BorderLayout.SOUTH);

        // Sự kiện nút
        btnAdd.addActionListener(e -> addStudent());
        btnShow.addActionListener(e -> showStudents());
    }

    private void addStudent() {
        try {
            String id = txtId.getText();
            String name = txtName.getText();
            int age = Integer.parseInt(txtAge.getText());
            double gpa = Double.parseDouble(txtGpa.getText());
            String className = txtClass.getText();
            Student student = new Student(id, name, age, gpa, className);
            studentService.addStudent(student);
            JOptionPane.showMessageDialog(this, "Added successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    private void showStudents() {
        txtDisplay.setText(studentService.getAllStudentsAsString());
    }
}
