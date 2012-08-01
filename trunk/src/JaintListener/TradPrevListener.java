package JaintListener;

/**
 * Classe permettant d'afficher la liste de mots d'avant à traduire.
 * @author Jeff
 */


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *
 * @author Joshua
 */
public class TradPrevListener implements ActionListener
{
	TranslateDialog t;

        /**
         *
         * @param t
         */
        public TradPrevListener(TranslateDialog t)
	{
		this.t = t;
	}

	public void actionPerformed(ActionEvent e)
	{
		this.t.previous();
	}
}
