package JaintPlug;


import Jaint.*;
import JaintPlug.nb.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Jonas
 */
public class NoirEtBlanc implements Plugin
{

    private boolean estActive = true;
    private boolean ok = false;
    private int nbApplyPlug = 3;
    private double scaleValue = 1;
    private int tolerance;

    private BufferedImage biOri;
    private BufferedImage biModif;


    private JButton jbModif;

    private JDialog jd;
    private JPanel jpImage;
    private JPanel jpText;
    private JPanel jpPrincipal;

    private JLabel jlApply;
    private JaintSpinner jsTaille;




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
        return("Noir et blanc");
    }

    public String getDescri()
    {
        return("Permet d'appliquer un effet de noir et blanc");
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
     * Prend l'image courante en parametre et retourne l'image modifie ou non
     * @param img image courante
     * @return imgModif image modifie ou non
     */
    public BufferedImage modify(BufferedImage img)
    {
        if(img.getType() != BufferedImage.TYPE_INT_ARGB )
            {
                BufferedImage src = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = src.createGraphics();
                g.drawRenderedImage(img, null);
                g.dispose();
                img = src;
            }

            this.biOri = img;

            this.showDialog(img);

            BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

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


    public BufferedImage applyEffectIcon(BufferedImage img)
    {
        this.tolerance = 2;
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
        BufferedImage imgNB = new BufferedImage( img.getWidth(), img.getHeight(), img.getType() );

        tolerance++;

        imgNB = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_ARGB);
        for(int i=0;i<img.getWidth();i++)
        {
            for(int j=0;j<img.getHeight();j++)
            {
                int rgb = img.getRGB(i,j);

                if (((rgb >>24 ) & 0xFF) != 0)
                {
                    Color c = new Color(img.getRGB(i, j));
                    int r=c.getRed();
                    int g=c.getGreen();
                    int b=c.getBlue();
                    int a=c.getAlpha();

                    int moyenne = (r+g+b)/3;
                    int coef=0;
                    while(moyenne>((256/this.tolerance)*(coef+1)))
                            coef++;
                    coef=(coef==tolerance-1)? tolerance : coef;
                    int comp = (256/tolerance)*(coef)-1;
                    if(comp<0)
                            comp=0;

                    Color color = new Color(comp,comp,comp,a);
                    imgNB.setRGB(i, j, color.getRGB());
                }
                else
                {
                     imgNB.setRGB(i,j,rgb);
                }
            }
        }

        tolerance--;
	return imgNB;
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
        this.tolerance = 1;
        Image mini;
        BufferedImage miniBi;

        this.jd = new JDialog(JFrame.getFrames()[0], "Noir et blanc", true);
        this.jpPrincipal = new JPanel(new BorderLayout());

        this.jpImage = new JPanel();
        this.jpText = new JPanel();

        this.jsTaille = new JaintSpinner(this);

        this.jpText.add(jsTaille);



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
        this.biModif = applyEffectPlugin(miniBi);

        //Creation et ajout des ecouteurs aux deux miniatures
        this.jbModif = new JButton(new ImageIcon(this.biModif));
        this.jbModif.addActionListener(new EcouteurOK(this));
        this.jbModif.addMouseListener(new EcouteurBoutonAppliquer(this)); 

        //On fixe la taille du bouton, c'est moche je sais ...
        this.jbModif.setPreferredSize(new Dimension((int)(img.getWidth() * this.scaleValue)+20, (int)(img.getHeight() * this.scaleValue)+20));


        this.jpImage.add(this.jbModif);

        this.jpPrincipal.add(this.jpImage, BorderLayout.CENTER);
        this.jpPrincipal.add(this.jpText, BorderLayout.SOUTH);

        this.jd.add(jpPrincipal);
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

    /**
     *previsualiser
     */
    public void previsualiser()
    {
        


        this.biModif = applyEffectPlugin(this.biOri);
        this.jbModif.setIcon(new ImageIcon(this.biModif));


    }

    /**
     *Recupere la tolerance dans le JaintSpiner
     */
    public void setTaille()
    {

        this.tolerance =  this.jsTaille.getValue() + 1;
    }


    /**
    *	Methode comparant deux couleurs avec une certaine tolerance et retournant un boolean
    *
    *	@param	pixel1		Couleur du premier pixel a comparer
    *	@param	pixel2		Couleur du deuxieme pixel a comparer
    *	@param	tolerance	Difference tolere entre les deux couleurs pour les declarer identiques
    *	@return	estIdentique	Vrai si les couleurs sont identiques, faux sinon.
    */
    @SuppressWarnings("unused")
	private static boolean couleurIdentique( int pixel1, int pixel2, int tolerance )
    {
        boolean estIdentique = false;
        
        Color   couleur1     = new Color( pixel1 );
        Color   couleur2     = new Color( pixel2 );

        if (!( Math.abs( couleur1.getRed() - couleur2.getRed() ) > tolerance ))
                if ( !(Math.abs( couleur1.getBlue() - couleur2.getBlue() ) > tolerance ))
                        if (!( Math.abs( couleur1.getGreen() - couleur2.getGreen() ) > tolerance ))
                                estIdentique = true;	// si les trois composantes des couleurs ne different pas de plus de la tolerance
        // alors les couleurs sont declarees identiques.

        return estIdentique;
    }

    /**
    *
    *	Compare deux couleurs et retourne la difference qu'il y a entre les deux
    *
    *	@param	pixel1		Couleur du premier pixel a comparer
    *	@param	pixel2		Couleur du deuxieme pixel a comparer
    *	@return	difference		Correspond a la difference qu'il y a entre 2couleurs
    */
    public static int difference_couleur( int pixel1, int pixel2 )
    {
        Color couleur1 = new Color( pixel1 );
        Color couleur2 = new Color( pixel2 );

        int   diff     = Math.abs( couleur1.getRed() - couleur2.getRed() );
        diff += Math.abs( couleur1.getGreen() - couleur2.getGreen() );		// calcule la difference des trois composantes
        diff += Math.abs( couleur1.getBlue() - couleur2.getBlue() );


        return diff / 3;	// retourne la moyenne des differences
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
