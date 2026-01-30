import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Color;

public class AppletCircle extends Applet {

    public void init() {
        setSize(500, 500);
        setBackground(Color.WHITE);
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        int radius = 185;   // 5 cm approx
        int diameter = radius * 2;
        g.drawOval(50, 50, diameter, diameter);
    }
}
