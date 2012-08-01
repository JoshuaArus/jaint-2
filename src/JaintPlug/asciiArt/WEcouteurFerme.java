package JaintPlug.asciiArt;

/**
 *
 * @author Jonas
 */

import java.awt.event.*;
import JaintPlug.*;

public class WEcouteurFerme extends WindowAdapter
{
	 private AsciiArt as;

         /**
          *
          * @param a
          */
         public WEcouteurFerme(AsciiArt a)
	{
		this.as = a;
	}

    @Override
	public void windowClosing(WindowEvent e)
	{
		this.as.annuler();
	}
}
