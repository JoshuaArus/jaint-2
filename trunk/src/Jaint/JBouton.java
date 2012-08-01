package Jaint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Classe creant des boutons similaires a des radio boutons
 * @author Jeff
 */
public class JBouton extends JButton
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3293565158233934699L;
	private boolean check = false;

    /**
     * Construit un bouton
     * @param title titre du bouton
     */
    public JBouton(String title)
    {
        super(title);
    }

    /**
     * Construit un bouton avec une icone
     * @param img Icon du bouton
     */
    public JBouton(Icon img)
    {
        super(img);
    }

    public JBouton()
    {
        super();
    }

    /**
     * permet de dire que le bouton est selectionnee
     */
    public void check()
    {
        check = true;
    }

    /**
     * permet de dire que le bouton est deselectionnee
     */
    public void uncheck()
    {
        check = false;
    }

    /**
     * Teste si le bouton est selectionne
     * @return Resultat du test
     */
    public boolean isChecked()
    {
        return check;
    }

    /**
     * place la couleur par defaut des boutons du selecteur de couleur
     * @param btn Le tableau comportant les deux boutons
     */
    public static void setColorIcon(JBouton[] btn)
    {
        Dimension dimBtn = btn[0].getSize();
        dimBtn.height = (Math.max(1,dimBtn.height));
        dimBtn.width = (Math.max(1,dimBtn.width));
        btn[0].setIcon(new ImageIcon(getIconColor(Color.BLACK, dimBtn)));
        btn[1].setIcon(new ImageIcon(getIconColor(Color.WHITE, dimBtn)));
    }

    /**
     * Change la couleur du bouton selectionnee
     * @param btn Le tableau de bouton
     * @param color La couleur a appliquer
     */
    public static void setColorIcon(JBouton[] btn, Color color)
    {
        Dimension dimBtn = btn[0].getSize();
        
        BufferedImage colorBtn = getIconColor(color, dimBtn);

        if(btn[0].isChecked())
        {
            btn[0].setIcon(new ImageIcon(colorBtn));
        }
        else
        {
            btn[1].setIcon(new ImageIcon(colorBtn));
        }
    }

    private static BufferedImage getIconColor(Color c, Dimension dim)
    {
        BufferedImage res = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
        Graphics graphBtn = res.getGraphics();
        graphBtn.setColor(c);
        graphBtn.fillRect(0, 0, dim.width, dim.height);

        return res;
    }
}
