/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JaintListener;

import Jaint.NouveauFichier;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;

/**
 *
 * @author Jeff
 */
@SuppressWarnings("serial")
public class EcouteurLargeurHauteur extends PlainDocument implements DocumentListener
{
    private NouveauFichier fichier;

    /**
     *
     * @param f
     */
    public EcouteurLargeurHauteur(NouveauFichier f)
    {
        fichier = f;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
    {
        
            super.insertString(offs, trier(str), a);

            String l = fichier.getLargeur();
              
            String h = fichier.getHauteur();

                if(!fichier.btnCreer.isEnabled())
                {
                    if(!l.equals("") && !h.equals(""))
                    {
                        fichier.btnCreer.setEnabled(true);
                    }

                }
                
        }

    public void insertUpdate(DocumentEvent e)
    {

    }

    public void removeUpdate(DocumentEvent e)
    {

            String l = fichier.getLargeur();
            String h = fichier.getHauteur();

                if(fichier.btnCreer.isEnabled())
                {
                    if(l.equals("") || h.equals(""))
                    {
                        fichier.btnCreer.setEnabled(false);
                    }
                }


    }

    public void changedUpdate(DocumentEvent e)
    {

    }

    /**
     *
     * @param s
     * @return
     */
    public String trier(String s)
    {
        String res = "";

        for (int i = 0; i < s.length(); i++)
        {
            if(Character.isDigit(s.charAt(i)))
            {
                res = res + s.charAt(i);
            }
        }

        return res;
    }
}
