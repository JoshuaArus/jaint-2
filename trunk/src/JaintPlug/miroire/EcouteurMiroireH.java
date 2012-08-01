

package JaintPlug.miroire;


import java.awt.event.*;
import JaintPlug.*;
/**
 *
 * @author jonas
 */
public class EcouteurMiroireH implements ActionListener
{
    private Miroire mi;

    /**
     *
     * @param mi
     */
    public EcouteurMiroireH(Miroire mi)
    {
        this.mi = mi;
    }

    public void actionPerformed(ActionEvent ae)
    {
        mi.previsualiserMiroireH();
    }
}



