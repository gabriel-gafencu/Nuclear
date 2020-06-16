package Nuclear.UserInterface.Button;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;
import Nuclear.UserInterface.SettingsUserInterface;

public class Player2Button extends Button {

    public Player2Button(RefLinks refLinks, int x, int y) {
        super(refLinks, x, y);
        width = 180;
        height = 60;
        defaultImage = Assets.player2Default;
        activeImage = Assets.player2Active;
        currentImage = defaultImage;
    }


    @Override
    public void execute() {
        SettingsUserInterface.playerId = 2;
    }
}
