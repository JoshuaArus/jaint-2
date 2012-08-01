package JaintPlug.grain;

/**
 *
 * @author Jonas
 */


import java.awt.event.*;
import JaintPlug.*;

public class WEcouteurFerme extends WindowAdapter
{
    private Grain g;

    /**
     *
     * @param g
     */
    public WEcouteurFerme(Grain g)
    {
            this.g = g;
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
            this.g.annuler();
    }
}
