package JaintPlug.flouNet;

/**
 *
 * @author Jonas
 */

import java.awt.event.*;
import JaintPlug.*;


public class EcouteurAnnuler implements ActionListener
{
    private FlouNettete flNe;

    /**
     *
     * @param f
     */
    public EcouteurAnnuler(FlouNettete f)
    {
        this.flNe = f;
    }

    public void actionPerformed(ActionEvent ae)
    {
        flNe.annuler();
    }



}
