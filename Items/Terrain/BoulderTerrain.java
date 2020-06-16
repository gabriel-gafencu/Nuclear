package Nuclear.Items.Terrain;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

/*!
 * Concrete Terrain
 */

public class BoulderTerrain extends Terrain {

    public BoulderTerrain(RefLinks refLink, float x, float y) {
        super(refLink, x, y);
        image = Assets.boulder;
        isDestroyable = false;
    }

}
