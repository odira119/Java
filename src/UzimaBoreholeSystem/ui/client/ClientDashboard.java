package UzimaBoreholeSystem.ui.client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import UzimaBoreholeSystem.services.ClientService;

import UzimaBoreholeSystem.models.*;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class ClientDashboard extends JFrame {
    private final Client client;
    private final ClientService clientService;
    private final NumberFormat currencyFormat;
    
    public ClientDashboard(Client client) {
        this.client = client;
        this.clientService = new ClientService();
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale.Builder().setLanguage("en").setRegion("KE").build());
        
        setTitle("Client Dashboard - " + client.getName());
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        
        // Content with scroll pane
        JPanel contentPanel = createContentPanel();
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(25, 118, 210));
        panel.setPreferredSize(new Dimension(900, 80));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Welcome, " + client.getName());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(211, 47, 47));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setOpaque(true);
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setPreferredSize(new Dimension(100, 35));
        logoutButton.addActionListener(e -> logout());
        
        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(logoutButton, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Client Info Card
        panel.add(createClientInfoCard());
        panel.add(Box.createVerticalStrut(20));
        
        // Project Details Card
        panel.add(createProjectDetailsCard());
        panel.add(Box.createVerticalStrut(20));
        
        // Cost Breakdown Card
        panel.add(createCostBreakdownCard());
        
        return panel;
    }
    
    private JPanel createClientInfoCard() {
        JPanel card = new JPanel(new GridLayout(4, 2, 10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                "Client Information", 0, 0, new Font("Arial", Font.BOLD, 16)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        addInfoRow(card, "Client ID:", client.getClientId());
        addInfoRow(card, "Name:", client.getName());
        addInfoRow(card, "Phone:", client.getPhoneNumber());
        addInfoRow(card, "Location:", client.getLocation());
        
        return card;
    }
    
    private JPanel createProjectDetailsCard() {
        JPanel card = new JPanel(new GridLayout(6, 2, 10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                "Project Details", 0, 0, new Font("Arial", Font.BOLD, 16)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        addInfoRow(card, "Category:", client.getCategory());
        addInfoRow(card, "Drilling Type:", client.getDrillingType());
        addInfoRow(card, "Depth (meters):", String.valueOf(client.getDepth()));
        addInfoRow(card, "Pump Type:", client.getPumpType());
        addInfoRow(card, "Plumbing Specs:", client.getPlumbingSpecs());
        addInfoRow(card, "Status:", client.getStatus());
        
        return card;
    }
    
    private JPanel createCostBreakdownCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                "Cost Breakdown", 0, 0, new Font("Arial", Font.BOLD, 16)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        // Fetch cost details
        SurveyFee surveyFee = clientService.getSurveyFeeByClientId(client.getClientId());
        DrillingService drilling = clientService.getDrillingServiceByClientId(client.getClientId());
        DepthCharge depthCharge = clientService.getDepthChargeByClientId(client.getClientId());
        PumpInstallation pump = clientService.getPumpInstallationByClientId(client.getClientId());
        
        JPanel costsPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        costsPanel.setBackground(Color.WHITE);
        
        if (surveyFee != null) {
            addCostRow(costsPanel, "Survey Fee:", surveyFee.getAmount());
        }
        
        // Add local authority fee
        addCostRow(costsPanel, "Local Authority Fee:", client.getLocalAuthorityFee());
        
        if (drilling != null) {
            addCostRow(costsPanel, "Drilling Fee:", drilling.getAmount());
        }
        
        if (depthCharge != null) {
            addCostRow(costsPanel, "Depth Charge:", depthCharge.getAmount());
        }
        
        if (pump != null) {
            addCostRow(costsPanel, "Pump Installation:", pump.getAmount());
            addCostRow(costsPanel, "Plumbing Fee:", pump.getPlumbingFee());
        }
        
        card.add(costsPanel);
        card.add(Box.createVerticalStrut(10));
        
        // Subtotal, Tax, and Total
        JPanel summaryPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        summaryPanel.setBackground(Color.WHITE);
        summaryPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        // Subtotal
        JLabel subtotalLabel = new JLabel("Subtotal:");
        subtotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel subtotalValue = new JLabel(currencyFormat.format(client.getSubtotal()));
        subtotalValue.setFont(new Font("Arial", Font.BOLD, 14));
        summaryPanel.add(subtotalLabel);
        summaryPanel.add(subtotalValue);
        
        // Tax (16%)
        JLabel taxLabel = new JLabel("Tax (16%):");
        taxLabel.setFont(new Font("Arial", Font.BOLD, 14));
        taxLabel.setForeground(new Color(243, 156, 18));
        JLabel taxValue = new JLabel(currencyFormat.format(client.getTaxPaid()));
        taxValue.setFont(new Font("Arial", Font.BOLD, 14));
        taxValue.setForeground(new Color(243, 156, 18));
        summaryPanel.add(taxLabel);
        summaryPanel.add(taxValue);
        
        // Total
        JLabel totalLabel = new JLabel("Total Cost:");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(new Color(25, 118, 210));
        JLabel totalValue = new JLabel(currencyFormat.format(client.getTotalCost()));
        totalValue.setFont(new Font("Arial", Font.BOLD, 16));
        totalValue.setForeground(new Color(25, 118, 210));
        summaryPanel.add(totalLabel);
        summaryPanel.add(totalValue);
        
        card.add(summaryPanel);
        
        return card;
    }
    
    private void addInfoRow(JPanel panel, String label, String value) {
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Arial", Font.BOLD, 13));
        
        JLabel lblValue = new JLabel(value != null ? value : "N/A");
        lblValue.setFont(new Font("Arial", Font.PLAIN, 13));
        
        panel.add(lblLabel);
        panel.add(lblValue);
    }
    
    private void addCostRow(JPanel panel, String label, double amount) {
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JLabel lblValue = new JLabel(currencyFormat.format(amount));
        lblValue.setFont(new Font("Arial", Font.PLAIN, 13));
        
        panel.add(lblLabel);
        panel.add(lblValue);
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", 
            "Confirm Logout", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                UzimaBoreholeSystem.ui.login.LoginUI loginUI = new UzimaBoreholeSystem.ui.login.LoginUI();
                loginUI.setVisible(true);
            });
        }
    }
}
