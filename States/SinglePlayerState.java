package Nuclear.States;

import Nuclear.Graphics.Assets;
import Nuclear.Items.Character;
import Nuclear.Items.CharacterFactory;
import Nuclear.RefLinks;
import Nuclear.Settings.SettingsLoader;

import java.awt.*;
import java.util.ArrayList;

/*!
 * A concrete PlayState allowing a player to battle the computer
 * Has 2 different levels: 1 - against a single computer; 2 - against three computers
 * Upon defeating the first level, the player has the choice to either go back to the main menu or continue on to
 * the second level
 */

public class SinglePlayerState extends PlayState {

    private Character hero;
    private ArrayList<Character> villains;
    private int level;
    private boolean villainsDead;
    private boolean nextLevel;

    private long startTime, finalTime, totalTime = -1;

    public SinglePlayerState(RefLinks refLink, int level) {
        super(refLink);
        startTime = System.currentTimeMillis();
        this.level = level;
        InitializeCharacters();
    }

    @Override
    public void Update() {
        if (nextLevel) {
            if (refLink.GetKeyManager().escape)
                refLink.GetGame().SetState(new MenuState(refLink));
            if (refLink.GetKeyManager().enter)
                refLink.GetGame().SetState(new SinglePlayerState(refLink, 2));
        } else {
            if (timer == 0) {
                if (level == 1 && !hero.GetState())
                    nextLevel = true;
                else
                    refLink.GetGame().SetState(new MenuState(refLink));
            } else
                checkVillains();
            if (hero.GetState() || villainsDead || gameIsOver) {
                finalTime = System.currentTimeMillis();
                if (totalTime == -1) {
                    totalTime = finalTime - startTime;
                    if(!hero.GetState())
                        SettingsLoader.InsertHighscore(totalTime);
                }
                gameIsOver = true;
                if (hero.GetState())
                    winningMessage = computerwon;
                else
                    winningMessage = player1won;
                timer--;
            } else {
                map.Update();
                hero.Update();
                UpdateEnemies(hero);
                for (Character v : villains) {
                    v.Update();
                    UpdateEnemies(v);
                }
            }
        }
    }

    @Override
    public void Draw(Graphics g) {
        if (nextLevel) {
            g.drawImage(Assets.background, 0, 0, refLink.GetWidth(), refLink.GetHeight(), null);
            g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 30));
            g.drawString("ENTER - go to the next level", 60, 450);
            g.drawString("ESC - go to the main menu", 60, 510);
        } else {
            map.Draw(g);
            if (hero.GetTimer() != 0)
                hero.Draw(g);

            for (Character v : villains)
                if (v.GetTimer() != 0)
                    v.Draw(g);

            if (gameIsOver)
                g.drawImage(winningMessage, refLink.GetWidth() / 2 - winningMessage.getWidth() / 2, refLink.GetHeight() / 2 - winningMessage.getHeight() / 2, null);
        }
    }

    @Override
    public void InitializeCharacters() {
        CharacterFactory characterFactory = new CharacterFactory();
        hero = characterFactory.GetCharacter("Hero", refLink, 600, 600, 1);
        villains = new ArrayList<>(level);
        villains.add(characterFactory.GetCharacter("Villain", refLink, 120, 120, 0));
        if (level == 2) {
            villains.add(characterFactory.GetCharacter("Villain", refLink, 120, 600, 0));
            villains.add(characterFactory.GetCharacter("Villain", refLink, 600, 120, 0));
        }

        for (Character v1 : villains) {
            hero.addEnemy(v1);
            v1.addEnemy(hero);
            for (Character v2 : villains) {
                if (!v1.equals(v2))
                    v1.addEnemy(v2);
            }
        }

    }

    private void checkVillains() {
        boolean okay = true;
        for (Character v : villains)
            if (!v.GetState()) {
                okay = false;
                break;
            }
        villainsDead = okay;
    }
}
