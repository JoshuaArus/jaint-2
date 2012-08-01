package JaintPlug.sobel;

/**
 *
 * @author Jonas
 */
import java.awt.event.*;
import JaintPlug.*;


public class EcouteurOK implements ActionListener
{

    private Sobel sbl;
    
    /**
     *
     * @param s
     */
    public EcouteurOK(Sobel s)
    {
        this.sbl = s;
    }

    public void actionPerformed(ActionEvent ae)
    {
        this.sbl.apply();
    }

}
