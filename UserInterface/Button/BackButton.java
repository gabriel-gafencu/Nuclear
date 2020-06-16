package Nuclear.UserInterface.Button;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;
import Nuclear.States.MenuState;

public class BackButton extends Button {
    public BackButton(RefLinks refLinks, int x, int y) {
        super(refLinks, x, y);
        defaultImage = Assets.backDefault;
        activeImage = Assets.backActive;
        currentImage = defaultImage;
    }

    @Override
    public void execute() {
        refLinks.GetGame().SetState(new MenuState(refLinks));
    }
}
