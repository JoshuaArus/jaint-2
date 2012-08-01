package JaintPlug.torsion;

import JaintPlug.*;
import java.awt.event.*;



/**
 *
 * @author Joshua
 */
public class EcouteurCentre implements MouseListener
{
	private Torsion to;

        /**
         *
         * @param to
         */
        public EcouteurCentre(Torsion to)
	{
		this.to = to;
	}

	public void mouseClicked(MouseEvent e)
	{
            this.to.selectLocation(e.getX(), e.getY());
	}

        
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
}

