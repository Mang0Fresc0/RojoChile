package rojochile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Mob {

    int x;
    int y;
    int height;
    int width;
    int strength;
    Rectangle pos;
    boolean aggro;

    public Mob(int x, int y, int width, int height, int strength) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.strength = strength;
        pos = new Rectangle(x, y, width, height);
    }

    public void move() {
        if (pos.intersects(Camera.shot)) {
            aggro = true;
        }
        if (pos.intersects(Vato.getPos())) {
            collision();
        }
        if (pos.intersects(Vato.close())) {
            closeAction();
        } else if (pos.intersects(Camera.stillShot)) {
            midAction();
        } else if (pos.intersects(Camera.loadArea)) {
            farAction();
        } else {
            aggro = false;
        }
    }

    public void paint(Graphics2D g) {
        g.setColor(Color.orange);
        g.fillRect(x - Camera.shot.x, y - Camera.shot.y, width, height);
    }

    public void collision() {
        Vato.bounce();
        Vato.hurt(strength);
    }

    public void closeAction() {

    }

    public void midAction() {

    }

    public void farAction() {

    }

    public Rectangle getPos() {
        return pos;
    }
}
