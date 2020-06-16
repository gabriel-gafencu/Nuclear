package Nuclear.UserInterface;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;
import Nuclear.UserInterface.Button.*;
import Nuclear.UserInterface.Button.Button;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MenuUserInterface extends UserInterface {

    private final int bufferSeconds = 20;
    private int buffer = bufferSeconds;
    private boolean canSwitch = true;

    private BufferedImage background = Assets.background;

    private int currentButtonIndex = -1;
    private Button currentButton;
    private ArrayList<Button> buttons;

    public MenuUserInterface(RefLinks refLinks) {
        super(refLinks);
        buttons = new ArrayList<Button>(3);
        buttons.add(new PlayButton(refLinks, 180, 360));
        buttons.add(new SettingsButton(refLinks, 180, 480));
        buttons.add(new ExitButton(refLinks, 180, 600));
    }

    @Override
    public void Update() {
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
