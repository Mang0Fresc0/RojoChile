/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rojochile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

/**
 *
 * @author gusal
 */
public class Bala {

    double x;
    double y;
    double prevX;
    double prevY;
    double dx;
    double dy;
    int strength;
    int diameter = 5;
    int speed = 30;

    public Bala(int x, int y) {
        this.x = x;
        this.y = y;
        double angle = FakeMouse.getAngle();
        dx = FakeMouse.rightQuad() ? Math.cos(angle) : -Math.cos(angle);
        dy = FakeMouse.upperQuad() ? Math.sin(angle) : -Math.sin(angle);
        strength = 100;
    }

    public Bala(int x, int y, int angleDev) {
        this.x = x;
        this.y = y;
        double angle = FakeMouse.getAngle() + (double) angleDev / 1000;
        dx = FakeMouse.rightQuadDev() ? Math.cos(angle) : -Math.cos(angle);
        dy = FakeMouse.upperQuadDev() ? Math.sin(angle) : -Math.sin(angle);
        strength = 20;
    }

    public Bala(int x, int y, double angle, int xo, int yo) {
        this.x = x;
        this.y = y;
        dx = xo == 1 ? Math.cos(angle) : -Math.cos(angle);
        dy = yo == 1? Math.sin(angle) : -Math.sin(angle);
        strength = 100;
        diameter = 10;
    }

    public void pintar(Graphics2D g) {
        if (diameter == 10) {
            g.setColor(Color.cyan);
        } else if (strength == 100) {
            g.setColor(new Color(235, 5, 231));
        } else {
            g.setColor(Color.RED);
        }
        g.fillOval((int) Math.round(x - Camera.shot.x - diameter / 2), (int) Math.round(y - Camera.shot.y - diameter / 2), diameter, diameter);
    }

    public void movimiento() {
        prevX = x;
        prevY = y;
        x += dx * speed;
        y += dy * speed;
    }

    public boolean intersects(Rectangle target) {
        Line2D path = new Line2D.Double();
        path.setLine(prevX, prevY, x, y);
        return path.intersects(target);
    }
}
