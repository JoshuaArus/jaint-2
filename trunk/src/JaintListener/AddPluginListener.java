package JaintListener;

/**
 * Ecouteur qui appelle ihm.addPlugin()
 * @author jonas
 */

import Jaint.*;
import java.awt.event.*;


public class AddPluginListener implements ActionListener
{
        IHM ihm;

        /**
         * Rajoute l'ecouteur sur l'IHM passee en parametre
         * @param i ihm courante
         */
        public AddPluginListener(IHM i)
	{
            this.ihm = i;
        }
	public void actionPerformed(ActionEvent e)
	{
		this.ihm.addPlugin();
	}
}