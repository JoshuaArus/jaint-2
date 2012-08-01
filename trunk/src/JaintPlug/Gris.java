

package JaintPlug;


import java.awt.Color;
import Jaint.Plugin;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
 *
 * @author Joshua
 */
public class Gris implements Plugin
{
    private boolean estActive = true;
    private int nbApplyPlug = 1;

    public String getName()
    {
            return("Niveau de gris");
    }

    public String getDescri()
    {
            return("L'image est grisée");
    }

    public boolean isEnabled()
    {
        return estActive;
    }

    public void setEnabled(boolean b)
    {
        estActive = b;
    }

    /**
     *
     * @param img
     * @return
     */
    public BufferedImage modify(BufferedImage img)
    {

        BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        if(img.getType() == BufferedImage.TYPE_3BYTE_BGR)
        {
            BufferedImage src = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = src.createGraphics();
            g.drawRenderedImage(img, null);
            g.dispose();
            img = src;
        }

        bi = applyEffectPlugin(img);

        return bi;

    }

    public BufferedImage applyEffectIcon(BufferedImage img)
    {
        BufferedImage icon = applyEffectPlugin(img);
        return icon;
    }
    

    /**
     * Applique l'effet à l'image en parametre et retourne l'image modifiée
     * @param img BufferedImage image courante
     * @return biModif BufferedImage image modifié
     */
    public BufferedImage applyEffectPlugin(BufferedImage img)
    {

        BufferedImage biRes = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < img.getWidth(); i++)
        {
            for (int j = 0; j < img.getHeight(); j++)
            {
                int rgb = img.getRGB(i,j);

                if (((rgb >>24 ) & 0xFF) != 0)
                {
                    Color temp = new Color(img.getRGB(i, j));

                    int r = temp.getRed();
                    int g = temp.getGreen();
                    int b = temp.getBlue();

                    int luminance = (int) (0.299 * r + 0.587 * g + 0.114 * b);

                    biRes.setRGB(i, j, (new Color(luminance,luminance,luminance)).getRGB());
                }
                else
                {
                    biRes.setRGB(i,j,rgb);
                }

            }
        }

        return biRes;
    }

    /**
     * Affiche l'icone validé sur le bouton valider si l'utilisateur se trouve dessu
     * @param entre boolean
     */
    public int getNbApplyPlug()
    {
        return nbApplyPlug;
    }


}
