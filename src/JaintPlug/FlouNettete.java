package JaintPlug;


import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;

import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Image;

import JaintPlug.flouNet.*;
import Jaint.*;


import Jaint.Plugin;

/**
 * Permet d'appliquer un effet de flou ou ameliorer la nettete de l'image
 * @author Jonas
 */

public class FlouNettete implements Plugin
{
    private ArrayList<Integer> lstEffet = new ArrayList<Integer>();
    private boolean estActive = true;
    private boolean ok = false;
    private int nbApplyPlug = 1;
    double scaleValue = 1;
    

    private BufferedImage biFlou;
    private BufferedImage biOri;
    private BufferedImage biNet;
    private JDialog jd;
    private JPanel jp;
    private JPanel jpFlou;
    private JPanel jpOri;
    private JPanel jpNet;
    
    

    private JButton jbFlou;
    private JButton jbOri;
    private JButton jbNet;
    private JLabel jlFlou;
    private JLabel jlNet;
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
        return("Flou / Nettete");
    }

    public String getDescri()
    {
        return("Regler le flou et la nettete de l'image");
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

            BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

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

    /**
     * Affiche la fenetre du plugin, en prenant en parametre l'image courante
     * @param img image courante
     */
    public void showDialog(BufferedImage img)
    {

        Image mini;
        BufferedImage miniBi;

        this.jd = new JDialog(JFrame.getFrames()[0], "Flou / Nettete", true);
        this.jp = new JPanel(new BorderLayout());

        this.jpFlou = new JPanel(new BorderLayout());
        this.jpOri = new JPanel(new BorderLayout());
        this.jpNet = new JPanel(new BorderLayout());

        this.jd.addWindowListener(new WEcouteurFerme(this));

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


        mini = img.getScaledInstance((int)(img.getWidth() * this.scaleValue), (int)(img.getHeight() * this.scaleValue),Image.SCALE_SMOOTH);

        miniBi = Pictimage.toBufferedImage(mini);

        //Creation de la miniature de l'image flou
        this.biFlou = applyFlou(miniBi);
        
        //Creation de la miniature de l'image d'origine
        this.biOri = miniBi;

        //Creation de la miniature de l'image plus nette
        this.biNet = applyNet(miniBi);

        //JPanel contenant l'image et le jlabel de flou
        this.jbFlou = new JButton(new ImageIcon(this.biFlou));
        this.jbFlou.addActionListener(new EcouteurFlou(this));
        this.jlFlou = new JLabel(new ImageIcon("./lib/Icons/Plugins/flou.png"));
  

        this.jpFlou.add(this.jbFlou,  BorderLayout.CENTER);
        this.jpFlou.add(this.jlFlou,  BorderLayout.WEST);


        //JPanel contenant l'image et le jlabel de l'origine
        this.jbOri = new JButton(new ImageIcon(this.biOri));
        this.jbOri.addActionListener(new EcouteurOK(this));
        this.jbOri.addMouseListener(new EcouteurBoutonAppliquer(this)); 
   

        this.jpOri.add(this.jbOri,  BorderLayout.CENTER);



        //JPanel contenant l'image et le jlabel de nettete
        this.jbNet = new JButton(new ImageIcon(this.biNet));
        this.jbNet.addActionListener(new EcouteurNet(this));
        this.jlNet = new JLabel(new ImageIcon("./lib/Icons/Plugins/nette.png"));


        this.jpNet.add(this.jbNet,  BorderLayout.CENTER);
        this.jpNet.add(this.jlNet,  BorderLayout.EAST);

         this.jp.add(jpFlou, BorderLayout.WEST);
         this.jp.add(jpOri, BorderLayout.CENTER);
         this.jp.add(jpNet, BorderLayout.EAST);

         this.jd.add(jp);
         this.jd.pack();

         
         Dimension d = this.jd.getSize();
         Dimension dScreen = this.jd.getToolkit().getScreenSize();

         this.jd.setLocation((int)(dScreen.getWidth()/2-d.getWidth()/2), (int)(dScreen.getHeight()/2-d.getHeight()/2));


         //Icone pour appliquer l'effet
        JLayeredPane jlp  = this.jd.getLayeredPane();
        jlApply = new JLabel (new ImageIcon("./lib/Icons/Plugins/apply.png"));

        Dimension dApply = jlApply.getPreferredSize();
        jlApply.setBounds((int)(d.width/2 - dApply.width/2),(int)(d.height/2 - dApply.height/2), (int)dApply.width, (int)dApply.height);

        jlp.add(jlApply);

        this.jd.setResizable(false);
        this.jd.setVisible(true);
    }

    public BufferedImage applyEffectIcon(BufferedImage img)
    {

        BufferedImage icon =  applyFlou(img);
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
                bi = applyNet(bi);
            }
            if(i == 1)
            {
                bi = applyFlou(bi);
            }
        }
        lstEffet = new ArrayList<Integer>();
        return bi;
    }


    public int getNbApplyPlug()
    {
        return nbApplyPlug;
    }


    /**
     *Applique un effet de flou a l'image passee en parametre
     * @param img BufferedImage courante
     * @return biModif BufferedImage modifie
     */
    public BufferedImage applyFlou(BufferedImage img)
    {
        Kernel kernel = new Kernel(3, 3, new float[] {6f/97f, 12f/97f, 6f/97f, 12f/97f, 25f/97f, 12f/97f, 6f/97f, 12f/97f, 6f/97f});

        ConvolveOp co = new ConvolveOp(kernel);
        BufferedImage bi = co.filter(img, null);
        return bi;

    }

    /**
     *Applique un effet de nette a l'image passee en parametre
     * @param img BufferedImage courante
     * @return biModif BufferedImage modifie
     */
    public BufferedImage applyNet(BufferedImage img)
    {
        int matriceHeight=3;
        int coefficient = 0;
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage imageNette=img;
        double r=0,g=0,b=0,r_centre=0,g_centre=0,b_centre=0;
        double red,green,blue;

        for(int turn=0;turn<1;turn++)
        {
            imageNette = new BufferedImage(width,height,img.getType());
            for(int i=0;i<width;i++)
            {
                for(int j=0;j<height;j++)
                {
                    red=0;
                    green=0;
                    blue=0;
                    coefficient =0;

                    int rgb = img.getRGB(i,j);

                    if (((rgb >>24 ) & 0xFF) != 0)
                    {
                    
                        for(int k=-(matriceHeight/2);k<=(matriceHeight/2);k++)
                        {
                            for(int l=-(matriceHeight/2);l<=(matriceHeight/2);l++)
                            {
                                if(k+i>=0 && k+i<width && l+j>=0 && l+j<height)
                                {
                                    coefficient++;
                                    Color pix_color = new Color(img.getRGB(i+k, j+l));
                                    if(k == 0 && l == 0)
                                    {
                                        r_centre = (double) pix_color.getRed();
                                        g_centre = (double) pix_color.getGreen();
                                        b_centre = (double) pix_color.getBlue();
                                    }
                                    else
                                    {
                                        r = (double) pix_color.getRed();
                                        red -= 0.1*r;
                                        g = (double) pix_color.getGreen();
                                        green -= 0.1*g;
                                        b = (double) pix_color.getBlue();
                                        blue -= 0.1*b;
                                    }
                                }
                            }
                        }

                        red += (1 + 0.1 * coefficient) * r_centre;
                        green += (1 + 0.1 * coefficient) * g_centre;
                        blue += (1 + 0.1 * coefficient) * b_centre;

                        if(red < 0)
                                red = 0;
                        else if(red > 255)
                                red = 255;

                        if(green < 0)
                                green = 0;
                        else if(green > 255)
                                green = 255;


                        if(blue < 0)
                                blue = 0;
                        else if(blue > 255)
                                blue = 255;

                        Color glow = new Color((int)red,(int)green,(int)blue);
                        imageNette.setRGB(i, j, glow.getRGB());

                    }
                    else
                    {
                        imageNette.setRGB(i,j,rgb);
                    }
                        

                }
            }
            img=imageNette;
        }
        return imageNette;

    }

    /**
     * Previsualisation de l'effet de nettete sur l'apercu
     */
    public void previsualiserNet()
    {
        lstEffet.add(0);
        BufferedImage bi = new BufferedImage(this.biOri.getWidth(), this.biOri.getHeight(), biOri.getType());
        bi = applyNet(this.biOri);

        this.biOri = bi;

        this.biNet = applyNet(this.biOri);
        this.biFlou = applyFlou(this.biOri);

        this.jbFlou.setIcon(new ImageIcon(this.biFlou));
        this.jbNet.setIcon(new ImageIcon(this.biNet));
        this.jbOri.setIcon(new ImageIcon(this.biOri));
        
        this.jd.pack();
    }

    /**
     * Previsualisation de l'effet de flou sur l'apercu
     */
    public void previsualiserFlou()
    {
        lstEffet.add(1);

        BufferedImage bi = new BufferedImage(this.biOri.getWidth(), this.biOri.getHeight(), biOri.getType());
        bi = applyFlou(this.biOri);

        this.biOri = bi;

        this.biFlou = applyFlou(this.biOri);
        this.biNet = applyNet(this.biOri);

        this.jbFlou.setIcon(new ImageIcon(this.biFlou));
        this.jbOri.setIcon(new ImageIcon(this.biOri));
        this.jbNet.setIcon(new ImageIcon(this.biNet));


        this.jd.pack();
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
