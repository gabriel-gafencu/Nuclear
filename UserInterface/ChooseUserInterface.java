package Nuclear.UserInterface;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;
import Nuclear.States.MenuState;
import Nuclear.UserInterface.Button.*;
import Nuclear.UserInterface.Button.Button;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ChooseUserInterface extends UserInterface {

    private final int bufferSeconds = 20;
    private int buffer = bufferSeconds;
    private boolean canSwitch = true;

    private BufferedImage background = Assets.background;

    private ArrayList<Button> buttons;
    private Button currentButton;
    private int currentButtonIndex;

    public ChooseUserInterface(RefLinks refLinks) {
        super(refLinks);
        buttons = new ArrayList<Button>(3);
        buttons.add(new PlayComputerButton(refLinks, 180, 360));
        buttons.add(new MultiplayerButton(refLinks, 180, 480));
        buttons.add(new HighscoreButton(refLinks, 180, 600));
        currentButtonIndex = -1;
    }

    @Override
    public void Update() {
        if (refLinks.GetKeyManager().escape || refLinks.GetKeyManager().backspace)
            refLinks.GetGame().SetState(new MenuState(refLinks));

        if (buffer == 0) {
            buffer = bufferSeconds;
            canSwitch = true;
        } else
            buffer--;
        if (currentButtonIndex == -1) {
            if (refLinks.GetKeyManager().up == true) {
                currentButtonIndex = buttons.size() - 1;
                canSwitch = false;
                buffer = bufferSeconds;
            }
            if (refLinks.GetKeyManager().down == true) {
                currentButtonIndex = 0;
                canSwitch = false;
                buffer = bufferSeconds;
            }
        } else if (refLinks.GetKeyManager().enter == true && currentButtonIndex >= 0 && currentButtonIndex < buttons.size()) {
            currentButton.execute();
        } else if (refLinks.GetKeyManager().up == true) {
            if (currentButtonIndex != 0 && canSwitch) {
                currentButtonIndex--;
                canSwitch = false;
                buffer = bufferSeconds;
            }
        } else if (refLinks.GetKeyManager().down == true) {
            if (currentButtonIndex != buttons.size() - 1 && canSwitch) {
                currentButtonIndex++;
                canSwitch = false;
                buffer = bufferSeconds;
            }
        }
        try {
            currentButton = buttons.get(currentButtonIndex);
        } catch (IndexOutOfBoundsException e) {
        }
        for (Button b : buttons) {
            try {
                if (currentButton.equals(b))
                    b.SetActive(true);
                else
                    b.SetActive(false);
            } catch (Exception e) {
            }
            b.Update();
        }
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(background, 0, 0, refLinks.GetWidth(), refLinks.GetHeight(), null);
        for (Button b : buttons)
            b.Draw(g);
    }
}
