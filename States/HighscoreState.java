package Nuclear.States;

import Nuclear.RefLinks;
import Nuclear.UserInterface.HighscoreUserInterface;
import Nuclear.UserInterface.UserInterface;

import java.awt.*;

/**
 * Shows the first 5 best times
 */

public class HighscoreState extends State {

    private UserInterface ui;

    public HighscoreState(RefLinks refLink) {
        super(refLink);
        ui = new HighscoreUserInterface(refLink);
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
