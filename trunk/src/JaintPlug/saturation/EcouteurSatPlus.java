

package JaintPlug.saturation;


import java.awt.event.*;
import JaintPlug.*;
/**
 *
 * @author jonas
 */
public class EcouteurSatPlus implements ActionListener
{
    private Saturation sat;

    /**
     *
     * @param sat
     */
    public EcouteurSatPlus(Saturation sat)
    {
        this.sat = sat;
    }

    public void actionPerformed(ActionEvent ae)
    {
        sat.previsualiserSatPlus();
    }

    


}
