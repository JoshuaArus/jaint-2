package JaintPlug;

import Jaint.Pictimage;
import Jaint.Plugin;
import JaintPlug.redEye.EcouteurImage;
import JaintPlug.redEye.EcouteurOk;
import JaintPlug.redEye.WEcouteurFerme;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 *
 * @author Joshua
 */

public class RedEye implements Plugin
{
    private boolean estActive = true;
    private int nbApplyPlug = 1;
    private boolean ok = false;
    private JDialog jd;
    private Point centre = new Point(-1,-1);
    private JLabel miniature;
    private JButton JBResult;
    private double coef = 1;
    private BufferedImage copy;
    private BufferedImage mini;
    private JSlider js = new JSlider(0,255,25);
    private JLabel jlCentre;
    private int x;
    private int y;

    public String getName() {
        return "Yeux rouges";
    }

    public String getDescri() {
        return "Plugin qui permet de corriger les yeux rouges sur une photo";
    }

    public boolean isEnabled() {
        return estActive;
    }

    public void setEnabled(boolean b) {
        estActive = b;
    }

    public BufferedImage applyEffectIcon(BufferedImage img)
    {
        BufferedImage icon = null;
        try {
            icon = ImageIO.read(new File("./lib/Icons/Plugins/yeux.png"));
        } catch (IOException ex) {
            icon = new BufferedImage(5,5,BufferedImage.TYPE_INT_RGB);
        }
       
        return icon;
    }

    /**
     *
     * @param img
     * @return
     */
    public BufferedImage applyEffectPlugin(BufferedImage img)
    {
        int hauteur = img.getHeight();
        int largeur = img.getWidth();
        if (centre.x > -1 && centre.y > -1)
        {
            int base = img.getRGB(centre.x, centre.y);

            ArrayList<Point> afaire = new ArrayList<Point>();
            ArrayList<Point> fait = new ArrayList<Point>();

            afaire.add(centre);
            while(!afaire.isEmpty())
            {
                Point pCourant = afaire.get(0);

                pCourant.x = Math.min(Math.max(pCourant.x,0),largeur);
                pCourant.y = Math.min(Math.max(pCourant.y,0),hauteur);

                int courant = img.getRGB(pCourant.x, pCourant.y);

                if (!fait.contains(pCourant) && Fonction.couleurIdentique(base, courant, js.getValue()))
                {
                    Color c = new Color(courant);

                    float[] hsb = Color.RGBtoHSB((c.getGreen()+c.getBlue())/2, c.getGreen(), c.getBlue(), null);

                    hsb[1] = hsb[1]*0.8f;

                    img.setRGB(pCourant.x, pCourant.y, Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));

                    fait.add(pCourant);

                    if(pCourant.x>0)
                        afaire.add(new Point(pCourant.x-1,pCourant.y));//a gauche
                    if(pCourant.x<img.getWidth())
                        afaire.add(new Point(pCourant.x+1,pCourant.y));//a droite
                    if(pCourant.y>0)
                        afaire.add(new Point(pCourant.x,pCourant.y-1));//au dessus
                    if(pCourant.y<img.getHeight())
                        afaire.add(new Point(pCourant.x,pCourant.y+1));//en dessous
                }

                afaire.remove(0);
            }
        }
        
        return img;
    }

    /**
     *
     * @param img
     * @return
     */
    public BufferedImage modify(BufferedImage img) {

        if(img.getType() == BufferedImage.TYPE_3BYTE_BGR)
        {
            BufferedImage src = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = src.createGraphics();
            g.drawRenderedImage(img, null);
            g.dispose();
            img = src;
        }

        copy = Fonction.buffToImage(img);

        showDialog(img);

        if (ok)
        {
                return copy;
        }

        return img;
    }

    /**
     *
     * @return
     */
    public int getNbApplyPlug() {
        return nbApplyPlug;
    }

    private void showDialog(BufferedImage img)
    {
       this.jd = new JDialog(JFrame.getFrames()[0], "Correction des yeux rouges", true);
       Dimension dScreen = Toolkit.getDefaultToolkit().getScreenSize();
       coef = img.getHeight() *3 / (dScreen.height);

       mini = Pictimage.toBufferedImage(img.getScaledInstance((int)(img.getWidth()/coef), (int)(img.getHeight()/coef), Image.SCALE_SMOOTH));
       miniature = new JLabel(new ImageIcon(mini));
       JBResult = new JButton(new ImageIcon(mini));
       miniature.setPreferredSize(new Dimension((int)(img.getWidth() / this.coef), (int)(img.getHeight() / this.coef)));
       miniature.addMouseListener(new EcouteurImage(this));
       miniature.addMouseMotionListener(new EcouteurImage(this));
       JBResult.addActionListener(new EcouteurOk(this));

       JPanel jp = new JPanel();
       jp.add(miniature);
       jp.add(JBResult);
       jd.add(jp,BorderLayout.CENTER);
       JPanel jsp = new JPanel(new BorderLayout());
       JPanel temp = new JPanel();
       temp.add(new JLabel("Tolérance : "));
       jsp.add(temp,BorderLayout.NORTH);
       jsp.add(js,BorderLayout.CENTER);
       jd.add(jsp,BorderLayout.SOUTH);

       jd.pack();
        
       //jd.setPreferredSize(new Dimension((int)(img.getWidth() * this.coef)+20, (int)(img.getHeight() * this.coef)+20));
       Dimension d = this.jd.getSize();

       this.jd.setLocation((int)(dScreen.getWidth()/2-d.getWidth()/2), (int)(dScreen.getHeight()/2-d.getHeight()/2));
       this.jd.addWindowListener(new WEcouteurFerme(this));
       this.jd.setResizable(false);
       this.jd.setVisible(true);
    }

    /**
     *
     */
    public void annuler()
    {
        this.ok = false;
        this.jd.dispose();
    }

    /**
     *
     */
    public void apply()
    {
        this.ok = true;
        this.jd.dispose();
    }

    /**
     *
     * @param point
     */
    public void previsualiser(Point point) {
        centre = point;
        mini = Fonction.buffToImage(applyEffectPlugin(copy).getScaledInstance((int)(copy.getWidth()/coef), (int)(copy.getHeight()/coef), Image.SCALE_SMOOTH));
        JBResult.setIcon(new ImageIcon(mini));
        miniature.setIcon(new ImageIcon(mini));
    }

    /**
     *
     * @param cursor
     */
    public void setCursor(Cursor cursor) {
        miniature.setCursor(cursor);
    }

    /**
     *
     * @param posX
     * @param posY
     */
    public void selectLocation(int posX, int posY)
    {
        this.x = (int)(posX);
        this.y = (int)(posY);
        //On met a jour la position de la punaise seulement si toujours sur l'image de gauche
        if(this.x <= mini.getWidth() && this.y <= mini.getHeight() && this.x>=0 && this.y>=0)
        {
            this.jlCentre.setBounds(this.x, this.y-16, 32, 32);
        }
    }
}
