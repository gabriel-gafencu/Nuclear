package Nuclear.Items.Terrain;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;

/*!
 * Concrete Terrain
 */

public class TreeTerrain extends Terrain {

    public TreeTerrain(RefLinks refLink, float x, float y) {
        super(refLink, x, y);
        image = Assets.tree;
        isDestroyable = true;
    }

}
