package JaintPlug.asciiArt;

/**
 *
 * @author Jonas
 */


import javax.swing.event.*;
import JaintPlug.*;


public class EcouteurSpinner implements DocumentListener
{
    private AsciiArt aa;

    /**
     *
     * @param a
     */
    public EcouteurSpinner(AsciiArt a)
    {
        this.aa = a;
    }

    public void insertUpdate(DocumentEvent e)
    {
        this.aa.setTaille();
        this.aa.previsualiser();
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






