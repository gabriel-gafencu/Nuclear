package Nuclear.States;

import Nuclear.RefLinks;
import Nuclear.UserInterface.SettingsUserInterface;
import Nuclear.UserInterface.UserInterface;

import java.awt.*;

/*!
 * The state where the user can change settings and player controls
 */

public class SettingsState extends State {

    UserInterface ui;

    public SettingsState(RefLinks refLink) {
        super(refLink);
        ui = new SettingsUserInterface(refLink);
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
