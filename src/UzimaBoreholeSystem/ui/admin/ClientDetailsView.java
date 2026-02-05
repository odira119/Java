package UzimaBoreholeSystem.ui.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import UzimaBoreholeSystem.models.Client;
import UzimaBoreholeSystem.services.ClientService;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ClientDetailsView extends JPanel {
    private final ClientService clientService;
    private final NumberFormat currencyFormat;
    private final AdminDashboard parentDashboard;
    private List<Client> allClients;
    private int currentClientIndex = 0;
    private JPanel contentPanel;
    private JButton prevButton;
    private JButton nextButton;
    private JLabel clientCountLabel;
    
    public ClientDetailsView(AdminDashboard parentDashboard) {
        this.parentDashboard = parentDashboard;
        this.clientService = new ClientService();
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale.Builder().setLanguage("en").setRegion("KE").build());
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        loadAllClients();
        initComponents();
    }
    
    private void loadAllClients() {
        allClients = clientService.getAllClients();
        currentClientIndex = 0;
    }
    
    private void initComponents() {
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        
        JLabel titleLabel = new JLabel("👤 View Client Details");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JButton backButton = new JButton("← Back");
        backButton.setBackground(new Color(41, 128, 185));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(100, 35));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> parentDashboard.showClientsTableView());
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);
        
        // Search and Navigation Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        searchPanel.setBackground(Color.WHITE);
        
        JLabel searchLabel = new JLabel("Search by Name:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        
        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchField.setPreferredSize(new Dimension(300, 40));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(52, 152, 219));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setPreferredSize(new Dimension(120, 40));
        searchButton.setFocusPainted(false);
        searchButton.setBorderPainted(false);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        searchButton.addActionListener(e -> {
            String searchName = searchField.getText().trim();
            if (searchName.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Please enter a client name",
                    "Input Required", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            List<Client> searchResults = clientService.searchClients(searchName);
            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "No clients found matching: " + searchName,
                    "Not Found", JOptionPane.INFORMATION_MESSAGE);
            } else if (searchResults.size() == 1) {
                allClients = searchResults;
                currentClientIndex = 0;
                displayCurrentClient();
            } else {
                // Multiple results - show selection dialog
                String[] clientNames = searchResults.stream()
                    .map(c -> c.getName() + " (ID: " + c.getClientId() + ")")
                    .toArray(String[]::new);
                String selected = (String) JOptionPane.showInputDialog(
                    this,
                    "Multiple clients found. Select one:",
                    "Select Client",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    clientNames,
                    clientNames[0]
                );
                
                if (selected != null) {
                    int index = java.util.Arrays.asList(clientNames).indexOf(selected);
                    allClients = searchResults;
                    currentClientIndex = index;
                    displayCurrentClient();
                }
            }
        });
        
        searchField.addActionListener(e -> searchButton.doClick());
        
        JButton showAllButton = new JButton("Show All");
        showAllButton.setBackground(new Color(46, 204, 113));
        showAllButton.setForeground(Color.WHITE);
        showAllButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        showAllButton.setPreferredSize(new Dimension(120, 40));
        showAllButton.setFocusPainted(false);
        showAllButton.setBorderPainted(false);
        showAllButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showAllButton.addActionListener(e -> {
            loadAllClients();
            displayCurrentClient();
        });
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);
        
        // Navigation Panel
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        navPanel.setBackground(Color.WHITE);
        navPanel.setBorder(new EmptyBorder(5, 20, 10, 20));
        
        prevButton = new JButton("← Previous");
        prevButton.setBackground(new Color(52, 73, 94));
        prevButton.setForeground(Color.WHITE);
        prevButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        prevButton.setPreferredSize(new Dimension(130, 40));
        prevButton.setFocusPainted(false);
        prevButton.setBorderPainted(false);
        prevButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        prevButton.addActionListener(e -> showPreviousClient());
        
        clientCountLabel = new JLabel();
        clientCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        clientCountLabel.setForeground(new Color(52, 73, 94));
        
        nextButton = new JButton("Next →");
        nextButton.setBackground(new Color(52, 73, 94));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nextButton.setPreferredSize(new Dimension(130, 40));
        nextButton.setFocusPainted(false);
        nextButton.setBorderPainted(false);
        nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nextButton.addActionListener(e -> showNextClient());
        
        navPanel.add(prevButton);
        navPanel.add(clientCountLabel);
        navPanel.add(nextButton);
        
        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(navPanel, BorderLayout.SOUTH);
        
        // Content Panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(236, 240, 241));
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setPreferredSize(new Dimension(900, 600));
        
        // Main content area
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        
        // Display first client if available
        if (!allClients.isEmpty()) {
            displayCurrentClient();
        } else {
            displayNoClients();
        }
    }
    
    private void showPreviousClient() {
        if (currentClientIndex > 0) {
            currentClientIndex--;
            displayCurrentClient();
        }
    }
    
    private void showNextClient() {
        if (currentClientIndex < allClients.size() - 1) {
            currentClientIndex++;
            displayCurrentClient();
        }
    }
    
    private void displayCurrentClient() {
        if (allClients.isEmpty()) {
            displayNoClients();
            return;
        }
        
        Client client = allClients.get(currentClientIndex);
        displayClientDetails(client);
        updateNavigationButtons();
    }
    
    private void updateNavigationButtons() {
        if (allClients.isEmpty()) {
            prevButton.setEnabled(false);
            nextButton.setEnabled(false);
            clientCountLabel.setText("No clients");
        } else {
            prevButton.setEnabled(currentClientIndex > 0);
            nextButton.setEnabled(currentClientIndex < allClients.size() - 1);
            clientCountLabel.setText("Client " + (currentClientIndex + 1) + " of " + allClients.size());
        }
    }
    
    private void displayNoClients() {
        contentPanel.removeAll();
        contentPanel.setBorder(new EmptyBorder(50, 30, 50, 30));
        
        JLabel noClientsLabel = new JLabel("No clients found in the system");
        noClientsLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        noClientsLabel.setForeground(new Color(127, 140, 141));
        noClientsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(noClientsLabel);
        contentPanel.add(Box.createVerticalGlue());
        
        contentPanel.revalidate();
        contentPanel.repaint();
        updateNavigationButtons();
    }
    
    private void displayClientDetails(Client client) {
        contentPanel.removeAll();
        contentPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        
        // Client Info Card
        contentPanel.add(createInfoCard("📋 Client Information", new String[][]{
            {"Client ID:", client.getClientId()},
            {"Name:", client.getName()},
            {"Phone:", client.getPhone()},
            {"Email:", client.getEmail()},
            {"Address:", client.getAddress()}
        }, new Color(52, 152, 219)));
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Project Details Card
        contentPanel.add(createInfoCard("🔧 Project Details", new String[][]{
            {"Category:", client.getClientCategory()},
            {"Location:", client.getBoreholeLocation()},
            {"Drilling Type:", client.getDrillingType()},
            {"Depth:", client.getDepthOrHeight() + " meters"},
            {"Pump Type:", client.getPumpType()},
            {"Pipe Type:", client.getPipeType()},
            {"Number of Outlets:", String.valueOf(client.getNumberOfOutlets())}
        }, new Color(46, 204, 113)));
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Cost Details Card
        contentPanel.add(createCostCard(client));
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Action Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(236, 240, 241));
        
        JButton editButton = new JButton("Edit Client");
        editButton.setBackground(new Color(243, 156, 18));
        editButton.setForeground(Color.WHITE);
        editButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        editButton.setPreferredSize(new Dimension(150, 40));
        editButton.setFocusPainted(false);
        editButton.setBorderPainted(false);
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Edit functionality coming soon!", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton deleteButton = new JButton("Delete Client");
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteButton.setPreferredSize(new Dimension(150, 40));
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteButton.addActionListener(e -> handleDeleteClient(client));
        
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        contentPanel.add(buttonPanel);
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private JPanel createInfoCard(String title, String[][] data, Color borderColor) {
        JPanel card = new JPanel(new GridLayout(data.length, 2, 15, 12));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(borderColor, 2),
                title,
                0, 0,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(52, 73, 94)
            ),
            new EmptyBorder(15, 20, 15, 20)
        ));
        
        for (String[] row : data) {
            JLabel label = new JLabel(row[0]);
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            JLabel value = new JLabel(row[1] != null ? row[1] : "N/A");
            value.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            card.add(label);
            card.add(value);
        }
        
        return card;
    }
    
    private JPanel createCostCard(Client client) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(241, 196, 15), 2),
                "💰 Cost Breakdown",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(52, 73, 94)
            ),
            new EmptyBorder(15, 20, 15, 20)
        ));
        
        JPanel costsPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        costsPanel.setBackground(Color.WHITE);
        
        addCostRow(costsPanel, "Survey Fee:", client.getSurveyFee());
        addCostRow(costsPanel, "Local Authority Fee:", client.getLocalAuthorityFee());
        addCostRow(costsPanel, "Drilling Fee:", client.getDrillingFee());
        addCostRow(costsPanel, "Pump Installation:", client.getPumpInstallationFee());
        addCostRow(costsPanel, "Plumbing Fee:", client.getPlumbingFee());
        addCostRow(costsPanel, "Depth Charge:", client.getDepthCharge());
        
        card.add(costsPanel);
        card.add(Box.createVerticalStrut(10));
        
        // Summary
        JPanel summaryPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        summaryPanel.setBackground(Color.WHITE);
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(189, 195, 199)),
            new EmptyBorder(10, 0, 0, 0)
        ));
        
        addSummaryRow(summaryPanel, "Subtotal:", client.getSubtotal(), Font.BOLD, Color.BLACK);
        addSummaryRow(summaryPanel, "Tax (16%):", client.getTaxPaid(), Font.BOLD, new Color(243, 156, 18));
        addSummaryRow(summaryPanel, "Total Cost:", client.getTotalCost(), Font.BOLD, new Color(25, 118, 210));
        
        JLabel statusLabel = new JLabel("Payment Status:");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        JLabel statusValue = new JLabel(client.getPaymentStatus());
        statusValue.setFont(new Font("Segoe UI", Font.BOLD, 15));
        statusValue.setForeground("Paid".equalsIgnoreCase(client.getPaymentStatus()) ? 
            new Color(46, 204, 113) : new Color(231, 76, 60));
        summaryPanel.add(statusLabel);
        summaryPanel.add(statusValue);
        
        card.add(summaryPanel);
        
        return card;
    }
    
    private void addCostRow(JPanel panel, String label, double amount) {
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JLabel lblValue = new JLabel(currencyFormat.format(amount));
        lblValue.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panel.add(lblLabel);
        panel.add(lblValue);
    }
    
    private void addSummaryRow(JPanel panel, String label, double amount, int style, Color color) {
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Segoe UI", style, 15));
        lblLabel.setForeground(color);
        JLabel lblValue = new JLabel(currencyFormat.format(amount));
        lblValue.setFont(new Font("Segoe UI", style, 15));
        lblValue.setForeground(color);
        panel.add(lblLabel);
        panel.add(lblValue);
    }
    
    private void handleDeleteClient(Client client) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete client: " + client.getName() + "?\n" +
            "Client ID: " + client.getClientId() + "\n\n" +
            "This action cannot be undone!",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = clientService.deleteClient(client.getClientId());
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Client deleted successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                parentDashboard.showClientsTableView();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to delete client. Please try again.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
