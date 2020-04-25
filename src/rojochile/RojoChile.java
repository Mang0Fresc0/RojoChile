package rojochile;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RojoChile extends JPanel {

    int lvl = 2;
    static int W;
    static int H;
    static int centerW;
    static int centerH;
    static int mapWidth;
    static int mapHeight;
    static boolean paused = false;
    public static Rectangle bounds;
    static int wideBorderSize;
    static int borderSize;
    Timer timer = new Timer(17, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            move();
            repaint();
        }
    });
    Level level = new Level(lvl);
    Map map;
    Camera camera;
    Vato vato;
    FakeMouse fakeMouse;
    Robot rob;
    //Hay que quitar este y todas las referencia a él después
    Mob test;
    Mob test2;

    public RojoChile() throws IOException {
        W = Level.width;
        H = Level.height;
        centerW = Math.round(W / 2);
        centerH = Math.round(H / 2);
        mapWidth = Level.mapWidth;
        mapHeight = Level.mapHeight;
        map = new Map(mapWidth / Tile.WIDTH, mapHeight / Tile.HEIGHT);
        camera = new Camera(W, H, map);
        vato = new Vato(this);
        centerMouse();
        fakeMouse = new FakeMouse();
        test = new Mob(500, 500);
        test2 = new Mob(800, 500);
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
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    paused = !paused;
                    FakeMouse.x = Camera.centerShot.x;
                    FakeMouse.y = Camera.centerShot.y;
                    FakeMouse.visible = paused == true;
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!paused) {
                    centerMouse();
                } else {
                    FakeMouse.x = e.getXOnScreen();
                    FakeMouse.y = e.getYOnScreen();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!paused) {
                    centerMouse();
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (!paused) {
                    centerMouse();
                }
                fakeMouse.move(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (!paused) {
                    centerMouse();
                }
                fakeMouse.move(e);
            }
        });

        setFocusable(true);
        timer.start();
    }

    public void move() {
        if (!paused) {
            vato.move();
            camera.move();
            test.move();
            test2.move();
        }
    }

    public void gameOver() {
        //Hay que agregar pantalla de fin del juego o algo
        this.setVisible(false);
        System.exit(ABORT);
    }

    public void centerMouse() {
        try {
            rob = new Robot();
            rob.mouseMove(centerW + borderSize, centerH + wideBorderSize);
        } catch (AWTException ex) {
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        map.drawTiles(g2d);
        vato.paint(g2d);
        test.paint(g2d);
        test2.paint(g2d);
        fakeMouse.paint(g2d);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(W, H);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        JFrame frame = new JFrame("RojoChile");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor");
        //frame.getContentPane().setCursor(blankCursor);
        RojoChile game = new RojoChile();
        frame.add(game);
        frame.setResizable(false);
        frame.pack();
        bounds = frame.getBounds();
        borderSize = (int) Math.round((bounds.getWidth() - W) / 2);
        wideBorderSize = bounds.height - H - borderSize;
        frame.setVisible(true);
    }
}
