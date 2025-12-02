package UzimaBoreholeSystem;
import ui.login.LoginUI;
import javax.swing.*;
import UzimaBoreholeSystem.database.DatabaseInitializer;

public class Main {
    
    public static void main(String[] args) {
        try {
    
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            DatabaseInitializer.initialize();
            
            SwingUtilities.invokeLater(() -> {
                LoginUI loginUI = new LoginUI();
                loginUI.setVisible(true);
            });
            
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error starting application: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            UzimaBoreholeSystem.database.DBConnection.closeConnection();
        }));
    }
}