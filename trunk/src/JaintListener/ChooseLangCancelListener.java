package JaintListener;

/**
 * Classe permettant de fermer le JDialog de sélecteur de langue
 * @author Jeff
 */



import Jaint.ChooseLanguage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChooseLangCancelListener implements ActionListener
{
	private ChooseLanguage cl;

        /**
         *
         * @param cl La boite de dialogue qui permet le choix de la langue
         */
        public ChooseLangCancelListener(ChooseLanguage cl)
	{
		this.cl = cl;
	}

	public void actionPerformed(ActionEvent e)
	{
		this.cl.dispose();
	}
}
