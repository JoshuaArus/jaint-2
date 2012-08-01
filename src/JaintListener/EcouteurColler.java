package JaintListener;

import Jaint.*;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Joshua
 */
public class EcouteurColler implements ActionListener
{
    IHM ihm;

    /**
     *
     * @param aThis
     */
    public EcouteurColler(IHM aThis)
    {
        ihm = aThis;
    }

    public void actionPerformed(ActionEvent e)
    {
        if (ihm.jtp.getSelectedIndex() > -1)
        {
            if (ihm.jaint.cut.getBufferedImage() != null)
            {
            	ihm.hidePalettes();
                ihm.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                ihm.jaint.cut.setEnabled(true);
                ihm.jaint.mode = 2;
            }
            else if(ihm.jaint.copy.getBufferedImage() != null)
            {
            	ihm.hidePalettes();
                ihm.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                ihm.jaint.copy.setEnabled(true);
                ihm.jaint.mode = 3;
            }
        }
    }

}
