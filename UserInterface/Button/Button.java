package Nuclear.UserInterface.Button;

import Nuclear.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

/*!
 * Buttons have their own functionality, being able to change the game's state or the look of a user interface.
 * There are two images, for the sleeping state and the active state of the Button
 */

public abstract class Button {
    public static final int DEFAULT_WIDTH = 360;
    public static final int DEFAULT_HEIGHT = 80;

    protected RefLinks refLinks;
    protected BufferedImage defaultImage;
    protected BufferedImage activeImage;
    protected BufferedImage currentImage;
    protected boolean isActive;             /// the state of the button, active or sleeping
    protected int x;
    protected int y;
    protected int width = DEFAULT_WIDTH;
    protected int height = DEFAULT_HEIGHT;

    public Button(RefLinks refLinks, int x, int y) {
        this.refLinks = refLinks;
        this.x = x;
        this.y = y;
        isActive = false;
    }

    public void Update() {
        if (isActive)
            currentImage = activeImage;
        else
            currentImage = defaultImage;
    }

    public void Draw(Graphics g) {
        g.drawImage(currentImage, x, y, width, height, null);
    }

    public void SetActive(boolean state) {
        this.isActive = state;
    }

    /*!
     * the execute function is the action provided by the button for the user when pressed
     */
    public abstract void execute();
}
