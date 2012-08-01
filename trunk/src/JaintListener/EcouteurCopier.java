package JaintListener;

import Jaint.*;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Joshua
 */
public class EcouteurCopier implements ActionListener
{
    IHM ihm;

    /**
     *
     * @param aThis
     */
    public EcouteurCopier(IHM aThis)
    {
        ihm = aThis;
    }

    public void actionPerformed(ActionEvent e)
    {
        if (ihm.jtp.getSelectedIndex() > -1)
        {
        	ihm.hidePalettes();
            ihm.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            ihm.jaint.mode = 1;
        }
    }

}
