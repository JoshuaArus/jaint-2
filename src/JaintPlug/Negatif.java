package JaintPlug;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import Jaint.Plugin;

/**
 *
 * @author Jonas
 */
public class Negatif implements Plugin
{
    private boolean estActive = true;
    private int nbApplyPlug = 1;
    

    public String getName()
    {
            return("Négatif");
    }

    public String getDescri()
    {
            return("Inversion des couleurs de l'image");
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
     * Prend l'image courante en parametre et retourne l'image modifié ou non
     * @param img image courante
     * @return imgModif image modifié ou non
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

       BufferedImage res = new BufferedImage(img.getWidth(),img.getHeight(),img.getType());
        int red = 0;
        int green = 0;
        int blue = 0;
        
        for(int i=0;i<img.getWidth();i++)// On parcour chaque ligne de l'image
        {
                for(int j=0;j<img.getHeight();j++)// On parcour chaque colonne de l'image
                {
                        int rgb = img.getRGB(i,j);
                        
                        if (((rgb >>24 ) & 0xFF) != 0)
                        {
                            Color c = new Color(rgb);
                            red=c.getRed();
                            green=c.getGreen();
                            blue=c.getBlue();
                            Color inverse = new Color(255-red,255-green,255-blue);// obtiens l'inverse de la couleur
                            res.setRGB(i, j, inverse.getRGB());
                        }
                        else
                        {
                            res.setRGB(i,j,rgb);
                        }
                }
        }

        return res;
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
