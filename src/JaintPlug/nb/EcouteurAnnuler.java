package JaintPlug.nb;

/**
 *
 * @author Jonas
 */
import java.awt.event.*;
import JaintPlug.*;

public class EcouteurAnnuler implements ActionListener
{

    private NoirEtBlanc nb;

    /**
     *
     * @param nb
     */
    public EcouteurAnnuler(NoirEtBlanc nb)
    {
        this.nb = nb;
    }

    public void actionPerformed(ActionEvent ae)
    {
        this.nb.annuler();
    }

}
