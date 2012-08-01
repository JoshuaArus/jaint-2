package JaintPlug.redim;

/**
 *
 * @author Jonas
 */


import java.awt.event.*;
import JaintPlug.*;

/**
 *
 * @author Joshua
 */
public class WEcouteurFerme extends WindowAdapter
{
    /**
     *
     */
    public Redimensionnement re;

         /**
          *
          * @param re
          */
         public WEcouteurFerme(Redimensionnement re)
	{
		this.re = re;
	}

    @Override
	public void windowClosing(WindowEvent e)
	{
		this.re.annuler();
	}
}
