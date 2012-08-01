package JaintPlug.oeilpoisson;

/**
 *
 * @author Jonas
 */
import JaintPlug.*;
import java.awt.event.*;

public class EcouteurCentreMove implements MouseMotionListener
{

    private OeilDePoisson odp;

    /**
     *
     * @param odp
     */
    public EcouteurCentreMove(OeilDePoisson odp)
    {
            this.odp = odp;
    }

    public void mouseDragged(MouseEvent e)
    {

        this.odp.selectLocation(e.getX(), e.getY());
    }


    public void mouseMoved(MouseEvent e)
    {

        double scale = this.odp.getScaleValue();
        this.odp.getJlImage().setToolTipText("x :"+(int)(e.getX()/scale)+"y : "+(int)(e.getY()/scale));
    }

}
