package rojochile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.util.Random;

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
        prev = new Point(x + Level.startPosX, y + Level.startPosY);
    }

    public void move(MouseEvent e) {
        xa = e.getXOnScreen() - prev.x;
        ya = e.getYOnScreen() - prev.getLocation().y;
        if (!RojoChile.paused) {
            if (x + xa > Camera.shot.x && x + xa < Camera.shot.x + Camera.shot.width - diameter) {
                x += xa;
            }
            if (y + ya < Camera.shot.y + Camera.shot.height - diameter && y + ya > Camera.shot.y) {
                y += ya;
            }
        } else {
            x = MouseInfo.getPointerInfo().getLocation().x + Camera.shot.x - RojoChile.borderSize;
            y = MouseInfo.getPointerInfo().getLocation().y + Camera.shot.y - RojoChile.wideBorderSize;
        }
        prev = MouseInfo.getPointerInfo().getLocation();
    }

    public void paint(Graphics2D g) {
        if (visible) {
            g.setColor(new Color(252,234,40));
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.fillOval(x - Camera.shot.x - Math.round(diameter / 2), y - Camera.shot.y - Math.round(diameter / 2), diameter, diameter);
        }
    }

    public static double getAngle() {
        double angle;
        Point a = new Point(Vato.x + Math.round(Vato.width / 2), Vato.y + +Math.round(Vato.height / 2));
        Point b = upperQuad() ? new Point(x, y) : new Point(x, y + 2 * ((Vato.y + Math.round(Vato.height / 2)) - y));
        Point c = new Point(x, Vato.y + +Math.round(Vato.height / 2));
        double ab = Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2));
        double ac = Math.sqrt(Math.pow((a.x - c.x), 2) + Math.pow((a.y - c.y), 2));
        angle = Math.acos(ac / ab);
        return angle;
    }

    public static boolean upperQuad() {
        return y > Vato.y + Math.round(Vato.height / 2);
    }

    public static boolean rightQuad() {
        return x > Vato.x + Math.round(Vato.width / 2);
    }

    public static boolean upperQuadDev() {
        Random r = new Random();
        return y + r.nextInt(50) - r.nextInt(50) > Vato.y + Math.round(Vato.height / 2);
    }

    public static boolean rightQuadDev() {
        Random r = new Random();
        return x + r.nextInt(50) - r.nextInt(50) > Vato.x + Math.round(Vato.width / 2);
    }
}
