package JaintListener;

/**
 * Classe creant la boite de dialogue qui permet la selection d'une langue
 * @author Jeff
 */


import Jaint.ChooseLanguage;
import Jaint.IHM;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChooseLangListener implements ActionListener
{
	private IHM ihm;

        /**
         *
         * @param nl
         */
        public ChooseLangListener(IHM nl)
	{
		this.ihm = nl;
	}

	public void actionPerformed(ActionEvent e)
	{
		new ChooseLanguage(this.ihm);
	}
}
