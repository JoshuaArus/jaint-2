package Jaint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.ImageIcon;

/**
 * Classe créant un objet comme <code>JaintPalette</code> mais avec des caractéristiques différentes
 * @author Jeff
 */
public class JaintPalette2 extends JPalette
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2715196839714741705L;
	/**
     * Code pour la palette d'option
     */
    public static final int OPTION = 0;
    /**
     * Code pour la palette de gestion
     */
    public static final int GESTION = 1;

    private Image defaultImage;
    private Image imgCenter2;
    private int rayon;
    private int typePalette;
    private JaintPalette2 palette;

    //vrai si la palette s'est redessiné lors d'un clic droit
    private boolean dessinClic = false;
    

    /**
     * Construit la palette
     * @param angle Angle du début du premier JPaletteItem
     * @param img Chemin de l'image du centre
     * @param img2 Chemin de la deuxième image du centre
     * @param i IHM principale
     * @param type Type de palette (GESTION ou OPTION)
     */
    public JaintPalette2(int angle, String img, String img2, IHM i, int type)
    {
        super(angle, i);
        
        rayon = getPetitRayon();

        defaultImage = new ImageIcon(img).getImage().getScaledInstance(rayon * 2, rayon * 2, Image.SCALE_SMOOTH);
        imgCenter2 = new ImageIcon(img2).getImage().getScaledInstance(rayon * 2, rayon * 2, Image.SCALE_SMOOTH);
        typePalette = type;

        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(e.getButton() == MouseEvent.BUTTON1 && onCenter())
                {
                                      
                  if(palette != null)
                  {
                      setVisible(false);
                      Point p = getLocation();
                      palette.setBounds(p.x, p.y, palette.getPreferredSize().width, palette.getPreferredSize().height);
                      JaintPalette2.this.dessinClic = true;
                      palette.setVisible(true);
                  }

                }
            }

        });

    }

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D graph = (Graphics2D ) g;

        transparenceOn(graph);
        super.paintComponent(graph);
        transparenceOff(graph);

    	graph.setClip(new Ellipse2D.Double(coordX - rayon, coordY - rayon, rayon * 2, rayon * 2));


        if(onCenter())
            graph.drawImage(imgCenter2, coordX - rayon, coordY - rayon, this);
         else if(dessinClic)
         {
             graph.drawImage(imgCenter2, coordX - rayon, coordY - rayon, this);
             dessinClic = false;
         }
         else
            graph.drawImage(defaultImage, coordX - rayon, coordY - rayon, this);


        graph.setColor(Color.BLACK);
        graph.drawOval(coordX - rayon, coordY - rayon, rayon * 2, rayon * 2);

    }

    /**
     * Renvoie le type de la palette (GESTION ou OPTION)
     * @return
     */
    public int getType()
    {
        return typePalette;
    }

    /**
     * Indique la palette qui permet d'être affiché en cliquant sur le centre
     * @param jp La palette
     */
    public void addPalette(JaintPalette2 jp)
    {
        if(getType() == JaintPalette2.GESTION && jp.getType() == JaintPalette2.OPTION)
            palette = jp;
        else if(getType() == JaintPalette2.OPTION && jp.getType() == JaintPalette2.GESTION)
            palette = jp;
        else
            throw new IllegalArgumentException("Les deux palettes sont du meme type");
    }



}
