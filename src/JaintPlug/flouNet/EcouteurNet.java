

package JaintPlug.flouNet;


import java.awt.event.*;
import JaintPlug.*;
/**
 *
 * @author jonas
 */
public class EcouteurNet implements ActionListener
{
    private FlouNettete flNe;

    /**
     *
     * @param f
     */
    public EcouteurNet(FlouNettete f)
    {
        this.flNe = f;
    }

    public void actionPerformed(ActionEvent ae)
    {
        flNe.previsualiserNet();
    }
}



