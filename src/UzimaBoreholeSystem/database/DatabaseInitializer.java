package UzimaBoreholeSystem.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    

    public static void initialize() {
        createDatabase();
        createTables();
        insertDefaultData();
    }
    
    //Create database if not exists

    private static void createDatabase() {
        String url = "jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "odira2005";
        
        try (Connection conn = java.sql.DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS uzima_db");
            System.out.println("Database 'uzima_db' ready.");
            
        } catch (SQLException e) {
            System.err.println("Error creating database: " + e.getMessage());
        }
    }
    
    // Create all required tables
    private static void createTables() {
        Connection conn = DBConnection.getConnection();
        
        String[] createTableQueries = {
            // Clients table
            "CREATE TABLE IF NOT EXISTS clients (" +
            "client_id VARCHAR(20) PRIMARY KEY," +
            "name VARCHAR(100) NOT NULL," +
            "address VARCHAR(200)," +
            "phone VARCHAR(20)," +
            "email VARCHAR(100)," +
            "password VARCHAR(100)," +
            "client_category VARCHAR(20)," +
            "borehole_location VARCHAR(100)," +
            "depth_or_height INT," +
            "pump_type VARCHAR(50)," +
            "pipe_type VARCHAR(50)," +
            "pipe_diameter INT," +
            "pipe_length INT," +
            "number_of_outlets INT," +
            "drilling_type VARCHAR(50)," +
            "survey_fee DOUBLE DEFAULT 0," +
            "local_authority_fee DOUBLE DEFAULT 0," +
            "drilling_fee DOUBLE DEFAULT 0," +
            "pump_installation_fee DOUBLE DEFAULT 0," +
            "plumbing_fee DOUBLE DEFAULT 0," +
            "depth_charge DOUBLE DEFAULT 0," +
            "subtotal DOUBLE DEFAULT 0," +
            "tax_paid DOUBLE DEFAULT 0," +
            "total_cost DOUBLE DEFAULT 0," +
            "payment_status VARCHAR(20) DEFAULT 'Pending'," +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ")",
            
            // Staff table
            "CREATE TABLE IF NOT EXISTS staff (" +
            "staff_id INT PRIMARY KEY AUTO_INCREMENT," +
            "username VARCHAR(50) UNIQUE NOT NULL," +
            "password VARCHAR(100) NOT NULL" +
            ")",
            
            // Survey fees table
            "CREATE TABLE IF NOT EXISTS survey_fees (" +
            "category VARCHAR(30) PRIMARY KEY," +
            "survey_fee DOUBLE NOT NULL," +
            "local_authority_fee DOUBLE NOT NULL" +
            ")",
            
            // Drilling services table
            "CREATE TABLE IF NOT EXISTS drilling_services (" +
            "drilling_type VARCHAR(50) PRIMARY KEY," +
            "down_payment DOUBLE NOT NULL" +
            ")",
            
            // Pump installation table
            "CREATE TABLE IF NOT EXISTS pump_installation (" +
            "pump_type VARCHAR(50) PRIMARY KEY," +
            "cost DOUBLE NOT NULL" +
            ")",
            
            // Depth charges table
            "CREATE TABLE IF NOT EXISTS depth_charges (" +
            "range_from INT NOT NULL," +
            "range_to INT NOT NULL," +
            "cost_per_meter DOUBLE NOT NULL," +
            "PRIMARY KEY (range_from, range_to)" +
            ")"
        };
        
        try (Statement stmt = conn.createStatement()) {
            for (String query : createTableQueries) {
                stmt.executeUpdate(query);
            }
            System.out.println("All tables created successfully!");
            
            // Add password column to clients table if it doesn't exist (for existing databases)
            try {
                stmt.executeUpdate("ALTER TABLE clients ADD COLUMN password VARCHAR(100) AFTER email");
                System.out.println("Added password column to clients table.");
            } catch (SQLException e) {
                // Column already exists - ignore the error
            }
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    //Insert default data into lookup tables
    private static void insertDefaultData() {
        Connection conn = DBConnection.getConnection();
        
        try {
            // Insert default staff (admin)
            String checkStaff = "SELECT COUNT(*) FROM staff WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(checkStaff)) {
                ps.setString(1, "admin");
                java.sql.ResultSet rs = ps.executeQuery();
                rs.next();
                if (rs.getInt(1) == 0) {
                    String insertStaff = "INSERT INTO staff (username, password) VALUES (?, ?)";
                    try (PreparedStatement insertPs = conn.prepareStatement(insertStaff)) {
                        insertPs.setString(1, "admin");
                        insertPs.setString(2, "admin123");
                        insertPs.executeUpdate();
                        System.out.println("Default admin account created.");
                    }
                }
            }
            
            // Insert survey fees
            String checkSurvey = "SELECT COUNT(*) FROM survey_fees";
            try (PreparedStatement ps = conn.prepareStatement(checkSurvey)) {
                java.sql.ResultSet rs = ps.executeQuery();
                rs.next();
                if (rs.getInt(1) == 0) {
                    String insertSurvey = "INSERT INTO survey_fees (category, survey_fee, local_authority_fee) VALUES (?, ?, ?)";
                    try (PreparedStatement insertPs = conn.prepareStatement(insertSurvey)) {
                        insertPs.setString(1, "Industrial");
                        insertPs.setDouble(2, 20000);
                        insertPs.setDouble(3, 50000);
                        insertPs.executeUpdate();
                        
                        insertPs.setString(1, "Commercial");
                        insertPs.setDouble(2, 15000);
                        insertPs.setDouble(3, 30000);
                        insertPs.executeUpdate();
                        
                        insertPs.setString(1, "Domestic");
                        insertPs.setDouble(2, 7000);
                        insertPs.setDouble(3, 10000);
                        insertPs.executeUpdate();
                        
                        System.out.println("Survey fees data inserted.");
                    }
                }
            }
            
            // Insert drilling services
            String checkDrilling = "SELECT COUNT(*) FROM drilling_services";
            try (PreparedStatement ps = conn.prepareStatement(checkDrilling)) {
                java.sql.ResultSet rs = ps.executeQuery();
                rs.next();
                if (rs.getInt(1) == 0) {
                    String insertDrilling = "INSERT INTO drilling_services (drilling_type, down_payment) VALUES (?, ?)";
                    try (PreparedStatement insertPs = conn.prepareStatement(insertDrilling)) {
                        insertPs.setString(1, "Symmetric");
                        insertPs.setDouble(2, 130000);
                        insertPs.executeUpdate();
                        
                        insertPs.setString(1, "Core");
                        insertPs.setDouble(2, 225000);
                        insertPs.executeUpdate();
                        
                        insertPs.setString(1, "Geo-Technical");
                        insertPs.setDouble(2, 335000);
                        insertPs.executeUpdate();
                        
                        System.out.println("Drilling services data inserted.");
                    }
                }
            }
            
            // Insert pump installation
            String checkPump = "SELECT COUNT(*) FROM pump_installation";
            try (PreparedStatement ps = conn.prepareStatement(checkPump)) {
                java.sql.ResultSet rs = ps.executeQuery();
                rs.next();
                if (rs.getInt(1) == 0) {
                    String insertPump = "INSERT INTO pump_installation (pump_type, cost) VALUES (?, ?)";
                    try (PreparedStatement insertPs = conn.prepareStatement(insertPump)) {
                        insertPs.setString(1, "Submersible electric pump");
                        insertPs.setDouble(2, 90000);
                        insertPs.executeUpdate();
                        
                        insertPs.setString(1, "Solar pump");
                        insertPs.setDouble(2, 65000);
                        insertPs.executeUpdate();
                        
                        insertPs.setString(1, "Hand pump");
                        insertPs.setDouble(2, 30000);
                        insertPs.executeUpdate();
                        
                        System.out.println("Pump installation data inserted.");
                    }
                }
            }
            
            // Insert depth charges
            String checkDepth = "SELECT COUNT(*) FROM depth_charges";
            try (PreparedStatement ps = conn.prepareStatement(checkDepth)) {
                java.sql.ResultSet rs = ps.executeQuery();
                rs.next();
                if (rs.getInt(1) == 0) {
                    String insertDepth = "INSERT INTO depth_charges (range_from, range_to, cost_per_meter) VALUES (?, ?, ?)";
                    try (PreparedStatement insertPs = conn.prepareStatement(insertDepth)) {
                        insertPs.setInt(1, 1);
                        insertPs.setInt(2, 100);
                        insertPs.setDouble(3, 1000);
                        insertPs.executeUpdate();
                        
                        insertPs.setInt(1, 101);
                        insertPs.setInt(2, 200);
                        insertPs.setDouble(3, 1500);
                        insertPs.executeUpdate();
                        
                        insertPs.setInt(1, 201);
                        insertPs.setInt(2, 300);
                        insertPs.setDouble(3, 2000);
                        insertPs.executeUpdate();
                        
                        insertPs.setInt(1, 301);
                        insertPs.setInt(2, 9999);
                        insertPs.setDouble(3, 2500);
                        insertPs.executeUpdate();
                        
                        System.out.println("Depth charges data inserted.");
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error inserting default data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
