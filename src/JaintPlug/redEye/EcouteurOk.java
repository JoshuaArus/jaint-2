package JaintPlug.redEye;

/**
 *
 * @author Joshua
 */
import java.awt.event.*;
import JaintPlug.*;

public class EcouteurOk implements ActionListener
{

    private RedEye re;

    /**
     *
     * @param e
     */
    public EcouteurOk(RedEye e)
    {
        this.re = e;
    }

    public void actionPerformed(ActionEvent ae)
    {
        this.re.apply();
    }

}
