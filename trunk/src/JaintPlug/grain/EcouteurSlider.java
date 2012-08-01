package JaintPlug.grain;

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
	private Grain g;
        private CompteARebourds car;
   

        /**
         *
         * @param g
         * @param c
         */
        public EcouteurSlider(Grain g, CompteARebourds c)
	{
		this.g = g;
                this.car = c;
	}

   
	public void stateChanged(ChangeEvent e)
	{

            if(this.car == null)
                    {
                        this.car = new CompteARebourds(500, g);
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


                car = new CompteARebourds(500, g);
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
