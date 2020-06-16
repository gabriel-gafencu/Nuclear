package Nuclear.UserInterface.Button;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;
import Nuclear.States.SettingsState;

public class SettingsButton extends Button {

    public SettingsButton(RefLinks refLinks, int x, int y){
        super(refLinks, x, y);
        defaultImage = Assets.settingsDefault;
        activeImage = Assets.settingsActive;
        currentImage = defaultImage;
    }

    @Override
    public void execute() {
        refLinks.GetGame().SetState(new SettingsState(refLinks));
    }
}
