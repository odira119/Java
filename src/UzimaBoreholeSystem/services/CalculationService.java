package UzimaBoreholeSystem.services;

import java.sql.*;
import UzimaBoreholeSystem.models.Client;
import UzimaBoreholeSystem.database.DBConnection;

/**
 * Calculation Service - Handles all fee calculations for clients
 * 
 * This service calculates:
 * 1. Survey fees based on client category
 * 2. Local authority fees based on client category
 * 3. Drilling fees based on drilling type
 * 4. Pump installation fees based on pump type
 * 5. Depth charges based on borehole depth
 * 6. Plumbing fees based on pipe specifications
 * 7. Tax (16% of subtotal) - Government VAT requirement
 * 8. Total cost (subtotal + tax)
 */
public class CalculationService {
    
    // Tax rate constant - 16% VAT as per requirements
    public static final double TAX_RATE = 0.16;
    
    /**
     * Calculate all fees for a client including tax
     * This is the main method that orchestrates all calculations
     * @param client The client object to calculate fees for
     */
    public void calculateAllFees(Client client) {
        calculateSurveyFees(client);
        calculateDrillingFee(client);
        calculatePumpInstallationFee(client);
        calculateDepthCharge(client);
        calculatePlumbingFee(client);
        calculateTotals(client);  // This includes tax calculation
    }
    
    /**
     * Calculate survey and local authority fees
     * These fees are based on the client category (Industrial, Commercial, Domestic)
     * @param client The client object
     */
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
    
    /**
     * Calculate plumbing fee based on pipe specifications
     * Factors considered:
     * - Base cost
     * - Pipe type (Basic/Standard/Premium)
     * - Pipe diameter
     * - Pipe length
     * - Number of outlets
     * @param client The client object
     */
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
    
    /**
     * Calculate subtotal, tax, and total cost
     * 
     * Calculation breakdown:
     * 1. Subtotal = Sum of all service fees (survey + local authority + drilling + 
     *               pump + plumbing + depth charges)
     * 2. Tax = 16% of subtotal (Government VAT)
     * 3. Total Cost = Subtotal + Tax
     * 
     * This ensures every client is charged 16% tax on the total cost of services offered
     * 
     * @param client The client object
     */
    private void calculateTotals(Client client) {
        // Subtotal = sum of all services including local authority fees
        double subtotal = client.getSurveyFee() + 
                         client.getLocalAuthorityFee() +  // Local authority fee included in subtotal
                         client.getDrillingFee() + 
                         client.getPumpInstallationFee() + 
                         client.getPlumbingFee() + 
                         client.getDepthCharge();
        
        client.setSubtotal(subtotal);
        
        // Tax = 16% of subtotal (as per requirements)
        double taxPaid = subtotal * TAX_RATE;
        client.setTaxPaid(taxPaid);
        
        // Total cost = subtotal + tax
        double totalCost = subtotal + taxPaid;
        client.setTotalCost(totalCost);
    }
    
    /**
     * Get the current tax rate
     * @return Tax rate as a decimal (0.16 = 16%)
     */
    public double getTaxRate() {
        return TAX_RATE;
    }
    
    /**
     * Get the tax rate as a percentage
     * @return Tax rate as percentage (16.0 = 16%)
     */
    public double getTaxRatePercentage() {
        return TAX_RATE * 100;
    }
}
