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
    double xd;
    double yd;
    int strength = 100;
    int diameter = 5;
    int speed = 30;

    public Bala(int x, int y) {
        this.x = x;
        this.y = y;
        double angle = FakeMouse.getAngle();
        xd = FakeMouse.rightQuad() ? Math.cos(angle) : -Math.cos(angle);
        yd = FakeMouse.upperQuad() ? Math.sin(angle) : -Math.sin(angle);
    }

    public void pintar(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval((int) Math.round(x - Camera.shot.x - diameter / 2), (int) Math.round(y - Camera.shot.y - diameter / 2), diameter, diameter);
    }

    public void movimiento() {
        prevX = x;
        prevY = y;
        x += xd * speed;
        y += yd * speed;
    }

    public boolean intersects(Rectangle target) {
        Line2D path = new Line2D.Double();
        path.setLine(prevX, prevY, x, y);
        return path.intersects(target);
    }
}
