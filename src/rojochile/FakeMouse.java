package rojochile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

public class FakeMouse {

    static int x;
    static int y;
    static int xa;
    static int ya;
    static int diameter = 8;
    static boolean visible = false;
    static Point prev;

    public FakeMouse() {
        //la posicion inicial no es correcta
        x = Camera.centerShot.x;
        y = Camera.centerShot.y;
        prev = new Point(x - Level.startPosX, y - Level.startPosY);
    }

    public void move(MouseEvent e) {
        Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        xa = e.getXOnScreen() - prev.x;
        System.out.println(e.getYOnScreen());
        System.out.println(prev.y);
        ya = e.getYOnScreen() - prev.getLocation().y;
        if (!RojoChile.paused) {
            if (x + xa > 0 && x + xa < RojoChile.mapWidth - diameter) {
                x += xa;
            }
            if (y + diameter + ya < RojoChile.mapHeight && y + ya > 0) {
                y += ya;
            }
        } else {
            if (x + xa > Camera.shot.x && x + xa < Camera.shot.x + RojoChile.W - diameter) {
                x += xa;
            }
            if (y + diameter + ya < Camera.shot.y + RojoChile.H && y + ya > Camera.shot.y) {
                y += ya;
            }
        }
        prev = MouseInfo.getPointerInfo().getLocation();
    }

    public void paint(Graphics2D g) {
        if (visible) {
            g.setColor(Color.white);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.fillOval(x - Camera.shot.x - Math.round(diameter / 2), y - Camera.shot.y - Math.round(diameter / 2), diameter, diameter);
        }
    }
}
