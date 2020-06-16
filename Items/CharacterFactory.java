package Nuclear.Items;

import Nuclear.RefLinks;

/*!
 * The Character Factory handles the creation of Character objects: Hero and Villain
 */

public class CharacterFactory {
    public Character GetCharacter(String chacterType, RefLinks refLinks, float x, float y, int playerId) {
        switch (chacterType) {
            case "Hero":
                return new Hero(refLinks, x, y, playerId);
            case "Villain":
                return new Villain(refLinks, x, y);
            default:
                return null;
        }
    }
}
