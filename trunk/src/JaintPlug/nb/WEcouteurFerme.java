package JaintPlug.nb;

/**
 *
 * @author Jonas
 */

import java.awt.event.*;
import JaintPlug.*;

public class WEcouteurFerme extends WindowAdapter
{
	 private NoirEtBlanc nb;

         /**
          *
          * @param nb
          */
         public WEcouteurFerme(NoirEtBlanc nb)
	{
		this.nb = nb;
	}

    @Override
	public void windowClosing(WindowEvent e)
	{
		this.nb.annuler();
	}
}
