package JaintPlug.miroire;

/**
 *
 * @author Jonas
 */

import java.awt.event.*;
import JaintPlug.*;


public class EcouteurAnnuler implements ActionListener
{
    private Miroire mi;

    /**
     *
     * @param mi
     */
    public EcouteurAnnuler(Miroire mi)
    {
        this.mi = mi;
    }

    public void actionPerformed(ActionEvent ae)
    {
        mi.annuler();
    }



}
