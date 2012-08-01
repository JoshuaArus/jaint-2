package JaintPlug.torsion;

/**
 *
 * @author Jonas
 */
import JaintPlug.*;
import java.awt.event.*;


public class EcouteurCentreMove implements MouseMotionListener
{

    private Torsion to;

    /**
     *
     * @param to
     */
    public EcouteurCentreMove(Torsion to)
    {
            this.to = to;
    }

    public void mouseDragged(MouseEvent e)
    {
       
        this.to.selectLocation(e.getX(), e.getY());
    }


    public void mouseMoved(MouseEvent e)
    {

        double scale = this.to.getScaleValue();
        this.to.getJlImage().setToolTipText("x :"+(int)(e.getX()/scale)+"y : "+(int)(e.getY()/scale));
    }

}
