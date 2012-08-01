package Jaint;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JMenuItem;

/**
 * Objet permettant d'etre ajoute a un JPaletteItem
 * @author Jeff
 */
public class SubmenuItem extends JMenuItem
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4509624530119332732L;
	private Image img;
        private boolean enabled = true;
       
        private Dimension dimImg;
        private String toolTypeText;

    /**
     * Construit un SubmenuItem
     */
    public SubmenuItem()
    {
    	super();
        
        dimImg = pythagore(JPalette.RAYON_SUBMENU - JPalette.GRAND_RAYON);
    }
    
    /**
     * Construit un SubmenuItem avec une image
     * @param image Chemin de l'image
     */
    public SubmenuItem(String image)
    {
        
    	super();
       
        dimImg = pythagore(JPalette.RAYON_SUBMENU - JPalette.GRAND_RAYON);
    	img = new ImageIcon(image).getImage().getScaledInstance(dimImg.width, dimImg.height, Image.SCALE_SMOOTH);

    }

    /**
     * Construit un SubmenuItem avec une image
     * @param i L'image
     */
    public SubmenuItem(BufferedImage i)
    {
        super();
       
        dimImg = pythagore(JPalette.RAYON_SUBMENU - JPalette.GRAND_RAYON);
        img = (Image) i;
        img.getScaledInstance(dimImg.width, dimImg.height, Image.SCALE_SMOOTH);
    }
    
    /**
      Construit un SubmenuItem avec une image
     * @param image L'image
     */
    public void setImage(String image)
    {
    	img = new ImageIcon(image).getImage().getScaledInstance(dimImg.width, dimImg.height, Image.SCALE_SMOOTH);
    }
    
    /**
     * Renvoie l'image de ce SubmenuItem ou une image vide s'il n'en avait pas
     * @return
     */
    public Image getImage()
    {
        if(img != null)
            return new ImageIcon(img).getImage();
        else
            return new ImageIcon().getImage();
    }

    @Override
    public void setEnabled(boolean state)
    {
        
        enabled = state;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    private Dimension pythagore(int hypothenuse)
    {
        int res = (int) (hypothenuse / Math.sqrt(2));

        return new Dimension(res, res);

    }

    @Override
    public void setToolTipText(String text)
    {
        toolTypeText = text;
    }

    @Override
    public String getToolTipText()
    {
        return toolTypeText;
    }

    
    
}