package UzimaBoreholeSystem.ui.client;

import UzimaBoreholeSystem.utils.IDGenerator;
import UzimaBoreholeSystem.models.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import UzimaBoreholeSystem.services.CalculationService;
import UzimaBoreholeSystem.services.ClientService;
import UzimaBoreholeSystem.ui.login.LoginUI;

import java.awt.*;

public class ClientRegistrationForm extends JFrame {
    private final ClientService clientService;
    private final CalculationService calculationService;
    
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField locationField;
    private JComboBox<String> categoryCombo;
    private JComboBox<String> drillingTypeCombo;
    private JSpinner depthSpinner;
    private JComboBox<String> pumpTypeCombo;
    private JComboBox<String> plumbingCombo;
    private JSpinner pipeDiameterSpinner;
    private JSpinner pipeLengthSpinner;
    private JSpinner outletsSpinner;
    
    public ClientRegistrationForm() {
        this.clientService = new ClientService();
        this.calculationService = new CalculationService();
        
        setTitle("Client Registration - Borehole Details");
        setSize(700, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(25, 118, 210));
        headerPanel.setPreferredSize(new Dimension(700, 70));
        
        JLabel titleLabel = new JLabel("Register & Submit Borehole Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // Form panel with scroll
        JPanel formPanel = createFormPanel();
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(30, 50, 30, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Section: Personal Information
        addSectionTitle(panel, gbc, row++, "Personal Information");
        
        // Name
        addFormField(panel, gbc, row++, "Full Name:");
        nameField = new JTextField(30);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        
        // Phone
        addFormField(panel, gbc, row++, "Phone Number:");
        phoneField = new JTextField(30);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);
        
        // Email
        addFormField(panel, gbc, row++, "Email Address:");
        emailField = new JTextField(30);
        gbc.gridx = 1;
        panel.add(emailField, gbc);
        
        // Location
        addFormField(panel, gbc, row++, "Borehole Location:");
        locationField = new JTextField(30);
        gbc.gridx = 1;
        panel.add(locationField, gbc);
        
        // Spacing
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        panel.add(Box.createVerticalStrut(20), gbc);
        gbc.gridwidth = 1;
        
        // Section: Borehole Specifications
        addSectionTitle(panel, gbc, row++, "Borehole Specifications");
        
        // Category
        addFormField(panel, gbc, row++, "Project Category:");
        categoryCombo = new JComboBox<>(new String[]{"Domestic", "Commercial", "Industrial"});
        gbc.gridx = 1;
        panel.add(categoryCombo, gbc);
        
        // Drilling Type
        addFormField(panel, gbc, row++, "Drilling Type:");
        drillingTypeCombo = new JComboBox<>(new String[]{"Symmetric", "Core", "Geo-Technical"});
        gbc.gridx = 1;
        panel.add(drillingTypeCombo, gbc);
        
        // Depth
        addFormField(panel, gbc, row++, "Required Depth (meters):");
        depthSpinner = new JSpinner(new SpinnerNumberModel(50, 1, 500, 10));
        gbc.gridx = 1;
        panel.add(depthSpinner, gbc);
        
        // Pump Type
        addFormField(panel, gbc, row++, "Pump Type:");
        pumpTypeCombo = new JComboBox<>(new String[]{"Submersible electric pump", "Solar pump", "Hand pump"});
        gbc.gridx = 1;
        panel.add(pumpTypeCombo, gbc);
        
        // Plumbing Specs
        addFormField(panel, gbc, row++, "Plumbing Package:");
        plumbingCombo = new JComboBox<>(new String[]{"Basic", "Standard", "Premium"});
        gbc.gridx = 1;
        panel.add(plumbingCombo, gbc);
        
        // Pipe Diameter
        addFormField(panel, gbc, row++, "Pipe Diameter (inches):");
        pipeDiameterSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        gbc.gridx = 1;
        panel.add(pipeDiameterSpinner, gbc);
        
        // Pipe Length
        addFormField(panel, gbc, row++, "Pipe Length (meters):");
        pipeLengthSpinner = new JSpinner(new SpinnerNumberModel(50, 10, 500, 10));
        gbc.gridx = 1;
        panel.add(pipeLengthSpinner, gbc);
        
        // Number of Outlets
        addFormField(panel, gbc, row++, "Number of Outlets:");
        outletsSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 20, 1));
        gbc.gridx = 1;
        panel.add(outletsSpinner, gbc);
        
        // Buttons
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 10, 10, 10);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton submitButton = new JButton("Submit Registration");
        submitButton.setBackground(new Color(76, 175, 80));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setPreferredSize(new Dimension(200, 40));
        submitButton.addActionListener(e -> handleRegistration());
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(158, 158, 158));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.addActionListener(e -> backToLogin());
        
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        
        panel.add(buttonPanel, gbc);
        
        return panel;
    }
    
    private void addSectionTitle(JPanel panel, GridBagConstraints gbc, int row, String title) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        JLabel sectionLabel = new JLabel(title);
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sectionLabel.setForeground(new Color(25, 118, 210));
        panel.add(sectionLabel, gbc);
        gbc.gridwidth = 1;
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String label) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(lbl, gbc);
    }
    
    private void handleRegistration() {
        // Validate inputs
        if (nameField.getText().trim().isEmpty() || 
            phoneField.getText().trim().isEmpty() || 
            emailField.getText().trim().isEmpty() ||
            locationField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all required fields", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Create client with generated ID
            String clientId = IDGenerator.generateClientId();
            Client client = new Client();
            client.setClientId(clientId);
            client.setName(nameField.getText().trim());
            client.setPhone(phoneField.getText().trim());
            client.setEmail(emailField.getText().trim());
            client.setAddress(locationField.getText().trim()); // Use address field
            client.setBoreholeLocation(locationField.getText().trim());
            client.setClientCategory((String) categoryCombo.getSelectedItem());
            client.setDrillingType((String) drillingTypeCombo.getSelectedItem());
            client.setDepthOrHeight((Integer) depthSpinner.getValue());
            client.setPumpType((String) pumpTypeCombo.getSelectedItem());
            client.setPipeType((String) plumbingCombo.getSelectedItem());
            client.setPipeDiameter((Integer) pipeDiameterSpinner.getValue());
            client.setPipeLength((Integer) pipeLengthSpinner.getValue());
            client.setNumberOfOutlets((Integer) outletsSpinner.getValue());
            client.setPaymentStatus("Pending");
            
            // Calculate all fees
            calculationService.calculateAllFees(client);
            
            // Save client
            boolean success = clientService.addClient(client);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Registration Successful!\n\n" +
                    "Your Client ID: " + clientId + "\n" +
                    "Phone Number: " + phoneField.getText().trim() + "\n\n" +
                    "Please save these credentials to login and view your project details.\n" +
                    "Total Estimated Cost: KES " + String.format("%,.2f", client.getTotalCost()), 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Go to client dashboard
                this.dispose();
                SwingUtilities.invokeLater(() -> {
                    ClientDashboard dashboard = new ClientDashboard(client);
                    dashboard.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to register. Please check the console for error details.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error during registration:\n" + e.getClass().getName() + "\n" + e.getMessage(), 
                "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void backToLogin() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to cancel registration?", 
            "Confirm Cancel", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                LoginUI loginUI = new LoginUI();
                loginUI.setVisible(true);
            });
        }
    }
}
