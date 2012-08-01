package Jaint;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;

/**
 * Composant place dans les onglets de l'interface principale
 * @author Joshua
 */
public class TabComponent extends JPanel implements Observer
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5054479986044048895L;
	JPanel component = new JPanel(new BorderLayout());
    Pictimage miniature;
    private JLabel name;
    JButton exit;
    int height = 0;
    int width = 0;
    JPanel top = new JPanel(new BorderLayout());
    JPanel blank = (new JPanel());
    JPanel centre = new JPanel();
    private IHM ihm = null;

    /**
     *
     * @param picture Image a afficher
     * @param display_name Nom a afficher en dessous de l'image
     * @param ihm Reference vers l'interface principale
     */
    public TabComponent(Pictimage picture, String display_name,IHM ihm)
    {
        this.ihm = ihm;

        setRatio(picture);

    	miniature = new Pictimage(picture.getBufferedImage().getScaledInstance(width,height,Image.SCALE_SMOOTH), picture.getPath());
        miniature.setPreferredSize(new Dimension(width,height));
    	name = new JLabel(display_name,JLabel.CENTER);

        ImageIcon quit = new ImageIcon(new ImageIcon("./lib/Icons/close.png").getImage().getScaledInstance(10,10,Image.SCALE_SMOOTH));
        
        exit = new JButton(quit);
        exit.setPreferredSize(new Dimension(15,15));
        top.add(exit,BorderLayout.EAST);
        
        blank.setPreferredSize(new Dimension(2,2));
        top.add(blank,BorderLayout.NORTH);
        top.add(blank,BorderLayout.SOUTH);
        
    	component.add(top,BorderLayout.NORTH);
    	component.add(name,BorderLayout.SOUTH);
        centre.add(miniature);
    	component.add(centre,BorderLayout.CENTER);
    	component.setOpaque(false);
    	top.setOpaque(false);
        centre.setOpaque(false);
        miniature.setOpaque(false);
    	blank.setOpaque(false);
    	this.setOpaque(false);
    	this.add(component);

        exit.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {

                    TabComponent.this.ihm.close(TabComponent.this.ihm.jtp.indexOfTabComponent(TabComponent.this));
            }
        });
    }
    
    /**
     * Renvoi la chaine affichee en dessous de l'image
     * @return Nom de l'image
     */
    public String getLabel()
    {
        return name.getText();
    }

    /**
     * Definit la chaint affichee en dessous de l'image
     * @param s Nom a utiliser
     */
    public void setLabel(String s)
    {
        name.setText(s);
    }

    public void update(Observable t,Object o)
    {
        int index = ihm.jtp.getSelectedIndex();
        
        ArrayList<Pictimage> lst = ihm.jaint.lst_pict.get(index);
        Pictimage image = lst.get(ihm.jaint.lst_pos.get(index));
        setRatio(image);
    	miniature.setBufferedImage(image.getBufferedImage().getScaledInstance(width,height,Image.SCALE_SMOOTH),image.getType());
        miniature.setSize(new Dimension(width,height));
        centre.setSize(new Dimension(width,height));
        if (!name.getText().endsWith("  *"))
            name.setText(this.getLabel() + "  *");
    }

    /**
     * Renvoi l'image affichee dans l'onglet
     * @return L'image affichee
     */
    public Pictimage getPictimage()
    {
        return miniature;
    }

    /**
     * Definit le ratio pour le redimensionnement de l'image afin de rester dans les limites de l'onglet
     * @param picture Image a afficher
     */
    public void setRatio(Pictimage picture)
    {
        int hauteur = picture.getSize().height;
        int largeur = picture.getSize().width;

        if (hauteur > largeur)
        {
            height = 95;
            width = (int) (largeur*95/hauteur)+1;
        }
        else if (hauteur == largeur)
        {
            width = 95;
            height = 95;
        }
        else
        {
            width = 95;
            height = (int) (hauteur*95/largeur)+1;
        }
    }

    @Override
    public Dimension getPreferredSize()
    {
        Dimension dimNord = top.getSize();
        Dimension dimCentre = centre.getSize();
        Dimension dimSud = name.getSize();

        return new Dimension((int)Math.max(dimCentre.getWidth(),dimSud.getWidth())+10,(int)(dimCentre.getHeight()+dimNord.getHeight()+dimSud.getHeight()+15));
    }
}