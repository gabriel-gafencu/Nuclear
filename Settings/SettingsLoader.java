package Nuclear.Settings;

import java.sql.*;
import java.util.LinkedList;

/*!
 * Class which has the purpose of communicating between the database and the game
 */

public class SettingsLoader {

    /*!
     * Loads the Settings for the Player based on the playerId passed to the function
     */
    public static void LoadSettings(int playerId) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = null;
            switch (playerId) {
                case 1:
                    rs = stmt.executeQuery("SELECT * FROM PlayerSettings WHERE PlayerNumber=1");
                    while (rs.next()) {

                        Player1Settings.up = rs.getInt("UP");
                        Player1Settings.down = rs.getInt("DOWN");
                        Player1Settings.right = rs.getInt("RIGHT");
                        Player1Settings.left = rs.getInt("LEFT");
                        Player1Settings.bomb = rs.getInt("BOMB");
                        /*System.out.println((char) Player1Settings.up + " " +
                                (char) Player1Settings.down + " " +
                                (char) Player1Settings.right + " " +
                                (char) Player1Settings.left + " " +
                                (char) Player1Settings.bomb);*/
                    }
                    break;
                case 2:
                    rs = stmt.executeQuery("SELECT * FROM PlayerSettings WHERE PlayerNumber=2");
                    while (rs.next()) {

                        Player2Settings.up = rs.getInt("UP");
                        Player2Settings.down = rs.getInt("DOWN");
                        Player2Settings.right = rs.getInt("RIGHT");
                        Player2Settings.left = rs.getInt("LEFT");
                        Player2Settings.bomb = rs.getInt("BOMB");
                        /*System.out.println((char) Player2Settings.up + " " +
                                (char) Player2Settings.down + " " +
                                (char) Player2Settings.right + " " +
                                (char) Player2Settings.left + " " +
                                (char) Player2Settings.bomb);*/
                    }
                    break;
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /*!
     * Changes a certain setting in the PlayerSetting table
     */
    public static void UpdateSetting(int playerId, int action, int key) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String s = "UPDATE PlayerSettings set ";
            switch (action) {
                case 1:
                    s += "UP";
                    break;
                case 2:
                    s += "DOWN";
                    break;
                case 3:
                    s += "RIGHT";
                    break;
                case 4:
                    s += "LEFT";
                    break;
                case 5:
                    s += "BOMB";
                    break;
            }
            s += "=" + key + " WHERE PlayerNumber=" + playerId + ";";
            //System.out.println(s);
            stmt.executeUpdate(s);
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /*!
     * Inserts a new time in the Highscore table
     */
    public static void InsertHighscore(long time) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String s = "INSERT INTO Highscore (Time) " +
                    "VALUES (" + time + ");";
            stmt.executeUpdate(s);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /*!
     * Gets the first 5 best times in the Highscore table and returns them
     */
    public static LinkedList<Integer> GetHighscoreList() {
        Connection c = null;
        Statement stmt = null;
        LinkedList<Integer> list = new LinkedList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Highscore ORDER BY Time ASC;");
            int i = 0;
            while (rs.next() && i != 5) {
                Integer time = rs.getInt("Time");
                list.addLast(time);
                i++;
            }
            stmt.close();
            c.commit();
            c.close();
            return list;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

}
