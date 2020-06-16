package Nuclear.States;

import java.awt.*;

import Nuclear.RefLinks;

/*!
 * At every point in time, the game is in a fixed State
 */

public abstract class State {

    private static State previousState = null;
    private static State currentState = null;
    protected RefLinks refLink;

    public State(RefLinks refLink) {
        this.refLink = refLink;
    }


    public static void SetState(State state) {
        previousState = currentState;
        currentState = state;
    }

    public abstract void Update();

    public abstract void Draw(Graphics g);
}
