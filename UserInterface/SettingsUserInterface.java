package Nuclear.UserInterface;

import Nuclear.Graphics.Assets;
import Nuclear.RefLinks;
import Nuclear.Settings.Player1Settings;
import Nuclear.Settings.Player2Settings;
import Nuclear.Settings.SettingsLoader;
import Nuclear.States.MenuState;
import Nuclear.UserInterface.Button.*;
import Nuclear.UserInterface.Button.Button;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SettingsUserInterface extends UserInterface {

    private BufferedImage background = Assets.background;

    private final int bufferSeconds = 20;
    private int buffer = bufferSeconds;
    private boolean canSwitch = true;

    String p1up, p1down, p1right, p1left, p1bomb;
    String p2up, p2down, p2right, p2left, p2bomb;

    private ArrayList<Button> mainButtons;
    private Button currentMainButton;
    private int currentMainButtonIndex = -1;

    private ArrayList<Button> changeButtons;
    private Button currentChangeButton;
    private int currentChangeButtonIndex = -1;

    private boolean lookingAtChange, waitingForKey;
    private int keyToChange;

    public static int playerId = 1;

    public SettingsUserInterface(RefLinks refLinks) {
        super(refLinks);
        mainButtons = new ArrayList<Button>(3);
        mainButtons.add(new Player1Button(refLinks, 50, 320));
        mainButtons.add(new Player2Button(refLinks, 270, 320));
        mainButtons.add(new MenuButton(refLinks, 490, 320));

        changeButtons = new ArrayList<Button>(5);
        changeButtons.add(new ChangeButton(refLinks, 512, 420));
        changeButtons.add(new ChangeButton(refLinks, 512, 480));
        changeButtons.add(new ChangeButton(refLinks, 512, 540));
        changeButtons.add(new ChangeButton(refLinks, 512, 600));
        changeButtons.add(new ChangeButton(refLinks, 512, 660));
        lookingAtChange = false;
    }

    @Override
    public void Update() {
        if (buffer == 0) {
            buffer = bufferSeconds;
            canSwitch = true;
        } else
            buffer--;
        if (!waitingForKey) {
            if (lookingAtChange == false) {
                if (currentMainButtonIndex == -1) {
                    if (refLinks.GetKeyManager().left == true) {
                        currentMainButtonIndex = mainButtons.size() - 1;
                        canSwitch = false;
                        buffer = bufferSeconds;
                    }
                    if (refLinks.GetKeyManager().right == true) {
                        currentMainButtonIndex = 0;
                        canSwitch = false;
                        buffer = bufferSeconds;
                    }
                } else if (refLinks.GetKeyManager().enter == true) {
                    currentMainButton.execute();
                    lookingAtChange = true;
                    currentChangeButtonIndex = -1;
                } else if (refLinks.GetKeyManager().left == true) {
                    if (currentMainButtonIndex != 0 && canSwitch) {
                        currentMainButtonIndex--;
                        canSwitch = false;
                        buffer = bufferSeconds;
                    }
                } else if (refLinks.GetKeyManager().right == true) {
                    if (currentMainButtonIndex != mainButtons.size() - 1 && canSwitch) {
                        currentMainButtonIndex++;
                        canSwitch = false;
                        buffer = bufferSeconds;
                    }
                }
                try {
                    currentMainButton = mainButtons.get(currentMainButtonIndex);
                } catch (IndexOutOfBoundsException e) {
                }
                for (Button b : mainButtons) {
                    try {
                        if (currentMainButton.equals(b))
                            b.SetActive(true);
                        else
                            b.SetActive(false);
                    } catch (Exception e) {
                    }
                    b.Update();
                }
            } else {
                if (refLinks.GetKeyManager().escape == true) {
                    lookingAtChange = false;
                    canSwitch = false;
                    buffer = bufferSeconds;
                } else {
                    if (currentChangeButtonIndex == -1) {
                        if (refLinks.GetKeyManager().up == true) {
                            currentChangeButtonIndex = changeButtons.size() - 1;
                            canSwitch = false;
                            buffer = bufferSeconds;
                        }
                        if (refLinks.GetKeyManager().down == true) {
                            currentChangeButtonIndex = 0;
                            canSwitch = false;
                            buffer = bufferSeconds;
                        }
                    } else if (refLinks.GetKeyManager().enter == true) {
                        waitingForKey = true;
                        currentChangeButton.execute();
                        keyToChange = currentChangeButtonIndex + 1;
                        canSwitch = false;
                        buffer = bufferSeconds;

                    } else if (refLinks.GetKeyManager().up == true) {
                        if (currentChangeButtonIndex != 0 && canSwitch) {
                            currentChangeButtonIndex--;
                            canSwitch = false;
                            buffer = bufferSeconds;
                        }
                    } else if (refLinks.GetKeyManager().down == true) {
                        if (currentChangeButtonIndex != changeButtons.size() - 1 && canSwitch) {
                            currentChangeButtonIndex++;
                            canSwitch = false;
                            buffer = bufferSeconds;
                        }
                    }
                    try {
                        currentChangeButton = changeButtons.get(currentChangeButtonIndex);
                    } catch (IndexOutOfBoundsException e) {
                    }
                    for (Button b : changeButtons) {
                        try {
                            if (currentChangeButton.equals(b))
                                b.SetActive(true);
                            else
                                b.SetActive(false);
                        } catch (Exception e) {
                        }
                        b.Update();
                    }
                }
            }
        } else {
            if (refLinks.GetKeyManager().GetWaiting() == false) {
                //System.out.println(refLinks.GetKeyManager().GetLastKey());
                SettingsLoader.UpdateSetting(playerId, keyToChange, refLinks.GetKeyManager().GetLastKey());
                refLinks.GetKeyManager().LoadSettings();
                waitingForKey = false;
            }
        }
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(background, 0, 0, refLinks.GetWidth(), refLinks.GetHeight(), null);
        g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 30));
        if (!waitingForKey) {
            if (playerId == 1) {

                p1up = returnAscii(Player1Settings.up);
                p1down = returnAscii(Player1Settings.down);
                p1right = returnAscii(Player1Settings.right);
                p1left = returnAscii(Player1Settings.left);
                p1bomb = returnAscii(Player1Settings.bomb);

                g.drawString("Player 1 Up - " + p1up, 60, 450);
                g.drawString("Player 1 Down - " + p1down, 60, 510);
                g.drawString("Player 1 Right - " + p1right, 60, 570);
                g.drawString("Player 1 Left - " + p1left, 60, 630);
                g.drawString("Player 1 Bomb - " + p1bomb, 60, 690);

            } else {

                p2up = returnAscii(Player2Settings.up);
                p2down = returnAscii(Player2Settings.down);
                p2right = returnAscii(Player2Settings.right);
                p2left = returnAscii(Player2Settings.left);
                p2bomb = returnAscii(Player2Settings.bomb);

                g.drawString("Player 2 Up - " + p2up, 60, 450);
                g.drawString("Player 2 Down - " + p2down, 60, 510);
                g.drawString("Player 2 Right - " + p2right, 60, 570);
                g.drawString("Player 2 Left - " + p2left, 60, 630);
                g.drawString("Player 2 Bomb - " + p2bomb, 60, 690);
            }

            if (lookingAtChange)
                for (Button b : changeButtons)
                    b.Draw(g);

        } else {
            g.drawString("Press new key:", 60, 450);
        }

        for (Button b : mainButtons)
            b.Draw(g);

        //System.out.println(p1up + " " + p1down + " " + p1right + " " + p1left + " " + p1bomb);
    }

    private String returnAscii(int a) {
        switch (a) {
            case KeyEvent.VK_DOWN:
                return "DOWN";
            case KeyEvent.VK_UP:
                return "UP";
            case KeyEvent.VK_LEFT:
                return "LEFT";
            case KeyEvent.VK_RIGHT:
                return "RIGHT";
            case KeyEvent.VK_ENTER:
                return "ENTER";
            case KeyEvent.VK_ESCAPE:
                return "ESCAPE";
            case KeyEvent.VK_SPACE:
                return "SPACE";
            default:
                return Character.toString((char) a);


        }
    }
}
