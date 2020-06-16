package Nuclear.States;

import Nuclear.RefLinks;
import Nuclear.UserInterface.ChooseUserInterface;
import Nuclear.UserInterface.UserInterface;

import java.awt.*;

/**
 * The second page of the menu
 * Serves as a 'choice' menu in which the player can choose whether to play the computer, a secondary player or view
 * the highscore table
 */

public class ChooseState extends State {

    UserInterface ui;

    public ChooseState(RefLinks refLink) {
        super(refLink);
        ui = new ChooseUserInterface(refLink);
    }

    @Override
    public void Update() {
        ui.Update();
    }

    @Override
    public void Draw(Graphics g) {
        ui.Draw(g);
    }
}
