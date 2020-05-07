package rojochile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

public class Vato {

    static int animation = 0;

    Image backmove = new ImageIcon(this.getClass().getResource("Animaciones/Main/Move/backmove.gif")).getImage();
    Image frontmove = new ImageIcon(this.getClass().getResource("Animaciones/Main/Move/frontmove.gif")).getImage();
    Image rightmove = new ImageIcon(this.getClass().getResource("Animaciones/Main/Move/rightmove.gif")).getImage();
    Image leftmove = new ImageIcon(this.getClass().getResource("Animaciones/Main/Move/leftmove.gif")).getImage();

    Image backidle = new ImageIcon(this.getClass().getResource("Animaciones/Main/Idol/backidol 64.png")).getImage();
    Image frontidle = new ImageIcon(this.getClass().getResource("Animaciones/Main/Idol/frontidol 64.png")).getImage();
    Image rightidle = new ImageIcon(this.getClass().getResource("Animaciones/Main/Idol/rightidol 64.png")).getImage();
    Image leftidle = new ImageIcon(this.getClass().getResource("Animaciones/Main/Idol/leftidol 64.png")).getImage();

    Image recharge = new ImageIcon(this.getClass().getResource("Animaciones/Main/Attack/mainattack 64.gif")).getImage();

    //                           0         1          2           3         4         5         6           7         8
    Image[] animationArray = {backidle, leftidle, frontidle, rightidle, backmove, leftmove, frontmove, rightmove, recharge};
    Image Player = recharge;

    static int x;
    static int y;
    static int xa = 0;
    static int ya = 0;
    static int width = 55;
    static int height = 64;
    static boolean inv = false;
    static int invCount = 0;
    static int hp = 500;
    static int energy = 500;
    static int delay = 0;
    static boolean shooting = false;
    static boolean bouncing = false;
    static boolean parrying = false;
    static boolean vulnerable = false;
    static int parryCount = 0;
    static int vulCount = 0;
    static ArrayList<Bala> toDispose = new ArrayList<>();
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
        if (!parrying && !vulnerable) {
            parrying = true;
            parryCount = 10;
        }
    }

    public void move() throws IOException {
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
        if (parrying || vulnerable) {
            if (parryCount > 0) {
                parryCount--;
            } else if (!vulnerable) {
                parrying = false;
                vulnerable = true;
                vulCount = 60;
            }
            if (vulCount > 0) {
                vulCount--;
            } else {
                vulnerable = false;
            }
        }
        if (x + xa > 0 && x + xa < RojoChile.mapWidth - width && x + xa > Camera.loadArea.x && x + xa < Camera.loadArea.x + Camera.loadArea.width - width) {
            x += xa;
        }
        if (y + ya > 0 && y + height + ya < RojoChile.mapHeight && y + ya > Camera.loadArea.y && y + height + ya < Camera.loadArea.y + Camera.loadArea.height && y + ya > Camera.loadArea.y) {
            y += ya;
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
            i.movimiento();
            if (!Camera.loadArea.intersects(i.x, i.y, i.diameter, i.diameter)) {
                toDispose.add(i);
            }
        }
        dispose();
    }

    public void paint(Graphics2D g) {
        g.setColor(Color.red);
        g.fillRect(10, 10, hp, 5);
        g.setColor(new Color(242, 108, 241));
        g.fillRect(10, 25, energy, 5);
        g.setColor(Color.black);
        g.drawRect(10, 10, hp, 5);
        g.drawRect(10, 25, energy, 5);
        if (invCount % 2 == 0) {
            g.drawImage(Player, x - Camera.shot.x, y - Camera.shot.y, null);
        }
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        for (Bala i : balas) {
            i.pintar(g);
        }
    }

    public void dispose() {
        for (Bala i : toDispose) {
            balas.remove(i);
        }
        toDispose.clear();
    }

    public void keyReleased(KeyEvent e) {
        if (!bouncing) {
            if (e.getKeyCode() == KeyEvent.VK_A) {
                xa = 0;
                if (ya == 0) {
                    Player = leftidle;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                xa = 0;
                if (ya == 0) {
                    Player = rightidle;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                ya = 0;
                if (xa == 0) {
                    Player = backidle;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                ya = 0;
                if (xa == 0) {
                    Player = frontidle;
                }
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT && !RojoChile.paused) {
            FakeMouse.visible = false;
            Player = rightidle;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (!bouncing && !FakeMouse.visible && !vulnerable && !parrying) {
            if (e.getKeyCode() == KeyEvent.VK_A) {
                xa = -4;
                if (ya == 0) {
                    Player = leftmove;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                xa = 4;
                if (ya == 0) {
                    Player = rightmove;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                ya = -4;
                if (xa == 0) {
                    Player = backmove;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                ya = 4;
                if (xa == 0) {
                    Player = frontmove;
                }
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_E && !RojoChile.paused) {
            parry();
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT && xa == 0 && ya == 0) {
            FakeMouse.visible = true;
            Player = recharge;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            parry();
        }
    }

    public static void bounce(int b, int xo, int yo) {
        bouncing = true;
        xa = b * xo;
        ya = b * yo;
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
