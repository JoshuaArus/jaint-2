package JaintListener;

import Jaint.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Joshua
 */
public class SaveAsListener implements ActionListener
{

    /**
     *
     */
    public IHM ihm;

    /**
     *
     * @param i
     */
    public SaveAsListener(IHM i)
    {
        this.ihm = i;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        ihm.jaint.enregistrerSous();
    }
}