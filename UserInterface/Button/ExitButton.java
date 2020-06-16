package Nuclear.UserInterface.Button;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;

public class ExitButton extends Button {
    public ExitButton(RefLinks refLinks, int x, int y) {
        super(refLinks, x, y);
        defaultImage = Assets.exitDefault;
        activeImage = Assets.exitActive;
        currentImage = defaultImage;
    }

    @Override
    public void execute() {
        refLinks.GetGame().StopGame();
    }
}
