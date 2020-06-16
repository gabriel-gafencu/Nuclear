package Nuclear.UserInterface.Button;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;
import Nuclear.States.MenuState;

public class MenuButton extends Button {

    public MenuButton(RefLinks refLinks, int x, int y) {
        super(refLinks, x, y);
        width = 180;
        height = 60;
        defaultImage = Assets.menuDefault;
        activeImage = Assets.menuActive;
        currentImage = defaultImage;
    }

    @Override
    public void execute() {
        refLinks.GetGame().SetState(new MenuState(refLinks));
    }
}
