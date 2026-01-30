import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PhoneBillingSystem extends JFrame {

    // Modern color scheme
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color DARK_BG = new Color(44, 62, 80);
    private static final Color LIGHT_BG = new Color(236, 240, 241);
    private static final Color TEXT_COLOR = new Color(52, 73, 94);

    private JLabel statusLabel, timerLabel;
    private JCheckBox sameNetworkCheck;
    private JButton startBtn, endBtn;
    private JTextArea receiptArea;

    private Timer callTimer;
    private int secondsPassed = 0;

    private LocalTime startTime, endTime;

    public PhoneBillingSystem() {
        setTitle("Phone Billing System");
        setSize(520, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(LIGHT_BG);
        mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        
        // Timer Panel
        JPanel timerPanel = createTimerPanel();
        
        // Controls Panel
        JPanel controlsPanel = createControlsPanel();
        
        // Receipt Panel
        JPanel receiptPanel = createReceiptPanel();

        // Add all panels
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(timerPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(controlsPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(receiptPanel);

        add(mainPanel);

        // Timer for call duration
        callTimer = new Timer(1000, e -> updateTimer());

        // Button 
        startBtn.addActionListener(e -> startCall());
        endBtn.addActionListener(e -> endCall());
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(LIGHT_BG);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Phone Billing System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(DARK_BG);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        statusLabel = new JLabel("● Ready");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        statusLabel.setForeground(TEXT_COLOR);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(statusLabel);

        return panel;
    }

    private JPanel createTimerPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(DARK_BG);
        panel.setBorder(new CompoundBorder(
            new LineBorder(PRIMARY_COLOR, 2, true),
            new EmptyBorder(20, 40, 20, 40)
        ));
        panel.setMaximumSize(new Dimension(460, 110));

        timerLabel = new JLabel("00:00:00");
        timerLabel.setFont(new Font("Consolas", Font.BOLD, 52));
        timerLabel.setForeground(Color.WHITE);

        panel.add(timerLabel);
        return panel;
    }

    private JPanel createControlsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(LIGHT_BG);
        panel.setBorder(new CompoundBorder(
            new TitledBorder(new LineBorder(PRIMARY_COLOR, 1, true), 
                " Call Options ", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12), PRIMARY_COLOR),
            new EmptyBorder(10, 15, 15, 15)
        ));
        panel.setMaximumSize(new Dimension(460, 150));

        // Network checkbox with modern styling
        sameNetworkCheck = new JCheckBox("  Same Network Call");
        sameNetworkCheck.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sameNetworkCheck.setBackground(LIGHT_BG);
        sameNetworkCheck.setForeground(TEXT_COLOR);
        sameNetworkCheck.setFocusPainted(false);
        sameNetworkCheck.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(LIGHT_BG);

        startBtn = createStyledButton("▶  Start Call", SUCCESS_COLOR);
        endBtn = createStyledButton("■  End Call", DANGER_COLOR);
        endBtn.setEnabled(false);

        buttonPanel.add(startBtn);
        buttonPanel.add(endBtn);

        panel.add(sameNetworkCheck);
        panel.add(Box.createVerticalStrut(10));
        panel.add(buttonPanel);

        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(bgColor.darker());
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private JPanel createReceiptPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHT_BG);
        panel.setBorder(new CompoundBorder(
            new TitledBorder(new LineBorder(PRIMARY_COLOR, 1, true), 
                " Billing Receipt ", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12), PRIMARY_COLOR),
            new EmptyBorder(10, 10, 10, 10)
        ));
        panel.setMaximumSize(new Dimension(460, 340));

        receiptArea = new JTextArea(14, 40);
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        receiptArea.setBackground(Color.WHITE);
        receiptArea.setForeground(TEXT_COLOR);
        receiptArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(receiptArea);
        scrollPane.setBorder(new LineBorder(new Color(189, 195, 199), 1));

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void startCall() {
        secondsPassed = 0;
        timerLabel.setText("00:00:00");

        startTime = LocalTime.now();
        statusLabel.setText("● Call in Progress...");
        statusLabel.setForeground(SUCCESS_COLOR);
        callTimer.start();

        startBtn.setEnabled(false);
        endBtn.setEnabled(true);
        receiptArea.setText("");
    }

    private void updateTimer() {
        secondsPassed++;
        int hrs = secondsPassed / 3600;
        int mins = (secondsPassed % 3600) / 60;
        int secs = secondsPassed % 60;

        timerLabel.setText(String.format("%02d:%02d:%02d", hrs, mins, secs));
    }

    private void endCall() {
        callTimer.stop();
        endTime = LocalTime.now();

        statusLabel.setText("● Call Ended");
        statusLabel.setForeground(DANGER_COLOR);
        startBtn.setEnabled(true);
        endBtn.setEnabled(false);

        generateReceipt();
    }

    private void generateReceipt() {
        boolean sameNetwork = sameNetworkCheck.isSelected();

        // Determine tariff (rate per second)
        String tariffType;
        double ratePerSecond;
        int hour = startTime.getHour();

        if (hour >= 8 && hour < 18) {
            tariffType = "Day Tariff (8AM - 6PM)";
            ratePerSecond = sameNetwork ? (4.00 / 60.0) : (10.00 / 60.0);
        } else {
            tariffType = "Night Tariff (6PM - 8AM)";
            ratePerSecond = sameNetwork ? (3.00 / 60.0) : (5.00 / 60.0);
        }

        // Calculate total cost
        double totalCost = secondsPassed * ratePerSecond;

        String networkText = sameNetwork ? "Same Network" : "Different Network";
        double ratePerMinute = ratePerSecond * 60;

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");

        int hrs = secondsPassed / 3600;
        int mins = (secondsPassed % 3600) / 60;
        int secs = secondsPassed % 60;
        String durationFormatted = String.format("%02d:%02d:%02d", hrs, mins, secs);

        // Clean modern receipt format
        receiptArea.setText(
            "\n" +
            "        ========================================\n" +
            "              PHONE BILLING RECEIPT\n" +
            "        ========================================\n" +
            "\n" +
            "        CALL DETAILS\n" +
            "\n" +
            "            Start Time     " + startTime.format(fmt) + "\n" +
            "            End Time       " + endTime.format(fmt) + "\n" +
            "            Duration       " + durationFormatted + " (" + secondsPassed + " sec)\n" +
            "\n" +
            "        ----------------------------------------\n" +
            "\n" +
            "        BILLING INFO\n" +
            "\n" +
            "            Network        " + networkText + "\n" +
            "            Tariff         " + tariffType.split(" \\(")[0] + "\n" +
            "            Rate/Min       Ksh " + String.format("%.2f", ratePerMinute) + "\n" +
            "\n" +
            "        ----------------------------------------\n" +
            "\n" +
            "        TOTAL\n" +
            "\n" +
            "            Amount Due     Ksh " + String.format("%.2f", totalCost) + "\n" +
            "\n" +
            "        ========================================\n" +
            "\n" +
            "           Thank you for using our service!\n"
        );
    }

    public static void main(String[] args) {
        // Use system look and feel for modern appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new PhoneBillingSystem().setVisible(true));
    }
}
