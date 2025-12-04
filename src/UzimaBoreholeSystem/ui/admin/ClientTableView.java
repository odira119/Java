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
        
        JLabel titleLabel = new JLabel("Client List");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        searchField = new JTextField(30);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(25, 118, 210));
        searchButton.setForeground(Color.WHITE);
        searchButton.setOpaque(true);
        searchButton.setBorderPainted(false);
        searchButton.setFocusPainted(false);
        searchButton.setPreferredSize(new Dimension(100, 35));
        searchButton.addActionListener(e -> searchClients());
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(76, 175, 80));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setOpaque(true);
        refreshButton.setBorderPainted(false);
        refreshButton.setFocusPainted(false);
        refreshButton.setPreferredSize(new Dimension(100, 35));
        refreshButton.addActionListener(e -> loadClients());
        
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
        clientTable.setFont(new Font("Arial", Font.PLAIN, 12));
        clientTable.setRowHeight(30);
        clientTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        clientTable.getTableHeader().setBackground(new Color(25, 118, 210));
        clientTable.getTableHeader().setForeground(Color.WHITE);
        clientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(clientTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
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
