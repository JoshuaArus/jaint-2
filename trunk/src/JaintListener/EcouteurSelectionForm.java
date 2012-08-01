package JaintListener;

import Jaint.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Classe mettant a jour le centre de la palette lorsque une forme geometrique est selectionnee
 * @author Jeff
 */
public class EcouteurSelectionForm implements ActionListener
{
    private JaintPalette palette;
    private JBouton[] tab_btn;



    /**
     *
     * @param p
     */
    public EcouteurSelectionForm(JaintPalette p)
    {
        palette = p;
        tab_btn = palette.getBoutonForme();
    }

    public void actionPerformed(ActionEvent e)
    {
        JBouton btnEnfonce = (JBouton) e.getSource();

        if(tab_btn[JaintPalette.CARRE] == btnEnfonce)
        {
            palette.setForm(JaintPalette.CARRE);
        }
        else if(tab_btn[JaintPalette.ROND]== btnEnfonce)
        {
            palette.setForm(JaintPalette.ROND);
        }
        else if(tab_btn[JaintPalette.LIGNE] == btnEnfonce)
        {
            palette.setForm(JaintPalette.LIGNE);
        }

        palette.repaint();
    }



   

}
