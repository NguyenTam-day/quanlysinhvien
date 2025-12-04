package service;

import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private final String url = "jdbc:mysql://localhost:3306/quanlysinhvien";
    private final String user = "root";
    private final String password = "Nguyenductam123!";

    private static final String SELECT_ALL_STUDENTS_JOIN_SQL = 

        "SELECT s.*, c.class_name AS className, s.class_id AS classId " + 
        "FROM students s JOIN classrooms c ON s.class_id = c.class_id"; 

    private static final String INSERT_STUDENT_SQL = 
        "INSERT INTO students(id, name, age, gpa, class_id) VALUES (?, ?, ?, ?, ?)";
    
    private static final String UPDATE_STUDENT_SQL = 
        "UPDATE students SET name=?, age=?, gpa=?, class_id=? WHERE id=?";

    public StudentService() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL_STUDENTS_JOIN_SQL)) { 

            while (rs.next()) {
                list.add(extractStudent(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addStudent(Student s) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_STUDENT_SQL)) {

            ps.setString(1, s.getId());
            ps.setString(2, s.getName());
            ps.setInt(3, s.getAge());
            ps.setDouble(4, s.getGpa());

            ps.setString(5, s.getClassId()); 
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStudent(String id, Student s) {

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_STUDENT_SQL)) {

            ps.setString(1, s.getName());
            ps.setInt(2, s.getAge());
            ps.setDouble(3, s.getGpa());

            ps.setString(4, s.getClassId()); 
            ps.setString(5, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(String id) {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> searchByName(String keyword) {
        List<Student> list = new ArrayList<>();

        String sql = SELECT_ALL_STUDENTS_JOIN_SQL + " WHERE s.name LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractStudent(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Student> sortByName() {
        List<Student> list = new ArrayList<>();

        String sql = SELECT_ALL_STUDENTS_JOIN_SQL + " ORDER BY s.name ASC";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(extractStudent(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Student extractStudent(ResultSet rs) throws SQLException {

        return new Student(
            rs.getString("id"),
            rs.getString("name"),
            rs.getInt("age"),
            rs.getDouble("gpa"),
            rs.getString("classId"),   
            rs.getString("className")  
        );
    }
}