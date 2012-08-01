package JaintPlug.sobel;

/**
 *
 * @author Jonas
 */

import java.awt.event.*;
import JaintPlug.*;

public class WEcouteurFerme extends WindowAdapter
{
	 private Sobel sbl;

         /**
          *
          * @param s
          */
         public WEcouteurFerme(Sobel s)
	{
		this.sbl = s;
	}

    @Override
	public void windowClosing(WindowEvent e)
	{
		this.sbl.annuler();
	}
}
