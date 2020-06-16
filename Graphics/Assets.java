package Nuclear.Graphics;

import java.awt.image.BufferedImage;

/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets {
    /// Referinte catre elementele grafice (dale) utilizate in joc.
    public static BufferedImage soil;
    public static BufferedImage grass;
    public static BufferedImage rock;
    public static BufferedImage boulder;
    public static BufferedImage box;
    public static BufferedImage tree;
    public static BufferedImage stone;
    public static BufferedImage ice;
    public static BufferedImage icetree;

    /*!
     * Every image necessary to make player animations as well as bomb animations
     */
    public static BufferedImage player1Idle;
    public static BufferedImage player1Right1;
    public static BufferedImage player1Right2;
    public static BufferedImage player1Left1;
    public static BufferedImage player1Left2;
    public static BufferedImage player1Up1;
    public static BufferedImage player1Up2;
    public static BufferedImage player1Down1;
    public static BufferedImage player1Down2;
    public static BufferedImage player1Dead;

    public static BufferedImage player2Idle;
    public static BufferedImage player2Right1;
    public static BufferedImage player2Right2;
    public static BufferedImage player2Left1;
    public static BufferedImage player2Left2;
    public static BufferedImage player2Up1;
    public static BufferedImage player2Up2;
    public static BufferedImage player2Down1;
    public static BufferedImage player2Down2;
    public static BufferedImage player2Dead;

    public static BufferedImage bomb1;
    public static BufferedImage bomb2;
    public static BufferedImage explosion;
    public static BufferedImage explosionVertical;
    public static BufferedImage explosionHorizontal;

    public static BufferedImage bomb1Red;
    public static BufferedImage bomb2Red;
    public static BufferedImage explosionRed;
    public static BufferedImage explosionVerticalRed;
    public static BufferedImage explosionHorizontalRed;


    /*!
     * Images for the power-ups
     */
    public static BufferedImage speedPowerUp;
    public static BufferedImage detonationDistancePowerUp;


    /*!
     * images, default and active for buttons
     * used in the user interfaces
     */
    public static BufferedImage playVsComputerDefault;
    public static BufferedImage playVsComputerActive;
    public static BufferedImage settingsDefault;
    public static BufferedImage settingsActive;
    public static BufferedImage exitDefault;
    public static BufferedImage exitActive;
    public static BufferedImage player1Default;
    public static BufferedImage player1Active;
    public static BufferedImage player2Default;
    public static BufferedImage player2Active;
    public static BufferedImage menuDefault;
    public static BufferedImage menuActive;
    public static BufferedImage changeDefault;
    public static BufferedImage changeActive;
    public static BufferedImage playDefault;
    public static BufferedImage playActive;
    public static BufferedImage multiplayerDefault;
    public static BufferedImage multiplayerActive;
    public static BufferedImage backDefault;
    public static BufferedImage backActive;
    public static BufferedImage highscoreDefault;
    public static BufferedImage highscoreActive;

    public static BufferedImage background;

    public static BufferedImage player1Won;
    public static BufferedImage player2Won;
    public static BufferedImage computerWon;

    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init() {

        SpriteSheet firstSheet = new SpriteSheet(ImageLoader.LoadImage("/textures/tilemap.png"));
        player1Idle = firstSheet.crop(439, 69, 17, 21);
        player1Right1 = firstSheet.crop(646, 69, 16, 21);
        player1Right2 = firstSheet.crop(669, 69, 16, 21);
        player1Left1 = firstSheet.cropMirrorVertical(646, 69, 16, 21);
        player1Left2 = firstSheet.cropMirrorVertical(669, 69, 16, 21);
        player1Up1 = firstSheet.crop(554, 69, 17, 21);
        player1Up2 = firstSheet.crop(577, 69, 17, 21);
        player1Down1 = firstSheet.cropMirrorHorizontal(554, 69, 17, 21);
        player1Down2 = firstSheet.cropMirrorHorizontal(577, 69, 17, 21);
        player1Dead = firstSheet.crop(623, 322, 17, 21);

        player2Idle = firstSheet.crop(439, 46, 17, 21);
        player2Right1 = firstSheet.crop(646, 46, 16, 21);
        player2Right2 = firstSheet.crop(669, 46, 16, 21);
        player2Left1 = firstSheet.cropMirrorVertical(646, 46, 16, 21);
        player2Left2 = firstSheet.cropMirrorVertical(669, 46, 16, 21);
        player2Up1 = firstSheet.crop(554, 46, 17, 21);
        player2Up2 = firstSheet.crop(577, 46, 17, 21);
        player2Down1 = firstSheet.cropMirrorHorizontal(554, 46, 17, 21);
        player2Down2 = firstSheet.cropMirrorHorizontal(577, 46, 17, 21);
        player2Dead = firstSheet.crop(623, 322, 17, 21);

        box = firstSheet.crop(253, 138, 21, 21);
        ice = firstSheet.crop(23, 368, 21, 21);
        icetree = firstSheet.crop(46, 506, 21, 21);
        grass = firstSheet.crop(23, 552, 21, 21);

        bomb1 = firstSheet.crop(507, 116, 19, 19);
        bomb2 = firstSheet.crop(621, 160, 21, 21);
        explosion = firstSheet.crop(623, 669, 17, 17);
        explosionVertical = firstSheet.crop(583, 644, 5, 21);
        explosionHorizontal = firstSheet.crop(575, 675, 21, 5);

        bomb1Red = firstSheet.crop(484, 116, 19, 19);
        bomb2Red = firstSheet.crop(621, 160, 21, 21);
        explosionRed = firstSheet.crop(600, 669, 17, 17);
        explosionVerticalRed = firstSheet.crop(560, 644, 5, 21);
        explosionHorizontalRed = firstSheet.crop(552, 675, 21, 5);


        SpriteSheet secondSheet = new SpriteSheet(ImageLoader.LoadImage("/textures/towerDefense_tilesheet.png"));
        //grass = secondSheet.crop(1216, 384, 64, 64);
        soil = secondSheet.crop(1280, 384, 64, 64);
        rock = secondSheet.crop(1408, 384, 64, 64);
        tree = secondSheet.crop(960, 320, 64, 64);
        stone = secondSheet.crop(1344, 320, 64, 64);
        boulder = secondSheet.crop(1280, 448, 64, 64);

        speedPowerUp = ImageLoader.LoadImage("/textures/SpeedPowerup.png");
        detonationDistancePowerUp = ImageLoader.LoadImage("/textures/FlamePowerup.png");

        playVsComputerDefault = ImageLoader.LoadImage("/buttons/button_play-vs-computer-default.png");
        playVsComputerActive = ImageLoader.LoadImage("/buttons/button_play-vs-computer-active.png");
        settingsDefault = ImageLoader.LoadImage("/buttons/button_settings-default.png");
        settingsActive = ImageLoader.LoadImage("/buttons/button_settings-active.png");
        exitDefault = ImageLoader.LoadImage("/buttons/button_exit-default.png");
        exitActive = ImageLoader.LoadImage("/buttons/button_exit-active.png");
        player1Default = ImageLoader.LoadImage("/buttons/button_player1-default.png");
        player1Active = ImageLoader.LoadImage("/buttons/button_player1-active.png");
        player2Default = ImageLoader.LoadImage("/buttons/button_player2-default.png");
        player2Active = ImageLoader.LoadImage("/buttons/button_player2-active.png");
        menuDefault = ImageLoader.LoadImage("/buttons/button_menu-default.png");
        menuActive = ImageLoader.LoadImage("/buttons/button_menu-active.png");
        changeDefault = ImageLoader.LoadImage("/buttons/button_change-default.png");
        changeActive = ImageLoader.LoadImage("/buttons/button_change-active.png");
        playDefault = ImageLoader.LoadImage("/buttons/button_play-default.png");
        playActive = ImageLoader.LoadImage("/buttons/button_play-active.png");
        multiplayerDefault = ImageLoader.LoadImage("/buttons/button_multiplayer-default.png");
        multiplayerActive = ImageLoader.LoadImage("/buttons/button_multiplayer-active.png");
        backDefault = ImageLoader.LoadImage("/buttons/button_back-default.png");
        backActive = ImageLoader.LoadImage("/buttons/button_back-active.png");
        highscoreDefault = ImageLoader.LoadImage("/buttons/button_highscore-default.png");
        highscoreActive = ImageLoader.LoadImage("/buttons/button_highscore-active.png");

        background = ImageLoader.LoadImage("/background/background.png");

        player1Won = ImageLoader.LoadImage("/messages/player1won.png");
        player2Won = ImageLoader.LoadImage("/messages/player2won.png");
        computerWon = ImageLoader.LoadImage("/messages/computerwon.png");
    }
}
