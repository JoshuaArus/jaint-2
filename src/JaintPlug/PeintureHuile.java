package JaintPlug;


import Jaint.*;
import JaintPlug.peinture.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Jonas
 */
public class PeintureHuile implements Plugin
{

    private boolean estActive = true;
    private boolean ok = false;
    private int nbApplyPlug = 3;
    private double scaleValue = 1;
    private int intensite;
    private int rayon;

    private CompteARebourds car;

    private JSlider jsRay;
    private JSlider jsInt;
    private JButton jbModif;
    private JPanel jp;
    private JPanel jpSlider;
    private JPanel jpSliderInt;
    private JPanel jpSliderRay;

    private BufferedImage biOri;
    private BufferedImage biModif;

    private JLabel jlApply;

    private JDialog jd;

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

    public String getName()
    {
        return("Peinture a l'huile");
    }

    public String getDescri()
    {
        return("Permet de creer une peinture a l'huile a partir de l'image");
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
     *Recupere l'intensite renvoye par le slide
     */
    public void setJsIntValue()
    {
            this.intensite = this.jsInt.getValue();
    }

    /**
     * Recupere le rayon renvoye par le slider
     */
    public void setJsRayValue()
    {
            this.rayon = this.jsRay.getValue();
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

            if(ok)
            {
                bi = applyEffectPlugin(img);

                return bi;
            }
            else
            {
                return img;
            }
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
     * Affiche la fenetre du plugin, en prenant en parametre l'image courante
     * @param img image courante
     */
    public void showDialog(BufferedImage img)
    {
        Image mini;
        BufferedImage miniBi;

        this.intensite = 0;
        this.rayon = 0;



        this.jd = new JDialog(JFrame.getFrames()[0], "Peinture a l'huile", true);
        

        this.jp = new JPanel(new BorderLayout());
        this.jpSlider = new JPanel(new BorderLayout());
        this.jpSliderInt = new JPanel(new BorderLayout());
        this.jpSliderRay = new JPanel(new BorderLayout());

        
        this.jsRay = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
        this.jsRay.setPreferredSize(new Dimension(300, 50));
        this.jsRay.addChangeListener(new EcouteurSlider(this, car));

        this.jsInt = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
        this.jsInt.setPreferredSize(new Dimension(300, 50));
        this.jsInt.addChangeListener(new EcouteurSlider(this, car));


        this.jpSliderRay.add(new JLabel(new ImageIcon("./lib/Icons/Plugins/pingouin.png")), BorderLayout.WEST);
        this.jpSliderRay.add(jsRay,BorderLayout.CENTER);
        this.jpSliderRay.add(new JLabel(new ImageIcon("./lib/Icons/Plugins/flou.png")), BorderLayout.EAST);

        this.jpSliderInt.add(new JLabel(new ImageIcon("./lib/Icons/Plugins/pingouin.png")),BorderLayout.WEST);
        this.jpSliderInt.add(jsInt,BorderLayout.CENTER);
        this.jpSliderInt.add(new JLabel(new ImageIcon("./lib/Icons/Plugins/peintureHuile.png")),BorderLayout.EAST);

        this.jpSlider.add(this.jpSliderRay, BorderLayout.NORTH);
        this.jpSlider.add(this.jpSliderInt, BorderLayout.SOUTH);

        /*Ajout de l'image reduite dans le panel nord*/
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

        mini = img.getScaledInstance((int)(img.getWidth() * this.scaleValue), (int)(img.getHeight() * this.scaleValue),Image.SCALE_DEFAULT);

        miniBi = Pictimage.toBufferedImage(mini);

        this.biOri = miniBi;
        this.biModif = applyEffectPlugin(miniBi);

        //Creation et ajout des ecouteurs aux deux miniatures
        this.jbModif = new JButton(new ImageIcon(this.biModif));
        this.jbModif.addActionListener(new OkListener(this));
        this.jbModif.addMouseListener(new EcouteurBoutonAppliquer(this));

        //On fixe la taille du bouton, c'est moche je sais ...
        this.jbModif.setPreferredSize(new Dimension((int)(img.getWidth() * this.scaleValue)+20, (int)(img.getHeight() * this.scaleValue)+20));

        this.jp.add(this.jbModif, BorderLayout.CENTER);
        this.jp.add(this.jpSlider, BorderLayout.SOUTH);
        
        this.jd.add(jp);
        this.jd.pack();


        Dimension d = this.jd.getSize();
        Dimension dScreen = this.jd.getToolkit().getScreenSize();
        this.jd.setLocation((int)(dScreen.getWidth()/2-d.getWidth()/2), (int)(dScreen.getHeight()/2-d.getHeight()/2));

        //Icone pour appliquer l'effet
        JLayeredPane jlp  = this.jd.getLayeredPane();
        jlApply = new JLabel (new ImageIcon("./lib/Icons/Plugins/apply.png"));

        Dimension dApply = jlApply.getPreferredSize();
        Dimension dBtnApply = this.jbModif.getPreferredSize();
        jlApply.setBounds((int)(d.getWidth()/2 - dApply.getWidth()/2),(int)(dBtnApply.getHeight()/2 - dApply.getHeight()/2), (int)dApply.getWidth(), (int)dApply.getHeight());

        jlp.add(jlApply);

        this.jd.addWindowListener(new WEcouteurFerme(this));
        this.jd.setResizable(false);
        this.jd.setVisible(true);



    }

    public BufferedImage applyEffectIcon(BufferedImage img)
    {
        this.rayon = 5;
        this.intensite = 5;
        BufferedImage icon = applyEffectPlugin(img);
        return icon;
    }

    /**
     * Applique l'effet a l'image en parametre et retourne l'image modifiee
     * @param img BufferedImage image courante
     * @return biModif BufferedImage image modifie
     */
    public BufferedImage applyEffectPlugin(BufferedImage img)
    {
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

            for (int i = 0; i < bi.getWidth(); i++) 
            {
                for (int j = 0; j < bi.getHeight(); j++) 
                {
                    int e = img.getRGB(i,j);

                    if (((e >>24 ) & 0xFF) != 0)
                    {
                        int[] intensiteCompteur = new int[this.intensite+1];
                        int[] couleurMoyenneR = new int[this.intensite+1];
                        int[] couleurMoyenneG = new int[this.intensite+1];
                        int[] couleurMoyenneB = new int[this.intensite+1];
                        for (int k = i - this.rayon; k <= i + this.rayon; k++)
                        {
                            for (int l = j - this.rayon; l <= j + this.rayon; l++)
                            {
                                if (k >= 0 && k < img.getWidth() && l >= 0 && l < img.getHeight())
                                {
                                    int rgb = img.getRGB(k, l);
                                    int nIntensite = (int) (intensite(rgb) * this.intensite / 255);
                                    intensiteCompteur[nIntensite] ++;
                                    couleurMoyenneR[nIntensite] += ((rgb & 0xff0000)>>16);
                                    couleurMoyenneG[nIntensite] += ((rgb & 0xff00)>>8);
                                    couleurMoyenneB[nIntensite] += ((rgb & 0xff));
                                }
                            }
                        }

                        int nIntensite = 0;
                        int max = 0;
                        for (int k = 0; k <= this.intensite; k++)
                        {
                            if (intensiteCompteur[k] > max)
                            {
                                nIntensite = k;
                                max = intensiteCompteur[k];
                            }
                        }

                        int r = couleurMoyenneR[nIntensite]/max;
                        int g = couleurMoyenneG[nIntensite]/max;
                        int b = couleurMoyenneB[nIntensite]/max;
                        bi.setRGB(i, j, (0xff<<24)+(r<<16)+(g<<8)+(b));
                    }
                    else
                    {
                        bi.setRGB(i,j,e);
                    }
                }
            }
            return bi;
	}
	
    /**
    * Calcul de l'intensite correspondant a une couleur (r, g, b) :<BR>
    * i = 0.299*r + 0.587*g + 0.114*b
    *
    * @param rgb Couleur.
    * @return Intensite.
    */
    public static double intensite(int rgb)
    {
        int r = (rgb & 0xff0000)>>16;
        int g = (rgb & 0xff00)>>8;
        int b = (rgb & 0xff);
        return (0.299*r + 0.587*g + 0.114*b);
    }


    /**
     *
     */
    public void previsualiser()
    {

        this.biModif = applyEffectPlugin(this.biOri);
        this.jbModif.setIcon(new ImageIcon(this.biModif));
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
