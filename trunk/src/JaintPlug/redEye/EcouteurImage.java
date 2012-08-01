package JaintPlug.redEye;

import JaintPlug.RedEye;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Joshua
 */
public class EcouteurImage implements MouseListener, MouseMotionListener
{
    private RedEye re;
    private static boolean bool = false;

    /**
     *
     * @param e
     */
    public EcouteurImage(RedEye e)
    {
        re = e;
    }

    public void mouseClicked(MouseEvent e) {
        
    }

    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1)
            bool = true;
    }

    public void mouseReleased(MouseEvent e) {
        if(bool)
        {
            re.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            re.previsualiser(e.getPoint());
            re.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }
        bool = false;
    }

    public void mouseEntered(MouseEvent e) {
        re.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

    }

    public void mouseExited(MouseEvent e) {
        re.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        
    }

}
