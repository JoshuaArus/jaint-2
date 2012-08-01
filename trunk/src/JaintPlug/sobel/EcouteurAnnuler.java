package JaintPlug.sobel;

/**
 *
 * @author Jonas
 */
import java.awt.event.*;
import JaintPlug.*;

public class EcouteurAnnuler implements ActionListener
{

    private Sobel sbl;

    /**
     *
     * @param s
     */
    public EcouteurAnnuler(Sobel s)
    {
        this.sbl = s;
    }

    public void actionPerformed(ActionEvent ae)
    {
        this.sbl.annuler();
    }

}
