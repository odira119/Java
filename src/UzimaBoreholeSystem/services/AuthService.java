package UzimaBoreholeSystem.services;

import java.sql.*;
import UzimaBoreholeSystem.models.Client;
import UzimaBoreholeSystem.database.DBConnection;

public class AuthService {
    private ClientService clientService = new ClientService();
    
    //Register new client with basic information
    public boolean registerClient(String clientId, String name, String phone, String email, String password) {
        
        Client client = new Client();
        client.setClientId(clientId);
        client.setName(name);
        client.setPhone(phone);
        client.setEmail(email);
        client.setPaymentStatus("Pending");
        
        return clientService.addClient(client);
    }
    
    // Authenticate client using client ID
    public Client authenticateClient(String clientId) {
        return clientService.getClientById(clientId);
    }
    
    //Authenticate client using client ID and pyone number
    public Client authenticateClient(String clientId, String phoneNumber) {
        Client client = clientService.getClientById(clientId);
        if (client != null && client.getPhone().equals(phoneNumber)) {
            return client;
        }
        return null;
    }
    
    //Check if client ID exists
    public boolean clientIdExists(String clientId) {
        String sql = "SELECT COUNT(*) FROM clients WHERE client_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, clientId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
