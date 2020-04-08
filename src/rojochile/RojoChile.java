package rojochile;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RojoChile extends JPanel implements ActionListener {

    int lvl = 2;
    static int W;
    static int H;
    static int mapWidth;
    static int mapHeight;
    Timer timer = new Timer(17, this);
    Level level = new Level(lvl);
    Map map;
    Camera camera;
    Vato vato;
    long start;
    long start2;

    public RojoChile() throws IOException {
        W = Level.width;
        H = Level.height;
        mapWidth = Level.mapWidth;
        mapHeight = Level.mapHeight;
        map = new Map(mapWidth / Tile.WIDTH, mapHeight / Tile.HEIGHT);
        camera = new Camera(W, H, map);
        vato = new Vato(this);
        timer.setInitialDelay(0);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                camera.keyReleased(e);
                vato.keyReleased(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                vato.keyPressed(e);
                camera.keyPressed(e);
            }
        });
        setFocusable(true);
        timer.start();
        start = System.nanoTime();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        start2 = System.nanoTime();
        System.out.print((start2 - start) * 10E-10 + "   ");
        move();
        repaint();
        System.out.println((System.nanoTime() - start2) * 10E-10);
    }

    public void move() {
        vato.move();
        camera.move();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        map.drawTiles(g2d);
        vato.paint(g2d);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(W, H);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        JFrame frame = new JFrame("RojoChile");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RojoChile game = new RojoChile();
        frame.add(game);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
