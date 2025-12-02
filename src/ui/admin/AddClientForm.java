package ui.admin;

import utils.IDGenerator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import UzimaBoreholeSystem.services.ClientService;

import UzimaBoreholeSystem.models.Client;

import java.awt.*;

public class AddClientForm extends JPanel {
    private final ClientService clientService;
    private final AdminDashboard parentDashboard;
    
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField locationField;
    private JComboBox<String> categoryCombo;
    private JComboBox<String> drillingTypeCombo;
    private JSpinner depthSpinner;
    private JComboBox<String> pumpTypeCombo;
    private JComboBox<String> plumbingCombo;
    private JSpinner pipeDiameterSpinner;
    private JSpinner pipeLengthSpinner;
    private JSpinner outletsSpinner;
    
    public AddClientForm(AdminDashboard parentDashboard) {
        this.clientService = new ClientService();
        this.parentDashboard = parentDashboard;
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Title
        JLabel titleLabel = new JLabel("Add New Client");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Name
        addFormField(formPanel, gbc, row++, "Name:");
        nameField = new JTextField(30);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);
        
        // Phone
        addFormField(formPanel, gbc, row++, "Phone Number:");
        phoneField = new JTextField(30);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);
        
        // Location
        addFormField(formPanel, gbc, row++, "Location:");
        locationField = new JTextField(30);
        gbc.gridx = 1;
        formPanel.add(locationField, gbc);
        
        // Category
        addFormField(formPanel, gbc, row++, "Category:");
        categoryCombo = new JComboBox<>(new String[]{"Domestic", "Commercial", "Industrial"});
        gbc.gridx = 1;
        formPanel.add(categoryCombo, gbc);
        
        // Drilling Type
        addFormField(formPanel, gbc, row++, "Drilling Type:");
        drillingTypeCombo = new JComboBox<>(new String[]{"Symmetric", "Core", "Geo-Technical"});
        gbc.gridx = 1;
        formPanel.add(drillingTypeCombo, gbc);
        
        // Depth
        addFormField(formPanel, gbc, row++, "Depth (meters):");
        depthSpinner = new JSpinner(new SpinnerNumberModel(50, 1, 500, 10));
        gbc.gridx = 1;
        formPanel.add(depthSpinner, gbc);
        
        // Pump Type
        addFormField(formPanel, gbc, row++, "Pump Type:");
        pumpTypeCombo = new JComboBox<>(new String[]{"Submersible electric pump", "Solar pump", "Hand pump"});
        gbc.gridx = 1;
        formPanel.add(pumpTypeCombo, gbc);
        
        // Plumbing Specs
        addFormField(formPanel, gbc, row++, "Plumbing Specs:");
        plumbingCombo = new JComboBox<>(new String[]{"Basic", "Standard", "Premium"});
        gbc.gridx = 1;
        formPanel.add(plumbingCombo, gbc);
        
        // Pipe Diameter
        addFormField(formPanel, gbc, row++, "Pipe Diameter (inches):");
        pipeDiameterSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        gbc.gridx = 1;
        formPanel.add(pipeDiameterSpinner, gbc);
        
        // Pipe Length
        addFormField(formPanel, gbc, row++, "Pipe Length (meters):");
        pipeLengthSpinner = new JSpinner(new SpinnerNumberModel(50, 10, 500, 10));
        gbc.gridx = 1;
        formPanel.add(pipeLengthSpinner, gbc);
        
        // Number of Outlets
        addFormField(formPanel, gbc, row++, "Number of Outlets:");
        outletsSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 20, 1));
        gbc.gridx = 1;
        formPanel.add(outletsSpinner, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton saveButton = new JButton("Save Client");
        saveButton.setBackground(new Color(76, 175, 80));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.addActionListener(e -> saveClient());
        
        JButton clearButton = new JButton("Clear Form");
        clearButton.setBackground(new Color(158, 158, 158));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.addActionListener(e -> clearForm());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Wrap in scroll pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String label) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lbl, gbc);
    }
    
    private void saveClient() {
        // Validate inputs
        if (nameField.getText().trim().isEmpty() || 
            phoneField.getText().trim().isEmpty() || 
            locationField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all required fields", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Create client object
            Client client = new Client();
            client.setClientId(IDGenerator.generateClientId());
            client.setName(nameField.getText().trim());
            client.setPhoneNumber(phoneField.getText().trim());
            client.setLocation(locationField.getText().trim());
            client.setCategory((String) categoryCombo.getSelectedItem());
            client.setDrillingType((String) drillingTypeCombo.getSelectedItem());
            client.setDepth((Integer) depthSpinner.getValue());
            client.setPumpType((String) pumpTypeCombo.getSelectedItem());
            client.setPlumbingSpecs((String) plumbingCombo.getSelectedItem());
            client.setPipeDiameter((Integer) pipeDiameterSpinner.getValue());
            client.setPipeLength((Integer) pipeLengthSpinner.getValue());
            client.setNumberOfOutlets((Integer) outletsSpinner.getValue());
            client.setStatus("Pending");
            
            // Save client (this will automatically calculate costs)
            boolean success = clientService.addClient(client);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Client added successfully!\nClient ID: " + client.getClientId(), 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                parentDashboard.showClientsTableView();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to add client. Please try again.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void clearForm() {
        nameField.setText("");
        phoneField.setText("");
        locationField.setText("");
        categoryCombo.setSelectedIndex(0);
        drillingTypeCombo.setSelectedIndex(0);
        depthSpinner.setValue(50);
        pumpTypeCombo.setSelectedIndex(0);
        plumbingCombo.setSelectedIndex(0);
    }
}
