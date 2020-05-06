package rojochile;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import javax.imageio.ImageIO;

public class Map {

    Tile tiles[][];
    String type[][];
    HashMap<String, BufferedImage> images = new HashMap<>();
    Random r = new Random();
    private static final GraphicsConfiguration GFX_CONFIG = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

    public Map(int width, int height) throws IOException {
        type = new String[width][height];
        tiles = new Tile[width][height];
        setImages();
        setMap(width, height);
    }

    private void setMap(int x, int y) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("Resources/maps/2.txt"));
        //String line;
        for (int i = 0; i < y; i++) {
            //line = reader.readLine();
            for (int j = 0; j < 2 * x; j += 2) {
                switch (r.nextInt(7)) {
                    case 1:
                        type[j / 2][i] = "b1";
                        break;
                    case 2:
                        type[j / 2][i] = "g1";
                        break;
                    case 3:
                        type[j / 2][i] = "r1";
                        break;
                    case 4:
                        type[j / 2][i] = "b1";
                        break;
                    case 5:
                        type[j / 2][i] = "g1";
                        break;
                    case 6:
                        type[j / 2][i] = "r1";
                        break;
                    default:
                        type[j / 2][i] = "y1";
                        break;
                }
                //type[j / 2][i] = String.format("%c%c", line.charAt(j), line.charAt(j + 1));
            }
        }
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                tiles[j][i] = new Tile(j, i, type[j][i], images.get(type[j][i]));
            }
        }
    }

    public void drawTiles(Graphics2D g) {
        for (Tile[] i : tiles) {
            for (Tile j : i) {
                j.paint(g);
            }
        }
    }

    private void setImages() throws IOException {
        images.put("r1", toCompatibleImage(ImageIO.read(new File("Resources/tiles/r1.png"))));
        images.put("b1", toCompatibleImage(ImageIO.read(new File("Resources/tiles/b1.png"))));
        images.put("g1", toCompatibleImage(ImageIO.read(new File("Resources/tiles/g1.png"))));
        images.put("y1", toCompatibleImage(ImageIO.read(new File("Resources/tiles/y1.png"))));
    }

    //nada que ver aquí, solo código robado
    public static BufferedImage toCompatibleImage(final BufferedImage image) {
        /*
     * if image is already compatible and optimized for current system settings, simply return it
         */
        if (image.getColorModel().equals(GFX_CONFIG.getColorModel())) {
            return image;
        }

        // image is not optimized, so create a new image that is
        final BufferedImage new_image = GFX_CONFIG.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());

        // get the graphics context of the new image to draw the old image on
        final Graphics2D g2d = (Graphics2D) new_image.getGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // return the new optimized image
        return new_image;
    }

}
