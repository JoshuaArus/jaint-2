/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JaintListener;

import Jaint.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe mettant a jour le centre de la palette lorsque la selection de couleur est selectionnee
 * @author Jeff
 */
public class EcouteurSelectionCouleur implements ActionListener
{
    private JaintPalette palette;

    /**
     *
     * @param p
     */
    public EcouteurSelectionCouleur(JaintPalette p)
    {
        palette = p;
    }

    public void actionPerformed(ActionEvent e)
    {
        palette.setCenter(JaintPalette.CHOIX_COULEUR);
        palette.repaint();
    }

}
