package JaintPlug.lumicont;

/**
 *
 * @author Jonas
 */

import java.awt.event.*;
import JaintPlug.*;

public class EcouteurFermer implements ActionListener
{
    private LumiCont lc;

    /**
     *
     * @param lc
     */
    public EcouteurFermer(LumiCont lc)
    {
        this.lc = lc;
    }

    public void actionPerformed(ActionEvent ae)
    {
        lc.annuler();
    }
}
