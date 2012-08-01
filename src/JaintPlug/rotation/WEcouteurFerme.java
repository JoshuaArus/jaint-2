package JaintPlug.rotation;

/**
 *
 * @author Jonas
 */


import java.awt.event.*;
import JaintPlug.*;

public class WEcouteurFerme extends WindowAdapter
{
	 private Rotation ro;

         /**
          *
          * @param ro
          */
         public WEcouteurFerme(Rotation ro)
	{
		this.ro = ro;
	}

    @Override
	public void windowClosing(WindowEvent e)
	{
		this.ro.annuler();
	}
}
