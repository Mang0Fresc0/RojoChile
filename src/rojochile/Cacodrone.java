package rojochile;

import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Cacodrone extends Mob {

    ArrayList<Bala> toDispose = new ArrayList<>();
    ArrayList<Bala> balas = new ArrayList<>();
    Bala bullet;

    public Cacodrone(int x, int y) {
        this.x = x;
        this.y = y;
        move = new ImageIcon(this.getClass().getResource("Animaciones/Cacodrone/cacodrone.gif")).getImage();
        dead = new ImageIcon(this.getClass().getResource("Animaciones/Cacodrone/cacodroneDead.gif")).getImage();
    }

    @Override
    public void attack() {
        if (windUp < STDATKT) {
            windUp += agility;
        } else if (windUp >= STDATKT) {
            shoot();
        }
    }

    public void shoot() {
        balas.add(bullet);
    }
}
