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

    static int x = 0;
    static int y = 0;
    static int xa;
    static int ya;
    static int diameter = 8;
    static boolean visible = false;

    public FakeMouse(int centerW, int centerH) {
        x = centerW - Math.round(diameter / 2) + Level.startPosX;
        y = centerH - Math.round(diameter / 2) + Level.startPosY;
    }

    public void move(MouseEvent e) {
        Point mousePos = MouseInfo.getPointerInfo().getLocation();
        Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        if (!RojoChile.paused) {
            xa = e.getXOnScreen() - mousePos.x;
            ya = e.getYOnScreen() - mousePos.getLocation().y;
            if (x + xa > 0 && x + xa < RojoChile.mapWidth - diameter) {
                x += xa;
            }
            if (y + diameter + ya < RojoChile.mapHeight && y + ya > 0) {
                y += ya;
            }
        } else {
            if (mousePos.x > 0 && mousePos.x < bounds.width - diameter) {
                x = mousePos.x + Camera.shot.x;
            }
            if (mousePos.y + diameter < bounds.height && mousePos.y > 0) {
                y = mousePos.y + Camera.shot.y;
            }
        }
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
