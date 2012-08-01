package JaintListener;

/**
 * Classe créant la boite de dialogue pour la traduction d'une langue
 * @author Jeff
 */

import Jaint.IHM;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TradLangListener implements ActionListener
{
	private IHM ihm;

        /**
         *
         * @param ih
         */
        public TradLangListener(IHM ih)
	{
		this.ihm = ih;
	}

	public void actionPerformed(ActionEvent e)
	{
		new TranslateDialog(ihm, "Nouvelle langue", true);
	}
}
