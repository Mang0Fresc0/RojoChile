package rojochile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Vato {

    static int x;
    static int y;
    static int xa = 0;
    static int ya = 0;
    static int width = 30;
    static int height = 50;
    static boolean inv = false;
    static int invCount = 0;
    static int health = 500;
    static int stamina = 500;

    int sup = 0;
    private RojoChile game;
    static int s = 0;

    public Vato(RojoChile game) {
        this.game = game;
        x = (int) Math.round(Camera.shot.getCenterX() - width / 2);
        y = (int) Math.round(Camera.shot.getCenterY() - height / 2);
    }

    public void move() {
        if (x + xa > 0 && x + xa < RojoChile.mapWidth - width) {
            x += xa;
        }
        if (y + height + ya < RojoChile.mapHeight && y + ya > 0) {
            y += ya;
        }
        if (health < 1) {
            game.gameOver();
        }
        if (invCount > 0) {
            inv = true;
            invCount--;
        } else {
            inv = false;
        }
    }

    public void paint(Graphics2D g) {
        g.setColor(Color.red);
        g.fillRect(10, 10, health, 5);
        g.setColor(Color.green);
        g.fillRect(10, 25, stamina, 5);
        g.setColor(Color.black);
        g.drawRect(10, 10, health, 5);
        g.drawRect(10, 25, stamina, 5);
        if (invCount % 2 == 0) {
            g.fillRect(x - Camera.shot.x, y - Camera.shot.y, width, height);
        }
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

    public static void bounce() {
        x -= 10 * xa;
        y -= 10 * ya;
        Camera.displace(-10 * xa, -10 * ya);
    }

    public static void hurt(int dmg) {
        if (!inv) {
            health -= dmg;
            inv = true;
            invCount = 100;
        }
    }

    public static void hurt(int dmg, int tire) {
        if (!inv) {
            health -= dmg;
            stamina -= tire;
            invCount = 100;
        }
    }

    public static Rectangle getPos() {
        Rectangle r = new Rectangle(x, y, width, height);
        return r;
    }

    public static Rectangle close() {
        int rangeX = 400;
        int rangeY = 400;
        Rectangle r = new Rectangle(x - rangeX, y - rangeY, width + 2 * rangeX, height + 2 * rangeY);
        return r;
    }
}
