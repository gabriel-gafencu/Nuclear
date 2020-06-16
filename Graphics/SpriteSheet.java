package Nuclear.Graphics;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/*! \class public class SpriteSheet
    \brief Clasa retine o referinta catre o imagine formata din dale (sprite sheet)

    Metoda crop() returneaza o dala de dimensiuni fixe (o subimagine) din sprite sheet
    de la adresa (x * latimeDala, y * inaltimeDala)
 */
public class SpriteSheet {
    private BufferedImage spriteSheet;        /*!< Referinta catre obiectul BufferedImage ce contine sprite sheet-ul.*/
    private static int tileWidth = 48;   /*!< Latimea unei dale din sprite sheet.*/
    private static int tileHeight = 48;   /*!< Inaltime unei dale din sprite sheet.*/

    /*! \fn public SpriteSheet(BufferedImage sheet)
        \brief Constructor, initializeaza spriteSheet.

        \param buffImg Un obiect BufferedImage valid.
     */
    public SpriteSheet(BufferedImage buffImg) {
        /// Retine referinta catre BufferedImage object.
        spriteSheet = buffImg;
    }

    public SpriteSheet(BufferedImage buffImg, int width, int height) {
        spriteSheet = buffImg;
        tileWidth = width;
        tileHeight = height;
    }

    /*! \fn public BufferedImage crop(int x, int y)
        \brief Returneaza un obiect BufferedImage ce contine o subimage (dala).

        Subimaginea este localizata avand ca referinta punctul din stanga sus.

        \param x numarul dalei din sprite sheet pe axa x.
        \param y numarul dalei din sprite sheet pe axa y.
     */

    public BufferedImage crop(int x, int y, int width, int height) {
        return spriteSheet.getSubimage(x, y, width, height);
    }

    public BufferedImage cropMirrorVertical(int x, int y, int width, int height) {
        BufferedImage image = spriteSheet.getSubimage(x, y, width, height);
        BufferedImage mirrored = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) mirrored.getGraphics();
        AffineTransform transform = new AffineTransform();
        transform.setToScale(-1, 1);
        transform.translate(-image.getWidth(), 0);
        graphics.setTransform(transform);
        graphics.drawImage(image, 0, 0, null);
        return mirrored;
    }

    public BufferedImage cropMirrorHorizontal(int x, int y, int width, int height) {
        BufferedImage image = spriteSheet.getSubimage(x, y, width, height);
        BufferedImage mirrored = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) mirrored.getGraphics();
        AffineTransform transform = new AffineTransform();
        transform.setToScale(1, -1);
        transform.translate(0, -image.getHeight());
        graphics.setTransform(transform);
        graphics.drawImage(image, 0, 0, null);
        return mirrored;
    }

}
