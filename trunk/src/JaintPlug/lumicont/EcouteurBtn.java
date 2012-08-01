package JaintPlug.lumicont;

/**
 *
 * @author Jonas
 */
import java.awt.event.*;
import javax.swing.*;
import JaintPlug.*;

public class EcouteurBtn implements ActionListener
{
    private LumiCont lc;

    /**
     *
     * @param lc
     */
    public EcouteurBtn(LumiCont lc)
    {
        this.lc = lc;
    }

    public void actionPerformed(ActionEvent ae)
    {
        //On recupere le nom du bouton sur lequel l'utilisateur a cliquer
        String btn = ((JButton) ae.getSource()).getName();

        if(btn.equals("jbcl"))
        {
            lc.previsualiserContMoins();
            lc.previsualiserLumiMoins();
        }
        else if(btn.equals("jbl"))
        {
            lc.previsualiserLumiMoins();
        }
        else if(btn.equals("jbCl"))
        {
            lc.previsualiserContPlus();
            lc.previsualiserLumiMoins();
        }
        else if(btn.equals("jbc"))
        {
            lc.previsualiserContMoins();
        }
        else if(btn.equals("jbC"))
        {
            lc.previsualiserContPlus();
        }
        else if(btn.equals("jbcL"))
        {
            lc.previsualiserContMoins();
            lc.previsualiserLumiPlus();
        }
        else if(btn.equals("jbL"))
        {
            lc.previsualiserLumiPlus();
        }
        else
        {
            lc.previsualiserContPlus();
            lc.previsualiserLumiPlus();
        }

     }
}
