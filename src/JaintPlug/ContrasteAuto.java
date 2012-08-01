package JaintPlug;

import Jaint.Plugin;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Joshua
 */
public class ContrasteAuto implements Plugin
{
    private boolean estActive = true;
    private int nbApplyPlug = 1;

    public String getName()
    {
        return "Contraste Automatique";
    }

    public String getDescri()
    {
        return "Amelioration automatique du contraste pour les images trop sombres ou trop claires";
    }

    public boolean isEnabled()
    {
        return estActive;
    }

    public void setEnabled(boolean b)
    {
        estActive = b;
    }

    public BufferedImage applyEffectIcon(BufferedImage img)
    {
        BufferedImage icon = applyEffectPlugin(img);
        return icon;
    }

    /**
     *
     * @param img
     * @return
     */
    public BufferedImage applyEffectPlugin(BufferedImage img)
    {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        int max = img.getWidth() * img.getHeight();

        Gris gr = new Gris();
        BufferedImage gris = gr.applyEffectPlugin(img);

        double[] tab_gris = new double[256];
        double[] tab_pct = new double[256];

        for (int i = 0; i < 256; i++)
        {
            tab_gris[i] = 0;
            tab_pct[i] = 0;
        }

        for (int i = 0 ; i < img.getWidth(); i++)
        {
            for (int j = 0; j < img.getHeight(); j++)
            {
                tab_gris[(new Color(gris.getRGB(i, j))).getRed()]++;
            }
        }

        for (int i = 1; i< 256; i++)
        {
            tab_gris[i] = tab_gris[i] + tab_gris[i-1];
        }

        for (int i = 0; i < 256; i++)
        {
            tab_pct[i] = tab_gris[i] / max * 255;
        }

        for (int i = 0; i < img.getWidth(); i++)
        {
            for (int j = 0; j < img.getHeight(); j++)
            {
                int rgb = img.getRGB(i,j);

                if (((rgb >>24 ) & 0xFF) != 0)
                {
                    int temp = new Color(gris.getRGB(i, j)).getRed();
                    double coef = tab_pct[temp]/temp;

                    Color couleur = new Color(img.getRGB(i, j));

                    int r = (int) (couleur.getRed() * coef);
                    if (r < 0)
                        r = 0;
                    if (r > 255)
                        r = 255;

                    int g = (int) (couleur.getGreen() * coef);
                    if (g < 0)
                        g = 0;
                    if (g > 255)
                        g = 255;

                    int b = (int) (couleur.getBlue() * coef);
                    if (b < 0)
                        b = 0;
                    if (b > 255)
                        b = 255;

                    Color c = new Color(r,g,b);
                    res.setRGB(i, j, c.getRGB());
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
     *
     * @param img
     * @return
     */
    public BufferedImage modify(BufferedImage img)
    {
        BufferedImage bi;

        if(img.getType() != BufferedImage.TYPE_INT_ARGB)
        {
            BufferedImage src = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = src.createGraphics();
            g.drawRenderedImage(img, null);
            g.dispose();
            img = src;
        }

        bi = applyEffectPlugin(img);

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
