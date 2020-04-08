package rojochile;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tile {

    BufferedImage tileImage;
    final static int WIDTH = 16, HEIGHT = 16;
    int x;
    int y;

    public Tile(int x, int y, String c, BufferedImage b) throws IOException {
        this.x = x * WIDTH;
        this.y = y * HEIGHT;
        tileImage = b;
        setTileProperties(c);
    }

    public final void setTileProperties(String c) {
        switch (c) {
        }
    }

    public void paint(Graphics2D g) {
        if (new Rectangle(x, y, WIDTH, HEIGHT).intersects(Camera.shot)) {
            g.drawImage(tileImage, x - Camera.shot.x, y - Camera.shot.y, null);
        }
    }

}

