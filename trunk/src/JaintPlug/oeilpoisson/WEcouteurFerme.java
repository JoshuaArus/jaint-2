package JaintPlug.oeilpoisson;

/**
 *
 * @author Jonas
 */


import java.awt.event.*;
import JaintPlug.*;


public class WEcouteurFerme extends WindowAdapter
{
	 private OeilDePoisson odp;

         /**
          *
          * @param odp
          */
         public WEcouteurFerme(OeilDePoisson odp)
	{
		this.odp = odp;
	}

    @Override
	public void windowClosing(WindowEvent e)
	{
		this.odp.annuler();
	}
}
