

package JaintPlug.miroire;


import java.awt.event.*;
import JaintPlug.*;
/**
 *
 * @author jonas
 */
public class EcouteurMiroireV implements ActionListener
{
    private Miroire mi;

    /**
     *
     * @param mi
     */
    public EcouteurMiroireV(Miroire mi)
    {
        this.mi = mi;
    }

    public void actionPerformed(ActionEvent ae)
    {
        mi.previsualiserMiroireV();
    }

 

}
