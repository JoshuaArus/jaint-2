package JaintPlug;


import java.awt.image.BufferedImage;
import java.awt.*;
import javax.swing.*;

import JaintPlug.miroire.*;
import Jaint.*;


import Jaint.Plugin;


/**
 *
 * @author Jonas
 */
public class Miroire implements Plugin
{
    private int effectH = 0;
    private int effectV = 0;
    private boolean estActive = true;
    private boolean ok = false;
    private int nbApplyPlug = 1;
    private double scaleValue = 1;


    private BufferedImage biMiroireV;
    private BufferedImage biOri;
    private BufferedImage biMiroireH;
    private JDialog jd;
    private JPanel jp;
    private JPanel jpMiroireV;
    private JPanel jpOri;
    private JPanel jpMiroireH;

    private JButton jbMv;
    private JButton jbOri;
    private JButton jbMh;

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
        return("Miroir");
    }

    public String getDescri()
    {
        return("Permet d'effectuer la symétrie de l'image par rapport à un axe");
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
        if(img.getType() == BufferedImage.TYPE_3BYTE_BGR || img.getType() == 0)
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

        this.jd = new JDialog(JFrame.getFrames()[0], "Miroir", true);
        this.jp = new JPanel(new BorderLayout());

        this.jpMiroireV = new JPanel(new BorderLayout());
        this.jpOri = new JPanel(new BorderLayout());
        this.jpMiroireH = new JPanel(new BorderLayout());

        

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

        //Création de la miniature de l'image selon l'axe vertical
        this.biMiroireV = applyMiroireV(miniBi);

        //Création de la miniature de l'image d'origine
        this.biOri = miniBi;

        //Création de la miniature de l'image selon l'axe horizontal
        this.biMiroireH = applyMiroireH(miniBi);

        //JPanel contenant l'image et le jlabel de l'image verticale
        this.jbMv = new JButton(new ImageIcon(this.biMiroireV));
        this.jbMv.addActionListener(new EcouteurMiroireV(this));

        this.jpMiroireV.add(this.jbMv,  BorderLayout.CENTER);



        //JPanel contenant l'image et le jlabel de l'origine
        this.jbOri = new JButton(new ImageIcon(this.biOri));
        this.jbOri.addActionListener(new EcouteurOK(this));
        this.jbOri.addMouseListener(new EcouteurBoutonAppliquer(this));

        this.jpOri.add(this.jbOri,  BorderLayout.CENTER);



        //JPanel contenant l'image et le jlabel de l'image horizontale
        this.jbMh = new JButton(new ImageIcon(this.biMiroireH));
        this.jbMh.addActionListener(new EcouteurMiroireH(this));

        this.jpMiroireH.add(this.jbMh,  BorderLayout.CENTER);

         this.jp.add(jpMiroireV, BorderLayout.WEST);
         this.jp.add(jpOri, BorderLayout.CENTER);
         this.jp.add(jpMiroireH, BorderLayout.EAST);

         this.jd.add(jp);
         this.jd.pack();


         Dimension d = this.jd.getSize();
         Dimension dScreen = this.jd.getToolkit().getScreenSize();
         this.jd.setLocation((int)(dScreen.getWidth()/2-d.getWidth()/2), (int)(dScreen.getHeight()/2-d.getHeight()/2));

         //Icone pour appliquer l'effet
        JLayeredPane jlp  = this.jd.getLayeredPane();
        jlApply = new JLabel (new ImageIcon("./lib/Icons/Plugins/apply.png"));

        Dimension dApply = jlApply.getPreferredSize();
        jlApply.setBounds((int)(d.getWidth()/2 - dApply.getWidth()/2),(int)(d.getHeight()/2 - dApply.getHeight()/2), (int)dApply.getWidth(), (int)dApply.getHeight());

        jlp.add(jlApply);

        this.jd.addWindowListener(new WEcouteurFerme(this));
        this.jd.setResizable(false);
        this.jd.setVisible(true);
    }


    public BufferedImage applyEffectIcon(BufferedImage img)
    {

        BufferedImage icon = applyMiroireV(img);
        return icon;
    }


    /**
     * Applique les différents effets à l'image courante et renvoie l'image modifié
     * @param img image courante
     * @return imgModif image modifiée
     */
    public BufferedImage applyEffect(BufferedImage img)
    {
        BufferedImage bi = img;

            if(effectH == 1)
            {
                bi = applyMiroireH(bi);
            }
            if(effectV == 1)
            {
                bi = applyMiroireV(bi);
            }
        effectH = 0;
        effectV = 0;
        return bi;
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
     *Applique un miroire vertical sur l'image passée en parametre
     * @param img image courante
     * @return img image modifiée
     */
    public BufferedImage applyMiroireV(BufferedImage img)
    {
            BufferedImage imgMiroireH = new BufferedImage( img.getWidth(), img.getHeight(), img.getType());
            for ( int i = 0; i < img.getHeight(); i++ )
            {
                for ( int j = 0; j < img.getWidth(); j++ )
                {
                    int pixel = img.getRGB( j, i );
                    imgMiroireH.setRGB( img.getWidth() - j - 1, i, pixel );

                }
            }

	return imgMiroireH;
    }

    /**
     *Applique un miroire horizontal sur l'image passée en parametre
     * @param img image courante
     * @return img image modifiée
     */
    public BufferedImage applyMiroireH(BufferedImage img)
    {
        BufferedImage imgMiroireH = new BufferedImage( img.getWidth(), img.getHeight(), img.getType());
            for ( int i = 0; i < img.getHeight(); i++ )
            {
                for ( int j = 0; j < img.getWidth(); j++ )
                {
                    int pixel = img.getRGB( j, i );
                    imgMiroireH.setRGB( j, img.getHeight() - i - 1, pixel );

                }
            }

	return imgMiroireH;
    }

    /**
     *Prévisualisation miroire horizontal
     */
    public void previsualiserMiroireH()
    {

        effectH = Math.abs(effectH - 1);
        BufferedImage bi = new BufferedImage(this.biOri.getWidth(), this.biOri.getHeight(), biOri.getType());
        bi = applyMiroireH(this.biOri);

        this.biOri = bi;

        this.biMiroireH = applyMiroireH(this.biOri);
        this.biMiroireV = applyMiroireV(this.biOri);
        this.jbMv.setIcon(new ImageIcon(this.biMiroireV));
        this.jbMh.setIcon(new ImageIcon(this.biMiroireH));
        this.jbOri.setIcon(new ImageIcon(this.biOri));

        this.jd.pack();
    }

    /**
     *Prévisualisation miroire vertical
     */
    public void previsualiserMiroireV()
    {
        effectV = Math.abs(effectV - 1);

        BufferedImage bi = new BufferedImage(this.biOri.getWidth(), this.biOri.getHeight(), BufferedImage.TYPE_INT_RGB);
        bi = applyMiroireV(this.biOri);

        this.biOri = bi;

        this.biMiroireV = applyMiroireV(this.biOri);
        this.biMiroireH = applyMiroireH(this.biOri);
        
        this.jbMh.setIcon(new ImageIcon(this.biMiroireH));
        this.jbMv.setIcon(new ImageIcon(this.biMiroireV));
        this.jbOri.setIcon(new ImageIcon(this.biOri));

        this.jd.pack();
    }

    /**
     * Affiche l'icone validé sur le bouton valider si l'utilisateur se trouve dessu
     * @param entre boolean
     */
    public void modifBtnApply(boolean entre)
    {
      jlApply.setVisible(entre);
    }
}
