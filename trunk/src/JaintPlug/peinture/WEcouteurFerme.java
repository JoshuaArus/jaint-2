package JaintPlug.peinture;

/**
 *
 * @author Jonas
 */


import java.awt.event.*;
import JaintPlug.*;

public class WEcouteurFerme extends WindowAdapter
{
	 private PeintureHuile ph;

         /**
          *
          * @param ph
          */
         public WEcouteurFerme(PeintureHuile ph)
	{
		this.ph = ph;
	}

    @Override
	public void windowClosing(WindowEvent e)
	{
		this.ph.annuler();
	}
}
