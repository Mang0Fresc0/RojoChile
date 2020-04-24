package rojochile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

public class FakeMouse {

    static int x = 0;
    static int y = 0;
    static int diameter = 8;

    public FakeMouse(int centerW, int centerH) {
        x = centerW - Math.round(diameter / 2);
        y = centerH - Math.round(diameter / 2);
    }

    public void move(MouseEvent e) {
        x += e.getX();
        y += e.getY();
        System.out.println(e.getXOnScreen());
    }

    public void paint(Graphics2D g) {
        g.setColor(Color.red);
         g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
        g.fillOval(x, y, diameter, diameter);
    }
}
