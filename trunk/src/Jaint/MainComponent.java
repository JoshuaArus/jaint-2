package Jaint;

import JaintListener.EcouteurRotation;
import JaintListener.EcouteurDessin;
import JaintListener.EcouteurCCC;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

/**
 * Composant principal. Contient l'image à afficher et les bordures avec les icones permettant la rotation libre
 * @author Joshua
 */
public class MainComponent extends JScrollPane implements Observer
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3535236870405302991L;
	private JPanel main = new JPanel(new BorderLayout());
    Pictimage picture;
    IHM ihm;
    int marge = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 30;
    int marge2 = marge - marge/4;
    JLabel icon1 = new JLabel(new ImageIcon(new ImageIcon("./lib/Icons/rotation1.png").getImage().getScaledInstance(marge2, marge2,Image.SCALE_SMOOTH)));
    JLabel icon2 = new JLabel(new ImageIcon(new ImageIcon("./lib/Icons/rotation2.png").getImage().getScaledInstance(marge2, marge2,Image.SCALE_SMOOTH)));
    JLabel icon4 = new JLabel(new ImageIcon(new ImageIcon("./lib/Icons/rotation4.png").getImage().getScaledInstance(marge2, marge2,Image.SCALE_SMOOTH)));
    JLabel icon3 = new JLabel(new ImageIcon(new ImageIcon("./lib/Icons/rotation3.png").getImage().getScaledInstance(marge2, marge2,Image.SCALE_SMOOTH)));


    /**
     * Renvoi une instance de <code>MainComponent</code>
     * @param pic L'image à afficher
     * @param ihm Le composant parent
     */
    public MainComponent(Pictimage pic, IHM ihm)
    {
        super();
        this.ihm = ihm;
        
        
        JPanel nord = new JPanel(new BorderLayout());
        nord.setBackground(Color.WHITE);
        nord.add(icon1,BorderLayout.WEST);
        nord.add(icon2,BorderLayout.EAST);

        JPanel east = new JPanel(new BorderLayout());
        east.setBackground(Color.WHITE);
        
        JPanel west = new JPanel(new BorderLayout());
        west.setBackground(Color.WHITE);

        JPanel south = new JPanel(new BorderLayout());
        south.setBackground(Color.WHITE);
        south.add(icon3,BorderLayout.EAST);
        south.add(icon4,BorderLayout.WEST);


        nord.setPreferredSize(new Dimension(0,marge));
        south.setPreferredSize(new Dimension(0,marge));
        east.setPreferredSize(new Dimension(marge,0));
        west.setPreferredSize(new Dimension(marge,0));

        main.add(nord,BorderLayout.NORTH);
        main.add(south,BorderLayout.SOUTH);
        main.add(east,BorderLayout.EAST);
        main.add(west,BorderLayout.WEST);

    	picture = pic;

        picture.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.GRAY));

        EcouteurDessin ED = new EcouteurDessin(ihm);
        EcouteurCCC eCCC = new EcouteurCCC(ihm);
        picture.addMouseListener(ED);
        picture.addMouseMotionListener(ED);

        picture.setDessinListener(ED);
        picture.setCCCListener(eCCC);
        picture.addMouseListener(eCCC);
        picture.addMouseMotionListener(eCCC);

    	main.add(picture,BorderLayout.CENTER);

        this.setViewportView(main);
        
    	repaint();
    }

    /**
     * Permet de récuperer l'image contenu dans l'instance
     * @return L'image sous forme de <code>BufferedImage</code>
     */
    public BufferedImage getBufferedImage()
    {
        return picture.getBufferedImage();
    }

    /**
     * Permet de changer l'image affichée
     * @param img Nouvelle image à afficher
     */
    public void setBufferedImage(BufferedImage img)
    {
        picture.setBufferedImage(img);
    }

    /**
     * Fonction permettant d'ajoute les écouteurs sur les icones de rotation libre (MouseListener)
     */
    public void initListener()
    {
        icon1.addMouseListener(new EcouteurRotation(icon1,"1",marge2,ihm));
        icon2.addMouseListener(new EcouteurRotation(icon2,"2",marge2,ihm));
        icon3.addMouseListener(new EcouteurRotation(icon3,"3",marge2,ihm));
        icon4.addMouseListener(new EcouteurRotation(icon4,"4",marge2,ihm));
        icon1.addMouseMotionListener(new EcouteurRotation(icon1,"1",marge2,ihm));
        icon2.addMouseMotionListener(new EcouteurRotation(icon2,"2",marge2,ihm));
        icon3.addMouseMotionListener(new EcouteurRotation(icon3,"3",marge2,ihm));
        icon4.addMouseMotionListener(new EcouteurRotation(icon4,"4",marge2,ihm));
    }

    public void update(Observable t,Object o)
    {
        int index = ihm.jtp.getSelectedIndex();
        ArrayList<Pictimage> lst = ihm.jaint.lst_pict.get(index);
        picture.setBufferedImage(lst.get(ihm.jaint.lst_pos.get(index)).getBufferedImage());
    }
}