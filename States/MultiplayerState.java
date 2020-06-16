package Nuclear.States;

import Nuclear.Items.Character;
import Nuclear.Items.CharacterFactory;
import Nuclear.RefLinks;

import java.awt.*;

/*!
 * A concrete PlayState allowing 2 players to battle each other;
 */

public class MultiplayerState extends PlayState {

    private Character player1, player2;

    public MultiplayerState(RefLinks refLink) {
        super(refLink);
        InitializeCharacters();
    }

    @Override
    public void Update() {
        if (timer == 0)
            refLink.GetGame().SetState(new MenuState(refLink));
        ;
        if (player1.GetState() || player2.GetState() || gameIsOver) {
            gameIsOver = true;
            if (player1.GetState())
                winningMessage = player2won;
            else
                winningMessage = player1won;
            timer--;
        } else {
            map.Update();
            player1.Update();
            player2.Update();
            UpdateEnemies(player1);
            UpdateEnemies(player2);
        }
    }

    @Override
    public void Draw(Graphics g) {
        map.Draw(g);

        if (player1.GetTimer() != 0)
            player1.Draw(g);

        if (player2.GetTimer() != 0)
            player2.Draw(g);

        if (gameIsOver)
            g.drawImage(winningMessage, refLink.GetWidth() / 2 - winningMessage.getWidth() / 2, refLink.GetHeight() / 2 - winningMessage.getHeight() / 2, null);
    }

    @Override
    public void InitializeCharacters() {
        CharacterFactory characterFactory = new CharacterFactory();
        player1 = characterFactory.GetCharacter("Hero", refLink, 120, 120, 1);
        player2 = characterFactory.GetCharacter("Hero", refLink, 600, 600, 2);

        player1.addEnemy(player2);
        player2.addEnemy(player1);
    }


}
