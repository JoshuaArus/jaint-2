package JaintListener;

import Jaint.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JSpinner;
import javax.swing.border.Border;

/**
 * Classe permettant de mettre a jour les informations de la JPalette lorsqu'on des boutons de couleur est presse
 * @author Jeff
 */
public class EcouteurBtnCouleur implements ActionListener
{

    private JaintPalette palette;

    /**
     *
     * @param p
     */
    public EcouteurBtnCouleur(JaintPalette p)
    {
        palette = p;
    }

    public void actionPerformed(ActionEvent e)
    {
        
        JBouton btn1 = palette.getBoutonColor()[0];
        JBouton btn2 = palette.getBoutonColor()[1];

        JBouton btnEnfonce = (JBouton) e.getSource();
        Color c;
       
        if(btnEnfonce == btn1 && !btn1.isChecked())
        {
            Border bordBtn = btn1.getBorder();
            btn2.setBorder(bordBtn);
            btn1.setBorder(BorderFactory.createEtchedBorder(Color.ORANGE, Color.ORANGE));
            btn1.check();
            btn2.uncheck();

            c = palette.getFirstColor();
            majSpinner(c);
           
        }
        else if(btnEnfonce == btn2 && !btn2.isChecked())
        {
            Border bordBtn = btn2.getBorder();
            btn1.setBorder(bordBtn);
            btn2.setBorder(BorderFactory.createEtchedBorder(Color.ORANGE, Color.ORANGE));
            btn2.check();
            btn1.uncheck();

            c = palette.getSecondColor();
             majSpinner(c);
           
        }

    }

    private void majSpinner(Color c)
    {
        JSpinner[] rvb = palette.getSpinner();

            rvb[0].setValue(c.getRed());
            rvb[1].setValue(c.getGreen());
            rvb[2].setValue(c.getBlue());

    }

}
