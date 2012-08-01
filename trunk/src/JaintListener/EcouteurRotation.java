package JaintListener;

import Jaint.*;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Joshua
 */
public class EcouteurRotation implements MouseListener, MouseMotionListener
{
    JLabel icon;
    static BufferedImage bi;
    static BufferedImage bi2;
    String num_icon = "";
    int marge = 0;
    IHM ihm;
    static Point debut;
    static Boolean button = null;

    /**
     * 
     * @param jl JLabel contenant l'une des 4 icones de la rotation
     * @param i Numero du JLabel (permet de savoir lequel a ete active)
     * @param m Taille des marges pour avoir une miniature des icones de rotation a la bonne taille
     * @param ihm Instance de l'interface principale
     */
    public EcouteurRotation(JLabel jl,String i,int m,IHM ihm)
    {
        icon = jl;
        num_icon = i;
        marge = m;
        this.ihm = ihm;
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        button = false;
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            debut = e.getPoint();
            button = true;
            int index = ihm.jtp.getSelectedIndex();
            bi = ihm.jaint.lst_pict.get(index).get(ihm.jaint.lst_pos.get(index)).getBufferedImage();
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (button)
        {
            modif(e.getPoint(),AffineTransformOp.TYPE_BILINEAR);
            ihm.jaint.applyEffect(((MainComponent)ihm.jtp.getSelectedComponent()).getBufferedImage());
            ihm.initPluginsMenu();
            button = false;
        }
        debut = null;
    }

    public void mouseEntered(MouseEvent e) {
        icon.setIcon(new ImageIcon(new ImageIcon("./lib/Icons/rotation"+num_icon+"_2.png").getImage().getScaledInstance(marge, marge, Image.SCALE_SMOOTH)));
    }

    public void mouseExited(MouseEvent e) {
        icon.setIcon(new ImageIcon(new ImageIcon("./lib/Icons/rotation"+num_icon+".png").getImage().getScaledInstance(marge, marge, Image.SCALE_SMOOTH)));
    }

    /**
     * Fonction effectuant une rotation de l'image passee en parametre.
     * @param image Image a pivoter
     * @param scale Angle de la rotation (en Radian)
     * @param type Type de rotation a utiliser (cf <code>AffineTransformOp</code>)
     * @return Nouvelle image contenant le resultat de l'operation
     */
    public BufferedImage rotate(BufferedImage image, double scale,int type)
	{
		AffineTransform tx = new AffineTransform();
		tx.rotate(scale, image.getWidth()/2, image.getHeight()/2);

		AffineTransformOp op = new AffineTransformOp(tx, type);
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics g = bufferedImage.getGraphics();
                g.drawImage(op.filter(image, null),0,0,ihm);
                //BufferedImage bufferedImage = op.filter(image,null);

		return bufferedImage;
	}

    public void mouseDragged(MouseEvent e) {
        if (button)
        {
            Point fin = e.getPoint();
            modif(fin,AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        }
    }

    public void mouseMoved(MouseEvent e) {

    }

    /**
     * Fonction calculant l'angle de la rotation selon la position de la souris et appliquant le resultat de la rotation au MainComponent
     * @param fin Position de la souris
     * @param type Type de rotation a utiliser (cf <code>AffineTransformOp</code>)
     */
    public void modif(Point fin, int type)
    {
        ihm.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        Point centre = new Point(bi.getWidth()/2,bi.getHeight()/2);

        double angleDepart = Math.atan2(debut.x-centre.x, centre.y - debut.y);
        double angleFin = Math.atan2(fin.x-centre.x, centre.y - fin.y);

        if (angleDepart < 0 && angleFin > 0)
            angleDepart+=Math.PI*2;
        else if (angleFin < 0 && angleDepart > 0)
            angleFin+=Math.PI*2;

        double angle = angleDepart - angleFin;

        bi2 = rotate(bi, angle,type);
        int index = ihm.jtp.getSelectedIndex();
        ((MainComponent)ihm.jtp.getComponentAt(index)).setBufferedImage(bi2);
        ((MainComponent)ihm.jtp.getComponentAt(index)).setBufferedImage(((MainComponent)ihm.jtp.getComponentAt(index)).getBufferedImage());
        ihm.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}
