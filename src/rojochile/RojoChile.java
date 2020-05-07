package rojochile;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.Window;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
    static BufferedImage bg;
    static Window e;
    static RojoChile b;
    static boolean GO = false;
    static Clip clip;
    Timer timer = new Timer(17, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                spawn();
                move();
            } catch (IOException ex) {
                Logger.getLogger(RojoChile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(RojoChile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            repaint();
        }
    });
    Random r = new Random();
    Level level = new Level(lvl);
    Map map;
    Camera camera;
    Vato vato;
    FakeMouse fakeMouse;
    Robot rob;
    Mob mob1;
    Mob mob2;
    int cdToSpawn = 300;
    ArrayList<Cacodrone> cDrones = new ArrayList<>();
    static ArrayList<Cacodrone> toDispose = new ArrayList<>();

    public RojoChile() throws IOException {
        W = Level.width;
        H = Level.height;
        centerW = Math.round(W / 2);
        centerH = Math.round(H / 2);
        mapWidth = Level.mapWidth;
        mapHeight = Level.mapHeight;
        map = new Map(mapWidth / Tile.WIDTH, mapHeight / Tile.HEIGHT);
        camera = new Camera(W, H, map);
        vato = new Vato();
        centerMouse();
        fakeMouse = new FakeMouse();
        mob1 = new Mob(500, 500);
        mob2 = new Mob(800, 500);
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
                    try {
                        rob = new Robot();
                        rob.mouseMove(FakeMouse.x - Camera.shot.x + RojoChile.borderSize, FakeMouse.y - Camera.shot.y + RojoChile.wideBorderSize);
                    } catch (AWTException ex) {
                    }
                    FakeMouse.visible = paused == true;
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!paused) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        Vato.shoot();
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        Vato.specialShot();
                    }
                }
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
        loop("musica/gaming.wav");
        timer.start();
    }

    public void move() throws IOException, LineUnavailableException {
        if (!paused) {
            vato.move();
            camera.move();
            mob1.move();
            mob2.move();
            for (Cacodrone i : cDrones) {
                i.move();
                if (i.inactive) {
                    toDispose.add(i);
                }
            }
        }
    }

    public void dispose() {
        for (Cacodrone i : toDispose) {
            cDrones.remove(i);
        }
        toDispose.clear();
    }

    public void spawn() {
        if (cdToSpawn > 0) {
            cdToSpawn--;
        } else {
            int x, y;
            if (r.nextBoolean()) {
                x = r.nextBoolean() ? 0 : mapWidth - 64;
                y = r.nextInt(mapHeight - 128) + 64;
            } else {
                y = r.nextBoolean() ? 0 : mapHeight - 64;
                x = r.nextInt(mapWidth - 128) + 64;
            }
            cDrones.add(new Cacodrone(x, y));
            dispose();
            cdToSpawn = 300;
        }
    }

    public static void gameOver() throws IOException, LineUnavailableException {
        if (!GO) {
            GO = true;
            e = SwingUtilities.getWindowAncestor(b);
            e.setVisible(false);
            e.dispose();
            clip.stop();
            loop("musica/TemaIntro.wav");
            MenuMuerte a = new MenuMuerte();
            a.setVisible(true);
        }
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
        g.drawImage(bg, 0, 0, null);
        Graphics2D g2d = (Graphics2D) g;
        map.drawTiles(g2d);
        mob1.paint(g2d);
        mob2.paint(g2d);
        for (Cacodrone i : cDrones) {
            i.paint(g2d);
        }
        vato.paint(g2d);
        fakeMouse.paint(g2d);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(W, H);
    }

    //código robado pa la música
    public static synchronized void loop(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException();
        }

        try {
            clip = AudioSystem.getClip();
            InputStream is = RojoChile.class.getResourceAsStream(filename);
            AudioInputStream ais = AudioSystem.getAudioInputStream(is);
            clip.open(ais);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
        } catch (LineUnavailableException e) {
        } catch (IOException e) {
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        JFrame frame = new JFrame("RojoChile");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor");
        frame.getContentPane().setCursor(blankCursor);
        RojoChile game = new RojoChile();
        b = game;
        frame.add(game);
        frame.setResizable(false);
        bg = ImageIO.read(new File("Resources/tiles/space.jpg"));
        frame.pack();
        bounds = frame.getBounds();
        borderSize = (int) Math.round((bounds.getWidth() - W) / 2);
        wideBorderSize = bounds.height - H - borderSize;
        frame.setVisible(true);

    }
}
