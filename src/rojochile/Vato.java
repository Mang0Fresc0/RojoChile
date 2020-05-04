package rojochile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Vato {

    static int x;
    static int y;
    static int xa = 0;
    static int ya = 0;
    static int width = 30;
    static int height = 50;
    static boolean inv = false;
    static int invCount = 0;
    static int hp = 500;
    static int energy = 500;
    static int delay = 0;
    static boolean shooting = false;
    static boolean bouncing = false;
    static boolean parrying = false;
    static boolean vulnerable = false;
    static ArrayList<Bala> balas = new ArrayList<>();

    public Vato() {
        x = Math.round(Camera.centerShot.x - width / 2);
        y = Math.round(Camera.centerShot.y - height / 2);
    }

    public static void shoot() {
        if (energy > 400) {
            balas.add(new Bala(x + Math.round(width / 2), y + Math.round(height / 2)));
            energy -= 20;
        } else if (energy > 0 && delay == 0) {
            delay = (int) Math.round(10000 / energy);
            shooting = true;
        }
    }

    public static void specialShot() {
        Random r = new Random();
        hp -= r.nextInt(200);
        for (int i = 0; i < 100; i++) {
            balas.add(new Bala(x + Math.round(width / 2), y + Math.round(height / 2), r.nextInt(262)));

        }
    }

    public void parry() {
    }

    public void move() {
        if (shooting) {
            if (delay > 0) {
                delay--;
            } else {
                balas.add(new Bala(x + Math.round(width / 2), y + Math.round(height / 2)));
                energy -= 20;
                shooting = false;
            }
        }
        if (delay != 0) {
            delay--;
        }
        if (x + xa > 0 && x + xa < RojoChile.mapWidth - width) {
            x += xa;
            FakeMouse.x += xa;
        }
        if (y + height + ya < RojoChile.mapHeight && y + ya > 0) {
            y += ya;
            FakeMouse.y += ya;
        }

        if (hp < 1) {
            RojoChile.gameOver();
        }
        if (invCount > 0) {
            inv = true;
            invCount--;
        } else {
            inv = false;
        }
        for (Bala i : balas) {
            if (i != null) {
                i.movimiento();
                if (!Camera.loadArea.intersects(i.x, i.y, i.diameter, i.diameter)) {
                    i = null;
                }
            }
        }
    }

    public void paint(Graphics2D g) {
        g.setColor(Color.red);
        g.fillRect(10, 10, hp, 5);
        g.setColor(Color.cyan);
        g.fillRect(10, 25, energy, 5);
        g.setColor(Color.black);
        g.drawRect(10, 10, hp, 5);
        g.drawRect(10, 25, energy, 5);
        if (invCount % 2 == 0) {
            g.fillRect(x - Camera.shot.x, y - Camera.shot.y, width, height);
        }
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);

        for (Bala i : balas) {
            i.pintar(g);
        }

        for (Bala i:balas) {
        i.pintar(g);   
    } 

    }

    public void keyReleased(KeyEvent e) {
        if (!bouncing) {
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
        if (e.getKeyCode() == KeyEvent.VK_SHIFT && !RojoChile.paused) {
            FakeMouse.visible = false;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (!bouncing && !FakeMouse.visible) {
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
        if (e.getKeyCode() == KeyEvent.VK_SHIFT && xa == 0 && ya == 0) {
            FakeMouse.visible = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            parry();
        }

    }

    public static void bounce(int b, int xo, int yo) {
        bouncing = true;
        xa += b * xo;
        ya += b * yo;
    }

    public static void hurt(int dmg) {
        if (!inv) {
            hp -= dmg;
            inv = true;
            invCount = 100;
        }
    }

    public static void hurt(int dmg, int tire) {
        if (!inv) {
            hp -= dmg;
            energy -= tire;
            invCount = 100;
        }
    }

    public static Rectangle getPos() {
        Rectangle r = new Rectangle(x, y, width, height);
        return r;
    }

    public static Rectangle close() {
        int range = 200;
        Rectangle r = new Rectangle(x - range, y - range, width + 2 * range, height + 2 * range);
        return r;
    }

}
