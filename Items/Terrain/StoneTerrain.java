package Nuclear.Items.Terrain;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;

/*!
 * Concrete Terrain
 */

public class StoneTerrain extends Terrain {
    public StoneTerrain(RefLinks refLink, float x, float y) {
        super(refLink, x, y);
        image = Assets.stone;
        isDestroyable = false;
    }
}
