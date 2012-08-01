package JaintListener;

import Jaint.*;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

/**
 * Classe permettant d'enclencher les fonction de dessin
 * @author Jeff
 */
public class EcouteurDessin implements MouseListener, MouseMotionListener
{
    private IHM ihm;
    private BufferedImage img;
    private static Point debut;
    private static Point origine;
    private static boolean button;
    private static boolean modif;

    private Color color1;
    private Color color2;
    private int margeH = 0;
    private int margeW = 0;

    /**
     *
     * @param i
     */
    public EcouteurDessin(IHM i)
    {
        ihm = i;
    }

    public void mouseClicked(MouseEvent e)
    {
        
    }

    public void mousePressed(MouseEvent e)
    {
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            debut = e.getPoint();
            debut = new Point(debut.x - margeW, debut.y - margeH);
            origine = new Point(debut);
            button = true;
            dessiner(debut);
        }
            
    }

    public void mouseReleased(MouseEvent e)
    {
        if (button && modif)
        {
            ihm.jaint.applyEffect(((MainComponent)ihm.jtp.getSelectedComponent()).getBufferedImage());
            //On met a jour les icone du menu des plugin
             ihm.initPluginsMenu();
        }

        debut = null;
        button = false;
        modif = false;
    }

    public void mouseEntered(MouseEvent e)
    {

    }

    public void mouseExited(MouseEvent e)
    {

    }

    public void mouseDragged(MouseEvent e)
    {
        if(button)
        {
            Point point = e.getPoint();
            point = new Point(point.x - margeW, point.y - margeH);
            dessiner(point);
            debut = point;
        }
    }

    public void mouseMoved(MouseEvent e)
    {

    }


    private void dessiner(Point p)
    {
        int taille = ihm.palette.getSliderSize().getValue();
        p.x = p.x - taille/2;
        p.y = p.y - taille/2;
        color1 = ihm.palette.getFirstColor();
        color2 = ihm.palette.getSecondColor();
        img = ((MainComponent)ihm.jtp.getSelectedComponent()).getBufferedImage();
        
            switch(ihm.palette.getCenter())
            {
                case JaintPalette.CHOIX_PINCEAU :
                    ihm.fd.tracer_pinceau(img, debut, p, taille, color1);
                    modif = true;
                break;

                case JaintPalette.CHOIX_POT_PEINTURE :                    
                    ihm.fd.remplissage(img, p, ihm.palette.getSliderPotValue(), color1);
                    modif = true;
                break;

                case JaintPalette.CHOIX_GOMME :
                    ihm.fd.tracer_pinceau(img, debut, p, taille, color2);
                    modif = true;
                break;

                case JaintPalette.CHOIX_FORME :
                    int index = ihm.jtp.getSelectedIndex();
                    img = ihm.jaint.lst_pict.get(index).get(ihm.jaint.lst_pos.get(index)).getBufferedImage();
                    switch(ihm.palette.getSelectedForm())
                    {
                        case JaintPalette.CARRE :
                            switch (ihm.palette.getSelectedForm2())
                            {
                                case JaintPalette.CARRE1 :
                                    ihm.fd.dessinRectangle(img,origine,p,taille,color1,null);
                                break;

                                case JaintPalette.CARRE3 :
                                    ihm.fd.dessinRectangle(img,origine,p,taille,color1,color2);
                                break;

                                case JaintPalette.CARRE2 :
                                    ihm.fd.dessinRectangle(img,origine,p,taille,null,color1);
                                break;
                            }
                        break;

                        case JaintPalette.LIGNE :
                                    ihm.fd.dessinLigne(img,origine,p,taille,color1);
                        break;

                        case JaintPalette.ROND :
                            switch (ihm.palette.getSelectedForm2())
                            {
                                case JaintPalette.ROND1 :
                                     ihm.fd.dessinOval(img,origine,p,taille,color1,null);
                                break;

                                case JaintPalette.ROND2 :
                                     ihm.fd.dessinOval(img,origine,p,taille,null,color1);
                                break;

                                case JaintPalette.ROND3 :
                                     ihm.fd.dessinOval(img,origine,p,taille,color1,color2);
                                break;
                            }
                        break;
                    }
                    modif = true;
                break;

                case JaintPalette.CHOIX_PIPETTE :
                    ihm.fd.pipette(p);
                    modif = true;
                break;
            }

    }

    /**
     * Methode permettant de specifier la taille des marges contenant les icones de rotation libre
     * @param margeW Largeur de la marge de gauche
     * @param margeH Hauteur de la marge de droite
     */
    public void setMarges(int margeW, int margeH) {
        this.margeH = margeH;
        this.margeW = margeW;
    }

}
