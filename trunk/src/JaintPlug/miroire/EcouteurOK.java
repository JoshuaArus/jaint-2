

package JaintPlug.miroire;


import java.awt.event.*;
import JaintPlug.*;
/**
 *
 * @author jonas
 */
public class EcouteurOK implements ActionListener
{
    private Miroire mi;

    /**
     *
     * @param mi
     */
    public EcouteurOK(Miroire mi)
    {
        this.mi = mi;
    }

    public void actionPerformed(ActionEvent ae)
    {
        mi.apply();
    }

    


}