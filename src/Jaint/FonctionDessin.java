package Jaint;

import JaintPlug.Fonction;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Librairie des fonctions utilisees par les differents outils de dessin
 * @author Joshua
 */
public class FonctionDessin
{
    IHM ihm;
    
    /**
     * Constructeur pour avoir une reference de l'instance de l'IHM
     * @param ihm Interface principale
     */
    public FonctionDessin(IHM ihm)
    {
        this.ihm = ihm;
    }
    
    /**
     * Fonction pour dessiner un trait au pinceau (arrondi, pas carre) entre 2 points
     * @param img L'image a modifier
     * @param debut La coordonnee du debut du trait
     * @param fin La coordonnee de fin du trait
     * @param taille Epaisseur du trait
     * @param color Couleur du trait
     */
    public void tracer_pinceau(BufferedImage img,Point debut, Point fin, int taille, Color color)
    {
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);

        g.setStroke(new BasicStroke(taille, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        g.drawLine(debut.x+taille/2, debut.y+taille/2, fin.x+taille/2, fin.y+taille/2);
        
        g.dispose();

        ((MainComponent) ihm.jtp.getSelectedComponent()).setBufferedImage(img);
    }

    /**
     * Fonction utilisee pour remplir une zone avec une couleur (pot de peinture)
     * @param img L'image a modifier
     * @param a Point d'origine a colorier. Les points voisins seront evalues puis traites si necessaire
     * @param tolerance Tolerance permettant de specifier la precision du pot de peinture
     * @param c Couleur a appliquer
     */
    public void remplissage(BufferedImage img, Point a, int tolerance,Color c)
	{
                ihm.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		BufferedImage img_new = Fonction.cloneBuff(img);
                if (tolerance == 255)
                {
                    Graphics g = img_new.getGraphics();
                    g.setColor(c);
                    g.fillRect(0,0,img.getWidth(),img.getHeight());
                }
                else
                {
                    ArrayList<Point> afaire = new ArrayList<Point>();	// liste des points a traiter
                    ArrayList<Point> fait = new ArrayList<Point>();	// liste des points deja traites
                    int c_base = img_new.getRGB(a.x, a.y);		// couleur du point de depart de la proliferation pour calcule des couleurs semblables
                    afaire.add(a);					// point de depart est rajouter dans la liste a faire

                    while(!afaire.isEmpty())
                    {					// debut de la proliferation

                            Point temps = (Point) afaire.get(0);	// recuperation du premier point a faire
                            afaire.remove(0);						//
                            if (!fait.contains(temps))
                            {
                                img_new.setRGB(temps.x, temps.y, c.getRGB());	// mise en place de la couleur sur le point
                                fait.add(temps);								// ajout du point dans le vecteur des points fait

                                Point droite = new Point(temps.x+1,temps.y);
                                if(droite.x < img.getWidth() && Fonction.couleurIdentique(c_base, img_new.getRGB(droite.x, droite.y), tolerance))				// si ce pixel est compris dans l'image
                                    afaire.add(droite);	// et que sa couleur est proche de celle du point de depart

                                // on fait pareil avec le pixel gauche
                                Point gauche = new Point(temps.x-1,temps.y);
                                if(gauche.x >= 0 && Fonction.couleurIdentique(c_base, img_new.getRGB(gauche.x, gauche.y), tolerance))
                                    afaire.add(gauche);

                                // le pixel dessous
                                Point dessous = new Point(temps.x,temps.y+1);
                                if(dessous.y < img.getHeight() && Fonction.couleurIdentique(c_base, img_new.getRGB(dessous.x, dessous.y), tolerance))
                                    afaire.add(dessous);

                                //le pixel dessus
                                Point dessus = new Point(temps.x,temps.y-1);
                                if(dessus.y >= 0 && Fonction.couleurIdentique(c_base, img_new.getRGB(dessus.x, dessus.y), tolerance))
                                    afaire.add(dessus);

                            }
                    }
                }
               
                ihm.jaint.applyEffect(img_new);
                ihm.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
          }

        /**
         * Fonction permettant de recuperer la couleur du pixel selectionne
         * @param p Point où recuperer la couleur
         */
        public void pipette(Point p)
    {
        BufferedImage img = ihm.jaint.getImageCourante();
        Color res = new Color(img.getRGB(p.x, p.y));

        if(ihm.palette.getBoutonColor()[0].isChecked())
            ihm.palette.setFirstColor(res);
        else
            ihm.palette.setSecondColor(res);

        ihm.palette.repaint();
    }

    /**
     * Fonction permettant de dessiner un rectangle
     * @param img Image a modifier
     * @param debut Origine du rectangle
     * @param fin Fin du rectangle
     * @param taille Epaisseur du trait du rectangle
     * @param color1 Couleur du bord du rectangle (null pour pas de bordure)
     * @param color2 Couleur du centre du rectangle (null pour un centre transparent)
     */
    public void dessinRectangle(BufferedImage img, Point debut, Point fin, int taille, Color color1, Color color2)
    {
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        if (color1 == null)
        {
            g.setColor(color2);
            g.fillRect(Math.min(debut.x, fin.x), Math.min(debut.y, fin.y),Math.abs(debut.x-fin.x),Math.abs(debut.y-fin.y));
        }
        else if (color2 == null)
        {
            g.setColor(color1);
            g.setStroke(new BasicStroke(taille));
            g.drawRect(Math.min(debut.x, fin.x)+taille/2, Math.min(debut.y, fin.y)+taille/2,Math.abs(debut.x-fin.x)-taille,Math.abs(debut.y-fin.y)-taille);
        }
        else
        {
            g.setColor(color2);
            g.fillRect(Math.min(debut.x, fin.x), Math.min(debut.y, fin.y),Math.abs(debut.x-fin.x),Math.abs(debut.y-fin.y));
            g.setStroke(new BasicStroke(taille));
            g.setColor(color1);
            g.drawRect(Math.min(debut.x, fin.x)+taille/2, Math.min(debut.y, fin.y)+taille/2,Math.abs(debut.x-fin.x)-taille,Math.abs(debut.y-fin.y)-taille);
        }


        g.dispose();

        ((MainComponent) ihm.jtp.getSelectedComponent()).setBufferedImage(img);
    }

    /**
     * Fonction pour dessiner une ellipse (marche aussi pour les ronds)
     * @param img Image a modifier
     * @param debut Origine de l'ellipse
     * @param fin Fin de l'ellipse
     * @param taille Epaisseur du trait
     * @param color1 Couleur exterieur de l'ellipse (null pour pas de bordure)
     * @param color2 Couleur interieur de l'ellipse (null pour un centre transparent)
     */
    public void dessinOval(BufferedImage img, Point debut, Point fin, int taille, Color color1, Color color2)
    {
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        if (color1 == null)
        {
            g.setColor(color2);
            g.fillOval(Math.min(debut.x, fin.x), Math.min(debut.y, fin.y),Math.abs(debut.x-fin.x),Math.abs(debut.y-fin.y));
        }
        else if (color2 == null)
        {
            g.setColor(color1);
            g.setStroke(new BasicStroke(taille));
            g.drawOval(Math.min(debut.x, fin.x)+taille/2, Math.min(debut.y, fin.y)+taille/2,Math.abs(debut.x-fin.x)-taille,Math.abs(debut.y-fin.y)-taille);
        }
        else
        {
            g.setColor(color2);
            g.fillOval(Math.min(debut.x+1, fin.x+1), Math.min(debut.y+1, fin.y+1),Math.abs(debut.x-fin.x)-2,Math.abs(debut.y-fin.y)-2);
            g.setStroke(new BasicStroke(taille));
            g.setColor(color1);
            g.drawOval(Math.min(debut.x, fin.x)+taille/2, Math.min(debut.y, fin.y)+taille/2,Math.abs(debut.x-fin.x)-taille,Math.abs(debut.y-fin.y)-taille);
        }


        g.dispose();

        ((MainComponent) ihm.jtp.getSelectedComponent()).setBufferedImage(img);
    }

    /**
     * Fonction pour dessiner une ligne entre 2 points
     * @param img Image a modifier
     * @param debut Origine de la ligne
     * @param fin Fin de la ligne
     * @param taille Epaisseur de la ligne
     * @param color1 Couleur de la ligne
     */
    public void dessinLigne(BufferedImage img, Point debut, Point fin, int taille, Color color1)
    {
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color1);
        g.setStroke(new BasicStroke(taille, BasicStroke.CAP_BUTT, BasicStroke.CAP_BUTT));
        g.drawLine(debut.x,debut.y,fin.x,fin.y);
        g.dispose();
        ((MainComponent) ihm.jtp.getSelectedComponent()).setBufferedImage(img);
    }

    
}
