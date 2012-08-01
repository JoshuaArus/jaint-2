package JaintPlug.miroire;
/**
 *
 * @author Jonas
 */


import java.awt.event.*;
import JaintPlug.*;

public class WEcouteurFerme extends WindowAdapter
{
	 private Miroire mi;

         /**
          *
          * @param mi
          */
         public WEcouteurFerme(Miroire mi)
	{
		this.mi = mi;
	}

    @Override
	public void windowClosing(WindowEvent e)
	{
		this.mi.annuler();
	}
}
