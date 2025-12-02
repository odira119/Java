import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Table{
    private static DefaultTableModel model;
    private static JTextField nameField,idField,ageField,phoneField,residenceField;
    private static JTable table;
    private static DatabaseService dbService = new DatabaseService();

    public static void main(String[]args){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "MySQL JDBC Driver not found: " + e.getMessage());
            return; 
        }

        dbService.createDatabaseAndTable();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        JFrame frame = new JFrame("People Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBorder(new EmptyBorder(16, 16, 16, 16));

        model = new DefaultTableModel(new Object[]{"Name", "ID", "Age", "Phone", "Residence"}, 0);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(22);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane tableScroll = new JScrollPane(table);
        root.add(tableScroll, BorderLayout.CENTER);

        // INPUT PANEL
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add / Edit Record"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(16);
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        idField = new JTextField(10);
        inputPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        ageField = new JTextField(6);
        inputPanel.add(ageField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(12);
        inputPanel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        inputPanel.add(new JLabel("Residence:"), gbc);
        gbc.gridx = 1;
        residenceField = new JTextField(16);
        inputPanel.add(residenceField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Save");
        addButton.addActionListener(e -> addRecord());
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(e -> deleteRecord());
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(buttonPanel, gbc);

        root.add(inputPanel, BorderLayout.NORTH);

        frame.setContentPane(root);
        frame.setSize(720, 480);
        frame.setLocationRelativeTo(null);

        // Load data into table model
        loadRecords();
        frame.setVisible(true);
    }

    private static void addRecord() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String phone = phoneField.getText();
            String residence = residenceField.getText();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Name cannot be empty!");
                return;
            }

            model.addRow(new Object[]{name, id, age, phone, residence});
            dbService.savePerson(id, name, age, phone, residence);

            nameField.setText("");
            idField.setText("");
            ageField.setText("");
            phoneField.setText("");
            residenceField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "ID and Age must be numbers!");
        }
    }

    private static void deleteRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a record to delete!");
            return;
        }

        int id = (int) model.getValueAt(selectedRow, 1);
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Delete this record?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            model.removeRow(selectedRow);
            dbService.deletePerson(id);
            JOptionPane.showMessageDialog(null, "Record deleted successfully!");
        }
    }

    private static void loadRecords() {
        model.setRowCount(0);
        List<DatabaseService.Person> people = dbService.loadAllPeople();
        for (DatabaseService.Person p : people) {
            model.addRow(new Object[]{p.getName(), p.getId(), p.getAge(), p.getPhone(), p.getResidence()});
        }
    }
}
