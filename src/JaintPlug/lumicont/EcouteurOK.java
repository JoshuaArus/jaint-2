
package JaintPlug.lumicont;



import java.awt.event.*;
import JaintPlug.*;
/**
 *
 * @author jonas
 */
public class EcouteurOK implements ActionListener
{
    private LumiCont lc;

    /**
     *
     * @param lc
     */
    public EcouteurOK(LumiCont lc)
    {
        this.lc = lc;
    }

    public void actionPerformed(ActionEvent ae)
    {
        lc.apply();
    }

    


}