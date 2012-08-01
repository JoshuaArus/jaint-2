package JaintListener;
/**
 * @(#)EcouteurPalette.java
 * Classe permettant d'afficher ou de desafficher une palette
 *
 * @author Jeff
 * @version 1.00 2009/11/16
 */


import Jaint.*;
import java.awt.event.*;
import java.awt.*;

public class EcouteurPalette extends MouseAdapter
{
	private JPalette palette;
        

        private int numBtn;
        private static int marge = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 30;
        static IHM ihm;

    /**
     *
     * @param p
     * @param ihm
     */
    public EcouteurPalette(JPalette p, IHM ihm)
    {
        super();
    	palette = p;
        EcouteurPalette.ihm = ihm;
    }
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
        numBtn = e.getButton();
        String os = System.getProperty("os.name").split(" ")[0].toLowerCase();

        if(numBtn == MouseEvent.BUTTON3 && (os.equals("windows") || os.equals("linux")))
    	{
           if(palette.isVisible())
            {
                    palette.setVisible(false);
                    palette.setState(false);

            }
            else if(!palette.anotherPaletteDisplayed())
            {

                if(!e.isControlDown() && ! e.isShiftDown() && palette instanceof JaintPalette) // si c'est la palette de dessin
                {
                    displayPalette(palette, e.getPoint());
                }
                else if(!e.isControlDown() && e.isShiftDown() && palette instanceof JaintPalette2) // palette Gestion
                {
                    JaintPalette2 j2 = (JaintPalette2) palette;

                    if(j2.getType() == JaintPalette2.GESTION)
                        displayPalette(palette, e.getPoint());
                }
                else if(e.isControlDown() && ! e.isShiftDown() && palette instanceof JaintPalette2) //palette option
                {
                    JaintPalette2 j2 = (JaintPalette2) palette;

                    if(j2.getType() == JaintPalette2.OPTION)
                        displayPalette(palette, e.getPoint());
                }

            }
    	}
        /*else if ((numBtn == MouseEvent.BUTTON1 || numBtn == MouseEvent.BUTTON3) && !(os.equals("windows") && os.equals("linux")))//click sous mac, sans modifier
        {
            if(palette.isVisible())
            {
                    palette.setVisible(false);
                    palette.setState(false);

            }
            else if(!palette.anotherPaletteDisplayed())
            {
                if(numBtn == MouseEvent.BUTTON3 && palette instanceof JaintPalette) // si c'est la palette de dessin
                {
                    displayPalette(palette, e.getPoint());
                }
                else if(numBtn == MouseEvent.BUTTON1 && e.isShiftDown() && palette instanceof JaintPalette2) // palette Gestion
                {
                    JaintPalette2 j2 = (JaintPalette2) palette;

                    if(j2.getType() == JaintPalette2.GESTION)
                        displayPalette(palette, e.getPoint());
                }
                else if(numBtn == MouseEvent.BUTTON1 && e.isMetaDown() && palette instanceof JaintPalette2) //palette option
                {
                    JaintPalette2 j2 = (JaintPalette2) palette;

                    if(j2.getType() == JaintPalette2.OPTION)
                        displayPalette(palette, e.getPoint());
                }
            }
        }*/
    }

    /**
     * Affiche la palette a l'endroit du clique
     * @param palette La palette
     * @param p Coordonnee où afficher le centre de la palette
     */
    public static void displayPalette(JPalette palette, Point p)
    {

            palette.setState(true);
                        int pointX = 0;
                        int pointY = 0;

                        if (ihm.jtp.getSelectedIndex() == -1)
                        {
                            pointX = p.x - (palette.getCenterPoint().x);
                            pointY = p.y - (palette.getCenterPoint().y);
                        }
                        else
                        {
                            pointX = p.x - (palette.getCenterPoint().x) + marge;
                            pointY = p.y - (palette.getCenterPoint().y) + marge;
                        }

    			if(pointX < 0)
    				pointX = 0;
    			else if(pointX + palette.getPreferredSize().width > Toolkit.getDefaultToolkit().getScreenSize().width)
    				pointX = Toolkit.getDefaultToolkit().getScreenSize().width - palette.getPreferredSize().width;



    			if(pointY < 0)
    				pointY = 0;
    			else if(pointY + palette.getPreferredSize().height > Toolkit.getDefaultToolkit().getScreenSize().height)
    				pointY = Toolkit.getDefaultToolkit().getScreenSize().height - palette.getPreferredSize().height;



    			palette.setBounds(pointX, pointY, palette.getPreferredSize().width, palette.getPreferredSize().height);
    			palette.setVisible(true);
    }
    

       
    
}