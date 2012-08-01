
package JaintPlug;


import Jaint.*;
import java.awt.event.*;


/**
 * Ecouteur pour l'icone sur le bouton pour appliquer
 * @author jonas
 */
public class EcouteurBoutonAppliquer implements MouseListener
{
    private Plugin plug;

    /**
     *Constructeur prenant un plugin en parametre
     * @param pl Plugin utilise
     */
    public EcouteurBoutonAppliquer(Plugin  pl)
    {
        this.plug = pl;
    }


    /**
     * Affiche l'icone valide sur le bouton valider si l'utilisateur se trouve dessu
     * @param entre boolean
     */
    public void modifBtn(Boolean enter)
    {
        if(this.plug instanceof AsciiArt)
        {
            AsciiArt p =  (AsciiArt) this.plug;
            p.modifBtnApply(enter);
        }
        else if(this.plug instanceof FlouNettete)
        {
            FlouNettete p =  (FlouNettete) this.plug;
            p.modifBtnApply(enter);
        }
        else if(this.plug instanceof Grain)
        {
            Grain p =  (Grain) this.plug;
            p.modifBtnApply(enter);
        }
        else if(this.plug instanceof LumiCont)
        {
            LumiCont p =  (LumiCont) this.plug;
            p.modifBtnApply(enter);
        }
        else if(this.plug instanceof Miroire)
        {
            Miroire p =  (Miroire) this.plug;
            p.modifBtnApply(enter);
        }
        else if(this.plug instanceof NoirEtBlanc)
        {
            NoirEtBlanc p =  (NoirEtBlanc) this.plug;
            p.modifBtnApply(enter);
        }
        else if(this.plug instanceof OeilDePoisson)
        {
            OeilDePoisson p =  (OeilDePoisson) this.plug;
            p.modifBtnApply(enter);
        }
        else if(this.plug instanceof PeintureHuile)
        {
            PeintureHuile p =  (PeintureHuile) this.plug;
            p.modifBtnApply(enter);
        }
        else if(this.plug instanceof Saturation)
        {
            Saturation p =  (Saturation) this.plug;
            p.modifBtnApply(enter);
        }
        else if(this.plug instanceof Sobel)
        {
            Sobel p =  (Sobel) this.plug;
            p.modifBtnApply(enter);
        }
        else if(this.plug instanceof Torsion)
        {
            Torsion p =  (Torsion) this.plug;
            p.modifBtnApply(enter);
        }
        else if(this.plug instanceof Rotation)
        {
            Rotation p = (Rotation) this.plug;
            p.modifBtnApply(enter);

        }
        else if(this.plug instanceof Teinte)
        {
            Teinte p = (Teinte) this.plug;
            p.modifBtnApply(enter);
        }



    }
    public void mouseEntered(MouseEvent e)
    {
       modifBtn(true);
    }


    public void mouseExited(MouseEvent e)
    {
        modifBtn(false);
    }

    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}

}
