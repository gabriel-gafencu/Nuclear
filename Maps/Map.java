package Nuclear.Maps;

import Nuclear.Items.Bomb;
import Nuclear.Items.PowerUp.DetonationDistancePowerUp;
import Nuclear.Items.PowerUp.PowerUp;
import Nuclear.Items.PowerUp.SpeedPowerUp;
import Nuclear.Items.Terrain.*;
import Nuclear.RefLinks;
import Nuclear.Tiles.Tile;

import java.awt.*;
import java.util.Random;

/*! \class public class Map
    \brief Implementeaza notiunea de harta a jocului.
 */
public class Map {
    private RefLinks refLink;   /*!< O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.*/
    private int width;          /*!< Latimea hartii in numar de dale.*/
    private int height;         /*!< Inaltimea hartii in numar de dale.*/
    private int[][] tiles;     /*!< Referinta catre o matrice cu codurile dalelor ce vor construi harta.*/
    private Terrain[][] terrains;  /// a matrix of the terrain
    private PowerUp[][] powerUps;   /// a matrix of the power-ups
    private Random r = new Random();    /// a random used to determine if a destroyed terrain spawns a power-up
    private int mapType;

    /*! \fn public Map(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public Map(RefLinks refLink, int mapType) {
        /// Retine referinta "shortcut".
        this.refLink = refLink;
        ///incarca harta de start. Functia poate primi ca argument id-ul hartii ce poate fi incarcat.
        this.mapType = mapType;
        LoadWorld();
    }

    /*! \fn public  void Update()
        \brief Actualizarea hartii in functie de evenimente (un copac a fost taiat)
     */
    public void Update() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (powerUps[x][y] != null) {
                    powerUps[x][y].Update();
                }
            }
        }
    }

    /*! \fn public void Draw(Graphics g)
        \brief Functia de desenare a hartii.

        \param g Contextl grafi in care se realizeaza desenarea.
     */
    public void Draw(Graphics g) {
        ///Se parcurge matricea de dale (codurile aferente) si se deseneaza harta respectiva
        for (int y = 0; y < refLink.GetGame().GetHeight() / Tile.TILE_HEIGHT; y++) {
            for (int x = 0; x < refLink.GetGame().GetWidth() / Tile.TILE_WIDTH; x++) {
                {
                    switch (mapType) {
                        case 1:
                            Tile.soilTile.Draw(g, x * Tile.TILE_HEIGHT, y * Tile.TILE_WIDTH);
                            break;
                        case 2:
                            Tile.grassTile.Draw(g, x * Tile.TILE_HEIGHT, y * Tile.TILE_WIDTH);
                            break;
                        case 3:
                            Tile.rockTile.Draw(g, x * Tile.TILE_HEIGHT, y * Tile.TILE_WIDTH);
                    }
                }
            }
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (terrains[x][y] != null) {
                    terrains[x][y].Draw(g);
                }
                if (powerUps[x][y] != null) {
                    powerUps[x][y].Draw(g);
                }
            }
        }
    }

    public Terrain GetTerrain(float x, float y) {
        return terrains[(int) x / Terrain.DEFAULT_WIDTH][(int) y / Terrain.DEFAULT_HEIGHT];
    }

    public PowerUp GetPowerUp(float x, float y) {
        try {
            if (powerUps[(int) x / PowerUp.DEFAULT_WIDTH][(int) y / PowerUp.DEFAULT_HEIGHT] != null && !powerUps[(int) x / PowerUp.DEFAULT_WIDTH][(int) y / PowerUp.DEFAULT_HEIGHT].cantBePickedUp())
                return powerUps[(int) x / PowerUp.DEFAULT_WIDTH][(int) y / PowerUp.DEFAULT_HEIGHT];
            else
                return null;
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }
    }


    /*! \fn private void LoadWorld()
        \brief Functie de incarcare a hartii jocului.
        Aici se poate genera sau incarca din fisier harta. Momentan este incarcata static.
     */
    private void LoadWorld() {
        switch (mapType) {
            case 1:
                this.map = map1;
                break;
            case 2:
                this.map = map2;
                break;
            case 3:
                this.map = map3;
                break;
        }
        //atentie latimea si inaltimea trebuiesc corelate cu dimensiunile ferestrei sau
        //se poate implementa notiunea de camera/cadru de vizualizare al hartii
        ///Se stabileste latimea hartii in numar de dale.
        width = 15;
        ///Se stabileste inaltimea hartii in numar de dale
        height = 15;
        ///Se construieste matricea de coduri de dale
        tiles = new int[width][height];
        terrains = new Terrain[width][height];
        powerUps = new PowerUp[width][height];
        //Se incarca matricea cu coduri
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[x][y] = returnMap(y, x);
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                switch (returnMap(y, x)) {
                    case 1:
                        terrains[x][y] = new BoulderTerrain(refLink, x * Terrain.DEFAULT_WIDTH, y * Terrain.DEFAULT_HEIGHT);
                        break;
                    case 2:
                        terrains[x][y] = new BoxTerrain(refLink, x * Terrain.DEFAULT_WIDTH, y * Terrain.DEFAULT_HEIGHT);
                        break;
                    case 3:
                        terrains[x][y] = new IceTreeTerrain(refLink, x * Terrain.DEFAULT_WIDTH, y * Terrain.DEFAULT_HEIGHT);
                        break;
                    case 4:
                        terrains[x][y] = new IceTerrain(refLink, x * Terrain.DEFAULT_WIDTH, y * Terrain.DEFAULT_HEIGHT);
                        break;
                    case 5:
                        terrains[x][y] = new StoneTerrain(refLink, x * Terrain.DEFAULT_WIDTH, y * Terrain.DEFAULT_HEIGHT);
                        break;
                    case 6:
                        terrains[x][y] = new TreeTerrain(refLink, x * Terrain.DEFAULT_WIDTH, y * Terrain.DEFAULT_HEIGHT);
                        break;
                }
            }
        }
    }

    public void disposeOfPowerUp(PowerUp powerUp) {
        int x = (int) powerUp.GetX() / PowerUp.DEFAULT_WIDTH;
        int y = (int) powerUp.GetY() / PowerUp.DEFAULT_HEIGHT;
        if (this.powerUps[x][y] != null)
            this.powerUps[x][y] = null;

    }

    public boolean disposeOfTerrain(Bomb bomb) {
        int bombX = (int) bomb.GetX();
        int bombY = (int) bomb.GetY();
        int detonationDistance = bomb.GetDetonationDistance();

        //verific in sus
        for (int i = bombY / Terrain.DEFAULT_HEIGHT; i >= (bombY - detonationDistance) / Terrain.DEFAULT_HEIGHT; i--)
            if (i >= 0) {
                if (terrains[bombX / Terrain.DEFAULT_WIDTH][i] != null) {
                    if (terrains[bombX / Terrain.DEFAULT_WIDTH][i].GetDestroyable()) {
                        terrains[bombX / Terrain.DEFAULT_WIDTH][i] = null;
                        spawnPowerUp(bombX / Terrain.DEFAULT_WIDTH, i);
                    }
                    bomb.setTop((i + 1) * Terrain.DEFAULT_HEIGHT);
                    break;
                }
            } else {
                bomb.setTop(0);
            }

        //verific in jos
        for (int i = (bombY + bomb.GetBoundHeight()) / Terrain.DEFAULT_HEIGHT; i <= (bombY + bomb.GetBoundHeight() + detonationDistance) / Terrain.DEFAULT_HEIGHT; i++)
            if (i < height) {
                if (terrains[bombX / Terrain.DEFAULT_WIDTH][i] != null) {
                    if (terrains[bombX / Terrain.DEFAULT_WIDTH][i].GetDestroyable()) {
                        terrains[bombX / Terrain.DEFAULT_WIDTH][i] = null;
                        spawnPowerUp(bombX / Terrain.DEFAULT_WIDTH, i);
                    }
                    bomb.setBot(i * Terrain.DEFAULT_HEIGHT);
                    break;
                }
            } else {
                bomb.setBot(height * Terrain.DEFAULT_HEIGHT);
            }

        //verific in dreapta
        for (int i = (bombX + bomb.GetBoundWidth()) / Terrain.DEFAULT_WIDTH; i <= (bombX + bomb.GetBoundWidth() + detonationDistance) / Terrain.DEFAULT_WIDTH; i++)
            if (i < width) {
                if (terrains[i][bombY / Terrain.DEFAULT_HEIGHT] != null) {
                    if (terrains[i][bombY / Terrain.DEFAULT_HEIGHT].GetDestroyable()) {
                        terrains[i][bombY / Terrain.DEFAULT_HEIGHT] = null;
                        spawnPowerUp(i, bombY / Terrain.DEFAULT_HEIGHT);
                    }
                    bomb.setRight(i * Terrain.DEFAULT_WIDTH);
                    break;
                }
            } else {
                bomb.setRight(width * Terrain.DEFAULT_WIDTH);
            }

        //verific in stanga
        for (int i = bombX / Terrain.DEFAULT_WIDTH; i >= (bombX - detonationDistance) / Terrain.DEFAULT_WIDTH; i--)
            if (i >= 0) {
                if (terrains[i][bombY / Terrain.DEFAULT_HEIGHT] != null) {
                    if (terrains[i][bombY / Terrain.DEFAULT_HEIGHT].GetDestroyable()) {
                        terrains[i][bombY / Terrain.DEFAULT_HEIGHT] = null;
                        spawnPowerUp(i, bombY / Terrain.DEFAULT_HEIGHT);
                    }
                    bomb.setLeft((i + 1) * Terrain.DEFAULT_WIDTH);
                    break;
                }
            } else {
                bomb.setLeft(0);
            }

        return true;
    }

    public void spawnPowerUp(int x, int y) {
        int seed = r.nextInt(100);
        if (seed <= 10)
            powerUps[x][y] = new SpeedPowerUp(refLink, x * PowerUp.DEFAULT_WIDTH, y * PowerUp.DEFAULT_HEIGHT);
        if (seed > 10 && seed <= 20)
            powerUps[x][y] = new DetonationDistancePowerUp(refLink, x * PowerUp.DEFAULT_WIDTH, y * PowerUp.DEFAULT_HEIGHT);
    }


    /*! \fn private int MiddleEastMap(int x ,int y)
        \brief O harta incarcata static.

        \param x linia pe care se afla codul dalei de interes.
        \param y coloana pe care se afla codul dalei de interes.
     */
    private int returnMap(int x, int y) {
        ///Definire statica a matricei de coduri de dale.
        return map[x][y];
    }

    public static int[][] map;

    public static final int map1[][] = {
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 2, 0, 2, 0, 0, 2},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 2, 0, 2, 0, 0, 2},
            {0, 2, 0, 2, 2, 0, 2, 1, 0, 2, 0, 0, 0, 0, 0},
            {0, 2, 0, 2, 0, 2, 0, 2, 0, 0, 2, 0, 0, 0, 2},
            {0, 2, 0, 2, 0, 0, 1, 2, 2, 1, 1, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 2, 1, 1, 0, 0, 2, 2, 1, 1, 2},
            {2, 0, 2, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {2, 1, 1, 2, 0, 2, 0, 1, 2, 1, 1, 0, 0, 2, 2},
            {0, 0, 0, 0, 1, 2, 0, 2, 2, 2, 2, 2, 1, 0, 0},
            {2, 0, 0, 2, 0, 1, 1, 2, 2, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 2, 1, 0, 0, 2, 2, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 2, 0, 0, 1, 2, 0, 0, 2, 1, 2, 0},
            {0, 0, 2, 0, 2, 0, 2, 1, 2, 0, 0, 2, 0, 0, 0},
            {0, 0, 2, 0, 0, 2, 2, 1, 2, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 2, 2, 0, 2, 2, 2, 0, 0, 0}
    };

    public static final int map2[][] = {
            {3, 4, 0, 0, 0, 3, 3, 3, 0, 0, 3, 3, 3, 0, 0},
            {3, 0, 0, 0, 0, 0, 0, 4, 0, 0, 4, 0, 0, 4, 0},
            {4, 0, 0, 0, 0, 4, 4, 4, 4, 0, 4, 0, 0, 0, 3},
            {3, 3, 0, 0, 3, 4, 3, 0, 0, 3, 0, 0, 3, 4, 0},
            {0, 4, 0, 3, 4, 0, 3, 0, 0, 3, 4, 4, 3, 3, 0},
            {0, 3, 4, 4, 4, 3, 0, 0, 4, 0, 4, 3, 4, 0, 0},
            {0, 4, 4, 4, 3, 4, 3, 4, 0, 3, 3, 3, 4, 0, 4},
            {3, 0, 4, 3, 3, 0, 4, 3, 4, 0, 3, 0, 0, 0, 4},
            {0, 0, 0, 0, 4, 4, 4, 4, 0, 0, 0, 4, 4, 3, 4},
            {0, 3, 3, 0, 0, 0, 3, 3, 0, 3, 0, 0, 0, 0, 0},
            {0, 0, 4, 4, 3, 4, 3, 0, 0, 4, 4, 0, 3, 0, 0},
            {0, 0, 0, 4, 4, 0, 4, 0, 4, 4, 0, 4, 0, 0, 0},
            {0, 0, 0, 0, 4, 4, 0, 3, 0, 3, 3, 4, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 3, 4, 4, 0, 0, 0, 0, 0, 3},
            {0, 0, 0, 0, 0, 0, 3, 3, 4, 4, 0, 0, 0, 3, 0}
    };

    public static final int map3[][] = {
            {5, 5, 5, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0},
            {6, 0, 0, 0, 6, 5, 5, 6, 0, 0, 0, 5, 0, 0, 6},
            {6, 6, 0, 0, 5, 0, 5, 5, 0, 6, 0, 5, 0, 0, 6},
            {6, 6, 6, 5, 6, 6, 5, 6, 0, 5, 6, 6, 0, 6, 6},
            {6, 0, 0, 0, 6, 0, 6, 0, 0, 5, 0, 0, 0, 0, 0},
            {5, 6, 5, 6, 6, 6, 6, 0, 6, 0, 0, 0, 0, 6, 0},
            {0, 0, 0, 5, 0, 6, 0, 0, 6, 6, 5, 6, 5, 6, 5},
            {0, 0, 0, 6, 0, 0, 0, 6, 0, 0, 5, 0, 6, 0, 5},
            {0, 5, 6, 5, 0, 0, 0, 0, 0, 6, 5, 6, 6, 6, 5},
            {0, 0, 6, 5, 5, 5, 5, 6, 5, 5, 0, 0, 6, 6, 0},
            {0, 6, 6, 0, 6, 0, 0, 6, 6, 0, 0, 0, 0, 6, 6},
            {0, 0, 0, 0, 0, 5, 0, 5, 6, 0, 5, 0, 0, 0, 6},
            {0, 0, 0, 5, 5, 6, 0, 5, 6, 0, 0, 0, 0, 0, 6},
            {0, 0, 0, 0, 0, 0, 0, 6, 6, 6, 6, 0, 0, 5, 5}
    };
}