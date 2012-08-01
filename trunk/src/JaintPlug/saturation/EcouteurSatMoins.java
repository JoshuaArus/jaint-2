

package JaintPlug.saturation;


import java.awt.event.*;
import JaintPlug.*;
/**
 *
 * @author jonas
 */
public class EcouteurSatMoins implements ActionListener
{
    private Saturation sat;

    /**
     *
     * @param sat
     */
    public EcouteurSatMoins(Saturation sat)
    {
        this.sat = sat;
    }

    public void actionPerformed(ActionEvent ae)
    {
        sat.previsualiserSatMoins();
    }

  
}
