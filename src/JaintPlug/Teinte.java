package JaintPlug;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import Jaint.*;
import Jaint.Plugin;
import JaintPlug.teinte.*;


/**
 *
 * @author jonas
 *
 */

public class Teinte implements Plugin
{


    private JDialog jd;
    private JSliderColorChooser jsColor;
    private double scaleValue = 1;
    private boolean ok = false;

    private BufferedImage biFirst;
    private BufferedImage biNew;

    private JPanel jpimage;
    private JPanel jp;

    private JButton jbModif;
    private JLabel jlApply;

    private int red;
    private int green;
    private int blue;




    private boolean estActive = true;
    private int nbApplyPlug = 1;
    
    
    public String getName()
    {
            return("Teinte");
    }

    public String getDescri()
    {
            return("Permet de modifier la teinte de l'image ");
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
     * Prend l'image courante en parametre et retourne l'image modifié ou non
     * @param img image courante
     * @return imgModif image modifié ou non
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

            this.biFirst = img;

            this.showDialog(img);

            BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

            if (ok)
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
     * Affiche la fenetre du plugin, en prenant en parametre l'image courante
     * @param img image courante
     */
    public void showDialog(BufferedImage img)
    {

        Image mini;
        BufferedImage miniBi;
        
        this.jd = new JDialog(JFrame.getFrames()[0], "Teinte", true);


        this.jp = new JPanel(new BorderLayout());

     

        /*Ajout de l'image réduite dans le panel nord*/
        int width = img.getWidth();
        int height = img.getHeight();

        this.jpimage = new JPanel(new BorderLayout());
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

        mini = img.getScaledInstance((int)(img.getWidth() * this.scaleValue), (int)(img.getHeight() * this.scaleValue),Image.SCALE_SMOOTH);

        miniBi = Pictimage.toBufferedImage(mini);

        this.biFirst = miniBi;
        this.biNew = applyEffectPlugin(miniBi);

        //Creation et ajout des ecouteurs aux deux miniatures
        this.jbModif = new JButton(new ImageIcon(this.biNew));
        this.jbModif.addActionListener(new OkListener(this));
        this.jbModif.addMouseListener(new EcouteurBoutonAppliquer(this));

        
        this.jpimage.add(this.jbModif);

        this.jp.add(this.jpimage, BorderLayout.CENTER);

      
        this.jsColor = new JSliderColorChooser((int)this.jpimage.getPreferredSize().getWidth(),15,this);
        this.jp.add(this.jsColor, BorderLayout.SOUTH);

        this.jd.add(jp);


        Dimension d = this.jd.getSize();
        Dimension dScreen = this.jd.getToolkit().getScreenSize();
        this.jd.setLocation((int)(dScreen.getWidth()/2-d.getWidth()/2), (int)(dScreen.getHeight()/2-d.getHeight()/2));

        //Icone pour appliquer l'effet
        JLayeredPane jlp  = this.jd.getLayeredPane();
        jlApply = new JLabel (new ImageIcon("./lib/Icons/Plugins/apply.png"));

        Dimension dApply = jlApply.getPreferredSize();
        Dimension dBtnApply = this.jbModif.getPreferredSize();
        jlApply.setBounds((int)((this.jpimage.getPreferredSize().getWidth())/2 - dApply.getWidth()/2),(int)(dBtnApply.getHeight()/2 - dApply.getHeight()/2), (int)dApply.getWidth(), (int)dApply.getHeight());

        jlp.add(jlApply);
        
        this.jd.pack();
        this.jd.addWindowListener(new WEcouteurFerme(this));
        this.jd.setResizable(false);
        this.jd.setVisible(true);
        System.out.print( this.jd);

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
     * Recupere les valeurs de la couleur selectionnée par le JSliderCouleur
     */
    public void setJsValue()
    {
        Color c = this.jsColor.getColor();
        this.red = c.getRed();
        this.blue = c.getBlue();
        this.green = c.getGreen();
        this.previsualiser();
          
    }



    /**
     *Prévialiser
     */
    public void previsualiser()
    {
        this.biNew = applyEffectPlugin(this.biFirst);
        this.jbModif.setIcon(new ImageIcon(this.biNew));
    }


    public BufferedImage applyEffectIcon(BufferedImage img)
    {
        this.red = 255;
        this.green = 100;
        this.blue = 100;
        BufferedImage icon = applyEffectPlugin(img);
        return icon;
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
     * Affiche l'icone validé sur le bouton valider si l'utilisateur se trouve dessu
     * @param entre boolean
     */
    public void modifBtnApply(boolean entre)
    {
      jlApply.setVisible(entre);
    }


    /**
     * Applique l'effet à l'image en parametre et retourne l'image modifiée
     * @param img BufferedImage image courante
     * @return biModif BufferedImage image modifié
     */
    public BufferedImage applyEffectPlugin(BufferedImage image)
    {


        BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int i = 0; i < bi.getHeight(); i++)
        {
                for (int j = 0; j < bi.getWidth(); j++)
                {
                    

                        Color cPix = new Color(image.getRGB(j, i));

                        int rPix = cPix.getRed() + (cPix.getRed() * this.red)/255;

                        if (rPix > 255)
                        {
                                rPix = 255;
                        }

                        int bPix = cPix.getBlue() + (cPix.getBlue() * this.blue)/255;

                        if (bPix > 255)
                        {
                                bPix = 255;
                        }

                        int vPix = cPix.getGreen() + (cPix.getGreen()* this.green)/255;

                        if (vPix > 255)
                        {
                                vPix = 255;
                        }

                        cPix = new Color(rPix, vPix, bPix);
                        int cP = cPix.getRGB();
                        bi.setRGB(j, i, cP);
                   

                }
        }

        return bi;
    }




}
