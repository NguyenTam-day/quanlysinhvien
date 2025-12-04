package service;

import model.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectService {

    private final String url = "jdbc:mysql://localhost:3306/quanlysinhvien";
    private final String user = "root";
    private final String password = "Nguyenductam123!";

    private static final String SELECT_ALL_SUBJECTS_SQL =
            "SELECT * FROM subjects";

    private static final String INSERT_SUBJECT_SQL =
            "INSERT INTO subjects(subjectId, subjectName, credit, teacherName, department) " +
            "VALUES (?, ?, ?, ?, ?)";

    private static final String UPDATE_SUBJECT_SQL =
            "UPDATE subjects SET subjectName=?, credit=?, teacherName=?, department=? " +
            "WHERE subjectId=?";

    private static final String DELETE_SUBJECT_SQL =
            "DELETE FROM subjects WHERE subjectId=?";

    public SubjectService() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // load driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // ==================== LẤY DANH SÁCH MÔN HỌC ====================
    public List<Subject> findAll() {
        List<Subject> list = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL_SUBJECTS_SQL)) {

            while (rs.next()) {
                list.add(extractSubject(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ==================== THÊM MÔN HỌC ====================
    public void addSubject(Subject s) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SUBJECT_SQL)) {

            ps.setString(1, s.getSubjectId());
            ps.setString(2, s.getSubjectName());
            ps.setInt(3, s.getCredit());
            ps.setString(4, s.getTeacherName());
            ps.setString(5, s.getDepartment());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==================== SỬA MÔN HỌC ====================
    public void updateSubject(String id, Subject s) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SUBJECT_SQL)) {

            ps.setString(1, s.getSubjectName());
            ps.setInt(2, s.getCredit());
            ps.setString(3, s.getTeacherName());
            ps.setString(4, s.getDepartment());
            ps.setString(5, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==================== XÓA MÔN HỌC ====================
    public void deleteSubject(String id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SUBJECT_SQL)) {

            ps.setString(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==================== TÌM THEO TÊN ====================
    public List<Subject> searchByName(String keyword) {
        List<Subject> list = new ArrayList<>();

        String sql = SELECT_ALL_SUBJECTS_SQL + " WHERE subjectName LIKE ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractSubject(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==================== SẮP XẾP THEO TÊN ====================
    public List<Subject> sortByName() {
        List<Subject> list = new ArrayList<>();

        String sql = SELECT_ALL_SUBJECTS_SQL + " ORDER BY subjectName ASC";

        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(extractSubject(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==================== HÀM TẠO OBJECT SUBJECT ====================
    private Subject extractSubject(ResultSet rs) throws SQLException {
        return new Subject(
                rs.getString("subjectId"),
                rs.getString("subjectName"),
                rs.getInt("credit"),
                rs.getString("teacherName"),
                rs.getString("department")
        );
    }
}
