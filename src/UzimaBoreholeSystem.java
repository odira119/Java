import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class UzimaBoreholeSystem extends JFrame {

    //DATABASE CONFIG
    private static final String DB_URL = "jdbc:mysql://localhost:3306/uzima_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "odira2005";

    //FEES MAPS
    private static final Map<String, Integer> surveyFees;
    private static final Map<String, Integer> localFees;
    private static final Map<String, Integer> drillingFees;
    private static final Map<String, Integer> pumpCosts;

    static {
        Map<String, Integer> s = new HashMap<>();
        s.put("Industrial", 20000);
        s.put("Commercial", 15000);
        s.put("Domestic", 7000);
        surveyFees = Collections.unmodifiableMap(s);

        Map<String, Integer> l = new HashMap<>();
        l.put("Industrial", 50000);
        l.put("Commercial", 30000);
        l.put("Domestic", 10000);
        localFees = Collections.unmodifiableMap(l);

        Map<String, Integer> d = new HashMap<>();
        d.put("Symmetric", 130000);
        d.put("Core", 225000);
        d.put("Geo-Technical", 335000);
        drillingFees = Collections.unmodifiableMap(d);

        Map<String, Integer> p = new HashMap<>();
        p.put("Submersible", 90000);
        p.put("Solar", 65000);
        p.put("Hand", 30000);
        pumpCosts = Collections.unmodifiableMap(p);
    }

    // COMPONENTS
    JComboBox<String> categoryBox, drillingBox, pumpBox;
    JTextField depthField, plumbingField, nameField, addressField, phoneField;
    JTextArea outputArea;
    JButton calcButton, clearButton, saveButton, exitButton, viewButton;

    public UzimaBoreholeSystem() {

        setTitle("Uzima Borehole Drilling System");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 14));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));

        //HEADER
        JLabel header = new JLabel("UZIMA BOREHOLE DRILLING SYSTEM", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setOpaque(true);
        header.setBackground(new Color(0, 102, 204));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(header, BorderLayout.NORTH);

        //CLIENT PANEL
        JPanel clientPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        clientPanel.setBorder(BorderFactory.createTitledBorder("Client Information"));
        clientPanel.setBackground(new Color(240, 248, 255));

        nameField = new JTextField();
        addressField = new JTextField();
        phoneField = new JTextField();
        categoryBox = new JComboBox<>(new String[]{"Industrial", "Commercial", "Domestic"});

        clientPanel.add(new JLabel("Name:"));
        clientPanel.add(nameField);
        clientPanel.add(new JLabel("Address:"));
        clientPanel.add(addressField);
        clientPanel.add(new JLabel("Phone:"));
        clientPanel.add(phoneField);
        clientPanel.add(new JLabel("Category:"));
        clientPanel.add(categoryBox);

        //SERVICE PANEL
        JPanel servicePanel = new JPanel(new GridLayout(5, 2, 10, 10));
        servicePanel.setBorder(BorderFactory.createTitledBorder("Service Details"));
        servicePanel.setBackground(new Color(245, 250, 255));

        drillingBox = new JComboBox<>(new String[]{"Symmetric", "Core", "Geo-Technical"});
        pumpBox = new JComboBox<>(new String[]{"Submersible", "Solar", "Hand"});
        depthField = new JTextField();
        plumbingField = new JTextField();

        servicePanel.add(new JLabel("Type of Drilling:"));
        servicePanel.add(drillingBox);
        servicePanel.add(new JLabel("Pump Type:"));
        servicePanel.add(pumpBox);
        servicePanel.add(new JLabel("Depth (meters):"));
        servicePanel.add(depthField);
        servicePanel.add(new JLabel("Plumbing Cost (Ksh):"));
        servicePanel.add(plumbingField);

        //OUTPUT AREA
        outputArea = new JTextArea(18, 30);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        outputArea.setBorder(BorderFactory.createTitledBorder("Billing Summary"));
        outputArea.setBackground(new Color(255, 255, 245));

        // BUTTON PANEL
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        calcButton = new JButton("Compute Total");
        saveButton = new JButton("Save to Database");
        viewButton = new JButton("View Records");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");

        JButton[] buttons = {calcButton, saveButton, viewButton, clearButton, exitButton};
        Color[] colors = {
                Color.GREEN,
                new Color(0, 102, 204),
                new Color(102, 0, 204),
                new Color(255, 153, 0),
                new Color(204, 0, 0)
        };

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        for (int i = 0; i < buttons.length; i++) {
            JButton b = buttons[i];
            b.setForeground(Color.WHITE);
            b.setBackground(colors[i]);
            b.setOpaque(true);
            b.setContentAreaFilled(true);
            b.setFocusPainted(false);
            b.setFont(new Font("Segoe UI", Font.BOLD, 14));
            b.setPreferredSize(new Dimension(160, 40));
            b.setUI(new BasicButtonUI());
            b.setBorder(BorderFactory.createLineBorder(colors[i].darker(), 2));
            buttonPanel.add(b);
        }

        // MAIN CENTER PANEL
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.add(clientPanel);
        centerPanel.add(servicePanel);

        add(centerPanel, BorderLayout.CENTER);
        add(new JScrollPane(outputArea), BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        //EVENT LISTENERS
        calcButton.addActionListener(e -> calculate());
        saveButton.addActionListener(e -> saveToDatabase());
        viewButton.addActionListener(e -> viewRecords());
        clearButton.addActionListener(e -> clearFields());
        exitButton.addActionListener(e -> System.exit(0));

        createDatabase();
    }

    //DATABASE METHODS
    private void createDatabase() {
        String sql =
                "CREATE TABLE IF NOT EXISTS clients (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "name VARCHAR(100)," +
                        "address VARCHAR(255)," +
                        "phone VARCHAR(50)," +
                        "category VARCHAR(50)," +
                        "drilling VARCHAR(50)," +
                        "pump VARCHAR(50)," +
                        "depth INT," +
                        "plumbing DOUBLE," +
                        "total DOUBLE" +
                ")";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Could not connect to MySQL.\n" + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveToDatabase() {
        String sql =
                "INSERT INTO clients (name, address, phone, category, drilling, pump, depth, plumbing, total) " +
                        "VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, nameField.getText());
            pst.setString(2, addressField.getText());
            pst.setString(3, phoneField.getText());
            pst.setString(4, (String) categoryBox.getSelectedItem());
            pst.setString(5, (String) drillingBox.getSelectedItem());
            pst.setString(6, (String) pumpBox.getSelectedItem());
            pst.setInt(7, Integer.parseInt(depthField.getText()));
            pst.setDouble(8, Double.parseDouble(plumbingField.getText()));
            pst.setDouble(9, extractTotalFromOutput());

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record saved successfully!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error saving to database: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewRecords() {
        JFrame tableFrame = new JFrame("Saved Client Records");
        tableFrame.setSize(900, 400);
        tableFrame.setLocationRelativeTo(this);

        String[] columnNames =
                {"ID", "Name", "Address", "Phone", "Category", "Drilling", "Pump", "Depth", "Plumbing", "Total"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM clients")) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("category"),
                        rs.getString("drilling"),
                        rs.getString("pump"),
                        rs.getInt("depth"),
                        rs.getDouble("plumbing"),
                        rs.getDouble("total")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading records: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        tableFrame.add(new JScrollPane(table));
        tableFrame.setVisible(true);
    }

    private double extractTotalFromOutput() {
        for (String line : outputArea.getText().split("\n")) {
            if (line.contains("TOTAL PAYABLE:")) {
                String num = line.replaceAll("[^0-9.]", "");
                try {
                    return Double.parseDouble(num);
                } catch (NumberFormatException ex) {
                    return 0.0;
                }
            }
        }
        return 0.0;
    }

    private void calculate() {
        try {
            String category = (String) categoryBox.getSelectedItem();
            String drilling = (String) drillingBox.getSelectedItem();
            String pump = (String) pumpBox.getSelectedItem();

            int depth = Integer.parseInt(depthField.getText());
            double plumbing = Double.parseDouble(plumbingField.getText());

            double survey = surveyFees.get(category);
            double local = localFees.get(category);
            double drillFee = drillingFees.get(drilling);
            double pumpCost = pumpCosts.get(pump);
            double depthCost = getDepthCost(depth);

            double subtotal =
                    survey + local + drillFee + pumpCost + depthCost + plumbing;

            double tax = subtotal * 0.16;
            double total = subtotal + tax;

            outputArea.setText("----- UZIMA CLIENT BILL -----\n");
            outputArea.append("Client: " + nameField.getText() + "\n");
            outputArea.append("Category: " + category + "\n");
            outputArea.append("Drilling: " + drilling + " (Ksh " + drillFee + ")\n");
            outputArea.append("Pump: " + pump + " (Ksh " + pumpCost + ")\n");
            outputArea.append("Depth: " + depth + " m (Ksh " + depthCost + ")\n");
            outputArea.append("Survey + Local: Ksh " + (survey + local) + "\n");
            outputArea.append("Plumbing: Ksh " + plumbing + "\n");
            outputArea.append("------------------------------\n");
            outputArea.append(String.format("Subtotal: Ksh %.2f\n", subtotal));
            outputArea.append(String.format("Tax (16%%): Ksh %.2f\n", tax));
            outputArea.append(String.format("TOTAL PAYABLE: Ksh %.2f\n", total));
            outputArea.append("------------------------------\n");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numeric values for depth and plumbing.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double getDepthCost(int depth) {
        if (depth <= 100) return depth * 1000;
        else if (depth <= 200) return depth * 1500;
        else if (depth <= 300) return depth * 2000;
        else return depth * 2500;
    }

    private void clearFields() {
        nameField.setText("");
        addressField.setText("");
        phoneField.setText("");
        depthField.setText("");
        plumbingField.setText("");
        outputArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UzimaBoreholeSystem frame = new UzimaBoreholeSystem();
            frame.setVisible(true);
        });
    }
}
