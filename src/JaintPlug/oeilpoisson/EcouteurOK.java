package JaintPlug.oeilpoisson;

/**
 *
 * @author Jonas
 */

import java.awt.event.*;
import JaintPlug.*;


public class EcouteurOK implements ActionListener
{

    private OeilDePoisson odp;

    /**
     *
     * @param odp
     */
    public EcouteurOK(OeilDePoisson odp)
    {
        this.odp = odp;
    }

    public void actionPerformed(ActionEvent ae)
    {
        this.odp.apply();
    }

}
