
package JaintPlug;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import Jaint.Plugin;
import java.awt.Graphics2D;


/**
 *
 * @author Jonas
 */
public class Double implements Plugin
{
    private boolean estActive = true;
    private int nbApplyPlug = 1;
   
    public String getName()
    {
            return("Double");
    }

    public String getDescri()
    {
            return("Les traits de l'image sont doublés");
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
        if(img.getType() == BufferedImage.TYPE_3BYTE_BGR)
        {
                BufferedImage src = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g = src.createGraphics();
                g.drawRenderedImage(img, null);
                g.dispose();
                img = src;
        }

        BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

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
        Kernel kernel = new Kernel(5, 5, new float[] {-1f/-7f, -2f/-7f, -1f/-7f, -2f/-7f, -1f/-7f, 2f/-7f, -1f/-7f, 2f/-7f, -1f/-7f, 2f/-7f, -1f/-7f, -2f/-7f, 5f/-7f, -2f/-7f, -1f/-7f, 2f/-7f, -1f/-7f, 2f/-7f, -1f/-7f, 2f/-7f, -1f/-7f, -2f/-7f, -1f/-7f, -2f/-7f, -1f/-7f});

        ConvolveOp op = new ConvolveOp(kernel);
        BufferedImage bi = op.filter(img, null);
        return bi;
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
