package JaintPlug.saturation;

/**
 *
 * @author Jonas
 */

import java.awt.event.*;
import JaintPlug.*;


public class EcouteurAnnuler implements ActionListener
{
    private Saturation sat;

    /**
     *
     * @param sat
     */
    public EcouteurAnnuler(Saturation sat)
    {
       this.sat = sat;
    }

    public void actionPerformed(ActionEvent ae)
    {
        sat.annuler();
    }



}
