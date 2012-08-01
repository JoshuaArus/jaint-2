package JaintListener;

import Jaint.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author Joshua
 */
public class JaintWindowListener implements WindowListener
{
    IHM ihm;

    /**
     * Ecouteur appelant la fonction quitter() de la classe <code>Jaint</code> lorsqu'on ferme le logiciel
     * @param ihm
     */
    public JaintWindowListener(IHM ihm)
    {
        this.ihm = ihm;
    }
    public void windowClosing(WindowEvent e)
    {
        ihm.jaint.quitter();
    }

    public void windowOpened(WindowEvent e) {
        ;
    }

    public void windowClosed(WindowEvent e) {
        ;
    }

    public void windowIconified(WindowEvent e) {
        ;
    }

    public void windowDeiconified(WindowEvent e) {
        ;
    }

    public void windowActivated(WindowEvent e) {
        ;
    }

    public void windowDeactivated(WindowEvent e) {
        ;
    }

}
