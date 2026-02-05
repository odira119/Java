package UzimaBoreholeSystem.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import UzimaBoreholeSystem.models.Client;
import UzimaBoreholeSystem.database.DBConnection;

public class ClientService {
    
    // Add new client to database
    public boolean addClient(Client client) {
        String sql = "INSERT INTO clients (client_id, name, address, phone, email, password, client_category, " +
            "borehole_location, depth_or_height, pump_type, pipe_type, pipe_diameter, " +
            "pipe_length, number_of_outlets, drilling_type, survey_fee, local_authority_fee, " +
            "drilling_fee, pump_installation_fee, plumbing_fee, depth_charge, subtotal, " +
            "tax_paid, total_cost, payment_status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, client.getClientId());
            ps.setString(2, client.getName());
            ps.setString(3, client.getAddress());
            ps.setString(4, client.getPhone());
            ps.setString(5, client.getEmail());
            ps.setString(6, client.getPassword());
            ps.setString(7, client.getClientCategory());
            ps.setString(8, client.getBoreholeLocation());
            ps.setInt(9, client.getDepthOrHeight());
            ps.setString(10, client.getPumpType());
            ps.setString(11, client.getPipeType());
            ps.setInt(12, client.getPipeDiameter());
            ps.setInt(13, client.getPipeLength());
            ps.setInt(14, client.getNumberOfOutlets());
            ps.setString(15, client.getDrillingType());
            ps.setDouble(16, client.getSurveyFee());
            ps.setDouble(17, client.getLocalAuthorityFee());
            ps.setDouble(18, client.getDrillingFee());
            ps.setDouble(19, client.getPumpInstallationFee());
            ps.setDouble(20, client.getPlumbingFee());
            ps.setDouble(21, client.getDepthCharge());
            ps.setDouble(22, client.getSubtotal());
            ps.setDouble(23, client.getTaxPaid());
            ps.setDouble(24, client.getTotalCost());
            ps.setString(25, client.getPaymentStatus());
            
            int rows = ps.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Update existing client
    public boolean updateClient(Client client) {
        String sql = "UPDATE clients SET name = ?, address = ?, phone = ?, email = ?, " +
            "client_category = ?, borehole_location = ?, depth_or_height = ?, pump_type = ?, " +
            "pipe_type = ?, pipe_diameter = ?, pipe_length = ?, number_of_outlets = ?, " +
            "drilling_type = ?, survey_fee = ?, local_authority_fee = ?, drilling_fee = ?, " +
            "pump_installation_fee = ?, plumbing_fee = ?, depth_charge = ?, subtotal = ?, " +
            "tax_paid = ?, total_cost = ?, payment_status = ? " +
            "WHERE client_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, client.getName());
            ps.setString(2, client.getAddress());
            ps.setString(3, client.getPhone());
            ps.setString(4, client.getEmail());
            ps.setString(5, client.getClientCategory());
            ps.setString(6, client.getBoreholeLocation());
            ps.setInt(7, client.getDepthOrHeight());
            ps.setString(8, client.getPumpType());
            ps.setString(9, client.getPipeType());
            ps.setInt(10, client.getPipeDiameter());
            ps.setInt(11, client.getPipeLength());
            ps.setInt(12, client.getNumberOfOutlets());
            ps.setString(13, client.getDrillingType());
            ps.setDouble(14, client.getSurveyFee());
            ps.setDouble(15, client.getLocalAuthorityFee());
            ps.setDouble(16, client.getDrillingFee());
            ps.setDouble(17, client.getPumpInstallationFee());
            ps.setDouble(18, client.getPlumbingFee());
            ps.setDouble(19, client.getDepthCharge());
            ps.setDouble(20, client.getSubtotal());
            ps.setDouble(21, client.getTaxPaid());
            ps.setDouble(22, client.getTotalCost());
            ps.setString(23, client.getPaymentStatus());
            ps.setString(24, client.getClientId());
            
            int rows = ps.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //Delete client by ID
    public boolean deleteClient(String clientId) {
        String sql = "DELETE FROM clients WHERE client_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, clientId);
            int rows = ps.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //Get client by ID
    public Client getClientById(String clientId) {
        String sql = "SELECT * FROM clients WHERE client_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, clientId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return extractClientFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Get client by email
    public Client getClientByEmail(String email) {
        String sql = "SELECT * FROM clients WHERE email = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return extractClientFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Get all clients
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients ORDER BY created_at DESC";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                clients.add(extractClientFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
    
    //Search clients by name or ID
    public List<Client> searchClients(String searchTerm) {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients WHERE client_id LIKE ? OR name LIKE ? ORDER BY created_at DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String term = "%" + searchTerm + "%";
            ps.setString(1, term);
            ps.setString(2, term);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                clients.add(extractClientFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
    
    // Get total revenue
    public double getTotalRevenue() {
        String sql = "SELECT SUM(total_cost) FROM clients";
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
    
    //Get total tax collected
    public double getTotalTax() {
        String sql = "SELECT SUM(tax_paid) FROM clients";
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
    
    //Get count of pending payments
    public int getPendingPaymentsCount() {
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
    
    //Extract client object from ResultSet
    private Client extractClientFromResultSet(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setClientId(rs.getString("client_id"));
        client.setName(rs.getString("name"));
        client.setAddress(rs.getString("address"));
        client.setPhone(rs.getString("phone"));
        client.setEmail(rs.getString("email"));
        
        // Try to get password if column exists
        try {
            client.setPassword(rs.getString("password"));
        } catch (SQLException e) {
            // Password column may not exist in older database schemas
        }
        
        client.setClientCategory(rs.getString("client_category"));
        client.setBoreholeLocation(rs.getString("borehole_location"));
        client.setDepthOrHeight(rs.getInt("depth_or_height"));
        client.setPumpType(rs.getString("pump_type"));
        client.setPipeType(rs.getString("pipe_type"));
        client.setPipeDiameter(rs.getInt("pipe_diameter"));
        client.setPipeLength(rs.getInt("pipe_length"));
        client.setNumberOfOutlets(rs.getInt("number_of_outlets"));
        client.setDrillingType(rs.getString("drilling_type"));
        client.setSurveyFee(rs.getDouble("survey_fee"));
        client.setLocalAuthorityFee(rs.getDouble("local_authority_fee"));
        client.setDrillingFee(rs.getDouble("drilling_fee"));
        client.setPumpInstallationFee(rs.getDouble("pump_installation_fee"));
        client.setPlumbingFee(rs.getDouble("plumbing_fee"));
        client.setDepthCharge(rs.getDouble("depth_charge"));
        client.setSubtotal(rs.getDouble("subtotal"));
        client.setTaxPaid(rs.getDouble("tax_paid"));
        client.setTotalCost(rs.getDouble("total_cost"));
        client.setPaymentStatus(rs.getString("payment_status"));
        client.setCreatedAt(rs.getTimestamp("created_at"));
        return client;
    }
    
    //Get survey fee by client ID
    public UzimaBoreholeSystem.models.SurveyFee getSurveyFeeByClientId(String clientId) {
        Client client = getClientById(clientId);
        if (client != null) {
            UzimaBoreholeSystem.models.SurveyFee surveyFee = new UzimaBoreholeSystem.models.SurveyFee();
            surveyFee.setCategory(client.getClientCategory());
            surveyFee.setSurveyFee(client.getSurveyFee());
            surveyFee.setLocalAuthorityFee(client.getLocalAuthorityFee());
            return surveyFee;
        }
        return null;
    }
    
    // Get drilling service by client ID
    public UzimaBoreholeSystem.models.DrillingService getDrillingServiceByClientId(String clientId) {
        Client client = getClientById(clientId);
        if (client != null) {
            UzimaBoreholeSystem.models.DrillingService drilling = new UzimaBoreholeSystem.models.DrillingService();
            drilling.setDrillingType(client.getDrillingType());
            drilling.setDownPayment(client.getDrillingFee());
            return drilling;
        }
        return null;
    }
    
    //Get depth charge by client ID
    public UzimaBoreholeSystem.models.DepthCharge getDepthChargeByClientId(String clientId) {
        Client client = getClientById(clientId);
        if (client != null) {
            UzimaBoreholeSystem.models.DepthCharge depthCharge = new UzimaBoreholeSystem.models.DepthCharge();
            depthCharge.setCostPerMeter(client.getDepthCharge());
            return depthCharge;
        }
        return null;
    }
    
    //Get pump installation by client ID
    public UzimaBoreholeSystem.models.PumpInstallation getPumpInstallationByClientId(String clientId) {
        Client client = getClientById(clientId);
        if (client != null) {
            UzimaBoreholeSystem.models.PumpInstallation pump = new UzimaBoreholeSystem.models.PumpInstallation();
            pump.setPumpType(client.getPumpType());
            pump.setCost(client.getPumpInstallationFee());
            return pump;
        }
        return null;
    }
    
    // ============ REVENUE TRACKING METHODS ============
    
    /**
     * Get total revenue from all services for all clients
     * @return Total revenue including tax
     */
    public double getTotalRevenueAllServices() {
        String sql = "SELECT SUM(total_cost) FROM clients WHERE payment_status = 'Paid'";
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
    
    /**
     * Get total revenue from drilling services only
     * @return Total revenue from drilling
     */
    public double getTotalRevenueFromDrilling() {
        String sql = "SELECT SUM(drilling_fee) FROM clients WHERE payment_status = 'Paid'";
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
    
    /**
     * Get total revenue from plumbing services only
     * @return Total revenue from plumbing
     */
    public double getTotalRevenueFromPlumbing() {
        String sql = "SELECT SUM(plumbing_fee) FROM clients WHERE payment_status = 'Paid'";
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
    
    /**
     * Get total revenue from pump installation services only
     * @return Total revenue from pump installations
     */
    public double getTotalRevenueFromPumpInstallation() {
        String sql = "SELECT SUM(pump_installation_fee) FROM clients WHERE payment_status = 'Paid'";
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
    
    /**
     * Get total revenue from depth charges only
     * @return Total revenue from depth charges
     */
    public double getTotalRevenueFromDepthCharges() {
        String sql = "SELECT SUM(depth_charge) FROM clients WHERE payment_status = 'Paid'";
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
    
    /**
     * Get total revenue from survey and local authority fees
     * @return Total revenue from survey fees and local authority fees combined
     */
    public double getTotalRevenueFromSurveyAndLocalAuthority() {
        String sql = "SELECT SUM(survey_fee + local_authority_fee) FROM clients WHERE payment_status = 'Paid'";
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
    
    /**
     * Get total revenue from survey fees only
     * @return Total revenue from survey fees
     */
    public double getTotalRevenueFromSurveyFees() {
        String sql = "SELECT SUM(survey_fee) FROM clients WHERE payment_status = 'Paid'";
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
    
    /**
     * Get total revenue from local authority fees only
     * @return Total revenue from local authority fees
     */
    public double getTotalRevenueFromLocalAuthorityFees() {
        String sql = "SELECT SUM(local_authority_fee) FROM clients WHERE payment_status = 'Paid'";
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
    
    /**
     * Get total tax collected from all clients
     * @return Total tax amount (16% of subtotal)
     */
    public double getTotalTaxCollected() {
        String sql = "SELECT SUM(tax_paid) FROM clients WHERE payment_status = 'Paid'";
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
    
    /**
     * Get revenue from a specific customer by client ID
     * @param clientId The client's unique identifier
     * @return Total revenue from this specific customer
     */
    public double getRevenueFromCustomer(String clientId) {
        String sql = "SELECT total_cost FROM clients WHERE client_id = ? AND payment_status = 'Paid'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, clientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
     * Get tax paid by a specific customer
     * @param clientId The client's unique identifier
     * @return Tax amount paid by this customer
     */
    public double getTaxPaidByCustomer(String clientId) {
        Client client = getClientById(clientId);
        if (client != null) {
            return client.getTaxPaid();
        }
        return 0;
    }
    
    /**
     * Get detailed revenue breakdown by service type
     * Returns a formatted string with all revenue categories
     * @return Formatted revenue report
     */
    public String getRevenueBreakdownReport() {
        StringBuilder report = new StringBuilder();
        report.append("========== REVENUE BREAKDOWN REPORT ==========\n\n");
        
        double surveyRevenue = getTotalRevenueFromSurveyFees();
        double localAuthorityRevenue = getTotalRevenueFromLocalAuthorityFees();
        double drillingRevenue = getTotalRevenueFromDrilling();
        double pumpRevenue = getTotalRevenueFromPumpInstallation();
        double plumbingRevenue = getTotalRevenueFromPlumbing();
        double depthChargeRevenue = getTotalRevenueFromDepthCharges();
        double totalTax = getTotalTaxCollected();
        double totalRevenue = getTotalRevenueAllServices();
        
        report.append(String.format("Survey Fees:              KSh %,.2f\n", surveyRevenue));
        report.append(String.format("Local Authority Fees:     KSh %,.2f\n", localAuthorityRevenue));
        report.append(String.format("Drilling Services:        KSh %,.2f\n", drillingRevenue));
        report.append(String.format("Pump Installation:        KSh %,.2f\n", pumpRevenue));
        report.append(String.format("Plumbing Services:        KSh %,.2f\n", plumbingRevenue));
        report.append(String.format("Depth Charges:            KSh %,.2f\n", depthChargeRevenue));
        report.append("----------------------------------------------\n");
        report.append(String.format("Subtotal (All Services):  KSh %,.2f\n", 
            surveyRevenue + localAuthorityRevenue + drillingRevenue + pumpRevenue + plumbingRevenue + depthChargeRevenue));
        report.append(String.format("Tax Collected (16%%):      KSh %,.2f\n", totalTax));
        report.append("==============================================\n");
        report.append(String.format("TOTAL REVENUE:            KSh %,.2f\n", totalRevenue));
        report.append("==============================================\n");
        
        return report.toString();
    }
    
    /**
     * Get revenue summary for a specific customer
     * @param clientId The client's unique identifier
     * @return Formatted revenue summary for the customer
     */
    public String getCustomerRevenueReport(String clientId) {
        Client client = getClientById(clientId);
        if (client == null) {
            return "Client not found.";
        }
        
        StringBuilder report = new StringBuilder();
        report.append("========== CUSTOMER REVENUE REPORT ==========\n\n");
        report.append(String.format("Client ID:        %s\n", client.getClientId()));
        report.append(String.format("Client Name:      %s\n", client.getName()));
        report.append(String.format("Payment Status:   %s\n\n", client.getPaymentStatus()));
        
        report.append("Service Breakdown:\n");
        report.append(String.format("  Survey Fee:              KSh %,.2f\n", client.getSurveyFee()));
        report.append(String.format("  Local Authority Fee:     KSh %,.2f\n", client.getLocalAuthorityFee()));
        report.append(String.format("  Drilling Service:        KSh %,.2f\n", client.getDrillingFee()));
        report.append(String.format("  Pump Installation:       KSh %,.2f\n", client.getPumpInstallationFee()));
        report.append(String.format("  Plumbing Service:        KSh %,.2f\n", client.getPlumbingFee()));
        report.append(String.format("  Depth Charge:            KSh %,.2f\n", client.getDepthCharge()));
        report.append("----------------------------------------------\n");
        report.append(String.format("Subtotal:                  KSh %,.2f\n", client.getSubtotal()));
        report.append(String.format("Tax (16%%):                 KSh %,.2f\n", client.getTaxPaid()));
        report.append("==============================================\n");
        report.append(String.format("TOTAL COST:                KSh %,.2f\n", client.getTotalCost()));
        report.append("==============================================\n");
        
        return report.toString();
    }
    
    /**
     * Get count of paid clients
     * @return Number of clients who have paid
     */
    public int getPaidClientsCount() {
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
    
    /**
     * Get average revenue per paid customer
     * @return Average revenue per customer
     */
    public double getAverageRevenuePerCustomer() {
        int paidClients = getPaidClientsCount();
        if (paidClients == 0) return 0;
        return getTotalRevenueAllServices() / paidClients;
    }
}
