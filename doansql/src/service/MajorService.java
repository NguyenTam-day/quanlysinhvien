package service;

import model.Major;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MajorService {
    private final String url = "jdbc:mysql://localhost:3306/quanlysinhvien";
    private final String user = "root";
    private final String password = "Nguyenductam123!"; 

    public MajorService() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void addMajor(Major m) {
        // Cột SQL: major_id, name
        String sql = "INSERT INTO majors(major_id, major_name) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            fillPreparedStatement(ps, m); 
            ps.executeUpdate();
            System.out.println("Thêm ngành học thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Major> getAllMajors() {
        List<Major> list = new ArrayList<>();
        String sql = "SELECT * FROM majors";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(extractMajor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Major getMajorByID(String id) { 
        String sql = "SELECT * FROM majors WHERE major_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractMajor(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }

    public void updateMajor(Major m) {
        String sql = "UPDATE majors SET major_name=? WHERE major_id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getName());
            ps.setString(2, m.getId()); 
            ps.executeUpdate();
            System.out.println("Cập nhật ngành học thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeMajor(String id) { 
        String sql = "DELETE FROM majors WHERE major_id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id); // CẬP NHẬT: Dùng id
            ps.executeUpdate();
            System.out.println("Xóa ngành học thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Major extractMajor(ResultSet rs) throws SQLException {
        return new Major(
                rs.getString("major_id"),
                rs.getString("major_name")); 
    }

    private void fillPreparedStatement(PreparedStatement ps, Major m) throws SQLException {
        ps.setString(1, m.getId());
        ps.setString(2, m.getName());
    }
}