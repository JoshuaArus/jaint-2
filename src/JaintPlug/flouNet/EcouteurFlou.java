
package JaintPlug.flouNet;


import java.awt.event.*;
import JaintPlug.*;
/**
 *
 * @author jonas
 */
public class EcouteurFlou implements ActionListener
{
    private FlouNettete flNe;

    /**
     *
     * @param f
     */
    public EcouteurFlou(FlouNettete f)
    {
        this.flNe = f;
    }

    public void actionPerformed(ActionEvent ae)
    {
        flNe.previsualiserFlou();
    }

 

}
