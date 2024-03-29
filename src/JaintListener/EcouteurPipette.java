package JaintListener;

import Jaint.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe mettant a jour le centre de la palette lorsque la pipette est selectionnee
 * @author Jeff
 */
public class EcouteurPipette implements ActionListener
{
    private IHM ihm;

    /**
     *
     * @param i
     */
    public EcouteurPipette(IHM i)
    {
        ihm = i;
    }

    public void actionPerformed(ActionEvent e)
    {

       ihm.palette.setCenter(JaintPalette.CHOIX_PIPETTE);
    }

}
