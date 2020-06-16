package Nuclear.UserInterface.Button;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;
import Nuclear.States.ChooseState;

public class PlayButton extends Button {
    public PlayButton(RefLinks refLinks, int x, int y) {
        super(refLinks, x, y);
        defaultImage = Assets.playDefault;
        activeImage = Assets.playActive;
        currentImage = defaultImage;
    }

    @Override
    public void execute() {
        refLinks.GetGame().SetState(new ChooseState(refLinks));
    }
}
