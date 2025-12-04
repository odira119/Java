package UzimaBoreholeSystem.ui.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import UzimaBoreholeSystem.models.Staff;
import UzimaBoreholeSystem.ui.login.LoginUI;
import UzimaBoreholeSystem.services.ClientService;
import UzimaBoreholeSystem.services.RevenueService;
import UzimaBoreholeSystem.models.Client;

import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AdminDashboard extends JFrame {
    private final Staff staff;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private final ClientService clientService;
    private final RevenueService revenueService;
    private final NumberFormat currencyFormat;
    
    public AdminDashboard(Staff staff) {
        this.staff = staff;
        this.clientService = new ClientService();
        this.revenueService = new RevenueService();
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale.Builder().setLanguage("en").setRegion("KE").build());
        
        setTitle("Admin Dashboard - " + staff.getName());
        setSize(1200, 800);
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
        contentPanel.add(createDashboardView(), "dashboard");
        contentPanel.add(new AddClientForm(this), "addClient");
        contentPanel.add(new ClientTableView(), "viewClients");
        contentPanel.add(createRevenueReportView(), "revenueReport");
        
        splitPane.setRightComponent(contentPanel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(25, 118, 210));
        panel.setPreferredSize(new Dimension(1200, 70));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Uzima Borehole System - Admin Portal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        
        JLabel staffLabel = new JLabel("Staff: " + staff.getName() + " (" + staff.getRole() + ")");
        staffLabel.setForeground(Color.WHITE);
        staffLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(211, 47, 47));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> logout());
        
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
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(new EmptyBorder(20, 10, 20, 10));
        
        JLabel menuLabel = new JLabel("Menu");
        menuLabel.setFont(new Font("Arial", Font.BOLD, 16));
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(menuLabel);
        panel.add(Box.createVerticalStrut(20));
        
        // Menu buttons
        panel.add(createMenuButton("Dashboard", "dashboard"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createMenuButton("Add Client", "addClient"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createMenuButton("View Clients", "viewClients"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createMenuButton("Revenue Report", "revenueReport"));
        
        return panel;
    }
    
    private JButton createMenuButton(String text, String viewName) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(230, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(25, 118, 210));
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.addActionListener(e -> showView(viewName));
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
    
    private JPanel createDashboardView() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        JLabel welcomeLabel = new JLabel("Welcome to Admin Dashboard");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JTextArea infoArea = new JTextArea();
        infoArea.setText("\nQuick Stats:\n\n" +
                        "• Use 'Add Client' to register new clients\n" +
                        "• Use 'View Clients' to see all registered clients\n" +
                        "• All costs are calculated automatically\n" +
                        "• Client IDs are generated in format UZ-XXX\n");
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 16));
        infoArea.setBackground(Color.WHITE);
        infoArea.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(infoArea, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createRevenueReportView() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Revenue Report");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Main content with scroll
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        
        // Summary Cards
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        summaryPanel.setBackground(Color.WHITE);
        summaryPanel.setMaximumSize(new Dimension(1100, 120));
        
        double totalRevenue = clientService.getTotalRevenueAllServices();
        double totalTax = clientService.getTotalTaxCollected();
        int paidClients = clientService.getPaidClientsCount();
        
        summaryPanel.add(createStatCard("Total Revenue", currencyFormat.format(totalRevenue), new Color(76, 175, 80)));
        summaryPanel.add(createStatCard("Tax Collected (16%)", currencyFormat.format(totalTax), new Color(243, 156, 18)));
        summaryPanel.add(createStatCard("Paid Clients", String.valueOf(paidClients), new Color(25, 118, 210)));
        
        contentPanel.add(summaryPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Revenue by Service Type
        contentPanel.add(createServiceRevenuePanel());
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Revenue by Customer Table
        contentPanel.add(createCustomerRevenuePanel());
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titleLabel.setForeground(new Color(127, 140, 141));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 20));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(valueLabel);
        
        return card;
    }
    
    private JPanel createServiceRevenuePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                "Revenue by Service Type", 0, 0, new Font("Arial", Font.BOLD, 16)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        panel.setMaximumSize(new Dimension(1100, 350));
        
        JPanel gridPanel = new JPanel(new GridLayout(7, 2, 10, 10));
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
    
    private JPanel createCustomerRevenuePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                "Revenue from Each Customer", 0, 0, new Font("Arial", Font.BOLD, 16)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        panel.setMaximumSize(new Dimension(1100, 400));
        
        String[] columnNames = {"Client ID", "Client Name", "Subtotal", "Tax (16%)", "Total Revenue", "Status"};
        Object[][] data = getCustomerRevenueData();
        
        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(25, 118, 210));
        table.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1070, 300));
        
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
        lblLabel.setFont(new Font("Arial", Font.BOLD, 13));
        
        JLabel lblValue = new JLabel(currencyFormat.format(amount));
        lblValue.setFont(new Font("Arial", Font.PLAIN, 13));
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
