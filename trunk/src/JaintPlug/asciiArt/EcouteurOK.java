package JaintPlug.asciiArt;

/**
 *
 * @author Jonas
 */
import java.awt.event.*;
import JaintPlug.*;

public class EcouteurOK implements ActionListener
{

    private AsciiArt aa;

    /**
     *
     * @param a
     */
    public EcouteurOK(AsciiArt a)
    {
        this.aa = a;
    }

    public void actionPerformed(ActionEvent ae)
    {
        this.aa.apply();
    }

}
