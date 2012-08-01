package JaintListener;


import Jaint.*;
import java.awt.event.*;

/**
 *
 * @author jonas
 */
public class GestPlugListener implements ActionListener
{
        IHM ihm;

        /**
           *Constructeur prenant en parametre l'ihm courante
       * @param i IHM courante
      */
        public GestPlugListener(IHM i)
	{
            this.ihm = i;
        }
	public void actionPerformed(ActionEvent e)
	{
		this.ihm.gestPlugins();
	}
}