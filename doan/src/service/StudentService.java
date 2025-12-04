package service;

import model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private final String url = "jdbc:mysql://localhost:3306/quanlysinhvien";
    private final String user = "root";
    private final String password = "Nguyenductam123!"; // Password của bạn

    // SQL Queries (Đã sửa tên cột sang tiếng Việt)
    private static final String SELECT_ALL_SQL = "SELECT * FROM students";
    
    private static final String INSERT_SQL = 
        "INSERT INTO students(mssv, hoten, tuoi, gioitinh, gpa, sdt, email, diachi) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String UPDATE_SQL = 
        "UPDATE students SET hoten=?, tuoi=?, gioitinh=?, gpa=?, sdt=?, email=?, diachi=? WHERE mssv=?";
    
    private static final String DELETE_SQL = "DELETE FROM students WHERE mssv=?";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL_SQL)) {

            while (rs.next()) {
                Student s = new Student();
                // Phải lấy đúng tên cột trong database
                s.setMssv(rs.getString("mssv"));
                s.setHoten(rs.getString("hoten"));
                s.setTuoi(rs.getInt("tuoi"));
                s.setGioitinh(rs.getString("gioitinh"));
                s.setGpa(rs.getDouble("gpa"));
                s.setSdt(rs.getString("sdt"));
                s.setEmail(rs.getString("email"));
                s.setDiachi(rs.getString("diachi"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addStudent(Student s) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setString(1, s.getMssv());
            ps.setString(2, s.getHoten());
            ps.setInt(3, s.getTuoi());
            ps.setString(4, s.getGioitinh());
            ps.setDouble(5, s.getGpa());
            ps.setString(6, s.getSdt());
            ps.setString(7, s.getEmail());
            ps.setString(8, s.getDiachi());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStudent(Student s) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, s.getHoten());
            ps.setInt(2, s.getTuoi());
            ps.setString(3, s.getGioitinh());
            ps.setDouble(4, s.getGpa());
            ps.setString(5, s.getSdt());
            ps.setString(6, s.getEmail());
            ps.setString(7, s.getDiachi());
            ps.setString(8, s.getMssv()); // Điều kiện WHERE
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(String mssv) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
            ps.setString(1, mssv);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public java.util.List<String> getAllClasses() {
        java.util.List<String> list = new java.util.ArrayList<>();
        // Lấy các mã lớp không trùng nhau từ bảng students
        String sql = "SELECT DISTINCT classId FROM students WHERE classId IS NOT NULL AND classId <> ''";

        try (java.sql.Connection conn = getConnection();
             java.sql.Statement st = conn.createStatement();
             java.sql.ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(rs.getString("classId"));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}