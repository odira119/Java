package ui.admin;

import ui.login.LoginUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import UzimaBoreholeSystem.models.Staff;

import java.awt.*;

public class AdminDashboard extends JFrame {
    private final Staff staff;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    
    public AdminDashboard(Staff staff) {
        this.staff = staff;
        
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
        
        return panel;
    }
    
    private JButton createMenuButton(String text, String viewName) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(230, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(25, 118, 210));
        button.setForeground(Color.WHITE);
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
