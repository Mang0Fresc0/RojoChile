package rojochile;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Vato {

    static int x;
    static int y;
    static int xa = 0;
    static int ya = 0;
    static int w = 30;
    static int h = 50;

    int sup = 0;
    private RojoChile game;
    static int s = 0;

    public Vato(RojoChile game) {
        this.game = game;
        x = (int) Math.round(Camera.shot.getCenterX() - w / 2);
        y = (int) Math.round(Camera.shot.getCenterY() - h / 2);
    }

    public void move() {
        if (x + xa > 0 && x + xa < RojoChile.mapWidth - w) {
            x += xa;
        }
        if (y + h + ya < RojoChile.mapHeight && y + ya > 0) {
            y += ya;
        }
    }

    public void paint(Graphics2D g) {
        g.fillRect(x - Camera.shot.x, y - Camera.shot.y, w, h);
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            xa = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            xa = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            ya = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            ya = 0;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            xa = -4;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            xa = 4;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            ya = -4;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            ya = 4;
        }
    }

    public static Rectangle getPos() {
        Rectangle r = new Rectangle(x, y, w, h);
        return r;
    }

}
