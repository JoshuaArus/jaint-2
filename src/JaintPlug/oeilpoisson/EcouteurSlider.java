package JaintPlug.oeilpoisson;

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
	private OeilDePoisson odp;
        private CompteARebourd car;
   

        /**
         *
         * @param odp
         * @param c
         */
        public EcouteurSlider(OeilDePoisson odp, CompteARebourd c)
	{
		this.odp = odp;
                this.car = c;
	}

	public void stateChanged(ChangeEvent e)
	{

            if(this.car == null)
                    {
                        this.car = new CompteARebourd(50);
                    }
            

                if(Thread.activeCount() > 4)
                {
                    CompteARebourd.interrupted();
                    
                    try
                    {
                        this.car.start();
                    }
                    catch (IllegalThreadStateException ex)
                    {
                        ;
                    }
                }
                else
                {
                    
                   
                         ThreadEffet effect = new ThreadEffet(odp);
                         effect.start();

                         CompteARebourd.interrupted();
                         
                         this.car.start();
                   
                }


           
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
