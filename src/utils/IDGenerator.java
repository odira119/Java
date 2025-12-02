package utils;

import java.sql.*;

import UzimaBoreholeSystem.database.DBConnection;

public class IDGenerator {
    
    //Generate next client ID in sequence

    public static String generateClientId() {
        String sql = "SELECT client_id FROM clients ORDER BY client_id DESC LIMIT 1";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                String lastId = rs.getString("client_id");
                // Extract number from UZ-001 format
                String numberPart = lastId.substring(3);
                int nextNumber = Integer.parseInt(numberPart) + 1;
                return String.format("UZ-%03d", nextNumber);
            } else {
                // First client
                return "UZ-001";
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Fallback to timestamp-based ID if error
            return "UZ-" + System.currentTimeMillis() % 1000;
        }
    }
    
    //Validate client ID format
    public static boolean isValidClientId(String clientId) {
        if (clientId == null || clientId.isEmpty()) {
            return false;
        }
        return clientId.matches("UZ-\\d{3,}");
    }
}
