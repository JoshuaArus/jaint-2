package Jaint;

import javax.swing.JComponent;
import javax.swing.JSlider;

/**
 *
 * @author Jean-François
 */
public class SelectionForm extends JComponent
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8326855323284159702L;
	private JPalette palette;
    private int rayon;
    private JSlider slider = new JSlider();

    private int coordX;
    private int coordY;


    /**
     * Classe permettant d'afficher un slider
     * @param p
     */
    public SelectionForm(JPalette p)
    {
        super();
        palette = p;

        coordX = palette.getCenterPoint().x;
        coordY = palette.getCenterPoint().y;


        rayon = palette.getPetitRayon();
        setBounds(coordX - rayon, coordY - rayon, rayon * 2, rayon * 2);

        slider.setBounds(coordX - rayon, coordY, coordX + rayon, coordY);
    }

}
