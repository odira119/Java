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
    private final String callerSource; // "login" or "admin"
    
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
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
        this("login");
    }
    
    public ClientRegistrationForm(String callerSource) {
        this.clientService = new ClientService();
        this.calculationService = new CalculationService();
        this.callerSource = callerSource;
        
        setTitle("Uzima Borehole System - Client Registration");
        setSize(1350, 780);
        setMinimumSize(new Dimension(1250, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setPreferredSize(new Dimension(1400, 65));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel subtitleLabel = new JLabel("New Client Registration");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(236, 240, 241));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("📝 Register & Submit Borehole Details");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(subtitleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(titleLabel);
        
        // Form panel with scroll pane
        JPanel formPanel = createFormPanel();
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createFormPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(15, 25, 15, 25));
        
        // Create two-column layout panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;
        
        // LEFT COLUMN - Personal Information
        JPanel leftPanel = createStyledPanel("👤 PERSONAL INFORMATION");
        JPanel leftFields = new JPanel(new GridBagLayout());
        leftFields.setBackground(Color.WHITE);
        GridBagConstraints leftGbc = new GridBagConstraints();
        leftGbc.insets = new Insets(5, 10, 5, 10);
        leftGbc.fill = GridBagConstraints.HORIZONTAL;
        leftGbc.anchor = GridBagConstraints.WEST;
        leftGbc.weightx = 0.4;
        
        int leftRow = 0;
        
        // Name
        addCompactField(leftFields, leftGbc, leftRow++, "Full Name:");
        nameField = createStyledTextField(25);
        leftGbc.gridx = 1;
        leftFields.add(nameField, leftGbc);
        
        // Phone
        addCompactField(leftFields, leftGbc, leftRow++, "Phone Number:");
        phoneField = createStyledTextField(25);
        leftGbc.gridx = 1;
        leftFields.add(phoneField, leftGbc);
        
        // Email
        addCompactField(leftFields, leftGbc, leftRow++, "Email Address:");
        emailField = createStyledTextField(25);
        leftGbc.gridx = 1;
        leftFields.add(emailField, leftGbc);
        
        // Password
        addCompactField(leftFields, leftGbc, leftRow++, "Password:");
        passwordField = createStyledPasswordField(25);
        leftGbc.gridx = 1;
        leftFields.add(passwordField, leftGbc);
        
        // Confirm Password
        addCompactField(leftFields, leftGbc, leftRow++, "Confirm Password:");
        confirmPasswordField = createStyledPasswordField(25);
        leftGbc.gridx = 1;
        leftFields.add(confirmPasswordField, leftGbc);
        
        // Location
        addCompactField(leftFields, leftGbc, leftRow++, "Borehole Location:");
        locationField = createStyledTextField(25);
        leftGbc.gridx = 1;
        leftFields.add(locationField, leftGbc);
        
        leftPanel.add(leftFields, BorderLayout.CENTER);
        
        // RIGHT COLUMN - Borehole Specifications
        JPanel rightPanel = createStyledPanel("🔧 BOREHOLE SPECIFICATIONS");
        JPanel rightFields = new JPanel(new GridBagLayout());
        rightFields.setBackground(Color.WHITE);
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.insets = new Insets(5, 10, 5, 10);
        rightGbc.fill = GridBagConstraints.HORIZONTAL;
        rightGbc.anchor = GridBagConstraints.WEST;
        rightGbc.weightx = 0.4;
        
        int rightRow = 0;
        
        // Category
        addCompactField(rightFields, rightGbc, rightRow++, "Project Category:");
        categoryCombo = createStyledComboBox(new String[]{"Domestic", "Commercial", "Industrial"});
        rightGbc.gridx = 1;
        rightFields.add(categoryCombo, rightGbc);
        
        // Drilling Type
        addCompactField(rightFields, rightGbc, rightRow++, "Drilling Type:");
        drillingTypeCombo = createStyledComboBox(new String[]{"Symmetric", "Core", "Geo-Technical"});
        rightGbc.gridx = 1;
        rightFields.add(drillingTypeCombo, rightGbc);
        
        // Depth
        addCompactField(rightFields, rightGbc, rightRow++, "Required Depth (m):");
        depthSpinner = createStyledSpinner(50, 1, 500, 10);
        rightGbc.gridx = 1;
        rightFields.add(depthSpinner, rightGbc);
        
        // Pump Type
        addCompactField(rightFields, rightGbc, rightRow++, "Pump Type:");
        pumpTypeCombo = createStyledComboBox(new String[]{"Submersible electric pump", "Solar pump", "Hand pump"});
        rightGbc.gridx = 1;
        rightFields.add(pumpTypeCombo, rightGbc);
        
        rightPanel.add(rightFields, BorderLayout.CENTER);
        
        // BOTTOM LEFT - Plumbing Details
        JPanel bottomLeftPanel = createStyledPanel("💧 PLUMBING DETAILS");
        JPanel bottomLeftFields = new JPanel(new GridBagLayout());
        bottomLeftFields.setBackground(Color.WHITE);
        GridBagConstraints blGbc = new GridBagConstraints();
        blGbc.insets = new Insets(5, 10, 5, 10);
        blGbc.fill = GridBagConstraints.HORIZONTAL;
        blGbc.anchor = GridBagConstraints.WEST;
        blGbc.weightx = 0.4;
        
        int blRow = 0;
        
        // Plumbing Package
        addCompactField(bottomLeftFields, blGbc, blRow++, "Plumbing Package:");
        plumbingCombo = createStyledComboBox(new String[]{"Basic", "Standard", "Premium"});
        blGbc.gridx = 1;
        bottomLeftFields.add(plumbingCombo, blGbc);
        
        // Pipe Diameter
        addCompactField(bottomLeftFields, blGbc, blRow++, "Pipe Diameter (in):");
        pipeDiameterSpinner = createStyledSpinner(1, 1, 12, 1);
        blGbc.gridx = 1;
        bottomLeftFields.add(pipeDiameterSpinner, blGbc);
        
        bottomLeftPanel.add(bottomLeftFields, BorderLayout.CENTER);
        
        // BOTTOM RIGHT - Additional Specs
        JPanel bottomRightPanel = createStyledPanel("📏 ADDITIONAL SPECIFICATIONS");
        JPanel bottomRightFields = new JPanel(new GridBagLayout());
        bottomRightFields.setBackground(Color.WHITE);
        GridBagConstraints brGbc = new GridBagConstraints();
        brGbc.insets = new Insets(5, 10, 5, 10);
        brGbc.fill = GridBagConstraints.HORIZONTAL;
        brGbc.anchor = GridBagConstraints.WEST;
        brGbc.weightx = 0.4;
        
        int brRow = 0;
        
        // Pipe Length
        addCompactField(bottomRightFields, brGbc, brRow++, "Pipe Length (m):");
        pipeLengthSpinner = createStyledSpinner(50, 10, 500, 10);
        brGbc.gridx = 1;
        bottomRightFields.add(pipeLengthSpinner, brGbc);
        
        // Number of Outlets
        addCompactField(bottomRightFields, brGbc, brRow++, "Number of Outlets:");
        outletsSpinner = createStyledSpinner(2, 1, 20, 1);
        brGbc.gridx = 1;
        bottomRightFields.add(outletsSpinner, brGbc);
        
        bottomRightPanel.add(bottomRightFields, BorderLayout.CENTER);
        
        // Layout the four panels in 2x2 grid
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(leftPanel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(rightPanel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(bottomLeftPanel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(bottomRightPanel, gbc);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        JButton submitButton = new JButton("✓ Submit Registration");
        submitButton.setBackground(new Color(46, 204, 113));
        submitButton.setForeground(Color.WHITE);
        submitButton.setOpaque(true);
        submitButton.setBorderPainted(false);
        submitButton.setFocusPainted(false);
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        submitButton.setPreferredSize(new Dimension(220, 48));
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitButton.addActionListener(e -> handleRegistration());
        
        // Add hover effect
        submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(new Color(39, 174, 96));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(new Color(46, 204, 113));
            }
        });
        
        JButton backButton = new JButton("← Back");
        backButton.setBackground(new Color(149, 165, 166));
        backButton.setForeground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        backButton.setPreferredSize(new Dimension(130, 48));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> goBack());
        
        // Add hover effect
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(127, 140, 141));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(149, 165, 166));
            }
        });
        
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);
        
        return mainPanel;
    }
    
    private JPanel createStyledPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(8, 10, 10, 10)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 8, 8));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        return panel;
    }
    
    private void addCompactField(JPanel panel, GridBagConstraints gbc, int row, String label) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.4;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(new Color(44, 62, 80));
        lbl.setPreferredSize(new Dimension(180, 34));
        panel.add(lbl, gbc);
        gbc.weightx = 0.6;
    }
    
    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(300, 38));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return field;
    }
    
    private JPasswordField createStyledPasswordField(int columns) {
        JPasswordField field = new JPasswordField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(300, 38));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return field;
    }
    
    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setPreferredSize(new Dimension(300, 38));
        combo.setBackground(Color.WHITE);
        return combo;
    }
    
    private JSpinner createStyledSpinner(int initial, int min, int max, int step) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(initial, min, max, step));
        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        spinner.setPreferredSize(new Dimension(300, 38));
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return spinner;
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
        
        // Validate password
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a password", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            passwordField.requestFocus();
            return;
        }
        
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, 
                "Password must be at least 6 characters long", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            passwordField.requestFocus();
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, 
                "Passwords do not match", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            confirmPasswordField.requestFocus();
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
            client.setPassword(password);
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
                    "Your Email: " + emailField.getText().trim() + "\n\n" +
                    "Please use your email and password to login.\n" +
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
    
    private void goBack() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to go back? Any unsaved data will be lost.", 
            "Confirm", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                if ("admin".equals(callerSource)) {
                    // Return to admin dashboard - need to create a staff object
                    // For now, just reopen login and they can log back in as admin
                    LoginUI loginUI = new LoginUI();
                    loginUI.setVisible(true);
                } else {
                    LoginUI loginUI = new LoginUI();
                    loginUI.setVisible(true);
                }
            });
        }
    }
}
