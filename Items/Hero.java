package Nuclear.Items;

import java.awt.*;
import java.awt.image.BufferedImage;

import Nuclear.RefLinks;
import Nuclear.Graphics.Assets;

/*! \class public class Hero extends Character
    \brief Implementeaza notiunea de erou/player (caracterul controlat de jucator).

    Elementele suplimentare pe care le aduce fata de clasa de baza sunt:
        imaginea (acest atribut poate fi ridicat si in clasa de baza)
        deplasarea
        atacul (nu este implementat momentan)
        dreptunghiul de coliziune
 */
public class Hero extends Character {

    private BufferedImage image;    /*!< Referinta catre imaginea curenta a eroului.*/
    private BufferedImage imageIdle;
    private BufferedImage imageUp1;
    private BufferedImage imageUp2;
    private BufferedImage imageDown1;
    private BufferedImage imageDown2;
    private BufferedImage imageRight1;
    private BufferedImage imageRight2;
    private BufferedImage imageLeft1;
    private BufferedImage imageLeft2;
    private BufferedImage imageDead;

    private boolean pressedUp, pressedDown, pressedRight, pressedLeft, pressedBomb;

    private boolean up, down, right, left;
    private int upTimer, downTimer, rightTimer, leftTimer;

    private int playerId;   /// every Hero has a playerId, 1 - main player; 2 - secondary player

    /*! \fn public Hero(RefLinks refLink, float x, float y)
        \brief Constructorul de initializare al clasei Hero.

        \param refLink Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a eroului.
        \param y Pozitia initiala pe axa Y a eroului.
     */
    public Hero(RefLinks refLink, float x, float y, int playerId) {
        ///Apel al constructorului clasei de baza
        super(refLink, x, y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT);
        ///Seteaza imaginea de start a eroului
        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea implicita(normala)
        bounds.x = 4;
        bounds.y = 6;
        bounds.width = 24;
        bounds.height = 30;


        up = false;
        down = false;
        right = false;
        left = false;
        upTimer = 0;
        downTimer = 0;
        rightTimer = 0;
        leftTimer = 0;

        this.playerId = playerId;

        switch (playerId) {
            case 1:
                imageIdle = Assets.player1Idle;
                imageUp1 = Assets.player1Up1;
                imageUp2 = Assets.player1Up2;
                imageDown1 = Assets.player1Down1;
                imageDown2 = Assets.player1Down2;
                imageRight1 = Assets.player1Right1;
                imageRight2 = Assets.player1Right2;
                imageLeft1 = Assets.player1Left1;
                imageLeft2 = Assets.player1Left2;
                imageDead = Assets.player1Dead;
                break;
            case 2:
                imageIdle = Assets.player2Idle;
                imageUp1 = Assets.player2Up1;
                imageUp2 = Assets.player2Up2;
                imageDown1 = Assets.player2Down1;
                imageDown2 = Assets.player2Down2;
                imageRight1 = Assets.player2Right1;
                imageRight2 = Assets.player2Right2;
                imageLeft1 = Assets.player2Left1;
                imageLeft2 = Assets.player2Left2;
                imageDead = Assets.player2Dead;
                break;
        }
        image = imageIdle;
    }

    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea eroului.
     */
    @Override
    public void Update() {
        ///Verifica daca a fost apasata o tasta
        if (!isDead) {
            GetInput();
            if (bomb != null) {
                bomb.Update();
                if (!bomb.GetState())
                    bomb = null;
            }
            ///Actualizeaza pozitia
            Move();
            PickUpPowerUp();
            boolean upKey = refLink.GetKeyManager().up;
            boolean downKey = refLink.GetKeyManager().down;
            boolean rightKey = refLink.GetKeyManager().right;
            boolean leftKey = refLink.GetKeyManager().left;
            ///Actualizeaza imaginea
            if (!upKey && !downKey && !rightKey && !leftKey)
                image = imageIdle;
        } else {
            if (bomb != null)
                bomb = null;
            if (deathTimer % 15 == 0)
                image = imageIdle;
            else
                image = imageDead;
            if (deathTimer != 0)
                deathTimer--;
        }
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, width, height, null);
        //g.setColor(Color.blue);
        //g.fillRect((int) (x + bounds.x), (int) (y + bounds.y), bounds.width, bounds.height);
        if (bomb != null) {
            bomb.Draw(g);
        }

    }

    /*! \fn private void GetInput()
        \brief Verifica daca a fost apasata o tasta din cele stabilite pentru controlul eroului.
     */
    private void GetInput() {
        ///Implicit eroul nu trebuie sa se deplaseze daca nu este apasata o tasta
        xMove = 0;
        yMove = 0;
        switch (playerId) {
            case 2:
                pressedUp = refLink.GetKeyManager().player2Up;
                pressedDown = refLink.GetKeyManager().player2Down;
                pressedRight = refLink.GetKeyManager().player2Right;
                pressedLeft = refLink.GetKeyManager().player2Left;
                pressedBomb = refLink.GetKeyManager().player2Bomb;
                break;
            case 1:
                pressedUp = refLink.GetKeyManager().player1Up;
                pressedDown = refLink.GetKeyManager().player1Down;
                pressedRight = refLink.GetKeyManager().player1Right;
                pressedLeft = refLink.GetKeyManager().player1Left;
                pressedBomb = refLink.GetKeyManager().player1Bomb;
                break;
        }
        ///Verificare apasare tasta "sus"
        if (pressedUp && y + bounds.y - speed > 0) {
            //if-ul de mai jos verifica coliziunea
            if (refLink.GetMap().GetTerrain(x + bounds.x, y + bounds.y - speed) == null && refLink.GetMap().GetTerrain(x + bounds.x + bounds.width, y + bounds.y - speed) == null)
                yMove = -speed;
            if (upTimer == 10) {
                if (!up) {
                    image = imageUp1;
                    up = true;
                    upTimer = 0;
                } else {
                    image = imageUp2;
                    up = false;
                    upTimer = 0;
                }
            } else
                upTimer++;
        }
        ///Verificare apasare tasta "jos"
        else if (pressedDown && y + speed < refLink.GetGame().GetHeight() - height) {
            if (refLink.GetMap().GetTerrain(x + bounds.x, y + bounds.y + bounds.height + speed) == null && refLink.GetMap().GetTerrain(x + bounds.x + bounds.width, y + bounds.y + bounds.height + speed) == null)
                yMove = speed;
            if (downTimer == 10) {
                if (!down) {
                    image = imageDown1;
                    down = true;
                    downTimer = 0;
                } else {
                    image = imageDown2;
                    down = false;
                    downTimer = 0;
                }
            } else
                downTimer++;
        }
        ///Verificare apasare tasta "dreapta"
        else if (pressedRight && x + bounds.x + bounds.width + speed < refLink.GetGame().GetWidth()) {
            if (refLink.GetMap().GetTerrain(x + bounds.x + speed + bounds.width, y + bounds.y) == null && refLink.GetMap().GetTerrain(x + bounds.x + speed + bounds.width, y + bounds.y + bounds.height) == null)
                xMove = speed;
            if (rightTimer == 10) {
                if (!right) {
                    image = imageRight1;
                    right = true;
                    rightTimer = 0;
                } else {
                    image = imageRight2;
                    right = false;
                    rightTimer = 0;
                }
            } else
                rightTimer++;
        }
        ///Verificare apasare tasta "left"
        else if (pressedLeft && x + bounds.x - speed > 0) {
            if (refLink.GetMap().GetTerrain(x + bounds.x - speed, y + bounds.y) == null && refLink.GetMap().GetTerrain(x + bounds.x - speed, y + bounds.y + bounds.height) == null)
                xMove = -speed;
            if (leftTimer == 10) {
                if (!left) {
                    image = imageLeft1;
                    left = true;
                    leftTimer = 0;
                } else {
                    image = imageLeft2;
                    left = false;
                    leftTimer = 0;
                }
            } else
                leftTimer++;
        }
        //tasta x pentru bomb
        if (pressedBomb && bomb == null) {
            if (playerId == 1)
                bomb = new Bomb(refLink, x + bounds.x + 2, y + bounds.y, Bomb.BOMB_WIDTH, Bomb.BOMB_HEIGHT);
            else
                bomb = new Bomb(refLink, x + bounds.x + 2, y + bounds.y, Bomb.BOMB_WIDTH, Bomb.BOMB_HEIGHT);
            bomb.SetDetionationDistance(myDetonationDistance);
            bomb.SetCharacter(this);
        }

    }


}
