package rojochile;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Cacodrone extends Mob {

    ArrayList<Bala> toDispose = new ArrayList<>();
    ArrayList<Bala> balas = new ArrayList<>();
    Bala bullet;
    int cooldown = 300;

    public Cacodrone(int x, int y) {
        this.x = x;
        this.y = y;
        speed = 4;
        hostility = 10;
        meleeAtkMir = null;
        move = new ImageIcon(this.getClass().getResource("Animaciones/Cacodrone/cacodrone.gif")).getImage();
        dead = new ImageIcon(this.getClass().getResource("Animaciones/Cacodrone/cacodroneDead.gif")).getImage();
    }

    @Override
    public void die() {
        inactive = true;
        inactCount = 500;
    }

    @Override
    public void attack() {
        System.out.println(windUp);
        if (windUp < STDATKT) {
            windUp += agility;
        } else if (windUp >= STDATKT) {
            shoot();
            Atk = false;
            windUp = 0;
        }
    }

    @Override
    public void closeAction() {
        int n = r.nextInt(90);
        orient();
        if (!Atk) {
            if (n + hostility > 90 && cooldown == 0) {
                aim();
                Atk = true;
            } else if (n < 5) {
                xa = 0;
            } else if (n >= 10 && n < 26) {
                xa = -xOrient * speed;
            } else if (n >= 26 && n < 40) {
                ya = -yOrient * speed;
            } else if (n >= 40 && n < 50) {
                ya = 0;
            } else if (n >= 50 && n < 60) {
                xa = -xOrient * speed;
                ya = -yOrient * speed;
            }
        } else {
            xa = 0;
            ya = 0;
        }
    }

    @Override
    public void midAction() {
        int n = r.nextInt(90);
        orient();
        if (!Atk) {
            if (n + hostility > 90 && cooldown == 0) {
                aim();
                Atk = true;
            } else if (n < 5 && cooldown == 0) {
                xa = xOrient;
                ya = yOrient;
            } else if (n >= 10 && n < 20) {
                xa = 0;
                ya = 1;
            } else if (n >= 20 && n < 30) {
                xa = 0;
                ya = -1;
            } else if (n >= 30 && n < 40) {
                xa = 1;
                ya = 0;
            } else if (n >= 50 && n < 60) {
                xa = -1;
                ya = 0;
            } else if (n >= 50 && n < 70) {
                xa = -xOrient;
                ya = -yOrient;
            }
        }
    }

    @Override
    public void checkBullets() {
        for (Bala i : balas) {
            i.movimiento();
            if (i.intersects(Vato.getPos())) {
                if (Vato.parrying) {
                    parried(i.strength);
                    toDispose.add(i);
                } else if (Vato.vulnerable) {
                    assail(strength);
                    toDispose.add(i);
                } else {
                    Vato.hurt(strength);
                    toDispose.add(i);
                }
            }
            if (!Camera.loadArea.intersects(i.x, i.y, i.diameter, i.diameter)) {
                toDispose.add(i);
            }
        }
        dispose();
    }

    @Override
    public void drawBullets(Graphics2D g) {
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

    @Override
    public void cooldown() {
        if (cooldown > 0) {
            cooldown--;
        }
    }

    public void aim() {
        orient();
        bullet = new Bala(x, y, getAngle(), xOrient, yOrient);
    }

    public double getAngle() {
        double angle;
        Point a = new Point(Vato.x + Math.round(Vato.width / 2), Vato.y + Math.round(Vato.height / 2));
        Point b = new Point(x, y);
        Point c = new Point(x, Vato.y + +Math.round(Vato.height / 2));
        double ab = Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2));
        double ac = Math.sqrt(Math.pow((a.x - c.x), 2) + Math.pow((a.y - c.y), 2));
        angle = Math.acos(ac / ab);
        return angle;
    }

    public void shoot() {
        balas.add(bullet);
        cooldown = 120;
    }
}
