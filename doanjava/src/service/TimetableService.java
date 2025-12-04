package service;

import model.Timetable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimetableService {
    private final String url = "jdbc:mysql://localhost:3306/quanlysinhvien";
    private final String user = "root";
    private final String password = "Nguyenductam123!"; // Nhớ kiểm tra password

    // Query lấy dữ liệu có JOIN để lấy Tên môn
    private static final String SELECT_ALL = 
        "SELECT t.*, s.subjectName FROM timetable t " +
        "LEFT JOIN subjects s ON t.subjectId = s.subjectId " +
        "ORDER BY t.dayOfWeek, t.shift";

    private static final String INSERT_SQL = 
        "INSERT INTO timetable(subjectId, className, dayOfWeek, shift, room) VALUES (?, ?, ?, ?, ?)";
    
    private static final String DELETE_SQL = "DELETE FROM timetable WHERE id=?";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public List<Timetable> findAll() {
        List<Timetable> list = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL)) {

            while (rs.next()) {
                Timetable t = new Timetable();
                t.setId(rs.getInt("id"));
                t.setSubjectId(rs.getString("subjectId"));
                t.setSubjectName(rs.getString("subjectName")); 
                t.setClassName(rs.getString("className"));
                t.setDayOfWeek(rs.getString("dayOfWeek"));
                t.setShift(rs.getString("shift"));
                t.setRoom(rs.getString("room"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addTimetable(Timetable t) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setString(1, t.getSubjectId());
            ps.setString(2, t.getClassName());
            ps.setString(3, t.getDayOfWeek());
            ps.setString(4, t.getShift());
            ps.setString(5, t.getRoom());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTimetable(int id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}