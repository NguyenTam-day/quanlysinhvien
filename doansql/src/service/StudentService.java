package service;

import model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private final String url = "jdbc:mysql://localhost:3306/quanlysinhvien";
    private final String user = "root";
    private final String password = "Nguyenductam123!";

    private static final String SELECT_ALL_STUDENTS_SQL =
            "SELECT mssv, hoten, tuoi, gioitinh, sdt,diachi, email, gpa FROM students";

    private static final String INSERT_STUDENT_SQL =
            "INSERT INTO students(mssv, hoten, tuoi, gioitinh, sdt, diachi, email, gpa) VALUES (?,?,?,?,?,?,?,?)";

    private static final String UPDATE_STUDENT_SQL =
            "UPDATE students SET hoten=?, tuoi=?, gioitinh=?, sdt=?, diachi=?, email=?, gpa=? WHERE mssv=?";

    private static final String DELETE_STUDENT_SQL =
            "DELETE FROM students WHERE mssv=?";

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

    // ================== FIND ALL ==================
    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL_STUDENTS_SQL)) {

            while (rs.next()) {
                list.add(extractStudent(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================== FIND BY ID ==================
    public Student findById(String mssv) {
        String sql = "SELECT * FROM students WHERE mssv=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, mssv);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractStudent(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================== ADD STUDENT ==================
    public boolean addStudent(Student s) {
        if (findById(s.getMssv()) != null) {
            return false; // MSSV đã tồn tại
        }

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_STUDENT_SQL)) {

            ps.setString(1, s.getMssv());
            ps.setString(2, s.getHoten());
            ps.setInt(3, s.getTuoi());
            ps.setString(4, s.getGioitinh());
            ps.setInt(5, s.getSdt()); 
            ps.setString(6, s.getDiachi());
            ps.setString(7, s.getEmail());
            ps.setDouble(8, s.getGpa());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================== UPDATE STUDENT ==================
    public boolean updateStudent(Student s) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_STUDENT_SQL)) {

            ps.setString(1, s.getHoten());
            ps.setInt(2, s.getTuoi());
            ps.setString(3, s.getGioitinh());
            ps.setInt(4, s.getSdt());
            ps.setString(5, s.getEmail());
            ps.setDouble(6, s.getGpa());
            ps.setString(7, s.getMssv());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================== DELETE STUDENT ==================
    public boolean deleteStudent(String id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_STUDENT_SQL)) {

            ps.setString(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================== SEARCH BY NAME ==================
    public List<Student> searchByName(String keyword) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE hoten LIKE ?";
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

    // ================== SORT BY NAME ==================
    public List<Student> sortByName() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY hoten ASC";
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

    // ================== HELPER METHOD ==================
    private Student extractStudent(ResultSet rs) throws SQLException {
        return new Student(
                rs.getString("mssv"),
                rs.getString("hoten"),
                rs.getInt("tuoi"),
                rs.getString("gioitinh"),
                rs.getInt("sdt"),
                rs.getString("diachi"),
                rs.getString("email"),
                rs.getDouble("gpa")
        );
    }
}
