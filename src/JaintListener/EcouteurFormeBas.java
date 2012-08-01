package JaintListener;

import Jaint.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

/**
 * Classe permettant de mettre a jour le bas du centre pour selectionner les formes geometriques
 * @author Jeff
 */
public class EcouteurFormeBas implements ActionListener
{
    private JaintPalette palette;

    /**
     *
     * @param p
     */
    public EcouteurFormeBas(JaintPalette p)
    {
        palette = p;
    }

    public void actionPerformed(ActionEvent e)
    {
       JBouton btnEnfonce = (JBouton) e.getSource();

       String name = btnEnfonce.getName();
       if(name.equals("carre1"))
           palette.setSelectedForm2(JaintPalette.CARRE1);
       else if(name.equals("carre2"))
           palette.setSelectedForm2(JaintPalette.CARRE2);
       else if(name.equals("carre3"))
           palette.setSelectedForm2(JaintPalette.CARRE3);
       else if(name.equals("rond1"))
           palette.setSelectedForm2(JaintPalette.ROND1);
       else if(name.equals("rond2"))
           palette.setSelectedForm2(JaintPalette.ROND2);
       else if(name.equals("rond3"))
           palette.setSelectedForm2(JaintPalette.ROND3);

       Border border = new JButton().getBorder();

       JBouton[] tab_btn = palette.getBoutonBas();

       for(JBouton btn : tab_btn)
           btn.setBorder(border);

       btnEnfonce.setBorder(BorderFactory.createEtchedBorder(Color.ORANGE, Color.ORANGE));
    }



}
