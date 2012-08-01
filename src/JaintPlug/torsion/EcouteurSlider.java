package JaintPlug.torsion;

/**
 *
 * @author Jonas
 */
import java.awt.event.MouseEvent;
import javax.swing.event.*;
import JaintPlug.*;
import java.awt.event.MouseListener;


public class EcouteurSlider implements ChangeListener, MouseListener
{
	private Torsion to;
  
   

        /**
         *
         * @param to
         */
        public EcouteurSlider(Torsion to)
	{
		this.to = to;
               
	}

	public void stateChanged(ChangeEvent e)
	{

            this.to.setAngle();
            this.to.previsualiser();

	}

    public void mouseClicked(MouseEvent e)
    {

    }

    public void mousePressed(MouseEvent e)
    {

        
    }

    public void mouseReleased(MouseEvent e)
    {
        
    }

    public void mouseEntered(MouseEvent e)
    {

    }

    public void mouseExited(MouseEvent e)
    {

    }
}
