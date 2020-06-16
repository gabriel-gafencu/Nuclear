package Nuclear.Items.Terrain;

import Nuclear.Items.Item;
import Nuclear.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

/*!
 * Terrain is a special set of Items that block the movement of the Characters
 * There are two subsets of Terrain, divided by the possibility of it being destroyed
 * It's different from the Tile class as it has isn't inherently drawn every frame on the map
 * and also can be destroyed and doesn't necessarily occupy every block
 *
 * The concrete Terrain classes only differ by the image and their isDestroyable property
 */

public abstract class Terrain extends Item {
    public static final int DEFAULT_WIDTH = 48;
    public static final int DEFAULT_HEIGHT = 48;

    protected BufferedImage image;

    protected boolean isDestroyable;                /// property saying whether the Terrain object can be destroyed or not
    protected boolean isDestroyed;                  /// property that tracks whether the object has been destroyed

    public Terrain(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        isDestroyed = false;
    }

    /**
     * there's nothing to Update in a Terrain object
     */
    @Override
    public void Update() {

    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, DEFAULT_WIDTH, DEFAULT_HEIGHT, null);
    }

    public boolean GetState() {
        return isDestroyed;
    }

    public boolean GetDestroyable() {
        return isDestroyable;
    }

}
