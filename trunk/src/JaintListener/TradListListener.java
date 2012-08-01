package JaintListener;

/**
 * Classe changeant le drapeau dans la boite de dialogue qui permet la traduction d'une langue en fonction de la langue choisie
 * @author Jeff
 */

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TradListListener implements ActionListener
{
	TranslateDialog t;

        /**
         *
         * @param t
         */
        public TradListListener(TranslateDialog t)
	{
		this.t = t;
	}

	public void actionPerformed(ActionEvent e)
	{
		try
		{
			this.t.savedLanguage();
		}
		catch (java.io.IOException ioe)
		{
		}
		this.t.flagChange();
	}
}
