/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JaintListener;

import Jaint.*;
import java.awt.event.*;
/**
 *
 * @author jonas
 */
public class BtnPluginListener implements ActionListener
{
    /**
     * Index du plugin dans la liste
     */
    private int plug;
    /**
     * IHM
     */
    private IHM ihm;

    /**
     * Construit l'ecouteur
     * @param p Plugin selectionne
     * @param i IHM courante
     */
    public BtnPluginListener(int p, IHM i)
    {
        this.plug = p;
        this.ihm = i;
    }

     public void actionPerformed(ActionEvent ae)
    {
        this.ihm.modify(plug);
    }
}
