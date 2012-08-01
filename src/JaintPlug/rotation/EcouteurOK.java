

package JaintPlug.rotation;


import java.awt.event.*;
import JaintPlug.*;
/**
 *
 * @author jonas
 */
public class EcouteurOK implements ActionListener
{
    private Rotation ro;

    /**
     *
     * @param r
     */
    public EcouteurOK(Rotation r)
    {
        this.ro = r;
    }

    public void actionPerformed(ActionEvent ae)
    {
        ro.apply();
    }




}