package Nuclear.UserInterface.Button;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;
import Nuclear.States.PlayState;
import Nuclear.States.SinglePlayerState;

public class PlayComputerButton extends Button {

    public PlayComputerButton(RefLinks refLinks, int x, int y) {
        super(refLinks, x, y);
        defaultImage = Assets.playVsComputerDefault;
        activeImage = Assets.playVsComputerActive;
        currentImage = defaultImage;
    }

    @Override
    public void execute() {
        refLinks.GetGame().SetState(new SinglePlayerState(refLinks,1));
    }
}
