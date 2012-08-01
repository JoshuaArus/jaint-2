/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JaintListener;

import Jaint.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JSpinner;

/**
 * Classe mettant un jour la couleur selectionnee dans le selecteur de couleur
 * @author Jeff
 */
public class EcouteurChangementCouleur extends MouseAdapter
{
    private JaintPalette palette;
    private BufferedImage degrade;

    /**
     *
     * @param p
     */
    public EcouteurChangementCouleur(JaintPalette p)
    {
        super();
        palette = p;
        degrade = palette.getDegrade();
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
       if(e.getButton() == MouseEvent.BUTTON1)
       {
           int centre = palette.getCenter();
           if(centre == JaintPalette.CHOIX_COULEUR || centre == JaintPalette.CHOIX_PIPETTE || centre == JaintPalette.CHOIX_POT_PEINTURE)
           {   int x = e.getX();

                palette.setMouse(x, e.getY());

                if(palette.onDegrade())
                {
                    int lgDebutToCentre = (palette.getPreferredSize().width / 2 - palette.getPetitRayon());

                    Color color = new Color(degrade.getRGB(x - lgDebutToCentre, 0));

                    JBouton[] btn = palette.getBoutonColor();

                    JBouton.setColorIcon(btn, color);

                    if(btn[0].isChecked())
                    {
                        palette.setFirstColor(color);
                    }
                    else
                    {
                        palette.setSecondColor(color);
                    }

                    JSpinner[] rvb = palette.getSpinner();

                    rvb[0].setValue(color.getRed());
                    rvb[1].setValue(color.getGreen());
                    rvb[2].setValue(color.getBlue());


                    palette.repaint();
                }

           }
       }
       
    }

    

}
