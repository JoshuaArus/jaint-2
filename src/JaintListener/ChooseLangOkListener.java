package JaintListener;

/**
 * Classe lançcant le traitement adéquat lorsque l'utilisateur a validé la sélection d'une langue
 * @author Jeff
 */


import Jaint.ChooseLanguage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChooseLangOkListener implements ActionListener
{
	private ChooseLanguage cl;

        /**
         *
         * @param cl
         */
        public ChooseLangOkListener(ChooseLanguage cl)
	{
		this.cl = cl;
	}

	public void actionPerformed(ActionEvent e)
	{
		this.cl.languageOk();
		this.cl.dispose();
	}
}

