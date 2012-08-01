package JaintPlug.teinte;

/**
 *
 * @author Jonas
 */


import java.awt.event.*;
import JaintPlug.*;

public class WEcouteurFerme extends WindowAdapter
{
     
  
    private Teinte te;

    /**
     *
     * @param t
     */
    public WEcouteurFerme(Teinte t)
    {
            this.te = t;
    }

@Override
    public void windowClosing(WindowEvent e)
    {
            this.te.annuler();
    }
}
