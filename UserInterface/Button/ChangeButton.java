package Nuclear.UserInterface.Button;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;

public class ChangeButton extends Button {
    public ChangeButton(RefLinks refLinks, int x, int y) {
        super(refLinks, x, y);
        width = 135;
        height = 45;
        defaultImage = Assets.changeDefault;
        activeImage = Assets.changeActive;
        currentImage = defaultImage;
    }

    @Override
    public void execute() {
        refLinks.GetKeyManager().SetWaiting();
    }
}
