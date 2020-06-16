package Nuclear.UserInterface.Button;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;
import Nuclear.States.MultiplayerState;

public class MultiplayerButton extends Button {
    public MultiplayerButton(RefLinks refLinks, int x, int y) {
        super(refLinks, x, y);
        defaultImage = Assets.multiplayerDefault;
        activeImage = Assets.multiplayerActive;
        currentImage = defaultImage;
    }

    @Override
    public void execute() {
        refLinks.GetGame().SetState(new MultiplayerState(refLinks));
    }
}
