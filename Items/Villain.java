package Nuclear.Items;

import Nuclear.Graphics.Assets;
import Nuclear.Items.Terrain.Terrain;
import Nuclear.RefLinks;
import Nuclear.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/*!
 * The Villain class is the computer version of a Character
 * Its AI is simple as it chases either the Player or other Computer Characters and trying to kill them.
 * It also runs away from its own Bombs.
 */


public class Villain extends Character {
    private BufferedImage image;    /*!< Referinta catre imaginea curenta a eroului.*/
    private BufferedImage imageIdle = Assets.player2Idle;
    private BufferedImage imageUp1 = Assets.player2Up1;
    private BufferedImage imageUp2 = Assets.player2Up2;
    private BufferedImage imageDown1 = Assets.player2Down1;
    private BufferedImage imageDown2 = Assets.player2Down2;
    private BufferedImage imageRight1 = Assets.player2Right1;
    private BufferedImage imageRight2 = Assets.player2Right2;
    private BufferedImage imageLeft1 = Assets.player2Left1;
    private BufferedImage imageLeft2 = Assets.player2Left2;
    private BufferedImage imageDead = Assets.player2Dead;

    private boolean triedUp, triedDown, triedRight, triedLeft;
    private boolean up, down, right, left;
    private boolean alreadyChecked;
    private int upTimer, downTimer, rightTimer, leftTimer;
    private int pathTimer = 120;
    private LinkedList<Node> path = new LinkedList<>();         /// the path from the Villain to its Enemy
    LinkedList<LinkedList> paths = new LinkedList<>();

    public Villain(RefLinks refLink, float x, float y) {
        super(refLink, x, y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT);
        ///Seteaza imaginea de start a eroului
        image = Assets.player2Idle;
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
    }

    /*!
     * The Update function switches between the two states of the Villain
     * If a bomb was planted, it runs away from it, otherwise it chases its nearest enemy
     */
    @Override
    public void Update() {
        //System.out.println(new Node((int) this.x / Tile.TILE_WIDTH, (int) this.y / Tile.TILE_HEIGHT));
        //System.out.println(new Node((int) refLink.GetGame().GetPlayState().GetHero().GetX() / Tile.TILE_WIDTH, (int) refLink.GetGame().GetPlayState().GetHero().GetY() / Tile.TILE_HEIGHT));
        if (!isDead) {
            if (bomb == null) {
                //Node powerUpPath = checkForPowerUps();
                /*if (powerUpPath != null) {

                } else*/
                {
                    FindEnemy();
                    alreadyChecked = false;
                }
                //System.out.println("Trying to move" + path.toString());
            } else {
                runAway();
                alreadyChecked = false;
            }
            Move();
            PickUpPowerUp();
            if (bomb != null) {
                bomb.Update();
                if (!bomb.GetState())
                    bomb = null;
            }
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

    /*!
     * Draws the Character as well as its bomb if it was planted
     */
    @Override
    public void Draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, width, height, null);
        if (bomb != null) {
            bomb.Draw(g);
        }
    }

    /*!
     * It runs away from the planted bomb in a predicted pattern checking for safety distance as well as
     * if it has run into terrain blocking its way
     */
    private void runAway() {
        boolean canUp = (this.y + bounds.y - speed) > 0 && refLink.GetMap().GetTerrain((int) x + bounds.x, (int) y + bounds.y - speed) == null && refLink.GetMap().GetTerrain((int) x + bounds.x + bounds.width, (int) y + bounds.y - speed) == null;
        boolean canDown = (this.y + bounds.y + bounds.height + speed) < refLink.GetGame().GetHeight() && refLink.GetMap().GetTerrain((int) x + bounds.x, (int) y + bounds.y + bounds.height + speed) == null && refLink.GetMap().GetTerrain((int) x + bounds.x + bounds.width, (int) y + bounds.y + bounds.height + speed) == null;
        boolean canLeft = (this.x + bounds.x - speed) > 0 && refLink.GetMap().GetTerrain((int) this.x + bounds.x - speed, (int) this.y + bounds.y) == null && refLink.GetMap().GetTerrain((int) this.x + bounds.x - speed, (int) this.y + bounds.y + bounds.height) == null;
        boolean canRight = (this.x + bounds.x + bounds.width + speed) < refLink.GetGame().GetWidth() && refLink.GetMap().GetTerrain((int) x + bounds.x + bounds.width + speed, (int) y + bounds.y) == null && refLink.GetMap().GetTerrain((int) x + bounds.x + bounds.width + speed, (int) y + bounds.y + bounds.height) == null;
        xMove = 0;
        yMove = 0;

        if (checkForBomb()) {
            if (!triedRight) {
                if (canRight)
                    moveRight();
                else
                    triedRight = true;
            } else if (!triedUp) {
                if (canUp)
                    moveUp();
                else
                    triedUp = true;
            } else if (!triedLeft) {
                if (canLeft)
                    moveLeft();
                else
                    triedLeft = true;

            } else if (!triedDown) {
                if (canDown)
                    moveDown();
                else
                    triedDown = true;
            }
        } else
            FindPath();
    }

    /*!
     * Checks for every possible path to an Enemy and then chooses the shortest one
     */
    private void FindPath() {

        for (Character enemy : this.enemy) {
            paths.add(search(
                    refLink.GetMap().map,
                    new Node((int) (this.x + bounds.x) / Tile.TILE_WIDTH, (int) (this.y + bounds.y) / Tile.TILE_HEIGHT),
                    new Node((int) (enemy.GetX()) / Tile.TILE_WIDTH, (int) (enemy.GetY()) / Tile.TILE_HEIGHT)));
            //System.out.println(paths);
        }
        int size = 100;
        LinkedList<Node> actualPath = new LinkedList<>();
        for (LinkedList p : paths) {
            try {
                if (p.size() < size & !p.isEmpty())
                    actualPath = p;
            } catch (NullPointerException ex) {
            }
        }
        path = (LinkedList) actualPath.clone();
    }

    /*!
     *  After deciding on the path, it moves the Computer on the map according to the path found
     *  The path updates every 2 seconds
     *  If there are no moves left on the path to be made, the Computer plants a bomb
     */
    private void FindEnemy() {
        if (pathTimer == 120) {
            FindPath();
            //System.out.println(path);
            //System.out.println(path);
            //System.out.println((int) this.x / Tile.TILE_WIDTH + " " + (int) this.y / Tile.TILE_HEIGHT);
            pathTimer--;
        } else {
            pathTimer--;
            if (pathTimer == 0)
                pathTimer = 120;
        }
        xMove = 0;
        yMove = 0;
        boolean canUp = (this.y + bounds.y - speed) > 0 && refLink.GetMap().GetTerrain((int) x + bounds.x, (int) y + bounds.y - speed) == null && refLink.GetMap().GetTerrain((int) x + bounds.x + bounds.width, (int) y + bounds.y - speed) == null;
        boolean canDown = (this.y + bounds.y + bounds.height + speed) < refLink.GetGame().GetHeight() && refLink.GetMap().GetTerrain((int) x + bounds.x, (int) y + bounds.y + bounds.height + speed) == null && refLink.GetMap().GetTerrain((int) x + bounds.x + bounds.width, (int) y + bounds.y + bounds.height + speed) == null;
        boolean canLeft = (this.x + bounds.x - speed) > 0 && refLink.GetMap().GetTerrain((int) this.x + bounds.x - speed, (int) this.y + bounds.y) == null && refLink.GetMap().GetTerrain((int) this.x + bounds.x - speed, (int) this.y + bounds.y + bounds.height) == null;
        boolean canRight = (this.x + bounds.x + bounds.width + speed) < refLink.GetGame().GetWidth() && refLink.GetMap().GetTerrain((int) x + bounds.x + bounds.width + speed, (int) y + bounds.y) == null && refLink.GetMap().GetTerrain((int) x + bounds.x + bounds.width + speed, (int) y + bounds.y + bounds.height) == null;
        Node next;

        if (path != null) {
            if (!path.isEmpty()) {
                next = path.getFirst();
                if (next != null) {
                    int nextX = next.getX() * Tile.TILE_WIDTH;
                    int nextY = next.getY() * Tile.TILE_HEIGHT;
                    boolean movedX = true;
                    boolean movedY = true;
                    if (nextX != this.x) {
                        if (nextX < this.x)
                            if (canLeft)
                                moveLeft();
                            else
                                placeBomb();
                        if (nextX > this.x)
                            if (canRight)
                                moveRight();
                            else
                                placeBomb();
                    } else
                        movedX = false;
                    if (nextY != this.y && movedX == false) {
                        if (nextY < this.y)
                            if (canUp)
                                moveUp();
                            else
                                placeBomb();
                        if (nextY > this.y)
                            if (canDown)
                                moveDown();
                            else
                                placeBomb();
                    } else
                        movedY = false;

                    if (movedX == false && movedY == false) {
                        path.removeFirst();
                    }
                }
            } else placeBomb();
        } else placeBomb();
    }


    /*!
     * the checkForBomb function returns a boolean telling the Computer whether it is in the blast zone
     * of its own bomb
     */
    private boolean checkForBomb() {
        double detonationDistance = 1.75 * bomb.GetDetonationDistance();
        float myX = x + bounds.x;
        float myY = y + bounds.y;

        float bombX = bomb.GetX();
        float bombY = bomb.GetY();

        return (bombX - detonationDistance <= myX + bounds.width && myX <= bombX + bomb.GetBoundWidth() + detonationDistance && bombY <= myY + bounds.height && myY <= bombY + bomb.GetBoundHeight())
                || (bombY - detonationDistance <= myY + bounds.height && myY <= bombY + bomb.GetBoundHeight() + detonationDistance && bombX <= myX + bounds.width && myX <= bombX + bomb.GetBoundWidth());
    }

    private Node checkForPowerUps() {
        int myX = (int) (this.x + bounds.x) / Terrain.DEFAULT_WIDTH;
        int myY = (int) (this.y + bounds.y) / Terrain.DEFAULT_HEIGHT;

        for (int i = myX - 3; i <= myX + 3; i++) {
            for (int j = myY - 3; j <= myY + 3; j++) {
                if (refLink.GetMap().GetPowerUp(i * Terrain.DEFAULT_WIDTH, j * Terrain.DEFAULT_HEIGHT) != null)
                    return new Node(i, j);
            }
        }
        return null;
    }


    private void placeBomb() {
        if (bomb == null) {
            bomb = new Bomb(refLink, x + bounds.x + 2, y + bounds.y, Bomb.BOMB_WIDTH, Bomb.BOMB_HEIGHT, "Red");
            bomb.SetDetionationDistance(myDetonationDistance);
            bomb.SetCharacter(this);
            triedUp = false;
            triedDown = false;
            triedRight = false;
            triedLeft = false;
        }
    }


    private void moveUp() {
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

    private void moveDown() {
        yMove = +speed;
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

    private void moveRight() {
        xMove = +speed;
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

    private void moveLeft() {
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

    private class Node {
        private int x, y;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + this.y + ", " + this.x + ") ";
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

    }


    private Node[][] solve(int[][] matrix, Node start) {
        Queue<Node> q = new LinkedList<Node>();
        q.add(start);

        boolean[][] visited = new boolean[matrix.length][matrix.length];
        for (boolean[] row : visited)
            Arrays.fill(row, false);

        visited[start.getY()][start.getX()] = true;
        Node[][] prev = new Node[matrix.length][matrix.length];

        Node up = null, down = null, right = null, left = null;
        while (!q.isEmpty()) {
            Node node = q.remove();
            int x = node.getX();
            int y = node.getY();
            if (y - 1 >= 0 &&
                    (matrix[y - 1][x] == 0 || refLink.GetMap().GetTerrain((x) * Terrain.DEFAULT_WIDTH, (y - 1) * Terrain.DEFAULT_WIDTH) == null || refLink.GetMap().GetTerrain((x) * Terrain.DEFAULT_WIDTH, (y - 1) * Terrain.DEFAULT_WIDTH).GetDestroyable()))
                up = new Node(x, y - 1);
            if (y + 1 < matrix.length &&
                    (matrix[y + 1][x] == 0 || refLink.GetMap().GetTerrain((x) * Terrain.DEFAULT_WIDTH, (y + 1) * Terrain.DEFAULT_WIDTH) == null || refLink.GetMap().GetTerrain((x) * Terrain.DEFAULT_WIDTH, (y + 1) * Terrain.DEFAULT_WIDTH).GetDestroyable()))
                down = new Node(x, y + 1);
            if (x - 1 >= 0 &&
                    (matrix[y][x - 1] == 0 || refLink.GetMap().GetTerrain((x - 1) * Terrain.DEFAULT_WIDTH, (y) * Terrain.DEFAULT_WIDTH) == null || refLink.GetMap().GetTerrain((x - 1) * Terrain.DEFAULT_WIDTH, (y) * Terrain.DEFAULT_WIDTH).GetDestroyable()))
                left = new Node(x - 1, y);
            if (x + 1 < matrix.length &&
                    (matrix[y][x + 1] == 0 || refLink.GetMap().GetTerrain((x + 1) * Terrain.DEFAULT_WIDTH, (y) * Terrain.DEFAULT_WIDTH) == null || refLink.GetMap().GetTerrain((x + 1) * Terrain.DEFAULT_WIDTH, (y) * Terrain.DEFAULT_WIDTH).GetDestroyable()))
                right = new Node(x + 1, y);
            if (up != null) {
                if (!visited[up.getY()][up.getX()]) {
                    q.add(up);
                    visited[up.getY()][up.getX()] = true;
                    prev[up.getY()][up.getX()] = node;
                }
            }
            if (down != null) {
                if (!visited[down.getY()][down.getX()]) {
                    q.add(down);
                    visited[down.getY()][down.getX()] = true;
                    prev[down.getY()][down.getX()] = node;
                }
            }
            if (right != null) {
                if (!visited[right.getY()][right.getX()]) {
                    q.add(right);
                    visited[right.getY()][right.getX()] = true;
                    prev[right.getY()][right.getX()] = node;
                }
            }
            if (left != null) {
                if (!visited[left.getY()][left.getX()]) {
                    q.add(left);
                    visited[left.getY()][left.getX()] = true;
                    prev[left.getY()][left.getX()] = node;
                }
            }
        }
        return prev;
    }

    /*!
     *  The way the path is found is through a simple breadth-first search in the map from the Computer to the Enemy
     *  The algorithm checks for both Nodes that don't have a Terrain and Nodes that are occupied by Terrain which
     *  is destroyable
     */
    private LinkedList<Node> search(int[][] matrix, Node start, Node end) {
        Node[][] prev = solve(matrix, start);
        /*for (int i = 0; i < prev.length; i++) {
            for (int j = 0; j < prev[0].length; j++) {
                if (prev[i][j] != null)
                    System.out.print(prev[i][j]);
                else
                    System.out.print("(/, /) ");
            }
            System.out.println("");
        }*/
        return reconstructPath(start, end, prev);
    }

    private LinkedList<Node> reconstructPath(Node start, Node end, Node[][] prev) {
        LinkedList<Node> path = new LinkedList<Node>();
        path.add(end);
        Node node;
        do {
            node = path.getLast();
            if (node == null)
                return null;
            /*if (node.getY() < 0 || node.getY() >= refLink.GetGame().GetHeight() || node.getX() < 0 || node.getX() >= refLink.GetGame().GetWidth())
                return null;*/
            try {
                if (prev[node.getY()][node.getX()] != null)
                    path.addLast(prev[node.getY()][node.getX()]);
                else
                    //System.out.println("returning null");
                    return null;
            } catch (ArrayIndexOutOfBoundsException exception) {
                return null;
            }
        } while (node.getX() != start.getX() && node.getY() != start.getY());
        LinkedList<Node> reversePath = new LinkedList<Node>();
        while (!path.isEmpty()) {
            reversePath.add(path.removeLast());
        }
        return reversePath;
    }
}
