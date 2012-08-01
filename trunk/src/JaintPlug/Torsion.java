package JaintPlug;


import Jaint.*;
import JaintPlug.torsion.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;


/**
 *
 * @author Jonas
 */

public class Torsion implements Plugin
{

    private double scaleValue;
    private boolean ok = false;
    private boolean estActive = true;
    private int nbApplyPlug = 1;
    private double angle = 1;
    private int x;
    private int y;


    private BufferedImage biOri;
    private BufferedImage biModif;

    

    private JDialog jd;

    private JPanel jpPrincipal;
    private JPanel jpImage;
    private JPanel jpInfo;
    

    private JButton jbModif;
    private JLabel jlImage;
    private JSlider jsAngle;
    private JLabel jlAnglePlus;
    private JLabel jlAngle;
    private JLabel jlAngleMoins;
    private JLabel jlApply;

    private JLabel jlCentre;
    private JLayeredPane jlp;

    public String getName()
    {
            return("Torsion");
    }

    public String getDescri()
    {
            return("Permet d'appliquer un tourbillon a un endroit de l'image ");
    }

    public boolean isEnabled()
    {
        return estActive;
    }

    public void setEnabled(boolean b)
    {
        estActive = b;
    }

    /**
     *
     * @return
     */
    public int getNbApplyPlug()
    {
        return nbApplyPlug;
    }

    /**
     * Prend l'image courante en parametre et retourne l'image modifie ou non
     * @param img image courante
     * @return imgModif image modifie ou non
     */
    public BufferedImage modify(BufferedImage img)
    {


        if(img.getType() != BufferedImage.TYPE_INT_ARGB)
        {
            BufferedImage src = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = src.createGraphics();
            g.drawRenderedImage(img, null);
            g.dispose();
            img = src;
        }

        this.biOri = img;

        this.showDialog(img);

        BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        if (ok)
        {
                this.x = (int) (this.x/this.scaleValue);
                this.y = (int) (this.y/this.scaleValue);
                bi = applyEffect(img);

                return bi;
        }
        else
        {
                return img;
        }
    }

    /**
     * Affiche la fenetre du plugin, en prenant en parametre l'image courante
     * @param img image courante
     */
    public void showDialog(BufferedImage img)
    {

        Image mini;
        BufferedImage miniBi;


        this.jd = new JDialog(JFrame.getFrames()[0], "Torsion", true);
        this.jpPrincipal = new JPanel(new BorderLayout());
        this.jpImage = new JPanel();
        this.jpInfo = new JPanel(new BorderLayout());
        

        this.jlAngle = new JLabel(new ImageIcon("./lib/Icons/Plugins/pingouin.png"));
        this.jlAngleMoins = new JLabel(new ImageIcon("./lib/Icons/Plugins/torsionMoins.png"));
        this.jlAnglePlus = new JLabel(new ImageIcon("./lib/Icons/Plugins/torsionPlus.png"));
        this.jsAngle = new JSlider(JSlider.HORIZONTAL, -2000, 2000, 0);
        //this.jsAngle.setPreferredSize(new Dimension(300, 50));
        this.jsAngle.setPaintTicks( true );
        this.jsAngle.setPaintLabels( true );
        this.jsAngle.addChangeListener(new EcouteurSlider(this));



        this.jpInfo.add(this.jsAngle, BorderLayout.NORTH);
        this.jpInfo.add(this.jlAngleMoins, BorderLayout.EAST);
        this.jpInfo.add(this.jlAngle, BorderLayout.CENTER);
        this.jpInfo.add(this.jlAnglePlus, BorderLayout.WEST);


        int width = img.getWidth();
        int height = img.getHeight();

        if (width > 1600 || height > 1600)
        {
            this.scaleValue = 0.125;
        }
        else if (width > 800 || height > 800)
        {
            this.scaleValue = 0.25;
        }
        else if(width > 400 || height > 400)
        {
            this.scaleValue = 0.5;
        }
        else
        {
            this.scaleValue = 1;
        }


        mini = img.getScaledInstance((int)(img.getWidth() * this.scaleValue), (int)(img.getHeight() * this.scaleValue),Image.SCALE_DEFAULT);

        miniBi = Pictimage.toBufferedImage(mini);

        this.biOri = miniBi;
        this.biModif = applyEffect(miniBi);

        this.jlImage = new JLabel(new ImageIcon(this.biOri));
        this.jlImage.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        this.jlImage.addMouseListener(new EcouteurCentre(this));
        this.jlImage.addMouseMotionListener(new EcouteurCentreMove(this));
        
        this.jbModif = new JButton(new ImageIcon(this.biModif));
        this.jbModif.addActionListener(new EcouteurOK(this));
        this.jbModif.addMouseListener(new EcouteurBoutonAppliquer(this));

        //On fixe la taille du bouton, c'est moche je sais ...
        this.jbModif.setPreferredSize(new Dimension((int)(img.getWidth() * this.scaleValue)+20, (int)(img.getHeight() * this.scaleValue)+20));


        this.jpImage.add(this.jlImage);
        this.jpImage.add(this.jbModif);

        this.jpPrincipal.add(this.jpImage, BorderLayout.CENTER);
        this.jpPrincipal.add(this.jpInfo, BorderLayout.SOUTH);

        //Parametrage des JToolTipeText pour les coordonnees
        ToolTipManager.sharedInstance().registerComponent(this.jlImage);
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay( 1000 );
        ToolTipManager.sharedInstance().setReshowDelay( 0 );
        
        
        this.jd.add(jpPrincipal);
        this.jd.pack();


        Dimension d = this.jd.getSize();
        Dimension dScreen = this.jd.getToolkit().getScreenSize();
        this.jd.setLocation((int)(dScreen.getWidth()/2-d.getWidth()/2), (int)(dScreen.getHeight()/2-d.getHeight()/2));

        //Icone pour appliquer l'effet
        this.jlp  = this.jd.getLayeredPane();
        jlApply = new JLabel (new ImageIcon("./lib/Icons/Plugins/apply.png"));

        Dimension dApply = jlApply.getPreferredSize();
        Dimension dBtnApply = jbModif.getPreferredSize();
        Point p = jbModif.getLocation();
        jlApply.setBounds((int)(p.x + dBtnApply.width/2 - dApply.width/2),(int)(p.y + dBtnApply.height/2 - dApply.height/2), (int)dApply.width, (int)dApply.height);
        jlp.add(jlApply);

        //Parametrage de l'indicateur pour le centre de leffet
        this.jlCentre = new JLabel(new ImageIcon("./lib/Icons/Plugins/centre.png"));
        this.jlCentre.setBounds(this.x, this.y, 32, 32);

        this.jlp.add(this.jlCentre);

        this.jd.addWindowListener(new WEcouteurFerme(this));
        this.jd.setResizable(false);
        this.jd.setVisible(true);

    }
    /**
     *Annule l'effet et femer la fenetre du plugin
     */
    public void annuler()
    {
        this.ok = false;
        this.jd.dispose();
    }

   /**
    *Applique l'effet et femer la fenetre du plugin
    */
   public void apply()
    {
        this.ok = true;
        this.jd.dispose();
    }

    /**
     * Recupere l'angle de la torsion
     */
    public void setAngle()
    {
            this.angle = this.jsAngle.getValue();
    }



    /**
     *Previalisation et mise a jour des boutons
     */
    public void previsualiser()
    {
        
        this.biModif = applyEffect(this.biOri);
        this.jbModif.setIcon(new ImageIcon(this.biModif));
    }


    public BufferedImage applyEffectIcon(BufferedImage img)
    {
        this.x = (int) (img.getWidth()/2);
        this.y = (int) (img.getHeight()/2);
        this.angle = 2000;
        BufferedImage icon = applyEffect(img);
        this.angle = 1;
        return icon;
    }

    

    /**
     * Applique l'effet a l'image en parametre et retourne l'image modifiee
     * @param img BufferedImage image courante
     * @return biModif BufferedImage image modifie
     */
    public BufferedImage applyEffect(BufferedImage img)
    {
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        int R = Math.min(img.getWidth(null), img.getHeight(null)) / 2;
        int centreX = this.x;
        int centreY = this.y;

        // FAIRE VARIER CETTE VALEUR POUR MODIFIER L'EFFET DE L'EFFET
        double radians = Math.toRadians(-(double)this.angle);

        double a = radians / (R * R);
        double b = -2 * radians / R;

        for (int i = 0; i < bi.getWidth(); i++)
        {
            for (int j = 0; j < bi.getHeight(); j++)
            {
                int rgb = img.getRGB(i,j);

                if (((rgb >>24 ) & 0xFF) != 0)
                {
                    try
                    {
                        int px = i - centreX;
                        int py = j - centreY;
                        double r = Math.sqrt(px * px + py * py);
                        if (r<=R)
                        {
                            double theta = Math.atan2(py, px);
                            double anteTheta = theta + a * r * r + b * r + radians;
                            int X = (int) (r * Math.cos(anteTheta));
                            int Y = (int) (r * Math.sin(anteTheta));
                            int I = X + centreX;
                            int J = Y + centreY;
                            bi.setRGB(i, j, img.getRGB(I, J));
                        }
                        else
                        bi.setRGB(i, j, img.getRGB(i, j));
                    }
                    catch(ArrayIndexOutOfBoundsException ae)
                    {

                    }
                }
                else
                {
                    bi.setRGB(i,j,rgb);
                }

                
            }
        }
        return bi;
    }
    

    /**
      * Modifie la position du centre de l'effet en prenant comme parametre les coordonnees de la souris
      * @param posX coordonnee x de la souris
      * @param posY coordonnee y de la souris
      */
    public void selectLocation(int posX, int posY)
    {
        this.x = (int)(posX);
        this.y = (int)(posY);
        //On met a jour la position de la punaise seulement si toujours sur l'image de gauche
        if(this.x <= biOri.getWidth() && this.y <= biOri.getHeight() && this.x>=0 && this.y>=0)
        {
            this.jlCentre.setBounds(this.x, this.y-16, 32, 32);
        }
        this.previsualiser();
    }

    /**
     * Retourne le JLabel contenant l'image
     * @return jlImage
     */
    public JLabel getJlImage() {
        return jlImage;
    }

    /**
     * Retourne la valeur de l'echelle
     * @return scaleValue
     */
    public double getScaleValue() {
        return scaleValue;
    }



    /**
     * Affiche l'icone valide sur le bouton valider si l'utilisateur se trouve dessu
     * @param entre boolean
     */
    public void modifBtnApply(boolean entre)
    {
      jlApply.setVisible(entre);
    }
}

