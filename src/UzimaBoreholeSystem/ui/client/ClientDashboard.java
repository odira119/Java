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
        
        setTitle("Uzima Borehole System - Client Portal");
        setSize(1150, 800);
        setMinimumSize(new Dimension(950, 650));
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
        panel.setBackground(new Color(41, 128, 185));
        panel.setPreferredSize(new Dimension(1150, 95));
        panel.setBorder(new EmptyBorder(22, 35, 22, 35));
        
        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        
        JLabel welcomeLabel = new JLabel("Welcome Back");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        welcomeLabel.setForeground(new Color(236, 240, 241));
        
        JLabel titleLabel = new JLabel("👤 " + client.getName());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        
        leftPanel.add(welcomeLabel);
        leftPanel.add(titleLabel);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setOpaque(true);
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setPreferredSize(new Dimension(110, 42));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> logout());
        
        // Add hover effect
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(192, 57, 43));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(231, 76, 60));
            }
        });
        
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(logoutButton, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(236, 240, 241)); // Light background
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));
        
        // Client Info Card
        panel.add(createClientInfoCard());
        panel.add(Box.createVerticalStrut(20));
        
        // Project Details Card
        panel.add(createProjectDetailsCard());
        panel.add(Box.createVerticalStrut(20));
        
        // Cost Breakdown Card
        panel.add(createCostBreakdownCard());
        panel.add(Box.createVerticalStrut(20));
        
        // Payment Card
        panel.add(createPaymentCard());
        
        return panel;
    }
    
    private JPanel createClientInfoCard() {
        JPanel card = new JPanel(new GridLayout(4, 2, 15, 12));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2), 
                "📋 Client Information", 
                0, 0, 
                new Font("Segoe UI", Font.BOLD, 18),
                new Color(52, 73, 94)
            ),
            new EmptyBorder(20, 25, 20, 25)
        ));
        
        addInfoRow(card, "Client ID:", client.getClientId());
        addInfoRow(card, "Name:", client.getName());
        addInfoRow(card, "Phone:", client.getPhoneNumber());
        addInfoRow(card, "Location:", client.getLocation());
        
        return card;
    }
    
    private JPanel createProjectDetailsCard() {
        JPanel card = new JPanel(new GridLayout(6, 2, 15, 12));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(46, 204, 113), 2), 
                "🔧 Project Details", 
                0, 0, 
                new Font("Segoe UI", Font.BOLD, 18),
                new Color(52, 73, 94)
            ),
            new EmptyBorder(20, 25, 20, 25)
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
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(241, 196, 15), 2), 
                "💰 Cost Breakdown", 
                0, 0, 
                new Font("Segoe UI", Font.BOLD, 18),
                new Color(52, 73, 94)
            ),
            new EmptyBorder(20, 25, 20, 25)
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
    
    private JPanel createPaymentCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(155, 89, 182), 2), 
                "💳 Payment Options", 
                0, 0, 
                new Font("Segoe UI", Font.BOLD, 18),
                new Color(52, 73, 94)
            ),
            new EmptyBorder(20, 25, 20, 25)
        ));
        
        // Payment Status
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        statusPanel.setBackground(Color.WHITE);
        JLabel statusLabel = new JLabel("Payment Status:");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        JLabel statusValue = new JLabel(client.getStatus());
        statusValue.setFont(new Font("Segoe UI", Font.BOLD, 15));
        if ("Paid".equalsIgnoreCase(client.getStatus())) {
            statusValue.setForeground(new Color(46, 204, 113));
        } else {
            statusValue.setForeground(new Color(231, 76, 60));
        }
        statusPanel.add(statusLabel);
        statusPanel.add(statusValue);
        card.add(statusPanel);
        card.add(Box.createVerticalStrut(15));
        
        // Payment Mode Selection
        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        modePanel.setBackground(Color.WHITE);
        JLabel modeLabel = new JLabel("Select Payment Mode:");
        modeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        modePanel.add(modeLabel);
        
        String[] paymentModes = {"M-Pesa", "Bank Transfer", "Cash", "Credit/Debit Card"};
        JComboBox<String> paymentModeCombo = new JComboBox<>(paymentModes);
        paymentModeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        paymentModeCombo.setPreferredSize(new Dimension(200, 35));
        modePanel.add(paymentModeCombo);
        card.add(modePanel);
        card.add(Box.createVerticalStrut(15));
        
        // Amount to Pay
        JPanel amountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        amountPanel.setBackground(Color.WHITE);
        JLabel amountLabel = new JLabel("Amount to Pay:");
        amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel amountValue = new JLabel(currencyFormat.format(client.getTotalCost()));
        amountValue.setFont(new Font("Segoe UI", Font.BOLD, 16));
        amountValue.setForeground(new Color(25, 118, 210));
        amountPanel.add(amountLabel);
        amountPanel.add(amountValue);
        card.add(amountPanel);
        card.add(Box.createVerticalStrut(20));
        
        // Make Payment Button
        JButton payButton = new JButton("Make Payment");
        payButton.setBackground(new Color(46, 204, 113));
        payButton.setForeground(Color.WHITE);
        payButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        payButton.setPreferredSize(new Dimension(200, 45));
        payButton.setMaximumSize(new Dimension(200, 45));
        payButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        payButton.setFocusPainted(false);
        payButton.setBorderPainted(false);
        payButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        payButton.addActionListener(e -> handlePayment((String) paymentModeCombo.getSelectedItem()));
        
        payButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                payButton.setBackground(new Color(39, 174, 96));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                payButton.setBackground(new Color(46, 204, 113));
            }
        });
        
        card.add(payButton);
        
        return card;
    }
    
    private void handlePayment(String paymentMode) {
        if ("Paid".equalsIgnoreCase(client.getStatus())) {
            JOptionPane.showMessageDialog(this,
                "This invoice has already been paid!",
                "Payment Complete", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String message = "Payment Details:\n\n" +
                        "Payment Mode: " + paymentMode + "\n" +
                        "Amount: " + currencyFormat.format(client.getTotalCost()) + "\n\n";
        
        switch (paymentMode) {
            case "M-Pesa":
                message += "M-Pesa Instructions:\n" +
                          "1. Go to M-Pesa menu\n" +
                          "2. Select Lipa na M-Pesa\n" +
                          "3. Select Pay Bill\n" +
                          "4. Enter Business Number: 247247\n" +
                          "5. Account Number: " + client.getClientId() + "\n" +
                          "6. Enter Amount: " + client.getTotalCost() + "\n" +
                          "7. Enter your M-Pesa PIN\n\n" +
                          "You will receive a confirmation SMS.";
                break;
            case "Bank Transfer":
                message += "Bank Transfer Details:\n" +
                          "Bank: Equity Bank\n" +
                          "Account Name: Uzima Borehole System\n" +
                          "Account Number: 0123456789\n" +
                          "Branch: Nairobi\n" +
                          "Reference: " + client.getClientId() + "\n\n" +
                          "Please use your Client ID as reference.";
                break;
            case "Cash":
                message += "Cash Payment:\n" +
                          "Please visit our office at:\n" +
                          "Uzima Borehole System\n" +
                          "Nairobi, Kenya\n" +
                          "Mon-Fri: 8:00 AM - 5:00 PM\n\n" +
                          "Bring your Client ID: " + client.getClientId();
                break;
            case "Credit/Debit Card":
                message += "Card Payment:\n" +
                          "You will be redirected to our secure payment gateway.\n" +
                          "Reference: " + client.getClientId();
                break;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            message + "\n\nProceed with payment?",
            "Confirm Payment - " + paymentMode,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Update payment status
            client.setStatus("Paid");
            client.setPaymentStatus("Paid");
            clientService.updateClient(client);
            
            JOptionPane.showMessageDialog(this,
                "Payment of " + currencyFormat.format(client.getTotalCost()) + " initiated successfully!\n\n" +
                "Payment Mode: " + paymentMode + "\n" +
                "Transaction will be processed within 24 hours.\n" +
                "You will receive a confirmation email.",
                "Payment Successful",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Refresh the dashboard
            dispose();
            new ClientDashboard(clientService.getClientById(client.getClientId())).setVisible(true);
        }
    }
    
    private void addInfoRow(JPanel panel, String label, String value) {
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblLabel.setForeground(new Color(52, 73, 94));
        
        JLabel lblValue = new JLabel(value != null ? value : "N/A");
        lblValue.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblValue.setForeground(new Color(44, 62, 80));
        
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
