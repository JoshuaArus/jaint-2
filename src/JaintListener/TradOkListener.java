package JaintListener;

/**
 * Classe lancant le traitement pour traduire la langue lorsque l'utilisateur a clique sur OK
 * @author Jeff
 */


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TradOkListener implements ActionListener
{
	private TranslateDialog t;

        /**
         *
         * @param t
         */
        public TradOkListener(TranslateDialog t)
	{
		this.t = t;
	}

	public void actionPerformed(ActionEvent e)
	{
		String[] s = this.t.getComponentsStrings();
		try
		{
			this.t.ok(s);
		}
		catch (java.io.IOException ioe)
		{
		}
	}
}

