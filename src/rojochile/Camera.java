package rojochile;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Camera {

    static Rectangle shot, loadArea, stillShot, screen;
    Map map;
    static int xa = 0, ya = 0;
    int lxa = 0, lya = 0;
    static boolean left, right, up, down, xAlign, yAlign, xLAAlign, yLAAlign;

    public Camera(int w, int h, Map map) {
        shot = new Rectangle(Level.startPosX, Level.startPosY, w, h);
        stillShot = (Rectangle) shot.clone();
        loadArea = new Rectangle(Math.round(-w / 8) + shot.x, Math.round(-h / 8) + shot.y, Math.round(w * 5 / 4), Math.round(h * 5 / 4));
        this.map = map;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            left = false;
            lxa = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            right = false;
            lxa = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            up = false;
            lya = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            down = false;
            lya = 0;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            left = true;
            lxa = Vato.xa;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            right = true;
            lxa = Vato.xa;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            up = true;
            lya = Vato.ya;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            down = true;
            lya = Vato.ya;
        }
    }

    public void move() {
        stillShot.x += stillShot.x + lxa > 0 && stillShot.x + stillShot.width + lxa < RojoChile.mapWidth && xAlign ? lxa : 0;
        stillShot.y += stillShot.y + lya > 0 && stillShot.y + stillShot.height + lya < RojoChile.mapHeight && yAlign ? lya : 0;
        loadArea.x += loadArea.x + lxa > -Math.round(shot.width / 8) && loadArea.x + loadArea.width + lxa < RojoChile.mapWidth + Math.round(shot.width / 8) && xLAAlign ? lxa : 0;
        loadArea.y += loadArea.y + lya > -Math.round(shot.height / 8) && loadArea.y + loadArea.height + lya < RojoChile.mapHeight + Math.round(shot.height / 8) && yLAAlign ? lya : 0;
        moveShot();
        shot.x += xa;
        shot.y += ya;
    }

    public void moveShot() {
        Rectangle charCenterY = new Rectangle(Vato.x, (int) Math.round(Vato.getPos().getCenterY()) - 1, Vato.x + Vato.width, 3);
        Rectangle stillShotCenterY = new Rectangle(stillShot.x, (int) Math.round(stillShot.getCenterY()), stillShot.x + stillShot.width, 1);
        Rectangle lAreaCenterY = new Rectangle(loadArea.x, (int) Math.round(loadArea.getCenterY()), loadArea.x + loadArea.width, 1);
        Rectangle charCenterX = new Rectangle((int) Math.round(Vato.getPos().getCenterX()) - 1, Vato.y, 3, Vato.y + Vato.height);
        Rectangle stillShotCenterX = new Rectangle((int) Math.round(stillShot.getCenterX()), stillShot.y, 1, stillShot.y + stillShot.height);
        Rectangle lAreaCenterX = new Rectangle((int) Math.round(loadArea.getCenterX()), loadArea.y, 1, loadArea.y + loadArea.height);
        int accel = 12;
        xLAAlign = charCenterX.intersects(lAreaCenterX);
        yLAAlign = charCenterY.intersects(lAreaCenterY);
        xAlign = charCenterX.intersects(stillShotCenterX);
        yAlign = charCenterY.intersects(stillShotCenterY);
        if (xAlign) {
            if (left && shot.x + xa > loadArea.x) {
                xa = -accel;
            } else if (left) {
                xa = lxa;
            }
            if (right && shot.x + shot.width + xa < loadArea.x + loadArea.width) {
                xa = accel;
            } else if (right) {
                xa = lxa;
            }
        } else {
            xa = 0;
        }
        if (yAlign) {
            if (up && shot.y + ya > loadArea.y) {
                ya = -accel;
            } else if (up) {
                ya = lya;
            }
            if (down && shot.y + shot.height + ya < loadArea.y + loadArea.height) {
                ya = accel;
            } else if (down) {
                ya = lya;
            }
        } else {
            ya = 0;
        }
        if (left == right) {
            xa = 0;
        }
        if (up == down) {
            ya = 0;
        }
    }
}
