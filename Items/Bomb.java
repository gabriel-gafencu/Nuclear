package Nuclear.Items;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

/*!
 * Bomb is a special Item which defines the Bomb used by a Character
 */

public class Bomb extends Item {
    public static final int BOMB_WIDTH = 20;
    public static final int BOMB_HEIGHT = 20;

    private int top = -1, bot = -1, right = -1, left = -1;              /// in case the bomb detonates terrain, these properties define the lines of the drawing

    private Character myCharacter;                                      /// every Bomb has a unique Character, also every Character has a unique Bomb

    private int detonationDistance = 48;                                /// property describing the detonation distance, by default 48
    private BufferedImage image;
    private BufferedImage imageBomb1 = Assets.bomb1;
    private BufferedImage imageBomb2 = Assets.bomb2;
    private BufferedImage imageExplosion = Assets.explosion;
    private BufferedImage imageVertical = Assets.explosionVertical;
    private BufferedImage imageHorizontal = Assets.explosionHorizontal;

    private boolean animation;
    private int timer;
    private int bombTimer;
    private boolean isAlive;
    private boolean isExploding;
    private int explodeTimer;

    /*!
     * Constructor called by the main player
     */
    public Bomb(RefLinks refLinks, float x, float y, int width, int height) {
        super(refLinks, x, y, width, height);
        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 20;
        bounds.height = 20;
        timer = 120;
        explodeTimer = 20;
        image = Assets.bomb1;
        isAlive = true;
        isExploding = false;
        animation = false;
        bombTimer = 0;
    }

    /*!
     * Constructor called by the Computer or the secondary player
     */
    public Bomb(RefLinks refLinks, float x, float y, int width, int height, String color) {
        super(refLinks, x, y, width, height);
        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 20;
        bounds.height = 20;
        timer = 120;
        explodeTimer = 20;
        image = Assets.bomb1;
        isAlive = true;
        isExploding = false;
        animation = false;
        bombTimer = 0;
        switch (color) {
            case "Red":
                imageBomb1 = Assets.bomb1Red;
                imageBomb2 = Assets.bomb2Red;
                imageExplosion = Assets.explosionRed;
                imageVertical = Assets.explosionVerticalRed;
                imageHorizontal = Assets.explosionHorizontalRed;
        }
    }

    /*!
     * The Update function of Bomb counts down to zero at which point it switches to an exploding state
     * At the beginning of the state, the bomb first destroys nearby terrain and after kills any Character
     * in the blast zone.
     * The Bomb has a + shape upon detonation.
     * <p>
     * The Update function also animates the Bomb prior to exploding
     */
    @Override
    public void Update() {
        if (!isExploding) {
            timer--;
            bombTimer++;
            if (bombTimer == 10) {
                bombTimer = 0;
                if (animation) {
                    animation = false;
                    image = imageBomb1;
                } else {
                    animation = true;
                    image = imageBomb2;
                }
                if (timer == 0) {
                    isExploding = true;
                    Destroy();
                    Kill();
                }
            }
        } else {
            image = imageExplosion;
            explodeTimer--;
            if (explodeTimer == 0) {
                isAlive = false;
            }
        }
    }


    /*!
     * The Draw function handles both the pre-exploding state and the exploding state
     * In the first, it simply draws the current image
     * In the latter, it draws the blast zone depending on the destroyed terrain, which is why
     * it checks for the top/bot/right/left
     */
    @Override
    public void Draw(Graphics g) {
        //g.setColor(Color.red);
        //g.fillRect((int) (x + bounds.x), (int) (y + bounds.y), bounds.width, bounds.height);
        g.drawImage(image, (int) x, (int) y, width, height, null);
        if (isExploding) {
            //desen explozia sus
            if (top == -1)
                g.drawImage(imageVertical, (int) x + bounds.x, (int) y + bounds.y - detonationDistance, bounds.width, detonationDistance, null);
            else
                g.drawImage(imageVertical, (int) x + bounds.x, (int) top, bounds.width, (int) (y + bounds.y - top), null);

            //desenez explozia jos
            if (bot == -1)
                g.drawImage(imageVertical, (int) x + bounds.x, (int) y + bounds.y + bounds.height, bounds.width, detonationDistance, null);
            else
                g.drawImage(imageVertical, (int) x + bounds.x, (int) y + bounds.y + bounds.height, bounds.width, (int) (bot - (y + bounds.y + bounds.height)), null);

            //desenez explozia dreapta
            if (right == -1)
                g.drawImage(imageHorizontal, (int) x + bounds.x + bounds.width, (int) y + bounds.y, detonationDistance, bounds.height, null);
            else
                g.drawImage(imageHorizontal, (int) x + bounds.x + bounds.width, (int) y + bounds.y, (int) (right - (x + bounds.x + bounds.width)), bounds.height, null);

            //desenez explozia stanga
            if (left == -1)
                g.drawImage(imageHorizontal, (int) x + bounds.x - detonationDistance, (int) y + bounds.y, detonationDistance, bounds.height, null);
            else
                g.drawImage(imageHorizontal, (int) left, (int) y + bounds.y, (int) (x + bounds.x - left), bounds.height, null);
        }
    }

    /*!
     * The Kill function kills every Character hit by the Bomb, including its owner
     * To easily access every Character's location, it uses the owner Enemy array
     */
    public void Kill() {
        for (Character enemy : myCharacter.GetEnemy()) {
            //kill enemy
            float enemyX = enemy.GetX();
            float enemyY = enemy.GetY();
            if (this.x + bounds.x - detonationDistance <= enemyX + myCharacter.GetBoundWidth() && enemyX <= this.x + bounds.x + bounds.width + detonationDistance && this.y + bounds.y <= enemyY + myCharacter.GetBoundHeight() && enemyY <= this.y + bounds.y + bounds.height)
                enemy.killCharacter();

            if (this.y + bounds.y - detonationDistance <= enemyY + myCharacter.GetBoundHeight() && enemyY <= this.y + bounds.y + bounds.height + detonationDistance && this.x + bounds.x <= enemyX + myCharacter.GetBoundWidth() && enemyX <= this.x + bounds.x + bounds.width)
                enemy.killCharacter();

        }

        //kill myself
        float myX = myCharacter.GetX();
        float myY = myCharacter.GetY();

        if (this.x + bounds.x - detonationDistance <= myX + myCharacter.GetBoundWidth() && myX <= this.x + bounds.x + bounds.width + detonationDistance && this.y + bounds.y <= myY + myCharacter.GetBoundHeight() && myY <= this.y + bounds.y + bounds.height)
            myCharacter.killCharacter();

        if (this.y + bounds.y - detonationDistance <= myY + myCharacter.GetBoundHeight() && myY <= this.y + bounds.y + bounds.height + detonationDistance && this.x + bounds.x <= myX + myCharacter.GetBoundWidth() && myX <= this.x + bounds.x + bounds.width)
            myCharacter.killCharacter();
    }


    /*!
     * The Destroy function simply tells the map that a Bomb exploded at the X, Y location and lets it handle
     * the logic for destroying the nearby terrain on its own
     */
    public void Destroy() {
        refLink.GetMap().disposeOfTerrain(this);
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void setBot(int bot) {
        this.bot = bot;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public float GetX() {
        return x + bounds.x;
    }

    public float GetY() {
        return y + bounds.y;
    }

    public void SetDetionationDistance(int newDetonationDistance) {
        this.detonationDistance = newDetonationDistance;
    }

    public int GetDetonationDistance() {
        return detonationDistance;
    }

    public void SetCharacter(Character character) {
        this.myCharacter = character;
    }

    public boolean GetState() {
        return isAlive;
    }
}
