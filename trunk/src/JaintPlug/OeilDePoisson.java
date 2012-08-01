package JaintPlug;


import Jaint.*;
import JaintPlug.oeilpoisson.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Jonas
 */
public class OeilDePoisson implements Plugin
{
    
  
    private double scaleValue;
    private boolean ok = false;
    private boolean estActive = true;
    private int nbApplyPlug = 1;
    private double zoom = 1;
    private int x;
    private int y;


    private BufferedImage biOri;
    private BufferedImage biModif;

    private CompteARebourd car;

    private JDialog jd;

    private JPanel jpPrincipal;
    private JPanel jpImage;
    private JPanel jpSlider;
    
    
    private JButton jbModif;
    private JLabel jlImage;
    private JLabel jlApply;
    private JSlider jsZoom;
    private JLabel jlZoomMoins;
    private JLabel jlZoomPlus;

    private JLabel jlCentre;
    private JLayeredPane jlp;


    public String getName()
    {
            return("Oeil de poisson");
    }

    public String getDescri()
    {
            return("Permet d'appliquer un grossissement circulaire a un endroit de l'image ");
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

        this.jd = new JDialog(JFrame.getFrames()[0], "Oeil de poisson", true);
        this.jpPrincipal = new JPanel(new BorderLayout());
        this.jpImage = new JPanel();
        this.jpSlider = new JPanel(new BorderLayout());
        
        this.jlZoomMoins = new JLabel(new ImageIcon("./lib/Icons/Plugins/pingouin.png"));
        this.jlZoomPlus = new JLabel(new ImageIcon("./lib/Icons/Plugins/poisson.png"));
        this.jsZoom = new JSlider(JSlider.HORIZONTAL, 1, 100, 1);
        this.jsZoom.setPreferredSize(new Dimension(300, 50));
        this.jsZoom.setPaintTicks( true );
        this.jsZoom.setPaintLabels( true );
        this.jsZoom.addChangeListener(new EcouteurSlider(this, car));

        this.jpSlider.add(this.jlZoomMoins,BorderLayout.WEST);
        this.jpSlider.add(this.jsZoom, BorderLayout.CENTER);
        this.jpSlider.add(this.jlZoomPlus, BorderLayout.EAST);

       
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
        else if(width < 200 || height < 200)
        {
            this.scaleValue = 4;
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
        jlImage.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
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
        this.jpPrincipal.add(this.jpSlider, BorderLayout.SOUTH);

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
     *Recupere le niveau de zoom renvoye par le slider
     */
    public void setZoom()
    {
            this.zoom = this.jsZoom.getValue();
    }



    /**
     *Previsualiser l'effet
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
        this.zoom = 100;
        BufferedImage icon = applyEffect(img);
        this.zoom = 1;
        return icon;
    }


     /**
     * Applique l'effet a l'image en parametre et retourne l'image modifiee
     * @param img BufferedImage image courante
     * @return biModif BufferedImage image modifie
     */
     public BufferedImage applyEffect(BufferedImage img)
    {
        
        double w = this.zoom/1000; //0.019
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        int R = Math.min(img.getWidth(), img.getHeight())/2;
        double s = R/Math.log(w*R+1);
        int centreX = this.x;
        int centreY = this.y;

        for (int i=0; i<bi.getWidth(); i++)
        {
            for (int j=0; j<bi.getHeight(); j++)
            {
                int rgb = img.getRGB(i,j);

                if (((rgb >>24 ) & 0xFF) != 0)
                {

                    double r = Math.sqrt((i-centreX)*(i-centreX)+(j-centreY)*(j-centreY));
                    if (r>R)
                            bi.setRGB(i, j, img.getRGB(i, j));
                    else
                    {
                        double rayonOrigine = (Math.exp(r/s)-1)/w;
                        double angle = Math.atan2(j-centreY, i-centreX);
                        int I = (int) (rayonOrigine*Math.cos(angle)+centreX);
                        int J = (int) (rayonOrigine*Math.sin(angle)+centreY);
                        bi.setRGB(i, j, img.getRGB(I, J));
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
     *Retourne l'image
     * @return l'image
     */
    public JLabel getJlImage() {
        return jlImage;
    }

    /**
     *
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
