package JaintPlug.flouNet;

/**
 *
 * @author Jonas
 */

import java.awt.event.*;
import JaintPlug.*;

public class WEcouteurFerme extends WindowAdapter
{
	 private FlouNettete flNe;

         /**
          *
          * @param f
          */
         public WEcouteurFerme(FlouNettete f)
	{
		this.flNe = f;
	}

    @Override
	public void windowClosing(WindowEvent e)
	{
		this.flNe.annuler();
	}
}
