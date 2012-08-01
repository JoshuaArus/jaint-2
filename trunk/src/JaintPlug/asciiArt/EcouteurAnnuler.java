package JaintPlug.asciiArt;

/**
 *
 * @author Jonas
 */
import java.awt.event.*;
import JaintPlug.*;

public class EcouteurAnnuler implements ActionListener
{

    private AsciiArt aa;

    /**
     *
     * @param a
     */
    public EcouteurAnnuler(AsciiArt a)
    {
        this.aa = a;
    }

    public void actionPerformed(ActionEvent ae)
    {
        this.aa.annuler();
    }

}
