

package JaintPlug.saturation;


import java.awt.event.*;
import JaintPlug.*;
/**
 *
 * @author jonas
 */
public class EcouteurOK implements ActionListener
{
    private Saturation sat;

    /**
     *
     * @param sat
     */
    public EcouteurOK(Saturation sat)
    {
        this.sat = sat;
    }

    public void actionPerformed(ActionEvent ae)
    {
        sat.apply();
    }

    


}