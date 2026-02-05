package UzimaBoreholeSystem.ui.login;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import UzimaBoreholeSystem.services.AuthService;
import UzimaBoreholeSystem.models.Client;
import UzimaBoreholeSystem.services.StaffService;
import UzimaBoreholeSystem.ui.admin.AdminDashboard;
import UzimaBoreholeSystem.ui.client.ClientDashboard;
import UzimaBoreholeSystem.models.Staff;
import UzimaBoreholeSystem.ui.client.ClientRegistrationForm;

import java.awt.*;
import java.awt.event.*;

public class LoginUI extends JFrame {
    private final AuthService authService;
    private final StaffService staffService;
    
    // Login form components
    private JTextField clientEmailField;
    private JPasswordField clientPasswordField;
    private JTextField staffIdField;
    private JPasswordField staffPasswordField;
    private boolean clientPasswordVisible = false;
    private boolean staffPasswordVisible = false;
    
    // Colors
    private static final Color PRIMARY_GRADIENT_START = new Color(0, 180, 156);
    private static final Color PRIMARY_GRADIENT_END = new Color(64, 145, 200);
    private static final Color CLIENT_ACCENT = new Color(52, 152, 219);
    private static final Color CLIENT_ACCENT_HOVER = new Color(41, 128, 185);
    private static final Color ADMIN_ACCENT = new Color(155, 89, 182);
    private static final Color ADMIN_ACCENT_HOVER = new Color(142, 68, 173);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color SUCCESS_HOVER = new Color(39, 174, 96);
    private static final Color TEXT_DARK = new Color(44, 62, 80);
    private static final Color TEXT_MUTED = new Color(127, 140, 141);
    private static final Color BORDER_COLOR = new Color(200, 205, 210);
    
    public LoginUI() {
        this.authService = new AuthService();
        this.staffService = new StaffService();
        
        setTitle("Uzima Borehole System - Welcome");
        setSize(1100, 750);
        setMinimumSize(new Dimension(900, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        
        initComponents();
    }
    
    private void initComponents() {
        // Main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(
                    0, 0, PRIMARY_GRADIENT_START,
                    getWidth(), getHeight(), PRIMARY_GRADIENT_END
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        
        // Cards Panel
        JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        cardsPanel.setOpaque(false);
        cardsPanel.add(createClientLoginCard());
        cardsPanel.add(createAdminLoginCard());
        
        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setOpaque(false);
        footerPanel.setBorder(new EmptyBorder(10, 0, 20, 0));
        JLabel footerLabel = new JLabel("© 2026 Uzima Borehole System. All rights reserved.");
        footerLabel.setForeground(new Color(255, 255, 255, 180));
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerPanel.add(footerLabel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(cardsPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(new EmptyBorder(40, 20, 30, 20));
        
        // Water Drop Icon
        JLabel iconLabel = new JLabel("\uD83D\uDCA7");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Title
        JLabel titleLabel = new JLabel("UZIMA BOREHOLE SYSTEM");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Water for Life \u2022 Maisha Bora");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(iconLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(subtitleLabel);
        
        return headerPanel;
    }
    
    private JPanel createClientLoginCard() {
        // Outer wrapper panel with border
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(new LineBorder(CLIENT_ACCENT, 3, true));
        wrapper.setPreferredSize(new Dimension(380, 480));
        wrapper.setMaximumSize(new Dimension(380, 480));
        
        // Inner content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        
        // Icon
        JLabel iconLabel = new JLabel("\uD83D\uDC64");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Title
        JLabel titleLabel = new JLabel("Client Portal");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(CLIENT_ACCENT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(iconLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(25));
        
        // Email Field
        contentPanel.add(createFieldLabel("Email Address"));
        contentPanel.add(Box.createVerticalStrut(5));
        clientEmailField = createTextField("Enter your email address");
        contentPanel.add(clientEmailField);
        contentPanel.add(Box.createVerticalStrut(12));
        
        // Password Field
        contentPanel.add(createFieldLabel("Password"));
        contentPanel.add(Box.createVerticalStrut(5));
        JPanel clientPwdPanel = createClientPasswordFieldPanel();
        contentPanel.add(clientPwdPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Button Panel - Two buttons side by side
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setMaximumSize(new Dimension(350, 50));
        
        // Login Button
        JButton loginButton = new JButton("Sign In");
        loginButton.setBackground(CLIENT_ACCENT);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(145, 45));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setOpaque(true);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> handleClientLogin());
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(CLIENT_ACCENT_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(CLIENT_ACCENT);
            }
        });
        
        // Create Account Button
        JButton createAccountBtn = new JButton("Create Account");
        createAccountBtn.setBackground(SUCCESS_COLOR);
        createAccountBtn.setForeground(Color.WHITE);
        createAccountBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        createAccountBtn.setPreferredSize(new Dimension(145, 45));
        createAccountBtn.setFocusPainted(false);
        createAccountBtn.setBorderPainted(false);
        createAccountBtn.setOpaque(true);
        createAccountBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createAccountBtn.addActionListener(e -> openRegistrationForm());
        createAccountBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                createAccountBtn.setBackground(SUCCESS_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                createAccountBtn.setBackground(SUCCESS_COLOR);
            }
        });
        
        buttonPanel.add(loginButton);
        buttonPanel.add(createAccountBtn);
        contentPanel.add(buttonPanel);
        
        // Wrap content in JScrollPane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        wrapper.add(scrollPane, BorderLayout.CENTER);
        
        // Add Enter key listener
        KeyAdapter enterListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleClientLogin();
                }
            }
        };
        clientEmailField.addKeyListener(enterListener);
        clientPasswordField.addKeyListener(enterListener);
        
        return wrapper;
    }
    
    private JPanel createAdminLoginCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ADMIN_ACCENT, 3, true),
            new EmptyBorder(30, 40, 30, 40)
        ));
        card.setPreferredSize(new Dimension(380, 480));
        card.setMaximumSize(new Dimension(380, 480));
        
        // Icon
        JLabel iconLabel = new JLabel("\u2699");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Title
        JLabel titleLabel = new JLabel("Admin Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(ADMIN_ACCENT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(25));
        
        // Staff ID Field
        card.add(createFieldLabel("Staff ID"));
        card.add(Box.createVerticalStrut(5));
        staffIdField = createTextField("Enter your staff ID");
        card.add(staffIdField);
        card.add(Box.createVerticalStrut(15));
        
        // Password Field
        card.add(createFieldLabel("Password"));
        card.add(Box.createVerticalStrut(5));
        JPanel passwordPanel = createStaffPasswordFieldPanel();
        card.add(passwordPanel);
        card.add(Box.createVerticalStrut(25));
        
        // Login Button
        JButton loginButton = createButton("Sign In", ADMIN_ACCENT, ADMIN_ACCENT_HOVER);
        loginButton.addActionListener(e -> handleStaffLogin());
        card.add(loginButton);
        card.add(Box.createVerticalStrut(20));
        
        // Forgot Password Link
        JLabel forgotLink = new JLabel("Forgot your password?");
        forgotLink.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        forgotLink.setForeground(TEXT_MUTED);
        forgotLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        forgotLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(LoginUI.this,
                    "Please contact your system administrator to reset your password.",
                    "Password Recovery", JOptionPane.INFORMATION_MESSAGE);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                forgotLink.setForeground(ADMIN_ACCENT);
                forgotLink.setText("<html><u>Forgot your password?</u></html>");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                forgotLink.setForeground(TEXT_MUTED);
                forgotLink.setText("Forgot your password?");
            }
        });
        card.add(forgotLink);
        
        // Add Enter key listener
        staffIdField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    staffPasswordField.requestFocus();
                }
            }
        });
        staffPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleStaffLogin();
                }
            }
        });
        
        return card;
    }
    
    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT_DARK);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
    
    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setMaximumSize(new Dimension(300, 40));
        field.setPreferredSize(new Dimension(300, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Placeholder behavior
        field.setForeground(Color.GRAY);
        field.setText(placeholder);
        
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(TEXT_DARK);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(CLIENT_ACCENT, 2, true),
                    new EmptyBorder(7, 11, 7, 11)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(BORDER_COLOR, 1, true),
                    new EmptyBorder(8, 12, 8, 12)
                ));
            }
        });
        
        return field;
    }
    
    private JPanel createClientPasswordFieldPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(300, 40));
        panel.setPreferredSize(new Dimension(300, 40));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        clientPasswordField = new JPasswordField(20);
        clientPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        clientPasswordField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        
        // Placeholder
        clientPasswordField.setEchoChar((char) 0);
        clientPasswordField.setForeground(Color.GRAY);
        clientPasswordField.setText("Enter your password");
        
        clientPasswordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(clientPasswordField.getPassword()).equals("Enter your password")) {
                    clientPasswordField.setText("");
                    clientPasswordField.setForeground(TEXT_DARK);
                    clientPasswordField.setEchoChar('\u2022');
                }
                clientPasswordField.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(CLIENT_ACCENT, 2, true),
                    new EmptyBorder(7, 11, 7, 11)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (clientPasswordField.getPassword().length == 0) {
                    clientPasswordField.setEchoChar((char) 0);
                    clientPasswordField.setText("Enter your password");
                    clientPasswordField.setForeground(Color.GRAY);
                }
                clientPasswordField.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(BORDER_COLOR, 1, true),
                    new EmptyBorder(8, 12, 8, 12)
                ));
            }
        });
        
        // Toggle button
        JButton toggleBtn = new JButton("\uD83D\uDC41");
        toggleBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        toggleBtn.setPreferredSize(new Dimension(40, 40));
        toggleBtn.setFocusPainted(false);
        toggleBtn.setBorderPainted(false);
        toggleBtn.setContentAreaFilled(false);
        toggleBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleBtn.addActionListener(e -> {
            clientPasswordVisible = !clientPasswordVisible;
            if (clientPasswordVisible) {
                clientPasswordField.setEchoChar((char) 0);
                toggleBtn.setText("\uD83D\uDD12");
            } else {
                if (!String.valueOf(clientPasswordField.getPassword()).equals("Enter your password")) {
                    clientPasswordField.setEchoChar('\u2022');
                }
                toggleBtn.setText("\uD83D\uDC41");
            }
        });
        
        panel.add(clientPasswordField, BorderLayout.CENTER);
        panel.add(toggleBtn, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createStaffPasswordFieldPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(300, 40));
        panel.setPreferredSize(new Dimension(300, 40));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        staffPasswordField = new JPasswordField(20);
        staffPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        staffPasswordField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        
        // Placeholder
        staffPasswordField.setEchoChar((char) 0);
        staffPasswordField.setForeground(Color.GRAY);
        staffPasswordField.setText("Enter your password");
        
        staffPasswordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(staffPasswordField.getPassword()).equals("Enter your password")) {
                    staffPasswordField.setText("");
                    staffPasswordField.setForeground(TEXT_DARK);
                    staffPasswordField.setEchoChar('\u2022');
                }
                staffPasswordField.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(ADMIN_ACCENT, 2, true),
                    new EmptyBorder(7, 11, 7, 11)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (staffPasswordField.getPassword().length == 0) {
                    staffPasswordField.setEchoChar((char) 0);
                    staffPasswordField.setText("Enter your password");
                    staffPasswordField.setForeground(Color.GRAY);
                }
                staffPasswordField.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(BORDER_COLOR, 1, true),
                    new EmptyBorder(8, 12, 8, 12)
                ));
            }
        });
        
        // Toggle button
        JButton toggleBtn = new JButton("\uD83D\uDC41");
        toggleBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        toggleBtn.setPreferredSize(new Dimension(40, 40));
        toggleBtn.setFocusPainted(false);
        toggleBtn.setBorderPainted(false);
        toggleBtn.setContentAreaFilled(false);
        toggleBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleBtn.addActionListener(e -> {
            staffPasswordVisible = !staffPasswordVisible;
            if (staffPasswordVisible) {
                staffPasswordField.setEchoChar((char) 0);
                toggleBtn.setText("\uD83D\uDD12");
            } else {
                if (!String.valueOf(staffPasswordField.getPassword()).equals("Enter your password")) {
                    staffPasswordField.setEchoChar('\u2022');
                }
                toggleBtn.setText("\uD83D\uDC41");
            }
        });
        
        panel.add(staffPasswordField, BorderLayout.CENTER);
        panel.add(toggleBtn, BorderLayout.EAST);
        
        return panel;
    }
    
    private JButton createButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setMaximumSize(new Dimension(300, 42));
        button.setPreferredSize(new Dimension(300, 42));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private String getFieldText(JTextField field, String placeholder) {
        String text = field.getText().trim();
        return text.equals(placeholder) ? "" : text;
    }
    
    private void handleClientLogin() {
        String email = getFieldText(clientEmailField, "Enter your email address");
        String password = String.valueOf(clientPasswordField.getPassword());
        
        if (password.equals("Enter your password")) {
            password = "";
        }
        
        if (email.isEmpty()) {
            showWarning("Please enter your Email Address");
            clientEmailField.requestFocus();
            return;
        }
        
        if (password.isEmpty()) {
            showWarning("Please enter your Password");
            clientPasswordField.requestFocus();
            return;
        }
        
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        final String finalPassword = password;
        final String finalEmail = email;
        
        SwingWorker<Client, Void> worker = new SwingWorker<Client, Void>() {
            @Override
            protected Client doInBackground() {
                return authService.authenticateClientByEmail(finalEmail, finalPassword);
            }
            
            @Override
            protected void done() {
                setCursor(Cursor.getDefaultCursor());
                try {
                    Client client = get();
                    if (client != null) {
                        JOptionPane.showMessageDialog(LoginUI.this,
                            "Welcome back, " + client.getName() + "!",
                            "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new ClientDashboard(client).setVisible(true);
                    } else {
                        showError("Invalid Email or Password.\nPlease check your credentials and try again.");
                        clientPasswordField.setText("");
                        clientPasswordField.setEchoChar('\u2022');
                        clientPasswordField.setForeground(TEXT_DARK);
                        clientPasswordField.requestFocus();
                    }
                } catch (Exception e) {
                    String errorMsg = "Database connection error.\n\n" +
                        "Please ensure:\n" +
                        "1. MySQL server is running\n" +
                        "2. Database 'uzima_db' exists\n" +
                        "3. MySQL connector JAR is in classpath\n\n" +
                        "Error: " + e.getMessage();
                    showError(errorMsg);
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }
    
    private void handleStaffLogin() {
        String staffId = getFieldText(staffIdField, "Enter your staff ID");
        String password = String.valueOf(staffPasswordField.getPassword());
        
        if (password.equals("Enter your password")) {
            password = "";
        }
        
        if (staffId.isEmpty()) {
            showWarning("Please enter your Staff ID");
            staffIdField.requestFocus();
            return;
        }
        
        if (password.isEmpty()) {
            showWarning("Please enter your Password");
            staffPasswordField.requestFocus();
            return;
        }
        
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        final String finalPassword = password;
        
        SwingWorker<Staff, Void> worker = new SwingWorker<Staff, Void>() {
            @Override
            protected Staff doInBackground() {
                return staffService.authenticateStaff(staffId, finalPassword);
            }
            
            @Override
            protected void done() {
                setCursor(Cursor.getDefaultCursor());
                try {
                    Staff staff = get();
                    if (staff != null) {
                        JOptionPane.showMessageDialog(LoginUI.this,
                            "Welcome, " + staff.getName() + "!",
                            "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new AdminDashboard(staff).setVisible(true);
                    } else {
                        showError("Invalid Staff ID or Password.\nPlease check your credentials and try again.");
                        staffPasswordField.setText("");
                        staffPasswordField.setEchoChar('\u2022');
                        staffPasswordField.setForeground(TEXT_DARK);
                        staffPasswordField.requestFocus();
                    }
                } catch (Exception e) {
                    String errorMsg = "Database connection error.\n\n" +
                        "Please ensure:\n" +
                        "1. MySQL server is running\n" +
                        "2. Database 'uzima_db' exists\n" +
                        "3. MySQL connector JAR is in classpath\n\n" +
                        "Error: " + e.getMessage();
                    showError(errorMsg);
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }
    
    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Missing Information", JOptionPane.WARNING_MESSAGE);
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Login Failed", JOptionPane.ERROR_MESSAGE);
    }
    
    private void openRegistrationForm() {
        dispose();
        new ClientRegistrationForm().setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginUI().setVisible(true);
        });
    }
}
