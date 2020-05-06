package rojochile;

public class Level {

    static int width, height, mapWidth, mapHeight, lvl, startPosX, startPosY;

    public Level(int lvl) {
        Level.lvl = lvl;
        switch (lvl) {
            case 2:
                width = 1024;
                height = 768;
                mapWidth = 2048;
                mapHeight = 1536;
                startPosX = 700;
                startPosY = 700;
                break;
            case 3:
                width = 1024;
                height = 768;
                mapWidth = 8193;
                mapHeight = 3072;
                startPosX = 1024;
                startPosY = 768;
                break;
        }
    }
}