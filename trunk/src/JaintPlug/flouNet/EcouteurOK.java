package JaintPlug.flouNet;


import java.awt.event.*;
import JaintPlug.*;
/**
 *
 * @author jonas
 */
public class EcouteurOK implements ActionListener
{
    private FlouNettete flNe;

    /**
     *
     * @param f
     */
    public EcouteurOK(FlouNettete f)
    {
        this.flNe = f;
    }

    public void actionPerformed(ActionEvent ae)
    {
        flNe.apply();
    }

    


}