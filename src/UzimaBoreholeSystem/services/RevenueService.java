package UzimaBoreholeSystem.services;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import UzimaBoreholeSystem.database.DBConnection;

public class RevenueService {

    public Map<String, Double> getRevenueByServiceType() {
        Map<String, Double> revenueMap = new HashMap<>();
        
        String sql = "SELECT " +
                    "SUM(survey_fee) as survey_revenue, " +
                    "SUM(local_authority_fee) as local_authority_revenue, " +
                    "SUM(drilling_fee) as drilling_revenue, " +
                    "SUM(pump_installation_fee) as pump_revenue, " +
                    "SUM(plumbing_fee) as plumbing_revenue, " +
                    "SUM(depth_charge) as depth_revenue, " +
                    "SUM(tax_paid) as tax_collected, " +
                    "SUM(total_cost) as total_revenue " +
                    "FROM clients WHERE payment_status = 'Paid'";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                revenueMap.put("Survey Fees", rs.getDouble("survey_revenue"));
                revenueMap.put("Local Authority Fees", rs.getDouble("local_authority_revenue"));
                revenueMap.put("Drilling Services", rs.getDouble("drilling_revenue"));
                revenueMap.put("Pump Installation", rs.getDouble("pump_revenue"));
                revenueMap.put("Plumbing Services", rs.getDouble("plumbing_revenue"));
                revenueMap.put("Depth Charges", rs.getDouble("depth_revenue"));
                revenueMap.put("Tax Collected", rs.getDouble("tax_collected"));
                revenueMap.put("Total Revenue", rs.getDouble("total_revenue"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return revenueMap;
    }
    
    public Map<String, Double> getRevenueByClientCategory() {
        Map<String, Double> categoryRevenue = new HashMap<>();

        String sql = "SELECT client_category, SUM(total_cost) as revenue " +
                    "FROM clients WHERE payment_status = 'Paid' " +
                    "GROUP BY client_category";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String category = rs.getString("client_category");
                double revenue = rs.getDouble("revenue");
                categoryRevenue.put(category, revenue);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return categoryRevenue;
    }
    
    public Map<String, Double> getRevenueByDrillingType() {
        Map<String, Double> drillingRevenue = new HashMap<>();
        
        String sql = "SELECT drilling_type, SUM(drilling_fee) as revenue " +
                    "FROM clients WHERE payment_status = 'Paid' " +
                    "GROUP BY drilling_type";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String type = rs.getString("drilling_type");
                double revenue = rs.getDouble("revenue");
                drillingRevenue.put(type, revenue);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return drillingRevenue;
    }
    
    public Map<String, Double> getRevenueByPumpType() {
        Map<String, Double> pumpRevenue = new HashMap<>();
        
        String sql = "SELECT pump_type, SUM(pump_installation_fee) as revenue " +
                    "FROM clients WHERE payment_status = 'Paid' " +
                    "GROUP BY pump_type";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String type = rs.getString("pump_type");
                double revenue = rs.getDouble("revenue");
                pumpRevenue.put(type, revenue);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return pumpRevenue;
    }
    
    public String generateComprehensiveReport() {
        StringBuilder report = new StringBuilder();
        
        report.append("╔════════════════════════════════════════════════════════════╗\n");
        report.append("║       UZIMA BOREHOLE SYSTEM - REVENUE REPORT              ║\n");
        report.append("╚════════════════════════════════════════════════════════════╝\n\n");
        
        // Service Type Revenue
        Map<String, Double> serviceRevenue = getRevenueByServiceType();
        report.append("1. REVENUE BY SERVICE TYPE\n");
        report.append("─────────────────────────────────────────────────────────────\n");
        report.append(String.format("   Survey Fees:              KSh %,15.2f\n", 
            serviceRevenue.getOrDefault("Survey Fees", 0.0)));
        report.append(String.format("   Local Authority Fees:     KSh %,15.2f\n", 
            serviceRevenue.getOrDefault("Local Authority Fees", 0.0)));
        report.append(String.format("   Drilling Services:        KSh %,15.2f\n", 
            serviceRevenue.getOrDefault("Drilling Services", 0.0)));
        report.append(String.format("   Pump Installation:        KSh %,15.2f\n", 
            serviceRevenue.getOrDefault("Pump Installation", 0.0)));
        report.append(String.format("   Plumbing Services:        KSh %,15.2f\n", 
            serviceRevenue.getOrDefault("Plumbing Services", 0.0)));
        report.append(String.format("   Depth Charges:            KSh %,15.2f\n", 
            serviceRevenue.getOrDefault("Depth Charges", 0.0)));
        report.append("   ─────────────────────────────────────────────────────────\n");
        
        double subtotal = serviceRevenue.getOrDefault("Survey Fees", 0.0) +
                         serviceRevenue.getOrDefault("Local Authority Fees", 0.0) +
                         serviceRevenue.getOrDefault("Drilling Services", 0.0) +
                         serviceRevenue.getOrDefault("Pump Installation", 0.0) +
                         serviceRevenue.getOrDefault("Plumbing Services", 0.0) +
                         serviceRevenue.getOrDefault("Depth Charges", 0.0);
        
        report.append(String.format("   Subtotal:                 KSh %,15.2f\n", subtotal));
        report.append(String.format("   Tax Collected (16%%):      KSh %,15.2f\n", 
            serviceRevenue.getOrDefault("Tax Collected", 0.0)));
        report.append("   ═════════════════════════════════════════════════════════\n");
        report.append(String.format("   TOTAL REVENUE:            KSh %,15.2f\n\n", 
            serviceRevenue.getOrDefault("Total Revenue", 0.0)));
        
        // Category Revenue
        Map<String, Double> categoryRevenue = getRevenueByClientCategory();
        report.append("2. REVENUE BY CLIENT CATEGORY\n");
        report.append("─────────────────────────────────────────────────────────────\n");
        for (Map.Entry<String, Double> entry : categoryRevenue.entrySet()) {
            report.append(String.format("   %-25s KSh %,15.2f\n", 
                entry.getKey() + ":", entry.getValue()));
        }
        report.append("\n");
        
        // Drilling Type Revenue
        Map<String, Double> drillingRevenue = getRevenueByDrillingType();
        report.append("3. REVENUE BY DRILLING TYPE\n");
        report.append("─────────────────────────────────────────────────────────────\n");
        for (Map.Entry<String, Double> entry : drillingRevenue.entrySet()) {
            report.append(String.format("   %-25s KSh %,15.2f\n", 
                entry.getKey() + ":", entry.getValue()));
        }
        report.append("\n");
        
        // Pump Type Revenue
        Map<String, Double> pumpRevenue = getRevenueByPumpType();
        report.append("4. REVENUE BY PUMP TYPE\n");
        report.append("─────────────────────────────────────────────────────────────\n");
        for (Map.Entry<String, Double> entry : pumpRevenue.entrySet()) {
            report.append(String.format("   %-25s KSh %,15.2f\n", 
                entry.getKey() + ":", entry.getValue()));
        }
        report.append("\n");
        
        // Statistics
        report.append("5. BUSINESS STATISTICS\n");
        report.append("─────────────────────────────────────────────────────────────\n");
        
        int totalClients = getTotalClientsCount();
        int paidClients = getPaidClientsCount();
        int pendingClients = getPendingClientsCount();
        double avgRevenue = paidClients > 0 ? 
            serviceRevenue.getOrDefault("Total Revenue", 0.0) / paidClients : 0;
        
        report.append(String.format("   Total Clients:            %d\n", totalClients));
        report.append(String.format("   Paid Clients:             %d\n", paidClients));
        report.append(String.format("   Pending Payments:         %d\n", pendingClients));
        report.append(String.format("   Average Revenue/Client:   KSh %,15.2f\n", avgRevenue));
        
        report.append("\n╔════════════════════════════════════════════════════════════╗\n");
        report.append("║                    END OF REPORT                           ║\n");
        report.append("╚════════════════════════════════════════════════════════════╝\n");
        
        return report.toString();
    }
    
    /**
     * Get total number of clients
     * @return Total client count
     */
    private int getTotalClientsCount() {
        String sql = "SELECT COUNT(*) FROM clients";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
     * Get count of paid clients
     * @return Number of clients who have paid
     */
    private int getPaidClientsCount() {
        String sql = "SELECT COUNT(*) FROM clients WHERE payment_status = 'Paid'";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    private int getPendingClientsCount() {
        String sql = "SELECT COUNT(*) FROM clients WHERE payment_status = 'Pending'";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public double getPendingPaymentAmount() {
        String sql = "SELECT SUM(total_cost) FROM clients WHERE payment_status = 'Pending'";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public double getMonthlyRevenue(int month, int year) {
        String sql = "SELECT SUM(total_cost) FROM clients " +
                    "WHERE payment_status = 'Paid' " +
                    "AND MONTH(created_at) = ? AND YEAR(created_at) = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, month);
            ps.setInt(2, year);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble(1);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public double getYearlyRevenue(int year) {
        String sql = "SELECT SUM(total_cost) FROM clients " +
                    "WHERE payment_status = 'Paid' " +
                    "AND YEAR(created_at) = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble(1);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
