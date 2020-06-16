package Nuclear.Items;

import Nuclear.RefLinks;

import java.util.ArrayList;


/*! \class public abstract class Character extends Item
    \brief Defineste notiunea abstracta de caracter/individ/fiinta din joc.

 */
public abstract class Character extends Item {
    public static final int DEFAULT_SPEED = 2; /*!< Viteza implicita a unu caracter.*/
    public static final int DEFAULT_CREATURE_WIDTH = 32;   /*!< Latimea implicita a imaginii caracterului.*/
    public static final int DEFAULT_CREATURE_HEIGHT = 36;   /*!< Inaltimea implicita a imaginii caracterului.*/

    protected boolean isDead;
    protected int deathTimer = 60;
    protected ArrayList<Character> enemy = new ArrayList<Character>();
    protected int speed;  /*!< Retine viteza de deplasare caracterului.*/
    protected float xMove;  /*!< Retine noua pozitie a caracterului pe axa X.*/
    protected float yMove;  /*!< Retine noua pozitie a caracterului pe axa Y.*/
    protected Bomb bomb;    /*!< Every Character has a unique Bomb*/
    protected int myDetonationDistance = 48;

    /*! \fn public Character(RefLinks refLink, float x, float y, int width, int height)
        \brief Constructor de initializare al clasei Character

        \param refLink Referinta catre obiectul shortcut (care retine alte referinte utile/necesare in joc).
        \param x Pozitia de start pa axa X a caracterului.
        \param y Pozitia de start pa axa Y a caracterului.
        \param width Latimea imaginii caracterului.
        \param height Inaltimea imaginii caracterului.
     */
    public Character(RefLinks refLink, float x, float y, int width, int height) {
        ///Apel constructor la clasei de baza
        super(refLink, x, y, width, height);
        //Seteaza pe valorile implicite pentru viata, viteza si distantele de deplasare
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
        isDead = false;
    }

    /*! \fn public void Move()
        \brief Modifica pozitia caracterului
     */
    public void Move() {
        ///Modifica pozitia caracterului pe axa X.
        ///Modifica pozitia caracterului pe axa Y.
        MoveX();
        MoveY();
    }

    /*! \fn public void MoveX()
        \brief Modifica pozitia caracterului pe axa X.
     */
    public void MoveX() {
        ///Aduna la pozitia curenta numarul de pixeli cu care trebuie sa se deplaseze pe axa X.
        x += xMove;
    }

    /*! \fn public void MoveY()
        \brief Modifica pozitia caracterului pe axa Y.
     */
    public void MoveY() {
        ///Aduna la pozitia curenta numarul de pixeli cu care trebuie sa se deplaseze pe axa Y.
        y += yMove;
    }

    /*! \fn public int GetSpeed()
        \brief Returneaza viteza caracterului.
     */
    public int GetSpeed() {
        return speed;
    }


    /*! \fn public void SetSpeed(float speed)
        \brief
     */
    public void SetSpeed(int speed) {
        this.speed = speed;
    }

    /*! \fn public float GetXMove()
        \brief Returneaza distanta in pixeli pe axa X cu care este actualizata pozitia caracterului.
     */
    public float GetXMove() {
        return xMove;
    }

    /*! \fn public float GetYMove()
        \brief Returneaza distanta in pixeli pe axa Y cu care este actualizata pozitia caracterului.
     */
    public float GetYMove() {
        return yMove;
    }

    /*! \fn public void SetXMove(float xMove)
        \brief Seteaza distanta in pixeli pe axa X cu care va fi actualizata pozitia caracterului.
     */
    public void SetXMove(float xMove) {
        this.xMove = xMove;
    }

    /*! \fn public void SetYMove(float yMove)
        \brief Seteaza distanta in pixeli pe axa Y cu care va fi actualizata pozitia caracterului.
     */
    public void SetYMove(float yMove) {
        this.yMove = yMove;
    }

    public float GetX() {
        return x;
    }

    public float GetY() {
        return y;
    }

    public void addEnemy(Character enemy) {
        this.enemy.add(enemy);
    }

    public void removeEnemy(Character enemy) {
        this.enemy.remove(enemy);
    }

    public boolean GetState() {
        return isDead;
    }

    public ArrayList<Character> GetEnemy() {
        return this.enemy;
    }

    public void killCharacter() {
        this.isDead = true;
    }

    public int GetTimer() {
        return deathTimer;
    }


    /*!
     * Checks at any given frame whether the Character walked on a Power-Up or not
     */
    public void PickUpPowerUp() {
        //dreapta
        if (refLink.GetMap().GetPowerUp(this.x + bounds.x + bounds.width, this.y + bounds.y) != null)
            refLink.GetMap().GetPowerUp(this.x + bounds.x + bounds.width, this.y + bounds.y).PickUp(this);

        else if (refLink.GetMap().GetPowerUp(this.x + bounds.x + bounds.width, this.y + bounds.y + bounds.height) != null)
            refLink.GetMap().GetPowerUp(this.x + bounds.x + bounds.width, this.y + bounds.y + bounds.height).PickUp(this);

            //stanga
        else if (refLink.GetMap().GetPowerUp(this.x + bounds.x, this.y + bounds.y) != null)
            refLink.GetMap().GetPowerUp(this.x + bounds.x, this.y + bounds.y).PickUp(this);
        else if (refLink.GetMap().GetPowerUp(this.x + bounds.x, this.y + bounds.y + bounds.height) != null)
            refLink.GetMap().GetPowerUp(this.x + bounds.x, this.y + bounds.y + bounds.height).PickUp(this);

            //sus
        else if (refLink.GetMap().GetPowerUp(this.x + bounds.x, this.y + bounds.y) != null)
            refLink.GetMap().GetPowerUp(this.x + bounds.x, this.y + bounds.y).PickUp(this);
        else if (refLink.GetMap().GetPowerUp(this.x + bounds.x + bounds.width, this.y + bounds.y) != null)
            refLink.GetMap().GetPowerUp(this.x + bounds.x + bounds.width, this.y + bounds.y).PickUp(this);

            //jos
        else if (refLink.GetMap().GetPowerUp(this.x + bounds.x, this.y + bounds.y + bounds.height) != null)
            refLink.GetMap().GetPowerUp(this.x + bounds.x, this.y + bounds.y + bounds.height).PickUp(this);
        else if (refLink.GetMap().GetPowerUp(this.x + bounds.x + bounds.width, this.y + bounds.y + bounds.height) != null)
            refLink.GetMap().GetPowerUp(this.x + bounds.x + bounds.width, this.y + bounds.y + bounds.height).PickUp(this);
    }

    public Bomb GetBomb() {
        return bomb;
    }

    public void SetDetonationDistance(int newDetonationDistance) {
        this.myDetonationDistance = newDetonationDistance;
    }

    public int GetDetonationDistance() {
        return myDetonationDistance;
    }

}

