package Nuclear.Settings;

/**
 * Settings for the Player2
 */

public class Player2Settings {
    public static int up, down, right, left, bomb;

    public static void InitializeSettings() {
        SettingsLoader.LoadSettings(2);
    }
}
