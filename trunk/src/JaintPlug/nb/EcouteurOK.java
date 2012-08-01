package JaintPlug.nb;

/**
 *
 * @author Jonas
 */
import java.awt.event.*;
import JaintPlug.*;

public class EcouteurOK implements ActionListener
{

    private NoirEtBlanc nb;
    
    /**
     *
     * @param nb
     */
    public EcouteurOK(NoirEtBlanc nb)
    {
        this.nb = nb;
    }

    public void actionPerformed(ActionEvent ae)
    {
        this.nb.apply();
    }

}
