package JaintListener;



import Jaint.*;
import java.awt.event.*;

/**
 *
 * @author jonas
 */
public class EDPluginListener implements ActionListener
{
        private IHM ihm;

        /**
         *Constructeur prenant une IHM en parametre
         * @param i ihm courante
         */
        public EDPluginListener(IHM i)
	{
            this.ihm = i;
        }
	public void actionPerformed(ActionEvent e)
	{
		this.ihm.edPlugin();
	}
}