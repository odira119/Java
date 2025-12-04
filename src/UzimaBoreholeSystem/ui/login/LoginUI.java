package UzimaBoreholeSystem.ui.login;

import javax.swing.*;

import UzimaBoreholeSystem.services.AuthService;
import UzimaBoreholeSystem.models.Client;
import UzimaBoreholeSystem.services.StaffService;
import UzimaBoreholeSystem.ui.admin.AdminDashboard;
import UzimaBoreholeSystem.ui.client.ClientDashboard;
import UzimaBoreholeSystem.models.Staff;

import java.awt.*;

public class LoginUI extends JFrame {
    private final AuthService authService;
    private final StaffService staffService;
    
    public LoginUI() {
        this.authService = new AuthService();
        this.staffService = new StaffService();
        
        setTitle("Uzima Borehole System - Login");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        initComponents();
    }
    
    private void initComponents() {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(25, 118, 210));
        headerPanel.setPreferredSize(new Dimension(500, 80));
        
        JLabel titleLabel = new JLabel("Uzima Borehole System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // Tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Client Login", createClientLoginPanel());
        tabbedPane.addTab("Staff Login", createStaffLoginPanel());
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createClientLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Info text
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel infoLabel = new JLabel("<html><center>Existing clients login below<br>New clients click 'Create Account'</center></html>");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        infoLabel.setForeground(new Color(100, 100, 100));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(infoLabel, gbc);
        
        gbc.gridwidth = 1;
        
        // Client ID
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Client ID:"), gbc);
        
        gbc.gridx = 1;
        JTextField clientIdField = new JTextField(20);
        panel.add(clientIdField, gbc);
        
        // Phone Number
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Phone Number:"), gbc);
        
        gbc.gridx = 1;
        JTextField phoneField = new JTextField(20);
        panel.add(phoneField, gbc);
        
        // Login Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(25, 118, 210));
        loginButton.setForeground(Color.WHITE);
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(250, 40));
        loginButton.addActionListener(e -> handleClientLogin(clientIdField.getText(), phoneField.getText()));
        panel.add(loginButton, gbc);
        
        // Separator
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 10, 10, 10);
        JLabel orLabel = new JLabel("OR");
        orLabel.setHorizontalAlignment(SwingConstants.CENTER);
        orLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        orLabel.setForeground(new Color(150, 150, 150));
        panel.add(orLabel, gbc);
        
        // Create Account Button
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton createAccountButton = new JButton("Create New Account");
        createAccountButton.setBackground(new Color(76, 175, 80));
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.setOpaque(true);
        createAccountButton.setBorderPainted(false);
        createAccountButton.setFocusPainted(false);
        createAccountButton.setFont(new Font("Arial", Font.BOLD, 14));
        createAccountButton.setPreferredSize(new Dimension(250, 40));
        createAccountButton.addActionListener(e -> openRegistrationForm());
        panel.add(createAccountButton, gbc);
        
        return panel;
    }
    
    private void openRegistrationForm() {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            UzimaBoreholeSystem.ui.client.ClientRegistrationForm registrationForm = new UzimaBoreholeSystem.ui.client.ClientRegistrationForm();
            registrationForm.setVisible(true);
        });
    }
    
    private JPanel createStaffLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Staff ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Staff ID:"), gbc);
        
        gbc.gridx = 1;
        JTextField staffIdField = new JTextField(20);
        panel.add(staffIdField, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);
        
        // Login Button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(25, 118, 210));
        loginButton.setForeground(Color.WHITE);
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(250, 40));
        loginButton.addActionListener(e -> handleStaffLogin(staffIdField.getText(), 
            new String(passwordField.getPassword())));
        panel.add(loginButton, gbc);
        
        return panel;
    }
    
    private void handleClientLogin(String clientId, String phoneNumber) {
        if (clientId.trim().isEmpty() || phoneNumber.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter both Client ID and Phone Number", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Client client = authService.authenticateClient(clientId, phoneNumber);
        if (client != null) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                ClientDashboard dashboard = new ClientDashboard(client);
                dashboard.setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(this, 
                "Invalid credentials. Please check your Client ID and Phone Number.", 
                "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleStaffLogin(String staffId, String password) {
        if (staffId.trim().isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter both Staff ID and Password", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Staff staff = staffService.authenticateStaff(staffId, password);
        if (staff != null) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                AdminDashboard dashboard = new AdminDashboard(staff);
                dashboard.setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(this, 
                "Invalid credentials. Please check your Staff ID and Password.", 
                "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
