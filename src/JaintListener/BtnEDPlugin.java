package JaintListener;


import Jaint.*;
import javax.swing.event.*;

/**
 * Ecouteur qui appelle ihm.modifyBtnED()
 * @author Jonas
 */

public class BtnEDPlugin implements ListSelectionListener
{
       IHM ihm;
       
       /**
         * Rajoute l'écouteur sur l'IHM passée en parametre
         * @param i ihm courante
         */
       public BtnEDPlugin(IHM i)
       {
           this.ihm = i;
       }

       public void valueChanged(ListSelectionEvent e)
       {
            this.ihm.modifyBtnED();
       }
}


