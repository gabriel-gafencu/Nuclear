package Nuclear.Input;

import Nuclear.Settings.Player1Settings;
import Nuclear.Settings.Player2Settings;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

/*! \class public class KeyManager implements KeyListener
    \brief Gestioneaza intrarea (input-ul) de tastatura.

    Clasa citeste daca au fost apasata o tasta, stabiliteste ce tasta a fost actionata si seteaza corespunzator un flag.
    In program trebuie sa se tina cont de flagul aferent tastei de interes. Daca flagul respectiv este true inseamna
    ca tasta respectiva a fost apasata si false nu a fost apasata.
 */
public class KeyManager implements KeyListener {
    private boolean[] keys; /*!< Vector de flaguri pentru toate tastele. Tastele vor fi regasite dupa cod [0 - 255]*/
    public boolean up;      /*!< Flag pentru tasta "sus" apasata.*/
    public boolean down;    /*!< Flag pentru tasta "jos" apasata.*/
    public boolean left;    /*!< Flag pentru tasta "stanga" apasata.*/
    public boolean right;   /*!< Flag pentru tasta "dreapta" apasata.*/
    public boolean reset;
    public boolean enter;
    public boolean escape;
    public boolean backspace;

    public boolean player1Up, player1Down, player1Right, player1Left, player1Bomb;
    public boolean player2Up, player2Down, player2Right, player2Left, player2Bomb;

    private int p1UpKey, p1DownKey, p1RightKey, p1LeftKey, p1BombKey;
    private int p2UpKey, p2DownKey, p2RightKey, p2LeftKey, p2BombKey;


    /*!
     * waitingForKey and lastKeyCode help tell the KeyManager that I want exactly the last key input
     * Necessary for changing the user settings
     */
    public boolean waitingForKey;
    private int lastKeyCode;

    /*! \fn public KeyManager()
        \brief Constructorul clasei.
     */
    public KeyManager() {
        ///Constructie vector de flaguri aferente tastelor.
        keys = new boolean[525];
    }


    public void Update() {
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        right = keys[KeyEvent.VK_RIGHT];
        left = keys[KeyEvent.VK_LEFT];
        backspace = keys[KeyEvent.VK_BACK_SPACE];

        reset = keys[KeyEvent.VK_R];
        enter = keys[KeyEvent.VK_ENTER];
        escape = keys[KeyEvent.VK_ESCAPE];


        player1Up = keys[p1UpKey];
        player1Down = keys[p1DownKey];
        player1Right = keys[p1RightKey];
        player1Left = keys[p1LeftKey];
        player1Bomb = keys[p1BombKey];

        player2Up = keys[p2UpKey];
        player2Down = keys[p2DownKey];
        player2Right = keys[p2RightKey];
        player2Left = keys[p2LeftKey];
        player2Bomb = keys[p2BombKey];

        //KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_A, KeyEvent.VK_G;
    }

    /*! \fn public void keyPressed(KeyEvent e)
        \brief Functie ce va fi apelata atunci cand un un eveniment de tasta apasata este generat.

         \param e obiectul eveniment de tastatura.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        ///se retine in vectorul de flaguri ca o tasta a fost apasata.
        keys[e.getKeyCode()] = true;
        if (waitingForKey) {
            lastKeyCode = e.getKeyCode();
            waitingForKey = false;
        }
    }

    /*! \fn public void keyReleased(KeyEvent e)
        \brief Functie ce va fi apelata atunci cand un un eveniment de tasta eliberata este generat.

         \param e obiectul eveniment de tastatura.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        ///se retine in vectorul de flaguri ca o tasta a fost eliberata.
        keys[e.getKeyCode()] = false;
    }

    /*! \fn public void keyTyped(KeyEvent e)
        \brief Functie ce va fi apelata atunci cand o tasta a fost apasata si eliberata.
        Momentan aceasta functie nu este utila in program.
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void LoadSettings() {
        Player1Settings.InitializeSettings();
        Player2Settings.InitializeSettings();

        p1UpKey = Player1Settings.up;
        p1DownKey = Player1Settings.down;
        p1RightKey = Player1Settings.right;
        p1LeftKey = Player1Settings.left;
        p1BombKey = Player1Settings.bomb;

        p2UpKey = Player2Settings.up;
        p2DownKey = Player2Settings.down;
        p2RightKey = Player2Settings.right;
        p2LeftKey = Player2Settings.left;
        p2BombKey = Player2Settings.bomb;
    }

    public int GetLastKey() {
        return lastKeyCode;
    }

    public void SetWaiting() {
        waitingForKey = true;
    }

    public boolean GetWaiting() {
        return waitingForKey;
    }
}
