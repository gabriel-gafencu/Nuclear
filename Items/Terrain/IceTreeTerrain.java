package Nuclear.Items.Terrain;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;

/*!
 * Concrete Terrain
 */

public class IceTreeTerrain extends Terrain {
    public IceTreeTerrain(RefLinks refLink, float x, float y) {
        super(refLink, x, y);
        image = Assets.icetree;
        isDestroyable = false;
    }
}
