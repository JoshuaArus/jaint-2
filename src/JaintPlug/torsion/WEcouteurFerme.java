package JaintPlug.torsion;

/**
 *
 * @author Jonas
 */


import java.awt.event.*;
import JaintPlug.*;

public class WEcouteurFerme extends WindowAdapter
{
	 private Torsion to;

         /**
          *
          * @param to
          */
         public WEcouteurFerme(Torsion to)
	{
		this.to = to;
	}

    @Override
	public void windowClosing(WindowEvent e)
	{
		this.to.annuler();
	}
}
