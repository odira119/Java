package UzimaBoreholeSystem.services;

import java.sql.*;
import UzimaBoreholeSystem.models.Staff;
import UzimaBoreholeSystem.database.DBConnection;
public class StaffService {
    
    // Authenticate staff member
    public Staff authenticateStaff(String username, String password) {
        String sql = "SELECT * FROM staff WHERE username = ? AND password = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffId(rs.getInt("staff_id"));
                staff.setUsername(rs.getString("username"));
                staff.setPassword(rs.getString("password"));
                return staff;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //Add new staff member
    public boolean addStaff(Staff staff) {
        String sql = "INSERT INTO staff (username, password) VALUES (?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, staff.getUsername());
            ps.setString(2, staff.getPassword());
            
            int rows = ps.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
