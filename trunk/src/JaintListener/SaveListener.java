package JaintListener;

import Jaint.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Joshua
 */
public class SaveListener implements ActionListener
{

    /**
     *
     */
    public IHM ihm;

    /**
     *
     * @param i
     */
    public SaveListener(IHM i)
    {
        this.ihm = i;
    }
    public void actionPerformed(ActionEvent e)
    {
        ihm.jaint.enregistrer();
    }
}
