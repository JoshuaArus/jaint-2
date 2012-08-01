package JaintListener;


import Jaint.*;
import javax.swing.event.*;

/**
 *Mise a jour des icones des plugins
 * @author Jonas
 */
public class EcouteurMAJIconPlug implements ChangeListener
{
    IHM ihm;

    /**
     *Constructeur prenant en parametre l'ihm courante
     * @param i IHM courante
     */
    public EcouteurMAJIconPlug(IHM i)
    {
        this.ihm = i;
    }
    public void stateChanged(ChangeEvent e)
    {
        if (ihm.jtp.getSelectedIndex() > -1)
            ihm.initPluginsMenu();
    }

}
