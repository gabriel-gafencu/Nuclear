package Nuclear.UserInterface;

import Nuclear.RefLinks;

import java.awt.*;

/*!
 * The user interface serves the role of offering the user to actively interact with the game, either
 * by changing its state or looking at different features
 *
 * Often has a set of buttons with different functionalities
 *
 * Almost every state besides the PlayState ones have their own user interface
 */

public abstract class UserInterface {

    protected RefLinks refLinks;

    public UserInterface(RefLinks refLinks) {
        this.refLinks = refLinks;
    }

    public abstract void Update();

    public abstract void Draw(Graphics g);
}
