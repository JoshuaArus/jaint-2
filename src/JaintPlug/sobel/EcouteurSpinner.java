package JaintPlug.sobel;

/**
 *
 * @author Jonas
 */


import javax.swing.event.*;
import JaintPlug.*;


public class EcouteurSpinner implements DocumentListener
{
    private Sobel sbl;

    /**
     *
     * @param s
     */
    public EcouteurSpinner(Sobel s)
    {
        this.sbl = s;
    }

    public void insertUpdate(DocumentEvent e)
    {
        this.sbl.setTaille();
        this.sbl.previsualiser();
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






