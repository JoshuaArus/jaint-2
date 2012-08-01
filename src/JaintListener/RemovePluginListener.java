package JaintListener;

import Jaint.*;
import java.awt.event.*;


/**
 * Suppression des plugins
 * @author Jonas
 */
public class RemovePluginListener implements ActionListener
{
	private IHM ihm;

        /**
     *Constructeur prenant en parametre l'ihm courante
     * @param i IHM courante
     */
        public RemovePluginListener(IHM i)
	{
		this.ihm = i;
	}
	public void actionPerformed(ActionEvent e)
	{
		this.ihm.removePlugin();
	}
}