package JaintPlug;

import Jaint.*;
import JaintPlug.lumicont.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;


/**
 *
 * @author Jonas
 */
public class LumiCont implements Plugin
{
    private ArrayList<Integer> lstEffet = new ArrayList<Integer>();
    private boolean estActive = true;
    private boolean ok = false;
    private int nbApplyPlug = 5;
    private double scaleValue = 1;
    private int lumi;
    private int cont;

    private JPanel jpImage;
    private JPanel jpPrincipal;
    private JDialog jd;

    private JButton jbOri;
    private JButton jbcl;
    private JButton jbl;
    private JButton jbCl;
    private JButton jbc;
    private JButton jbC;
    private JButton jbcL;
    private JButton jbL;
    private JButton jbCL;

    private BufferedImage biOri;
    private BufferedImage bicl;
    private BufferedImage bil;
    private BufferedImage biCl;
    private BufferedImage bic;
    private BufferedImage biC;
    private BufferedImage bicL;
    private BufferedImage biL;
    private BufferedImage biCL;

    private JPanel jpAideHaut;
    private JPanel jpAideBas;

    
    private JLabel jlApply;
   
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
        return("Luminosite / Contraste");
    }

    public String getDescri()
    {
        return("Permet de regler la luminosite et le contraste de l'image");
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

            BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), biOri.getType());

            if(ok)
            {
                bi = applyEffect(img);

                return bi;
            }
            else
            {
                return img;
            }
    }

    public BufferedImage applyEffectIcon(BufferedImage img)
    {
        this.lumi = 15;
        BufferedImage icon = applyLumiPlus(img);
        return icon;
    }

    

    /**
     * Applique les differents effets a l'image courante et renvoie l'image modifie
     * @param img image courante
     * @return imgModif image modifiee
     */
    public BufferedImage applyEffect(BufferedImage img)
    {
        BufferedImage bi = img;

        for(Integer i : lstEffet)
        {

            if(i == 0)
            {
                bi = applyLumiPlus(bi);
            }
            else if(i == 1)
            {
                bi = applyLumiMoins(bi);
            }
            else if(i == 2)
            {
                bi = applyContPlus(bi);
            }
            else if(i == 3)
            {
                bi = applyContMoins(bi);
            }
        }
        lstEffet = new ArrayList<Integer>();
        return bi;
    }

    /**
     * Affiche la fenetre du plugin, en prenant en parametre l'image courante
     * @param img image courante
     */
    public void showDialog(BufferedImage img)
    {
        Image mini;
        BufferedImage miniBi;

        this.jd = new JDialog(JFrame.getFrames()[0], "Luminosite / Contraste", true);
        this.jpImage = new JPanel(new GridLayout(3,3));
        this.jpPrincipal = new JPanel(new BorderLayout());

        this.jpAideHaut = new JPanel(new BorderLayout());
        this.jpAideBas = new JPanel(new BorderLayout());
      
        

   

       
        this.jd.addWindowListener(new WEcouteurFerme(this));

        int width = img.getWidth();
        int height = img.getHeight();

        if (width > 1600 || height > 1600)
        {
            this.scaleValue = 0.15;
        }
        else if (width > 400 || height > 400)
        {
            this.scaleValue = 0.2;
        }
        else if(width > 200 || height > 200)
        {
            this.scaleValue = 0.5;
        }
        else
        {
            this.scaleValue = 1;
        }


        mini = img.getScaledInstance((int)(img.getWidth() * this.scaleValue), (int)(img.getHeight() * this.scaleValue),Image.SCALE_SMOOTH);

        miniBi = Pictimage.toBufferedImage(mini);

        //Creation des miniatures
        this.biOri = miniBi;
        this.bicl = applyContMoins(applyLumiMoins(miniBi));
        this.bil = applyLumiMoins(miniBi);
        this.biCl = applyContPlus(applyLumiMoins(miniBi));
        this.bic = applyContMoins(miniBi);
        this.biC = applyContPlus(miniBi);
        this.bicL = applyContMoins(applyLumiPlus(miniBi));
        this.biL = applyLumiPlus(miniBi);
        this.biCL = applyContPlus(applyLumiPlus(miniBi));

        this.jbOri = new JButton(new ImageIcon(this.biOri));

        this.jbOri.addActionListener(new EcouteurOK(this));

        this.jbOri.addMouseListener(new EcouteurBoutonAppliquer(this));        
        this.jbOri.setName("jbOri");

        this.jbcl = new JButton(new ImageIcon(this.bicl));
        this.jbcl.addActionListener(new EcouteurBtn(this));
        this.jbcl.setName("jbcl");

        this.jbl = new JButton(new ImageIcon(this.bil));
        this.jbl.addActionListener(new EcouteurBtn(this));
        this.jbl.setName("jbl");

        this.jbCl = new JButton(new ImageIcon(this.biCl));
        this.jbCl.addActionListener(new EcouteurBtn(this));
        this.jbCl.setName("jbCl");

        this.jbc = new JButton(new ImageIcon(this.bic));
        this.jbc.addActionListener(new EcouteurBtn(this));
        this.jbc.setName("jbc");

        this.jbC = new JButton(new ImageIcon(this.biC));
        this.jbC.addActionListener(new EcouteurBtn(this));
        this.jbC.setName("jbC");

        this.jbcL = new JButton(new ImageIcon(this.bicL));
        this.jbcL.addActionListener(new EcouteurBtn(this));
        this.jbcL.setName("jbcL");

        this.jbL = new JButton(new ImageIcon(this.biL));
        this.jbL.addActionListener(new EcouteurBtn(this));
        this.jbL.setName("jbL");

        this.jbCL = new JButton(new ImageIcon(this.biCL));
        this.jbCL.addActionListener(new EcouteurBtn(this));
        this.jbCL.setName("jbCL");

        this.jpImage.add(jbcl,0);
        this.jpImage.add(jbl,1);
        this.jpImage.add(jbCl,2);
        this.jpImage.add(jbc,3);
        this.jpImage.add(jbOri,4);
        this.jpImage.add(jbC,5);
        this.jpImage.add(jbcL,6);
        this.jpImage.add(jbL,7);
        this.jpImage.add(jbCL,8);

        

        this.jpAideHaut.add(new JLabel (new ImageIcon("./lib/Icons/Plugins/LumiCont/lmcm.png")),BorderLayout.WEST);
        this.jpAideHaut.add(new JLabel (new ImageIcon("./lib/Icons/Plugins/LumiCont/lm.png")),BorderLayout.CENTER);
        this.jpAideHaut.add(new JLabel (new ImageIcon("./lib/Icons/Plugins/LumiCont/lmcp.png")),BorderLayout.EAST);

        
        this.jpPrincipal.add(new JLabel (new ImageIcon("./lib/Icons/Plugins/LumiCont/cm.png")),BorderLayout.WEST);
        this.jpPrincipal.add(new JLabel (new ImageIcon("./lib/Icons/Plugins/LumiCont/cp.png")),BorderLayout.EAST);

        this.jpAideBas.add(new JLabel (new ImageIcon("./lib/Icons/Plugins/LumiCont/lpcm.png")),BorderLayout.WEST);
        this.jpAideBas.add(new JLabel (new ImageIcon("./lib/Icons/Plugins/LumiCont/lp.png")),BorderLayout.CENTER);
        this.jpAideBas.add(new JLabel (new ImageIcon("./lib/Icons/Plugins/LumiCont/lpcp.png")),BorderLayout.EAST);
        

        this.jpPrincipal.add(this.jpAideHaut, BorderLayout.NORTH);
        this.jpPrincipal.add(this.jpImage, BorderLayout.CENTER);
        this.jpPrincipal.add(this.jpAideBas, BorderLayout.SOUTH);
        this.jd.add(this.jpPrincipal);

        this.jd.pack();


        Dimension d = this.jd.getSize();
        Dimension dScreen = this.jd.getToolkit().getScreenSize();
        this.jd.setLocation((int)(dScreen.getWidth()/2-d.getWidth()/2), (int)(dScreen.getHeight()/2-d.getHeight()/2));

        //Icone pour appliquer l'effet
        JLayeredPane jlp  = this.jd.getLayeredPane();
        ImageIcon temp = new ImageIcon("./lib/Icons/Plugins/LumiCont/apply.png");
        jlApply = new JLabel (temp);

        Dimension dApply = jlApply.getPreferredSize();
        jlApply.setBounds((int)(d.getWidth()/2 - dApply.getWidth()/2),(int)(d.getHeight()/2 - dApply.getHeight()/2), (int)dApply.getWidth(), (int)dApply.getHeight());

        jlp.add(jlApply);

        this.jd.setResizable(false);
        this.jd.setVisible(true);
        
        
     }

    /**
     * Augmente la luminosite de l'image passee en parametre
     * @param img image a modifier
     * @return img modifiee
     */
    public BufferedImage applyLumiPlus(BufferedImage img)
     {
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), img.getType());

        int height = img.getHeight();
        int width = img.getWidth();
        int rPix, bPix, vPix;

        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                int rgb = img.getRGB(j,i);

                if (((rgb >>24 ) & 0xFF) != 0)
                {
                    Color cPix = new Color(rgb);

                    rPix = cPix.getRed()+this.lumi;
                    if (rPix>255)
                    {
                            rPix=255;
                    }
                    if (rPix<0)
                    {
                            rPix=0;
                    }

                    bPix = cPix.getBlue()+this.lumi;
                    if (bPix>255)
                    {
                            bPix=255;
                    }
                    if (bPix<0)
                    {
                            bPix=0;
                    }

                    vPix = cPix.getGreen()+this.lumi;
                    if (vPix>255)
                    {
                            vPix=255;
                    }
                    if (vPix<0)
                    {
                            vPix=0;
                    }

                    cPix = new Color(rPix, vPix, bPix);
                    int cP = cPix.getRGB();
                    bi.setRGB(j, i, cP);
                }
                else
                {
                    bi.setRGB(j,i,rgb);
                }
            }
        }

        return bi;
     }

      /**
     * Diminue la luminosite de l'image passee en parametre
     * @param img image a modifier
     * @return img modifiee
     */
     public BufferedImage applyLumiMoins(BufferedImage img)
     {
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), img.getType());

        int height = img.getHeight();
        int width = img.getWidth();
        int rPix, bPix, vPix;

        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                int rgb = img.getRGB(j,i);

                if (((rgb >>24 ) & 0xFF) != 0)
                {

                    Color cPix = new Color(rgb);

                    rPix = cPix.getRed()-this.lumi;
                    if (rPix>255)
                    {
                            rPix=255;
                    }
                    if (rPix<0)
                    {
                            rPix=0;
                    }

                    bPix = cPix.getBlue()-this.lumi;
                    if (bPix>255)
                    {
                            bPix=255;
                    }
                    if (bPix<0)
                    {
                            bPix=0;
                    }

                    vPix = cPix.getGreen()-this.lumi;
                    if (vPix>255)
                    {
                            vPix=255;
                    }
                    if (vPix<0)
                    {
                            vPix=0;
                    }

                    cPix = new Color(rPix, vPix, bPix);
                    int cP = cPix.getRGB();
                    bi.setRGB(j, i, cP);
                }
                else
                {
                    bi.setRGB(j,i,rgb);
                }
            }
        }

        return bi;
     }

      /**
     * Augmente le contraste de l'image passee en parametre
     * @param img image a modifier
     * @return img modifiee
     */
     public BufferedImage applyContPlus(BufferedImage img)
     {
        BufferedImage bi = new BufferedImage( img.getWidth(), img.getHeight(), img.getType() );
        float fContrast = (float) this.cont / 128f;// permet d'obtenir le contraste entre -1 et 1

        for ( int i = 0; i < img.getHeight(); i++ )
        {
            for ( int j = 0; j < img.getWidth(); j++ )
            {
                int rgb = img.getRGB(j,i);
                if (((rgb >>24 ) & 0xFF) != 0)
                {
                    Color pixel = new Color(rgb);

                    // modifie le contraste de la composante rouge.
                    int   rouge = (int) ( pixel.getRed() + ( fContrast * ( pixel.getRed() - 127 ) ) );

                    if ( rouge > 255 )// Verifie que les couleurs ne sortent pas des bornes
                    {
                        rouge = 255;
                    }
                    else if ( rouge < 0 )
                    {
                            rouge = 0;
                    }

                    // modifie le contraste de la composante verte.
                    int vert = (int) ( pixel.getGreen() + ( fContrast * ( pixel.getGreen() - 127 ) ) );

                    if ( vert > 255 )// Verifie que les couleurs ne sortent pas des bornes
                    {
                            vert = 255;
                    }
                    else if ( vert < 0 )
                    {
                            vert = 0;
                    }
                    // modifie le contraste de la composante bleue.
                    int bleu = (int) ( pixel.getBlue() + ( fContrast * ( pixel.getBlue() - 127 ) ) );

                    if ( bleu > 255 )// Verifie que les couleurs ne sortent pas des bornes
                    {
                            bleu = 255;
                    }
                    else if ( bleu < 0 )
                    {
                            bleu = 0;
                    }

                    Color new_pixel = new Color( rouge, vert, bleu );

                    bi.setRGB( j, i, new_pixel.getRGB() );
                }
                else
                {
                    bi.setRGB(j,i,rgb);
                }

            }
        }

	return bi;

     }

     /**
     * Diminue le contraste de l'image passee en parametre
     * @param img image a modifier
     * @return img modifiee
     */
     public BufferedImage applyContMoins(BufferedImage img)
     {
        BufferedImage bi = new BufferedImage( img.getWidth(), img.getHeight(), img.getType() );
        float fContrast = (float) this.cont / 128f;// permet d'obtenir le contraste entre -1 et 1

        for ( int i = 0; i < img.getHeight(); i++ )
        {
            for ( int j = 0; j < img.getWidth(); j++ )
            {


                int rgb = img.getRGB(j,i);

                if (((rgb >>24 ) & 0xFF) != 0)
                {
                    Color pixel = new Color(rgb);

                    // modifie le contraste de la composante rouge.
                    int   rouge = (int) ( pixel.getRed() - ( fContrast * ( pixel.getRed() - 127 ) ) );

                    if ( rouge > 255 )// Verifie que les couleurs ne sortent pas des bornes
                    {
                        rouge = 255;
                    }
                    else if ( rouge < 0 )
                    {
                            rouge = 0;
                    }

                    // modifie le contraste de la composante verte.
                    int vert = (int) ( pixel.getGreen() - ( fContrast * ( pixel.getGreen() - 127 ) ) );

                    if ( vert > 255 )// Verifie que les couleurs ne sortent pas des bornes
                    {
                            vert = 255;
                    }
                    else if ( vert < 0 )
                    {
                            vert = 0;
                    }
                    // modifie le contraste de la composante bleue.
                    int bleu = (int) ( pixel.getBlue() - ( fContrast * ( pixel.getBlue() - 127 ) ) );

                    if ( bleu > 255 )// Verifie que les couleurs ne sortent pas des bornes
                    {
                            bleu = 255;
                    }
                    else if ( bleu < 0 )
                    {
                            bleu = 0;
                    }

                    Color new_pixel = new Color( rouge, vert, bleu );

                    bi.setRGB( j, i, new_pixel.getRGB() );
                }
                else
                {
                    bi.setRGB(j,i,rgb);
                }
            }
        }

	return bi;
     }

     /**
      * Previsualisation de luminosite plus
      */
     public void previsualiserLumiPlus()
    {
        lstEffet.add(0);
        BufferedImage bi = new BufferedImage(this.biOri.getWidth(), this.biOri.getHeight(), biOri.getType());

        bi = applyLumiPlus(this.biOri);

        this.biOri = bi;

        //Mise a jour des images
        this.bicl = applyLumiPlus(this.biOri);
        this.bil = applyLumiPlus(this.biOri);
        this.biCl = applyLumiPlus(this.biOri);
        this.bic = applyLumiPlus(this.biOri);
        this.biC = applyLumiPlus(this.biOri);
        this.bicL = applyLumiPlus(this.biOri);
        this.biL = applyLumiPlus( this.biOri);
        this.biCL = applyLumiPlus(this.biOri);

        //Mise a jour des boutons
        MajBtn();

        this.jd.pack();
    }

    /**
     *Previsualisation de luminosite Moins
     */
    public void previsualiserLumiMoins()
    {

        lstEffet.add(1);
        BufferedImage bi = new BufferedImage(this.biOri.getWidth(), this.biOri.getHeight(), biOri.getType());

        bi = applyLumiMoins(this.biOri);

        this.biOri = bi;

        //Mise a jour des images
        this.bicl = applyLumiMoins(this.biOri);
        this.bil = applyLumiMoins(this.biOri);
        this.biCl = applyLumiMoins(this.biOri);
        this.bic = applyLumiMoins(this.biOri);
        this.biC = applyLumiMoins(this.biOri);
        this.bicL = applyLumiMoins(this.biOri);
        this.biL = applyLumiMoins( this.biOri);
        this.biCL = applyLumiMoins(this.biOri);

        //Mise a jour des boutons
        MajBtn();

        this.jd.pack();
    }

    /**
     * Previsualisation contraste plus
     */
    public void previsualiserContPlus()
    {
        lstEffet.add(2);
        BufferedImage bi = new BufferedImage(this.biOri.getWidth(), this.biOri.getHeight(), biOri.getType());

        bi = applyContPlus(this.biOri);

        this.biOri = bi;

        //Mise a jour des images
        this.bicl = applyContPlus(this.biOri);
        this.bil = applyContPlus(this.biOri);
        this.biCl = applyContPlus(this.biOri);
        this.bic = applyContPlus(this.biOri);
        this.biC = applyContPlus(this.biOri);
        this.bicL = applyContPlus(this.biOri);
        this.biL = applyContPlus( this.biOri);
        this.biCL = applyContPlus(this.biOri);

        //Mise a jour des boutons
        MajBtn();

        this.jd.pack();
    }

    /**
     * Previsualisation contraste moins
     */
    public void previsualiserContMoins()
    {
        lstEffet.add(3);
        BufferedImage bi = new BufferedImage(this.biOri.getWidth(), this.biOri.getHeight(), biOri.getType());

        bi = applyContMoins(this.biOri);

        this.biOri = bi;

        //Mise a jour des images
        this.bicl = applyContMoins(this.biOri);
        this.bil = applyContMoins(this.biOri);
        this.biCl = applyContMoins(this.biOri);
        this.bic = applyContMoins(this.biOri);
        this.biC = applyContMoins(this.biOri);
        this.bicL = applyContMoins(this.biOri);
        this.biL = applyContMoins( this.biOri);
        this.biCL = applyContMoins(this.biOri);

        //Mise a jour des boutons
        MajBtn();

        this.jd.pack();
       
    }

    /**
     * Mise a jour des boutons contenant representant  les neuf miniatures
     */
    public void MajBtn()
    {
        this.jbOri.setIcon(new ImageIcon(this.biOri));
        this.jbcl.setIcon(new ImageIcon(this.bicl));
        this.jbl.setIcon(new ImageIcon(this.bil));
        this.jbCl.setIcon(new ImageIcon(this.biCl));
        this.jbc.setIcon(new ImageIcon(this.bic));
        this.jbC.setIcon(new ImageIcon(this.biC));
        this.jbcL.setIcon(new ImageIcon(this.bicL));
        this.jbL.setIcon(new ImageIcon(this.biL));
        this.jbCL.setIcon(new ImageIcon(this.biCL));
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
