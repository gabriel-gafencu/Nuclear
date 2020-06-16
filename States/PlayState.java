package Nuclear.States;

import Nuclear.Graphics.Assets;
import Nuclear.Items.Character;
import Nuclear.RefLinks;
import Nuclear.Maps.Map;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Random;

/*!
 * An abstract State that basically is the main part of the game
 */

public abstract class PlayState extends State {
    protected Map map;

    protected BufferedImage player1won = Assets.player1Won;
    protected BufferedImage player2won = Assets.player2Won;
    protected BufferedImage computerwon = Assets.computerWon;
    protected BufferedImage winningMessage;

    protected boolean gameIsOver = false;
    protected int timer = 180;


    public PlayState(RefLinks refLink) {
        super(refLink);
        Random r = new Random();
        int seed = r.nextInt(100);
        if (seed <= 33)
            map = new Map(refLink, 1);
        else if (seed <= 66)
            map = new Map(refLink, 2);
        else
            map = new Map(refLink, 3);
        refLink.SetMap(map);
    }

    @Override
    public void Update() {
    }


    @Override
    public void Draw(Graphics g) {
    }

    public void UpdateEnemies(Character c) {
        for (Iterator<Character> iterator = c.GetEnemy().iterator(); iterator.hasNext(); ) {
            Character enemy = iterator.next();
            if (enemy.GetState())
                iterator.remove();
        }
    }


    public abstract void InitializeCharacters();
}
