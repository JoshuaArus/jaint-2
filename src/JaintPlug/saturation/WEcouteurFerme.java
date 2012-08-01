package JaintPlug.saturation;

/**
 *
 * @author Jonas
 */

import java.awt.event.*;
import JaintPlug.*;


public class WEcouteurFerme extends WindowAdapter
{
	 private Saturation sat;

         /**
          *
          * @param sat
          */
         public WEcouteurFerme(Saturation sat)
	{
		this.sat = sat;
	}

    @Override
	public void windowClosing(WindowEvent e)
	{
		this.sat.annuler();
	}
}
