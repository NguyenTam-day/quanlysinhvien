package service;

import model.Grade;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeService {
    private final String url = "jdbc:mysql://localhost:3306/quanlysinhvien";
    private final String user = "root";
    private final String password = "Nguyenductam123!"; // Nhớ check pass

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // Lấy danh sách điểm
    public List<Grade> getGradesByClassAndSubject(String classId, String subjectId) {
        List<Grade> list = new ArrayList<>();
        // COALESCE(..., 0) để nếu chưa có điểm thì mặc định là 0
        String sql = "SELECT s.mssv, s.hoten, " +
                     "COALESCE(g.attendance, 0) as att, " +
                     "COALESCE(g.homework, 0) as hw, " +
                     "COALESCE(g.practical, 0) as prac, " +
                     "COALESCE(g.midterm, 0) as mid, " +
                     "COALESCE(g.final_exam, 0) as fin " +
                     "FROM students s " +
                     "LEFT JOIN grades g ON s.mssv = g.studentId AND g.subjectId = ? " +
                     "WHERE s.classId = ?"; 

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, subjectId);
            ps.setString(2, classId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Grade g = new Grade();
                g.setStudentId(rs.getString("mssv"));
                g.setStudentName(rs.getString("hoten"));
                g.setSubjectId(subjectId);
                
                g.setAttendance(rs.getDouble("att"));
                g.setHomework(rs.getDouble("hw"));
                g.setPractical(rs.getDouble("prac"));
                g.setMidterm(rs.getDouble("mid"));
                g.setFinalExam(rs.getDouble("fin"));
                
                list.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lưu điểm (Insert hoặc Update 5 cột)
    public void saveGrade(Grade g) {
        String sql = "INSERT INTO grades (studentId, subjectId, attendance, homework, practical, midterm, final_exam) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE " +
                     "attendance = VALUES(attendance), " +
                     "homework = VALUES(homework), " +
                     "practical = VALUES(practical), " +
                     "midterm = VALUES(midterm), " +
                     "final_exam = VALUES(final_exam)";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, g.getStudentId());
            ps.setString(2, g.getSubjectId());
            ps.setDouble(3, g.getAttendance());
            ps.setDouble(4, g.getHomework());
            ps.setDouble(5, g.getPractical());
            ps.setDouble(6, g.getMidterm());
            ps.setDouble(7, g.getFinalExam());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}