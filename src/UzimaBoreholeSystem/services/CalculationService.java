package UzimaBoreholeSystem.services;

import java.sql.*;
import UzimaBoreholeSystem.models.Client;
import UzimaBoreholeSystem.database.DBConnection;

public class CalculationService {
    
    // Calculate all fees for a client
    public void calculateAllFees(Client client) {
        calculateSurveyFees(client);
        calculateDrillingFee(client);
        calculatePumpInstallationFee(client);
        calculateDepthCharge(client);
        calculatePlumbingFee(client);
        calculateTotals(client);
    }
    
    //Calculate survey and local authority fees
    private void calculateSurveyFees(Client client) {
        String sql = "SELECT survey_fee, local_authority_fee FROM survey_fees WHERE category = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, client.getClientCategory());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                client.setSurveyFee(rs.getDouble("survey_fee"));
                client.setLocalAuthorityFee(rs.getDouble("local_authority_fee"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Calculate drilling fee
    private void calculateDrillingFee(Client client) {
        String sql = "SELECT down_payment FROM drilling_services WHERE drilling_type = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, client.getDrillingType());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                client.setDrillingFee(rs.getDouble("down_payment"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Calculate pump installation fee
    private void calculatePumpInstallationFee(Client client) {
        String sql = "SELECT cost FROM pump_installation WHERE pump_type = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, client.getPumpType());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                client.setPumpInstallationFee(rs.getDouble("cost"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Calculate depth charge
    private void calculateDepthCharge(Client client) {
        int depth = client.getDepthOrHeight();
        String sql = "SELECT cost_per_meter FROM depth_charges WHERE ? BETWEEN range_from AND range_to";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, depth);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                double costPerMeter = rs.getDouble("cost_per_meter");
                client.setDepthCharge(depth * costPerMeter);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Calculate plumbing fee
    private void calculatePlumbingFee(Client client) {
        // Base cost for plumbing service
        double baseCost = 10000;
        
        // Pipe type multiplier
        double typeMultiplier = 1.0;
        String pipeType = client.getPipeType();
        if (pipeType != null) {
            if (pipeType.equalsIgnoreCase("Basic")) {
                typeMultiplier = 1.0;
            } else if (pipeType.equalsIgnoreCase("Standard")) {
                typeMultiplier = 1.5;
            } else if (pipeType.equalsIgnoreCase("Premium")) {
                typeMultiplier = 2.0;
            }
        }
        
        // Diameter factor (cost per inch)
        double diameterCost = client.getPipeDiameter() * 500;
        
        // Length factor (cost per meter)
        double lengthCost = client.getPipeLength() * 300;
        
        // Outlet factor (cost per outlet)
        double outletCost = client.getNumberOfOutlets() * 3000;
        
        double plumbingFee = (baseCost + diameterCost + lengthCost + outletCost) * typeMultiplier;
        client.setPlumbingFee(plumbingFee);
    }
    
    //Calculate subtotal
    private void calculateTotals(Client client) {
        // Subtotal = sum of all services
        double subtotal = client.getSurveyFee() + 
                         client.getLocalAuthorityFee() + 
                         client.getDrillingFee() + 
                         client.getPumpInstallationFee() + 
                         client.getPlumbingFee() + 
                         client.getDepthCharge();
        
        client.setSubtotal(subtotal);
        
        // Tax = 16% of subtotal
        double taxPaid = subtotal * 0.16;
        client.setTaxPaid(taxPaid);
        
        // Total cost = subtotal + tax
        double totalCost = subtotal + taxPaid;
        client.setTotalCost(totalCost);
    }
}
