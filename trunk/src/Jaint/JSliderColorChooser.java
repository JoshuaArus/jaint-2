package Jaint;
/**
 * @(#)Text1.java
 * Cree un selecteur couleur pure
 *
 * @author Jeff
 * @version 1.00 2009/12/8
 */



import JaintPlug.Teinte;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;


public class JSliderColorChooser extends JComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5700438309059387793L;
	private int largeur;
	private int hauteur;
	
	private BufferedImage degrade;
       
	private Image slider;
	
	private int sliderX;
	
       private JaintPalette palette;
       private Teinte teinte;


       /**
        * Taille en pixel de la zone de gris dans le selecteur
        */
       public static final int FIN_GRIS = 10;


    
       /**
        * Construit le selecteur de couleur
        * @param width Largeur du selecteur
        * @param height Hauteur du degrade du selecteur
        * @param obj Une JPalette ou un plugin Teinte (utilise dans ses deux classes)
        */
       public JSliderColorChooser(int width, int height, Object obj)
    {
    	super();

        if(obj instanceof JaintPalette)
            palette = (JaintPalette) obj;
        else if(obj instanceof Teinte)
            teinte = (Teinte) obj;

        largeur = width;
    	hauteur = height;

        degrade = new BufferedImage(largeur, 1, BufferedImage.TYPE_INT_RGB);

        slider = new ImageIcon("./lib/Icons/Palette/Couleur/curseur.png").getImage().getScaledInstance(width / 13, height, Image.SCALE_SMOOTH);
    	
    	this.addMouseMotionListener(new MouseMotionListener()
        {

            public void mouseDragged(MouseEvent e) 
            {               

                if(e.getButton() != MouseEvent.BUTTON3)
                 {
                     int x = e.getX();
                     
                     if(x > 0 && x < largeur)
                     {
                         setSliderX(x);

                         
                         if(palette != null)
                         {
                            Color courante;

                             if(palette.getBoutonColor()[0].isChecked())
                                 courante = palette.getFirstColor();
                             else
                                 courante = palette.getSecondColor();


                             float[] hsb = Color.RGBtoHSB(courante.getRed(), courante.getGreen(), courante.getBlue(), null);

                             ControleSpinner.replacerPointille(courante, hsb, palette);
                             Color nouvelle = new Color(degrade.getRGB(x, 0));

                             JSpinner[] rvb = palette.getSpinner();

                             rvb[0].setValue(nouvelle.getRed());
                             rvb[1].setValue(nouvelle.getGreen());
                             rvb[2].setValue(nouvelle.getBlue());

                             palette.repaint();
                         }
                         else if(teinte != null)
                         {
                             teinte.setJsValue();
                             
                         }

                         repaint();
                     }
                 }             
                
            }

            public void mouseMoved(MouseEvent e) 
            {
                              
            }


      });


      this.addMouseListener(new MouseListener()
      {
          public void mouseClicked(MouseEvent e)
                    {
                     	;
                    }
			 public void mouseEntered(MouseEvent e)
			 {
                             /* Cette partie de code permet d'enlever la ligne de pointille lorsqu'on quitte la zone du degrade
                              * On test par quel cote on rentre. Si on rentre par le bas du JSliderColorChooser, il n'y avait pas de pointille de dessine
                              * Ce n'est donc pas necessaire de redessiner la palette
                              * */

                             if(palette != null)
                             {
                                 int y =  e.getY();

                                 if(y < hauteur)
                                 {
                                     palette.setMouse(e.getX(), y);
                                     palette.repaint();
                                 }
                             }

                             
                             
			 }
			 public void mouseExited(MouseEvent e)
			 {
			 	;
			 }
			public  void mousePressed(MouseEvent e)
			 {
                              if(e.getButton() != MouseEvent.BUTTON3)
                              {
                                int x = e.getX();

                                if(x > 0 && x < largeur)
                                {
                                     setSliderX(e.getX());
                                     if(palette != null)
                                        palette.repaint();
                                     else if(teinte != null)
                                     {
                                         teinte.setJsValue();
                                         repaint();
                                     }
                                }
                              }
                              else if(palette != null)
                              {
                                  palette.setVisible(false);
                                  palette.setState(false);
                              }

                              

			 }
			 public void mouseReleased(MouseEvent e)
			 {

			 }
      });
    	
    	
    	sliderX = largeur / 16;
    	
    }

    

    /**
     * Renvoie l'emplacement du slider
     * @return La coordonnee x du slider
     */
    public int getSliderX()
    {
        return sliderX;
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        

        Color hsb = new Color(127, 127, 127);
        Graphics graphDeg = degrade.getGraphics();

        g.setColor(hsb);
        graphDeg.setColor(hsb);
        g.fillRect(0, 0, FIN_GRIS, hauteur);
        graphDeg.drawLine(0, 0, FIN_GRIS, 0);

        for(int i = FIN_GRIS; i < largeur; i++)
        {
            hsb = Color.getHSBColor((float) (i - FIN_GRIS) / largeur, 1f, 1f);
            g.setColor(hsb);
            graphDeg.setColor(hsb);
            g.drawLine(i, 0, i, hauteur);
            graphDeg.drawLine(i, 0, i, 0);
        }

        g.setColor(Color.BLACK);
        g.drawLine(0, 0, largeur, 0);
        g.drawLine(0, hauteur, largeur, hauteur);
                
    	g.drawImage(slider, (int) (sliderX - largeur / 30), hauteur, this);
    }


    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(largeur, hauteur * 2);
    }
    
      
    /**
     * Place le slider du selecteur a l'endroit indique
     * @param x Coordonnee x
     */
    public void setSliderX(int x)
    {
    	sliderX = x;
    }
    
    

    /**
     * Renvoie la couleur pure où se trouve le slider
     * @return
     */
    public Color getColor()
    {
        Color res;
        try
        {
            res = new Color(degrade.getRGB(sliderX, 0));
        }
        catch(IndexOutOfBoundsException e)
        {
            res = new Color(degrade.getRGB(FIN_GRIS, 0));
            setSliderX(FIN_GRIS);
        }
        
        
        return  res;
    }

    /**
     * Renvoie le degrade contenant les couleurs pures
     * @return Le degrade
     */
    public BufferedImage getDegrade()
    {
        return degrade;
    }
   
}