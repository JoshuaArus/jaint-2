package JaintListener;

import Jaint.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

/**
 *
 * @author Joshua
 */
public class EcouteurCCC implements MouseListener, MouseMotionListener
{
    IHM ihm;
    private int margeH = 0;
    private int margeW = 0;
    private int mode = -1;
    private Point debut = new Point(1,1);
    private Point fin = new Point(2,2);
    private boolean button;

    /**
     *
     * @param ihm Composant parent
     */
    public EcouteurCCC(IHM ihm)
    {
        this.ihm = ihm;
    }

    public void mouseClicked(MouseEvent e)
    {

    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            mode = ihm.jaint.mode;
            button = true;

            if (mode == 0 || mode == 1 || mode == 4)
            {
                debut = e.getPoint();
                debut = new Point(Math.max((debut.x - margeW),0), Math.max((debut.y - margeH),0));
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(button)
        {
            fin = e.getPoint();
            fin = new Point(Math.max((fin.x - margeW),0), Math.max((fin.y - margeH),0));
            if (mode == 0 && !cmp(debut,fin))
            {
                ihm.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                stocker(ihm.jaint.cut, debut, fin);
                couper(debut,fin);
                ihm.jaint.copy.setPicture(null);
                ((MainComponent)ihm.jtp.getSelectedComponent()).update(null, null);
                //On met a jour les icone du menu des plugin
                ihm.initPluginsMenu();
                ihm.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            else if (mode == 1 && !cmp(debut,fin))
            {
                ihm.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                stocker(ihm.jaint.copy, debut, fin);
                ihm.jaint.cut.setPicture(null);
                ((MainComponent)ihm.jtp.getSelectedComponent()).update(null, null);
                ihm.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            else if (mode == 2)
            {
                ihm.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                appliquer(ihm.jaint.cut,fin);
                ihm.jaint.cut.setPicture(null);
                ihm.jaint.applyEffect(((MainComponent)ihm.jtp.getSelectedComponent()).getBufferedImage());
                //On met a jour les icone du menu des plugin
                ihm.initPluginsMenu();
                ihm.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            else if (mode == 3)
            {
                ihm.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                appliquer(ihm.jaint.copy,fin);
                ihm.jaint.applyEffect(((MainComponent)ihm.jtp.getSelectedComponent()).getBufferedImage());
                //On met a jour les icone du menu des plugin
                ihm.initPluginsMenu();
                ihm.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            else if (mode == 4 && !cmp(debut,fin))
            {
                ihm.setCursor(new Cursor(Cursor.WAIT_CURSOR));

                Calque temp = new Calque(null, new Point(0,0));
                stocker(temp, debut, fin);
                ((MainComponent)ihm.jtp.getSelectedComponent()).update(null, null);
                ihm.addTab(temp.getBufferedImage(), 0);

                //On met a jour les icone du menu des plugin
                ihm.initPluginsMenu();
                ihm.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            button = false;
            ihm.jaint.mode = -1;
            ihm.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            debut = new Point(1,1);
            fin = new Point(2,2);
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e)
    {
        if(button)
        {
            fin = e.getPoint();
            fin = new Point(fin.x - margeW, fin.y - margeH);
            if (mode == 0 || mode == 1 || mode == 4)
            {
                dessinTraits(debut,fin);
            }
            else if (mode == 2)
                appliquer(ihm.jaint.cut,fin);
            else if (mode == 3)
                appliquer(ihm.jaint.copy,fin);
        }
    }

    public void mouseMoved(MouseEvent e) 
    {
        mode = ihm.jaint.mode;
        if (mode == 2 || mode == 3)
        {
            fin = e.getPoint();
            fin = new Point(fin.x - margeW, fin.y - margeH);

            if (mode == 2)
            {
                appliquer(ihm.jaint.cut,fin);
            }
            else if (mode == 3)
            {
                appliquer(ihm.jaint.copy,fin);
            }
        }
    }

    /**
     * Définit la taille des marges contenant les icones de rotation libre
     * @param margeW Largeur de la marge de gauche
     * @param margeH Hauteur de la marge du haut
     */
    public void setMarges(int margeW, int margeH) {
        this.margeH = margeH;
        this.margeW = margeW;
    }

    /**
     * Permet de copier une partie de l'image dans un calque
     * @param calque Calque permettant de stocker temporairement une image
     * @param debut Origine de l'image à copier
     * @param fin Fin de l'image à copier
     */
    public void stocker(Calque calque, Point debut, Point fin)
    {
        int index = ihm.jtp.getSelectedIndex();
        BufferedImage src = ihm.jaint.lst_pict.get(index).get(ihm.jaint.lst_pos.get(index)).getBufferedImage();

        BufferedImage bi = new BufferedImage(Math.abs(fin.x-debut.x), Math.abs(fin.y-debut.y), src.getType());

        for (int i = 0 ; i < bi.getWidth(); i++)
        {
            for (int j = 0 ; j < bi.getHeight(); j++)
            {
                bi.setRGB(i, j, src.getRGB(i+Math.min(debut.x,fin.x), j+Math.min(debut.y, fin.y)));
            }
        }
        
        calque.setPicture(bi);
    }

    /**
     * Dessine le calque spécifié sur l'image courant avec pour origine le point P
     * @param calque Calque contenant l'image
     * @param p Coordonnée de l'origine du calque
     */
    public void appliquer(Calque calque, Point p)
    {
        int index = ihm.jtp.getSelectedIndex();
        BufferedImage img = ihm.jaint.lst_pict.get(index).get(ihm.jaint.lst_pos.get(index)).getBufferedImage();
        BufferedImage temp = calque.getBufferedImage();
        
        //if((p.x > 0 && p.y > 0)||(p.x<(img.getWidth()-temp.getWidth()) && p.y < (img.getHeight()-temp.getHeight())))
        //{
            Graphics g = img.getGraphics();
            g.drawImage(temp, p.x, p.y, ihm);

            ((MainComponent) ihm.jtp.getSelectedComponent()).setBufferedImage(img);
        //}
    }

    /**
     * Fonction dessinant un rectangle sur l'image courante pour indiquer à l'utilisateur la zone qui sera copiée dans le calque
     * @param debut Origine du rectangle
     * @param fin Fin du rectangle
     */
    public void dessinTraits(Point debut, Point fin)
    {
        int index = ihm.jtp.getSelectedIndex();
        BufferedImage bi = ihm.jaint.lst_pict.get(index).get(ihm.jaint.lst_pos.get(index)).getBufferedImage();
        Graphics g = bi.getGraphics();
        g.setColor(Color.BLACK);
        g.drawRect(Math.min(debut.x, fin.x), Math.min(debut.y, fin.y),Math.abs(debut.x-fin.x),Math.abs(debut.y-fin.y));
        g.setColor(Color.WHITE);
        g.drawRect(Math.min(debut.x+1, fin.x+1), Math.min(debut.y+1, fin.y+1),Math.abs(debut.x-fin.x)-2,Math.abs(debut.y-fin.y)-2);
        ((MainComponent) ihm.jtp.getSelectedComponent()).setBufferedImage(bi);
    }

    /**
     * Dessine un rectangle plein blanc
     * @param debut Origine
     * @param fin Fin
     */
    public void couper(Point debut, Point fin) {
        int index = ihm.jtp.getSelectedIndex();
        BufferedImage bi = ihm.jaint.lst_pict.get(index).get(ihm.jaint.lst_pos.get(index)).getBufferedImage();
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(Math.min(debut.x, fin.x), Math.min(debut.y, fin.y),Math.abs(debut.x-fin.x),Math.abs(debut.y-fin.y));
        ihm.jaint.applyEffect(bi);
    }
    /**
     * Compare 2 points
     * @param debut Point A
     * @param fin Point B
     * @return Vrai si les 2 points sont les mêmes, faux sinon
     */
    public boolean cmp(Point debut, Point fin) {
        return (debut.x == fin.x && debut.y == fin.y);
    }
}
