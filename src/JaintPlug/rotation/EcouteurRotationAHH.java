/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JaintPlug.rotation;

import JaintPlug.*;
import java.awt.event.*;
/**
 *
 * @author jonas
 */
public class EcouteurRotationAHH implements ActionListener
{

    private Rotation ro;
    private Boolean b;

    /**
     *
     * @param r
     * @param ah
     */
    public EcouteurRotationAHH(Rotation r, Boolean ah)
    {
        this.ro = r;
        this.b = ah;
    }

    public void actionPerformed(ActionEvent ae)
    {
        if(this.b)
        {
            ro.previsualiserRotationAH();
        }
        else
        {
            ro.previsualiserRotationH();
        }

    }


}
