package JaintPlug.nb;

/**
 *
 * @author Jonas
 */

import javax.swing.event.*;
import JaintPlug.*;

public class EcouteurSpinner implements DocumentListener
{
    private NoirEtBlanc nb;

    /**
     *
     * @param nb
     */
    public EcouteurSpinner(NoirEtBlanc nb)
    {
        this.nb = nb;
    }

    public void insertUpdate(DocumentEvent e)
    {
        this.nb.setTaille();
        this.nb.previsualiser();
    }
    public void removeUpdate(DocumentEvent e){}
    
    public void changedUpdate(DocumentEvent e){}
    
    /**
     *
     * @param e
     * @param action
     */
    public void updateLog(DocumentEvent e, String action){}
    
}






