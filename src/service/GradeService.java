package service;

import model.Grade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeService {

    private final String url = "jdbc:mysql://localhost:3306/quanlysinhvien";
    private final String user = "root";
    private final String password = "Nguyenductam123!";

    public GradeService() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // load driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public List<Grade> getAllGrades() {
        List<Grade> list = new ArrayList<>();
        String sql = "SELECT * FROM grades";
        try (Connection conn = getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(extractGrade(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addGrade(Grade g) {
        String sql = "INSERT INTO grades(student_id, subject_id, diemCC, diemBT, diemGK, diemTH, diemCK) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            fillPreparedStatement(ps, g);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGrade(String studentId, String subjectId, double cc, double bt, double gk, double th, double ck) {
        String sql = "UPDATE grades SET diemCC=?, diemBT=?, diemGK=?, diemTH=?, diemCK=? " +
                "WHERE student_id=? AND subject_id=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, cc);
            ps.setDouble(2, bt);
            ps.setDouble(3, gk);
            ps.setDouble(4, th);
            ps.setDouble(5, ck);
            ps.setString(6, studentId);
            ps.setString(7, subjectId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeGrade(String studentId, String subjectId) {
        String sql = "DELETE FROM grades WHERE student_id=? AND subject_id=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentId);
            ps.setString(2, subjectId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ===== Tìm kiếm theo studentId =====
    public List<Grade> findByStudentId(String studentId) {
        List<Grade> list = new ArrayList<>();
        String sql = "SELECT * FROM grades WHERE student_id LIKE ?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + studentId + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractGrade(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== Tìm kiếm theo subjectId =====
    public List<Grade> findBySubjectId(String subjectId) {
        List<Grade> list = new ArrayList<>();
        String sql = "SELECT * FROM grades WHERE subject_id LIKE ?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + subjectId + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractGrade(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== Sort theo studentId =====
    public List<Grade> sortByStudentId() {
        List<Grade> list = new ArrayList<>();
        String sql = "SELECT * FROM grades ORDER BY student_id ASC";
        try (Connection conn = getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(extractGrade(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== Sort theo subjectId =====
    public List<Grade> sortBySubjectId() {
        List<Grade> list = new ArrayList<>();
        String sql = "SELECT * FROM grades ORDER BY subject_id ASC";
        try (Connection conn = getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(extractGrade(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== Helper =====
    private Grade extractGrade(ResultSet rs) throws SQLException {
        return new Grade(
                rs.getString("student_id"),
                rs.getString("subject_id"),
                rs.getDouble("diemCC"),
                rs.getDouble("diemBT"),
                rs.getDouble("diemGK"),
                rs.getDouble("diemTH"),
                rs.getDouble("diemCK"));
    }

    private void fillPreparedStatement(PreparedStatement ps, Grade g) throws SQLException {
        ps.setString(1, g.getStudentId());
        ps.setString(2, g.getSubjectId());
        ps.setDouble(3, g.getDiemChuyenCan());
        ps.setDouble(4, g.getDiemBaiTap());
        ps.setDouble(5, g.getDiemGiuaKy());
        ps.setDouble(6, g.getDiemThucHanh());
        ps.setDouble(7, g.getDiemCuoiKy());
    }
}
