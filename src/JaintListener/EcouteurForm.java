package JaintListener;

import Jaint.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe permettant d'activer le centre des formes geometriques dans la JaintPalette
 * @author Jeff
 */
public class EcouteurForm implements ActionListener
{
    JaintPalette palette;

    /**
     *
     * @param p
     */
    public EcouteurForm(JaintPalette p)
    {
        palette = p;
    }

    public void actionPerformed(ActionEvent e)
    {
        palette.setCenter(JaintPalette.CHOIX_FORME);
        palette.repaint();
    }

}
