package Nuclear.UserInterface.Button;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;
import Nuclear.States.HighscoreState;

public class HighscoreButton extends Button {
    public HighscoreButton(RefLinks refLinks, int x, int y) {
        super(refLinks, x, y);
        defaultImage = Assets.highscoreDefault;
        activeImage = Assets.highscoreActive;
        currentImage = defaultImage;
    }

    @Override
    public void execute() {
        refLinks.GetGame().SetState(new HighscoreState(refLinks));
    }
}
