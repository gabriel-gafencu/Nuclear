package Nuclear.Items.PowerUp;

import Nuclear.Items.Item;
import Nuclear.Items.Character;
import Nuclear.RefLinks;


/*!
 * PowerUps are a special set of items which have a chance to appear after the destruction of Terrain
 * They then replace the same exact spot and can give benefits to the Characters picking them up
 */

public abstract class PowerUp extends Item {
    public static final int DEFAULT_WIDTH = 48;
    public static final int DEFAULT_HEIGHT = 48;

    protected boolean hasBeenPickedUp, cantBePickedUp;  /// booleans that define the current state of the power-up
    protected Character myCharacter;                    /// every power-up has its own character that it buffs
    protected int timer;                                /// the total time that the power-up buffs its character

    public PowerUp(RefLinks refLink, float x, float y, int width, int height) {
        super(refLink, x, y, width, height);

        ///by default, the total timer for every power-up is 10 seconds
        timer = 600;

        hasBeenPickedUp = false;
        cantBePickedUp = false;
    }

    /*!
     * updates the timer and on the count of zero tells the map object to get rid of it
     * it also downgrades its character at the end
     */
    @Override
    public void Update() {
        if (hasBeenPickedUp) {
            timer--;
            if (timer == 0) {
                Downgrade();
                this.disposeOfMe();
            }
        }
    }


    /*!
     * the working mechanics of the PowerUp class are buffing the character 'permanently' at first and then downgrading it after the timer expires
     */
    public abstract void Upgrade();

    public abstract void Downgrade();

    /*!
     * function to be called upon a character touching the power-up
     * @param character
     */
    public abstract void PickUp(Character character);


    /*!
     * tells the map to dispose of the power-up at the end of the timer
     */
    protected void disposeOfMe() {
        refLink.GetMap().disposeOfPowerUp(this);
    }

    /*!
     * In case that another Character tries to pick up the power-up after being picked up
     * the power-up is essentially invisible to the players but it still exists on the map as long as it buffs a Character
     */
    public boolean cantBePickedUp(){
        return cantBePickedUp;
    }

}
