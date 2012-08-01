package JaintPlug.redEye;

/**
 *
 * @author Joshua
 */

import java.awt.event.*;
import JaintPlug.*;

public class WEcouteurFerme extends WindowAdapter
{
    /**
     *
     */
    public RedEye re;

    /**
     *
     * @param a
     */
    public WEcouteurFerme(RedEye a)
	{
		this.re = a;
	}

    @Override
	public void windowClosing(WindowEvent e)
	{
		this.re.annuler();
	}
}
