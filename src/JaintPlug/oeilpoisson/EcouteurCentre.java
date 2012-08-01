

package JaintPlug.oeilpoisson;

import JaintPlug.*;
import java.awt.event.*;



/**
 *
 * @author Jonas
 */
public class EcouteurCentre implements MouseListener
{
	private OeilDePoisson odp;

        /**
         *
         * @param odp
         */
        public EcouteurCentre(OeilDePoisson odp)
	{
		this.odp = odp;
	}

	public void mouseClicked(MouseEvent e)
	{
		this.odp.selectLocation(e.getX(), e.getY());
	}

	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
}

