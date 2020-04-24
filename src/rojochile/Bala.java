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
int diameter = 5;

       
public Bala (int x, int y){
this.x = x;
this.y= y;
}

   
public  void pintar (Graphics2D g){
    g.setColor(Color.WHITE);
    g.fillOval(x-Camera.shot.x+Math.round(Vato.width/2), y-Camera.shot.y+Math.round(Vato.height/2), diameter, diameter);
}
public void movimiento(){
    x+=xa;
    y+=ya;
}
}
