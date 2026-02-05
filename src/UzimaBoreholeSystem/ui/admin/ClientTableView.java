package UzimaBoreholeSystem.ui.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import UzimaBoreholeSystem.services.ClientService;

import UzimaBoreholeSystem.models.Client;

import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ClientTableView extends JPanel {
    private final ClientService clientService;
    private final NumberFormat currencyFormat;
    private JTable clientTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    
    public ClientTableView() {
        this.clientService = new ClientService();
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale.Builder().setLanguage("en").setRegion("KE").build());
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        initComponents();
        loadClients();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Title and search panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(new EmptyBorder(10, 0, 20, 0));
        
        JLabel titleLabel = new JLabel("📋 Client Database");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(52, 73, 94));
        topPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(new EmptyBorder(15, 0, 10, 0));
        
        JLabel searchLabel = new JLabel("🔍 Search:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        searchLabel.setForeground(new Color(52, 73, 94));
        
        searchField = new JTextField(35);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        JButton searchButton = new JButton("🔍 Search");
        searchButton.setBackground(new Color(52, 152, 219));
        searchButton.setForeground(Color.WHITE);
        searchButton.setOpaque(true);
        searchButton.setBorderPainted(false);
        searchButton.setFocusPainted(false);
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setPreferredSize(new Dimension(120, 38));
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchButton.addActionListener(e -> searchClients());
        
        // Add hover effect
        searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(new Color(41, 128, 185));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(new Color(52, 152, 219));
            }
        });
        
        JButton refreshButton = new JButton("↻ Refresh");
        refreshButton.setBackground(new Color(46, 204, 113));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setOpaque(true);
        refreshButton.setBorderPainted(false);
        refreshButton.setFocusPainted(false);
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshButton.setPreferredSize(new Dimension(120, 38));
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.addActionListener(e -> loadClients());
        
        // Add hover effect
        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(new Color(39, 174, 96));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(new Color(46, 204, 113));
            }
        });
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);
        
        topPanel.add(searchPanel, BorderLayout.SOUTH);
        
        // Table
        String[] columnNames = {"Client ID", "Name", "Phone", "Location", "Category", 
                                "Drilling Type", "Depth (m)", "Pump Type", "Total Cost", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        clientTable = new JTable(tableModel);
        clientTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        clientTable.setRowHeight(35);
        clientTable.setGridColor(new Color(236, 240, 241));
        clientTable.setShowGrid(true);
        clientTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        clientTable.getTableHeader().setBackground(new Color(52, 73, 94));
        clientTable.getTableHeader().setForeground(Color.WHITE);
        clientTable.getTableHeader().setPreferredSize(new Dimension(0, 40));
        clientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clientTable.setSelectionBackground(new Color(174, 214, 241));
        clientTable.setSelectionForeground(new Color(44, 62, 80));
        
        JScrollPane scrollPane = new JScrollPane(clientTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    public void loadClients() {
        tableModel.setRowCount(0);
        List<Client> clients = clientService.getAllClients();
        
        for (Client client : clients) {
            Object[] row = {
                client.getClientId(),
                client.getName(),
                client.getPhoneNumber(),
                client.getLocation(),
                client.getCategory(),
                client.getDrillingType(),
                client.getDepth(),
                client.getPumpType(),
                currencyFormat.format(client.getTotalCost()),
                client.getStatus()
            };
            tableModel.addRow(row);
        }
    }
    
    private void searchClients() {
        String searchTerm = searchField.getText().trim().toLowerCase();
        
        if (searchTerm.isEmpty()) {
            loadClients();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Client> clients = clientService.getAllClients();
        
        for (Client client : clients) {
            if (client.getClientId().toLowerCase().contains(searchTerm) ||
                client.getName().toLowerCase().contains(searchTerm) ||
                client.getPhoneNumber().toLowerCase().contains(searchTerm) ||
                client.getLocation().toLowerCase().contains(searchTerm)) {
                
                Object[] row = {
                    client.getClientId(),
                    client.getName(),
                    client.getPhoneNumber(),
                    client.getLocation(),
                    client.getCategory(),
                    client.getDrillingType(),
                    client.getDepth(),
                    client.getPumpType(),
                    currencyFormat.format(client.getTotalCost()),
                    client.getStatus()
                };
                tableModel.addRow(row);
            }
        }
    }
    
    public void refreshTable() {
        loadClients();
    }
}
