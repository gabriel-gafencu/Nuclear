package Nuclear.Tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

/*!
 * The Tile class differs from the Terrain class as it serves only the purpose of drawing every background block
 * independent of existing and moving Items
 */

public class Tile {
    private static final int NO_TILES = 32;
    public static Tile[] tiles = new Tile[NO_TILES];

    public static Tile grassTile = new GrassTile(0);
    public static Tile soilTile = new SoilTile(1);
    public static Tile rockTile = new RockTile(2);
    public static final int TILE_WIDTH = 48;
    public static final int TILE_HEIGHT = 48;

    protected BufferedImage img;
    protected final int id;

    public Tile(BufferedImage image, int idd) {
        img = image;
        id = idd;

        tiles[id] = this;
    }

    public void Update() {

    }


    public void Draw(Graphics g, int x, int y) {
        g.drawImage(img, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }


    public int GetId() {
        return id;
    }
}
