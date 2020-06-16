package Nuclear.States;

import Nuclear.RefLinks;
import Nuclear.UserInterface.MenuUserInterface;
import Nuclear.UserInterface.UserInterface;

import java.awt.*;

/**
 * The main menu with which the game starts.
 */
public class MenuState extends State
{
    private UserInterface ui;


    public MenuState(RefLinks refLink)
    {
        super(refLink);
        ui = new MenuUserInterface(refLink);
    }
    @Override
    public void Update()
    {
        ui.Update();
    }

    @Override
    public void Draw(Graphics g)
    {
        ui.Draw(g);
    }
}
