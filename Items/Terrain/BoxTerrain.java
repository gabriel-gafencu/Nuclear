package Nuclear.Items.Terrain;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;

/*!
 * Concrete Terrain
 */

public class BoxTerrain extends Terrain {
    public BoxTerrain(RefLinks refLink, float x, float y) {
        super(refLink, x, y);
        image = Assets.box;
        isDestroyable = true;
    }

}
