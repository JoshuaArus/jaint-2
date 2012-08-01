package JaintPlug.torsion;

/**
 *
 * @author Jonas
 */

import java.awt.event.*;
import JaintPlug.*;


public class EcouteurOK implements ActionListener
{

    private Torsion to;

    /**
     *
     * @param to
     */
    public EcouteurOK(Torsion to)
    {
        this.to = to;
    }

    public void actionPerformed(ActionEvent ae)
    {
        this.to.apply();
    }

}
