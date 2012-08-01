package JaintListener;

/**
 * Classe permettant d'afficher la liste de mots suivant a traduire
 * @author Jeff
 */


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TradNextListener implements ActionListener
{
	TranslateDialog t;

        /**
         *
         * @param t
         */
        public TradNextListener(TranslateDialog t)
	{
		this.t = t;
	}

	public void actionPerformed(ActionEvent e)
	{
		this.t.next();
	}
}

