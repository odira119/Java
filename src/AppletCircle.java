import javax.swing.*;
import java.awt.*;

public class AppletCircle {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Draw Circle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setBackground(Color.WHITE);
        frame.add(new CirclePanel());
        frame.setVisible(true);
    }
}

class CirclePanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        int radius = 185;   // 5 cm approx
        int diameter = radius * 2;
        g.drawOval(50, 50, diameter, diameter);
    }
}
