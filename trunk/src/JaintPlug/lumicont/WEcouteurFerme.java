package JaintPlug.lumicont;

/**
 *
 * @author Jonas
 */


import java.awt.event.*;
import JaintPlug.*;

public class WEcouteurFerme extends WindowAdapter
{
	 private LumiCont lc;

         /**
          *
          * @param lc
          */
         public WEcouteurFerme(LumiCont lc)
	{
		this.lc = lc;
	}

    @Override
	public void windowClosing(WindowEvent e)
	{
		this.lc.annuler();
	}
}
