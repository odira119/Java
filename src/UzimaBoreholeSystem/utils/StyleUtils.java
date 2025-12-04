package UzimaBoreholeSystem.utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class StyleUtils {
    
    // Color scheme
    public static final Color PRIMARY_COLOR = new Color(44, 62, 80);
    public static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    public static final Color ACCENT_COLOR = new Color(231, 76, 60);
    public static final Color SUCCESS_COLOR = new Color(39, 174, 96);
    public static final Color WARNING_COLOR = new Color(243, 156, 18);
    public static final Color LIGHT_BG = new Color(236, 240, 241);
    public static final Color CARD_BG = Color.WHITE;
    public static final Color TEXT_COLOR = new Color(44, 62, 80);
    
    //Style button
    public static void styleButton(JButton button, String type) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        switch (type.toLowerCase()) {
            case "primary":
                button.setBackground(SECONDARY_COLOR);
                button.setForeground(Color.WHITE);
                break;
                
            case "success":
                button.setBackground(SUCCESS_COLOR);
                button.setForeground(Color.WHITE);
                break;
                
            case "danger":
                button.setBackground(ACCENT_COLOR);
                button.setForeground(Color.WHITE);
                break;
                
            case "warning":
                button.setBackground(WARNING_COLOR);
                button.setForeground(Color.WHITE);
                break;
        }
    }
    
    // Style text field
    public static void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
    }
    
    //Style a password field
    public static void stylePasswordField(JPasswordField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
    }
    
    //Style combo box
    public static void styleComboBox(JComboBox<?> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setBackground(Color.WHITE);
    }
    
    //Create card panel
    public static JPanel createCard(String title, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        card.setPreferredSize(new Dimension(200, 120));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(new Color(127, 140, 141));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(valueLabel);
        
        return card;
    }
    
    //Create label
    public static JLabel createLabel(String text, int fontSize, int fontStyle) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", fontStyle, fontSize));
        label.setForeground(TEXT_COLOR);
        return label;
    }
    
    //Create a titled pane with styling
    public static JPanel createTitledPane(String title) {
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.setBackground(CARD_BG);
        pane.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = createLabel(title, 18, Font.BOLD);
        pane.add(titleLabel);
        pane.add(Box.createVerticalStrut(15));
        
        return pane;
    }
    
    //Show styled alert
    public static void showAlert(int messageType, String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }
}
