/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rojochile;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author gusal
 */
public class Bala {

    double x;
    double y;
    double xa;
    double ya;
    int diameter = 5;

    public Bala(int x, int y) {
        this.x = x;
        this.y = y;
        double angle = FakeMouse.getAngle();
        xa = Math.cos(angle);
        ya = Math.sin(angle);
    }

    public void pintar(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval((int) Math.round(x - Camera.shot.x - diameter / 2), (int) Math.round(y - Camera.shot.y - diameter / 2), diameter, diameter);
    }

    public void movimiento() {
        x += xa;
        y += ya;
    }
}
