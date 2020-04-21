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
    
int x;
int y;
int xa = 1;
int ya = 1;
public RojoChile game;

       
public Bala (RojoChile games, int x, int y){
this.game= games;
this.x = x;
this.y= y;
}

   
public  void pintar (Graphics2D g){
    g.setColor(Color.WHITE);
    g.fillRect(x-Camera.shot.x, y-Camera.shot.y, 30, 30);
}
public void movimiento(){
    x+=xa;
    y+=ya;
}
}
