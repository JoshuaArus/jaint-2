package JaintListener;

import Jaint.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe mettant à jour le centre de la palette si le pinceau est sélectionné
 * @author Jeff
 */
public class EcouteurPinceau implements ActionListener
{
    private JaintPalette palette;

    /**
     *
     * @param p
     */
    public EcouteurPinceau(JaintPalette p)
    {
          palette = p;
    }

    public void actionPerformed(ActionEvent e)
    {
        palette.setCenter(JaintPalette.CHOIX_PINCEAU);
        palette.repaint();
    }



}
