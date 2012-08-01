package JaintListener;

import Jaint.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe permettant de mettre a jour le centre de la palette lorsque la gomme est selectionnee
 * @author Jeff
 */
public class EcouteurGomme implements ActionListener
{
    private JaintPalette palette;

    /**
     *
     * @param palette
     */
    public EcouteurGomme(JaintPalette palette)
    {
        this.palette = palette;
    }

    public void actionPerformed(ActionEvent e)
    {
        palette.setCenter(JaintPalette.CHOIX_GOMME);
        palette.repaint();
    }

}
