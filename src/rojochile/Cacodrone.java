package rojochile;

import javax.swing.ImageIcon;

public class Cacodrone extends Mob {

    public Cacodrone(int x, int y) {
        this.x = x;
        this.y = y;
        move = new ImageIcon(this.getClass().getResource("Animaciones/Cacodrone/cacodrone.gif")).getImage();
        dead = new ImageIcon(this.getClass().getResource("Animaciones/Cacodrone/cacodroneDead.gif")).getImage();
    }
}
