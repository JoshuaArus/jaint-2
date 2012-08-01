package JaintListener;

import Jaint.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe mettant à jour le centre de la palette lorsque le pot de peinture est sélectionnée
 * @author Jeff
 */
public class EcouteurPot implements ActionListener
{
    JaintPalette palette;

    /**
     *
     * @param palette
     */
    public EcouteurPot(JaintPalette palette)
    {
        this.palette = palette;
    }

    public void actionPerformed(ActionEvent e)
    {
        palette.setCenter(JaintPalette.CHOIX_POT_PEINTURE);
        palette.repaint();

    }

}
