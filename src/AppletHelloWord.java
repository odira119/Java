import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;

public class AppletHelloWord extends JPanel {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("Hello World", 20, 20);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello World");
        AppletHelloWord panel = new AppletHelloWord();
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
