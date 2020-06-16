package Nuclear.Items.PowerUp;

import Nuclear.Graphics.Assets;
import Nuclear.Items.Character;
import Nuclear.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Concrete PowerUp
 * The speed power-up increases its character movement speed by 1
 */

public class SpeedPowerUp extends PowerUp {

    private BufferedImage image = Assets.speedPowerUp;
    private int initialSpeed;                               /// necessary to remember the initial speed of the character
    private int addedSpeed = 1;                             /// actual increase to speed

    public SpeedPowerUp(RefLinks refLink, float x, float y) {
        super(refLink, x, y, PowerUp.DEFAULT_WIDTH, PowerUp.DEFAULT_HEIGHT);
    }

    @Override
    public void Draw(Graphics g) {
        if (!hasBeenPickedUp)
            g.drawImage(image, (int) x, (int) y, width, height, null);
    }

    @Override
    public void Upgrade() {
        myCharacter.SetSpeed(initialSpeed + addedSpeed);
    }

    @Override
    public void Downgrade() {
        myCharacter.SetSpeed(initialSpeed);
    }

    public void PickUp(Character character) {
        this.hasBeenPickedUp = true;
        this.myCharacter = character;
        this.initialSpeed = myCharacter.GetSpeed();
        this.cantBePickedUp = true;
        Upgrade();
    }
}
