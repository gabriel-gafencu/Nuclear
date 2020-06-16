package Nuclear.UserInterface;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;
import Nuclear.Settings.SettingsLoader;
import Nuclear.States.ChooseState;

import java.awt.*;
import java.util.LinkedList;


public class HighscoreUserInterface extends UserInterface {


    public HighscoreUserInterface(RefLinks refLinks) {
        super(refLinks);
    }

    @Override
    public void Update() {
        if (refLinks.GetKeyManager().escape)
            refLinks.GetGame().SetState(new ChooseState(refLinks));
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(Assets.background, 0, 0, refLinks.GetWidth(), refLinks.GetHeight(), null);
        g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 30));
        g.drawString("Top 5 Times:", 180, 360);
        LinkedList<Integer> top = SettingsLoader.GetHighscoreList();
        int index = 0;
        for (Integer i : top) {
            g.drawString((index + 1) + ". " + i / 1000 + "' " + i % 1000 + "'' ", 60, (450 + index * 60));
            index++;
        }
    }
}
