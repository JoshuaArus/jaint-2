package JaintPlug.redEye;

/**
 *
 * @author Joshua
 */
import java.awt.event.*;
import JaintPlug.*;

public class EcouteurAnnuler implements ActionListener
{

    private RedEye re;

    /**
     *
     * @param e
     */
    public EcouteurAnnuler(RedEye e)
    {
        this.re = e;
    }

    public void actionPerformed(ActionEvent ae)
    {
        this.re.annuler();
    }

}
