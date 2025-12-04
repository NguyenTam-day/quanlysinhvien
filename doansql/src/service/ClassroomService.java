package service;

import model.Classroom;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassroomService {

    private final String url = "jdbc:mysql://localhost:3306/quanlysinhvien";
    private final String user = "root";
    private final String password = "Nguyenductam123!"; 

    public ClassroomService() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }


    public void addClassroom(Classroom c) {

        String sql = "INSERT INTO classrooms(class_id, class_name, major_id) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            
            fillPreparedStatement(ps, c); 
            ps.executeUpdate();
            System.out.println("Thêm lớp học thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Classroom> getAllClassrooms() {
        List<Classroom> list = new ArrayList<>();
        String sql = "SELECT * FROM classrooms";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(extractClassroom(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Classroom getClassroomByID(String id) { 
        String sql = "SELECT * FROM classrooms WHERE classroom_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id); 
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractClassroom(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }

    public void updateClassroom(Classroom c) {
        String sql = "UPDATE classrooms SET class_name=?, major_id=? WHERE class_id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getMajorId()); 
            ps.setString(3, c.getId());      
            ps.executeUpdate();
            System.out.println("Cập nhật lớp học thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeClassroom(String id) { 
        String sql = "DELETE FROM classrooms WHERE class_id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id); 
            ps.executeUpdate();
            System.out.println("Xóa lớp học thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Classroom extractClassroom(ResultSet rs) throws SQLException {
        return new Classroom(
                rs.getString("class_id"), 
                rs.getString("class_name"),
                rs.getString("major_id"));
    }

    private void fillPreparedStatement(PreparedStatement ps, Classroom c) throws SQLException {
        ps.setString(1, c.getId());      
        ps.setString(2, c.getName());
        ps.setString(3, c.getMajorId()); 
    }
}