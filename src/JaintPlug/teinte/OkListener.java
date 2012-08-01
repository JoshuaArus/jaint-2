package JaintPlug.teinte;

/**
 *
 * @author Jonas
 */
import java.awt.event.*;
import JaintPlug.*;

public class OkListener implements ActionListener
{
    private Teinte te;

    /**
     *
     * @param t
     */
    public OkListener(Teinte t)
    {
            this.te = t;
    }

    public void actionPerformed(ActionEvent e)
    {
            this.te.apply();
    }
}