package JaintPlug.redim;


import java.awt.event.*;
import JaintPlug.*;
/**
 *
 * @author jonas
 */
public class EcouteurOK implements ActionListener
{
    private Redimensionnement re;

    /**
     *
     * @param re
     */
    public EcouteurOK(Redimensionnement re)
    {
        this.re = re;
    }

    public void actionPerformed(ActionEvent ae)
    {
        re.apply();
    }




}