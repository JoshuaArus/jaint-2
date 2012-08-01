package JaintListener;

import Jaint.*;
import JaintPlug.Redimensionnement;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Ecouteur permettant les raccourcis clavier
 * @author Joshua
 */
public class EcouteurClavier implements KeyListener
{
    IHM ihm;

    /**
     *
     * @param aThis Instance de l'interface principale
     */
    public EcouteurClavier(IHM aThis)
    {
        ihm = aThis;
    }

    public void keyTyped(KeyEvent e)
    {
    }

    public void keyPressed(KeyEvent e)
    {
    }

    public void keyReleased(KeyEvent e)
    {
        String modifier = KeyEvent.getKeyModifiersText(e.getModifiers());
        String touche = KeyEvent.getKeyText(e.getKeyCode());

        if (modifier.equals("Ctrl"))
        {

            if (touche.equals("Z"))
                ihm.jaint.undo();
            else if (touche.equals("Y"))
                ihm.jaint.redo();
            else if (touche.equals("S"))
                ihm.jaint.enregistrer();
            else if (touche.equals("P"))
                Imprimante.print(ihm);
            else if (touche.equals("O"))
                ihm.jaint.ouvrir();
            else if (touche.equals("C"))
                new EcouteurCopier(ihm).actionPerformed(null);
            else if (touche.equals("X"))
                new EcouteurCouper(ihm).actionPerformed(null);
            else if (touche.equals("V"))
                new EcouteurColler(ihm).actionPerformed(null);
            else if (touche.equals("W"))
                ihm.close(ihm.jtp.getSelectedIndex());
            else if (touche.equals("N"))
                ihm.nouveauFichier();
            else if (touche.equals("Q"))
                ihm.jaint.quitter();
            else if (touche.equals("B"))
                new EcouteurRogner(ihm).actionPerformed(null);
            else if (touche.equals("T"))
                ihm.addTab(new Redimensionnement().modify(((MainComponent)(ihm.jtp.getSelectedComponent())).getBufferedImage()),4);
        }

        else if (modifier.equals("Ctrl+Alt"))
        {
            if (touche.equals("S"))
                ihm.jaint.enregistrerSous();
        }

         else if(modifier.equals(""))
        {
            if (touche.equals("F2"))
            {
                ihm.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                AideDemarrage ad = new AideDemarrage(ihm);
                ad.initPos();
            }
            else if(touche.equals("F1"))
            {
                AideEnLigne ael = new AideEnLigne(ihm);
                ael.setVisible(true);
            }
            else if(touche.equals(KeyEvent.getKeyText(KeyEvent.VK_PRINTSCREEN)))
                ihm.addTab(IHM.getScreenshot(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())),1);
            else if (touche.equals("F11"))
                ihm.pleinEcran();
            else if(touche.equals("Esc"))
            	ihm.hidePalettes();
        }
    }
}
