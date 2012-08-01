

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
public class Vibration implements Plugin
{
    private boolean estActive = true;
    private int nbApplyPlug = 5;
    

    public String getName()
    {
            return("Vibration");
    }

    public String getDescri()
    {
            return("Applique a l'image un effet de flou");
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
     * Prend l'image courante en parametre et retourne l'image modifie ou non
     * @param img image courante
     * @return imgModif image modifie ou non
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
     * Applique l'effet a l'image en parametre et retourne l'image modifiee
     * @param img BufferedImage image courante
     * @return biModif BufferedImage image modifie
     */
    public BufferedImage applyEffectPlugin(BufferedImage img)
    {

        Kernel kernel = new Kernel(5, 5, new float[] {9f/49f, 0f, 3f/49f, 0f, 9f/49f, 0f, 0f, 0f, 0f, 0f, 3f/49f, 0f, 1f/49f, 0f, 3f/49f, 0f, 0f, 0f, 0f, 0f, 9f/49f, 0f, 3f/49f, 0f, 9f/49f});

        ConvolveOp op = new ConvolveOp(kernel);
        BufferedImage bi = op.filter(img, null);
        
        return bi;
    }

    /**
     *
     * @return
     */
    public int getNbApplyPlug()
    {
        return nbApplyPlug;
    }
}
