package rojochile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.ImageIcon;

public class Mob {

    Image move = new ImageIcon(this.getClass().getResource("Animaciones/Default/defaultmove.gif")).getImage();
    Image dead = new ImageIcon(this.getClass().getResource("Animaciones/Default/defaultdead.gif")).getImage();

    Image Mob;

    int x = 0;
    int y = 0;
    int xa = 0;
    int ya = 0;
    final int STDATKT = 150;
    int xOrient = 1;
    int yOrient = 1;
    int height = 64;
    int width = 64;
    int strength = 100;
    int hostility = 20;
    int agility = 10;
    int speed = 2;
    int windUp = 0;
    int kback = 48; //este tiene que ser un múltiplo de cuatro, o puede que la cámara se rompa.
    int hp = 1000;
    int meleeRange = 100;
    Rectangle meleeAtk;
    Rectangle meleeAtkMir;
    Rectangle pos;
    boolean Atk = false;
    boolean inactive = false;
    int inactCount = 0;
    Random r = new Random();

    public Mob(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Mob(){}

    public void move() {
        if (!inactive) {
            pos = new Rectangle(x, y, width, height);
            Checkhurt();
            if (x + xa > 0 && x + xa < RojoChile.mapWidth - width) {
                x += xa;
            }
            if (y + height + ya < RojoChile.mapHeight && y + ya > 0) {
                y += ya;
            }
            if (Atk) {
                attack();
            }
            if (pos.intersects(Vato.getPos())) {
                collision();
            }
            if (pos.intersects(Vato.close())) {
                closeAction();
            } else if (pos.intersects(Camera.stillShot)) {
                midAction();
            } else {
                farAction();
            }
        } else {
            if (inactCount == 0) {
                inactive = false;
                hp = 1000;
            } else {
                inactCount--;
            }
        }
    }

    public void paint(Graphics2D g) {
        if (inactive) {
            Mob = dead;
            g.drawImage(Mob, x - Camera.shot.x, y - Camera.shot.y, null);
        }

        if (!inactive) {
            Mob = move;
            g.drawImage(Mob, x - Camera.shot.x, y - Camera.shot.y, null);
        }
        if (Atk && windUp < STDATKT) {
            g.setColor(Color.orange);
            g.draw(meleeAtkMir);
        }
        if (Atk && windUp >= STDATKT) {
            g.setColor(Color.red);
            g.draw(meleeAtkMir);
            Atk = false;
            windUp = 0;
        }
    }

    public void collision() {
        Vato.hurt(strength);
        Vato.bounce(12, xOrient, yOrient);
    }

    public void Checkhurt() {
        for (Bala i : Vato.balas) {
            if (i != null) {
                if (i.intersects(pos)) {
                    hp -= i.strength;
                    i = null;
                }
            }
        }
        if (hp <= 0) {
            die();
        }
    }

    public void die() {
        inactive = true;
        inactCount = 300;
    }

    public void closeAction() {
        int n = r.nextInt(100);
        if (!Atk) {
            meleeAtk = new Rectangle(x - meleeRange, y - meleeRange, 2 * meleeRange + width, 2 * meleeRange + height);
            if (meleeAtk.intersects(Vato.getPos()) && n + hostility > 80) {
                Atk = true;
            } else if (n < 5) {
                xa = xOrient * speed;
            } else if (n >= 5 && n < 10) {
                ya = yOrient * speed;
            } else if (n >= 10 && n < 13) {
                xa = -xOrient * speed;
            } else if (n >= 13 && n < 15) {
                ya = -yOrient * speed;
            } else if (n >= 15 && n < 20) {
                xa = 0;
            } else if (n >= 20 && n < 25) {
                ya = 0;
            } else if (n >= 25 && n < 27) {
                xa = 0;
                ya = 0;
            }
        } else {
            xa = 0;
            ya = 0;
        }
    }

    public void midAction() {
        orient();
        xa = xOrient * speed;
        ya = yOrient * speed;
    }

    public void farAction() {
        orient();
        xa = xOrient * speed;
        ya = yOrient * speed;
    }

    public void attack() {
        meleeAtkMir = (Rectangle) meleeAtk.clone();
        meleeAtkMir.translate(-Camera.shot.x, -Camera.shot.y);
        if (windUp < STDATKT) {
            windUp += agility;
        }
        if (windUp >= STDATKT && meleeAtk.intersects(Vato.getPos())) {
            if (Vato.parrying) {
                parried();
            } else if (Vato.vulnerable) {
                assail();
            } else {
                Vato.hurt(strength);
                Vato.bounce(kback, xOrient, yOrient);
            }
        }
    }

    public void assail() {
        Vato.hurt(strength * 2);
        Vato.bounce(kback, xOrient, yOrient);
        Vato.vulnerable = false;
        Vato.vulCount = 0;
    }

    public void parried() {
        Vato.energy += strength;
        Vato.parrying = false;
        Vato.parryCount = 0;
    }

    public Rectangle getPos() {
        return pos;
    }

    public void orient() {
        xOrient = x < Vato.x ? 1 : -1;
        yOrient = y < Vato.y ? 1 : -1;
    }
}
