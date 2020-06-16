package Nuclear.Items.Terrain;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;

/*!
 * Concrete Terrain
 */


public class IceTerrain extends Terrain {
    public IceTerrain(RefLinks refLink, float x, float y) {
        super(refLink, x, y);
        image = Assets.ice;
        isDestroyable = true;
    }
}
