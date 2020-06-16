package Nuclear.Items.PowerUp;

import Nuclear.Graphics.Assets;
import Nuclear.Items.Character;
import Nuclear.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;


/*!
 * Concrete PowerUp
 * The detonation distance power-up doubles the detonation distance of its Character
 */

public class DetonationDistancePowerUp extends PowerUp {

    private BufferedImage image = Assets.detonationDistancePowerUp;
    private int initialDetonationDistance;                              /// necessary to remember the initial detonation distance
    private int addedDetonationDistance;                                /// actual increase to detonation distance

    public DetonationDistancePowerUp(RefLinks refLink, float x, float y) {
        super(refLink, x, y, PowerUp.DEFAULT_WIDTH, PowerUp.DEFAULT_HEIGHT);
    }

    @Override
    public void Draw(Graphics g) {
        if (!hasBeenPickedUp)
            g.drawImage(image, (int) x, (int) y, width, height, null);
    }

    @Override
    public void Upgrade() {
        myCharacter.SetDetonationDistance(initialDetonationDistance + addedDetonationDistance);
    }

    @Override
    public void Downgrade() {
        myCharacter.SetDetonationDistance(initialDetonationDistance);
    }

    public void PickUp(Character character) {
        this.hasBeenPickedUp = true;
        this.myCharacter = character;
        this.initialDetonationDistance = myCharacter.GetDetonationDistance();
        this.addedDetonationDistance = initialDetonationDistance;
        this.cantBePickedUp = true;
        Upgrade();
    }
}
