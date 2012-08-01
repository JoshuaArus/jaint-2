package JaintPlug.peinture;

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
	private PeintureHuile ph;
        private CompteARebourds car;
   

        /**
         *
         * @param ph
         * @param c
         */
        public EcouteurSlider(PeintureHuile ph, CompteARebourds c)
	{
		this.ph = ph;
                this.car = c;
	}

	public void stateChanged(ChangeEvent e)
	{


              if(this.car == null)
                    {
                        this.car = new CompteARebourds(500, ph);
                    }


                if(car.isAlive())
                {
                   try
                   {
                        this.car.interrupt();
                   }
                   catch(Exception ei)
                   {

                   }
                }


                car = new CompteARebourds(500, ph);
                this.car.start();

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
