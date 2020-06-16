package Nuclear.UserInterface.Button;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;
import Nuclear.UserInterface.SettingsUserInterface;

public class Player1Button extends Button {

    public Player1Button(RefLinks refLinks, int x, int y) {
        super(refLinks, x, y);
        width = 180;
        height = 60;
        defaultImage = Assets.player1Default;
        activeImage = Assets.player1Active;
        currentImage = defaultImage;
    }


    @Override
    public void execute() {
        SettingsUserInterface.playerId = 1;
    }
}
