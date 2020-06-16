package Nuclear.Settings;


/*!
 * Settings for the Player1
 */

public class Player1Settings {
    public static int up, down, right, left, bomb;

    public static void InitializeSettings() {
        SettingsLoader.LoadSettings(1);
    }
}
