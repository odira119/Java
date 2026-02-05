package UzimaBoreholeSystem.ui.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import UzimaBoreholeSystem.models.Staff;
import UzimaBoreholeSystem.ui.login.LoginUI;
import UzimaBoreholeSystem.services.ClientService;
import UzimaBoreholeSystem.models.Client;

import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdminDashboard extends JFrame {
    private final Staff staff;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private final ClientService clientService;
    private final NumberFormat currencyFormat;
    
    public AdminDashboard(Staff staff) {
        this.staff = staff;
        this.clientService = new ClientService();
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale.Builder().setLanguage("en").setRegion("KE").build());
        
        setTitle("Uzima Borehole System - Admin Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Start maximized
        setMinimumSize(new Dimension(1200, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
    }
    
    private void initComponents() {
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        
        // Sidebar and content
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(250);
        splitPane.setLeftComponent(createSidebarPanel());
        
        // Content panel with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);
        
        // Add different views
        contentPanel.add(new ClientTableView(), "viewClients");
        contentPanel.add(new ClientDetailsView(this), "clientDetails");
        contentPanel.add(createRevenueReportView(), "revenueReport");
        
        splitPane.setRightComponent(contentPanel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Show View Clients by default
        showView("viewClients");
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(41, 128, 185)); // Modern blue
        panel.setPreferredSize(new Dimension(1200, 80));
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        
        JLabel titleLabel = new JLabel("🔧 UZIMA BOREHOLE SYSTEM - Admin Portal");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        
        JLabel staffLabel = new JLabel("👤 " + staff.getName() + " • " + staff.getRole());
        staffLabel.setForeground(new Color(236, 240, 241));
        staffLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setOpaque(true);
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setPreferredSize(new Dimension(110, 40));
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
        
        rightPanel.add(staffLabel);
        rightPanel.add(Box.createHorizontalStrut(15));
        rightPanel.add(logoutButton);
        
        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createSidebarPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(52, 73, 94)); // Dark sidebar
        panel.setBorder(new EmptyBorder(30, 15, 30, 15));
        
        JLabel menuLabel = new JLabel("MENU");
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        menuLabel.setForeground(new Color(236, 240, 241));
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(menuLabel);
        panel.add(Box.createVerticalStrut(30));
        
        // Menu buttons
        panel.add(createMenuButton("Add Client", "addClient"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createMenuButton("View Clients", "viewClients"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createMenuButton("Client Details", "clientDetails"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createMenuButton("Revenue Report", "revenueReport"));
        
        return panel;
    }
    
    private JButton createMenuButton(String text, String viewName) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(230, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(44, 62, 80));
        button.setForeground(new Color(236, 240, 241));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(41, 128, 185));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(44, 62, 80));
            }
        });
        
        if (viewName.equals("addClient")) {
            button.addActionListener(e -> openClientRegistrationForm());
        } else {
            button.addActionListener(e -> showView(viewName));
        }
        
        return button;
    }
    
    private void showView(String viewName) {
        cardLayout.show(contentPanel, viewName);
    }
    
    public void showClientsTableView() {
        cardLayout.show(contentPanel, "viewClients");
        // Refresh the table
        Component[] components = contentPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof ClientTableView) {
                ((ClientTableView) comp).refreshTable();
                break;
            }
        }
    }
    
    private void openClientRegistrationForm() {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            UzimaBoreholeSystem.ui.client.ClientRegistrationForm registrationForm = 
                new UzimaBoreholeSystem.ui.client.ClientRegistrationForm("admin");
            registrationForm.setVisible(true);
        });
    }
    
    private JPanel createRevenueReportView() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Title
        JLabel titleLabel = new JLabel("📊 Revenue Report Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(5, 0, 15, 0));
        
        // Main content - NO SCROLL, everything visible
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(236, 240, 241));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.BOTH;
        
        double totalRevenue = clientService.getTotalRevenueAllServices();
        double totalTax = clientService.getTotalTaxCollected();
        int paidClients = clientService.getPaidClientsCount();
        
        // Top Row - Summary Cards (3 cards)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.33;
        gbc.weighty = 0.15;
        contentPanel.add(createCompactStatCard("💰 Total Revenue", currencyFormat.format(totalRevenue), new Color(46, 204, 113)), gbc);
        
        gbc.gridx = 1;
        contentPanel.add(createCompactStatCard("📋 Tax Collected (16%)", currencyFormat.format(totalTax), new Color(241, 196, 15)), gbc);
        
        gbc.gridx = 2;
        contentPanel.add(createCompactStatCard("👥 Paid Clients", String.valueOf(paidClients), new Color(52, 152, 219)), gbc);
        
        // Middle Row - Service Revenue (compact)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.6;
        gbc.weighty = 0.25;
        contentPanel.add(createCompactServiceRevenuePanel(), gbc);
        
        // Right Middle - Quick Stats
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.4;
        contentPanel.add(createQuickStatsPanel(), gbc);
        
        // Bottom Row - Customer Table (expanded)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 0.6;
        gbc.fill = GridBagConstraints.BOTH;
        contentPanel.add(createCompactCustomerRevenuePanel(), gbc);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createCompactStatCard(String title, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 3),
            new EmptyBorder(18, 20, 18, 20)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(new Color(127, 140, 141));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(valueLabel);
        
        return card;
    }
    
    private JPanel createCompactServiceRevenuePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2), 
                "📈 Revenue by Service", 
                0, 0, 
                new Font("Segoe UI", Font.BOLD, 15),
                new Color(52, 73, 94)
            ),
            new EmptyBorder(10, 15, 10, 15)
        ));
        
        JPanel gridPanel = new JPanel(new GridLayout(7, 2, 8, 6));
        gridPanel.setBackground(Color.WHITE);
        
        double surveyRevenue = clientService.getTotalRevenueFromSurveyFees();
        double localAuthorityRevenue = clientService.getTotalRevenueFromLocalAuthorityFees();
        double drillingRevenue = clientService.getTotalRevenueFromDrilling();
        double pumpRevenue = clientService.getTotalRevenueFromPumpInstallation();
        double plumbingRevenue = clientService.getTotalRevenueFromPlumbing();
        double depthChargeRevenue = clientService.getTotalRevenueFromDepthCharges();
        
        addRevenueRow(gridPanel, "Survey Fees:", surveyRevenue);
        addRevenueRow(gridPanel, "Local Authority Fees:", localAuthorityRevenue);
        addRevenueRow(gridPanel, "Drilling Services:", drillingRevenue);
        addRevenueRow(gridPanel, "Pump Installation:", pumpRevenue);
        addRevenueRow(gridPanel, "Plumbing Services:", plumbingRevenue);
        addRevenueRow(gridPanel, "Depth Charges:", depthChargeRevenue);
        
        double subtotal = surveyRevenue + localAuthorityRevenue + drillingRevenue + 
                         pumpRevenue + plumbingRevenue + depthChargeRevenue;
        addRevenueRow(gridPanel, "Subtotal:", subtotal);
        
        panel.add(gridPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createQuickStatsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(155, 89, 182), 2),
                "⚡ Quick Stats",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 15),
                new Color(52, 73, 94)
            ),
            new EmptyBorder(10, 15, 10, 15)
        ));
        
        List<Client> allClients = clientService.getAllClients();
        int totalClients = allClients.size();
        int paidClientsCount = clientService.getPaidClientsCount();
        int pendingClients = (int) allClients.stream().filter(c -> "Pending".equals(c.getPaymentStatus())).count();
        
        addQuickStatRow(panel, "Total Clients:", String.valueOf(totalClients));
        addQuickStatRow(panel, "Pending Payments:", String.valueOf(pendingClients));
        addQuickStatRow(panel, "Completion Rate:", String.format("%.1f%%", (paidClientsCount * 100.0 / Math.max(1, totalClients))));
        
        return panel;
    }
    
    private void addQuickStatRow(JPanel panel, String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
        
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblLabel.setForeground(new Color(52, 73, 94));
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblValue.setForeground(new Color(155, 89, 182));
        lblValue.setHorizontalAlignment(SwingConstants.RIGHT);
        
        row.add(lblLabel, BorderLayout.WEST);
        row.add(lblValue, BorderLayout.EAST);
        
        panel.add(row);
    }
    
    private JPanel createCompactCustomerRevenuePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setMinimumSize(new Dimension(600, 250));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(46, 204, 113), 2), 
                "👥 Customer Revenue Overview", 
                0, 0, 
                new Font("Segoe UI", Font.BOLD, 15),
                new Color(52, 73, 94)
            ),
            new EmptyBorder(10, 15, 15, 15)
        ));
        
        String[] columnNames = {"Client ID", "Client Name", "Subtotal", "Tax (16%)", "Total Revenue", "Status"};
        Object[][] data = getCustomerRevenueData();
        
        javax.swing.table.DefaultTableModel tableModel = new javax.swing.table.DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(28);
        table.setGridColor(new Color(236, 240, 241));
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));
        
        // Style table header
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(52, 73, 94));
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setReorderingAllowed(false);
        
        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(80);  // Client ID
        table.getColumnModel().getColumn(1).setPreferredWidth(180); // Client Name
        table.getColumnModel().getColumn(2).setPreferredWidth(120); // Subtotal
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // Tax
        table.getColumnModel().getColumn(4).setPreferredWidth(130); // Total Revenue
        table.getColumnModel().getColumn(5).setPreferredWidth(90);  // Status
        
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private Object[][] getCustomerRevenueData() {
        List<Client> clients = clientService.getAllClients();
        Object[][] data = new Object[clients.size()][6];
        
        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            data[i][0] = client.getClientId();
            data[i][1] = client.getName();
            data[i][2] = currencyFormat.format(client.getSubtotal());
            data[i][3] = currencyFormat.format(client.getTaxPaid());
            data[i][4] = currencyFormat.format(client.getTotalCost());
            data[i][5] = client.getPaymentStatus();
        }
        
        return data;
    }
    
    private void addRevenueRow(JPanel panel, String label, double amount) {
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblLabel.setForeground(new Color(52, 73, 94));
        
        JLabel lblValue = new JLabel(currencyFormat.format(amount));
        lblValue.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblValue.setForeground(new Color(44, 62, 80));
        lblValue.setHorizontalAlignment(SwingConstants.RIGHT);
        
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
                LoginUI loginUI = new LoginUI();
                loginUI.setVisible(true);
            });
        }
    }
}
